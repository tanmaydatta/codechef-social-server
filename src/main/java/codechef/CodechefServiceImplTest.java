package codechef;

import codechef.api.CodechefApiClient;
import codechef.api.models.CodechefProblemsResponse;
import codechef.api.models.MessageApiModel;
import codechef.api.models.MessageList;
import codechef.models.Problem;
import codechef.models.User;
import codechef.models.UserRating;
import codechef.modules.FirebaseModule;
import codechef.modules.MySqlModule;
import codechef.modules.PollingModule;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class CodechefServiceImplTest {

    private Injector injector;

    private CodechefService service;
    private CodechefApiClient client;
    private FirebaseMessaging firebase;
    private static final String[] categories = {"school", "easy", "medium", "hard", "challenge", "extcontest"};

    @BeforeMethod
    public void setUp() throws Exception {
        injector = Guice.createInjector(new MySqlModule(), new FirebaseModule());
        service = injector.getInstance(CodechefServiceImpl.class);
        client = injector.getInstance(CodechefApiClient.class);
        firebase = injector.getInstance(FirebaseMessaging.class);
    }

    @Test
    public void testName() {
//        String s = service.getNewToken("3ea2ad654ae80a66e2ef63ce155206466d962716").getAccessToken();
//        System.out.println(s);
        new Thread(() -> System.out.println("he")).start();
    }

    @Test
    public void testAddProblem() {
        int reqCount = 0;
        String accessToken = service.getNewToken("tanmaydatta", false).getAccessToken();
        for (int i = 0; i < categories.length; i++) {
            final String category = categories[i];
            int code = 9001;
            int offset = 0;
            while (code == 9001) {
                if (reqCount >= 25) {
                    reqCount = 0;
                    accessToken = service.getNewToken("tanmaydatta", false).getAccessToken();
                }
                CodechefProblemsResponse response;
                try {
                    response = client.getProblemsByCategoryName(category,
                            offset, accessToken);
                    code = response.getResult().getData().getCode();
                } catch (Exception e) {
                    accessToken = service.getNewToken("tanmaydatta", false).getAccessToken();
                    continue;
                }
                reqCount++;
                if (code == 9001) {
                    response.getResult().getData().getContent().stream().forEach(content -> {
                        System.out.println("Adding " + content.getProblemCode());
                        service.addProblem(new Problem(content.getProblemName(),
                                content.getProblemCode(), content.getSubmissions(), category));
                    });
                    offset += 100;
                }
            }
        }
    }

    @Test
    public void testSendNotification() {
        User user = service.getUserById("jalebi");
//        String fcmToken = "ekdgPgM9bW8:APA91bHLKccYwBOgzYnMoA6AzZUCL9kcilbGGEtFgIB_pYXuQWqoY_Ap9Al01V8uG5Qx8wRMoboybgX28epZHiMuy3Iv9oBl_uQg_s6fAy4Avtz_VjNq9lvdkKBgOn6-HkgBC1XaNjf9";
        String fcmToken = user.getFcmToken();
        Notification notification = new Notification("Title new", "Body new");
        Message message = Message.builder()
                .setAndroidConfig(AndroidConfig.builder().setPriority(AndroidConfig.Priority.HIGH).build())
                .setNotification(notification)
                .setToken(fcmToken)
                .putData("data key", "data value")
                .build();
        try {
            firebase.send(message);
        } catch (Exception e) {
            //TODO: log
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testSendMessage() {
        MessageApiModel messageApiModel = new MessageApiModel();
        messageApiModel.setMessage("test msg notification2");
        service.sendMessage(service.getAccessToken("tanmaydatta"), "jalebi", messageApiModel);
    }

    @Test
    public void testGetMessageList() {
        MessageList messageList = service.getMessageList(service.getAccessToken("jalebi"));
        System.out.println(messageList);
    }

    @Test
    public void testRatingsOfFriends() {
        List<UserRating> ratings = service.getRatingsOfFriends(service.getUserById("tanmaydatta"));
        assert(ratings != null && ratings.size() > 0);
    }
}