package codechef.utils;

import codechef.api.models.CommentApiModelResponse;
import codechef.api.models.FeedApiModelResponse;
import codechef.api.models.MessageApiModel;
import codechef.api.models.MessageApiModelResponse;
import codechef.api.models.PostApiModelResponse;
import codechef.api.models.ReplyApiModelResponse;
import codechef.api.models.UserApiModelResponse;
import codechef.models.Comment;
import codechef.models.Feed;
import codechef.models.Messages;
import codechef.models.Post;
import codechef.models.Reply;
import codechef.models.User;
import codechef.models.Votes;

import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class ModelToApiModel {

    public static UserApiModelResponse toUserApiModel(User user) {
        UserApiModelResponse response = new UserApiModelResponse();
        if (user == null) {
            return response;
        }
        response.setUserName(user.getUserName());
        response.setFullName(user.getFullName());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
//        if (user.getPosts() != null) {
//            response.setPosts(user.getPosts().stream()
//                    .map(ModelToApiModel::toPostApiModel).collect(Collectors.toSet()));
//        }
        return response;
    }

    public static PostApiModelResponse toPostApiModel(Post post) {
        PostApiModelResponse response = new PostApiModelResponse();
        if (post == null) {
            return response;
        }
        response.setId(post.getId());
        response.setPost(post.getPost());
        response.setUserName(post.getUser().getUserName());
        response.setCreatedAt(post.getCreatedAt());
        response.setUpdatedAt(post.getUpdatedAt());
        long upVotes = 0;
        long downVotes = 0;
        if (post.getVotes() != null) {
            for (Votes v : post.getVotes()) {
                long x = v.isUpOrDown() ? upVotes++ : downVotes++;
            }
            response.setUpVotes(upVotes);
            response.setDownVotes(downVotes);
        }
        if (post.getComments() != null) {
            response.setComments(post.getComments().stream()
                    .map(ModelToApiModel::toCommentApiModel).collect(Collectors.toSet()));
        }
        return response;
    }

    public static CommentApiModelResponse toCommentApiModel(Comment comment) {
        CommentApiModelResponse response = new CommentApiModelResponse();
        if (comment == null) {
            return response;
        }
        response.setComment(comment.getComment());
        response.setId(comment.getId());
        response.setPostId(comment.getPost().getId());
        response.setUserName(comment.getUser().getUserName());
        response.setCreatedAt(comment.getCreatedAt());
        response.setUpdatedAt(comment.getUpdatedAt());
        long upVotes = 0;
        long downVotes = 0;
        if (comment.getVotes() != null) {
            for (Votes v : comment.getVotes()) {
                long x = v.isUpOrDown() ? upVotes++ : downVotes++;
            }
            response.setUpVotes(upVotes);
            response.setDownVotes(downVotes);
        }
        if (comment.getReplies() != null) {
            response.setReplies(comment.getReplies().stream()
                    .map(ModelToApiModel::toReplyApiModel).collect(Collectors.toSet()));
        }
        return response;
    }

    public static ReplyApiModelResponse toReplyApiModel(Reply reply) {
        ReplyApiModelResponse response = new ReplyApiModelResponse();
        if (reply == null) {
            return response;
        }
        response.setReply(reply.getReply());
        response.setCommentId(reply.getComment().getId());
        response.setPostId(reply.getComment().getPost().getId());
        response.setId(reply.getId());
        response.setUserName(reply.getUser().getUserName());
        response.setCreatedAt(reply.getCreatedAt());
        response.setUpdatedAt(reply.getUpdatedAt());
        long upVotes = 0;
        long downVotes = 0;
        if (reply.getVotes() != null) {
            for (Votes v : reply.getVotes()) {
                long x = v.isUpOrDown() ? upVotes++ : downVotes++;
            }
            response.setUpVotes(upVotes);
            response.setDownVotes(downVotes);
        }
        return response;
    }

    public static FeedApiModelResponse toFeedApiModel(Feed feed) {
        FeedApiModelResponse response = new FeedApiModelResponse();
        if (feed == null) {
            return response;
        }
        if (feed.getActionUser() != null) {
            response.setActionUser(feed.getActionUser());
        }
        response.setContestCode(feed.getContestCode());
        response.setContestName(feed.getContestName());
        response.setId(feed.getId());
        response.setCreatedAt(feed.getCreatedAt());
        response.setUpdatedAt(feed.getUpdatedAt());
//        response.setPost(toPostApiModel(feed.getPost()));
        response.setProblemCode(feed.getProblemCode());
        response.setProblemName(feed.getProblemName());
        response.setType(feed.getType());
        response.setSubmissionId(feed.getSubmissionId());
        response.setUser(feed.getUser().getUserName());
        response.setFeedDate(feed.getFeedDate());
        response.setSubmissionResult(feed.getSubmissionResult());
        return response;
    }

    public static MessageApiModelResponse toMessageApiModel(Messages msg) {
        MessageApiModelResponse response = new MessageApiModelResponse();
        if (msg == null) {
            return null;
        }
        response.setId(msg.getId());
        response.setCreatedAt(msg.getCreatedAt());
        response.setFromUser(msg.getFromUser().getUserName());
        response.setToUser(msg.getToUser().getUserName());
        response.setMessage(msg.getMessage());
        return response;
    }

//    public

}
