package hung.deptrai.comicapp.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hung.deptrai.comicapp.R;
import hung.deptrai.comicapp.Utils.Tmp;
import hung.deptrai.comicapp.model.Account;
import hung.deptrai.comicapp.model.Comment;
import hung.deptrai.comicapp.viewmodel.CommentViewModel;
import hung.deptrai.comicapp.views.Interface.IClickComment;
import hung.deptrai.comicapp.views.Interface.iFilterComment;
import hung.deptrai.comicapp.views.Interface.iOptionComment;
import hung.deptrai.comicapp.views.Interface.iUpdateFavouriteStatus;
import hung.deptrai.comicapp.views.adapter.CommentAdapter;
import hung.deptrai.comicapp.views.fragment.BottomSheetAddtoFavComic;
import hung.deptrai.comicapp.views.fragment.BottomSheetOptionComment;
import hung.deptrai.comicapp.views.fragment.search.BottomSheetCommentFilter;

public class CommentActivity extends AppCompatActivity implements iFilterComment {
    private CommentAdapter adapter;
    private CommentViewModel commentViewModel;
    private RecyclerView commentRecyclerView;
    private TextView txt_noRecord,txt_filter;
    private ImageButton btn_back_comment,imageButton_send;
    private ConstraintLayout item_filter;
    private List<Comment> comments;
    private List<Account> accounts;
    private TextInputEditText commentWritting;
    private Snackbar snackbar,snackbar2;
    private List<Integer> accountIDList,commentIDList,childCommentCount,childCommentIDCount;
    private List<Boolean> booleanList;
    private Boolean filterStatus = true;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        View view = findViewById(android.R.id.content);
        snackbar = Snackbar.make(view,"Thành công", BaseTransientBottomBar.LENGTH_SHORT);
        snackbar2 = Snackbar.make(view,"Thất Bại",BaseTransientBottomBar.LENGTH_SHORT);

        // viewmodel
        commentViewModel = new ViewModelProvider(this).get(CommentViewModel.class);

        //UI components
        txt_noRecord = findViewById(R.id.txt_norecord_comment);
        txt_filter = findViewById(R.id.text_filter);
        btn_back_comment = findViewById(R.id.btn_back_comment);
        item_filter = findViewById(R.id.item_filter);
        commentWritting = findViewById(R.id.comment_txt);
        imageButton_send = findViewById(R.id.imageButton_send);
        getAllRootCommentInTheChapter();
        getAccountRootCommentInTheChapter();
        checkCommentID(getChapterID());
        checkAccountID(getChapterID());
        checkLODType(getChapterID());
        getChildCommentCount();
        getChildCommentIDCount();

