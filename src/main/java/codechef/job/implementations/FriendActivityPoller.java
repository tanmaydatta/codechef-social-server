package codechef.job.implementations;

import codechef.CodechefService;
import codechef.api.CodechefApiClient;
import codechef.api.models.CodechefSubmissionsApiModel;
import codechef.job.TokenProvider;
import codechef.job.interfaces.Notifier;
import codechef.job.interfaces.PollingTask;
import codechef.models.Feed;
import codechef.models.FeedType;
import codechef.models.Friend;
import codechef.modules.MainConfig;
import com.google.inject.Inject;

import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static codechef.job.implementations.ContestPoller.getDateFromString;

public class FriendActivityPoller extends TimerTask implements PollingTask {


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
    private Friend currentUser;
    private String currentFriend;
    private CodechefSubmissionsApiModel.SubmissionDetail submission;

    CodechefSubmissionsApiModel response;

    @Override
    public void poll() {
        if (threadPool.isShutdown()) {
            threadPool = Executors.newSingleThreadScheduledExecutor();
        }
        threadPool.scheduleAtFixedRate(this, 0, config.getFRIEND_ACTIVITY_JOB_INTERVAL(), TimeUnit.MILLISECONDS);
    }

    @Override
    public void fetch() {
        List<Friend> friends = service.getAllFriends();
        friends.forEach(u -> {
            currentUser = u;
            System.out.println("fetching friends for " + u.getUser().getUserName());
            List<String> friendList = Arrays.asList(u.getFriends().split(","));
            friendList.forEach(f -> {
                currentFriend = f;
                try {
                    response = client.getSubmissionsOfUser(f, tokenProvider.getAcessToken(u.getId()));
                    process();
                } catch (Exception e) {
                    tokenProvider.refreshToken(u.getId());
                    try {
                        Thread.sleep(TimeUnit.SECONDS.toMillis(20));
                    } catch (Exception e1) {
                        //TODO: log
                        e1.printStackTrace();
                    }
                }
//                process();
            });
        });
    }

    @Override
    public void process() {
//        service.add
//        Friend friend = new Friend();
//        friend.setFriends(response);
//        friend.setUser(currentUser);
//        friend.setId(currentUser.getUserName());
//        service.addFriends(friend);
        response.getResult().getData().getContent().forEach(s -> {
            submission = s;
            if (!service.isSubmissionFeed(currentUser.getUser(), currentFriend, s.getId())) {
                Feed feed = new Feed();
                feed.setFeedDate(getDateFromString(s.getDate()));
                boolean isNotification = service.isPresentContest(s.getContestCode());
                feed.setUser(currentUser.getUser());
                feed.setSubmissionId(s.getId());
                feed.setActionUser(currentFriend);
                feed.setContestCode(s.getContestCode());
                feed.setNotified(false);
                feed.setType(FeedType.SUBMISSION);
                feed.setNotification(isNotification);
                feed.setSubmissionResult(s.getResult());
                feed.setProblemCode(s.getProblemCode());
                service.addFeed(feed);
                if (isNotification) {
                    // nptify
                    notifyUser();
//                    notifier.notifyForSubmission(currentUser.getUser(), currentFriend, s.getProblemCode());
                }
            }
        });

    }

    @Override
    public void notifyUser() {
        notifier.notifyForSubmission(currentUser.getUser(), currentFriend, submission.getProblemCode());
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
