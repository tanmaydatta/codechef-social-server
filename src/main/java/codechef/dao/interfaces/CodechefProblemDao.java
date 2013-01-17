package codechef.dao.interfaces;

import codechef.dao.exceptions.DaoException;
import codechef.dao.implementations.CodechefProblemDaoImpl;
import codechef.models.ContestStatus;
import codechef.models.Contests;
import codechef.models.Problem;
import codechef.models.Rankings;
import codechef.models.User;
import com.google.inject.ImplementedBy;

import java.util.List;

@ImplementedBy(CodechefProblemDaoImpl.class)
public interface CodechefProblemDao {

    void addProblem(Problem problem) throws DaoException;

    List<Problem> searchProblems(String query) throws DaoException;

    void addContest(Contests contest) throws DaoException;

    List<Contests> getAllContests() throws DaoException;

    List<Contests> getContestsByStatus(ContestStatus status) throws DaoException;

    void addRank(Rankings rank) throws DaoException;

    boolean isPresentContest(String code) throws DaoException;

    boolean isContestNotification(User user, String contestCode) throws DaoException;

}
