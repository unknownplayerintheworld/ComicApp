package hung.deptrai.comicapp.views.adapter;

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
import hung.deptrai.comicapp.model.Comment;
import hung.deptrai.comicapp.views.Interface.IClickComment;

public class ChildCommentAdapter extends RecyclerView.Adapter<ChildCommentAdapter.ChildCommentViewHolder>{
    private List<Comment> commentList;
    private List<Account> accountList;
    private List<Integer> accountID,commentID;
    private List<Boolean> lod;
    private IClickComment iClickComment;
    public void setData(List<Comment> commentList,List<Account> accountList,List<Integer> accountID,List<Boolean> lod,List<Integer> commentID,IClickComment iClickComment){
        this.commentList = commentList;
        this.iClickComment = iClickComment;
        this.accountList = accountList;
        this.accountID = accountID;
        this.lod = lod;
        this.commentID = commentID;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ChildCommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_child_comment,parent,false);
        return new ChildCommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildCommentViewHolder holder, int position) {
        if (commentList == null || position < 0 ||
                position >= commentList.size() || accountList == null ||
                position >= accountList.size()) {
            return;
        }
        Account account = accountList.get(position);
        Comment comment = commentList.get(position);
        Boolean status = false;
//        Glide,ảnh người dùng,cần phát triển thêm
        Glide.with(holder.user_image_child).load(account.getAvatarAccount()).into(holder.user_image_child);
        holder.username_child.setText(account.getUsername());
//số comment con        holder.reply_count.setText(comment.);
        holder.comment_content_child.setText(comment.getContent());
//        holder.comment_time.setText(String.valueOf(comment.getUpdated_at()));

        long currentTime = System.currentTimeMillis();

// Lấy thời gian của comment
        long commentTime = comment.getUpdated_at().getTime(); // Đảm bảo thời gian là long hoặc chuyển đổi thích hợp

// Tính khoảng thời gian giữa hai thời điểm
        long timeDifference = currentTime - commentTime;

// Chuyển khoảng thời gian sang đơn vị phù hợp và hiển thị
        String timeAgo = DateUtils.getRelativeTimeSpanString(commentTime, currentTime, DateUtils.MINUTE_IN_MILLIS).toString();
        holder.comment_time_child.setText(timeAgo);
        holder.like_count_child.setText(String.valueOf(comment.getLike()));
        holder.dislike_count_child.setText(String.valueOf(comment.getDislike()));
        holder.likebtn_child.setImageResource(R.drawable.ic_like);
        holder.dislikebtn_child.setImageResource(R.drawable.ic_dislike);
        if(commentID != null && accountID != null && lod != null) {
            if(commentID.size() == accountID.size() && accountID.size() == lod.size()) {
                for (int i = 0; i < commentID.size(); i++) {
                    Log.e("comment|commentID", String.valueOf(comment.getCommentID()) + "|" + String.valueOf(commentID.get(i)));
                    if (comment.getCommentID() == commentID.get(i)) {
                        Log.e("status", "true");
                        if (accountID.get(i) == Integer.parseInt(Tmp.account_id)) {
                            if (lod.get(i) == true) {
                                //                            holder.like_count.setText(":)");
                                status = true;
                                holder.likebtn_child.setImageResource(R.drawable.ic_liked);
                            } else {
                                status = true;
                                //                            holder.dislike_count.setText(":(");
                                holder.dislikebtn_child.setImageResource(R.drawable.ic_disliked);
                            }
                            break;
                        }
                    }
                }
            }
        }
        comment.setIslod(status);
        holder.likebtn_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iClickComment!=null){
                    iClickComment.onClickLikeBtn(comment);
                }
            }
        });
        holder.dislikebtn_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iClickComment!=null){
                    iClickComment.onClickDisLikeBtn(comment);
                }
            }
        });
        holder.item_comment_child.setOnLongClickListener(new View.OnLongClickListener() {
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

    public class ChildCommentViewHolder extends RecyclerView.ViewHolder{
        private ShapeableImageView user_image_child;
        private TextView username_child,comment_content_child,comment_time_child,like_count_child,dislike_count_child;
        private LinearLayout item_comment_child;

        private ImageButton likebtn_child,dislikebtn_child;
        public ChildCommentViewHolder(@NonNull View itemView) {
            super(itemView);
            item_comment_child = itemView.findViewById(R.id.item_comment_child);
            user_image_child = itemView.findViewById(R.id.user_image_child);
            username_child = itemView.findViewById(R.id.user_name_cmt_child);
            comment_content_child = itemView.findViewById(R.id.comment_content_child);
            comment_time_child = itemView.findViewById(R.id.comment_time_child);
            like_count_child = itemView.findViewById(R.id.like_count_child);
            likebtn_child = itemView.findViewById(R.id.like_icon_child);
            dislikebtn_child = itemView.findViewById(R.id.dislike_icon_child);
            dislike_count_child = itemView.findViewById(R.id.dislike_count_child);
            likebtn_child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(iClickComment!=null){
                        iClickComment.onClickLikeBtn(commentList.get(getAdapterPosition()));
                    }
                }
            });
            dislikebtn_child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(iClickComment!=null){
                        iClickComment.onClickDisLikeBtn(commentList.get(getAdapterPosition()));
                    }
                }
            });
            item_comment_child.setOnLongClickListener(new View.OnLongClickListener() {
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
