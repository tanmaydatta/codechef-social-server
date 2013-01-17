package codechef;

import codechef.api.CodechefApiClient;
import codechef.api.models.CodechefTokenResponse;
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
import codechef.dao.interfaces.CodechefProblemDao;
import codechef.dao.interfaces.CodechefUserDao;
import codechef.dao.interfaces.DiscussDao;
import codechef.job.interfaces.Notifier;
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
import codechef.utils.ModelToApiModel;
import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CodechefServiceImpl implements CodechefService {

    @Inject
    CodechefUserDao userDao;

    @Inject
    DiscussDao discussDao;

    @Inject
    CodechefProblemDao problemDao;

    @Inject
    CodechefApiClient apiClient;

    @Inject
    Notifier notifier;

    @Override
    public TokenResponse getNewToken(String token, boolean overwriteAppToken) {
        TokenResponse response = new TokenResponse();
        User user;
        try {
            user = userDao.getUserByToken(token);
        } catch (Exception e) {
            //TODO: log
            System.out.println(e.getMessage());
            return response;
        }
        String refreshToken = user.getRefreshToken();
        CodechefTokenResponse tokenResponse = apiClient.getToken(refreshToken);
        try {
            response.setAccessToken(tokenResponse.getResult().getData().getAccessToken());
            response.setRefreshToken(tokenResponse.getResult().getData().getRefreshToken());
            user.setRefreshToken(tokenResponse.getResult().getData().getRefreshToken());
            user.setAccessToken(tokenResponse.getResult().getData().getAccessToken());
            if (overwriteAppToken) {
                user.setAppToken(tokenResponse.getResult().getData().getAccessToken());
            }
            System.out.println(tokenResponse.getResult().getData().getRefreshToken());
        } catch (Exception e) {
            //TODO: log
            System.out.println("Exception in getting token: " + e.getMessage());
        }
        boolean done = false;
        int retries = 5;
        while (!done && retries > 0) {
            try {
                userDao.saveOrUpdateUser(user);
                done = true;
            } catch (Exception e) {
                retries--;
                //TODO: log
                System.out.println("Exception in saving user after getting token " + e.getMessage());
                try {
                    Thread.sleep(TimeUnit.SECONDS.toMillis(10));
                } catch (Exception e1) {
                    System.out.println("could not sleep thread after exception in get new token");
                    e1.printStackTrace();
                }
            }
        }
        return response;
    }

    @Override
    public String getAccessToken(@NotNull String userId) {
        User user;
        try {
            user = userDao.getUser(userId);
        } catch (Exception e) {
            //TODO: log
            return null;
        }
        return user.getAccessToken();
    }

    @Override
    public Post addPost(@NotNull PostApiModel post, User user) {
        try {
            if (user == null) {
                return null;
            }
            Post newPost = new Post();
            newPost.setUser(user);
            newPost.setPost(post.getPost());
            discussDao.addPost(newPost);
            return newPost;
        } catch (Exception e) {
            //TODO: log
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public User getUserById(@NotNull String userId) {
        User user;
        try {
            user = userDao.getUser(userId);

        } catch (Exception e) {
            //TODO: log
            return null;
        }
        return user;
    }

    @Override
    public boolean addProblem(@NotNull Problem problem) {
        try {
            problemDao.addProblem(problem);
            return true;
        } catch (Exception e) {
            //TODO: log
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Post getPostById(@NotNull Long id) {
        try {
            Post post = discussDao.getPost(id);
            return post;
        } catch (Exception e) {
            //TODO: log
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Post updatePost(@NotNull Long postId, PostApiModel post) {
        try {
            Post getPost = getPostById(postId);
            if (getPost == null) {
                return null;
            }
            User user = getPost.getUser();
            getPost.setPost(post.getPost());
            discussDao.updatePost(getPost);
            return getPost;
        } catch (Exception e) {
            //TODO: log
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public User getUserByToken(@NotNull String token) {
        try {
            User user = userDao.getUserByToken(token);
            return user;
        } catch (Exception e) {
            //TODO: log
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public User getUserByAppToken(@NotNull String token) {
        try {
            User user = userDao.getUserByAppToken(token);
            return user;
        } catch (Exception e) {
            //TODO: log
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

//    @Override
//    public Set<Post> getAllPosts(@NotNull String userId) {
//        try {
//            return discussDao.getAllPosts(userId);
//        } catch (Exception e) {
//            //TODO: log
//            return new HashSet<>();
//        }
//    }
//
//    @Override
//    public boolean deletePost(@NotNull Long postId) {
//        try {
//            discussDao.deletePost(postId);
//            return true;
//        } catch (Exception e) {
//            //TODO: log
//            return false;
//        }
//    }

    @Override
    public boolean upVotePost(@NotNull Long postId, @NotNull String userName) {
        try {
            discussDao.upVotePost(postId, userName);
            return true;
        } catch (Exception e) {
            //TODO: log
            return false;
        }
    }

    @Override
    public boolean downVotePost(@NotNull Long postId, @NotNull String userName) {
        try {
            discussDao.downVotePost(postId, userName);
            return true;
        } catch (Exception e) {
            //TODO: log
            return false;
        }
    }

    @Override
    public Set<Comment> getAllComments(Long postId) {
        try {
            return discussDao.getAllComments(postId);
        } catch (Exception e) {
            //TODO: log
            return new HashSet<>();
        }
    }

    @Override
    public Comment addComment(@NotNull Long postId, @NotNull CommentApiModel comment, User user) {
        try {
            Post post = getPostById(postId);
            if (post == null) {
                //TODO: log
                return null;
            }
            Comment newComment = new Comment();
            newComment.setPost(post);
            newComment.setUser(user);
            newComment.setComment(comment.getComment());
            discussDao.addComment(newComment);
            return newComment;
        } catch (Exception e) {
            //TODO: log
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Comment getComment(@NotNull Long commentId) {
        try {
            return discussDao.getComment(commentId);
        } catch (Exception e) {
            //TODO: log
            return null;
        }
    }

    @Override
    public Comment updateComment(@NotNull Long commentId, @NotNull CommentApiModel comment) {
        try {
            Comment newComment = discussDao.getComment(commentId);
            newComment.setComment(comment.getComment());
            discussDao.updateComment(newComment);
            return newComment;
        } catch (Exception e) {
            //TODO: log
            return null;
        }
    }

    @Override
    public boolean deleteComment(@NotNull Long commentId) {
        try {
            discussDao.deleteComment(commentId);
            return true;
        } catch (Exception e) {
            //TODO:log
            return false;
        }
    }

    @Override
    public boolean upVoteComment(@NotNull Long commentId, @NotNull String userName) {
        try {
            discussDao.upVoteComment(commentId, userName);
            return true;
        } catch (Exception e) {
            //TODO: log
            return false;
        }
    }

    @Override
    public boolean downVoteComment(@NotNull Long commentId, @NotNull String userName) {
        try {
            discussDao.downVoteComment(commentId, userName);
            return true;
        } catch (Exception e) {
            //TODO: log
            return false;
        }
    }

    @Override
    public Set<Reply> getAllReplies(@NotNull Long commentId) {
        try {
            return discussDao.getAllReplies(commentId);
        } catch (Exception e) {
            //TODO: log
            return new HashSet<>();
        }
    }

    @Override
    public Reply getReply(@NotNull Long replyId) {
        try {
            return discussDao.getReply(replyId);
        } catch (Exception e) {
            //TODO: log
            return null;
        }
    }

    @Override
    public Reply addReply(@NotNull Long commentId, @NotNull ReplyApiModel reply, @NotNull User user) {
        try {
            Comment comment = getComment(commentId);
            if (comment == null) {
                //TODO: log
                return null;
            }
            Reply newReply = new Reply();
            newReply.setReply(reply.getReply());
            newReply.setUser(user);
            newReply.setComment(comment);
            discussDao.addReply(newReply);
            return newReply;
        } catch (Exception e) {
            //TODO: log
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Reply updateReply(@NotNull Long replyId, @NotNull ReplyApiModel reply) {
        try {
            Reply newReply = discussDao.getReply(replyId);
            newReply.setReply(reply.getReply());
            discussDao.updateReply(newReply);
            return newReply;
        } catch (Exception e) {
            //TODO: log
            return null;
        }
    }

    @Override
    public boolean deleteReply(@NotNull Long replyId) {
        try {
            discussDao.deleteReply(replyId);
            return true;
        } catch (Exception e) {
            //TODO:log
            return false;
        }
    }

    @Override
    public boolean upVoteReply(@NotNull Long replyId, @NotNull String userName) {
        try {
            discussDao.upVoteReply(replyId, userName);
            return true;
        } catch (Exception e) {
            //TODO: log
            return false;
        }
    }

    @Override
    public boolean downVoteReply(@NotNull Long replyId, @NotNull String userName) {
        try {
            discussDao.downVoteReply(replyId, userName);
            return true;
        } catch (Exception e) {
            //TODO: log
            return false;
        }
    }

    @Override
    public ProblemApiResponse searchProblems(String query) {
        ProblemApiResponse response = new ProblemApiResponse();
        try {
            List<Problem> problems = problemDao.searchProblems(query);
            problems = problems.subList(0, Math.min(10, problems.size()));
            response.setProblems(problems.stream().map(p -> new codechef.api.models.Problem(
                    p.getProblemName(), p.getProblemCode())).collect(Collectors.toSet()));
            return response;
        } catch (Exception e) {
            //TODO: log
            return response;
        }
    }

    @Override
    public boolean registerUser(String userId, TokenResponse token) {
        try {
            String fullname = apiClient.getFullName(userId, token.getAccessToken());
            if (fullname == null) {
                return false;
            }
            User user = new User();
            user.setUserName(userId);
            user.setAccessToken(token.getAccessToken());
            user.setAppToken(token.getAccessToken());
            user.setRefreshToken(token.getRefreshToken());
            user.setFullName(fullname);
            userDao.saveOrUpdateUser(user);
            return true;
        } catch (Exception e) {
            //TODO: log
            return false;
        }
    }

    @Override
    public List<FeedApiModelResponse> getUserFeed(String token, Long updatedAt) {
        try {
            User user = userDao.getUserByToken(token);
            List<Feed> feed = userDao.getUserFeed(user, new Date(updatedAt));
            return feed.stream().map(ModelToApiModel::toFeedApiModel).collect(Collectors.toList());
        } catch (Exception e) {
            //TODO: log
            return new ArrayList<>();
        }
    }

    @Override
    public List<FeedApiModelResponse> getUserNotifications(String token, Long updatedAt) {
        try {
            User user = userDao.getUserByToken(token);
            List<Feed> feed = userDao.getUserNotifications(user, new Date(updatedAt));
            return feed.stream().map(ModelToApiModel::toFeedApiModel).collect(Collectors.toList());
        } catch (Exception e) {
            //TODO: log
            return new ArrayList<>();
        }
    }

    @Override
    public List<MessageApiModelResponse> getMessages(String token, Long updatedAt, String to) {
        try {
            User fromUser = userDao.getUserByToken(token);
            User toUser = userDao.getUser(to);
            List<Messages> messages = userDao.getUserMessages(fromUser, toUser, new Date(updatedAt));
            messages.sort((o1, o2) -> -Long.compare(o1.getCreatedAt().getTime(), o2.getCreatedAt().getTime()));
            return messages.stream().map(ModelToApiModel::toMessageApiModel).collect(Collectors.toList());
        } catch (Exception e) {
            //TODO: log
            return new ArrayList<>();
        }
    }

    @Override
    public Messages sendMessage(String token, String to, MessageApiModel msg) {
        try {
            User fromUser = userDao.getUserByToken(token);
            User toUser = userDao.getUser(to);
            Messages message = userDao.sendMessage(fromUser, toUser, msg.getMessage());
            new Thread(() -> notifier.sendMessage(message)).start();
            return message;
        } catch (Exception e) {
            //TODO: log
            return null;
        }
    }

    @Override
    public void addContest(Contests contest) {
        try {
            problemDao.addContest(contest);
        } catch (Exception e) {
            //TODO: log
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public List<Contests> getAllContests() {
        try {
            return problemDao.getAllContests();
        } catch (Exception e) {
            //TODO: log
            return new ArrayList<>();
        }
    }

    @Override
    public void addRank(Rankings rank) {
        try {
            problemDao.addRank(rank);
        } catch (Exception e) {
            //TODO: log
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public List<Contests> getContestsByStatus(ContestStatus status) {
        try {
            return problemDao.getContestsByStatus(status);
        } catch (Exception e) {
            //TODO: log
            return new ArrayList<>();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try {
            return userDao.getAllUsers();
        } catch (Exception e) {
            //TODO: log
            return new ArrayList<>();
        }
    }

    @Override
    public void addFriends(Friend friend) {
        try {
            userDao.addFriends(friend);
        } catch (Exception e) {
            //TODO: log
        }
    }

    @Override
    public boolean setFcmToken(FcmTokenModel token, String accessToken) {
        try {
            User user = userDao.getUserByToken(accessToken);
            user.setFcmToken(token.getToken());
            userDao.saveOrUpdateUser(user);
            return true;
        } catch (Exception e) {
            //TODO: log
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public List<Friend> getAllFriends() {
        try {
            return userDao.getAllFriends();
        } catch (Exception e) {
            //TODO: log
            return new ArrayList<>();
        }
    }

    @Override
    public boolean isPresentContest(String code) {
        try {
            return problemDao.isPresentContest(code);
        } catch (Exception e) {
            //TODO: log
            return false;
        }
    }

    @Override
    public void addFeed(Feed feed) {
        try {
            userDao.addFeed(feed);
        } catch (Exception e) {
            //TODO:log
        }
    }

    @Override
    public boolean isSubmissionFeed(User user, String actionUser, Long submissionId) {
        try {
            return userDao.isSubmissionFeed(user, actionUser, submissionId);
        } catch (Exception e) {
            //TODO: log
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean isContestFeed(User user, String contestCode) {
        try {
            return problemDao.isContestNotification(user, contestCode);
        } catch (Exception e) {
            //TODO: log
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public void addUserRating(UserRating rating) {
        try {
            userDao.addUserRating(rating);
        } catch (Exception e) {
            //TODO:log
        }
    }

    @Override
    public List<UserRating> getRatingsOfFriends(User user) {
        try {
            return userDao.getRatingsOfFriends(user);
        } catch (Exception e) {
            //TODO: log
            return new ArrayList<>();
        }
    }

    @Override
    public List<String> searchUsers(String query) {
        try {
            return userDao.searchUsers(query).stream().map(User::getUserName).collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println("could not search users");
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public MessageList getMessageList(String token) {
        try {
            MessageList response = new MessageList();
            User user = getUserByAppToken(token);
            List<Object[]> messages = userDao.getMessageList(user);
            Map<String, Date> messageToDateMap = new HashMap<>();
            messages.forEach(m -> {
                String username = ((User) m[0]).getUserName()
                        .equals(user.getUserName()) ? ((User) m[1]).getUserName() : ((User) m[0]).getUserName();
                Date date = messageToDateMap.getOrDefault(username, null);
                if (date == null) {
                    messageToDateMap.put(username, ((Date) m[2]));
                } else {
                    messageToDateMap.put(username, date.getTime() > ((Date) m[2]).getTime() ? date : ((Date) m[2]));
                }
            });
            List<Map.Entry<String, Date>> entries = new ArrayList<>(messageToDateMap.entrySet());
            entries.sort((o1, o2) -> -Long.compare(o1.getValue().getTime(), o2.getValue().getTime()));
            response.setUsers(entries.stream().map(Map.Entry::getKey).collect(Collectors.toList()));
            return response;
        } catch (Exception e) {
            return new MessageList();
        }
    }
}
