package hung.deptrai.comicapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.HashMap;
import java.util.List;

import hung.deptrai.comicapp.model.Account;
import hung.deptrai.comicapp.model.Chapter;
import hung.deptrai.comicapp.model.Comment;
import hung.deptrai.comicapp.repository.CommentRepository;

public class CommentViewModel extends AndroidViewModel {
    private CommentRepository commentRepository;
    public CommentViewModel(@NonNull Application application) {
        super(application);
        commentRepository = new CommentRepository(application);
    }
    public LiveData<List<Comment>> getAllRootCommentInChapter(HashMap hashMap){
        return commentRepository.getRootCommentsChapter(hashMap);
    }
    public LiveData<List<Comment>> getAllRootCommentInChapterByReact(HashMap hashMap){
        return commentRepository.getRootCommentByReact(hashMap);
    }
    public LiveData<List<Comment>> getAllRootCommentInChapterByTime(HashMap hashMap){
        return commentRepository.getRootCommentByTime(hashMap);
    }
    public LiveData<List<Comment>> getAllRootCommentInComicByReact(HashMap hashMap){
        return commentRepository.getRootCommentComicByReact(hashMap);
    }
    public LiveData<List<Comment>> getAllRootCommentInComicByTime(HashMap hashMap){
        return commentRepository.getRootCommentComicByTime(hashMap);
    }
    public LiveData<List<Comment>> getAllRootCommentInComic(HashMap hashMap){
        return commentRepository.getRootCommentsComic(hashMap);
    }
    public LiveData<List<Integer>> getChildCommentCount(HashMap hashMap){
        return commentRepository.getChildCommentCount(hashMap);
    }
    public LiveData<List<Integer>> getChildCommentIDCount(HashMap hashMap){
        return commentRepository.getChildCommentIDCount(hashMap);
    }
    public LiveData<List<Integer>> getChildCommentCountComic(HashMap hashMap){
        return commentRepository.getChildCommentCountComic(hashMap);
    }
    public LiveData<List<Integer>> getChildCommentIDCountComic(HashMap hashMap){
        return commentRepository.getChildCommentIDCountComic(hashMap);
    }
    public LiveData<List<Comment>> getAllChildComment(HashMap hashMap){
        return commentRepository.getChildComment(hashMap);
    }
    public LiveData<List<Account>> getAccountRootChapter(HashMap hashMap){
        return commentRepository.getAccountRootChapters(hashMap);
    }
    public LiveData<List<Account>> getAccountRootChapterByReact(HashMap hashMap){
        return commentRepository.getAccountRootCommentByReact(hashMap);
    }
    public LiveData<List<Account>> getAccountRootChapterByTime(HashMap hashMap){
        return commentRepository.getAccountRootCommentByTime(hashMap);
    }
    public LiveData<List<Account>> getAccountRootComicByReact(HashMap hashMap){
        return commentRepository.getAccountRootCommentComicByReact(hashMap);
    }
    public LiveData<List<Account>> getAccountRootComicByTime(HashMap hashMap){
        return commentRepository.getAccountRootCommentComicByReact(hashMap);
    }
    public LiveData<List<Account>> getAccountRootComics(HashMap hashMap){
        return commentRepository.getAccountRootComics(hashMap);
    }
    public LiveData<List<Account>> getAccountChild(HashMap hashMap){
        return commentRepository.getAccountChild(hashMap);
    }
    public LiveData<Boolean> writeComment(HashMap hashMap){
        return commentRepository.writeComment(hashMap);
    }
    public LiveData<Boolean> updateLODInc(HashMap hashMap){
        return commentRepository.getUpdateLODIncStatus(hashMap);
    }
    public LiveData<Boolean> updateLODDesc(HashMap hashMap){
        return commentRepository.getUpdateLODDescStatus(hashMap);
    }
    public LiveData<List<Integer>> checkAccountLOD(HashMap hashMap){
        return commentRepository.checkAccountLOD(hashMap);
    }
    public LiveData<List<Boolean>> checkLODTypeLOD(HashMap hashMap){
        return commentRepository.checkLODTypeLOD(hashMap);
    }
    public LiveData<List<Integer>> checkCommentIDLOD(HashMap hashMap){
        return commentRepository.checkCommentIDLOD(hashMap);
    }
    public LiveData<List<Integer>> checkAccountLODComic(HashMap hashMap){
        return commentRepository.getCheckAccountLODComic(hashMap);
    }
    public LiveData<List<Boolean>> checkLODTypeLODComic(HashMap hashMap){
        return commentRepository.getCheckLODTypeLODComic(hashMap);
    }
    public LiveData<List<Integer>> checkCommentIDLODComic(HashMap hashMap){
        return commentRepository.getCheckCommentIDLODComic(hashMap);
    }
    public LiveData<List<Integer>> checkCommentWriteIDLODComic(HashMap hashMap){
        return commentRepository.checkCommentWriteIDLOD(hashMap);
    }
    public LiveData<List<Boolean>> removeComment(HashMap hashMap){
        return commentRepository.deleteComment(hashMap);
    }
    public LiveData<Boolean> replyComment(HashMap hashMap){
        return commentRepository.replyComment(hashMap);
    }
    public LiveData<List<Comment>> getCommentByCommentID(HashMap hashMap){
        return commentRepository.getCommentByCommentID(hashMap);
    }
    public LiveData<List<Account>> getAccountByCommentID(HashMap hashMap){
        return commentRepository.getAccountByCommentID(hashMap);
    }
    public LiveData<List<Chapter>> getChapterCommentComic(HashMap hashMap){
        return commentRepository.getChapterCommentComic(hashMap);
    }
    public LiveData<List<Chapter>> getChapterCommentComicReact(HashMap hashMap){
        return commentRepository.getChapterCommentComicReact(hashMap);
    }
}
