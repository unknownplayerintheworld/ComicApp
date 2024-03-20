package hung.deptrai.comicapp.model;

import com.google.gson.annotations.SerializedName;

public class Favourite {
    @SerializedName("favouriteID")
    private String id;
    private String comicID;
    private String ComicName;
    private String username;
    private String author;
    private String linkPicture;
    private int linkPicTest;

    public Favourite(String id, String comicName, String username, String author, String linkPicture) {
        this.id = id;
        ComicName = comicName;
        this.username = username;
        this.author = author;
        this.linkPicture = linkPicture;
    }

    public Favourite(String id, String comicName, String linkPicture) {
        this.id = id;
        this.ComicName = comicName;
        this.linkPicture = linkPicture;
    }
    public Favourite(String comicName, int linkPictest) {
        this.id = id;
        ComicName = comicName;
        this.linkPicTest = linkPictest;
    }


    public String getComicID() {
        return comicID;
    }

    public void setComicID(String comicID) {
        this.comicID = comicID;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLinkPicture() {
        return linkPicture;
    }

    public void setLinkPicture(String linkPicture) {
        this.linkPicture = linkPicture;
    }
}
