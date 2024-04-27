package hung.deptrai.comicapp.api;

import hung.deptrai.comicapp.model.Chapter;
import hung.deptrai.comicapp.model.DataJSON;
import hung.deptrai.comicapp.model.ImageChapter;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ImageChapterService {
    ImageChapterService imageChapterService = new Retrofit.Builder().baseUrl("https://comicapi-production.up.railway.app/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ImageChapterService.class);
    @POST("imagechapter/")
    Call<DataJSON<ImageChapter>> getAllImageByChapter(@Body Chapter chapter);
}
