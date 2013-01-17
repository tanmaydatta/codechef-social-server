package codechef.api.models;

import java.util.HashSet;
import java.util.Set;

public class ProblemApiResponse {

    Set<Problem> problems;

    public ProblemApiResponse() {
        this.problems = new HashSet<>();
    }

    public Set<Problem> getProblems() {
        return problems;
    }

    public void setProblems(Set<Problem> problems) {
        this.problems = problems;
    }

    public void addProblem(String name, String code) {
        problems.add(new Problem(name, code));
    }

}

