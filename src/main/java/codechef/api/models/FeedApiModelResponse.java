package codechef.api.models;

import codechef.models.FeedType;

import java.util.Date;

public class FeedApiModelResponse {

    Long id;
    String user;

    String actionUser;

//    PostApiModelResponse post;
    Long submissionId;
    String problemCode;
    String problemName;
    String contestName;
    String contestCode;
    Date feedDate;
    String submissionResult;

    Date createdAt;
    Date updatedAt;
    FeedType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getActionUser() {
        return actionUser;
    }

    public void setActionUser(String actionUser) {
        this.actionUser = actionUser;
    }

//    public PostApiModelResponse getPost() {
//        return post;
//    }

//    public void setPost(PostApiModelResponse post) {
//        this.post = post;
//    }

    public Long getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(Long submissionId) {
        this.submissionId = submissionId;
    }

    public String getProblemCode() {
        return problemCode;
    }

    public void setProblemCode(String problemCode) {
        this.problemCode = problemCode;
    }

    public String getProblemName() {
        return problemName;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
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

    public FeedType getType() {
        return type;
    }

    public void setType(FeedType type) {
        this.type = type;
    }

    public Date getFeedDate() {
        return feedDate;
    }

    public void setFeedDate(Date feedDate) {
        this.feedDate = feedDate;
    }

    public String getSubmissionResult() {
        return submissionResult;
    }

    public void setSubmissionResult(String submissionResult) {
        this.submissionResult = submissionResult;
    }
}
