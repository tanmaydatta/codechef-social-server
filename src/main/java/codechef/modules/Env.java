package codechef.modules;

import static com.google.common.base.Preconditions.checkNotNull;

public enum Env {

    DEV("codechef_social_dev_"),
    PROD("codechef_social_prod_");

    private final String prefix;

    Env(String prefix) {
        this.prefix = prefix;
    }


    String getPrefix() {
        return prefix;
    }

    public static Env fromString(String value) {
        checkNotNull(value, "value is null");
        for (Env env : values()) {
            if (env.name().equalsIgnoreCase(value)) {
                return env;
            }
        }
        throw new IllegalArgumentException("Unknown env : " + value);
    }
}