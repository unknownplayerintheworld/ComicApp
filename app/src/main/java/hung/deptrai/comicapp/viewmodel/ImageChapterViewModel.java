package hung.deptrai.comicapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import hung.deptrai.comicapp.model.Chapter;
import hung.deptrai.comicapp.model.ImageChapter;
import hung.deptrai.comicapp.repository.ImageChapterRepository;

public class ImageChapterViewModel extends AndroidViewModel {
    private ImageChapterRepository imageChapterRepository;
    public ImageChapterViewModel(@NonNull Application application) {
        super(application);
        imageChapterRepository = new ImageChapterRepository(application);
    }
    public LiveData<List<ImageChapter>> getAllImage(Chapter chapter){
        return imageChapterRepository.getImageList(chapter);
    }
    public LiveData<List<ImageChapter>> getAllImageNext(Chapter chapter){
        return imageChapterRepository.getImageList(chapter);
    }
    public LiveData<List<ImageChapter>> getAllImagePrev(Chapter chapter){
        return imageChapterRepository.getImageList(chapter);
    }
}
