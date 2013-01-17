package codechef.main;

import codechef.CodechefService;
import codechef.models.Problem;
import codechef.models.User;
import com.google.inject.Inject;
import org.hibernate.Session;
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

public class Main {

    @Inject
    static CodechefService service;

//    private static final SessionFactory sessionFactory = buildSessionFactory();
//
//    private static SessionFactory buildSessionFactory() {
//        try {
//            // Create the SessionFactory from hibernate.cfg.xml
//            Configuration configuration = new Configuration();
//            configuration.configure("hibernate.cfg.xml");
//            for (Class cls : getEntityClassesFromPackage("codechef.models")) {
//                configuration.addAnnotatedClass(cls);
//            }
//            ServiceRegistry sr = new StandardServiceRegistryBuilder()
//                    .applySettings(configuration.getProperties()).build();
//            return configuration.buildSessionFactory(sr);
//        } catch (Throwable ex) {
//            System.err.println("Initial SessionFactory creation failed." + ex);
//            throw new ExceptionInInitializerError(ex);
//        }
//    }
//
//    public static SessionFactory getSessionFactory() {
//        return sessionFactory;
//    }
//
//    public static List<Class<?>> getEntityClassesFromPackage(String packageName) throws ClassNotFoundException, IOException, URISyntaxException {
//        List<String> classNames = getClassNamesFromPackage(packageName);
//        List<Class<?>> classes = new ArrayList<Class<?>>();
//        for (String className : classNames) {
//            Class<?> cls = Class.forName(packageName + "." + className);
//            Annotation[] annotations = cls.getAnnotations();
//
//            for (Annotation annotation : annotations) {
//                System.out.println(cls.getCanonicalName() + ": " + annotation.toString());
//                if (annotation instanceof javax.persistence.Entity) {
//                    classes.add(cls);
//                }
//            }
//        }
//
//        return classes;
//    }
//
//    public static ArrayList<String> getClassNamesFromPackage(String packageName) throws IOException, URISyntaxException, ClassNotFoundException {
//        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//        ArrayList<String> names = new ArrayList<String>();
//
//        packageName = packageName.replace(".", "/");
//        URL packageURL = classLoader.getResource(packageName);
//
//        URI uri = new URI(packageURL.toString());
//        File folder = new File(uri.getPath());
//        File[] files = folder.listFiles();
//        for (File file : files) {
//            String name = file.getName();
//            name = name.substring(0, name.lastIndexOf('.'));  // remove ".class"
//            names.add(name);
//        }
//
//        return names;
//    }

    public static void main(String[] args) {
        service.addProblem(new Problem("name", "code1", 0, ""));
    }
}