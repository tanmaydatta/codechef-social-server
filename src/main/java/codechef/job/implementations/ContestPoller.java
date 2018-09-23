package codechef.job.implementations;

import codechef.CodechefService;
import codechef.api.CodechefApiClient;
import codechef.api.models.CodechefContestsApiModel;
import codechef.job.TokenProvider;
import codechef.job.interfaces.Notifier;
import codechef.job.interfaces.PollingTask;
import codechef.models.Contests;
import codechef.models.Feed;
import codechef.models.FeedType;
import codechef.modules.MainConfig;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Singleton
public class ContestPoller extends TimerTask implements PollingTask {

    @Inject
    CodechefApiClient client;

    @Inject
    TokenProvider tokenProvider;

    @Inject
    CodechefService service;

    @Inject
    Notifier notifier;

    @Inject
    MainConfig config;

    ScheduledExecutorService threadPool = Executors.newSingleThreadScheduledExecutor();
    //    private static final String[] statuses = {"future"};
    private static final String[] statuses = {"present", "future"};
    private static final List<String> monthName = Arrays.asList("January", "February",
            "March", "April", "May", "June", "July",
            "August", "September", "October", "November",
            "December");
    CodechefContestsApiModel response;
    CodechefContestsApiModel.Contest contest;

    @Override
    public void poll() {
        if (threadPool.isShutdown()) {
            threadPool = Executors.newSingleThreadScheduledExecutor();
        }
        threadPool.scheduleAtFixedRate(this, 0, config.getCONTEST_JOB_INTERVAL(), TimeUnit.MILLISECONDS);
    }

    @Override
    public void fetch() {
        for (final String status : statuses) {
            System.out.println("fetching contests for " + status);
            try {
                response = client.getContestsByStatus(status, tokenProvider.getAdminAccessToken());
                int code = response.getResult().getData().getCode();
                if (code == 9001) {
                    process();
                } else if (code == 9002) {
                    tokenProvider.refreshAdminToken();
                } else {
                    System.out.println(code);
                }
            } catch (Exception e) {
                tokenProvider.refreshAdminToken();
                try {
                    Thread.sleep(TimeUnit.SECONDS.toMillis(20));
                } catch (Exception e1) {
                    //TODO: log
                    e1.printStackTrace();
                }
            }

        }
    }

    @Override
    public void process() {
        response.getResult().getData().getContent().getContestList().forEach(content -> {
            if (!isValidContest(content)) {
                return;
            }
            System.out.println("Adding " + content.getName());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            try {
                Date start = formatter.parse(content.getStartDate());
                Date end = formatter.parse(content.getEndDate());
                service.addContest(new Contests(content.getName(), content.getCode(), start, end));
                if (start.getTime() > System.currentTimeMillis()
                        && start.getTime() - System.currentTimeMillis()
                        <= TimeUnit.MILLISECONDS.toHours(config.getCONTEST_REMIND_BUFFER())) {
                    contest = content;
                    notifyUser();
                }
            } catch (Exception e) {
                System.out.println("not able to add contest: " + content.getName());
                //TODO:log
            }
        });
    }

    private boolean isValidContest(CodechefContestsApiModel.Contest content) {
        Date start = getDateFromString(content.getStartDate());
        try {
            if (start.getTime() < 1519842600000L) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }

//        if (content.getName().toLowerCase().contains("lunchtime")
//                || content.getName().toLowerCase().contains("cook-off")) {
//            return content.getName().toLowerCase().contains("division");
//        }
//        if (content.getName().toLowerCase().contains(" challenge ")) {
//            for (String m : monthName) {
//                if (content.getName().contains(m)) {
//                    return content.getName().toLowerCase().contains("division");
//                }
//            }
//        }
        return true;
    }

    public static Date getDateFromString(String str) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        try {
            Date start = formatter.parse(str);
            return start;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void notifyUser() {
//        List<User>
        service.getAllUsers().forEach(u -> {
            if (!service.isContestFeed(u, contest.getCode())) {
                Feed feed = new Feed();
                feed.setNotification(true);
                feed.setContestCode(contest.getCode());
                feed.setContestName(contest.getName());
                feed.setUser(u);
                feed.setType(FeedType.CONTEST);
                feed.setFeedDate(getDateFromString(contest.getStartDate()));
                service.addFeed(feed);
                notifier.notifyForContest(u, contest.getName());
            }
        });

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
