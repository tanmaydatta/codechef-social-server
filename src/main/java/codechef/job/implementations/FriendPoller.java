package codechef.job.implementations;

import codechef.CodechefService;
import codechef.api.CodechefApiClient;
import codechef.api.models.SetMembers;
import codechef.job.TokenProvider;
import codechef.job.interfaces.PollingTask;
import codechef.models.Friend;
import codechef.models.User;
import codechef.models.UserRating;
import codechef.modules.MainConfig;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Singleton
public class FriendPoller extends TimerTask implements PollingTask {


    @Inject
    CodechefApiClient client;

    @Inject
    TokenProvider tokenProvider;

    @Inject
    CodechefService service;


    @Inject
    MainConfig config;


    ScheduledExecutorService threadPool = Executors.newSingleThreadScheduledExecutor();
    private User currentUser;

    List<SetMembers.Member> response;

    @Override
    public void poll() {
        if (threadPool.isShutdown()) {
            threadPool = Executors.newSingleThreadScheduledExecutor();
        }
        threadPool.scheduleAtFixedRate(this, 0, config.getFRIEND_JOB_INTERVAL(), TimeUnit.MILLISECONDS);
    }

    @Override
    public void fetch() {
        List<User> users = service.getAllUsers();
        users.stream().forEach(u -> {
            if (true || !u.getUserName().equals(config.getAdminUsername())) {
                currentUser = u;
                System.out.println("fetching friends for " + u.getUserName());
                try {
                    response = client.getFriendsOfUser(tokenProvider.getAcessToken(u.getUserName()));
                    process();
                } catch (Exception e) {
                    tokenProvider.refreshToken(u.getUserName());
                    try {
                        Thread.sleep(TimeUnit.SECONDS.toMillis(20));
                    } catch (Exception e1) {
                        //TODO: log
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void process() {
//        service.add
        Friend friend = new Friend();
        friend.setFriends(String.join(",",
                response.stream().map(SetMembers.Member::getMemberName).collect(Collectors.toList())));
        friend.setUser(currentUser);
        friend.setId(currentUser.getUserName());
        service.addFriends(friend);
        response.forEach(m -> {
            UserRating rating = new UserRating();
            rating.setUserName(m.getMemberName());
            rating.setAllContestRating(m.getAllContestRating());
            rating.setAllSchoolContestRating(m.getAllSchoolContestRating());
            rating.setLongContestRating(m.getLongContestRating());
            rating.setLongSchoolContestRating(m.getLongSchoolContestRating());
            rating.setlTimeContestRating(m.getlTimeContestRating());
            rating.setlTimeSchoolContestRating(m.getlTimeSchoolContestRating());
            rating.setShortContestRating(m.getShortContestRating());
            rating.setShortSchoolContestRating(m.getShortSchoolContestRating());
            service.addUserRating(rating);
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
