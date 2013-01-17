package codechef.api.models;

import com.google.gson.annotations.SerializedName;

public class CodechefTokenResult {

    @SerializedName("data")
    CodechefTokenData data;

    @SerializedName("errors")
    CodechefTokenError errors;

    public CodechefTokenData getData() {
        return data;
    }

    public void setData(CodechefTokenData data) {
        this.data = data;
    }

    public CodechefTokenError getErrors() {
        return errors;
    }

    public void setErrors(CodechefTokenError errors) {
        this.errors = errors;
    }
}
