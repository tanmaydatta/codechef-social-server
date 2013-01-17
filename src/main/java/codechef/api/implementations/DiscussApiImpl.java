package codechef.api.implementations;

import codechef.CodechefService;
import codechef.api.interfaces.DiscussApi;
import codechef.api.models.ApiResponse;
import codechef.api.models.CommentApiModel;
import codechef.api.models.CommentApiModelResponse;
import codechef.api.models.PostApiModel;
import codechef.api.models.PostApiModelResponse;
import codechef.api.models.ReplyApiModel;
import codechef.api.models.ReplyApiModelResponse;
import codechef.models.Comment;
import codechef.models.Post;
import codechef.models.Reply;
import codechef.models.User;
import codechef.utils.ModelToApiModel;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.jetbrains.annotations.NotNull;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import java.util.Set;
import java.util.stream.Collectors;

import static codechef.utils.ModelToApiModel.toCommentApiModel;
import static codechef.utils.ModelToApiModel.toPostApiModel;
import static codechef.utils.ModelToApiModel.toReplyApiModel;

@Path("discuss")
@Singleton
public class DiscussApiImpl implements DiscussApi  {

    @Inject
    CodechefService service;

    @Override
    @GET
    @Path("post/{postId}/get")
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResponse getPost(@PathParam("postId") long postId) {
        ApiResponse<PostApiModelResponse> response = new ApiResponse<>(200);
        Post post = service.getPostById(postId);
        if (post == null) {
            response.setStatus(500);
            return response;
        }
        response.setResult(toPostApiModel(post));
        return response;
    }

    @Override
    @POST
    @Path("post/add")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ApiResponse addPost(PostApiModel post, @NotNull @HeaderParam("token") String token) {
        ApiResponse<PostApiModelResponse> response = new ApiResponse<>(200);
        User user = service.getUserByToken(token);
        Post addedPost = service.addPost(post, user);
        if (addedPost != null) {
            response.setResult(toPostApiModel(addedPost));
            return response;
        }
        response.setStatus(500);
        return response;
    }

    @Override
    @POST
    @Path("post/{postId}/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ApiResponse updatePost(@PathParam("postId") Long postId, PostApiModel post,
                                  @NotNull @HeaderParam("token") String token) {
        ApiResponse<PostApiModelResponse> response = new ApiResponse<>(200);
        User user = service.getUserByToken(token);
        Post oldPost = service.getPostById(postId);
        if (user == null || oldPost == null) {
            response.setStatus(500);
            return response;
        }
        if (!user.getUserName().equals(oldPost.getUser().getUserName())) {
            response.setStatus(500);
            return response;
        }
        Post addedPost = service.updatePost(postId, post);
        if (addedPost != null) {
            response.setResult(toPostApiModel(addedPost));
            return response;
        }
        response.setStatus(500);
        return response;
    }


//    @Override
//    @DELETE
//    @Path("post/{postId}/delete")
//    @Produces(MediaType.APPLICATION_JSON)
//    public ApiResponse deletePost(@PathParam("postId") Long postId,  @NotNull @HeaderParam("token") String token) {
//        ApiResponse<String> response = new ApiResponse<>(200);
//        User user = service.getUserByToken(token);
//        Post oldPost = service.getPostById(postId);
//        if (user == null || oldPost == null) {
//            response.setStatus(500);
//            return response;
//        }
//        if (!user.getUserName().equals(oldPost.getUser().getUserName())) {
//            response.setStatus(500);
//            return response;
//        }
//        if(service.deletePost(postId)) {
//            response.setResult("success");
//            return response;
//        }
//        response.setStatus(500);
//        return response;
//    }

    @Override
    @POST
    @Path("post/{postId}/upvote")
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResponse upVotePost(@PathParam("postId") Long postId, @NotNull @HeaderParam("token") String token) {
        ApiResponse<String> response = new ApiResponse<>(200);
        User user = service.getUserByToken(token);
        if (service.upVotePost(postId, user.getUserName())) {
            response.setResult("scuccess");
        } else {
            response.setStatus(500);
        }
        return response;
    }

