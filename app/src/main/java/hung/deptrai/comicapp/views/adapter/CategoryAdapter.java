package hung.deptrai.comicapp.views.adapter;

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

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<Comic> listcomic;
    private IClickComic iclickcomic;
    public void setData(List<Comic> list,IClickComic iclickcomic){
        this.listcomic = list;
        this.iclickcomic = iclickcomic;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comic,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Comic comic = listcomic.get(position);
        if(comic == null){
            return ;
        }
        else{
            Glide.with(holder.img).load(comic.getLinkPicture()).into(holder.img);
            holder.tv.setText(comic.getComicName());
            holder.ln.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(iclickcomic != null){
                        iclickcomic.onClickComic(comic);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(listcomic != null){
            return listcomic.size();
        }
        else{
            return 0;
        }
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout ln;
        private ImageView img;
        private TextView tv;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ln = itemView.findViewById(R.id.item_comic);
            img = itemView.findViewById(R.id.img_comic);
            tv = itemView.findViewById(R.id.tv_comic);
            ln.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(iclickcomic != null){
                        iclickcomic.onClickComic(listcomic.get(getAdapterPosition()));
                    }
                }
            });
        }
    }
}
