package hung.deptrai.comicapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.HashMap;
import java.util.List;

import hung.deptrai.comicapp.model.Account;
import hung.deptrai.comicapp.model.Category;
import hung.deptrai.comicapp.model.Comic;
import hung.deptrai.comicapp.model.Favourite;
import hung.deptrai.comicapp.repository.ComicRepository;
import hung.deptrai.comicapp.repository.HistoryRepository;

public class ComicViewModel extends AndroidViewModel {
    private ComicRepository comicRepository;
    private HistoryRepository historyRepository;
    public ComicViewModel(@NonNull Application application) {
        super(application);
        comicRepository = new ComicRepository(application);
        historyRepository = new HistoryRepository(application);
    }
    public LiveData<List<Comic>> getOutstanding(){
        return comicRepository.getOutstandingComic();
    }
    public LiveData<List<Comic>> getComicByID(Comic comic){
        return comicRepository.getComicByID(comic);
    }
    public LiveData<List<Comic>> getComicByGenre(Category category){
        return comicRepository.getComicByGenre(category);
    }
    public LiveData<List<Comic>> getFavouriteComics(Account account){
        return comicRepository.getFavouriteComics(account);
    }
    public LiveData<List<Favourite>> getFavouriteStatus(HashMap hashMap){
        return comicRepository.getFavouriteStatus(hashMap);
    }
    public LiveData<List<Favourite>> getFavouriteID(HashMap hashMap){
        return comicRepository.getFavouriteID(hashMap);
    }
    public LiveData<List<Boolean>> addToFavourite(HashMap hashMap){
        return comicRepository.addToFav(hashMap);
    }
    public LiveData<List<Boolean>> removeFromFavourite(HashMap hashMap){
        return comicRepository.removeFromFav(hashMap);
    }
    public LiveData<List<Comic>> getProposal(Account account){
        return comicRepository.getProposalComic(account);
    }
    public LiveData<List<Comic>> getComicByTransteamID(HashMap hashMap){
        return comicRepository.getComicByTransteamID(hashMap);
    }
    public LiveData<List<Comic>> getRank(){
        return comicRepository.getRankingComic();
    }
    public LiveData<List<Comic>> getShounenComic(){
        return comicRepository.getShounenComic();
    }
    public LiveData<List<Comic>> getComicHistory(HashMap hashMap){
        return historyRepository.getComicHistory(hashMap);
    }
    public LiveData<List<Comic>> getComicSearch(HashMap hashMap){
        return comicRepository.getComicSearch(hashMap);
    }
}
