package hung.deptrai.comicapp.views.fragment.home.journey.Shounen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import hung.deptrai.comicapp.R;
import hung.deptrai.comicapp.model.Comic;
import hung.deptrai.comicapp.views.Interface.IClickComic;

public class ShounenAdapter extends RecyclerView.Adapter<ShounenAdapter.ShounenViewHolder>{
    private List<Comic> list;
    private IClickComic iClickComic;
    public void setData(List<Comic> list,IClickComic iClickComic){
        this.iClickComic = iClickComic;
        this.list = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ShounenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shounen,parent,false);
        return new ShounenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShounenViewHolder holder, int position) {
        Comic comic = list.get(position);
        if(comic == null){
            return;
        }
        else{
            Glide.with(holder.img_shounen).load(comic.getLinkPicture()).into(holder.img_shounen);
            holder.ComicName.setText(comic.getComicName());
            holder.shounen_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(iClickComic!= null){
                        iClickComic.onClickComic(comic);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(list!=null){
            return list.size();
        }
        else{
            return 0;
        }
    }

    public class ShounenViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout shounen_layout;
        private ImageView img_shounen;
        private TextView ComicName;
        public ShounenViewHolder(@NonNull View itemView) {
            super(itemView);
            shounen_layout = itemView.findViewById(R.id.shounen);
            img_shounen = itemView.findViewById(R.id.img_shounen);
            ComicName = itemView.findViewById(R.id.tv_shounen_title);
            shounen_layout.setOnClickListener(new View.OnClickListener() {
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
