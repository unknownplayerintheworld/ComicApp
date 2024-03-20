package hung.deptrai.comicapp.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import java.net.HttpURLConnection;
import java.util.List;

import hung.deptrai.comicapp.Utils.MessageStatusHTTP;
import hung.deptrai.comicapp.api.ImageChapterService;
import hung.deptrai.comicapp.model.Chapter;
import hung.deptrai.comicapp.model.DataJSON;
import hung.deptrai.comicapp.model.ImageChapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageChapterRepository {
    private Application application;
    private MutableLiveData<List<ImageChapter>> imageList = new MutableLiveData<>();

    public ImageChapterRepository(Application application){
        this.application = application;
    }
    public MutableLiveData<List<ImageChapter>> getImageList(Chapter chapter){
        if(chapter!=null){
            ImageChapterService.imageChapterService.getAllImageByChapter(chapter).enqueue(new Callback<DataJSON<ImageChapter>>() {
                @Override
                public void onResponse(Call<DataJSON<ImageChapter>> call, Response<DataJSON<ImageChapter>> response) {
                    if(response.isSuccessful()) {
                        DataJSON<ImageChapter> dataJSON = response.body();
                        if (dataJSON != null) {
                            if (dataJSON.isStatus()) {
                                List<ImageChapter> imageChapters = dataJSON.getData();
                                imageList.setValue(imageChapters);
                            }
                        }
                    }
                    else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                        Log.e("image image status","Method is not implemented");
                        Toast.makeText(application, MessageStatusHTTP.notImplemented, Toast.LENGTH_SHORT).show();
                    }
                    else if(response.code() == HttpURLConnection.HTTP_INTERNAL_ERROR){
                        Log.e("image comic status","Error server");
                        Toast.makeText(application, MessageStatusHTTP.internalServerError, Toast.LENGTH_SHORT).show();
                    }
                    else if(response.code() == HttpURLConnection.HTTP_BAD_REQUEST){
                        Log.e("image comic status","Bad request,please check argument and param");
                        Toast.makeText(application, MessageStatusHTTP.badGateway, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<DataJSON<ImageChapter>> call, Throwable t) {
                    Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                }
            });
        }
        return imageList;
    }
}
