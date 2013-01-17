package codechef;

import codechef.api.CodechefApiClient;
import codechef.api.RequestFilter;
import codechef.api.implementations.DiscussApiImpl;
import codechef.api.implementations.JobApiImpl;
import codechef.api.implementations.ProblemApiImpl;
import codechef.api.implementations.UserApiImpl;
import codechef.api.interfaces.DiscussApi;
import codechef.api.interfaces.JobApi;
import codechef.api.interfaces.ProblemApi;
import codechef.api.interfaces.UserApi;
import codechef.dao.interfaces.CodechefProblemDao;
import codechef.dao.interfaces.CodechefUserDao;
import codechef.dao.interfaces.DiscussDao;
import codechef.job.TokenProvider;
import codechef.modules.MainServerModules;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.glassfish.hk2.utilities.binding.AbstractBinder;


/**
 * Created by vikram.rathi on 12/21/16.
 */
public class JerseyBinder extends AbstractBinder {

    @Override
    protected void configure() {
        configureGuiceInjects();
    }

    private void configureGuiceInjects() {
        MainServerModules mainServerModules = new MainServerModules();
        Injector injector = Guice.createInjector(mainServerModules.getModules());
        bind(injector.getInstance(HelloWorldResource.class)).to(HelloWorldResource.class);
        bind(injector.getInstance(CodechefApiClient.class)).to(CodechefApiClient.class);
        bind(injector.getInstance(CodechefUserDao.class)).to(CodechefUserDao.class);
        bind(injector.getInstance(CodechefProblemDao.class)).to(CodechefProblemDao.class);
        bind(injector.getInstance(DiscussDao.class)).to(DiscussDao.class);
        bind(injector.getInstance(CodechefService.class)).to(CodechefService.class);
        bind(injector.getInstance(UserApiImpl.class)).to(UserApi.class);
        bind(injector.getInstance(JobApiImpl.class)).to(JobApi.class);
        bind(injector.getInstance(DiscussApiImpl.class)).to(DiscussApi.class);
        bind(injector.getInstance(ProblemApiImpl.class)).to(ProblemApi.class);
        bind(injector.getInstance(RequestFilter.class)).to(RequestFilter.class);
        bind(injector.getInstance(TokenProvider.class)).to(TokenProvider.class);
    }
}
