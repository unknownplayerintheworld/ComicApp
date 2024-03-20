package hung.deptrai.comicapp.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hung.deptrai.comicapp.R;
import hung.deptrai.comicapp.model.Author;
import hung.deptrai.comicapp.model.Comic;
import hung.deptrai.comicapp.viewmodel.AuthorViewModel;
import hung.deptrai.comicapp.viewmodel.ComicViewModel;
import hung.deptrai.comicapp.views.Interface.IClickComic;
import hung.deptrai.comicapp.views.adapter.AuthorAdapter;

public class AuthorActivity extends AppCompatActivity {
    private RecyclerView rcv;
    private TextView tv_trans_title,tv_trans_desc,trans_tv,txt_noComics;
    private ImageButton btnback;
    private AuthorAdapter adapter;
    private AuthorViewModel authorViewModel;
    private ComicViewModel comicViewModel;
    private ShapeableImageView img_trans;
    private List<Comic> list;
    private List<Author> listAuthor;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);

        //
        authorViewModel = new ViewModelProvider(this).get(AuthorViewModel.class);
        comicViewModel = new ViewModelProvider(this).get(ComicViewModel.class);
        rcv = findViewById(R.id.rcv_trans_comic_owner);
        tv_trans_desc = findViewById(R.id.trans_desc);
        tv_trans_title = findViewById(R.id.trans_title);
        trans_tv = findViewById(R.id.trans_tv);
        img_trans = findViewById(R.id.img_trans);
        txt_noComics = findViewById(R.id.txt_noComics);

        btnback = findViewById(R.id.btnback);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AuthorActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        getAuthor();
        getAuthorComics();

        adapter = new AuthorAdapter();
        adapter.setData(list, new IClickComic() {
            @Override
            public void onClickComic(Comic comic) {

            }
        });

        GridLayoutManager grid = new GridLayoutManager(this,3);
        rcv.setLayoutManager(grid);
        rcv.setAdapter(adapter);
    }

    public void getAuthorComics() {
//        List<Comic> authorlist = new ArrayList<>();
//        authorlist.add(new Comic("1","Bà chị độc thân","https://cdnntx.com/nettruyen/thumb/toi-la-tai-xe-xe-cong-nghe-co-chut-tien-thi-da-sao.jpg","Bất hủ tông","ecchi"));
//        return authorlist;
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("transteamID",getAuthorID());
        comicViewModel.getComicByTransteamID(hashMap).observe(this,comics->{
            list = new ArrayList<>();
            for (int i = 0; i < comics.size(); i++) {
                list.add(new Comic(comics.get(i).getId(),comics.get(i).getComicName(),comics.get(i).getLinkPicture(),comics.get(i).getViews(),comics.get(i).getCreated_at(),comics.get(i).getUpdated_at(),comics.get(i).getViews_in_month(),comics.get(i).getViews_in_week()));
            }
            updateUI();
        });
    }

    private void updateUI() {
        if(list.size()<1){
            rcv.setVisibility(View.GONE);
            txt_noComics.setVisibility(View.VISIBLE);
        }
        else{
            rcv.setVisibility(View.VISIBLE);
            txt_noComics.setVisibility(View.GONE);
        }
        adapter.setData(list, new IClickComic() {
            @Override
            public void onClickComic(Comic comic) {
                openComicActivity(comic);
            }
        });
    }

    public void getAuthor(){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("transteamID",getAuthorID());
        authorViewModel.getAuthorFromID(hashMap).observe(this,authors -> {
            listAuthor = authors;
            tv_trans_title.setText(listAuthor.get(0).getAuthor_name());
            trans_tv.setText(listAuthor.get(0).getAuthor_name());
            tv_trans_desc.setText(listAuthor.get(0).getDescription());
            Glide.with(this).load(listAuthor.get(0).getLink_picture_author()).into(img_trans);
            authorViewModel.getAuthorFromComic(hashMap).removeObservers(this);
        });
    }
    public String getAuthorID(){
        Intent intent = getIntent();
        if(intent!=null){
            return intent.getStringExtra("transteamID");
        }
        return "";
    }
    public void openComicActivity(Comic comic){
        Intent intent = new Intent(AuthorActivity.this,ComicActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("comicID",comic.getId());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}