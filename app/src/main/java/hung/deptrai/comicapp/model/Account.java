package hung.deptrai.comicapp.model;

import com.google.gson.annotations.SerializedName;

public class Account {
    private String username;
    private String password;
    @SerializedName("id")
    private String id;
    private String avatarAccount;

    public String getAvatarAccount() {
        return avatarAccount;
    }

    public void setAvatarAccount(String avatarAccount) {
        this.avatarAccount = avatarAccount;
    }

    public Account() {
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
