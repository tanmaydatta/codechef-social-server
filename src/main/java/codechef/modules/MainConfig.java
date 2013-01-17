package codechef.modules;

import com.typesafe.config.Config;

import java.util.concurrent.TimeUnit;

public class MainConfig {

    Config config;
    private String ADMIN_USERNAME = "tanmaydatta";
    private long FRIEND_JOB_INTERVAL = TimeUnit.MINUTES.toMillis(1);
    private long FRIEND_ACTIVITY_JOB_INTERVAL = TimeUnit.MINUTES.toMillis(1);
    private long CONTEST_JOB_INTERVAL = TimeUnit.MINUTES.toMillis(30);
    private long PROBLEM_JOB_INTERVAL = TimeUnit.HOURS.toMillis(12);
    private long CONTEST_REMIND_BUFFER = TimeUnit.HOURS.toMillis(24);
    private String CLIENT_ID;
    private String CLIENT_SECRET;

    public MainConfig(Config config) {
        this.config = config;
        try {
            ADMIN_USERNAME = config.getString("admin");
        } catch (Exception e) {
            //TODO: could not get admin username from config
            System.out.println("could not get admin username from config");
        }
        try {
            FRIEND_JOB_INTERVAL = config.getLong("FRIEND_JOB_INTERVAL");
        } catch (Exception e) {
            System.out.println("could not get FRIEND_JOB_INTERVAL from config");
        }
        try {
            FRIEND_ACTIVITY_JOB_INTERVAL = config.getLong("FRIEND_ACTIVITY_JOB_INTERVAL");
        } catch (Exception e) {
            System.out.println("could not get FRIEND_ACTIVITY_JOB_INTERVAL from config");
        }
        try {
            CONTEST_JOB_INTERVAL = config.getLong("CONTEST_JOB_INTERVAL");
        } catch (Exception e) {
            System.out.println("could not get CONTEST_JOB_INTERVAL from config");
        }
        try {
            PROBLEM_JOB_INTERVAL = config.getLong("PROBLEM_JOB_INTERVAL");
        } catch (Exception e) {
            System.out.println("could not get PROBLEM_JOB_INTERVAL from config");
        }
        try {
            CONTEST_REMIND_BUFFER = config.getLong("CONTEST_REMIND_BUFFER");
        } catch (Exception e) {
            System.out.println("could not get CONTEST_REMIND_BUFFER from config");
        }
        CLIENT_ID = config.getString("client_id");
        CLIENT_SECRET = config.getString("client_secret");
    }

    public String getAdminUsername() {
        return ADMIN_USERNAME;
    }

    public long getFRIEND_JOB_INTERVAL() {
        return FRIEND_JOB_INTERVAL;
    }

    public long getFRIEND_ACTIVITY_JOB_INTERVAL() {
        return FRIEND_ACTIVITY_JOB_INTERVAL;
    }

    public long getCONTEST_JOB_INTERVAL() {
        return CONTEST_JOB_INTERVAL;
    }

    public long getPROBLEM_JOB_INTERVAL() {
        return PROBLEM_JOB_INTERVAL;
    }

    public String getCLIENT_ID() {
        return CLIENT_ID;
    }

    public String getCLIENT_SECRET() {
        return CLIENT_SECRET;
    }

    public long getCONTEST_REMIND_BUFFER() {
        return CONTEST_REMIND_BUFFER;
    }
}
