package codechef;

import codechef.api.models.CommentApiModel;
import codechef.api.models.FcmTokenModel;
import codechef.api.models.FeedApiModelResponse;
import codechef.api.models.MessageApiModel;
import codechef.api.models.MessageApiModelResponse;
import codechef.api.models.MessageList;
import codechef.api.models.PostApiModel;
import codechef.api.models.ProblemApiResponse;
import codechef.api.models.ReplyApiModel;
import codechef.api.models.TokenResponse;
import codechef.CodechefServiceImpl;
import codechef.models.Comment;
import codechef.models.ContestStatus;
import codechef.models.Contests;
import codechef.models.Feed;
import codechef.models.Friend;
import codechef.models.Messages;
import codechef.models.Post;
import codechef.models.Problem;
import codechef.models.Rankings;
import codechef.models.Reply;
import codechef.models.User;
import codechef.models.UserRating;
import com.google.inject.ImplementedBy;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

@ImplementedBy(CodechefServiceImpl.class)
public interface CodechefService {

    TokenResponse getNewToken(@NotNull String token, boolean overwriteAppToken);

    String getAccessToken(@NotNull String userId);

    Post addPost(@NotNull PostApiModel post, User user);

    User getUserById(@NotNull String userId);

    boolean addProblem(@NotNull Problem problem);

    Post getPostById(@NotNull Long id);

    Post updatePost(@NotNull Long postId, PostApiModel post);

    User getUserByToken(@NotNull String token);

    User getUserByAppToken(@NotNull String token);

//    Set<Post> getAllPosts(@NotNull String userId);

//    boolean deletePost(@NotNull Long postId);

    boolean upVotePost(@NotNull Long postId, @NotNull String userName);

    boolean downVotePost(@NotNull Long postId, @NotNull String userName);

    Set<Comment> getAllComments(Long postId);

    Comment addComment(@NotNull Long postId, @NotNull CommentApiModel comment, @NotNull User user);

    Comment getComment(@NotNull Long commentId);

    Comment updateComment(@NotNull Long commentId, @NotNull CommentApiModel comment);

    boolean deleteComment(@NotNull Long commentId);

    boolean upVoteComment(@NotNull Long commentId, @NotNull String userName);

    boolean downVoteComment(@NotNull Long commentId, @NotNull String userName);

    Set<Reply> getAllReplies(@NotNull Long commentId);

    Reply getReply(@NotNull Long replyId);

    Reply addReply(@NotNull Long commentId, @NotNull ReplyApiModel reply, @NotNull User user);

    Reply updateReply(@NotNull Long replyId, @NotNull ReplyApiModel reply);

    boolean deleteReply(@NotNull Long replyId);

    boolean upVoteReply(@NotNull Long replyId, @NotNull String userName);

    boolean downVoteReply(@NotNull Long replyId, @NotNull String userName);

    ProblemApiResponse searchProblems(String query);

    boolean registerUser(String userId, TokenResponse token);

    List<FeedApiModelResponse> getUserFeed(String token, Long updatedAt);

    List<FeedApiModelResponse> getUserNotifications(String token, Long updatedAt);

    List<MessageApiModelResponse> getMessages(String token, Long updatedAt, String to);

    Messages sendMessage(String token, String to, MessageApiModel msg);

    void addContest(Contests contest);

    List<Contests> getAllContests();

    List<User> getAllUsers();

    List<Contests> getContestsByStatus(ContestStatus status);

    void addRank(Rankings rank);

    void addFriends(Friend friend);

    boolean setFcmToken(FcmTokenModel token, String accessToken);

    List<Friend> getAllFriends();

    boolean isPresentContest(String code);

    void addFeed(Feed feed);

    boolean isSubmissionFeed(User user, String actionUser, Long submissionId);

    boolean isContestFeed(User user, String contestCode);

    void addUserRating(UserRating rating);

    List<UserRating> getRatingsOfFriends(User user);

    List<String> searchUsers(String query);

    MessageList getMessageList(String token);

}
