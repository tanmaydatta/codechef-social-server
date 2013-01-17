package codechef.dao.implementations;

import codechef.dao.exceptions.DaoException;
import codechef.dao.interfaces.CodechefUserDao;
import codechef.models.Feed;
import codechef.models.Friend;
import codechef.models.Messages;
import codechef.models.User;
import codechef.models.UserRating;
import com.google.inject.Inject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.jetbrains.annotations.NotNull;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CodechefUserDaoImpl implements CodechefUserDao {

    @Inject
    SessionFactory sessionFactory;

    @Override
    public User getUser(@NotNull String userId) throws DaoException {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery(String.format("from User where username='%s'", userId));
            List<User> users = query.list();
            return users.get(0);
        } catch (Exception e) {
            //TODO: log
            throw new DaoException("Could not get User with id: " + userId, e);
        }
    }

    @Override
    public void saveOrUpdateUser(User user) throws DaoException {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            //TODO: log
            throw new DaoException("Could not save User with id: " + user.getUserName(), e);
        } finally {
            session.close();
        }
    }

    @Override
    public User getUserByToken(String token) throws DaoException {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery(String.format("from User where appToken='%s'", token));
            List<User> users = query.list();
            return users.get(0);
        } catch (Exception e) {
            //TODO: log
            throw new DaoException("Could not get User with token: " + token, e);
        }
    }

    @Override
    public User getUserByAppToken(String token) throws DaoException {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery(String.format("from User where appToken='%s'", token));
            List<User> users = query.list();
            return users.get(0);
        } catch (Exception e) {
            //TODO: log
            throw new DaoException("Could not get User with token: " + token, e);
        }
    }

    @Override
    public String getAdminToken(String userId) throws DaoException {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, userId);
            return user.getAccessToken();
        } catch (Exception e) {
            //TODO: log
            throw new DaoException("Could not get user with id: " + userId, e);
        }
    }

    @Override
    public List<Feed> getUserFeed(User user, Date lastUpdatedAtInSec) throws DaoException {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Feed> cr = cb.createQuery(Feed.class);
            Root<Feed> root = cr.from(Feed.class);
            ArrayList<Predicate> conditions = new ArrayList<>();
            conditions.add(cb.lessThan(root.get("feedDate"), lastUpdatedAtInSec));
            conditions.add(cb.equal(root.get("user"), user));
            cr.select(root)
                    .where(conditions.toArray(new Predicate[conditions.size()]))
                    .orderBy(cb.desc(root.get("feedDate")));
            Query<Feed> query = session.createQuery(cr).setMaxResults(20);
            List<Feed> feeds = query.getResultList();
            feeds.sort(new Comparator<Feed>() {
                @Override
                public int compare(Feed o1, Feed o2) {
                    return -Long.compare(o1.getFeedDate().getTime(), o2.getFeedDate().getTime());
                }
            });
            return feeds;
        } catch (Exception e) {
            //TODO: log
            throw new DaoException("Could not get feed for user " + user.getUserName(), e);
        }
    }

    @Override
    public List<Feed> getUserNotifications(User user, Date lastUpdatedAtInSec) throws DaoException {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Feed> cr = cb.createQuery(Feed.class);
            Root<Feed> root = cr.from(Feed.class);
            ArrayList<Predicate> conditions = new ArrayList<>();
            conditions.add(cb.lessThan(root.get("feedDate"), lastUpdatedAtInSec));
            conditions.add(cb.isTrue(root.get("isNotification")));
            conditions.add(cb.equal(root.get("user"), user));
            cr.select(root)
                    .where(conditions.toArray(new Predicate[conditions.size()]))
                    .orderBy(cb.desc(root.get("feedDate")));
            Query<Feed> query = session.createQuery(cr).setMaxResults(20);
            List<Feed> feeds = query.getResultList();
//            feeds = feeds.stream().filter(Feed::getNotification).collect(Collectors.toList());
            feeds.sort(new Comparator<Feed>() {
                @Override
                public int compare(Feed o1, Feed o2) {
                    return -Long.compare(o1.getFeedDate().getTime(), o2.getFeedDate().getTime());
                }
            });
            return feeds;
        } catch (Exception e) {
            //TODO: log
            throw new DaoException("Could not get feed for user " + user.getUserName(), e);
        }
    }

    @Override
    public List<Messages> getUserMessages(User fromUser, User toUser, Date lastUpdatedAtInSec) throws DaoException {
        try {
            List<Messages> messages = getMessages(fromUser, toUser, lastUpdatedAtInSec);
            messages.addAll(getMessages(toUser, fromUser, lastUpdatedAtInSec));
            messages.sort(new Comparator<Messages>() {
                @Override
                public int compare(Messages o1, Messages o2) {
                    return Long.compare(o1.getUpdatedAt().getTime(), o2.getUpdatedAt().getTime());
                }
            });
            return messages;
        } catch (Exception e) {
            //TODO: log
            throw new DaoException("Could not get messages for user " + fromUser.getUserName(), e);
        }
    }

    @Override
    public Messages sendMessage(User fromUser, User toUser, String msg) throws DaoException {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Messages message = new Messages();
            message.setFromUser(fromUser);
            message.setToUser(toUser);
            message.setMessage(msg);
            session.persist(message);
            session.getTransaction().commit();
            return message;
        } catch (Exception e) {
            //TODO: log
            throw new DaoException("Could not save message ", e);
        }
    }


    @Override
    public List<User> getAllUsers() throws DaoException {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cr = cb.createQuery(User.class);
            Root<User> root = cr.from(User.class);
            cr.select(root)
                    .where(cb.isNotNull(root.get("userName")));
            Query<User> query = session.createQuery(cr);
            return query.getResultList();
        } catch (Exception e) {
            //TODO: log
            throw new DaoException("Could not all users", e);
        }
    }

    @Override
    public void addFriends(Friend friend) throws DaoException {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(friend);
            session.getTransaction().commit();
        } catch (Exception e) {
            //TODO: log
            throw new DaoException("Could not save friends of user with id: " + friend.getUser().getUserName(), e);
        }
    }

    @Override
    public boolean isSubmissionFeed(User user, String actionUser, Long submissionId) throws DaoException {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Feed> cr = cb.createQuery(Feed.class);
            Root<Feed> root = cr.from(Feed.class);
            cr.select(root)
                    .where(cb.equal(root.get("actionUser"), actionUser))
                    .where(cb.equal(root.get("submissionId"), submissionId));
            Query<Feed> query = session.createQuery(cr);
            List<Feed> feeds = query.getResultList();
            feeds = feeds.stream().filter(f -> f.getUser().getUserName()
                    .equals(user.getUserName())).collect(Collectors.toList());
            return feeds.size() > 0;
        } catch (Exception e) {
            //TODO: log
            throw new DaoException("Could not all users", e);
        }
    }

    @Override
    public List<Friend> getAllFriends() throws DaoException {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Friend> cr = cb.createQuery(Friend.class);
            Root<Friend> root = cr.from(Friend.class);
            cr.select(root)
                    .where(cb.isNotNull(root.get("id")));
            Query<Friend> query = session.createQuery(cr);
            return query.getResultList();
        } catch (Exception e) {
            //TODO: log
            throw new DaoException("Could not all users", e);
        }
    }

    @Override
    public void addFeed(Feed feed) throws DaoException {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(feed);
            session.getTransaction().commit();
        } catch (Exception e) {
            //TODO: log
            throw new DaoException("Could not save feed ", e);
        }
    }

    @Override
    public void addUserRating(UserRating rating) throws DaoException {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(rating);
            session.getTransaction().commit();
        } catch (Exception e) {
            //TODO: log
            throw new DaoException("Could not save feed ", e);
        }
    }

    @Override
    public List<UserRating> getRatingsOfFriends(User user) throws DaoException {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Friend> cr = cb.createQuery(Friend.class);
            Root<Friend> root = cr.from(Friend.class);
            cr.select(root)
                    .where(cb.equal(root.get("id"), user.getUserName()));
            Query<Friend> query = session.createQuery(cr);
            assert (query.getResultList().size() == 1);
            Friend friend = query.getResultList().get(0);
            cb = session.getCriteriaBuilder();
            CriteriaQuery<UserRating> ur = cb.createQuery(UserRating.class);
            Root<UserRating> rating = ur.from(UserRating.class);
            Expression<String> exp = rating.get("userName");
            List<String> friends = Arrays.asList(friend.getFriends().split(","));
            Predicate in = exp.in(friends);
            ur.select(rating)
                    .where(in);
            Query<UserRating> queryRating = session.createQuery(ur);
            List<UserRating> result = queryRating.getResultList();
            return result;
        } catch (Exception e) {
            //TODO: log
            throw new DaoException("Could not all users", e);
        }
    }

    @Override
    public List<User> searchUsers(String query) throws DaoException {
        Session session = sessionFactory.openSession();
        Query q = session.createQuery("from User where username LIKE CONCAT('%',?1,'%')");
        q.setParameter(1, query);
        q.setMaxResults(10);
        try {
            return q.getResultList();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Object[]> getMessageList(User user) throws DaoException {
        Session session = sessionFactory.openSession();
        String query = "select m.fromUser, m.toUser, max(m.createdAt) from Messages m where " +
                "fromUser='" + user.getUserName() + "' or toUser='" + user.getUserName() + "' group by m.fromUser, m.toUser";
        TypedQuery q = session.createQuery(query);
        try {
            return q.getResultList();
        } finally {
            session.close();
        }
    }

    private List<Messages> getMessages(User from, User to, Date lastUpdatedAtInSec) throws Exception {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Messages> cr = cb.createQuery(Messages.class);
        Root<Messages> root = cr.from(Messages.class);
        ArrayList<Predicate> conditions = new ArrayList<>();
        conditions.add(cb.greaterThan(root.get("updatedAt"), lastUpdatedAtInSec));
        conditions.add(cb.equal(root.get("fromUser"), from));
        conditions.add(cb.equal(root.get("toUser"), to));
        cr.select(root)
                .where(conditions.toArray(new Predicate[conditions.size()]));
        Query<Messages> query = session.createQuery(cr);
        List<Messages> messages = query.getResultList();
        session.close();
        return messages;
    }
}
