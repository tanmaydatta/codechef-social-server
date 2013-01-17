package codechef.dao.implementations;

import codechef.api.models.CommentApiModel;
import codechef.api.models.ReplyApiModel;
import codechef.dao.exceptions.DaoException;
import codechef.dao.interfaces.CodechefUserDao;
import codechef.dao.interfaces.DiscussDao;
import codechef.models.Comment;
import codechef.models.Post;
import codechef.models.Reply;
import codechef.models.User;
import codechef.models.Votes;
import com.google.inject.Inject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public class DiscussDaoImpl implements DiscussDao {

    @Inject
    SessionFactory sessionFactory;

    @Inject
    CodechefUserDao userDao;

    @Override
    public void addPost(Post post) throws DaoException {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(post);
            session.getTransaction().commit();
        }
    }

    @Override
    public Post getPost(Long postId) throws DaoException {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery(String.format("from Post where id='%d'", postId));
            List<Post> posts = query.list();
            return posts.get(0);
        } catch (Exception e) {
            //TODO: log
            throw new DaoException("Could not get post with id: " + postId, e);
        }
    }

    @Override
    public void updatePost(Post post) throws DaoException {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(post);
            session.getTransaction().commit();
        }
    }

//    @Override
//    public Set<Post> getAllPosts(@NotNull String userId) throws DaoException {
//        try (Session session = sessionFactory.openSession()) {
//            User user = session.get(User.class, userId);
//            return user.getPosts();
//        } catch (Exception e) {
//            //TODO: log
//            throw new DaoException("Could not get user with id: " + userId, e);
//        }
//    }
//
//    @Override
//    public void deletePost(@NotNull Long postId) throws DaoException {
//        try (Session session = sessionFactory.openSession()) {
//            session.beginTransaction();
//            Post post = session.get(Post.class, postId);
//            User user = post.getUser();
//            user.getPosts().remove(post);
//            session.update(user);
//            session.getTransaction().commit();
//        } catch (Exception e) {
//            //TODO: log
//            throw new DaoException("Could not delete post with id: " + postId, e);
//        }
//    }

    @Override
    public void upVotePost(@NotNull Long postId, @NotNull String userName) throws DaoException {
        Votes vote = getVoteFromPostId(postId, userName);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            if (vote == null) {
                vote = new Votes();
                vote.setUser(userDao.getUser(userName));
                vote.setUpOrDown(true);
                vote.setPost(getPost(postId));
                session.persist(vote);
            } else {
                vote.setUpOrDown(true);
                session.update(vote);
            }
            session.getTransaction().commit();
        }
    }

    @Override
    public void downVotePost(@NotNull Long postId, @NotNull String userName) throws DaoException {
        Votes vote = getVoteFromPostId(postId, userName);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            if (vote == null) {
                vote = new Votes();
                vote.setUser(userDao.getUser(userName));
                vote.setUpOrDown(false);
                vote.setPost(getPost(postId));
                session.persist(vote);
            } else {
                vote.setUpOrDown(false);
                session.update(vote);
            }
            session.getTransaction().commit();
        }
    }

    @Override
    public Set<Comment> getAllComments(Long postId) throws DaoException {
        try (Session session = sessionFactory.openSession()) {
            Post post = session.get(Post.class, postId);
            return post.getComments();
        } catch (Exception e) {
            //TODO: log
            throw new DaoException("Could not get post with id: " + postId, e);
        }
    }

    @Override
    public void addComment(@NotNull Comment comment) throws DaoException {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(comment);
            session.getTransaction().commit();
        }
    }

    @Override
    public Comment getComment(@NotNull Long commentId) throws DaoException {
        try (Session session = sessionFactory.openSession()) {
            Comment comment = session.get(Comment.class, commentId);
            return comment;
        } catch (Exception e) {
            //TODO: log
            throw new DaoException("Could not get comment with id: " + commentId, e);
        }
    }

    @Override
    public void updateComment(@NotNull Comment comment) throws DaoException {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(comment);
            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteComment(@NotNull Long commentId) throws DaoException {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Comment comment = session.get(Comment.class, commentId);
            Post post = comment.getPost();
            post.getComments().remove(comment);
            session.update(post);
            session.getTransaction().commit();
        } catch (Exception e) {
            //TODO: log
            throw new DaoException("Could not delete comment with id: " + commentId, e);
        }
    }

    @Override
    public void upVoteComment(@NotNull Long commentId, @NotNull String userName) throws DaoException {
        Votes vote = getVoteFromCommentId(commentId, userName);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            if (vote == null) {
                vote = new Votes();
                vote.setUser(userDao.getUser(userName));
                vote.setUpOrDown(true);
                vote.setComment(getComment(commentId));
                session.persist(vote);
            } else {
                vote.setUpOrDown(false);
                session.update(vote);
            }
            session.getTransaction().commit();
        }
    }

    @Override
    public void downVoteComment(@NotNull Long commentId, @NotNull String userName) throws DaoException {
        Votes vote = getVoteFromCommentId(commentId, userName);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            if (vote == null) {
                vote = new Votes();
                vote.setUser(userDao.getUser(userName));
                vote.setUpOrDown(false);
                vote.setComment(getComment(commentId));
                session.persist(vote);
            } else {
                vote.setUpOrDown(false);
                session.update(vote);
            }
            session.getTransaction().commit();
        }
    }

    @Override
    public Set<Reply> getAllReplies(@NotNull Long commentId) throws DaoException {
        try (Session session = sessionFactory.openSession()) {
            Comment comment = session.get(Comment.class, commentId);
            return comment.getReplies();
        } catch (Exception e) {
            //TODO: log
            throw new DaoException("Could not get comment with id: " + commentId, e);
        }
    }

    @Override
    public Reply getReply(@NotNull Long replyId) throws DaoException {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Reply.class, replyId);
        } catch (Exception e) {
            //TODO: log
            throw new DaoException("Could not get reply with id: " + replyId, e);
        }
    }

    @Override
    public void addReply(@NotNull Reply reply) throws DaoException {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(reply);
            session.getTransaction().commit();
        }
    }

    @Override
    public void updateReply(@NotNull Reply reply) throws DaoException {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(reply);
            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteReply(@NotNull Long replyId) throws DaoException {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Reply reply = session.get(Reply.class, replyId);
            Comment comment = reply.getComment();
            comment.getReplies().remove(reply);
            session.update(comment);
            session.getTransaction().commit();
        } catch (Exception e) {
            //TODO: log
            throw new DaoException("Could not get reply with id: " + replyId, e);
        }
    }

    @Override
    public void upVoteReply(@NotNull Long replyId, @NotNull String userName) throws DaoException {
        Votes vote = getVoteFromReplyId(replyId, userName);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            if (vote == null) {
                vote = new Votes();
                vote.setUser(userDao.getUser(userName));
                vote.setUpOrDown(true);
                vote.setReply(getReply(replyId));
                session.persist(vote);
            } else {
                vote.setUpOrDown(false);
                session.update(vote);
            }
            session.getTransaction().commit();
        }
    }

    @Override
    public void downVoteReply(@NotNull Long replyId, @NotNull String userName) throws DaoException {
        Votes vote = getVoteFromReplyId(replyId, userName);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            if (vote == null) {
                vote = new Votes();
                vote.setUser(userDao.getUser(userName));
                vote.setUpOrDown(false);
                vote.setReply(getReply(replyId));
                session.persist(vote);
            } else {
                vote.setUpOrDown(false);
                session.update(vote);
            }
            session.getTransaction().commit();
        }
    }

    private Votes getVoteFromPostId(Long postId, String userName) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery(
                    String.format("from Votes where post='%d' and username='%s'", postId, userName));
            List<Votes> votes = query.list();
            if (votes.size() > 0) {
                return votes.get(0);
            }
            return null;
        } catch (Exception e) {
            //TODO: log
            throw new DaoException("Could not get post with id: " + postId, e);
        }
    }

    private Votes getVoteFromReplyId(Long replyId, String userName) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery(
                    String.format("from Votes where reply='%d' and username='%s'", replyId, userName));
            List<Votes> votes = query.list();
            if (votes.size() > 0) {
                return votes.get(0);
            }
            return null;
        } catch (Exception e) {
            //TODO: log
            throw new DaoException("Could not get reply with id: " + replyId, e);
        }
    }

    private Votes getVoteFromCommentId(Long commentId, String userName) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery(
                    String.format("from Votes where comment='%d' and username='%s'", commentId, userName));
            List<Votes> votes = query.list();
            if (votes.size() > 0) {
                return votes.get(0);
            }
            return null;
        } catch (Exception e) {
            //TODO: log
            throw new DaoException("Could not get comment with id: " + commentId, e);
        }
    }
}
