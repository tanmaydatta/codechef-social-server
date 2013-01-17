package codechef.models;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "rankings")
@IdClass(RankingPK.class)
public class Rankings {

    @Id
    Long rank;

    @Id
    @ManyToOne
    @JoinColumn(name="contest", nullable=false)
    private Contests contest;

    private String userName;
    private String institution;
    private Double totalScore;
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;

    public Rankings() {
        this.updatedAt = new Date();
    }

    public Rankings(Long rank, Contests contest, String userName, String institution, Double totalScore) {
        this.rank = rank;
        this.contest = contest;
        this.userName = userName;
        this.institution = institution;
        this.totalScore = totalScore;
    }

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }

    public Contests getContest() {
        return contest;
    }

    public void setContest(Contests contest) {
        this.contest = contest;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }
}
