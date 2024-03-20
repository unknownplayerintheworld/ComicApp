package hung.deptrai.comicapp.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hung.deptrai.comicapp.R;
import hung.deptrai.comicapp.Utils.Tmp;
import hung.deptrai.comicapp.api.HistoryService;
import hung.deptrai.comicapp.model.Author;
import hung.deptrai.comicapp.model.Chapter;
import hung.deptrai.comicapp.model.Comic;
import hung.deptrai.comicapp.model.History;
import hung.deptrai.comicapp.viewmodel.AuthorViewModel;
import hung.deptrai.comicapp.viewmodel.ChapterViewModel;
import hung.deptrai.comicapp.viewmodel.ComicViewModel;
import hung.deptrai.comicapp.viewmodel.HistoryViewModel;
import hung.deptrai.comicapp.views.Interface.IClickHistory;
import hung.deptrai.comicapp.views.adapter.HistoryAdapter;

public class HistoryActivity extends AppCompatActivity {
    private ImageButton backbtn;
    private RecyclerView rcv;
    private TextView txt_noRecord;
    private List<Comic> comicList;
    private ComicViewModel comicViewModel;
    private AuthorViewModel authorViewModel;
    private ChapterViewModel chapterViewModel;
    private HistoryViewModel historyViewModel;
    private List<Author> authorList;
    private List<Chapter> chapterList;
    private List<History> historyList;
    private HistoryAdapter adapter;
    private boolean s1 = false;
    private boolean s2 = false;
    private boolean s3 = false;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        txt_noRecord = findViewById(R.id.txt_noHistory);

        backbtn = findViewById(R.id.btn_back_history);
        rcv = findViewById(R.id.rcv_history_activity);
        comicViewModel = new ViewModelProvider(this).get(ComicViewModel.class);
        authorViewModel = new ViewModelProvider(this).get(AuthorViewModel.class);
        chapterViewModel = new ViewModelProvider(this).get(ChapterViewModel.class);
        historyViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);

        getListAuthor();
        getListComic();
        getListChapter();
        getHistoryID();

        LinearLayoutManager lnm = new LinearLayoutManager(HistoryActivity.this);
        rcv.setLayoutManager(lnm);

        adapter = new HistoryAdapter();
        adapter.setData(comicList, chapterList, authorList, new IClickHistory() {
            @Override
            public void onClickHistory(Chapter chapter, Comic comic) {
                openReadingActivity(chapter,comic);
            }

            @Override
            public void onClickDelHistory(Comic comic) {
                delComicAndUpdate(comic);
                Toast.makeText(HistoryActivity.this, "Click", Toast.LENGTH_SHORT).show();
            }
        });
        rcv.setAdapter(adapter);

        //
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void openReadingActivity(Chapter chapter,Comic comic) {
        Intent intent = new Intent(HistoryActivity.this,ReadingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("chapter",chapter.getChapter_pos());
        bundle.putSerializable("comicID",comic.getId());
        intent.putExtras(bundle);
        startActivity(intent);
    }
    private void delComicAndUpdate(Comic comic) {
        // thực hiện
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("comicID",comic.getId());
        hashMap.put("historyID",historyList.get(0).getId());
        historyViewModel.removeFromHistory(hashMap).observe(this,booleans -> {
            if(booleans.get(0) == true){
                Toast.makeText(this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                comicList.remove(comic);
                updateUI();
                historyViewModel.removeFromHistory(hashMap).removeObservers(this);
            }
        });
    }

    private List<History> getListHistory() {
        List<History> list = new ArrayList<>();
        list.add(new History("1","1","Toàn cơ từ",1,"https://img.wattpad.com/cover/109018367-144-k677651.jpg","hung",1234));
        return list;
    }
    public void getListComic(){
        comicList = new ArrayList<>();
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("username", Tmp.current_username);
        comicViewModel.getComicHistory(hashMap).observe(this,comics -> {
            for (int i = 0; i < comics.size(); i++) {
                comicList.add(new Comic(comics.get(i).getId(),comics.get(i).getComicName(),comics.get(i).getLinkPicture(),comics.get(i).getViews(),comics.get(i).getCreated_at(),comics.get(i).getUpdated_at(),comics.get(i).getViews_in_month(),comics.get(i).getViews_in_week()));
            }
            s1 = true;
            checkAndUpdate();
            comicViewModel.getComicHistory(hashMap).removeObservers(this);
        });
    }
    public void checkAndUpdate(){
        if(s1 && s2 && s3){
            updateUI();
        }
    }
    private void updateUI() {
        if(comicList.size()<1){
            rcv.setVisibility(View.GONE);
            txt_noRecord.setVisibility(View.VISIBLE);
        }
        else{
            rcv.setVisibility(View.VISIBLE);
            txt_noRecord.setVisibility(View.GONE);
        }
        adapter.setData(comicList, chapterList, authorList, new IClickHistory() {
            @Override
            public void onClickHistory(Chapter chapter,Comic comic) {
                openReadingActivity(chapter,comic);
            }

            @Override
            public void onClickDelHistory(Comic comic) {
                delComicAndUpdate(comic);
            }
        });
    }

    public void getListChapter(){
        chapterList = new ArrayList<>();
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("username", Tmp.current_username);
        chapterViewModel.getChapterHistory(hashMap).observe(this,list -> {
            for (int i = 0; i < list.size(); i++) {
                chapterList.add(new Chapter(list.get(i).getChapterID(),list.get(i).getChapter_pos(),list.get(i).getComicIDfk(),list.get(i).getUpdated_at()));
            }
            s2 = true;
            checkAndUpdate();
            chapterViewModel.getChapterHistory(hashMap).removeObservers(this);
        });
    }
    public void getListAuthor(){
        authorList = new ArrayList<>();
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("username", Tmp.current_username);
        authorViewModel.getAuthorHistory(hashMap).observe(this,list -> {
            for (int i = 0; i < list.size(); i++) {
                authorList.add(new Author(list.get(i).getId(),list.get(i).getAuthor_name(),list.get(i).getDescription(),list.get(i).getLink_picture_author()));
            }
            s3 = true;
            checkAndUpdate();
            authorViewModel.getAuthorHistory(hashMap).removeObservers(this);
        });
    }
    public void getHistoryID(){
        historyList = new ArrayList<>();
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("username", Tmp.current_username);
        historyViewModel.getHistoryID(hashMap).observe(this,histories -> {
            historyList = histories;
            historyViewModel.getHistoryID(hashMap).removeObservers(this);
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
