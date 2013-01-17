package codechef.job.interfaces;

public interface PollingTask {

    void poll();

    void fetch();

    void process();

    void notifyUser();

    void stop();
}
