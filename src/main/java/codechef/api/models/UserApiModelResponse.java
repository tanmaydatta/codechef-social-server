package codechef.api.models;

import java.util.Date;
import java.util.Set;

public class UserApiModelResponse {

    String userName;
    String fullName;
    Date createdAt;
    Date updatedAt;
//    Set<PostApiModelResponse> posts;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

//    public Set<PostApiModelResponse> getPosts() {
//        return posts;
//    }

//    public void setPosts(Set<PostApiModelResponse> posts) {
//        this.posts = posts;
//    }
}
