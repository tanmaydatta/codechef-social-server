package codechef.modules;

import com.google.inject.AbstractModule;
import okhttp3.OkHttpClient;

    public class NetworkModule extends AbstractModule {

        @Override
        protected void configure() {
            bind(OkHttpClient.class).toInstance(new OkHttpClient.Builder().build());
        }

}
