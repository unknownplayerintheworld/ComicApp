package hung.deptrai.comicapp.views.Interface;

import hung.deptrai.comicapp.model.Chapter;
import hung.deptrai.comicapp.model.Comment;

public interface IClickComicReply {
    void onClickReply(Comment comment, Chapter chapter);
    void onClickCountReply(Comment comment, Chapter chapter);
}
