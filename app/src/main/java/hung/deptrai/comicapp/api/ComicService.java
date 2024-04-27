package hung.deptrai.comicapp.api;

import java.util.HashMap;

import hung.deptrai.comicapp.model.Account;
import hung.deptrai.comicapp.model.Category;
import hung.deptrai.comicapp.model.Comic;
import hung.deptrai.comicapp.model.DataJSON;
import hung.deptrai.comicapp.model.Favourite;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;

public interface ComicService {
    ComicService comicService = new Retrofit.Builder().baseUrl("https://comicapi-production.up.railway.app/api/v1/comic/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ComicService.class);
    @GET("outstanding")
    Call<DataJSON<Comic>> getOutstandingComic();

    @POST("proposal")
    Call<DataJSON<Comic>> getProposalComic(@Body Account account);

    @GET("ranking")
    Call<DataJSON<Comic>> getRankingComic();

    @GET("shounen")
    Call<DataJSON<Comic>> getShounenComic();
    @POST("comicID")
    Call<DataJSON<Comic>> getComicByID(@Body Comic comic);
    @POST("category")
    Call<DataJSON<Comic>> getComicByGenre(@Body Category category);
    @POST("favourite")
    Call<DataJSON<Comic>> getFavouriteComics(@Body Account account);

    @POST("favourite/status")
    Call<DataJSON<Favourite>> getFavouriteStatus(@Body HashMap hashmap);

    @POST("favourite/id")
    Call<DataJSON<Favourite>> getFavouriteID(@Body HashMap hashmap);
    @POST("favourite/add")
    Call<DataJSON<Boolean>> addToFavourite(@Body HashMap hashmap);
    @HTTP(method = "DELETE", path = "favourite/remove", hasBody = true)
    Call<DataJSON<Boolean>> removeFromFavourite(@Body HashMap hashmap);

    @POST("trans")
    Call<DataJSON<Comic>> getComicByTrans(@Body HashMap hashMap);

    @POST("comicName")
    Call<DataJSON<Comic>> getComicSearch(@Body HashMap hashMap);

    @POST("update/views")
    Call<DataJSON<Boolean>> updateViewCount(@Body HashMap hashMap);
}
