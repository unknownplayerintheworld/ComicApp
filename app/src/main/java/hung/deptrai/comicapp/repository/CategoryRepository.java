package hung.deptrai.comicapp.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.net.HttpURLConnection;
import java.util.List;

import hung.deptrai.comicapp.api.CategoryService;
import hung.deptrai.comicapp.model.Category;
import hung.deptrai.comicapp.model.DataJSON;
import hung.deptrai.comicapp.model.ImageChapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {
    private MutableLiveData<List<Category>> categoryList = new MutableLiveData<>();
    public MutableLiveData<List<Category>> getAllCategory(){
        CategoryService.categoryService.getAllCategory().enqueue(new Callback<DataJSON<Category>>() {
            @Override
            public void onResponse(Call<DataJSON<Category>> call, Response<DataJSON<Category>> response) {
                if(response.isSuccessful()) {
                    DataJSON<Category> dataJSON = response.body();
                    if (dataJSON != null) {
                        if (dataJSON.isStatus()) {
                            List<Category> imageChapters = dataJSON.getData();
                            categoryList.setValue(imageChapters);
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
            public void onFailure(Call<DataJSON<Category>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
            }
        });
        return categoryList;
    }
}
