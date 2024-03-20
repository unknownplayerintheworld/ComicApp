package hung.deptrai.comicapp.model;

import com.google.gson.annotations.SerializedName;

public class History{
    @SerializedName("historyID")
    private String id;
    private String accountID;

    private String ComicID;
    //
    private String ComicName;
    private String username;
    private int current_reading_chapter;
    private String linkPicture;

    // Here are the properties used for UI testing
    private String author;
    private int views;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getComicID() {
        return ComicID;
    }

    public void setComicID(String comicID) {
        ComicID = comicID;
    }

    public History(String id, String accountID) {
        this.id = id;
        this.accountID = accountID;
    }

    public History(String id, String comicName, int current_reading_chapter, String linkPicture, String author, int views) {
        this.id = id;
        ComicName = comicName;
        this.current_reading_chapter = current_reading_chapter;
        this.linkPicture = linkPicture;
        this.author = author;
        this.views = views;
    }
    public History(String id,String comicID, String comicName, int current_reading_chapter, String linkPicture, String author, int views) {
        this.id = id;
        this.ComicID = comicID;
        ComicName = comicName;
        this.current_reading_chapter = current_reading_chapter;
        this.linkPicture = linkPicture;
        this.author = author;
        this.views = views;
    }

    public History(String id, String comicName, String username, int current_reading_chapter, String linkPicture) {
        this.id = id;
        ComicName = comicName;
        this.username = username;
        this.current_reading_chapter = current_reading_chapter;
        this.linkPicture = linkPicture;
    }
    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getLinkPicture() {
        return linkPicture;
    }

    public void setLinkPicture(String linkPicture) {
        this.linkPicture = linkPicture;
    }

    public History(String id, String comicName, String username, int current_reading_chapter) {
        this.id = id;
        ComicName = comicName;
        this.username = username;
        this.current_reading_chapter = current_reading_chapter;
    }

    public History(String id, String comicName, String username,String link) {
        this.id = id;
        this.linkPicture = link;
        ComicName = comicName;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComicName() {
        return ComicName;
    }

    public void setComicName(String comicName) {
        ComicName = comicName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCurrent_reading_chapter() {
        return current_reading_chapter;
    }

    public void setCurrent_reading_chapter(int current_reading_chapter) {
        this.current_reading_chapter = current_reading_chapter;
    }
}
