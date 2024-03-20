package hung.deptrai.comicapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import hung.deptrai.comicapp.model.Category;
import hung.deptrai.comicapp.repository.CategoryRepository;

public class CategoryViewModel extends AndroidViewModel {
    private CategoryRepository categoryRepository;
    public CategoryViewModel(@NonNull Application application) {
        super(application);
        categoryRepository = new CategoryRepository();
    }
    public LiveData<List<Category>> getAllGenre(){
        return categoryRepository.getAllCategory();
    }
}
