package codechef.job;

import codechef.CodechefService;
import codechef.modules.MainConfig;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Singleton
public class TokenProvider {

    private Map<String, ReentrantLock> locks = new ConcurrentHashMap<>();
    private Map<String, Long> updatedAt = new ConcurrentHashMap<>();

    @Inject
    CodechefService service;


    MainConfig config;

    @Inject
    public TokenProvider(MainConfig config) {
        this.config = config;
        locks.putIfAbsent(config.getAdminUsername(), getLock(config.getAdminUsername()));
        updatedAt.putIfAbsent(config.getAdminUsername(), 0L);
    }

    public String getAdminAccessToken() {
        return service.getAccessToken(config.getAdminUsername());
    }

    public void refreshAdminToken() {
        Long lastUpdatedAt = updatedAt.get(config.getAdminUsername());
        if ( - lastUpdatedAt + System.currentTimeMillis() <= TimeUnit.SECONDS.toMillis(10)) {
            return;
        }
        System.out.println("Refreshing admin token");
        ReentrantLock lock = locks.get(config.getAdminUsername());
        lock.lock();
        try {
            service.getNewToken(service.getUserById(config.getAdminUsername()).getAppToken(), false);
        } catch (Exception e) {
            //TODO: log
        } finally {
            updatedAt.put(config.getAdminUsername(), System.currentTimeMillis());
            lock.unlock();
        }
    }

    public String getAcessToken(String userName) {
        return service.getAccessToken(userName);
    }

    public void refreshToken(String userName) {
        Long lastUpdatedAt = updatedAt.getOrDefault(userName, 0L);
        if ( - lastUpdatedAt + System.currentTimeMillis() <= TimeUnit.SECONDS.toMillis(10)) {
            return;
        }
        System.out.println("refreshing token of " + userName);
        ReentrantLock lock = getLock(userName);
        lock.lock();
        try {
            service.getNewToken(service.getUserById(userName).getAppToken(), false);
        } catch (Exception e) {
            //TODO: log
            System.out.println("exception in refreshing token of " + userName);
        } finally {
            updatedAt.put(userName, System.currentTimeMillis());
            lock.unlock();
        }
    }

    ReentrantLock getLock(String userName) {
        if (locks.containsKey(userName)) {
            return locks.get(userName);
        }
        ReentrantLock lock = new ReentrantLock();
        locks.put(userName, lock);
        return lock;
    }
}
