package codechef.dao.implementations;

import codechef.dao.exceptions.DaoException;
import codechef.dao.interfaces.CodechefProblemDao;
import codechef.models.ContestStatus;
import codechef.models.Contests;
import codechef.models.Feed;
import codechef.models.Messages;
import codechef.models.Problem;
import codechef.models.Rankings;
import codechef.models.User;
import com.google.inject.Inject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;

public class CodechefProblemDaoImpl implements CodechefProblemDao {

    @Inject
    SessionFactory sessionFactory;

    @Override
    public void addProblem(Problem problem) throws DaoException {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(problem);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<Problem> searchProblems(String query) throws DaoException {
        Session session = sessionFactory.openSession();
        Query q = session.createQuery("from Problem where problemname LIKE CONCAT('%',?1,'%')");
        q.setParameter(1, query);
        q.setMaxResults(10);
        try {
            List<Problem> problems = q.getResultList();
            return problems;
        } finally {
            session.close();
        }
    }

    @Override
    public void addContest(Contests contest) throws DaoException {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(contest);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<Contests> getAllContests() throws DaoException {
        try {
            Session session = sessionFactory.openSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Contests> cr = cb.createQuery(Contests.class);
            Root<Contests> root = cr.from(Contests.class);
            cr.select(root)
                    .where(cb.isNotNull(root.get("contestCode")));
            Query<Contests> query = session.createQuery(cr);
            session.close();
            return query.getResultList();
        } catch (Exception e) {
            //TODO: log
            throw new DaoException("Unable to get all contests", e);
        }
    }

    @Override
    public void addRank(Rankings rank) throws DaoException {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(rank);
            session.getTransaction().commit();
        }
    }

    @Override
    public boolean isPresentContest(String code) throws DaoException {
        try (Session session = sessionFactory.openSession()) {
            Contests contest = session.get(Contests.class, code);
            return contest.getStatus() == ContestStatus.PRESENT;
        } catch (Exception e) {
            //TODO: log
            throw new DaoException("Unable to get all contests", e);
        }
    }

    @Override
    public List<Contests> getContestsByStatus(ContestStatus status) throws DaoException {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Contests> cr = cb.createQuery(Contests.class);
            Root<Contests> root = cr.from(Contests.class);
            cr.select(root)
                    .where(cb.equal(root.get("status"), status));
            Query<Contests> query = session.createQuery(cr);
            return query.getResultList();
        } catch (Exception e) {
            //TODO: log
            throw new DaoException("Could not get contests for status " + status, e);
        }
    }

    @Override
    public boolean isContestNotification(User user, String contestCode) throws DaoException {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Feed> cr = cb.createQuery(Feed.class);
            Root<Feed> root = cr.from(Feed.class);
            cr.select(root)
                    .where(cb.equal(root.get("contestCode"), contestCode));
            Query<Feed> query = session.createQuery(cr);
            List<Feed> feeds = query.getResultList();
            feeds = feeds.stream().filter(f -> f.getUser().getUserName()
                    .equals(user.getUserName()) && f.getSubmissionId() == null).collect(Collectors.toList());
            return feeds.size() > 0;
        } catch (Exception e) {
            //TODO: log
            throw new DaoException("Could not all users", e);
        }
    }
}
