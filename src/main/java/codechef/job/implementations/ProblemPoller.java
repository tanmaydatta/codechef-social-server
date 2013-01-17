package codechef.job.implementations;

import codechef.CodechefService;
import codechef.api.CodechefApiClient;
import codechef.api.models.CodechefProblemsResponse;
import codechef.job.TokenProvider;
import codechef.job.interfaces.PollingTask;
import codechef.models.Problem;
import codechef.modules.MainConfig;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Singleton
public class ProblemPoller extends TimerTask implements PollingTask {

    @Inject
    CodechefApiClient client;

    @Inject
    TokenProvider tokenProvider;

    @Inject
    CodechefService service;

    @Inject
    MainConfig config;


    ScheduledExecutorService threadPool = Executors.newSingleThreadScheduledExecutor();
    CodechefProblemsResponse response;
    String currentCategory;

    private static final String[] categories = {"school", "easy", "medium", "hard", "challenge", "extcontest"};


    @Override
    public void poll() {
        if (threadPool.isShutdown()) {
            threadPool = Executors.newSingleThreadScheduledExecutor();
        }
        threadPool.scheduleAtFixedRate(this, 0, config.getPROBLEM_JOB_INTERVAL(), TimeUnit.MILLISECONDS);
    }

    @Override
    public void fetch() {
        for (final String category : categories) {
            System.out.println("fetching problems for " + category);
            currentCategory = category;
            int code = 9001;
            int offset = 0;
            while (code == 9001 || code == 9002) {
                try {
                    response = client.getProblemsByCategoryName(category,
                            offset, tokenProvider.getAdminAccessToken());
                    code = response.getResult().getData().getCode();
                } catch (Exception e) {
                    tokenProvider.refreshAdminToken();
                    try {
                        Thread.sleep(TimeUnit.SECONDS.toMillis(10));
                    } catch (Exception e1) {
                        //TODO: log
                        e1.printStackTrace();
                    }
                    code = 9001;
                    continue;
                }
                if (code == 9001) {
                    process();
                    offset += 100;
                } else if (code == 9002) {
                    tokenProvider.refreshAdminToken();
                    try {
                        Thread.sleep(TimeUnit.SECONDS.toMillis(10));
                    } catch (Exception e1) {
                        //TODO: log
                        e1.printStackTrace();
                    }
                } else {
                    System.out.println(code);
                }
            }
        }
    }

    @Override
    public void process() {
        response.getResult().getData().getContent().stream().forEach(content -> {
            System.out.println("Adding " + content.getProblemCode());
            service.addProblem(new Problem(content.getProblemName(),
                    content.getProblemCode(), content.getSubmissions(), currentCategory));
        });
    }

    @Override
    public void notifyUser() {

    }

    @Override
    public void stop() {
        try {
            threadPool.shutdown();
        } catch (Exception e) {
            //TODO: log
        }
    }

    @Override
    public void run() {
        fetch();
    }
}
