package codechef.api.models;

import com.google.gson.annotations.SerializedName;

public class CodechefTokenData {

    @SerializedName("access_token")
    String accessToken;

    @SerializedName("scope")
    String scope;

    @SerializedName("refresh_token")
    String refreshToken;

    public String getAccessToken() {
        return accessToken;
    }

    public String getScope() {
        return scope;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
