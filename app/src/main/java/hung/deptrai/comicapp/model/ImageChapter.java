package hung.deptrai.comicapp.model;

import com.google.gson.annotations.SerializedName;

public class ImageChapter {
    @SerializedName("imageID")
    private String id;
    private String chapter_owner;
    @SerializedName("link")
    private String linkImage;
    private int ResourceIds;
    @SerializedName("image_pos")
    private int image_pos;



    public int getImage_pos() {
        return image_pos;
    }

    public void setImage_pos(int image_pos) {
        this.image_pos = image_pos;
    }

    public int getResourceIds() {
        return ResourceIds;
    }

    public ImageChapter(String id, String linkImage, int image_pos) {
        this.id = id;
        this.linkImage = linkImage;
        this.image_pos = image_pos;
    }

    public void setResourceIds(int resourceIds) {
        ResourceIds = resourceIds;
    }

    public ImageChapter(String id, String chapter_owner, String linkImage) {
        this.id = id;
        this.chapter_owner = chapter_owner;
        this.linkImage = linkImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChapter_owner() {
        return chapter_owner;
    }

    public void setChapter_owner(String chapter_owner) {
        this.chapter_owner = chapter_owner;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }
}
