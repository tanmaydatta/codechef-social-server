package codechef.api.models;

import codechef.models.Reply;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class CommentApiModelResponse {

    String comment;
    String userName;
    Long id;
    Long postId;
    Date createdAt;
    Date updatedAt;
    Long upVotes;
    Long downVotes;
    Set<ReplyApiModelResponse> replies;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getUpVotes() {
        return upVotes;
    }

    public void setUpVotes(Long upVotes) {
        this.upVotes = upVotes;
    }

    public Long getDownVotes() {
        return downVotes;
    }

    public void setDownVotes(Long downVotes) {
        this.downVotes = downVotes;
    }

    public Set<ReplyApiModelResponse> getReplies() {
        return replies;
    }

    public void setReplies(Set<ReplyApiModelResponse> replies) {
        this.replies = replies;
    }
}
