package codechef.api.models;

import com.google.gson.annotations.SerializedName;

public class CodechefTokenResponse {

    @SerializedName("code")
    String status;

    @SerializedName("result")
    CodechefTokenResult result;

    public String getStatus() {
        return status;
    }

    public CodechefTokenResult getResult() {
        return result;
    }
}
