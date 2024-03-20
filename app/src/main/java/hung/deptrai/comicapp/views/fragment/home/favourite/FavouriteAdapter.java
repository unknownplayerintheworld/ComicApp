package hung.deptrai.comicapp.views.fragment.home.favourite;

import android.util.Log;
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
import hung.deptrai.comicapp.model.Favourite;
import hung.deptrai.comicapp.views.Interface.IClickComic;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder>{
//    private List<Favourite> list;
    private List<Comic> favlist;
    private IClickComic iClickComic;
//    public void setData(List<Favourite> list){
//        this.list = list;
//        notifyDataSetChanged();
//    }
    public void setFavourite(List<Comic> list,IClickComic iClickComic){
        this.favlist = list;
        this.iClickComic = iClickComic;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favourite,parent,false);
        return new FavouriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position) {
        Comic comic = favlist.get(position);
        if(comic == null){
            Log.e("COMIC","Error");
            return;
        }
        else{
            holder.tv.setText(comic.getComicName());
            Log.e("COMIC",comic.getComicName());
            Glide.with(holder.img.getContext()).load(comic.getLinkPicture()).into(holder.img);
            holder.ln.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(iClickComic != null){
                        iClickComic.onClickComic(comic);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(favlist != null){
            return favlist.size();
        }
        else {
            return 0;
        }
    }

    public class FavouriteViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout ln;
        private ImageView img;
        private TextView tv;
        public FavouriteViewHolder(@NonNull View itemView) {
            super(itemView);
            ln = itemView.findViewById(R.id.fav);
            img = itemView.findViewById(R.id.img_fav);
            tv = itemView.findViewById(R.id.tv_fav);
            ln.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(iClickComic != null){
                        iClickComic.onClickComic(favlist.get(getAdapterPosition()));
                    }
                }
            });
        }
    }
}
