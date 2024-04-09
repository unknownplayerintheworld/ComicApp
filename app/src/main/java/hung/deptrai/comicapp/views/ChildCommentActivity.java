package hung.deptrai.comicapp.views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;

import hung.deptrai.comicapp.R;
import hung.deptrai.comicapp.Utils.Tmp;
import hung.deptrai.comicapp.model.Account;
import hung.deptrai.comicapp.model.Comment;
import hung.deptrai.comicapp.viewmodel.CommentViewModel;
import hung.deptrai.comicapp.views.Interface.IClickComment;
import hung.deptrai.comicapp.views.Interface.iOptionComment;
import hung.deptrai.comicapp.views.adapter.ChildCommentAdapter;
import hung.deptrai.comicapp.views.fragment.BottomSheetOptionComment;

public class ChildCommentActivity extends AppCompatActivity {
    private ChildCommentAdapter childCommentAdapter;
    private CommentViewModel commentViewModel;
    private RecyclerView recyclerView;
    private List<Comment> comments;
    private List<Account> accounts;
    private TextInputEditText commentWritting_reply;
    private ShapeableImageView user_image_parent;
    private Snackbar snackbar,snackbar2;
    private List<Integer> accountIDList,commentIDList,parrentAccountIDList,parent_commentIDList;
    private List<Boolean> booleanList,parent_booleanList;
    private Comment parent;

