package hung.deptrai.comicapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.HashMap;
import java.util.List;

import hung.deptrai.comicapp.model.History;
import hung.deptrai.comicapp.repository.HistoryRepository;

public class HistoryViewModel extends AndroidViewModel {
    private HistoryRepository historyRepository;
    public HistoryViewModel(@NonNull Application application) {
        super(application);
        historyRepository = new HistoryRepository(application);
    }
    public LiveData<List<Boolean>> removeFromHistory(HashMap hashMap){
        return historyRepository.getRemoveFromHistoryStatus(hashMap);
    }
    public LiveData<List<History>> getHistoryID(HashMap hashMap){
        return historyRepository.getHistoryID(hashMap);
    }
    public LiveData<List<Boolean>> upsertHistory(HashMap hashMap){
        return historyRepository.upsertHistory(hashMap);
    }
}
