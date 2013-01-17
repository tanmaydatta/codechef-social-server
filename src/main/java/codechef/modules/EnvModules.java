package codechef.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.typesafe.config.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public abstract class EnvModules {

    private List<Module> modules = new ArrayList<>();

    private static Env env;
    private static Config config;
    private static ConfigLoader configLoader;

    protected EnvModules() {
        loadConfig();
        verifyProdEnv();
        addModules(new AbstractModule() {
            @Override
            protected void configure() {
                bind(Env.class).toInstance(env);
                bind(Config.class).toInstance(config);
                bind(String.class).annotatedWith(EnvPrefix.class).toInstance(configLoader.getEnvPrefix());
            }
        });
    }

    private void verifyProdEnv() {
        if (env == Env.PROD) {
            String os = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
            if (os.contains("mac") || os.contains("win")) {
                throw new AssertionError(Env.PROD + " mode not allowed on laptops");
            }
        }
    }

    public Env getEnv() {
        return env;
    }

    public Config getConfig() {
        return config;
    }

    protected EnvModules addModules(Module... modules) {

        for (Module module : modules) {
            System.out.println("adding module" + module.toString());
            this.modules.add(module);
        }
        return this;
    }

    protected EnvModules addModules(List<Module> modules) {
        for (Module module : modules) {
            this.modules.add(module);
        }
        return this;
    }

    public List<Module> getModules() {
        return modules;
    }

    private static void loadConfig() {
        if (configLoader == null) {
            configLoader = new ConfigLoader("codechef_social");
            env = configLoader.getEnv();
            config = configLoader.getConfig();
        }
    }
}
