package codechef.api.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CodechefProblemsResponse {

    @SerializedName("code")
    String status;

    @SerializedName("result")
    ProblemResult result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ProblemResult getResult() {
        return result;
    }

    public void setResult(ProblemResult result) {
        this.result = result;
    }


    public class ProblemResult {

        @SerializedName("data")
        ProblemData data;

        public ProblemData getData() {
            return data;
        }

        public void setData(ProblemData data) {
            this.data = data;
        }
    }

    public class ProblemData {

        @SerializedName("content")
        List<ProblemContent> content;

        @SerializedName("code")
        Integer code;

        public List<ProblemContent> getContent() {
            return content;
        }

        public void setContent(List<ProblemContent> content) {
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

        @SerializedName("successfulSubmissions")
        Long submissions;

        @SerializedName("accuracy")
        Double accuracy;

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

        public Long getSubmissions() {
            return submissions;
        }

        public void setSubmissions(Long submissions) {
            this.submissions = submissions;
        }

        public Double getAccuracy() {
            return accuracy;
        }

        public void setAccuracy(Double accuracy) {
            this.accuracy = accuracy;
        }
    }
}