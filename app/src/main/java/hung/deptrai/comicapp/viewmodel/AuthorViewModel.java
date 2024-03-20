package hung.deptrai.comicapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.HashMap;
import java.util.List;

import hung.deptrai.comicapp.model.Author;
import hung.deptrai.comicapp.repository.AuthorRepository;
import hung.deptrai.comicapp.repository.HistoryRepository;

public class AuthorViewModel extends AndroidViewModel {
    private AuthorRepository authorRepository;
    private HistoryRepository historyRepository;
    public AuthorViewModel(@NonNull Application application) {
        super(application);
        authorRepository = new AuthorRepository(application);
        historyRepository = new HistoryRepository(application);
    }
    public LiveData<List<Author>> getAuthorFromComic(HashMap hashMap){
        return authorRepository.getAuthorFromComic(hashMap);
    }
    public LiveData<List<Author>> getAuthorFromID(HashMap hashMap){
        return authorRepository.getAuthorByID(hashMap);
    }
    public LiveData<List<Author>> getAuthorHistory(HashMap hashMap){
        return historyRepository.getAuthorHistory(hashMap);
    }
    public LiveData<List<Author>> getAuthorSearch(HashMap hashMap){
        return authorRepository.getAuthorSearch(hashMap);
    }
}