    @Override
    @POST
    @Path("post/{postId}/downvote")
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResponse downVotePost(@PathParam("postId") Long postId,  @NotNull @HeaderParam("token") String token) {
        ApiResponse<String> response = new ApiResponse<>(200);
        User user = service.getUserByToken(token);
        if (service.downVotePost(postId, user.getUserName())) {
            response.setResult("scuccess");
        } else {
            response.setStatus(500);
        }
        return response;
    }

    @Override
    @GET
    @Path("post/{postId}/comments")
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResponse getAllComments(@PathParam("postId") Long postId) {
        Set<Comment> comments = service.getAllComments(postId);
        ApiResponse<Set<CommentApiModelResponse>> response = new ApiResponse<>(200);
        if (comments == null) {
            response.setStatus(500);
            return response;
        }
        response.setResult(comments.stream().map(ModelToApiModel::toCommentApiModel).collect(Collectors.toSet()));
        return response;
    }

    @Override
    @POST
    @Path("post/{postId}/comments/add")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ApiResponse addComment(@PathParam("postId") Long postId,
                                  @NotNull CommentApiModel comment,  @NotNull @HeaderParam("token") String token) {
        ApiResponse<CommentApiModelResponse> response = new ApiResponse<>(200);
        User user = service.getUserByToken(token);
        Comment addedComment = service.addComment(postId, comment, user);
        if (addedComment != null) {
            response.setResult(toCommentApiModel(addedComment));
            return response;
        }
        response.setStatus(500);
        return response;
    }

    @Override
    @GET
    @Path("comment/{commentId}/get")
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResponse getComment(@PathParam("commentId") Long commentId) {
        ApiResponse<CommentApiModelResponse> response = new ApiResponse<>(200);
        Comment comment = service.getComment(commentId);
        if (comment == null) {
            response.setStatus(500);
            return response;
        }
        response.setResult(toCommentApiModel(comment));
        return response;
    }

    @Override
    @POST
    @Path("/comment/{commentId}/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ApiResponse updateComment(@PathParam("commentId") Long commentId,
                                     @NotNull CommentApiModel comment,  @NotNull @HeaderParam("token") String token) {
        ApiResponse<CommentApiModelResponse> response = new ApiResponse<>(200);
        User user = service.getUserByToken(token);
        Comment oldComment = service.getComment(commentId);
        if (user == null || oldComment == null) {
            response.setStatus(500);
            return response;
        }
        if (!user.getUserName().equals(oldComment.getUser().getUserName())) {
            response.setStatus(500);
            return response;
        }
        Comment addedComment = service.updateComment(commentId, comment);
        if (addedComment != null) {
            response.setResult(toCommentApiModel(addedComment));
            return response;
        }
        response.setStatus(500);
        return response;
    }

    @Override
    @DELETE
    @Path("comment/{commentId}/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResponse deleteComment(@PathParam("commentId") Long commentId,
                                     @NotNull @HeaderParam("token") String token) {
        ApiResponse<String> response = new ApiResponse<>(200);
        User user = service.getUserByToken(token);
        Comment oldComment = service.getComment(commentId);
        if (user == null || oldComment == null) {
            response.setStatus(500);
            return response;
        }
        if (!user.getUserName().equals(oldComment.getUser().getUserName())) {
            response.setStatus(500);
            return response;
        }
        if(service.deleteComment(commentId)) {
            response.setResult("success");
            return response;
        }
        response.setStatus(500);
        return response;
    }

    @Override
    @POST
    @Path("comment/{commentId}/upvote")
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResponse upVoteComment(@PathParam("commentId") Long commentId,  @NotNull @HeaderParam("token") String token) {
        ApiResponse<String> response = new ApiResponse<>(200);
        User user = service.getUserByToken(token);
        if (service.upVoteComment(commentId, user.getUserName())) {
            response.setResult("scuccess");
        } else {
            response.setStatus(500);
        }
        return response;
    }

    @Override
    @POST
    @Path("comment/{commentId}/downvote")
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResponse downVoteComment(@PathParam("commentId") Long commentId,  @NotNull @HeaderParam("token") String token) {
        ApiResponse<String> response = new ApiResponse<>(200);
        User user = service.getUserByToken(token);
        if (service.downVoteComment(commentId, user.getUserName())) {
            response.setResult("scuccess");
        } else {
            response.setStatus(500);
        }
        return response;
    }

