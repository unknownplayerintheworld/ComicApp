package hung.deptrai.comicapp.views.fragment.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import hung.deptrai.comicapp.R;
import hung.deptrai.comicapp.model.Comic;
import hung.deptrai.comicapp.views.Interface.IClickComic;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder>{
    private List<Comic> list;
    private IClickComic iClickComic;
    public void setData(List<Comic> comic,IClickComic iClickComic){
        this.iClickComic = iClickComic;
        this.list = comic;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_comic,parent,false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Comic comic_ = list.get(position);
        if(comic_ == null){
            return;
        }
        else{
            Glide.with(holder.img).load(comic_.getLinkPicture()).into(holder.img);
            holder.tv.setText(comic_.getComicName());
            holder.item_layout_search_comic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(iClickComic != null){
                        iClickComic.onClickComic(comic_);
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

    public class SearchViewHolder extends RecyclerView.ViewHolder{
        private ConstraintLayout item_layout_search_comic;
        private ImageView img;
        private TextView tv;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            item_layout_search_comic = itemView.findViewById(R.id.layoutItem);
            img = itemView.findViewById(R.id.img_comic_search);
            tv = itemView.findViewById(R.id.tv_Comic_Title);
            item_layout_search_comic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(iClickComic != null){
                        iClickComic.onClickComic(list.get(getAdapterPosition()));
                    }
                }
            });
        }
    }
}
