package hung.deptrai.comicapp.api;

import java.util.HashMap;

import hung.deptrai.comicapp.model.Chapter;
import hung.deptrai.comicapp.model.DataJSON;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ChapterService {
    ChapterService chapterService = new Retrofit.Builder().baseUrl("http://192.168.0.109:8080/api/v1/comic/chapter/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ChapterService.class);
    @POST("id")
    Call<DataJSON<Chapter>> getChapterList(@Body HashMap hashMap);

    @POST("chapterID")
    Call<DataJSON<Chapter>> getChapterID(@Body HashMap hashMap);
    @POST("history")
    Call<DataJSON<Chapter>> getChapterHistoryComic(@Body HashMap hashMap);
}