    @Override
    @GET
    @Path("comment/{commentId}/replies")
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResponse getAllReplies(@PathParam("commentId") Long commentId) {
        Set<Reply> replies = service.getAllReplies(commentId);
        ApiResponse<Set<ReplyApiModelResponse>> response = new ApiResponse<>(200);
        if (replies == null) {
            response.setStatus(500);
            return response;
        }
        response.setResult(replies.stream().map(ModelToApiModel::toReplyApiModel).collect(Collectors.toSet()));
        return response;
    }

    @Override
    @GET
    @Path("reply/{replyId}/get")
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResponse getReply(@PathParam("replyId") Long replyId) {
        ApiResponse<ReplyApiModelResponse> response = new ApiResponse<>(200);
        Reply reply = service.getReply(replyId);
        if (reply == null) {
            response.setStatus(500);
            return response;
        }
        response.setResult(toReplyApiModel(reply));
        return response;
    }

    @Override
    @POST
    @Path("comment/{commentId}/reply/add")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ApiResponse addReply(@PathParam("commentId") Long commentId,
                                @NotNull ReplyApiModel reply, @NotNull @HeaderParam("token") String token) {
        ApiResponse<ReplyApiModelResponse> response = new ApiResponse<>(200);
        User user = service.getUserByToken(token);
        Reply addedReply = service.addReply(commentId, reply, user);
        if (addedReply != null) {
            response.setResult(toReplyApiModel(addedReply));
            return response;
        }
        response.setStatus(500);
        return response;
    }

    @Override
    @POST
    @Path("reply/{replyId}/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ApiResponse updateReply(@PathParam("replyId") Long replyId,
                                   @NotNull ReplyApiModel reply,  @NotNull @HeaderParam("token") String token) {
        ApiResponse<ReplyApiModelResponse> response = new ApiResponse<>(200);
        User user = service.getUserByToken(token);
        Reply oldReply = service.getReply(replyId);
        if (user == null || oldReply == null) {
            response.setStatus(500);
            return response;
        }
        if (!user.getUserName().equals(oldReply.getUser().getUserName())) {
            response.setStatus(500);
            return response;
        }
        Reply addedReply = service.updateReply(replyId, reply);
        if (addedReply != null) {
            response.setResult(toReplyApiModel(addedReply));
            return response;
        }
        response.setStatus(500);
        return response;
    }

    @Override
    @DELETE
    @Path("reply/{replyId}/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResponse deleteReply(@PathParam("replyId") Long replyId,  @NotNull @HeaderParam("token") String token) {
        ApiResponse<String> response = new ApiResponse<>(200);
        User user = service.getUserByToken(token);
        Reply oldReply = service.getReply(replyId);
        if (user == null || oldReply == null) {
            response.setStatus(500);
            return response;
        }
        if (!user.getUserName().equals(oldReply.getUser().getUserName())) {
            response.setStatus(500);
            return response;
        }
        if(service.deleteReply(replyId)) {
            response.setResult("success");
            return response;
        }
        response.setStatus(500);
        return response;
    }

    @Override
    @POST
    @Path("reply/{replyId}/upvote")
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResponse upVoteReply(@PathParam("replyId") Long replyId,  @NotNull @HeaderParam("token") String token) {
        ApiResponse<String> response = new ApiResponse<>(200);
        User user = service.getUserByToken(token);
        if (service.upVoteReply(replyId, user.getUserName())) {
            response.setResult("scuccess");
        } else {
            response.setStatus(500);
        }
        return response;
    }

    @Override
    @POST
    @Path("reply/{replyId}/downvote")
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResponse downVoteReply(@PathParam("replyId") Long replyId, @NotNull @HeaderParam("token") String token) {
        ApiResponse<String> response = new ApiResponse<>(200);
        User user = service.getUserByToken(token);
        if (service.downVoteReply(replyId, user.getUserName())) {
            response.setResult("scuccess");
        } else {
            response.setStatus(500);
        }
        return response;
    }
}
