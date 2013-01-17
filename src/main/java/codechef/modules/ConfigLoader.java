package codechef.modules;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Locale;

import static com.google.common.base.Preconditions.checkNotNull;

final class ConfigLoader {

//    private static final //LOGger //LOG = //LOGgerFactory.get//LOGger(ConfigLoader.class);
    private static final Env DEFAULT_ENV = Env.DEV;
    private static final String CODECHEF_ENV_FILE_NAME = "codechef.env";
    private final String appName;
    private final String confFile;
    private final Config config;

    private Env env;
    private String envPrefix;

    public ConfigLoader(String appName) {
        checkNotNull(appName, "appName is null");
        //LOG.info("Loading config for app : {}", appName);
        this.appName = appName;
        this.confFile = appName + ".conf";
        this.config = loadConfig();
    }

    public Env getEnv() {
        return env;
    }

    public String getEnvPrefix() {
        return envPrefix;
    }

    public Config getConfig() {
        return config;
    }

    private Config loadConfig() {
        Config filesystemConfig = loadFilesystemConfig();
        Config appConfig = filesystemConfig.getConfig(appName);

        String env = readEnvironmentFromFile();
        //LOG.info("Environment variable from file: {}", env);

        if (env == null && appConfig.hasPath("env")) {
            env = appConfig.getString("env");
            //LOG.info("Using env from conf file: {}", env);
        }

        if (env == null) {
            //LOG.warn("env is not specified in environment variables or conf file. Using {} as default", DEFAULT_ENV);
            env = DEFAULT_ENV.name();
        }

        env = env.toLowerCase(Locale.ENGLISH);
        //LOG.info("Configuring {} for {} environment", getClass().getSimpleName(), env);

        this.env = Env.fromString(env);
        Config envConfig = appConfig.getConfig(env);

        envPrefix = appName + "_" + env + "_";

        return envConfig.withFallback(appConfig);
    }

    private Config loadFilesystemConfig() {
        String[] configDirs = {"/etc/codechef", System.getProperty("user.home")};
        Config originalConfig = null;
        for (String dir : configDirs) {
            File file = new File(dir, appName + ".conf").getAbsoluteFile();
            if (!file.exists()) {
                continue;
            }
            //LOG.info("Configuring {} from file {}", getClass().getSimpleName(), file);
            originalConfig = ConfigFactory.parseFile(file);
            break;
        }
        if (originalConfig == null) {
            throw new IllegalStateException(appName + ".conf not found in paths " + Arrays.asList(configDirs));
        }
        return originalConfig;
    }

    private String readEnvironmentFromFile() {
        String[] configDirs = {"/etc/codechef", System.getProperty("user.home")};
        for (String dir : configDirs) {
            File file = new File(dir, CODECHEF_ENV_FILE_NAME).getAbsoluteFile();
            if (!file.exists()) {
                continue;
            }

            try {
                String contents = new String(Files.readAllBytes(Paths.get(file.toURI())));
                return contents.trim();
            } catch (Exception e) {
                //LOG.error("Unable to readEnvironmentFromFile()", e);
            }
        }

        return null;
    }
}
