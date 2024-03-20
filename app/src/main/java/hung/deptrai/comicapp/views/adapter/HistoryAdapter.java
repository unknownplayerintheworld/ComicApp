package hung.deptrai.comicapp.views.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

import hung.deptrai.comicapp.R;
import hung.deptrai.comicapp.model.Author;
import hung.deptrai.comicapp.model.Chapter;
import hung.deptrai.comicapp.model.Comic;
import hung.deptrai.comicapp.model.History;
import hung.deptrai.comicapp.views.Interface.IClickComic;
import hung.deptrai.comicapp.views.Interface.IClickHistory;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>{
    private List<Comic> listComic;
    private List<Chapter> listChapter;
    private List<Author> listAuthor;
    private IClickHistory iClickComic;
    public void setData(List<Comic> list,List<Chapter> listChapter,List<Author> listAuthor,IClickHistory iClickComic){
        this.iClickComic = iClickComic;
        this.listComic = list;
        this.listChapter = listChapter;
        this.listAuthor = listAuthor;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_activity,parent,false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        if (listComic == null || listChapter == null || listAuthor == null || position < 0 ||
                position >= listComic.size() || position >= listChapter.size() || position >= listAuthor.size()) {
            return;
        }
        Comic comic = listComic.get(position);
        Chapter chapter = listChapter.get(position);
        Author author = listAuthor.get(position);
            Glide.with(holder.img).load(comic.getLinkPicture()).into(holder.img);
            holder.comicname.setText(comic.getComicName());
            holder.current_reading_chapter.setText("Chapter "+String.valueOf(chapter.getChapter_pos()));
            holder.author_name.setText(author.getAuthor_name());
            holder.views.setText(String.valueOf(comic.getViews()));
            holder.item_history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(iClickComic != null){
                        iClickComic.onClickHistory(chapter,comic);
                    }
                }
            });
            holder.del_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(iClickComic != null){
                        iClickComic.onClickDelHistory(comic);
                    }
                }
            });
        }


    @Override
    public int getItemCount() {
        if(listComic != null){
            return listComic.size();
        }
        else {
            return 0;
        }
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder{
        private RelativeLayout item_history;
        private ShapeableImageView img;
        private TextView comicname;
        private TextView current_reading_chapter,author_name;
        private TextView views;
        private AppCompatButton del_btn;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            item_history = itemView.findViewById(R.id.item_history_activity);
            img = itemView.findViewById(R.id.img_item_history_activity);
            comicname = itemView.findViewById(R.id.item_history_activity_title);
            current_reading_chapter = itemView.findViewById(R.id.item_history_activity_chapter);
            author_name = itemView.findViewById(R.id.item_history_activity_author);
            views = itemView.findViewById(R.id.item_history_activity_view);
            del_btn = itemView.findViewById(R.id.item_history_activity_delete_btn);
            del_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(iClickComic != null){
                        iClickComic.onClickDelHistory(listComic.get(getAdapterPosition()));
                    }
                }
            });
            item_history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(iClickComic != null){
                        iClickComic.onClickHistory(listChapter.get(getAdapterPosition()),listComic.get(getAdapterPosition()));
                    }
                }
            });
        }
    }
}
