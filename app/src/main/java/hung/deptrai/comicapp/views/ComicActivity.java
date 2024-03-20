package hung.deptrai.comicapp.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hung.deptrai.comicapp.R;
import hung.deptrai.comicapp.Utils.Tmp;
import hung.deptrai.comicapp.model.Account;
import hung.deptrai.comicapp.model.Author;
import hung.deptrai.comicapp.model.Chapter;
import hung.deptrai.comicapp.model.Comic;
import hung.deptrai.comicapp.model.Comment;
import hung.deptrai.comicapp.model.Favourite;
import hung.deptrai.comicapp.viewmodel.AuthorViewModel;
import hung.deptrai.comicapp.viewmodel.ComicViewModel;
import hung.deptrai.comicapp.viewmodel.CommentViewModel;
import hung.deptrai.comicapp.views.Interface.IClickComic;
import hung.deptrai.comicapp.views.Interface.IClickComicReply;
import hung.deptrai.comicapp.views.Interface.IClickComment;
import hung.deptrai.comicapp.views.Interface.iFilterComment;
import hung.deptrai.comicapp.views.Interface.iOptionComment;
import hung.deptrai.comicapp.views.Interface.iUpdateFavouriteStatus;
import hung.deptrai.comicapp.views.adapter.ComicAdapter;
import hung.deptrai.comicapp.views.fragment.BottomSheetAddToFavourite;
import hung.deptrai.comicapp.views.fragment.BottomSheetAddtoFavComic;
import hung.deptrai.comicapp.views.fragment.BottomSheetOptionComment;
import hung.deptrai.comicapp.views.fragment.search.BottomSheetCommentFilter;

public class ComicActivity extends AppCompatActivity implements iUpdateFavouriteStatus,iFilterComment {
    private ConstraintLayout item_header_author_comic;
    private AppCompatButton chapter_list_btn;
    private ShapeableImageView img_trans;
    private ImageButton back_btn,favourite_button,send_comment_btn;
    private TextView comic_name_txt,transName,txt_filter_comic;
    private TextView desc_comic_txt,views,txt_noRecord;
    private ImageView img;
    private ComicViewModel comicViewModel;
    private AuthorViewModel authorViewModel;
    private List<Favourite> favouriteListChecker,favouriteList;
    private List<Author> author;
    private List<Comic> comicList;
    // rcv and adapter comment
    private CommentViewModel commentViewModel;
    private ComicAdapter comicAdapter;
    private RecyclerView rcv;

