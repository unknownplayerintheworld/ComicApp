package hung.deptrai.comicapp.views.fragment.home.journey.outstanding;

public class Outstanding {
    private String id;
    private String OutstandingComicName;
    private int linkPicture;

    public Outstanding(String id, String outstandingComicName, int linkPicture) {
        this.id = id;
        this.OutstandingComicName = outstandingComicName;
        this.linkPicture = linkPicture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOutstandingComicName() {
        return OutstandingComicName;
    }

    public void setOutstandingComicName(String outstandingComicName) {
        OutstandingComicName = outstandingComicName;
    }

    public int getLinkPicture() {
        return linkPicture;
    }

    public void setLinkPicture(int linkPicture) {
        this.linkPicture = linkPicture;
    }
}
