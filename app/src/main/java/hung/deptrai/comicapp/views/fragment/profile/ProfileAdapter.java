package hung.deptrai.comicapp.views.fragment.profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import hung.deptrai.comicapp.R;
import hung.deptrai.comicapp.model.Chapter;
import hung.deptrai.comicapp.model.Comic;
import hung.deptrai.comicapp.model.History;
import hung.deptrai.comicapp.views.Interface.IClickComic;
import hung.deptrai.comicapp.views.Interface.IClickHistory;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>{
    private List<Comic> list;
    private List<Chapter> listChapters;
    private IClickHistory iClickComic;
    public void setData(List<Comic> list,List<Chapter> chapterList,IClickHistory iClickComic){
        this.iClickComic = iClickComic;
        this.list = list;
        this.listChapters = chapterList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history,parent,false);
        return new ProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {
        if(list == null || list.isEmpty() || position >= list.size()){
            return ;
        }
        if(listChapters == null || listChapters.isEmpty() || position >= listChapters.size()){
            return ;
        }
        Comic history = list.get(position);
        Chapter chapter = listChapters.get(position);
        if(history == null || chapter ==null){
            return;
        }
        else{
            holder.tvHistoryTitle.setText(history.getComicName());
            Glide.with(holder.imgHistory).load(history.getLinkPicture()).into(holder.imgHistory);
            holder.itemHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(iClickComic != null){
                        iClickComic.onClickHistory(chapter,history);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(list == null) {
            return 0;
        }
        else{
            return list.size();
        }
    }

    public class ProfileViewHolder extends RecyclerView.ViewHolder{
        private TextView tvHistoryTitle;
        private ImageView imgHistory;
        private LinearLayout itemHistory;
        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHistoryTitle = itemView.findViewById(R.id.tvHistoryTitle);
            imgHistory = itemView.findViewById(R.id.imgHistory);
            itemHistory = itemView.findViewById(R.id.itemHistory);
            itemHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(iClickComic != null){
                        iClickComic.onClickHistory(listChapters.get(getAdapterPosition()),list.get(getAdapterPosition()));
                    }
                }
            });
        }
    }
}
