package hung.deptrai.comicapp.views.adapter;

import android.annotation.SuppressLint;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

import hung.deptrai.comicapp.R;
import hung.deptrai.comicapp.Utils.Tmp;
import hung.deptrai.comicapp.model.Account;
import hung.deptrai.comicapp.model.Chapter;
import hung.deptrai.comicapp.model.Comment;
import hung.deptrai.comicapp.views.Interface.IClickComicReply;
import hung.deptrai.comicapp.views.Interface.IClickComment;

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.ComicViewHolder>{
    private List<Comment> commentList;
    private List<Account> accountList;
    private List<Integer> accountID,commentID,childCommentCount,childCommentIDCount;
    private List<Boolean> lod;
    private List<Chapter> chapterList;
    private IClickComment iClickComment;
    private IClickComicReply iClickComicReply;
    public void setData(List<Chapter> chapterList,List<Comment> commentList, List<Account> accountList, List<Integer> accountID, List<Boolean> lod, List<Integer> commentID, List<Integer> childCommentCount, List<Integer> childCommentIDCount, IClickComment iClickComment,IClickComicReply iClickComicReply){
        this.commentList = commentList;
        this.chapterList = chapterList;
        this.iClickComment = iClickComment;
        this.accountList = accountList;
        this.accountID = accountID;
        this.lod = lod;
        this.commentID = commentID;
        this.childCommentCount = childCommentCount;
        this.childCommentIDCount = childCommentIDCount;
        this.iClickComicReply = iClickComicReply;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ComicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment_comic,parent,false);
        return new ComicViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ComicViewHolder holder, int position) {
        if (commentList == null || position < 0 ||
                position >= commentList.size() || accountList == null ||
                position >= accountList.size() || chapterList == null) {
            return;
        }
        Account account = accountList.get(position);
        Comment comment = commentList.get(position);
        if(position>=chapterList.size()){
            holder.chapter_comment_position.setVisibility(View.GONE);
        }
        else {
            Chapter chapter = chapterList.get(position);
            holder.item_reply_count.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(iClickComicReply!=null){
                        iClickComicReply.onClickCountReply(comment,chapter);
                    }
                }
            });
            holder.reply_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(iClickComicReply!=null){
                        iClickComicReply.onClickReply(comment,chapter);
                    }
                }
            });
            if(chapter == null){
                holder.chapter_comment_position.setVisibility(View.GONE);
            }
            else{
                holder.chapter_comment_position.setVisibility(View.VISIBLE);
                holder.chapter_comment_position.setText("Chapter "+chapter.getChapter_pos());
                Log.e("Error","commentcontent:"+comment.getContent()+"|chapter"+chapter.getChapter_pos());

            }
        }
        Boolean status = false;
//        Glide,ảnh người dùng,cần phát triển thêm
        Glide.with(holder.user_image).load(account.getAvatarAccount()).into(holder.user_image);
        holder.username.setText(account.getUsername());
//số comment con
        if(childCommentIDCount!=null && childCommentCount!=null) {
            for (int i = 0; i < childCommentIDCount.size(); i++) {
                if (childCommentIDCount.get(i) == comment.getCommentID()) {
                    holder.reply_count.setText(String.valueOf(childCommentCount.get(i)));
                }
            }
        }
        holder.comment_content.setText(comment.getContent());
//        holder.comment_time.setText(String.valueOf(comment.getUpdated_at()));

        long currentTime = System.currentTimeMillis();

// Lấy thời gian của comment
        long commentTime = comment.getUpdated_at().getTime(); // Đảm bảo thời gian là long hoặc chuyển đổi thích hợp

// Tính khoảng thời gian giữa hai thời điểm
        long timeDifference = currentTime - commentTime;

