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
import hung.deptrai.comicapp.model.Account;
import hung.deptrai.comicapp.model.Category;
import hung.deptrai.comicapp.model.Comic;
import hung.deptrai.comicapp.model.DataJSON;
import hung.deptrai.comicapp.model.Favourite;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComicRepository {
    private Application application;
    private MutableLiveData<List<Comic>> comicByID = new MutableLiveData<>();
    private MutableLiveData<List<Comic>> outstandingComic = new MutableLiveData<>();
    private MutableLiveData<List<Comic>> proposalComic = new MutableLiveData<>();
    private MutableLiveData<List<Comic>> rankingComic = new MutableLiveData<>();
    private MutableLiveData<List<Comic>> shounenComic = new MutableLiveData<>();
    private MutableLiveData<List<Comic>> comicByGenre = new MutableLiveData<>();
    private MutableLiveData<List<Comic>> favouritecomics = new MutableLiveData<>();
    private MutableLiveData<Boolean> favouritestatus = new MutableLiveData<>();
    private MutableLiveData<List<Favourite>> favouriteMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Favourite>> favID = new MutableLiveData<>();
    private MutableLiveData<List<Boolean>> addToFavStatus = new MutableLiveData<>();
    private MutableLiveData<List<Boolean>> removeFromFavStatus = new MutableLiveData<>();
    private MutableLiveData<List<Comic>> comicByTrans = new MutableLiveData<>();

    private MutableLiveData<List<Comic>> comicSearch = new MutableLiveData<>();
    public ComicRepository(){

    }
    public ComicRepository(Application application){
        this.application = application;
    }
    public MutableLiveData<List<Comic>> getOutstandingComic(){
        ComicService.comicService.getOutstandingComic().enqueue(new Callback<DataJSON<Comic>>() {
            @Override
            public void onResponse(Call<DataJSON<Comic>> call, Response<DataJSON<Comic>> response) {
                DataJSON<Comic> dataJSON = response.body();
                if(response.isSuccessful()) {
                    if (dataJSON != null) {
                        if (dataJSON.isStatus() == true) {
                            List<Comic> comics = dataJSON.getData();
                            Log.e("Status outstanding:","");
                            outstandingComic.setValue(comics);
                        } else {

                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Log.e("Status outstanding:", MessageStatusHTTP.notImplemented);
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Comic>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
            }
        });
        return outstandingComic;
    }
    public MutableLiveData<List<Comic>> getProposalComic(Account account){
        ComicService.comicService.getProposalComic(account).enqueue(new Callback<DataJSON<Comic>>() {
            @Override
            public void onResponse(Call<DataJSON<Comic>> call, Response<DataJSON<Comic>> response) {
                DataJSON<Comic> dataJSON = response.body();
                if(response.isSuccessful()) {
                    if (dataJSON != null) {
                        if (dataJSON.isStatus() == true) {
                            List<Comic> comics = dataJSON.getData();
                            proposalComic.setValue(comics);
                        } else {

                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Log.e("Status proposal:", MessageStatusHTTP.notImplemented);
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Comic>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
            }
        });
        return proposalComic;
    }

    public MutableLiveData<List<Comic>> getRankingComic(){
        ComicService.comicService.getRankingComic().enqueue(new Callback<DataJSON<Comic>>() {
            @Override
            public void onResponse(Call<DataJSON<Comic>> call, Response<DataJSON<Comic>> response) {
                DataJSON<Comic> dataJSON = response.body();
                if(response.isSuccessful()) {
                    if (dataJSON != null) {
                        if (dataJSON.isStatus() == true) {
                            List<Comic> comics = dataJSON.getData();
                            rankingComic.setValue(comics);
                        } else {

                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Log.e("Status ranking:", MessageStatusHTTP.notImplemented);
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Comic>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
            }
        });
        return rankingComic;
    }
    public MutableLiveData<List<Comic>> getShounenComic(){
        ComicService.comicService.getShounenComic().enqueue(new Callback<DataJSON<Comic>>() {
            @Override
            public void onResponse(Call<DataJSON<Comic>> call, Response<DataJSON<Comic>> response) {
                DataJSON<Comic> dataJSON = response.body();
                if(response.isSuccessful()) {
                    if (dataJSON != null) {
                        if (dataJSON.isStatus() == true) {
                            List<Comic> comics = dataJSON.getData();
                            shounenComic.setValue(comics);
                        } else {

                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Log.e("Status shounen:", MessageStatusHTTP.notImplemented);
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Comic>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
            }
        });
        return shounenComic;
    }
    public MutableLiveData<List<Comic>> getComicByID(Comic comic){
        ComicService.comicService.getComicByID(comic).enqueue(new Callback<DataJSON<Comic>>() {
            @Override
            public void onResponse(Call<DataJSON<Comic>> call, Response<DataJSON<Comic>> response) {
                DataJSON<Comic> dataJSON = response.body();
                if(response.isSuccessful()) {
                    if (dataJSON != null) {
                        if (dataJSON.isStatus() == true) {
                            List<Comic> comics = dataJSON.getData();
                            comicByID.setValue(comics);
                        } else {

                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Log.e("Status comci by ID:", MessageStatusHTTP.notImplemented);
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Comic>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
            }
        });
        return comicByID;
    }
    public MutableLiveData<List<Comic>> getComicByGenre(Category category){
        ComicService.comicService.getComicByGenre(category).enqueue(new Callback<DataJSON<Comic>>() {
            @Override
            public void onResponse(Call<DataJSON<Comic>> call, Response<DataJSON<Comic>> response) {
                DataJSON<Comic> dataJSON = response.body();
                if(response.isSuccessful()) {
                    if (dataJSON != null) {
                        if (dataJSON.isStatus() == true) {
                            List<Comic> comics = dataJSON.getData();
                            comicByGenre.setValue(comics);
                        } else {

                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Log.e("Status comics by genre:", MessageStatusHTTP.notImplemented);
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Comic>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
            }
        });
        return comicByGenre;
    }
    public MutableLiveData<List<Comic>> getFavouriteComics(Account account){
        ComicService.comicService.getFavouriteComics(account).enqueue(new Callback<DataJSON<Comic>>() {
            @Override
            public void onResponse(Call<DataJSON<Comic>> call, Response<DataJSON<Comic>> response) {
                DataJSON<Comic> dataJSON = response.body();
                if(response.isSuccessful()) {
                    if (dataJSON != null) {
                        if (dataJSON.isStatus() == true) {
                            List<Comic> comics = dataJSON.getData();
                            favouritecomics.setValue(comics);
                        } else {

                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Log.e("Status comics by genre:", MessageStatusHTTP.notImplemented);
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Comic>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
            }
        });
        return favouritecomics;
    }
    public MutableLiveData<List<Favourite>> getFavouriteStatus(HashMap<String,String> hashMap){
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Log.d("HashMap Entry", "Key: " + key + ", Value: " + value);
        }
        ComicService.comicService.getFavouriteStatus(hashMap).enqueue(new Callback<DataJSON<Favourite>>() {
            @Override
            public void onResponse(Call<DataJSON<Favourite>> call, Response<DataJSON<Favourite>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()) {
                    if(dataJSON!=null){
                        if(dataJSON.isStatus() == true){
                            List<Favourite> favourites = dataJSON.getData();
                            if(favourites!=null){
                                favouriteMutableLiveData.setValue(favourites);
                            }
                            else{

                            }
                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Log.e("Status comics by genre:", MessageStatusHTTP.notImplemented);
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Favourite>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return favouriteMutableLiveData;
    }
    public MutableLiveData<List<Favourite>> getFavouriteID(HashMap<String,String> hashMap){
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Log.d("HashMap Entry", "Key: " + key + ", Value: " + value);
        }
        ComicService.comicService.getFavouriteID(hashMap).enqueue(new Callback<DataJSON<Favourite>>() {
            @Override
            public void onResponse(Call<DataJSON<Favourite>> call, Response<DataJSON<Favourite>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()) {
                    if(dataJSON!=null){
                        if(dataJSON.isStatus() == true){
                            List<Favourite> favourites = dataJSON.getData();
                            if(favourites!=null){
                                favID.setValue(favourites);
                            }
                            else{

                            }
                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Log.e("Status favourites repository:", MessageStatusHTTP.notImplemented);
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Favourite>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return favID;
    }
    public MutableLiveData<List<Boolean>> addToFav(HashMap<String,String> hashMap){
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Log.d("HashMap Entry", "Key: " + key + ", Value: " + value);
        }
        ComicService.comicService.addToFavourite(hashMap).enqueue(new Callback<DataJSON<Boolean>>() {
            @Override
            public void onResponse(Call<DataJSON<Boolean>> call, Response<DataJSON<Boolean>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()) {
                    if(dataJSON!=null){
                        if(dataJSON.isStatus() == true){
                            List<Boolean> favStatus = dataJSON.getData();
                            if(favStatus!=null){
                                addToFavStatus.setValue(favStatus);
                            }
                        }
                        else{
                            // do api trên server,nếu thành công add thì sẽ trả ra status = true
                            // còn ko thì false,và phần obj List sẽ ko có gì
                            // thành công thì obj List sẽ cx trả ra thêm 1 List<boolean> là true nữa
                            // thực ra là ko cần thiết,hơi thừa nhưng thêm vào cho chắc,nên nhìn nó mới hài hài như này
                            List<Boolean> st = new ArrayList<>();
                            st.add(false);
                            addToFavStatus.setValue(st);
                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Log.e("Status favourites add repository:", MessageStatusHTTP.notImplemented);
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Boolean>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return addToFavStatus;
    }
    public MutableLiveData<List<Boolean>> removeFromFav(HashMap<String,String> hashMap){
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Log.d("HashMap Entry", "Key: " + key + ", Value: " + value);
        }
        ComicService.comicService.removeFromFavourite(hashMap).enqueue(new Callback<DataJSON<Boolean>>() {
            @Override
            public void onResponse(Call<DataJSON<Boolean>> call, Response<DataJSON<Boolean>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()) {
                    if(dataJSON!=null){
                        if(dataJSON.isStatus() == true){
                            List<Boolean> favStatus = dataJSON.getData();
                            if(favStatus!=null){
                                removeFromFavStatus.setValue(favStatus);
                            }
                        }
                        else{
                            // do api trên server,nếu thành công remove thì sẽ trả ra status = true
                            // còn ko thì false,và phần obj List sẽ ko có gì
                            // thành công thì obj List sẽ cx trả ra thêm 1 List<boolean> là true nữa
                            // thực ra là ko cần thiết,hơi thừa nhưng thêm vào cho chắc,nên nhìn nó mới hài hài như này
                            List<Boolean> st = new ArrayList<>();
                            st.add(false);
                            removeFromFavStatus.setValue(st);
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
        return removeFromFavStatus;
    }
    public MutableLiveData<List<Comic>> getComicByTransteamID(HashMap<String,String> hashMap){
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Log.d("HashMap Entry", "Key: " + key + ", Value: " + value);
        }
        ComicService.comicService.getComicByTrans(hashMap).enqueue(new Callback<DataJSON<Comic>>() {
            @Override
            public void onResponse(Call<DataJSON<Comic>> call, Response<DataJSON<Comic>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()) {
                    if(dataJSON!=null){
                        if(dataJSON.isStatus() == true){
                            List<Comic> comics = dataJSON.getData();
                            comicByTrans.setValue(comics);
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
        return comicByTrans;
    }
    public MutableLiveData<List<Comic>> getComicSearch(HashMap<String,String> hashMap){
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Log.d("HashMap Entry", "Key: " + key + ", Value: " + value);
        }
        ComicService.comicService.getComicSearch(hashMap).enqueue(new Callback<DataJSON<Comic>>() {
            @Override
            public void onResponse(Call<DataJSON<Comic>> call, Response<DataJSON<Comic>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()) {
                    if(dataJSON!=null){
                        if(dataJSON.isStatus() == true){
                            List<Comic> comics = dataJSON.getData();
                            comicSearch.setValue(comics);
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
        return comicSearch;
    }
}
