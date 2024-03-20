package hung.deptrai.comicapp.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hung.deptrai.comicapp.api.ChapterService;
import hung.deptrai.comicapp.model.Category;
import hung.deptrai.comicapp.model.Chapter;
import hung.deptrai.comicapp.model.DataJSON;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChapterRepository {
    private Application application;
    private MutableLiveData<List<Chapter>> chapterList = new MutableLiveData<>();
    private MutableLiveData<List<Chapter>> chapterListID = new MutableLiveData<>();
    private MutableLiveData<List<Chapter>> chapterHistory = new MutableLiveData<>();
    public ChapterRepository(Application application){
        this.application = application;
    }
    public MutableLiveData<List<Chapter>> getChapterList(HashMap<String,String> hashMap){
        ChapterService.chapterService.getChapterList(hashMap).enqueue(new Callback<DataJSON<Chapter>>() {
            @Override
            public void onResponse(Call<DataJSON<Chapter>> call, Response<DataJSON<Chapter>> response) {
                if(response.isSuccessful()) {
                    DataJSON<Chapter> dataJSON = response.body();
                    if (dataJSON != null) {
                        if (dataJSON.isStatus()) {
                            List<Chapter> chapterList1 = dataJSON.getData();
                            chapterList.setValue(chapterList1);
                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Log.e("image comic status","Method is not implemented");
                }
                else if(response.code() == HttpURLConnection.HTTP_INTERNAL_ERROR){
                    Log.e("image comic status","Error server");
                }
                else if(response.code() == HttpURLConnection.HTTP_BAD_REQUEST){
                    Log.e("image comic status","Bad request,please check argument and param");
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Chapter>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return chapterList;
    }
    public MutableLiveData<List<Chapter>> getChapterListID(HashMap<String,String> hashMap){
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Log.d("HashMap Entry", "Key: " + key + ", Value: " + value);
        }
        ChapterService.chapterService.getChapterID(hashMap).enqueue(new Callback<DataJSON<Chapter>>() {
            @Override
            public void onResponse(Call<DataJSON<Chapter>> call, Response<DataJSON<Chapter>> response) {
                if(response.isSuccessful()) {
                    DataJSON<Chapter> dataJSON = response.body();
                    if (dataJSON != null) {
                        if (dataJSON.isStatus()) {
                            List<Chapter> chapterList1 = dataJSON.getData();
                            chapterListID.setValue(chapterList1);
                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Log.e("image comic status","Method is not implemented");
                }
                else if(response.code() == HttpURLConnection.HTTP_INTERNAL_ERROR){
                    Log.e("image comic status","Error server");
                }
                else if(response.code() == HttpURLConnection.HTTP_BAD_REQUEST){
                    Log.e("image comic status","Bad request,please check argument and param");
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Chapter>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return chapterListID;
    }
    public MutableLiveData<List<Chapter>> getChapterHistory(HashMap hashMap){
        ChapterService.chapterService.getChapterHistoryComic(hashMap).enqueue(new Callback<DataJSON<Chapter>>() {
            @Override
            public void onResponse(Call<DataJSON<Chapter>> call, Response<DataJSON<Chapter>> response) {
                if(response.isSuccessful()) {
                    DataJSON<Chapter> dataJSON = response.body();
                    if (dataJSON != null) {
                        if (dataJSON.isStatus()) {
                            List<Chapter> chapterList1 = dataJSON.getData();
                            chapterHistory.setValue(chapterList1);
                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Log.e("image comic status","Method chapterHistory is not implemented");
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Chapter>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return chapterHistory;
    }
}
