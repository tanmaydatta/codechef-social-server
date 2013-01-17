package codechef.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "problem", indexes = {@Index(columnList = "problemName", name = "problem_name")})
public class Problem {

    String problemCode;
    String problemName;
    long submissions;
    String category;
    private Date createdAt;
    private Date updatedAt;

    public Problem() {
        this.updatedAt = new Date();
    }

    public Problem(String problemName, String problemCode, long submissions, String category) {
        this.problemCode = problemCode;
        this.problemName = problemName;
        this.submissions = submissions;
        this.category = category;
    }

    @Id
    @Column(name = "problemCode")
    public String getProblemCode() {
        return problemCode;
    }

    public void setProblemCode(String id) {
        this.problemCode = id;
    }

    @Column
    public String getProblemName() {
        return problemName;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    @Column
    public long getSubmissions() {
        return submissions;
    }

    public void setSubmissions(long submissions) {
        this.submissions = submissions;
    }

    @Column
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @CreationTimestamp
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @UpdateTimestamp
    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}