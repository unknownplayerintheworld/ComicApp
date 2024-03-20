package hung.deptrai.comicapp.views.adapter;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.divider.MaterialDivider;

import java.util.List;

import hung.deptrai.comicapp.R;
import hung.deptrai.comicapp.model.Chapter;
import hung.deptrai.comicapp.views.Interface.IClickChapter;

public class SelectChapBottomSheetAdapter extends RecyclerView.Adapter<SelectChapBottomSheetAdapter.SelectChapBSheetViewHolder>{
    private List<Chapter> listchapter,historyChapter;
    private IClickChapter iclickchapter;
    public void setData(List<Chapter> listchapter,List<Chapter> historyChapter,IClickChapter iclick){
        this.historyChapter = historyChapter;
        this.iclickchapter = iclick;
        this.listchapter = listchapter;
        notifyDataSetChanged();
    }
    public void setOnItemClickListener(IClickChapter iclickchapter) {
        this.iclickchapter = iclickchapter;
    }
    @NonNull
    @Override
    public SelectChapBSheetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bottom_sheet,parent,false);
        return new SelectChapBSheetViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull SelectChapBSheetViewHolder holder, int position) {
        Chapter chapter = listchapter.get(position);
        if(chapter == null){
            return ;
        }
        else{
            holder.chapter.setText("Chapter "+chapter.getChapter_pos());
            holder.chapter.setTextSize(18);
            if(historyChapter == null){

            }
            else if(historyChapter.size()>0 && historyChapter.get(0).getChapterID() == chapter.getChapterID()){
                holder.chapter.setTextColor(R.color.purple_82);
                holder.chapter.setTypeface(null, Typeface.BOLD);
                holder.chapter.setTextSize(18);
            }
            else{
                holder.chapter.setTextColor(R.color.black);
                holder.chapter.setTypeface(null, Typeface.NORMAL);
                holder.chapter.setTextSize(18);
            }
            holder.item_chapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(iclickchapter != null){
                        iclickchapter.onClickChapter(chapter);
                        Toast.makeText(v.getContext(), "click "+chapter.getChapter_pos(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(listchapter!=null){
            return listchapter.size();
        }
        else{
            return 0;
        }
    }

    public class SelectChapBSheetViewHolder extends RecyclerView.ViewHolder{

        private TextView chapter;
        private ConstraintLayout item_chapter;
        public SelectChapBSheetViewHolder(@NonNull View itemView) {
            super(itemView);
            chapter = itemView.findViewById(R.id.txt_bottomsheet);
            item_chapter = itemView.findViewById(R.id.item_bts);
            item_chapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(iclickchapter != null){
                        iclickchapter.onClickChapter(listchapter.get(getAdapterPosition()));
                    }
                }
            });
        }
    }
}
