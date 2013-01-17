package codechef.models;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "contests")
public class Contests {

    String contestName;
    @Id
    @Column(name = "contestCode")
    String contestCode;
    Date startDate;
    Date endDate;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    ContestStatus status;
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;

    public Contests() {
        this.updatedAt = new Date();
    }

    public Contests(String contestName, String contestCode, Date startDate, Date endDate) {
        this.contestName = contestName;
        this.contestCode = contestCode;
        this.startDate = startDate;
        this.endDate = endDate;
        this.updatedAt = new Date();
        if (startDate.getTime() > System.currentTimeMillis()) {
            this.status = ContestStatus.FUTURE;
        } else if (startDate.getTime() < System.currentTimeMillis() && endDate.getTime() > System.currentTimeMillis()) {
            this.status = ContestStatus.PRESENT;
        } else {
            this.status = ContestStatus.PAST;
        }
    }

    public String getContestName() {
        return contestName;
    }

    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    public String getContestCode() {
        return contestCode;
    }

    public void setContestCode(String contestCode) {
        this.contestCode = contestCode;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public ContestStatus getStatus() {
        return status;
    }

    public void setStatus(ContestStatus status) {
        this.status = status;
    }
}
