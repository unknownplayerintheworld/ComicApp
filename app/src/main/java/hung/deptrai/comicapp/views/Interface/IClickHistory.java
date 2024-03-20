package hung.deptrai.comicapp.views.Interface;

import hung.deptrai.comicapp.model.Chapter;
import hung.deptrai.comicapp.model.Comic;
import hung.deptrai.comicapp.model.History;

public interface IClickHistory {
    void onClickHistory(Chapter chapter,Comic comic);
    void onClickDelHistory(Comic comic);
}
