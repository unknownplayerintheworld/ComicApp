package hung.deptrai.comicapp.views.fragment.home.journey.Section;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hung.deptrai.comicapp.R;
import hung.deptrai.comicapp.model.Comic;
import hung.deptrai.comicapp.views.Interface.IClickAuthor;
import hung.deptrai.comicapp.views.Interface.IClickCategory;
import hung.deptrai.comicapp.views.Interface.IClickComic;
import hung.deptrai.comicapp.views.fragment.home.journey.Proposal.ProposalAdapter;
import hung.deptrai.comicapp.views.fragment.home.journey.outstanding.Outstanding;
import hung.deptrai.comicapp.views.fragment.home.journey.outstanding.OutstandingAdapter;

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.SectionViewholder>{
    private List<Section> list;
    public static final int TYPE_OUTSTANDING = 1;
    public static final int TYPE_PROPOSAL = 2;
    private IClickComic iClickComic;
    private IClickAuthor iClickAuthor;
    private IClickCategory iClickCategory;
    private Context context;
    public void setData(List<Section> list,Context context,IClickComic listener){
        this.list = list;
        this.iClickComic = listener;
        this.context = context;
        notifyDataSetChanged();
    }
    public void setData(List<Section> list,Context context,IClickCategory listener){
        this.iClickCategory = listener;
        this.list = list;
        this.context = context;
        notifyDataSetChanged();
    }
    public void setData(List<Section> list,Context context,IClickAuthor listener){
        this.iClickAuthor = listener;
        this.list = list;
        this.context = context;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public SectionViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_section,parent,false);
        return new SectionViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SectionViewholder holder, int position) {
        Section section = list.get(position);
        if(section == null){
            return;
        }
        if(holder.getItemViewType() == TYPE_OUTSTANDING){
            LinearLayoutManager lnm = new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
            holder.rcv.setLayoutManager(lnm);
            holder.rcv.setFocusable(false);
            OutstandingAdapter out = new OutstandingAdapter();
            out.setData((List<Comic>) section.getList(), new IClickComic() {
                @Override
                public void onClickComic(Comic comic) {
                    if(iClickComic != null){
                        iClickComic.onClickComic(comic);
                    }
                }
            });
            holder.rcv.setAdapter(out);
        }
        else if(holder.getItemViewType() == TYPE_PROPOSAL){
            LinearLayoutManager lnm = new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
            holder.rcv.setLayoutManager(lnm);

            ProposalAdapter out = new ProposalAdapter();
            out.setData((List<Comic>) section.getList(), new IClickComic() {
                @Override
                public void onClickComic(Comic comic) {
                    if(iClickComic != null){
                        iClickComic.onClickComic(comic);
                    }
                }
            });
            holder.rcv.setAdapter(out);
        }
    }

    @Override
    public int getItemCount() {
        if(list != null){
            return list.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }

    public class SectionViewholder extends RecyclerView.ViewHolder{
        private RecyclerView rcv;
        public SectionViewholder(@NonNull View itemView) {
            super(itemView);
            rcv = itemView.findViewById(R.id.rcv);
        }
    }
}
