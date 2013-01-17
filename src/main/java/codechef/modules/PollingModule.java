package codechef.modules;

import codechef.job.ContestJob;
import codechef.job.FriendActivityJob;
import codechef.job.FriendJob;
import codechef.job.ProblemJob;
import codechef.job.RankingJob;
import codechef.job.implementations.ContestPoller;
import codechef.job.implementations.FcmNotifier;
import codechef.job.implementations.FriendActivityPoller;
import codechef.job.implementations.FriendPoller;
import codechef.job.implementations.ProblemPoller;
import codechef.job.implementations.RankingsPoller;
import codechef.job.interfaces.Notifier;
import codechef.job.interfaces.PollingTask;
import com.google.inject.AbstractModule;
import com.typesafe.config.Config;

public class PollingModule extends AbstractModule {

    Config config;

    public PollingModule(Config config) {
        this.config = config;
    }

    @Override
    protected void configure() {

        bind(PollingTask.class).annotatedWith(ProblemJob.class).toInstance(new ProblemPoller());
        bind(PollingTask.class).annotatedWith(RankingJob.class).toInstance(new RankingsPoller());
        bind(PollingTask.class).annotatedWith(FriendJob.class).toInstance(new FriendPoller());
        bind(PollingTask.class).annotatedWith(ContestJob.class).toInstance(new ContestPoller());
        bind(PollingTask.class).annotatedWith(FriendActivityJob.class).toInstance(new FriendActivityPoller());
        bind(Notifier.class).toInstance(new FcmNotifier());
        bind(MainConfig.class).toInstance(new MainConfig(config));
    }
}
