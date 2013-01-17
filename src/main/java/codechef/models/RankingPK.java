package codechef.models;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RankingPK implements Serializable {

    private long rank;
    private Contests contest;

    // standard constructor, getters, setters
    // equals() and hashCode()


    public long getRank() {
        return rank;
    }

    public void setRank(long rank) {
        this.rank = rank;
    }

    public Contests getContest() {
        return contest;
    }

    public void setContest(Contests contest) {
        this.contest = contest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RankingPK rankingPK = (RankingPK) o;
        return rank == rankingPK.rank && contest.contestCode.equals(rankingPK.contest.contestCode);
    }

    @Override
    public int hashCode() {

        return Objects.hash(rank, contest);
    }
}