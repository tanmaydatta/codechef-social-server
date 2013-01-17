package codechef.api.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CodechefUserApiModel {

    @SerializedName("code")
    Integer code;

    @SerializedName("result")
    UserResult result;

    public int getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public UserResult getResult() {
        return result;
    }

    public void setResult(UserResult result) {
        this.result = result;
    }

    public class UserResult {

        @SerializedName("data")
        UserData data;

        public UserData getData() {
            return data;
        }

        public void setData(UserData data) {
            this.data = data;
        }
    }

    public class UserData {

        @SerializedName("content")
        User content;

        public User getContent() {
            return content;
        }

        public void setContent(User content) {
            this.content = content;
        }
    }

    public class User {

        @SerializedName("username")
        String username;

        @SerializedName("fullname")
        String fullname;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }
    }
}
