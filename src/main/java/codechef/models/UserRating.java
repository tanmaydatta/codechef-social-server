package codechef.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "user_rating")
public class UserRating {

    @Id
    private String userName;

    private String allContestRating;

    private String longContestRating;

    private String shortContestRating;

    private String lTimeContestRating;

    private String allSchoolContestRating;

    private String longSchoolContestRating;

    private String shortSchoolContestRating;

    private String lTimeSchoolContestRating;

    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updateAt;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAllContestRating() {
        return allContestRating;
    }

    public void setAllContestRating(String allContestRating) {
        this.allContestRating = allContestRating;
    }

    public String getLongContestRating() {
        return longContestRating;
    }

    public void setLongContestRating(String longContestRating) {
        this.longContestRating = longContestRating;
    }

    public String getShortContestRating() {
        return shortContestRating;
    }

    public void setShortContestRating(String shortContestRating) {
        this.shortContestRating = shortContestRating;
    }

    public String getlTimeContestRating() {
        return lTimeContestRating;
    }

    public void setlTimeContestRating(String lTimeContestRating) {
        this.lTimeContestRating = lTimeContestRating;
    }

    public String getAllSchoolContestRating() {
        return allSchoolContestRating;
    }

    public void setAllSchoolContestRating(String allSchoolContestRating) {
        this.allSchoolContestRating = allSchoolContestRating;
    }

    public String getLongSchoolContestRating() {
        return longSchoolContestRating;
    }

    public void setLongSchoolContestRating(String longSchoolContestRating) {
        this.longSchoolContestRating = longSchoolContestRating;
    }

    public String getShortSchoolContestRating() {
        return shortSchoolContestRating;
    }

    public void setShortSchoolContestRating(String shortSchoolContestRating) {
        this.shortSchoolContestRating = shortSchoolContestRating;
    }

    public String getlTimeSchoolContestRating() {
        return lTimeSchoolContestRating;
    }

    public void setlTimeSchoolContestRating(String lTimeSchoolContestRating) {
        this.lTimeSchoolContestRating = lTimeSchoolContestRating;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }
}

