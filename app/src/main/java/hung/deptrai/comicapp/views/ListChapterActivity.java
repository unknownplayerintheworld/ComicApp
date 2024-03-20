package hung.deptrai.comicapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

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
import hung.deptrai.comicapp.model.Chapter;
import hung.deptrai.comicapp.model.Comic;
import hung.deptrai.comicapp.viewmodel.ChapterViewModel;
import hung.deptrai.comicapp.views.Interface.IClickChapter;
import hung.deptrai.comicapp.views.adapter.ListChapterAdapter;

public class ListChapterActivity extends AppCompatActivity {
    private RecyclerView rcv;
    private TextView txt_noRecord;
    private ImageButton back_btn;
    private TextView comic_name;
    // for testing
    private List<Comic> listcomic;
    private ChapterViewModel chapterViewModel;
    private List<Chapter> chapterList,chapterHistory;
    private ListChapterAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_listchapter);
        initListComicForTesting();
        rcv = findViewById(R.id.rcv_chapter_list);
        back_btn = findViewById(R.id.btn_back_chapter);
        txt_noRecord = findViewById(R.id.txt_norecord);
        comic_name = findViewById(R.id.comic_chapter_title);

        //
        chapterViewModel = new ViewModelProvider(this).get(ChapterViewModel.class);

        //
        getListChapter();
        getChapterHistory();

        LinearLayoutManager lnm = new LinearLayoutManager(this);
        rcv.setLayoutManager(lnm);

        adapter = new ListChapterAdapter();
        adapter.setData(chapterList,chapterHistory, new IClickChapter() {
            @Override
            public void onClickChapter(Chapter chapter) {
                // viết hàm gọi intent,truyền dữ liệu gói,...
                openReadingComicActivity(chapter);
            }
        });
        getComicNameForTesting();
        rcv.setAdapter(adapter);

        //
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(ListChapterActivity.RESULT_OK);
                finish();
            }
        });
    }

    public void getListChapter() {
        chapterList = new ArrayList<>();
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("comicID",getComicID());
        chapterViewModel.getChapterList(hashMap).observe(this,chapters -> {
            chapterList = chapters;
            comic_name.setText(getComicName());
            updateUI();
            chapterViewModel.getChapterList(hashMap).removeObservers(this);
        });
    }

    private void updateUI() {
        if(chapterList.size()<1){
            rcv.setVisibility(View.GONE);
            txt_noRecord.setVisibility(View.VISIBLE);
        }
        else{
            rcv.setVisibility(View.VISIBLE);
            txt_noRecord.setVisibility(View.GONE);
        }
        adapter.setData(chapterList,chapterHistory, new IClickChapter() {
            @Override
            public void onClickChapter(Chapter chapter) {
                openReadingComicActivity(chapter);
            }
        });
    }

    private void initListComicForTesting(){
        listcomic = new ArrayList<>();
//        listcomic.add(new Comic("2","Toàn cơ từ"));
//        listcomic.add(new Comic("1","Tân tác long hổ môn 1"));
    }
    public void getComicNameForTesting(){
        for(int i = 0;i<listcomic.size();i++){
            if(listcomic.get(i).getId().equals(getComicID())){
                comic_name.setText(listcomic.get(i).getComicName());
            }
        }
    }
    public String getComicID(){
        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return "";
        }
        return bundle.getString("comicID");
    }
    public String getComicName(){
        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return "";
        }
        return bundle.getString("comicName");
    }
    public void openReadingComicActivity(Chapter chapter){
        Intent intent = new Intent(ListChapterActivity.this,ReadingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("chapter",chapter.getChapter_pos());
        bundle.putSerializable("comicID",getComicID());
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void getChapterHistory(){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("comicID",getComicID());
        hashMap.put("accountID", Tmp.account_id);
        chapterViewModel.getChapterHistoryComic(hashMap).observe(this,chapterList1 -> {
            chapterHistory = chapterList1;
            updateUI();
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getListChapter();
        getChapterHistory();
    }
}