        // adapter and rcv
        commentRecyclerView = findViewById(R.id.rcv_comment_list);
        adapter = new CommentAdapter();
        adapter.setData2(comments, accounts,accountIDList,booleanList,commentIDList,childCommentCount,childCommentIDCount, new IClickComment() {
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
        });
        LinearLayoutManager lnm = new LinearLayoutManager(CommentActivity.this);
        commentRecyclerView.setLayoutManager(lnm);
        commentRecyclerView.setAdapter(adapter);
        btn_back_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageButton_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeComment();
            }
        });
        txt_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBottomSheetCommentFilter();
            }
        });
    }
    public void checkAccountID(String chapterID){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("chapterID",chapterID);
        commentViewModel.checkAccountLOD(hashMap).observe(this,integers -> {
            accountIDList = integers;
            updateUI(); // 100 là thời gian tính bằng mili giây, bạn có thể điều chỉnh thời gian này tùy theo nhu cầu của bạn

        });
    }
    public void checkLODType(String chapterID){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("chapterID",chapterID);
        commentViewModel.checkLODTypeLOD(hashMap).observe(this,integers -> {
            booleanList = integers;
            updateUI(); // 100 là thời gian tính bằng mili giây, bạn có thể điều chỉnh thời gian này tùy theo nhu cầu của bạn

        });
    }
    public void checkCommentID(String chapterID){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("chapterID",chapterID);
        commentViewModel.checkCommentIDLOD(hashMap).observe(this,integers -> {
            commentIDList = integers;
            updateUI();
        });
    }
    public void getAllRootCommentInTheChapter(){
        HashMap<String,String> hashmap = new HashMap<>();
        hashmap.put("chapterID",getChapterID());
        commentViewModel.getAllRootCommentInChapter(hashmap).observe(this,comments1 -> {
            comments = comments1;
            updateUI();
//            commentViewModel.getAllRootCommentInChapter(hashmap).removeObservers(this);
        });
    }
    public void getAccountRootCommentInTheChapter(){
        HashMap<String,String> hashmap = new HashMap<>();
        hashmap.put("chapterID",getChapterID());
        commentViewModel.getAccountRootChapter(hashmap).observe(this,accounts1 -> {
            accounts = accounts1;
            updateUI();
//            commentViewModel.getAccountRootChapter(hashmap).removeObservers(this);
        });
    }

    private void updateUI() {
        if(comments != null && comments.size()>0){
            txt_noRecord.setVisibility(View.GONE);
        }
        else{
            txt_noRecord.setVisibility(View.VISIBLE);
        }
        adapter.setData2(comments, accounts,accountIDList,booleanList,commentIDList,childCommentCount,childCommentIDCount,new IClickComment() {
            @Override
            public void onClickReply(Comment comment) {
                openChildCommentActivity(comment);
            }

            @Override
            public void onClickCountReply(Comment comment) {
                openChildCommentActivity(comment);
            }

            @Override
            public void onClickLikeBtn(Comment comment) {
                Boolean lodType = true;
                updateLODInc(comment,lodType);
                updateLODDesc(comment,lodType);
            }

            @Override
            public void onClickDisLikeBtn(Comment comment) {
                Boolean lodType = false;
                updateLODInc(comment,lodType);
                updateLODDesc(comment,lodType);
            }

            @Override
            public void onLongClickComment(Comment comment) {
                openBottomSheetOptionComment(comment);
            }
        });
    }
    public void getChildCommentCount(){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("chapterID",getChapterID());
        commentViewModel.getChildCommentCount(hashMap).observe(this,integers -> {
            childCommentCount = integers;
            updateUI();
        });
    }
    public void getChildCommentIDCount(){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("chapterID",getChapterID());
        commentViewModel.getChildCommentIDCount(hashMap).observe(this,integers -> {
            childCommentIDCount = integers;
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
        hashMap.put("chapterID",getChapterID());
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
                    getAllRootCommentInTheChapter();
                    getAccountRootCommentInTheChapter();
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
                getAllRootCommentInTheChapter();
                getAccountRootCommentInTheChapter();
                checkCommentID(getChapterID());
                checkAccountID(getChapterID());
                checkLODType(getChapterID());
                getChildCommentCount();
                getChildCommentIDCount();
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
                getAllRootCommentInTheChapter();
                getAccountRootCommentInTheChapter();
                checkCommentID(getChapterID());
                checkAccountID(getChapterID());
                checkLODType(getChapterID());
                getChildCommentCount();
                getChildCommentIDCount();
            });
        }
    }

    //    public void getAllChidComment(Comment commen  t){
