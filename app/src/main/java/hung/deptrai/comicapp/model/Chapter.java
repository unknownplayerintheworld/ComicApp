package hung.deptrai.comicapp.model;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class Chapter {
    private int chapterID;
    @SerializedName("chapter_number_pos")
    private String chapter_pos;
    @SerializedName("chapter_comicID")
    private int comicIDfk;
    private Timestamp updated_at;
    private String comic_owner;

    public int getChapterID() {
        return chapterID;
    }

    public void setChapterID(int chapterID) {
        this.chapterID = chapterID;
    }

    public int getComicIDfk() {
        return comicIDfk;
    }

    public Chapter() {
    }

    public Chapter(int chapterID, String chapter_pos, int comicIDfk, Timestamp updated_at) {
        this.chapterID = chapterID;
        this.chapter_pos = chapter_pos;
        this.comicIDfk = comicIDfk;
        this.updated_at = updated_at;
    }

    public void setComicIDfk(int comicIDfk) {
        this.comicIDfk = comicIDfk;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public Chapter(String chapter_pos, String comic_owner) {
        this.chapter_pos = chapter_pos;
        this.comic_owner = comic_owner;
    }

    public String getChapter_pos() {
        return chapter_pos;
    }

    public void setChapter_pos(String chapter_pos) {
        this.chapter_pos = chapter_pos;
    }

    public String getComic_owner() {
        return comic_owner;
    }

    public void setComic_owner(String comic_owner) {
        this.comic_owner = comic_owner;
    }
}
