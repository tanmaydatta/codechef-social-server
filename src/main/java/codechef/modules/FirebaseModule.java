package codechef.modules;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.inject.AbstractModule;

import java.io.FileInputStream;

public class FirebaseModule extends AbstractModule {

    public FirebaseModule() {
    }

    @Override
    protected void configure() {
        bind(FirebaseMessaging.class).toInstance(getFireBaseInstance());
    }

    FirebaseMessaging getFireBaseInstance() {
        try {
            System.out.println(System.getProperty("user.dir"));
            FileInputStream serviceAccount = new FileInputStream("/etc/firebase_ccsocial.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://codechefsocial.firebaseio.com")
                    .build();
            FirebaseApp.initializeApp(options);
            return FirebaseMessaging.getInstance(FirebaseApp.getInstance());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            //TODO: log
            return null;
        }
    }
}
