package codechef.job.interfaces;

import codechef.models.Messages;
import codechef.models.User;

public interface Notifier {

    void notifyForPost();

    void notifyForSubmission(User user, String actionUser, String problemCode);

    void notifyForContest(User user, String contest);

    void notifyForComment();

    void notifyForReply();

    void sendMessage(Messages message);
}
