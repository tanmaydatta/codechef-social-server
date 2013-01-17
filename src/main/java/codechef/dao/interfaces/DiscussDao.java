package codechef.dao.interfaces;

import codechef.api.models.CommentApiModel;
import codechef.api.models.ReplyApiModel;
import codechef.dao.exceptions.DaoException;
import codechef.dao.implementations.DiscussDaoImpl;
import codechef.models.Comment;
import codechef.models.Post;
import codechef.models.Reply;
import com.google.inject.ImplementedBy;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

@ImplementedBy(DiscussDaoImpl.class)
public interface DiscussDao {

    void addPost(Post post) throws DaoException;

    Post getPost(Long postId) throws DaoException;

    void updatePost(Post post) throws DaoException;

//    Set<Post> getAllPosts(@NotNull String userId) throws DaoException;

//    void deletePost(@NotNull Long postId) throws DaoException;

    void upVotePost(@NotNull Long postId, @NotNull String userName) throws DaoException;

    void downVotePost(@NotNull Long postId, @NotNull String userName) throws DaoException;

    Set<Comment> getAllComments(Long postId) throws DaoException;

    void addComment(@NotNull Comment comment) throws DaoException;

    Comment getComment(@NotNull Long commentId) throws DaoException;

    void updateComment(@NotNull Comment comment) throws DaoException;

    void deleteComment(@NotNull Long commentId) throws DaoException;

    void upVoteComment(@NotNull Long commentId, @NotNull String userName) throws DaoException;

    void downVoteComment(@NotNull Long commentId, @NotNull String userName) throws DaoException;

    Set<Reply> getAllReplies(@NotNull Long commentId) throws DaoException;

    Reply getReply(@NotNull Long replyId) throws DaoException;

    void addReply(@NotNull Reply reply) throws DaoException;

    void updateReply(@NotNull Reply reply) throws DaoException;

    void deleteReply(@NotNull Long replyId) throws DaoException;

    void upVoteReply(@NotNull Long replyId, @NotNull String userName) throws DaoException;

    void downVoteReply(@NotNull Long replyId, @NotNull String userName) throws DaoException;

}
