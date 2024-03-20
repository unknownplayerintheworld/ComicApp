package hung.deptrai.comicapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataJSON<T> {
    private boolean status;
    @SerializedName("obj")
    private List<T> data;
    private String message;

    public DataJSON() {
    }

    public DataJSON(boolean status, List<T> data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
