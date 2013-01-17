package codechef.api.models;

import org.jetbrains.annotations.NotNull;

public class PostApiModel {

    @NotNull
    String userName;
    String post;

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
