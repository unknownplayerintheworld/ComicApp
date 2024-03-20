package hung.deptrai.comicapp.model;

import com.google.gson.annotations.SerializedName;

public class Author {
    @SerializedName("transteamID")
    private String id;
    @SerializedName("transname")
    private String author_name;
    @SerializedName("transslogan")
    private String description;
    //
    private String comic_owner_author;
    //
    private String link_picture_comic;
    @SerializedName("transavatar")
    private String link_picture_author;

    public Author(String id, String author_name, String description, String comic_owner_author, String link_picture_comic, String link_picture_author) {
        this.id = id;
        this.author_name = author_name;
        this.description = description;
        this.comic_owner_author = comic_owner_author;
        this.link_picture_comic = link_picture_comic;
        this.link_picture_author = link_picture_author;
    }

    public Author(String id, String author_name, String description, String link_picture_author) {
        this.id = id;
        this.author_name = author_name;
        this.description = description;
        this.link_picture_author = link_picture_author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComic_owner_author() {
        return comic_owner_author;
    }

    public void setComic_owner_author(String comic_owner_author) {
        this.comic_owner_author = comic_owner_author;
    }

    public String getLink_picture_comic() {
        return link_picture_comic;
    }

    public void setLink_picture_comic(String link_picture_comic) {
        this.link_picture_comic = link_picture_comic;
    }

    public String getLink_picture_author() {
        return link_picture_author;
    }

    public void setLink_picture_author(String link_picture_author) {
        this.link_picture_author = link_picture_author;
    }
}
