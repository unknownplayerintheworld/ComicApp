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
import hung.deptrai.comicapp.model.Author;
import hung.deptrai.comicapp.model.Comic;
import hung.deptrai.comicapp.views.Interface.IClickComic;

public class AuthorAdapter extends RecyclerView.Adapter<AuthorAdapter.AuthorViewHolder>{
    private List<Comic> list;
    private IClickComic iClickComic;
    public void setData(List<Comic> list,IClickComic iClickComic){
        this.list = list;
        this.iClickComic = iClickComic;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favourite,parent,false);
        return new AuthorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorViewHolder holder, int position) {
        Comic au = list.get(position);
        if(au == null){
            return ;
        }
        else{
            holder.tv.setText(au.getComicName());
            Glide.with(holder.img).load(au.getLinkPicture()).into(holder.img);
            holder.ln.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(iClickComic!=null){
                        iClickComic.onClickComic(au);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(list == null){
            return 0;
        }
        else{
            return list.size();
        }
    }

    public class AuthorViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout ln;
        private ImageView img;
        private TextView tv;
        public AuthorViewHolder(@NonNull View itemView) {
            super(itemView);
            ln = itemView.findViewById(R.id.fav);
            img = itemView.findViewById(R.id.img_fav);
            tv = itemView.findViewById(R.id.tv_fav);
            ln.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(iClickComic!=null){
                        iClickComic.onClickComic(list.get(getAdapterPosition()));
                    }
                }
            });
        }
    }
}
