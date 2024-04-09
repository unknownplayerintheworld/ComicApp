package hung.deptrai.comicapp.views;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import hung.deptrai.comicapp.R;
import hung.deptrai.comicapp.Utils.MessageStatusHTTP;
import hung.deptrai.comicapp.Utils.Tmp;
import hung.deptrai.comicapp.api.ComicService;
import hung.deptrai.comicapp.api.HistoryService;
import hung.deptrai.comicapp.api.ImageChapterService;
import hung.deptrai.comicapp.model.Chapter;
import hung.deptrai.comicapp.model.Comic;
import hung.deptrai.comicapp.model.DataJSON;
import hung.deptrai.comicapp.model.History;
import hung.deptrai.comicapp.model.ImageChapter;
import hung.deptrai.comicapp.viewmodel.ChapterViewModel;
import hung.deptrai.comicapp.viewmodel.HistoryViewModel;
import hung.deptrai.comicapp.viewmodel.ImageChapterViewModel;
import hung.deptrai.comicapp.views.Interface.IClickChapter;
import hung.deptrai.comicapp.views.adapter.ListChapterAdapter;
import hung.deptrai.comicapp.views.adapter.ReadingAdapter;
import hung.deptrai.comicapp.views.adapter.SelectChapBottomSheetAdapter;
import hung.deptrai.comicapp.views.fragment.BottomSheetAddToFavourite;
import hung.deptrai.comicapp.views.fragment.BottomSheetChapterFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadingActivity extends AppCompatActivity {
    private AppCompatButton btn_prev,btn_next,btn_current_selector;
    private ImageButton back_btn_chapter,btn_comment;
    private TextView chapter_position;
    private RecyclerView rcv_image_chapter,rcv_bottom_sheet_chapter;
    private AppBarLayout scroll_header,scroll_bottom;
    private ProgressBar loading;
    private int currentChapterPosition;
    private int lastChapter,firstChapter;
    private TextView txt_norecord;

    private List<Chapter> chapterList,chapterList2;
    ReadingAdapter readingAdapter;
    private IClickChapter iClickChapter;
//    private ScrollView scroll_header,scroll_bottom;
    private ImageChapterViewModel imageChapterViewModel;
    private ChapterViewModel chapterViewModel;
    private HistoryViewModel historyViewModel;
    private String HistoryID = "";

    private static final String PREFS_NAME = "MyPrefs";
    private static final String LAST_UPDATE_TIME_KEY = "lastUpdateTime";

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);
        //txtnorecord
        txt_norecord = findViewById(R.id.txt_noimage);
        //
        scroll_header = findViewById(R.id.header_chapter_scroll_comic);
        scroll_bottom = findViewById(R.id.scroll_selector_chapter);

        // button
        back_btn_chapter = findViewById(R.id.btn_back_chapter_to_out);
        btn_comment = findViewById(R.id.btn_comment);
        btn_prev = findViewById(R.id.nav_button_previous);
        btn_next = findViewById(R.id.nav_button_next);
        btn_current_selector = findViewById(R.id.nav_button_current_selector);

        //
        chapter_position = findViewById(R.id.comic_chapter_number);
        rcv_image_chapter = findViewById(R.id.rcv_img_chapter);
        rcv_bottom_sheet_chapter = findViewById(R.id.bottom_shee_chapter_list_rcv);

        //progress bar
        loading = findViewById(R.id.progress_bar_loading);
        // viewmodel
        imageChapterViewModel = new ViewModelProvider(this).get(ImageChapterViewModel.class);
        chapterViewModel = new ViewModelProvider(this).get(ChapterViewModel.class);
        historyViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);

        //setup adapter
        readingAdapter = new ReadingAdapter();
        readingAdapter.setData(getImageFromChapterNumberPos());

        LinearLayoutManager lnm = new LinearLayoutManager(this);
        rcv_image_chapter.setLayoutManager(lnm);
        rcv_image_chapter.setAdapter(readingAdapter);

        //
        currentChapterPosition = Integer.parseInt(getChapterNumberPos());

        //

        getLastAndFirst(btn_prev,btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNextChapter(btn_next);
                Log.e("btn_next","click");
            }
        });
        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPrevChapter(btn_prev);
                Log.e("btn_prev","click");
            }
        });
        btn_current_selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBottomSheetListChapter();
            }
        });
        back_btn_chapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBottomSheetAddToFav();
            }
        });
        chapter_position.setText(getChapterNumberPos());
        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCommentActivity();
            }
        });
    }

    public void update() {
        updateStateIfNeeded(getComicID());
        getHistoryID();
    }

    private void updateStateIfNeeded(String comicID) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String lastUpdateTimeKey = LAST_UPDATE_TIME_KEY + "_" + comicID;
        long lastUpdateTime = prefs.getLong(lastUpdateTimeKey, 0);

        // Lấy thời gian hiện tại
        long currentTime = Calendar.getInstance().getTimeInMillis();

        // Kiểm tra thời gian giữa lần cập nhật gần nhất và thời gian hiện tại
        long timeSinceLastUpdate = currentTime - lastUpdateTime;
        long updateInterval = 15 * 60 * 1000; // 15 phút * 60 giây/phút * 1000 ms/giây

        if (timeSinceLastUpdate >= updateInterval) {
            // Thực hiện cập nhật trạng thái
            updateViews(comicID);

            // Lưu thời gian cập nhật mới
            SharedPreferences.Editor editor = prefs.edit();
            editor.putLong(lastUpdateTimeKey, currentTime);
            editor.apply();
        } else {
            // Thời gian giữa các lần cập nhật chưa đủ lớn, từ chối cập nhật
            Toast.makeText(this, "Please wait before updating again", Toast.LENGTH_SHORT).show();
        }
    }


    private void upsertHistory(String chapterID, String historyID, String comicID, String currentUsername) {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("chapterID",chapterID);
        hashMap.put("historyID",historyID);
        hashMap.put("comicID",comicID);
        hashMap.put("username",currentUsername);
        if(!chapterID.isEmpty() && !comicID.isEmpty() && !currentUsername.isEmpty() && !historyID.isEmpty()){
            historyViewModel.upsertHistory(hashMap).observe(this,booleans -> {
                List<Boolean> booleanList = booleans;
                Log.e("upsert successful?",booleanList.get(0).toString());
            });
        }
    }

    private void getHistoryID() {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("username", Tmp.current_username);
        historyViewModel.getHistoryID(hashMap).observe(this,histories -> {
            HistoryID = histories.get(0).getId();
            Log.e("historyID 1:",HistoryID);
            historyViewModel.getHistoryID(hashMap).removeObservers(this);
            if(chapterList.size()>0 && !HistoryID.isEmpty()){
                getChapterID();
            }
        });
    }

    private void getChapterID() {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("comicID", getComicID());
        hashMap.put("chapterPos",String.valueOf(currentChapterPosition));
        chapterViewModel.getChapterID(hashMap).observe(this,chapterList1 -> {
            chapterList2 = chapterList1;
            String chapter = String.valueOf(chapterList1.get(0).getChapterID());
            upsertHistory(chapter,HistoryID,getComicID(),Tmp.current_username);
        });
    }
    public void openCommentActivity(){
        Intent intent = new Intent(ReadingActivity.this,CommentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("chapter_commentID",String.valueOf(chapterList2.get(0).getChapterID()));
        bundle.putSerializable("comicID",getComicID());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void updateViews(String comicID) {
        if(!comicID.isEmpty()){
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("comicID",comicID);
            ComicService.comicService.updateViewCount(hashMap).enqueue(new Callback<DataJSON<Boolean>>() {
                @Override
                public void onResponse(Call<DataJSON<Boolean>> call, Response<DataJSON<Boolean>> response) {
                    DataJSON dataJSON = response.body();
                    if(response.isSuccessful()) {
                        if(dataJSON!=null){
                            if(dataJSON.isStatus() == true){
                                List<Boolean> booleans = dataJSON.getData();
                                Log.e("status view updated:",booleans.toString());
                            }
                        }
                    }
                    else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                        Log.e("Status views repository:", MessageStatusHTTP.notImplemented);
                    }
                    else if(response.code() == HttpURLConnection.HTTP_INTERNAL_ERROR){
                        Log.e("Status views repository:", MessageStatusHTTP.internalServerError);
                    }
                }

                @Override
                public void onFailure(Call<DataJSON<Boolean>> call, Throwable t) {
                    Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                    Toast.makeText(ReadingActivity.this, "No internet connection!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void getLastAndFirst(AppCompatButton btn_prev,AppCompatButton btn_next) {
        chapterList = new ArrayList<>();
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("comicID",getComicID());
        chapterViewModel.getChapterList(hashMap).observe(this,chapters -> {
            chapterList = chapters;
            if(chapterList.size()>0) {
                lastChapter = Integer.parseInt(chapterList.get(chapterList.size() - 1).getChapter_pos());
                firstChapter = Integer.parseInt(chapterList.get(0).getChapter_pos());
                Log.e("last and first",String.valueOf(lastChapter) + "|"+ String.valueOf(firstChapter));
            }
            Log.e("current chapter:",String.valueOf(currentChapterPosition));
            if(currentChapterPosition == firstChapter){
                btn_prev.setEnabled(false);
            }else{
                btn_prev.setEnabled(true);
            }
            if(currentChapterPosition<lastChapter){
                btn_next.setEnabled(true);
            }
            else{
                btn_next.setEnabled(false);
            }
            chapterViewModel.getChapterList(hashMap).removeObservers(this);
            update();
        });
    }

    private void openBottomSheetAddToFav() {
        finish();
    }



//    public List<ImageChapter> getListChapterImage(){
//        loading.setVisibility(View.VISIBLE);
//        List<ImageChapter> imgchap = new ArrayList<>();
////        imgchap.add(new ImageChapter("1","Chapter 1",R.drawable.img_4));
////        imgchap.add(new ImageChapter("1","Chapter 1","https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEhsN1BpZ57qkr0nq2MiwEA7GEYlJqciuGKN_OS7hsuH0zdlOIuzN7x805CuhDzek4yRehYqQoubOzQYJL4gpqty9wITdFHrba1I2syf1Z5261P80yJslVe-iHco4nOpMSVaeyrjg89atSY/s1600/be441_001cover.jpg?imgmax=0"));
////        imgchap.add(new ImageChapter("1","Chapter 1","https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEieHhtkFCD4qB3pePXB0l4rADp4YgPgNxhmTchbyJ13_r3vNvNQzlzNtL8ODBScEaIS0pxxRE7XhhJ5MlMg9AwHLwdiLZgaUIZUPeJ5rMtl-ue6OGAclMydKfU5RTJMs83B8NnHiCwvPs8/s1600/be441_003.jpg?imgmax=0"));
////        imgchap.add(new ImageChapter("1","Chapter 1","https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEj2w6BpOjkRYVOq-X_lY4sMks4a5uR7F7ZPspLdCxHDoe_Y3U1aHFwNuQ4UbacTe0xuvHr-M_HWuZp6zh577qDP3nIJLB9TjLFsK6RKbdFx1CqyjgM3F5y-DTP40qIK3TmjkAyCQDKdqa0/s1600/be441_004.jpg?imgmax=0"));
////        imgchap.add(new ImageChapter("1","Chapter 1","https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEiyKCGQQ2C4D9uAfzDVyriomxnvnD986dBrc66nK_CUFNYiPjQY5XUXb7K0zyJrBw35yYWBXYfvvxG-fweCeYYYjFTAOk7MwGbkkhRzorkjYxDbrvLrEcl8ATzvFk55XGFN7chDgXw5dq4/s1600/be441_005.jpg?imgmax=0"));
////        imgchap.add(new ImageChapter("1","Chapter 1","https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEhwz64SlRY6ljyG-m0swVMCc5Dymuu3heRrIWdwE1mP7OyWU7GtmzSKIBr3vpdLZFyOsY-YCTeE_w2N8vh0z4jU7iV4O7rerMOSZaNIVB7c1-Zbtn_CLbHTn6rQ7Gc9Ysb7rq0oCjsukO4/s1600/be441_006.jpg?imgmax=0"));
//        imgchap.add(new ImageChapter("1","https://img02.hamtruyen.vn/bbdata/k65gn92oisjpb1y/01_k65m9kvpmc9a3seabky.jpg?id=top1",1));
//        imgchap.add(new ImageChapter("1","https://firebasestorage.googleapis.com/v0/b/image-5b3a5.appspot.com/o/toi_S_rank_mao_hiem_gia_la_mot_nguoi_cuong_con_gai_juudo%2Fchapter_1%2Ftoi_S_rank_mao_hiem_gia_la_mot_nguoi_cuong_con_gai_juudo_1_1.jpg?alt=media&token=cfbb682b-7a40-4786-96c4-a130e03d427b",2));
//        loading.setVisibility(View.INVISIBLE);
//        return imgchap;
//    }


    private void openBottomSheetListChapter() {
        BottomSheetChapterFragment bottomSheetChapterFragment = new BottomSheetChapterFragment(chapterList, new IClickChapter() {
            @Override
            public void onClickChapter(Chapter chapter) {
                openAgainReadingActivity(chapter);
            }
        });
        Bundle bundle = new Bundle();
        bundle.putString("comicID",getComicID());
        Log.e("comicID bts:",getComicID());
        bottomSheetChapterFragment.setArguments(bundle);
        bottomSheetChapterFragment.show(getSupportFragmentManager(),bottomSheetChapterFragment.getTag());

    }
    private void openAgainReadingActivity(Chapter chapter) {
        // Tạo một Intent
        Intent intent = new Intent(ReadingActivity.this,ReadingActivity.class);
        Bundle bundle = new Bundle();

// Đính kèm dữ liệu vào Intent
        bundle.putString("chapter",chapter.getChapter_pos());
        bundle.putString("comicID",getComicID());
        Log.e("chapter|comicID",chapter.getChapter_pos()+"|"+getComicID());
        intent.putExtras(bundle);
// Kết thúc Activity hiện tại
        finish();

// Khởi động lại Activity hiện tại bằng Intent đã được nhận
        startActivity(intent);
    }
//    public List<Chapter> getChapterList(){
//        List<Chapter> list = new ArrayList<>();
//        list.add(new Chapter("1","Truyện 1"));
//        list.add(new Chapter("2","Truyện 1"));
//        list.add(new Chapter("3","Truyện 1"));
//        list.add(new Chapter("4","Truyện 1"));
//        list.add(new Chapter("5","Truyện 1"));
//        return list;
//    }
    public String getChapterNumberPos(){
        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return "";
        }
        else{
            return bundle.getString("chapter");
        }
    }
    public List<ImageChapter> getImageFromChapterNumberPos(){
        loading.setVisibility(View.VISIBLE);
        List<ImageChapter> imageChapterList = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        if(bundle == null){

        }
        else{
            String chapter_num = bundle.getString("chapter");
            String comicID = bundle.getString("comicID");
            Chapter chapter = new Chapter();
            chapter.setChapter_pos(chapter_num);
            chapter.setComicIDfk(Integer.parseInt(comicID));
            imageChapterViewModel.getAllImage(chapter).observe(this,imageChapters -> {
                if(imageChapters.isEmpty()){

                }
                else {
                    for (int i = 0; i < imageChapters.size(); i++) {
                        if(imageChapters.get(i)==null){

                        }
                        else {
                            imageChapterList.add(new ImageChapter(imageChapters.get(i).getId(), imageChapters.get(i).getLinkImage(), imageChapters.get(i).getImage_pos()));
                            Log.e("Status:", String.valueOf(i));
                        }
                    }
                }
                loading.setVisibility(View.INVISIBLE);
                updateUI(imageChapterList);
                imageChapterViewModel.getAllImage(chapter).removeObservers(this);
            });
        }
        return imageChapterList;
    }

    private void updateUI(List<ImageChapter> imageChapters) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(imageChapters.size()<1){
                    rcv_image_chapter.setVisibility(View.GONE);
//                    rcv_bottom_sheet_chapter.setVisibility(View.GONE);
                    txt_norecord.setVisibility(View.VISIBLE);
                    btn_prev.setVisibility(View.GONE);
                    btn_next.setVisibility(View.GONE);
                    btn_current_selector.setVisibility(View.GONE);
                    chapter_position.setText("?");
                }
                else{
                    rcv_image_chapter.setVisibility(View.VISIBLE);
                    txt_norecord.setVisibility(View.GONE);
                    readingAdapter.setData(imageChapters);
                    chapter_position.setText(String.valueOf(currentChapterPosition));
                    btn_current_selector.setText("Chapter "+String.valueOf(currentChapterPosition));
                    // Cuộn RecyclerView về đầu danh sách ảnh mới
                    rcv_image_chapter.smoothScrollToPosition(0);
                }
            }
        });
    }
    public String getComicID(){
        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return "";
        }
        return bundle.getString("comicID");
    }
    public void getNextChapter(AppCompatButton btn_next){
        if(btn_next.isEnabled()){
            List<ImageChapter> imageChapterList = new ArrayList<>();
            Chapter chapter = new Chapter();
            chapter.setChapter_pos(String.valueOf(currentChapterPosition+1));
            chapter.setComicIDfk(Integer.parseInt(getComicID()));
            loading.setVisibility(View.VISIBLE);
//            imageChapterViewModel.getAllImageNext(chapter).observe(this,imageChapters -> {
//                for (int i = 0; i < imageChapters.size(); i++) {
//                    imageChapterList.add(new ImageChapter(imageChapters.get(i).getId(),imageChapters.get(i).getLinkImage(),imageChapters.get(i).getImage_pos()));
//                    Log.e("Status:",String.valueOf(i));
//                }
            if(chapter!=null){
                ImageChapterService.imageChapterService.getAllImageByChapter(chapter).enqueue(new Callback<DataJSON<ImageChapter>>() {
                    @Override
                    public void onResponse(Call<DataJSON<ImageChapter>> call, Response<DataJSON<ImageChapter>> response) {
                        if(response.isSuccessful()) {
                            DataJSON<ImageChapter> dataJSON = response.body();
                            if (dataJSON != null) {
                                if (dataJSON.isStatus()) {
                                    List<ImageChapter> imageChapters = dataJSON.getData();
                                    for (int i = 0; i < imageChapters.size(); i++) {
                                        imageChapterList.add(new ImageChapter(imageChapters.get(i).getId(), imageChapters.get(i).getLinkImage(), imageChapters.get(i).getImage_pos()));
                                        Log.e("Status:", String.valueOf(i));
                                    }
                                    loading.setVisibility(View.INVISIBLE);
                                    currentChapterPosition++;
                                    updateUI(imageChapterList);
                                    getLastAndFirst(btn_prev,btn_next);
                                }
                            }
                        }
                        else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                            Log.e("image image status","Method is not implemented");
                            Toast.makeText(ReadingActivity.this, MessageStatusHTTP.notImplemented, Toast.LENGTH_SHORT).show();
                        }
                        else if(response.code() == HttpURLConnection.HTTP_INTERNAL_ERROR){
                            Log.e("image comic status","Error server");
                            Toast.makeText(ReadingActivity.this, MessageStatusHTTP.internalServerError, Toast.LENGTH_SHORT).show();
                        }
                        else if(response.code() == HttpURLConnection.HTTP_BAD_REQUEST){
                            Log.e("image comic status","Bad request,please check argument and param");
                            Toast.makeText(ReadingActivity.this, MessageStatusHTTP.badGateway, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DataJSON<ImageChapter>> call, Throwable t) {
                        Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                    }
                });
            }
//            });
        }
    }
    public void getPrevChapter(AppCompatButton btn_prev){
        if(btn_prev.isEnabled()){
            List<ImageChapter> imageChapterList = new ArrayList<>();
            Chapter chapter = new Chapter();
            chapter.setChapter_pos(String.valueOf(currentChapterPosition-1));
            chapter.setComicIDfk(Integer.parseInt(getComicID()));
//            imageChapterViewModel.getAllImagePrev(chapter).observe(this,imageChapters -> {
//                for (int i = 0; i < imageChapters.size(); i++) {
//                    imageChapterList.add(new ImageChapter(imageChapters.get(i).getId(),imageChapters.get(i).getLinkImage(),imageChapters.get(i).getImage_pos()));
//                    Log.e("Status:",String.valueOf(i));
//                }
            if(chapter!=null){
                ImageChapterService.imageChapterService.getAllImageByChapter(chapter).enqueue(new Callback<DataJSON<ImageChapter>>() {
                    @Override
                    public void onResponse(Call<DataJSON<ImageChapter>> call, Response<DataJSON<ImageChapter>> response) {
                        if(response.isSuccessful()) {
                            DataJSON<ImageChapter> dataJSON = response.body();
                            if (dataJSON != null) {
                                if (dataJSON.isStatus()) {
                                    List<ImageChapter> imageChapters = dataJSON.getData();
                                    for (int i = 0; i < imageChapters.size(); i++) {
                                        imageChapterList.add(new ImageChapter(imageChapters.get(i).getId(), imageChapters.get(i).getLinkImage(), imageChapters.get(i).getImage_pos()));
                                        Log.e("Status:", String.valueOf(i));
                                    }
                                    loading.setVisibility(View.INVISIBLE);
                                    currentChapterPosition--;
                                    updateUI(imageChapterList);
                                    getLastAndFirst(btn_prev,btn_next);
                                }
                            }
                        }
                        else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                            Log.e("image image status","Method is not implemented");
                            Toast.makeText(ReadingActivity.this, MessageStatusHTTP.notImplemented, Toast.LENGTH_SHORT).show();
                        }
                        else if(response.code() == HttpURLConnection.HTTP_INTERNAL_ERROR){
                            Log.e("image comic status","Error server");
                            Toast.makeText(ReadingActivity.this, MessageStatusHTTP.internalServerError, Toast.LENGTH_SHORT).show();
                        }
                        else if(response.code() == HttpURLConnection.HTTP_BAD_REQUEST){
                            Log.e("image comic status","Bad request,please check argument and param");
                            Toast.makeText(ReadingActivity.this, MessageStatusHTTP.badGateway, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DataJSON<ImageChapter>> call, Throwable t) {
                        Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                    }
                });
            }
//            });
        }
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
        getHistoryID();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getHistoryID();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
