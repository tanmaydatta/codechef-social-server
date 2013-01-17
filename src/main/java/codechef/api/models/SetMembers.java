package codechef.api.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SetMembers {

    @SerializedName("status")
    String status;

    @SerializedName("result")
    SetResult result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SetResult getResult() {
        return result;
    }

    public void setResult(SetResult result) {
        this.result = result;
    }

    public class SetResult {

        @SerializedName("data")
        SetData data;

        public SetData getData() {
            return data;
        }

        public void setData(SetData data) {
            this.data = data;
        }
    }

    public class SetData {

        @SerializedName("content")
        List<Member> content;

        public List<Member> getContent() {
            return content;
        }

        public void setContent(List<Member> content) {
            this.content = content;
        }
    }

    public class Member {
        @SerializedName("memberName")
        String memberName;

        @SerializedName("allContestRating")
        String allContestRating;

        @SerializedName("longContestRating")
        String longContestRating;

        @SerializedName("shortContestRating")
        String shortContestRating;

        @SerializedName("lTimeContestRating")
        String lTimeContestRating;

        @SerializedName("allSchoolContestRating")
        String allSchoolContestRating;

        @SerializedName("longSchoolContestRating")
        String longSchoolContestRating;

        @SerializedName("shortSchoolContestRating")
        String shortSchoolContestRating;

        @SerializedName("lTimeSchoolContestRating")
        String lTimeSchoolContestRating;

        public String getAllContestRating() {
            return allContestRating;
        }

        public void setAllContestRating(String allContestRating) {
            this.allContestRating = allContestRating;
        }

        public String getLongContestRating() {
            return longContestRating;
        }

        public void setLongContestRating(String longContestRating) {
            this.longContestRating = longContestRating;
        }

        public String getShortContestRating() {
            return shortContestRating;
        }

        public void setShortContestRating(String shortContestRating) {
            this.shortContestRating = shortContestRating;
        }

        public String getlTimeContestRating() {
            return lTimeContestRating;
        }

        public void setlTimeContestRating(String lTimeContestRating) {
            this.lTimeContestRating = lTimeContestRating;
        }

        public String getAllSchoolContestRating() {
            return allSchoolContestRating;
        }

        public void setAllSchoolContestRating(String allSchoolContestRating) {
            this.allSchoolContestRating = allSchoolContestRating;
        }

        public String getLongSchoolContestRating() {
            return longSchoolContestRating;
        }

        public void setLongSchoolContestRating(String longSchoolContestRating) {
            this.longSchoolContestRating = longSchoolContestRating;
        }

        public String getShortSchoolContestRating() {
            return shortSchoolContestRating;
        }

        public void setShortSchoolContestRating(String shortSchoolContestRating) {
            this.shortSchoolContestRating = shortSchoolContestRating;
        }

        public String getlTimeSchoolContestRating() {
            return lTimeSchoolContestRating;
        }

        public void setlTimeSchoolContestRating(String lTimeSchoolContestRating) {
            this.lTimeSchoolContestRating = lTimeSchoolContestRating;
        }

        public String getMemberName() {
            return memberName;
        }

        public void setMemberName(String memberName) {
            this.memberName = memberName;
        }

    }
}
