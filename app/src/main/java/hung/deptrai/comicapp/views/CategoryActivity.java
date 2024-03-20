package hung.deptrai.comicapp.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hung.deptrai.comicapp.R;
import hung.deptrai.comicapp.model.Category;
import hung.deptrai.comicapp.model.Comic;
import hung.deptrai.comicapp.viewmodel.ComicViewModel;
import hung.deptrai.comicapp.views.Interface.IClickComic;
import hung.deptrai.comicapp.views.adapter.CategoryAdapter;

public class CategoryActivity extends AppCompatActivity {
    private RecyclerView rcv_category;
    private ImageButton btn_back;
    private TextView tv_category,txt_noRecord;
    private  CategoryAdapter cat;
    private  List<Comic> comics;
    private ComicViewModel comicViewModel;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        rcv_category = findViewById(R.id.rcv_category_comic);
        btn_back = findViewById(R.id.btnback_category);
        tv_category = findViewById(R.id.catrogry_title);
        txt_noRecord = findViewById(R.id.txt_noComicCat);
        tv_category.setText(getCategory());

        GridLayoutManager lnm = new GridLayoutManager(this,3);
        rcv_category.setLayoutManager(lnm);
        //

        comicViewModel = new ViewModelProvider(this).get(ComicViewModel.class);

        //
        cat = new CategoryAdapter();

        Category category = new Category();
        category.setCat_name(getCategory());

        cat.setData(getListComicWithCategory(), new IClickComic() {
            @Override
            public void onClickComic(Comic comic) {
                openComicActivity(comic);
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(CategoryActivity.RESULT_OK);
                finish();
            }
        });
        rcv_category.setAdapter(cat);

        //
        getListComicByGenre(category);
    }
    public List<Comic> getListComicWithCategory() {
        List<Comic> list = new ArrayList<>();
        list.add(new Comic("7","Ore wa lolicon ja nai","https://st.nettruyenss.com/data/comics/177/ore-wa-lolicon-ja-nai.jpg","Ecchi Land"));
//        list.add(new Comic())
        return list;
    }
    public void openComicActivity(Comic comic){
        Intent intent = new Intent(CategoryActivity.this,ComicActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("comicID",comic.getId());
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public String getCategory(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            return bundle.getString("categoryName");
        }
        else{
            return "";
        }
    }
    public void getListComicByGenre(Category category){
        comics = new ArrayList<>();
        comicViewModel.getComicByGenre(category).observe(this,comics1 -> {
            for (int i = 0; i < comics1.size(); i++) {
                comics.add(new Comic(comics1.get(i).getId(),comics1.get(i).getComicName(),comics1.get(i).getLinkPicture(),comics1.get(i).getViews(),comics1.get(i).getCreated_at(),comics1.get(i).getUpdated_at(),comics1.get(i).getViews_in_month(),comics1.get(i).getViews_in_week()));
                Log.e("comic by genre: ",comics.get(i).getComicName()+ " " + comics.get(i).getLinkPicture());
            }
            comicViewModel.getComicByGenre(category).removeObservers(this);
            updateUI();
        });
    }

    private void updateUI() {
        if(comics.size()<1){
            rcv_category.setVisibility(View.GONE);
            txt_noRecord.setVisibility(View.VISIBLE);
        }
        else{
            rcv_category.setVisibility(View.VISIBLE);
            txt_noRecord.setVisibility(View.GONE);
        }
        cat.setData(comics, new IClickComic() {
            @Override
            public void onClickComic(Comic comic) {
                openComicActivity(comic);
            }
        });
    }
}
