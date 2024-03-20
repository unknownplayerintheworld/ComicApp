package hung.deptrai.comicapp.views.Interface;

import hung.deptrai.comicapp.model.Account;
import hung.deptrai.comicapp.model.Comment;

public interface IClickComment {
    void onClickReply(Comment comment);
    void onClickCountReply(Comment comment);
    void onClickLikeBtn(Comment comment);
    void onClickDisLikeBtn(Comment comment);
    void onLongClickComment(Comment comment);
}
