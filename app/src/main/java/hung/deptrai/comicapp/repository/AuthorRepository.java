package hung.deptrai.comicapp.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hung.deptrai.comicapp.Utils.MessageStatusHTTP;
import hung.deptrai.comicapp.api.AuthorService;
import hung.deptrai.comicapp.model.Author;
import hung.deptrai.comicapp.model.DataJSON;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorRepository {
    private Application application;
    private MutableLiveData<List<Author>> author = new MutableLiveData<>();
    private MutableLiveData<List<Author>> authorFromID = new MutableLiveData<>();
    private MutableLiveData<List<Author>> authorSearch = new MutableLiveData<>();
    public AuthorRepository(Application application)
    {
        this.application=application;
    }
    public MutableLiveData<List<Author>> getAuthorFromComic(HashMap<String,String> hashMap){
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Log.d("HashMap Entry", "Key: " + key + ", Value: " + value);
        }
        AuthorService.authorservice.getAuthorByComicID(hashMap).enqueue(new Callback<DataJSON<Author>>() {
            @Override
            public void onResponse(Call<DataJSON<Author>> call, Response<DataJSON<Author>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()){
                    if(dataJSON!=null){
                        List<Author> authorList = dataJSON.getData();
                        if(authorList!=null){
                            author.setValue(authorList);
                        }
                    }
                } else if (response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED) {
                    Log.e("Status author repository:", MessageStatusHTTP.notImplemented);
                    Toast.makeText(application.getApplicationContext(), "This feature isn't implemented", Toast.LENGTH_SHORT).show();
                }
                else if (response.code() == HttpURLConnection.HTTP_BAD_REQUEST) {
                    Log.e("Status author repository:", MessageStatusHTTP.badGateway);
                    Toast.makeText(application.getApplicationContext(), "Bad request!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Author>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return author;
    }
    public MutableLiveData<List<Author>> getAuthorByID(HashMap<String,String> hashMap){
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Log.d("HashMap Entry", "Key: " + key + ", Value: " + value);
        }
        AuthorService.authorservice.getAuthorByID(hashMap).enqueue(new Callback<DataJSON<Author>>() {
            @Override
            public void onResponse(Call<DataJSON<Author>> call, Response<DataJSON<Author>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()){
                    if(dataJSON!=null){
                        List<Author> authorList = dataJSON.getData();
                        if(authorList!=null){
                            authorFromID.setValue(authorList);
                        }
                    }
                } else if (response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED) {
                    Log.e("Status author repository:", MessageStatusHTTP.notImplemented);
                    Toast.makeText(application.getApplicationContext(), "This feature isn't implemented", Toast.LENGTH_SHORT).show();
                }
                else if (response.code() == HttpURLConnection.HTTP_BAD_REQUEST) {
                    Log.e("Status author repository:", MessageStatusHTTP.badGateway);
                    Toast.makeText(application.getApplicationContext(), "Bad request!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Author>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return authorFromID;
    }
    public MutableLiveData<List<Author>> getAuthorSearch(HashMap<String,String> hashMap){
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Log.d("HashMap Entry", "Key: " + key + ", Value: " + value);
        }
        AuthorService.authorservice.getTransSearch(hashMap).enqueue(new Callback<DataJSON<Author>>() {
            @Override
            public void onResponse(Call<DataJSON<Author>> call, Response<DataJSON<Author>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()){
                    if(dataJSON!=null){
                        List<Author> authorList = dataJSON.getData();
                        if(authorList!=null){
                            authorSearch.setValue(authorList);
                        }
                    }
                } else if (response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED) {
                    Log.e("Status author repository:", MessageStatusHTTP.notImplemented);
                    Toast.makeText(application.getApplicationContext(), "This feature isn't implemented", Toast.LENGTH_SHORT).show();
                }
                else if (response.code() == HttpURLConnection.HTTP_BAD_REQUEST) {
                    Log.e("Status author repository:", MessageStatusHTTP.badGateway);
                    Toast.makeText(application.getApplicationContext(), "Bad request!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Author>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return authorSearch;
    }
}
