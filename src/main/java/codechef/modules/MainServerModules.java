package codechef.modules;

public class MainServerModules extends EnvModules {
    public MainServerModules() {
        addModules(new NetworkModule());
        addModules(new MySqlModule());
        addModules(new PollingModule(getConfig()));
        addModules(new FirebaseModule());
    }
}
