package codechef;

import com.google.inject.Inject;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/")
public class JerseyResourceConfig extends ResourceConfig {


    @Inject
    public JerseyResourceConfig() {
        super();
        System.out.println("tanmay registering jersey");
//        register(JacksonFeature.class);
        register(new JerseyBinder());
//        register(new LoggingFeature(LOG, LoggingFeature.Verbosity.PAYLOAD_ANY));
//        packages(true, JerseyResourceConfig.class.getPackage().getName());
//        packages(JerseyResourceConfig.class.getPackage().getName(), HelloWorld.class.getPackage().getName());
    }
}
