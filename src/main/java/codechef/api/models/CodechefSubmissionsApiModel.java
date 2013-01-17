package codechef.api.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CodechefSubmissionsApiModel {

    @SerializedName("status")
    String status;

    @SerializedName("result")
    SubmissionsResult result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SubmissionsResult getResult() {
        return result;
    }

    public void setResult(SubmissionsResult result) {
        this.result = result;
    }

    public class SubmissionsResult {
        @SerializedName("data")
        SubmissionsData data;

        public SubmissionsData getData() {
            return data;
        }

        public void setData(SubmissionsData data) {
            this.data = data;
        }
    }

    public class SubmissionsData {
        @SerializedName("content")
        List<SubmissionDetail> content;

        public List<SubmissionDetail> getContent() {
            return content;
        }

        public void setContent(List<SubmissionDetail> content) {
            this.content = content;
        }
    }

    public class SubmissionDetail {
        @SerializedName("id")
        Long id;
        @SerializedName("date")
        String date;
        @SerializedName("contestCode")
        String contestCode;
        @SerializedName("result")
        String result;
        @SerializedName("problemCode")
        String problemCode;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getContestCode() {
            return contestCode;
        }

        public void setContestCode(String contestCode) {
            this.contestCode = contestCode;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }


        public String getProblemCode() {
            return problemCode;
        }

        public void setProblemCode(String problemCode) {
            this.problemCode = problemCode;
        }
    }
}
