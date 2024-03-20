package hung.deptrai.comicapp.views.adapter;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.divider.MaterialDivider;

import java.util.List;

import hung.deptrai.comicapp.R;
import hung.deptrai.comicapp.model.Chapter;
import hung.deptrai.comicapp.model.Comic;
import hung.deptrai.comicapp.views.Interface.IClickChapter;
import hung.deptrai.comicapp.views.Interface.IClickComic;

public class ListChapterAdapter extends RecyclerView.Adapter<ListChapterAdapter.ListChapterViewHolder>{
    private List<Chapter> listchapter;
    private List<Chapter> chapterHistory;
    private IClickChapter listener;
    public void setData(List<Chapter> listchapter,List<Chapter> chapterHistory, IClickChapter listener){
        this.listchapter = listchapter;
        this.chapterHistory = chapterHistory;
        this.listener = listener;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ListChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chapter,parent,false);
        return new ListChapterViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ListChapterViewHolder holder, int position) {
        if(listchapter == null || position>=listchapter.size()){
            return;
        }
        Chapter chap = listchapter.get(position);
        if(chap == null){
            return;
        }
        else{
            holder.chapter_txt.setText("Chapter:");
            holder.chapter.setText(chap.getChapter_pos());
            if(chapterHistory == null){

            }
            else if(chapterHistory.size()>0 && chapterHistory.get(0).getChapterID() == chap.getChapterID()){
                    holder.chapter.setTextColor(R.color.purple_82);
                    holder.chapter.setTypeface(null, Typeface.BOLD);
                    holder.chapter_txt.setTextColor(R.color.purple_82);
                    holder.chapter_txt.setTypeface(null, Typeface.BOLD);
            }
            else{
                holder.chapter.setTextColor(R.color.black);
                holder.chapter.setTypeface(null, Typeface.NORMAL);
                holder.chapter_txt.setTextColor(R.color.black);
                holder.chapter_txt.setTypeface(null, Typeface.NORMAL);
            }
            holder.item_chapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.onClickChapter(chap);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(listchapter == null) {
            return 0;
        }
        else{
            return listchapter.size();
        }
    }

    public class ListChapterViewHolder extends RecyclerView.ViewHolder{
        private TextView chapter_txt;
        private TextView chapter;
        private MaterialDivider mat;
        private ConstraintLayout item_chapter;

        public ListChapterViewHolder(@NonNull View itemView) {
            super(itemView);
            chapter_txt = itemView.findViewById(R.id.chapter_txt);
            chapter = itemView.findViewById(R.id.chapter_num);
            mat = itemView.findViewById(R.id.mat_div_comic);
            item_chapter = itemView.findViewById(R.id.item_chapter);
            item_chapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.onClickChapter(listchapter.get(getAdapterPosition()));
                    }
                }
            });
        }
    }
}
