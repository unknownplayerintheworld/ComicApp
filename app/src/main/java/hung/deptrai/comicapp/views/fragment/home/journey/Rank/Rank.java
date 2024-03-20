package hung.deptrai.comicapp.views.fragment.home.journey.Rank;

public class Rank {
    private String id;
    private int position;
    private String linkPicture;
    private String RankComicName;
    private int color;

    public Rank(String id, int position, String linkPicture, String rankComicName, int color) {
        this.id = id;
        this.position = position;
        this.linkPicture = linkPicture;
        RankComicName = rankComicName;
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getLinkPicture() {
        return linkPicture;
    }

    public void setLinkPicture(String linkPicture) {
        this.linkPicture = linkPicture;
    }

    public String getRankComicName() {
        return RankComicName;
    }

    public void setRankComicName(String rankComicName) {
        RankComicName = rankComicName;
    }
}
