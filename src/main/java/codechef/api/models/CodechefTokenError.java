package codechef.api.models;

import com.google.gson.annotations.SerializedName;

public class CodechefTokenError {

    @SerializedName("message")
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
