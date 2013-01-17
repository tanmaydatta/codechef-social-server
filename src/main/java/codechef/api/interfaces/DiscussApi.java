package codechef.api.interfaces;

import codechef.api.models.ApiResponse;
import codechef.api.models.CommentApiModel;
import codechef.api.models.PostApiModel;
import codechef.api.models.ReplyApiModel;
import org.jetbrains.annotations.NotNull;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.PathParam;

public interface DiscussApi {

    ApiResponse getPost(@PathParam("postId") long postId);

    ApiResponse addPost(@NotNull PostApiModel post, @NotNull @HeaderParam("token") String token);

    ApiResponse updatePost(@PathParam("postId") Long postId,
                           @NotNull PostApiModel post, @NotNull @HeaderParam("token") String token);

//    ApiResponse deletePost(@PathParam("postId") Long postId,  @NotNull @HeaderParam("token") String token);

    ApiResponse upVotePost(@PathParam("postId") Long postId, @NotNull @HeaderParam("token") String token);

    ApiResponse downVotePost(@PathParam("postId") Long postId,  @NotNull @HeaderParam("token") String token);

    ApiResponse getAllComments(@PathParam("postId") Long postId);

    ApiResponse addComment(@PathParam("postId") Long postId,
                           @NotNull CommentApiModel comment,  @NotNull @HeaderParam("token") String token);

    ApiResponse getComment(@PathParam("commentId") Long commentId);

    ApiResponse updateComment(@PathParam("commentId") Long commentId,
                              @NotNull CommentApiModel comment,  @NotNull @HeaderParam("token") String token);

    ApiResponse deleteComment(@PathParam("commentId") Long commentId,  @NotNull @HeaderParam("token") String token);

    ApiResponse upVoteComment(@PathParam("commentId") Long commentId,   @NotNull @HeaderParam("token") String token);

    ApiResponse downVoteComment(@PathParam("commentId") Long commentId,  @NotNull @HeaderParam("token") String token);

    ApiResponse getAllReplies(@PathParam("commentId") Long commentId);

    ApiResponse getReply(@PathParam("replyId") Long replyId);

    ApiResponse addReply(@PathParam("commentId") Long commentId,
                         @NotNull ReplyApiModel reply, @NotNull @HeaderParam("token") String token);

    ApiResponse updateReply(@PathParam("replyId") Long replyId,
                            @NotNull ReplyApiModel reply,  @NotNull @HeaderParam("token") String token);

    ApiResponse deleteReply(@PathParam("replyId") Long replyId,  @NotNull @HeaderParam("token") String token);

    ApiResponse upVoteReply(@PathParam("replyId") Long replyId,  @NotNull @HeaderParam("token") String token);

    ApiResponse downVoteReply(@PathParam("replyId") Long replyId,   @NotNull @HeaderParam("token") String token);

}
