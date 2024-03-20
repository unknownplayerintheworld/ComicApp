package hung.deptrai.comicapp.model;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class Comic {
    @SerializedName("comicID")
    private String id;
    @SerializedName("comicName")
    private String ComicName;
    @SerializedName("avatarLink")
    private String linkPicture;
    private String description;
    private String author;
    // test
    private String genre;
    // test
    private String linkPictureAuthor;
    //test
    private int views;
    private Timestamp created_at;
    private Timestamp updated_at;
    private int views_in_month;
    private int views_in_week;
    private int color_res;

    public int getColor_res() {
        return color_res;
    }

    public void setColor_res(int color_res) {
        this.color_res = color_res;
    }

    public Comic() {
    }

    public int getViews_in_month() {
        return views_in_month;
    }


    public void setViews_in_month(int views_in_month) {
        this.views_in_month = views_in_month;
    }

    public int getViews_in_week() {
        return views_in_week;
    }

    public void setViews_in_week(int views_in_week) {
        this.views_in_week = views_in_week;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public String getLinkPictureAuthor() {
        return linkPictureAuthor;
    }

    public void setLinkPictureAuthor(String linkPictureAuthor) {
        this.linkPictureAuthor = linkPictureAuthor;
    }

    public Comic(String id, String comicName, String linkPicture, String author, String genre, String linkPictureAuthor) {
        this.id = id;
        ComicName = comicName;
        this.linkPicture = linkPicture;
        this.author = author;
        this.genre = genre;
        this.linkPictureAuthor = linkPictureAuthor;
    }

    public Comic(String id, String comicName, String linkPicture, int views, Timestamp created_at, Timestamp updated_at, int views_in_month, int views_in_week) {
        this.id = id;
        ComicName = comicName;
        this.linkPicture = linkPicture;
        this.views = views;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.views_in_month = views_in_month;
        this.views_in_week = views_in_week;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Comic(String id, String comicName, String linkPicture, String author, String genre) {
        this.id = id;
        ComicName = comicName;
        this.linkPicture = linkPicture;
        this.author = author;
        this.genre = genre;
    }

    public Comic(String id, String comicName, String linkPicture, String author) {
        this.id = id;
        ComicName = comicName;
        this.linkPicture = linkPicture;
        this.author = author;
    }

    public Comic(String id, String comicName, String linkPicture) {
        this.id = id;
        ComicName = comicName;
        this.linkPicture = linkPicture;
    }

    public Comic(String id, String comicName) {
        this.id = id;
        ComicName = comicName;
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

    public String getLinkPicture() {
        return linkPicture;
    }

    public void setLinkPicture(String linkPicture) {
        this.linkPicture = linkPicture;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
