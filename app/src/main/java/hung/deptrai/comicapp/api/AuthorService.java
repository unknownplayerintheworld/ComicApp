package hung.deptrai.comicapp.api;

import java.util.HashMap;

import hung.deptrai.comicapp.model.Author;
import hung.deptrai.comicapp.model.Comic;
import hung.deptrai.comicapp.model.DataJSON;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthorService {
    AuthorService authorservice = new Retrofit.Builder().baseUrl("http://192.168.0.109:8080/api/v1/trans/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthorService.class);

    @POST("comic")
    Call<DataJSON<Author>> getAuthorByComicID(@Body HashMap hashMap);

    @POST("id")
    Call<DataJSON<Author>> getAuthorByID(@Body HashMap hashMap);

    @POST("transName")
    Call<DataJSON<Author>> getTransSearch(@Body HashMap hashMap);
}
