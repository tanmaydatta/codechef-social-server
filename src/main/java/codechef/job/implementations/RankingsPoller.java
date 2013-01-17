package codechef.job.implementations;

import codechef.CodechefService;
import codechef.api.CodechefApiClient;
import codechef.api.models.CodechefRankApiModel;
import codechef.job.TokenProvider;
import codechef.job.interfaces.PollingTask;
import codechef.models.ContestStatus;
import codechef.models.Contests;
import codechef.models.Rankings;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Singleton
public class RankingsPoller extends TimerTask implements PollingTask {

    @Inject
    CodechefApiClient client;

    @Inject
    CodechefService service;

    @Inject
    TokenProvider tokenProvider;

    ScheduledExecutorService threadPool = Executors.newSingleThreadScheduledExecutor();
    ExecutorService fetchThreadPool = Executors.newFixedThreadPool(10);
    private static final long PERIOD = TimeUnit.SECONDS.toMillis(10);
    private List<CodechefRankApiModel> response = new CopyOnWriteArrayList<>();
    private Contests currContest;
    private boolean fetching = true;


    @Override
    public void poll() {
        if (threadPool.isShutdown()) {
            threadPool = Executors.newSingleThreadScheduledExecutor();
        }
        threadPool.scheduleAtFixedRate(this, 0, PERIOD, TimeUnit.MILLISECONDS);
    }

    @Override
    public void fetch() {
        for (ContestStatus status : ContestStatus.values()) {
            if (status == ContestStatus.FUTURE || status == ContestStatus.PAST) {
                continue;
            }
            List<Contests> contestsList = service.getContestsByStatus(status);
            contestsList.forEach(contest -> {
                currContest = contest;
                fetchThreadPool.submit(() -> getRankList(contest.getContestCode()));
//                getRankList(contest.getContestCode());
                process();
            });
        }
    }

    @Override
    public void process() {
        while (fetching || !response.isEmpty()) {
            response.forEach(rank -> {
                rank.getResult().getData().getContent().forEach(content -> {
                    System.out.println("Adding rank " + content.getRank() + "for contest " + currContest.getContestName());
                    service.addRank(new Rankings(Long.parseLong(content.getRank()), currContest,
                            content.getUsername(), content.getInstitution(), Double.parseDouble(content.getTotalScore())));
                });
            });
        }
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

    private void getRankList(String contestCode) {
        System.out.println("fetching ranklist for " + contestCode);
        fetching = true;
        int code = 9001;
        int offset = 0;
        while (code == 9001 || code == 9002) {
            CodechefRankApiModel ranks;
            try {
                ranks = client.getRankingsOfContest(contestCode, offset, tokenProvider.getAdminAccessToken());
                code = ranks.getResult().getData().getCode();
            } catch (Exception e) {
                tokenProvider.refreshAdminToken();
                code = 9001;
                continue;
            }
            if (code == 9001) {
                response.add(ranks);
                offset += 25;
            } else if (code == 9002) {
                tokenProvider.refreshAdminToken();
            } else {
                System.out.println(code);
            }
        }
        fetching = false;
    }
}
