package hung.deptrai.comicapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.HashMap;
import java.util.List;

import hung.deptrai.comicapp.model.Chapter;
import hung.deptrai.comicapp.repository.ChapterRepository;
import hung.deptrai.comicapp.repository.HistoryRepository;

public class ChapterViewModel extends AndroidViewModel {
    private ChapterRepository chapterRepository;
    private HistoryRepository historyRepository;
    public ChapterViewModel(@NonNull Application application) {
        super(application);
        chapterRepository = new ChapterRepository(application);
        historyRepository = new HistoryRepository(application);
    }
    public LiveData<List<Chapter>> getChapterList(HashMap hashMap){
        return chapterRepository.getChapterList(hashMap);
    }
    public LiveData<List<Chapter>> getChapterHistory(HashMap hashMap){
        return historyRepository.getChapterHistory(hashMap);
    }
    public LiveData<List<Chapter>> getChapterID(HashMap hashMap){
        return chapterRepository.getChapterListID(hashMap);
    }
    public LiveData<List<Chapter>> getChapterHistoryComic(HashMap hashMap){
        return chapterRepository.getChapterHistory(hashMap);
    }
}
