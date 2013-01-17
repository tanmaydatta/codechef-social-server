package codechef.api.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CodechefContestsApiModel {

    @SerializedName("status")
    String status;

    @SerializedName("result")
    ContestResult result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ContestResult getResult() {
        return result;
    }

    public void setResult(ContestResult result) {
        this.result = result;
    }

    public class ContestResult {
        @SerializedName("data")
        ContestData data;



        public ContestData getData() {
            return data;
        }

        public void setData(ContestData data) {
            this.data = data;
        }

    }

    public class ContestData {
        @SerializedName("content")
        ContestContent content;

        @SerializedName("code")
        Integer code;

        public ContestContent getContent() {
            return content;
        }

        public void setContent(ContestContent content) {
            this.content = content;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }
    }

    public class ContestContent {

        @SerializedName("contestList")
        List<Contest> contestList;

        public List<Contest> getContestList() {
            return contestList;
        }

        public void setContestList(List<Contest> contestList) {
            this.contestList = contestList;
        }
    }

    public class Contest {
        @SerializedName("code")
        String code;
        @SerializedName("name")
        String name;
        @SerializedName("startDate")
        String startDate;
        @SerializedName("endDate")
        String endDate;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }
    }
}