// Chuyển khoảng thời gian sang đơn vị phù hợp và hiển thị
        String timeAgo = DateUtils.getRelativeTimeSpanString(commentTime, currentTime, DateUtils.MINUTE_IN_MILLIS).toString();
        holder.comment_time.setText(timeAgo);
        holder.like_count.setText(String.valueOf(comment.getLike()));
        holder.dislike_count.setText(String.valueOf(comment.getDislike()));
        holder.likebtn.setImageResource(R.drawable.ic_like);
        holder.dislikebtn.setImageResource(R.drawable.ic_dislike);
        if(commentID != null && accountID != null && lod != null) {
            for (int i = 0; i < commentID.size(); i++) {
                Log.e("comment|commentID",String.valueOf(comment.getCommentID())+"|"+String.valueOf(commentID.get(i)));
                if (comment.getCommentID() == commentID.get(i)) {
                    Log.e("status","true");
                    if (accountID.get(i) == Integer.parseInt(Tmp.account_id)) {
                        if (lod.get(i) == true) {
//                            holder.like_count.setText(":)");
                            status = true;
                            holder.likebtn.setImageResource(R.drawable.ic_liked);
                        } else {
                            status = true;
//                            holder.dislike_count.setText(":(");
                            holder.dislikebtn.setImageResource(R.drawable.ic_disliked);
                        }
                        break;
                    }
                }
            }
        }
        comment.setIslod(status);
        holder.likebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iClickComment!=null){
                    iClickComment.onClickLikeBtn(comment);
                }
            }
        });
        holder.dislikebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iClickComment!=null){
                    iClickComment.onClickDisLikeBtn(comment);
                }
            }
        });
        holder.reply_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iClickComment!=null){
                    iClickComment.onClickReply(comment);
                }
            }
        });
        holder.item_reply_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iClickComment!=null){
                    iClickComment.onClickCountReply(comment);
                }
            }
        });
        holder.item_comment.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(iClickComment!=null){
                    iClickComment.onLongClickComment(comment);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        if(commentList!=null){
            return commentList.size();
        }
        else {
            return 0;
        }
    }

    public class ComicViewHolder extends RecyclerView.ViewHolder{
        private ShapeableImageView user_image;
        private TextView username,comment_content,comment_time,reply_text,reply_reply_text,reply_count,like_count,dislike_count,chapter_comment_position;
        private LinearLayout item_reply_count,item_comment;

        private ImageButton likebtn,dislikebtn;
        public ComicViewHolder(@NonNull View itemView) {
            super(itemView);
            item_comment = itemView.findViewById(R.id.item_comment_comic);
            user_image = itemView.findViewById(R.id.user_image_comic);
            reply_reply_text = itemView.findViewById(R.id.reply_reply_text_comic);
            username = itemView.findViewById(R.id.user_name_cmt_comic);
            comment_content = itemView.findViewById(R.id.comment_content_comic);
            comment_time = itemView.findViewById(R.id.comment_time_comic);
            reply_text = itemView.findViewById(R.id.reply_text_comic);
            reply_count = itemView.findViewById(R.id.reply_count_comic);
            like_count = itemView.findViewById(R.id.like_count_comic);
            likebtn = itemView.findViewById(R.id.like_icon_comic);
            dislikebtn = itemView.findViewById(R.id.dislike_icon_comic);
            dislike_count = itemView.findViewById(R.id.dislike_count_comic);
            item_reply_count = itemView.findViewById(R.id.item_reply_count_comic);
            chapter_comment_position = itemView.findViewById(R.id.chapter_comment_position);
            likebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(iClickComment!=null){
                        iClickComment.onClickLikeBtn(commentList.get(getAdapterPosition()));
                    }
                }
            });
            dislikebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(iClickComment!=null){
                        iClickComment.onClickDisLikeBtn(commentList.get(getAdapterPosition()));
                    }
                }
            });
            reply_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(iClickComment!=null){
                        iClickComment.onClickReply(commentList.get(getAdapterPosition()));
                    }
                }
            });
            item_reply_count.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(iClickComment!=null){
                        iClickComment.onClickCountReply(commentList.get(getAdapterPosition()));
                    }
                }
            });
            item_comment.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(iClickComment!=null){
                        iClickComment.onLongClickComment(commentList.get(getAdapterPosition()));
                    }
                    return true;
                }
            });
        }
    }
}
