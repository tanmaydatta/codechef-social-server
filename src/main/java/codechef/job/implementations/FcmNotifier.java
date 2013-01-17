package codechef.job.implementations;

import codechef.CodechefService;
import codechef.job.interfaces.Notifier;
import codechef.models.Messages;
import codechef.models.User;
import codechef.modules.MainConfig;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.inject.Inject;

import java.util.concurrent.TimeUnit;

public class FcmNotifier implements Notifier {


    @Inject
    FirebaseMessaging firebase;

    @Inject
    CodechefService service;

    @Inject
    MainConfig config;

    @Override
    public void notifyForPost() {

    }

    @Override
    public void notifyForSubmission(User user, String actionUser, String problemCode) {
        System.out.println("Notifying user: " +
                user.getUserName() + " for submission of " + problemCode + " by " + actionUser);
        String fcmToken = user.getFcmToken();
        Notification notification = new Notification("New Submission",
                String.format("%s submitted a solution for problem %s", actionUser, problemCode));
        Message noti = Message.builder()
                .setNotification(notification)
                .setAndroidConfig(AndroidConfig.builder().setPriority(AndroidConfig.Priority.HIGH).build())
                .setToken(fcmToken)
                .build();

        Message message = Message.builder()
                .putData("submission", String.format("%s submitted a solution for problem %s", actionUser, problemCode))
                .setAndroidConfig(AndroidConfig.builder().setPriority(AndroidConfig.Priority.HIGH).build())
                .setToken(fcmToken)
                .build();
        try {
            firebase.send(noti);
            firebase.send(message);
        } catch (Exception e) {
            //TODO: log
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void notifyForContest(User user, String contest) {
        System.out.println("Notifying user: " + user.getUserName() + " for contest " + contest);
        String fcmToken = user.getFcmToken();
        Notification notification = new Notification("Contest Reminder",
                String.format("%s is starting in less than 7 hours", contest));
        Message noti = Message.builder()
                .setNotification(notification)
                .setAndroidConfig(AndroidConfig.builder().setPriority(AndroidConfig.Priority.HIGH).build())
                .setToken(fcmToken)
                .build();
        Message message = Message.builder()
                .setAndroidConfig(AndroidConfig.builder().setPriority(AndroidConfig.Priority.HIGH).build())
                .putData("contest", String.format("%s is starting in less than %s hours",
                        contest, Long.toString(TimeUnit.MILLISECONDS.toHours(config.getCONTEST_REMIND_BUFFER()))))
                .setToken(fcmToken)
                .build();
        try {
            firebase.send(noti);
            firebase.send(message);
        } catch (Exception e) {
            //TODO: log
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void notifyForComment() {

    }

    @Override
    public void notifyForReply() {

    }

    @Override
    public void sendMessage(Messages message) {
        String fcmToken = message.getToUser().getFcmToken();
        System.out.println("Messaging user: " +
                message.getToUser().getUserName() + " from " + message.getFromUser().getUserName());
        Notification notification = new Notification("New Message",
                String.format("%s sent you a new message", message.getFromUser().getUserName()));
        Message fcmMessage = Message.builder()
                .setAndroidConfig(AndroidConfig.builder().setPriority(AndroidConfig.Priority.HIGH).build())
                .setNotification(notification)
                .setToken(fcmToken)
                .putData("message", message.getMessage())
                .putData("username", message.getFromUser().getUserName())
                .putData("name", message.getFromUser().getFullName())
                .build();
        try {
            firebase.send(fcmMessage);
        } catch (Exception e) {
            //TODO: log
            System.out.println(e.getMessage());
        }
    }
}
