package hung.deptrai.comicapp.views.fragment.home.journey.Section;

import java.util.List;

public class Section {
    private List list;
    private String title;
    private int type;

    public Section(List list, int type) {
        this.list = list;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Section(List list, int type, String title) {
        this.list = list;
        this.type = type;
        this.title = title;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