    private List<Comment> commentList;
    private List<Account> accountList;
    private List<Integer> accountID,commentID,childCommentCount,childCommentIDCount;
    private List<Boolean> lod;
    private List<Chapter> chapterList;
    private TextInputEditText commentWritting;
    private Snackbar snackbar,snackbar2;
    private Boolean filterStatus = true;
    //
    private boolean isFAV = false;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic);

        // viewmodel created
        comicViewModel = new ViewModelProvider(this).get(ComicViewModel.class);
        authorViewModel = new ViewModelProvider(this).get(AuthorViewModel.class);
        commentViewModel = new ViewModelProvider(this).get(CommentViewModel.class);

        // transTeam
        img_trans = findViewById(R.id.author_header_comic);
        transName = findViewById(R.id.trans_title);

        item_header_author_comic = findViewById(R.id.item_author_comic);
        favourite_button = findViewById(R.id.iconFavorite);
        back_btn = findViewById(R.id.btnback);
        chapter_list_btn = findViewById(R.id.chapter_btn);
        img = findViewById(R.id.img_comic);

        comic_name_txt = findViewById(R.id.comic_name_tv);
        comic_name_txt.setText(getComicID());
        desc_comic_txt = findViewById(R.id.comic_desc);
        views = findViewById(R.id.views);

        getComicByID();
        getAuthorFromComicID();

        getStatusFavourite();
        getFavouriteID();

        getAllRootCommentInTheComic();
        getAccountRootCommentInTheComic();
        checkCommentID();
        checkAccountID();
        checkLODType();
        getChapterCommentComic();
        getChildCommentCountComic();
        getChildCommentIDCountComic();

        //cmt
        commentWritting = findViewById(R.id.comment_txt_comic);
        txt_noRecord = findViewById(R.id.txt_norecord_comment_comic);
        send_comment_btn = findViewById(R.id.imageButton_send_comic);
        txt_filter_comic = findViewById(R.id.text_filter_comic);

        //snackbar
        View view = findViewById(android.R.id.content);
        snackbar = Snackbar.make(view,"Thành công", BaseTransientBottomBar.LENGTH_SHORT);
        snackbar2 = Snackbar.make(view,"Thất Bại",BaseTransientBottomBar.LENGTH_SHORT);

        //
        chapter_list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openListChapter();
            }
        });

        //
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //2 dòng này có nghĩa là set trạng thái activity là OK,và xóa nó(activity hiện tại) khỏi backstack(ngăn xếp chứa activity,fragment)
                // thao tác như 1 ngăn xếp thông thg,finish là xóa khỏi ngăn xếp
                setResult(ComicActivity.RESULT_OK);
                finish();
            }
        });

        // fav button onClick
        favourite_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBottomSheetAddToFav();
            }
        });

        item_header_author_comic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAuthorActivity();
            }
        });
        rcv = findViewById(R.id.rcv_comment_list_comic);
        comicAdapter = new ComicAdapter();
        LinearLayoutManager lnm = new LinearLayoutManager(this);
        comicAdapter.setData(chapterList, commentList, accountList, accountID, lod, commentID, childCommentCount, childCommentIDCount, new IClickComment() {
            @Override
            public void onClickReply(Comment comment) {

            }

            @Override
            public void onClickCountReply(Comment comment) {

            }

            @Override
            public void onClickLikeBtn(Comment comment) {

            }

            @Override
            public void onClickDisLikeBtn(Comment comment) {

            }

            @Override
            public void onLongClickComment(Comment comment) {

            }
        }, new IClickComicReply() {
            @Override
            public void onClickReply(Comment comment, Chapter chapter) {

            }

            @Override
            public void onClickCountReply(Comment comment, Chapter chapter) {

            }
        });
        rcv.setLayoutManager(lnm);
        rcv.setAdapter(comicAdapter);

        //
        send_comment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeComment();
            }
        });
        txt_filter_comic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBottomSheetCommentFilter();
            }
        });
    }
    public void getChildCommentCountComic(){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("comicID",getComicID());
        commentViewModel.getChildCommentCountComic(hashMap).observe(this,integers -> {
            childCommentCount = integers;
            updateUI();
        });
    }
    public void getChildCommentIDCountComic(){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("comicID",getComicID());
        commentViewModel.getChildCommentIDCountComic(hashMap).observe(this,integers -> {
            childCommentIDCount = integers;
            updateUI();
        });
    }

    private void getAuthorFromComicID() {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("comicID",getComicID());
        authorViewModel.getAuthorFromComic(hashMap).observe(this,authors -> {
            author = authors;
            Glide.with(this).load(authors.get(0).getLink_picture_author()).into(img_trans);
            transName.setText(authors.get(0).getAuthor_name());
            authorViewModel.getAuthorFromComic(hashMap).removeObservers(this);
        });
    }

    public String getComicID(){
        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return "";
        }
        else{
            String comicID = bundle.getString("comicID");
            return comicID;
        }
    }
    public void getComicByID(){
        Comic comic = new Comic();
        comicList = new ArrayList<>();
        comic.setId(getComicID());
        comicViewModel.getComicByID(comic).observe(this,comics -> {
            comicList = comics;
            comic_name_txt.setText(comics.get(0).getComicName());
            desc_comic_txt.setText(comics.get(0).getDescription());
            views.setText(String.valueOf(comics.get(0).getViews()));
            Glide.with(this).load(comics.get(0).getLinkPicture()).into(img);
            comicViewModel.getComicByID(comic).removeObservers(this);
        });
    }

    public void getAllRootCommentInTheComic(){
        HashMap<String,String> hashmap = new HashMap<>();
        hashmap.put("comicID",getComicID());
        commentViewModel.getAllRootCommentInComic(hashmap).observe(this,comments1 -> {
            commentList = comments1;
            updateUI();
//            commentViewModel.getAllRootCommentInChapter(hashmap).removeObservers(this);
        });
    }
    public void getAccountRootCommentInTheComic(){
        HashMap<String,String> hashmap = new HashMap<>();
        hashmap.put("comicID",getComicID());
        commentViewModel.getAccountRootComics(hashmap).observe(this,accounts1 -> {
            accountList = accounts1;
            updateUI();
//            commentViewModel.getAccountRootChapter(hashmap).removeObservers(this);
        });
    }
    public void checkAccountID(){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("comicID",getComicID());
        commentViewModel.checkAccountLODComic(hashMap).observe(this,integers -> {
            accountID = integers;
            updateUI(); // 100 là thời gian tính bằng mili giây, bạn có thể điều chỉnh thời gian này tùy theo nhu cầu của bạn

        });
    }
    public void checkLODType(){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("comicID",getComicID());
        commentViewModel.checkLODTypeLODComic(hashMap).observe(this,integers -> {
            lod = integers;
            updateUI(); // 100 là thời gian tính bằng mili giây, bạn có thể điều chỉnh thời gian này tùy theo nhu cầu của bạn

        });
    }

    private void updateUI() {
        if(commentList != null && commentList.size()>0){
            txt_noRecord.setVisibility(View.GONE);
        }
        else{
            txt_noRecord.setVisibility(View.VISIBLE);
        }
        comicAdapter.setData(chapterList, commentList, accountList, accountID, lod, commentID, childCommentCount, childCommentIDCount, new IClickComment() {
            @Override
            public void onClickReply(Comment comment) {
                openChildCommentActivity2(comment);
            }

            @Override
            public void onClickCountReply(Comment comment) {
                openChildCommentActivity2(comment);
            }

            @Override
            public void onClickLikeBtn(Comment comment) {
                Boolean lodType = true;
                updateLODInc(comment, lodType);
                updateLODDesc(comment, lodType);
            }

            @Override
            public void onClickDisLikeBtn(Comment comment) {
                Boolean lodType = false;
                updateLODInc(comment, lodType);
                updateLODDesc(comment, lodType);
            }

            @Override
            public void onLongClickComment(Comment comment) {
                openBottomSheetOptionComment(comment);
            }
        }, new IClickComicReply() {
            @Override
            public void onClickReply(Comment comment, Chapter chapter) {
                openChildCommentActivity(comment,chapter);
            }

            @Override
            public void onClickCountReply(Comment comment, Chapter chapter) {
                openChildCommentActivity(comment,chapter);
            }
        });
    }

    private void openChildCommentActivity(Comment comment,Chapter chapter) {
        Intent intent = new Intent(ComicActivity.this,ChildCommentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("chapterID",chapter.getChapterID());
        bundle.putSerializable("comicID",getComicID());
        bundle.putSerializable("parent_commentID",String.valueOf(comment.getCommentID()));
        intent.putExtras(bundle);
        startActivity(intent);
    }
    private void openChildCommentActivity2(Comment comment) {
        Intent intent = new Intent(ComicActivity.this,ChildCommentActivity.class);
        Bundle bundle = new Bundle();
//        bundle.putSerializable("chapterID",chapter.getChapterID());
        bundle.putSerializable("comicID",getComicID());
        bundle.putSerializable("parent_commentID",String.valueOf(comment.getCommentID()));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void openBottomSheetOptionComment(Comment comment) {
        if (comment.getAccountID() == Integer.parseInt(Tmp.account_id)) {
            BottomSheetOptionComment bsoc = new BottomSheetOptionComment(new iOptionComment() {
                @Override
                public void onLongClick(Boolean status) {
                    if (status == true) {
                        getAllRootCommentInTheComic();
                        getAccountRootCommentInTheComic();
                        checkCommentID();
                        checkAccountID();
                        checkLODType();
                        getChildCommentCountComic();
                        getChildCommentIDCountComic();
                    } else {

                    }
                }
            });
            Bundle bundle = new Bundle();
            bundle.putString("commentID", String.valueOf(comment.getCommentID()));
            bsoc.setArguments(bundle);
            bsoc.show(getSupportFragmentManager(), bsoc.getTag());
        }
    }

    public void getChapterCommentComic(){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("comicID",getComicID());
        commentViewModel.getChapterCommentComic(hashMap).observe(this,integers -> {
            chapterList = integers;
            updateUI();
        });
    }
    public void getChapterCommentComicReact(){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("comicID",getComicID());
        commentViewModel.getChapterCommentComicReact(hashMap).observe(this,integers -> {
            chapterList = integers;
            updateUI();
        });
    }

    public void checkCommentID(){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("comicID",getComicID());
        commentViewModel.checkCommentIDLODComic(hashMap).observe(this,integers -> {
            commentID = integers;
            updateUI();
        });
    }
    @SuppressLint("ResourceAsColor")
    public void writeComment(){
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(commentWritting.getWindowToken(), 0);
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("accountID", Tmp.account_id);
        hashMap.put("comicID",getComicID());
        hashMap.put("content",commentWritting.getText().toString());
        if(commentWritting.getText().toString().isEmpty()){
            commentWritting.setError("Comment cannot be empty!");
            commentWritting.requestFocus();
        }
        else{
            commentViewModel.writeComment(hashMap).observe(this,aBoolean -> {
                if(aBoolean == true){
                    snackbar.setBackgroundTint(R.color.emerald_green);
                    snackbar.show();
                    getAllRootCommentInTheComic();
                    getAccountRootCommentInTheComic();
                    checkCommentID();
                    checkAccountID();
                    checkLODType();
                    getChildCommentCountComic();
                    getChildCommentIDCountComic();
                    if(filterStatus == true){
                        getChapterCommentComic();
                    }
                    else{
                        getChapterCommentComicReact();
                    }
                    Log.e("success","success");
                }
                else{
                    snackbar2.setBackgroundTint(R.color.red);
                    snackbar2.show();
                    Log.e("ERROR","Ko thành công");
                }
//
            });
        }
    }
    public void updateLODInc(Comment comment,Boolean lodType){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("commentID",String.valueOf(comment.getCommentID()));
        hashMap.put("lodType",lodType);
        hashMap.put("accountID",Tmp.account_id);
        if(comment.getIslod() == false) {
            commentViewModel.updateLODInc(hashMap).observe(this, aBoolean -> {
                if (aBoolean == true) {
                    Log.e("update like","success");
                }
                else{
                    Log.e("update like","failed");
                }
                getAllRootCommentInTheComic();
                getAccountRootCommentInTheComic();
                checkCommentID();
                checkAccountID();
                checkLODType();
                getChildCommentCountComic();
                getChildCommentIDCountComic();
                if(filterStatus == true){
                    getChapterCommentComic();
                }
                else{
                    getChapterCommentComicReact();
                }
            });
        }
    }
    public void updateLODDesc(Comment comment,Boolean lodType){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("commentID",String.valueOf(comment.getCommentID()));
        hashMap.put("lodType",lodType);
        hashMap.put("accountID",Tmp.account_id);
        if(comment.getIslod() == true) {
            commentViewModel.updateLODDesc(hashMap).observe(this, aBoolean -> {
                if (aBoolean == true) {
                    Log.e("update dislike","success");

                }
                else{
                    Log.e("update dislike","failed");
                }
                getAllRootCommentInTheComic();
                getAccountRootCommentInTheComic();
                checkCommentID();
                checkAccountID();
                checkLODType();
                getChildCommentCountComic();
                getChildCommentIDCountComic();
                if(filterStatus == true){
                    getChapterCommentComic();
                }
                else{
                    getChapterCommentComicReact();
                }
            });
        }
    }
    public void openBottomSheetCommentFilter(){
        BottomSheetCommentFilter bts = new BottomSheetCommentFilter(new iFilterComment() {
            @Override
            public void onFilterStatus(Boolean status) {
                if(status == true){
                    filterStatus = true;
                    getAccountCMTInComicByTime();
                    getAllRootCommentInTheComicByTime();
                    getChapterCommentComic();
                }
                else{
                    filterStatus = false;
                    getAccountCMTInComicByReact();
                    getAllRootCommentInTheComicByReact();
                    getChapterCommentComicReact();
                }
            }
        });
        Bundle bundle = new Bundle();
        bundle.putBoolean("statusFilter",filterStatus);
        bts.setArguments(bundle);
        bts.show(getSupportFragmentManager(),bts.getTag());
    }
    public void getAccountCMTInComicByReact(){
        HashMap<String,String> hashmap = new HashMap<>();
        hashmap.put("comicID",getComicID());
        commentViewModel.getAccountRootComicByReact(hashmap).observe(this,accounts1 -> {
            accountList = accounts1;
            updateUI();
//            commentViewModel.getAccountRootChapter(hashmap).removeObservers(this);
        });
    }
    public void getAccountCMTInComicByTime(){
        HashMap<String,String> hashmap = new HashMap<>();
        hashmap.put("comicID",getComicID());
        commentViewModel.getAccountRootComicByTime(hashmap).observe(this,accounts1 -> {
            accountList = accounts1;
            updateUI();
//            commentViewModel.getAccountRootChapter(hashmap).removeObservers(this);
        });
    }
    public void getAllRootCommentInTheComicByReact(){
        HashMap<String,String> hashmap = new HashMap<>();
        hashmap.put("comicID",getComicID());
        commentViewModel.getAllRootCommentInComicByReact(hashmap).observe(this,comments1 -> {
            commentList = comments1;
            updateUI();
//            commentViewModel.getAllRootCommentInChapter(hashmap).removeObservers(this);
        });
    }
    public void getAllRootCommentInTheComicByTime(){
        HashMap<String,String> hashmap = new HashMap<>();
        hashmap.put("comicID",getComicID());
        commentViewModel.getAllRootCommentInComicByTime(hashmap).observe(this,comments1 -> {
            commentList = comments1;
            updateUI();
//            commentViewModel.getAllRootCommentInChapter(hashmap).removeObservers(this);
        });
    }
    public void openListChapter(){
        Intent intent = new Intent(ComicActivity.this,ListChapterActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("comicID",getComicID());
        bundle.putSerializable("comicName",comicList.get(0).getComicName());
        intent.putExtras(bundle);
        startActivity(intent);
    }
    private void openBottomSheetAddToFav() {
//        BottomSheetAddToFavourite bts = new BottomSheetAddToFavourite();
//        bts.show(getSupportFragmentManager(),bts.getTag());
        BottomSheetAddtoFavComic btsc = new BottomSheetAddtoFavComic(new iUpdateFavouriteStatus() {
            @Override
            public void onFavouriteStatus(boolean isfav) {
                if (isfav) {
                    // Set the favourite button icon to red heart
                    favourite_button.setImageResource(R.drawable.ic_fav);
                } else {
                    // Set the favourite button icon to grey heart
                    favourite_button.setImageResource(R.drawable.ic_unfav);
                }
            }
        });
        Bundle bundle = new Bundle();
        bundle.putString("favouriteID", favouriteList.get(0).getId());
        Log.e("favouriteID bts:",favouriteList.get(0).getId().toString());
        bundle.putString("comicID",getComicID());
        Log.e("comicID bts:",getComicID());
        btsc.setArguments(bundle);
        btsc.setData(isFAV);
        btsc.show(getSupportFragmentManager(),btsc.getTag());
    }
    public void getStatusFavourite(){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("username", Tmp.current_username);
        hashMap.put("comicID",getComicID());
        Log.e("Status checking account and comic:",Tmp.current_username+"|"+getComicID());
        comicViewModel.getFavouriteStatus(hashMap).observe(this,aBoolean -> {
//            Boolean status = comicViewModel.getFavouriteStatus2().getValue();
            favouriteListChecker = aBoolean;
            Log.e("favourite:", String.valueOf(favouriteListChecker.size()));
            if(!favouriteListChecker.isEmpty()){
                favourite_button.setImageResource(R.drawable.ic_fav);
                isFAV = true;
                Log.e("status favourite checker:", String.valueOf(isFAV));
            }
            else{
                favourite_button.setImageResource(R.drawable.ic_unfav);
                isFAV = false;
                Log.e("status favourite checker:", String.valueOf(isFAV));
            }
            comicViewModel.getFavouriteStatus(hashMap).removeObservers(this);
        });
    }
    public void getFavouriteID(){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("username", Tmp.current_username);
        Log.e("Status checking account:",Tmp.current_username);
        comicViewModel.getFavouriteID(hashMap).observe(this,favourites -> {
            favouriteList = favourites;
            comicViewModel.getFavouriteID(hashMap).removeObservers(this);
        });
    }

    @Override
    public void onFavouriteStatus(boolean isfav) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isfav) {
                    // Set the favourite button icon to red heart
                    favourite_button.setImageResource(R.drawable.ic_fav);
                } else {
                    // Set the favourite button icon to grey heart
                    favourite_button.setImageResource(R.drawable.ic_unfav);
                }
            }
        });
    }
    public void openAuthorActivity(){
        Intent intent = new Intent(ComicActivity.this,AuthorActivity.class);
        intent.putExtra("transteamID",author.get(0).getId());
        startActivity(intent);
    }

    @Override
    public void onFilterStatus(Boolean status) {
        filterStatus = status;
        if(status == true){
            getAccountCMTInComicByTime();
            getAllRootCommentInTheComicByTime();
            getChapterCommentComic();
        }
        else{
            getAccountCMTInComicByReact();
            getAllRootCommentInTheComicByReact();
            getChapterCommentComicReact();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getAllRootCommentInTheComic();
        getAccountRootCommentInTheComic();
        checkCommentID();
        checkAccountID();
        checkLODType();
        getChapterCommentComic();
        getChildCommentCountComic();
        getChildCommentIDCountComic();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
