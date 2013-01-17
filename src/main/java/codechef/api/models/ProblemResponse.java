package codechef.api.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class ProblemResponse {
    @SerializedName("code")
    String status;

    @SerializedName("result")
    ProblemResponse.ProblemResult result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ProblemResponse.ProblemResult getResult() {
        return result;
    }

    public void setResult(ProblemResponse.ProblemResult result) {
        this.result = result;
    }


    public class ProblemResult {

        @SerializedName("data")
        ProblemResponse.ProblemData data;

        public ProblemResponse.ProblemData getData() {
            return data;
        }

        public void setData(ProblemResponse.ProblemData data) {
            this.data = data;
        }
    }

    public class ProblemData {

        @SerializedName("content")
        ProblemContent content;

        @SerializedName("code")
        Integer code;

        public ProblemContent getContent() {
            return content;
        }

        public void setContent(ProblemContent content) {
            this.content = content;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }
    }

    public class ProblemContent {

        @SerializedName("problemCode")
        String problemCode;

        @SerializedName("problemName")
        String problemName;

        @SerializedName("body")
        String body;

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
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

    }
}
