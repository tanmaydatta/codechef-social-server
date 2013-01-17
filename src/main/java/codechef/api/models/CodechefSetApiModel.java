package codechef.api.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CodechefSetApiModel {

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
        List<SetDesc> content;

        public List<SetDesc> getContent() {
            return content;
        }

        public void setContent(List<SetDesc> content) {
            this.content = content;
        }
    }

    public class SetDesc {
        @SerializedName("setName")
        String setName;
        @SerializedName("description")
        String description;

        public String getSetName() {
            return setName;
        }

        public void setSetName(String setName) {
            this.setName = setName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
