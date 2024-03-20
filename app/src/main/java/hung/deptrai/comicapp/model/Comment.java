package hung.deptrai.comicapp.model;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class Comment {
    @SerializedName("commentID")
    private int commentID;
    @SerializedName("parent_commentID")
    private Integer parent_commentID;
    @SerializedName("content")
    private String content;
    @SerializedName("like")
    private int like;

    public Boolean getIslod() {
        return islod;
    }

    public void setIslod(Boolean islod) {
        this.islod = islod;
    }

    @SerializedName("dislike")
    private int dislike;
    @SerializedName("updated_at")
    private Timestamp updated_at;
    @SerializedName("accountID")
    private int accountID;
    @SerializedName("chapterID")
    private int chapterID;
    private Boolean islod;

    public Comment(int commentID, Integer parent_commentID, String content, int like, int dislike, Timestamp updated_at, int accountID, int chapterID) {
        this.commentID = commentID;
        this.parent_commentID = parent_commentID;
        this.content = content;
        this.like = like;
        this.dislike = dislike;
        this.updated_at = updated_at;
        this.accountID = accountID;
        this.chapterID = chapterID;
    }

    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public Integer getParent_commentID() {
        return parent_commentID;
    }

    public void setParent_commentID(Integer parent_commentID) {
        this.parent_commentID = parent_commentID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public int getChapterID() {
        return chapterID;
    }

    public void setChapterID(int chapterID) {
        this.chapterID = chapterID;
    }
}
