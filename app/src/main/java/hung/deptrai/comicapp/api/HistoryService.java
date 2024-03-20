package hung.deptrai.comicapp.api;

import java.util.HashMap;

import hung.deptrai.comicapp.model.Account;
import hung.deptrai.comicapp.model.Author;
import hung.deptrai.comicapp.model.Chapter;
import hung.deptrai.comicapp.model.Comic;
import hung.deptrai.comicapp.model.DataJSON;
import hung.deptrai.comicapp.model.History;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.HTTP;
import retrofit2.http.POST;

public interface HistoryService {
    HistoryService historyService = new Retrofit.Builder().baseUrl("http://192.168.0.109:8080/api/v1/history/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HistoryService.class);
    @POST("comic")
    Call<DataJSON<Comic>> getAllComicHistory(@Body HashMap hashMap);
    @POST("trans")
    Call<DataJSON<Author>> getAllTransHistory(@Body HashMap hashMap);
    @POST("chapter")
    Call<DataJSON<Chapter>> getAllChapterHistory(@Body HashMap hashMap);

    @HTTP(method = "DELETE", path = "remove", hasBody = true)
    Call<DataJSON<Boolean>> removeFromHistory(@Body HashMap hashMap);
    @POST("id")
    Call<DataJSON<History>> getHistoryID(@Body HashMap hashMap);

    @POST("put")
    Call<DataJSON<Boolean>> upsertHistory(@Body HashMap hashMap);
}
