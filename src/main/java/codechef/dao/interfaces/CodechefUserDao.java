package codechef.dao.interfaces;

import codechef.dao.exceptions.DaoException;
import codechef.dao.implementations.CodechefUserDaoImpl;
import codechef.models.Feed;
import codechef.models.Friend;
import codechef.models.Messages;
import codechef.models.User;
import codechef.models.UserRating;
import com.google.inject.ImplementedBy;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;

@ImplementedBy(CodechefUserDaoImpl.class)
public interface CodechefUserDao {

    User getUser(@NotNull String userId) throws DaoException;

    void saveOrUpdateUser(User user) throws DaoException;

    User getUserByToken(String token) throws DaoException;

    User getUserByAppToken(String token) throws DaoException;

    String getAdminToken(String userId) throws DaoException;

    List<Feed> getUserFeed(User user, Date lastUpdatedAtInSec) throws DaoException;

    List<Feed> getUserNotifications(User user, Date lastUpdatedAtInSec) throws DaoException;

    List<Messages> getUserMessages(User fromUser, User toUser, Date lastUpdatedAtInSec) throws DaoException;

    Messages sendMessage(User fromUser, User toUser, String message) throws DaoException;

    List<User> getAllUsers() throws DaoException;

    void addFriends(Friend friend) throws DaoException;

    boolean isSubmissionFeed(User user, String actionUser, Long submissionId) throws DaoException;

    List<Friend> getAllFriends() throws DaoException;

    void addFeed(Feed feed) throws DaoException;

    void addUserRating(UserRating rating) throws DaoException;

    List<UserRating> getRatingsOfFriends(User user) throws DaoException;

    List<User> searchUsers(String query) throws DaoException;

    List<Object[]> getMessageList(User user) throws DaoException;
}
