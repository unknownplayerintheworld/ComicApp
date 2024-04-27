package hung.deptrai.comicapp.views.fragment.home.journey.Rank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import hung.deptrai.comicapp.R;
import hung.deptrai.comicapp.model.Comic;
import hung.deptrai.comicapp.service.ImageLoader;
import hung.deptrai.comicapp.views.Interface.IClickComic;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.RankViewHolder>{
    private List<Comic> list;
    private IClickComic iclickComic;
    Context context;

    public void setData(List<Comic> list,IClickComic iclickComic,Context context){
        this.iclickComic = iclickComic;
        this.list = list;
        notifyDataSetChanged();
        this.context = context;
    }
    @NonNull
    @Override
    public RankViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rank,parent,false);
        return new RankViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RankViewHolder holder, int position) {
        Comic comic = list.get(position);
        if(comic == null){
            return ;
        }
        int rank = position + 1;

        // Thiết lập văn bản cho positionRanktv
        holder.positionRakingtv.setText(String.valueOf(rank));

        // Đặt ảnh vector và thiết lập colortint
        switch (rank) {
            case 1:
                holder.rankcolor.setImageResource(R.drawable.ic_rank_gold);
                break;
            case 2:
                holder.rankcolor.setImageResource(R.drawable.ic_rank_silver);
                break;
            case 3:
                holder.rankcolor.setImageResource(R.drawable.ic_rank_bronze);
                break;
            default:
                holder.rankcolor.setImageResource(R.drawable.ic_rank);
                break;
        }
        setVectorColorTint(holder.rankcolor, rank, context);
        Glide.with(holder.img).load(comic.getLinkPicture()).into(holder.img);
        ImageLoader.loadImage2(holder.img,comic.getLinkPicture(),holder.img);
//        holder.rankcolor.setImageResource(rank.getColor());
//        holder.positionRakingtv.setText(String.valueOf(rank.getPosition()));
        holder.ComicName.setText(comic.getComicName());
        holder.item_rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iclickComic != null){
                    iclickComic.onClickComic(comic);
                }
            }
        });
    }
    private void setVectorColorTint(ImageView imageView, int rank, Context context) {
        int colorResId;
        switch (rank) {
            case 1:
                colorResId = R.color.gold;
                break;
            case 2:
                colorResId = R.color.silver;
                break;
            case 3:
                colorResId = R.color.bronze;
                break;
            default:
                colorResId = R.color.purple_82;
                break;
        }
        int color = ContextCompat.getColor(context, colorResId);
        imageView.setColorFilter(color);
    }

    @Override
    public int getItemCount() {
        if(list != null){
            return list.size();
        }
        else{
            return 0;
        }
    }

    public class RankViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        private ImageView rankcolor;
        private TextView positionRakingtv;
        private TextView ComicName;
        private RelativeLayout item_rank;
        public RankViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_rank);
            rankcolor = itemView.findViewById(R.id.rank_color);
            positionRakingtv = itemView.findViewById(R.id.positionRanktv);
            ComicName = itemView.findViewById(R.id.tv_rank_title);
            item_rank = itemView.findViewById(R.id.item_rank);
            item_rank.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(iclickComic != null){
                        iclickComic.onClickComic(list.get(getAdapterPosition()));
                    }
                }
            });
        }
    }
}
