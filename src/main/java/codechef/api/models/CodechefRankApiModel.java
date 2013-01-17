package codechef.api.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CodechefRankApiModel {

    @SerializedName("status")
    String status;

    @SerializedName("result")
    RankingResult result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public RankingResult getResult() {
        return result;
    }

    public void setResult(RankingResult result) {
        this.result = result;
    }

    public class RankingResult {
        @SerializedName("data")
        RankingData data;

        public RankingData getData() {
            return data;
        }

        public void setData(RankingData data) {
            this.data = data;
        }
    }

    public class RankingData {

        @SerializedName("content")
        List<Ranking> content;

        @SerializedName("code")
        Integer code;

        public List<Ranking> getContent() {
            return content;
        }

        public void setContent(List<Ranking> content) {
            this.content = content;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }
    }

    public class Ranking {

        @SerializedName("rank")
        String rank;
        @SerializedName("username")
        String username;
        @SerializedName("institution")
        String institution;
        @SerializedName("totalScore")
        String totalScore;

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getInstitution() {
            return institution;
        }

        public void setInstitution(String institution) {
            this.institution = institution;
        }

        public String getTotalScore() {
            return totalScore;
        }

        public void setTotalScore(String totalScore) {
            this.totalScore = totalScore;
        }
    }
}