    // Parent comment item components!
    private TextView user_name_cmt_parent,comment_time_parent,comment_content_parent,like_count_parent,dislike_count_parent;
    private ImageButton like_icon_parent,dislike_icon_parent,imageButton_send_reply,btn_back_comment_child;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_child);
        //
        commentViewModel = new ViewModelProvider(this).get(CommentViewModel.class);
        // snackbar
        View view = findViewById(android.R.id.content);
        snackbar = Snackbar.make(view,"Thành công", BaseTransientBottomBar.LENGTH_SHORT);
        snackbar2 = Snackbar.make(view,"Thất Bại",BaseTransientBottomBar.LENGTH_SHORT);

        //UI components
        user_image_parent = findViewById(R.id.user_image_parent);
        commentWritting_reply = findViewById(R.id.comment_reply_txt);
        user_name_cmt_parent = findViewById(R.id.user_name_cmt_parent);
        comment_time_parent = findViewById(R.id.comment_time_parent);
        comment_content_parent = findViewById(R.id.comment_content_parent);
        like_count_parent = findViewById(R.id.like_count_parent);
        dislike_count_parent = findViewById(R.id.dislike_count_parent);
        like_icon_parent = findViewById(R.id.like_icon_parent);
        dislike_icon_parent = findViewById(R.id.dislike_icon_parent);
        imageButton_send_reply = findViewById(R.id.imageButton_send_reply);
        btn_back_comment_child = findViewById(R.id.btn_back_comment_child);


        getAllChildCommentInTheChapter();
        getAccountChildCommentInTheChapter();
        checkCommentID(getChapterID());
        checkAccountID(getChapterID());
        checkLODType(getChapterID());
        //RCV and Adapter set up
        childCommentAdapter = new ChildCommentAdapter();
        recyclerView = findViewById(R.id.rcv_comment_list_child);
        childCommentAdapter.setData(comments, accounts, accountIDList, booleanList, commentIDList, new IClickComment() {
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
        LinearLayoutManager lnm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lnm);
        recyclerView.setAdapter(childCommentAdapter);

        btn_back_comment_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageButton_send_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replyComment();
            }
        });
        getAccountAndCommentParent();
        // UI component for parent comment item!
        like_icon_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean lodType = true;
                updateLODIncParentItem(parent,lodType);
                updateLODDescParentItem(parent,lodType);
            }
        });
        dislike_icon_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean lodType = false;
                updateLODIncParentItem(parent,lodType);
                updateLODDescParentItem(parent,lodType);
            }
        });
    }
    public void getAccountAndCommentParent(){
        HashMap<String,String> hashmap = new HashMap<>();
        hashmap.put("commentID",getParentCommentID());
        commentViewModel.getAccountByCommentID(hashmap).observe(this,accountList -> {
            user_name_cmt_parent.setText(accountList.get(0).getUsername());
            Glide.with(user_image_parent).load(accountList.get(0).getAvatarAccount()).into(user_image_parent);
        });
        commentViewModel.getCommentByCommentID(hashmap).observe(this,comments1 -> {
            parent = comments1.get(0);
            parent.setIslod(false);
            long currentTime = System.currentTimeMillis();

// Lấy thời gian của comment
            long commentTime = comments1.get(0).getUpdated_at().getTime(); // Đảm bảo thời gian là long hoặc chuyển đổi thích hợp

// Tính khoảng thời gian giữa hai thời điểm
            long timeDifference = currentTime - commentTime;

// Chuyển khoảng thời gian sang đơn vị phù hợp và hiển thị
            String timeAgo = DateUtils.getRelativeTimeSpanString(commentTime, currentTime, DateUtils.MINUTE_IN_MILLIS).toString();
            comment_time_parent.setText(timeAgo);
            comment_content_parent.setText(comments1.get(0).getContent());
            like_count_parent.setText(String.valueOf(comments1.get(0).getLike()));
            dislike_count_parent.setText(String.valueOf(comments1.get(0).getDislike()));
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("comicID",getComicID());
            commentViewModel.checkCommentIDLODComic(hashMap).observe(this,integers -> {
                parent_commentIDList = integers;
            });
            commentViewModel.checkAccountLODComic(hashMap).observe(this,integers -> {
                parrentAccountIDList = integers;
            });
            commentViewModel.checkLODTypeLODComic(hashMap).observe(this,booleans -> {
                parent_booleanList = booleans;
            });
            if(parent_booleanList != null && parent_commentIDList!=null && parrentAccountIDList!=null && parent != null){
                for(int i = 0; i < parent_commentIDList.size(); i++){
                    if(parent.getCommentID() == parent_commentIDList.get(i)){
                        if(parrentAccountIDList.get(i) == Integer.parseInt(Tmp.account_id)){
                            if(parent_booleanList.get(i) == true){
                                like_icon_parent.setImageResource(R.drawable.ic_liked);
                                parent.setIslod(true);
                            }
                            else{
                                dislike_icon_parent.setImageResource(R.drawable.ic_disliked);
                                parent.setIslod(true);
                            }
                            break;
                        }
                    }
                }
            }
        });
    }
    public String getParentCommentID(){
        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return "";
        }
        return bundle.getString("parent_commentID");
    }

    public void getAllChildCommentInTheChapter(){
        HashMap<String,String> hashmap = new HashMap<>();
        hashmap.put("parent_commentID",getParentCommentID());
        commentViewModel.getAllChildComment(hashmap).observe(this,comments1 -> {
            comments = comments1;
            updateUI();
        });
    }

    public void getAccountChildCommentInTheChapter(){
        HashMap<String,String> hashmap = new HashMap<>();
        hashmap.put("parent_commentID",getParentCommentID());
        commentViewModel.getAccountChild(hashmap).observe(this,accounts1 -> {
            accounts = accounts1;
            updateUI();
        });
    }
    private void updateUI() {
        childCommentAdapter.setData(comments, accounts, accountIDList, booleanList, commentIDList, new IClickComment() {
            @Override
            public void onClickReply(Comment comment) {

            }

            @Override
            public void onClickCountReply(Comment comment) {

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

    private void openBottomSheetOptionComment(Comment comment) {
        if(comment.getAccountID() == Integer.parseInt(Tmp.account_id)) {
            BottomSheetOptionComment bsoc = new BottomSheetOptionComment(new iOptionComment() {
                @Override
                public void onLongClick(Boolean status) {
                    if (status == true) {
                        checkAccountID(getChapterID());
                        checkCommentID(getChapterID());
                        checkLODType(getChapterID());
                        getAllChildCommentInTheChapter();
                        getAccountChildCommentInTheChapter();
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


    @SuppressLint("ResourceAsColor")
    public void replyComment(){
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(commentWritting_reply.getWindowToken(), 0);
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("accountID", Tmp.account_id);
        hashMap.put("chapterID",getChapterID());
        hashMap.put("comicID",getComicID());
        hashMap.put("content",commentWritting_reply.getText().toString());
        hashMap.put("parent_commentID",getParentCommentID());
        if(commentWritting_reply.getText().toString().isEmpty()){
            commentWritting_reply.setError("Comment cannot be empty!");
            commentWritting_reply.requestFocus();
        }
        else{
            commentViewModel.replyComment(hashMap).observe(this,aBoolean -> {
                if(aBoolean == true){
                    snackbar.setBackgroundTint(R.color.emerald_green);
                    snackbar.show();
                    getAllChildCommentInTheChapter();
                    getAccountChildCommentInTheChapter();
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

    private String getChapterID() {
        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return "";
        }
        return bundle.getString("chapterID");
    }
    private String getComicID() {
        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return "";
        }
        return bundle.getString("comicID");
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
                comment.setIslod(true);
                checkAccountID(getChapterID());
                checkCommentID(getChapterID());
                checkLODType(getChapterID());
                getAccountChildCommentInTheChapter();
                getAllChildCommentInTheChapter();
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
                comment.setIslod(false);
                checkAccountID(getChapterID());
                checkCommentID(getChapterID());
                checkLODType(getChapterID());
                getAllChildCommentInTheChapter();
                getAccountChildCommentInTheChapter();
            });
        }
    }
    public void updateLODIncParentItem(Comment comment,Boolean lodType){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("commentID",String.valueOf(comment.getCommentID()));
        hashMap.put("lodType",lodType);
        hashMap.put("accountID",Tmp.account_id);
        if(comment.getIslod() == false) {
            commentViewModel.updateLODInc(hashMap).observe(this, aBoolean -> {
                if (aBoolean == true) {
                    Log.e("update dislike","success");
                    if(lodType == true){
//                        like_count_parent.setText(String.valueOf(Integer.parseInt((String) like_count_parent.getText())+1));
                        commentViewModel.getCommentByCommentID(hashMap).observe(this,comments1 -> {
                            for (int i = 0; i < comments1.size(); i++) {
                                if(comment.getCommentID() == comments1.get(i).getCommentID()){
                                    like_count_parent.setText(String.valueOf(comments1.get(i).getLike()));
                                    like_icon_parent.setImageResource(R.drawable.ic_liked);
                                }
                            }
                        });
                    }
                    else{
                        commentViewModel.getCommentByCommentID(hashMap).observe(this,comments1 -> {
                            for (int i = 0; i < comments1.size(); i++) {
                                if(comment.getCommentID() == comments1.get(i).getCommentID()){
                                    dislike_count_parent.setText(String.valueOf(comments1.get(i).getDislike()));
                                    dislike_icon_parent.setImageResource(R.drawable.ic_disliked);
                                }
                            }
                        });
//                        dislike_count_parent.setText(String.valueOf(Integer.parseInt((String) dislike_count_parent.getText())+1));
                    }
//                    getAccountAndCommentParent();
                }
                else{
                    Log.e("update dislike","failed");
                }
                parent.setIslod(true);
            });
        }
    }
    public void updateLODDescParentItem(Comment comment,Boolean lodType){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("commentID",String.valueOf(comment.getCommentID()));
        hashMap.put("lodType",lodType);
        hashMap.put("accountID",Tmp.account_id);
        if(comment.getIslod() == true) {
            commentViewModel.updateLODDesc(hashMap).observe(this, aBoolean -> {
                if (aBoolean == true) {
                    Log.e("update dislike","success");
                    if(lodType == true){
//                        like_count_parent.setText(String.valueOf(Integer.parseInt((String) like_count_parent.getText())-1));
                        commentViewModel.getCommentByCommentID(hashMap).observe(this,comments1 -> {
                            for (int i = 0; i < comments1.size(); i++) {
                                if(comment.getCommentID() == comments1.get(i).getCommentID()){
                                    like_count_parent.setText(String.valueOf(comments1.get(i).getLike()));
                                    like_icon_parent.setImageResource(R.drawable.ic_like);
                                }
                            }
                        });
                    }
                    else{
                        commentViewModel.getCommentByCommentID(hashMap).observe(this,comments1 -> {
                            for (int i = 0; i < comments1.size(); i++) {
                                if(comment.getCommentID() == comments1.get(i).getCommentID()){
                                    dislike_count_parent.setText(String.valueOf(comments1.get(i).getDislike()));
                                    dislike_icon_parent.setImageResource(R.drawable.ic_dislike);
                                }
                            }
                        });
//                        dislike_count_parent.setText(String.valueOf(Integer.parseInt((String) dislike_count_parent.getText())-1));
                    }
//                    getAccountAndCommentParent();
                }
                else{
                    Log.e("update dislike","failed");
                }
                parent.setIslod(false);

            });
        }
    }
    private void checkCommentID(String chapterID) {
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("comicID",getComicID());
        commentViewModel.checkCommentIDLODComic(hashMap).observe(this,integers -> {
            commentIDList = integers;
            updateUI();
        });
    }

    private void checkLODType(String chapterID) {
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("comicID",getComicID());
        commentViewModel.checkLODTypeLODComic(hashMap).observe(this,integers -> {
            booleanList = integers;
            updateUI(); // 100 là thời gian tính bằng mili giây, bạn có thể điều chỉnh thời gian này tùy theo nhu cầu của bạn

        });
    }

    private void checkAccountID(String chapterID) {
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("comicID",getComicID());
        commentViewModel.checkAccountLODComic(hashMap).observe(this,integers -> {
            accountIDList = integers;
            updateUI(); // 100 là thời gian tính bằng mili giây, bạn có thể điều chỉnh thời gian này tùy theo nhu cầu của bạn

        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
