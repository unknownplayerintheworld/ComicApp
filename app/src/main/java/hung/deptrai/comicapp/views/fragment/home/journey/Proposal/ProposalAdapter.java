package hung.deptrai.comicapp.views.fragment.home.journey.Proposal;

import android.text.TextUtils;
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
import hung.deptrai.comicapp.service.ImageLoader;
import hung.deptrai.comicapp.views.Interface.IClickComic;

public class ProposalAdapter extends RecyclerView.Adapter<ProposalAdapter.ProposalViewHolder>{
    private List<Comic> list;
    private IClickComic Iclick;

    public void setData(List<Comic> list,IClickComic Iclick) {
        this.Iclick = Iclick;
        this.list = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ProposalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_proposal,parent,false);
        return new ProposalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProposalViewHolder holder, int position) {
        Comic comic = list.get(position);
        if(comic==null){
            return ;
        }
        Glide.with(holder.itemView).load(comic.getLinkPicture()).into(holder.img);
        ImageLoader.loadImage2(holder.itemView,comic.getLinkPicture(),holder.img);
        holder.tv.setText(comic.getComicName());
        holder.tv.setMaxLines(1);
        holder.tv.setEllipsize(TextUtils.TruncateAt.END);
        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Iclick != null){
                    Iclick.onClickComic(comic);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(list!=null){
            return list.size();
        }
        return 0;
    }

    public class ProposalViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout linear;
        private ImageView img;
        private TextView tv;

        public ProposalViewHolder(@NonNull View itemView) {
            super(itemView);
            linear = itemView.findViewById(R.id.proposal);
            img = itemView.findViewById(R.id.img_proposal);
            tv = itemView.findViewById(R.id.tv_proposal_title);
            linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Iclick != null){
                        Iclick.onClickComic(list.get(getAdapterPosition()));
                    }
                }
            });
        }
    }
}