//        HashMap<String,String> hashmap = new HashMap<>();
//        hashmap.put("parent_commentID",String.valueOf(comment.getCommentID()));
//        commentViewModel.getAllChildComment(hashmap).observe(this,comments1 -> {
//            updateUI();
//            commentViewModel.getAllRootCommentInChapter(hashmap).removeObservers(this);
//        });
//    }
    public String getChapterID(){
        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return "";
        }
        return bundle.getString("chapter_commentID");
    }
    public String getComicID(){
        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return "";
        }
        return bundle.getString("comicID");
    }


    @Override
    public void onFilterStatus(Boolean status) {
        filterStatus = status;
        if(status == true){
            getAccountCMTInChapByTime();
            getAllRootCommentInTheChapterByTime();
        }
        else{
            getAccountCMTInChapByReact();
            getAllRootCommentInTheChapterByReact();
        }
    }
    public void getAccountCMTInChapByReact(){
        HashMap<String,String> hashmap = new HashMap<>();
        hashmap.put("chapterID",getChapterID());
        commentViewModel.getAccountRootChapterByReact(hashmap).observe(this,accounts1 -> {
            accounts = accounts1;
            updateUI();
//            commentViewModel.getAccountRootChapter(hashmap).removeObservers(this);
        });
    }
    public void getAccountCMTInChapByTime(){
        HashMap<String,String> hashmap = new HashMap<>();
        hashmap.put("chapterID",getChapterID());
        commentViewModel.getAccountRootChapterByTime(hashmap).observe(this,accounts1 -> {
            accounts = accounts1;
            updateUI();
//            commentViewModel.getAccountRootChapter(hashmap).removeObservers(this);
        });
    }
    public void getAllRootCommentInTheChapterByReact(){
        HashMap<String,String> hashmap = new HashMap<>();
        hashmap.put("chapterID",getChapterID());
        commentViewModel.getAllRootCommentInChapterByReact(hashmap).observe(this,comments1 -> {
            comments = comments1;
            updateUI();
//            commentViewModel.getAllRootCommentInChapter(hashmap).removeObservers(this);
        });
    }
    public void getAllRootCommentInTheChapterByTime(){
        HashMap<String,String> hashmap = new HashMap<>();
        hashmap.put("chapterID",getChapterID());
        commentViewModel.getAllRootCommentInChapterByTime(hashmap).observe(this,comments1 -> {
            comments = comments1;
            updateUI();
//            commentViewModel.getAllRootCommentInChapter(hashmap).removeObservers(this);
        });
    }
    public void openBottomSheetCommentFilter(){
        BottomSheetCommentFilter bts = new BottomSheetCommentFilter(new iFilterComment() {
            @Override
            public void onFilterStatus(Boolean status) {
                if(status == true){
                    filterStatus = true;
                    getAccountCMTInChapByTime();
                    getAllRootCommentInTheChapterByTime();
                }
                else{
                    filterStatus = false;
                    getAccountCMTInChapByReact();
                    getAllRootCommentInTheChapterByReact();
                }
            }
        });
        Bundle bundle = new Bundle();
        bundle.putBoolean("statusFilter",filterStatus);
        bts.setArguments(bundle);
        bts.show(getSupportFragmentManager(),bts.getTag());
    }
    public void openBottomSheetOptionComment(Comment comment){
        if(comment.getAccountID() == Integer.parseInt(Tmp.account_id)) {
            BottomSheetOptionComment bsoc = new BottomSheetOptionComment(new iOptionComment() {
                @Override
                public void onLongClick(Boolean status) {
                    if (status == true) {
                        getAllRootCommentInTheChapter();
                        getAccountRootCommentInTheChapter();
                        checkCommentID(getChapterID());
                        checkAccountID(getChapterID());
                        checkLODType(getChapterID());
                        getChildCommentCount();
                        getChildCommentIDCount();
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
    public void openChildCommentActivity(Comment comment){
        Intent intent = new Intent(CommentActivity.this,ChildCommentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("chapterID",getChapterID());
        bundle.putSerializable("comicID",getComicID());
        bundle.putSerializable("parent_commentID",String.valueOf(comment.getCommentID()));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        checkAccountID(getChapterID());
//        checkCommentID(getChapterID());
//        checkLODType(getChapterID());
//        getAllRootCommentInTheChapter();
//        getAccountRootCommentInTheChapter();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getAllRootCommentInTheChapter();
        getAccountRootCommentInTheChapter();
        checkCommentID(getChapterID());
        checkAccountID(getChapterID());
        checkLODType(getChapterID());
        getChildCommentCount();
        getChildCommentIDCount();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
