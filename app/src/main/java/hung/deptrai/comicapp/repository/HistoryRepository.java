package hung.deptrai.comicapp.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hung.deptrai.comicapp.Utils.MessageStatusHTTP;
import hung.deptrai.comicapp.api.ComicService;
import hung.deptrai.comicapp.api.HistoryService;
import hung.deptrai.comicapp.model.Author;
import hung.deptrai.comicapp.model.Chapter;
import hung.deptrai.comicapp.model.Comic;
import hung.deptrai.comicapp.model.DataJSON;
import hung.deptrai.comicapp.model.History;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryRepository {
    private Application application;
    private MutableLiveData<List<Comic>> comicHistory = new MutableLiveData<>();
    private MutableLiveData<List<Chapter>> chapterHistory = new MutableLiveData<>();
    private MutableLiveData<List<History>> historyID = new MutableLiveData<>();
    private MutableLiveData<List<Author>> authorHistory = new MutableLiveData<>();
    private MutableLiveData<List<Boolean>> removeFromHistoryStatus = new MutableLiveData<>();
    private MutableLiveData<List<Boolean>> upsertHistoryStatus = new MutableLiveData<>();
    public HistoryRepository(Application application){
        this.application = application;
    }
    public MutableLiveData<List<History>> getHistoryID(HashMap<String,String> hashMap){
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Log.d("HashMap Entry", "Key: " + key + ", Value: " + value);
        }
        HistoryService.historyService.getHistoryID(hashMap).enqueue(new Callback<DataJSON<History>>() {
            @Override
            public void onResponse(Call<DataJSON<History>> call, Response<DataJSON<History>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()) {
                    if(dataJSON!=null){
                        if(dataJSON.isStatus() == true){
                            List<History> comics = dataJSON.getData();
                            historyID.setValue(comics);
                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Log.e("Status historyID repository:", MessageStatusHTTP.notImplemented);
                }
                else if(response.code() == HttpURLConnection.HTTP_INTERNAL_ERROR){
                    Log.e("Status historyID repository:", MessageStatusHTTP.internalServerError);
                }
            }

            @Override
            public void onFailure(Call<DataJSON<History>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return historyID;
    }
    public MutableLiveData<List<Boolean>> getRemoveFromHistoryStatus(HashMap<String,String> hashMap){
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Log.d("HashMap Entry", "Key: " + key + ", Value: " + value);
        }
        HistoryService.historyService.removeFromHistory(hashMap).enqueue(new Callback<DataJSON<Boolean>>() {
            @Override
            public void onResponse(Call<DataJSON<Boolean>> call, Response<DataJSON<Boolean>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()) {
                    if(dataJSON!=null){
                        if(dataJSON.isStatus() == true){
                            List<Boolean> favStatus = dataJSON.getData();
                            if(favStatus!=null){
                                removeFromHistoryStatus.setValue(favStatus);
                            }
                        }
                        else{
                            // do api trên server,nếu thành công remove thì sẽ trả ra status = true
                            // còn ko thì false,và phần obj List sẽ ko có gì
                            // thành công thì obj List sẽ cx trả ra thêm 1 List<boolean> là true nữa
                            // thực ra là ko cần thiết,hơi thừa nhưng thêm vào cho chắc,nên nhìn nó mới hài hài như này
                            List<Boolean> st = new ArrayList<>();
                            st.add(false);
                            removeFromHistoryStatus.setValue(st);
                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Log.e("Status favourites remove repository:", MessageStatusHTTP.notImplemented);
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Boolean>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return removeFromHistoryStatus;
    }
    public MutableLiveData<List<Comic>> getComicHistory(HashMap<String,String> hashMap){
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Log.d("HashMap Entry get comic history", "Key: " + key + ", Value: " + value);
        }
        HistoryService.historyService.getAllComicHistory(hashMap).enqueue(new Callback<DataJSON<Comic>>() {
            @Override
            public void onResponse(Call<DataJSON<Comic>> call, Response<DataJSON<Comic>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()) {
                    if(dataJSON!=null){
                        if(dataJSON.isStatus() == true){
                            List<Comic> comics = dataJSON.getData();
                            comicHistory.setValue(comics);
                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Log.e("Status favourites remove repository:", MessageStatusHTTP.notImplemented);
                }
                else if(response.code() == HttpURLConnection.HTTP_INTERNAL_ERROR){
                    Log.e("Status favourites remove repository:", MessageStatusHTTP.internalServerError);
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Comic>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return comicHistory;
    }
    public MutableLiveData<List<Chapter>> getChapterHistory(HashMap<String,String> hashMap){
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Log.d("HashMap Entry chapter history", "Key: " + key + ", Value: " + value);
        }
        HistoryService.historyService.getAllChapterHistory(hashMap).enqueue(new Callback<DataJSON<Chapter>>() {
            @Override
            public void onResponse(Call<DataJSON<Chapter>> call, Response<DataJSON<Chapter>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()) {
                    if(dataJSON!=null){
                        if(dataJSON.isStatus() == true){
                            List<Chapter> comics = dataJSON.getData();
                            chapterHistory.setValue(comics);
                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Log.e("Status favourites remove repository:", MessageStatusHTTP.notImplemented);
                }
                else if(response.code() == HttpURLConnection.HTTP_INTERNAL_ERROR){
                    Log.e("Status favourites remove repository:", MessageStatusHTTP.internalServerError);
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
    public MutableLiveData<List<Author>> getAuthorHistory(HashMap<String,String> hashMap){
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Log.d("HashMap Entry author history", "Key: " + key + ", Value: " + value);
        }
        HistoryService.historyService.getAllTransHistory(hashMap).enqueue(new Callback<DataJSON<Author>>() {
            @Override
            public void onResponse(Call<DataJSON<Author>> call, Response<DataJSON<Author>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()) {
                    if(dataJSON!=null){
                        if(dataJSON.isStatus() == true){
                            List<Author> comics = dataJSON.getData();
                            authorHistory.setValue(comics);
                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Log.e("Status favourites remove repository:", MessageStatusHTTP.notImplemented);
                }
                else if(response.code() == HttpURLConnection.HTTP_INTERNAL_ERROR){
                    Log.e("Status favourites remove repository:", MessageStatusHTTP.internalServerError);
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Author>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return authorHistory;
    }
    public MutableLiveData<List<Boolean>> upsertHistory(HashMap<String,String> hashMap){
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Log.d("HashMap Entry author history", "Key: " + key + ", Value: " + value);
        }
        HistoryService.historyService.upsertHistory(hashMap).enqueue(new Callback<DataJSON<Boolean>>() {
            @Override
            public void onResponse(Call<DataJSON<Boolean>> call, Response<DataJSON<Boolean>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()) {
                    if(dataJSON!=null){
                        if(dataJSON.isStatus() == true){
                            List<Boolean> comics = dataJSON.getData();
                            upsertHistoryStatus.setValue(comics);
                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Log.e("Status favourites remove repository:", MessageStatusHTTP.notImplemented);
                }
                else if(response.code() == HttpURLConnection.HTTP_INTERNAL_ERROR){
                    Log.e("Status favourites remove repository:", response.message());
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Boolean>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return upsertHistoryStatus;
    }
}
