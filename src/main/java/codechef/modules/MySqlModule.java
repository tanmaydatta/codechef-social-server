package codechef.modules;

import codechef.models.Comment;
import codechef.models.Contests;
import codechef.models.Feed;
import codechef.models.Friend;
import codechef.models.Messages;
import codechef.models.Post;
import codechef.models.Problem;
import codechef.models.Rankings;
import codechef.models.Reply;
import codechef.models.User;
import codechef.models.UserRating;
import codechef.models.Votes;
import com.google.inject.AbstractModule;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MySqlModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(SessionFactory.class).toInstance(buildSessionFactory());
    }

    private SessionFactory buildSessionFactory() {
        System.out.println("building session factory");
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
//            for (Class cls : getEntityClassesFromPackage("codechef.models")) {
//                configuration.addAnnotatedClass(cls);
//            }
            configuration.addAnnotatedClass(Problem.class);
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(Votes.class);
            configuration.addAnnotatedClass(Reply.class);
            configuration.addAnnotatedClass(Comment.class);
            configuration.addAnnotatedClass(Post.class);
            configuration.addAnnotatedClass(Messages.class);
            configuration.addAnnotatedClass(Feed.class);
            configuration.addAnnotatedClass(Rankings.class);
            configuration.addAnnotatedClass(Contests.class);
            configuration.addAnnotatedClass(Friend.class);
            configuration.addAnnotatedClass(UserRating.class);

            ServiceRegistry sr = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            return configuration.buildSessionFactory(sr);
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static List<Class<?>> getEntityClassesFromPackage(String packageName)
            throws ClassNotFoundException, IOException, URISyntaxException {
        List<String> classNames = getClassNamesFromPackage(packageName);
        List<Class<?>> classes = new ArrayList<Class<?>>();
        for (String className : classNames) {
            Class<?> cls = Class.forName(packageName + "." + className);
            Annotation[] annotations = cls.getAnnotations();

            for (Annotation annotation : annotations) {
                System.out.println(cls.getCanonicalName() + ": " + annotation.toString());
                if (annotation instanceof javax.persistence.Entity) {
                    classes.add(cls);
                }
            }
        }

        return classes;
    }

    public static ArrayList<String> getClassNamesFromPackage(String packageName)
            throws IOException, URISyntaxException, ClassNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        ArrayList<String> names = new ArrayList<String>();

        packageName = packageName.replace(".", "/");
        URL packageURL = classLoader.getResource(packageName);

        URI uri = new URI(packageURL.toString());
        File folder = new File(uri.getPath());
        File[] files = folder.listFiles();
        for (File file : files) {
            String name = file.getName();
            name = name.substring(0, name.lastIndexOf('.'));  // remove ".class"
            names.add(name);
        }

        return names;
    }

}
