package hung.deptrai.comicapp.api;

import hung.deptrai.comicapp.model.Category;
import hung.deptrai.comicapp.model.DataJSON;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface CategoryService {
    CategoryService categoryService = new Retrofit.Builder().baseUrl("http://192.168.0.109:8080/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CategoryService.class);
    @GET("genre/")
    Call<DataJSON<Category>> getAllCategory();
}
