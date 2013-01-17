package codechef.api.models;

import codechef.models.Post;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class PostApiModelResponse {

    Long id;
    String post;
    String userName;
    Date createdAt;
    Date updatedAt;
    Long upVotes;
    Long downVotes;
    Set<CommentApiModelResponse> comments;

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Set<CommentApiModelResponse> getComments() {
        return comments;
    }

    public void setComments(Set<CommentApiModelResponse> comments) {
        this.comments = comments;
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
}
