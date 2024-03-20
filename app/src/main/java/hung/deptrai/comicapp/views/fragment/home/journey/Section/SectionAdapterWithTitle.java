package hung.deptrai.comicapp.views.fragment.home.journey.Section;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hung.deptrai.comicapp.R;
import hung.deptrai.comicapp.model.Category;
import hung.deptrai.comicapp.model.Chapter;
import hung.deptrai.comicapp.model.Comic;
import hung.deptrai.comicapp.model.History;
import hung.deptrai.comicapp.views.Interface.IClickAuthor;
import hung.deptrai.comicapp.views.Interface.IClickCategory;
import hung.deptrai.comicapp.views.Interface.IClickComic;
import hung.deptrai.comicapp.views.Interface.IClickHistory;
import hung.deptrai.comicapp.views.fragment.home.journey.Category.CategoryAdapter;
import hung.deptrai.comicapp.views.fragment.home.journey.Proposal.ProposalAdapter;
import hung.deptrai.comicapp.views.fragment.home.journey.Rank.Rank;
import hung.deptrai.comicapp.views.fragment.home.journey.Rank.RankAdapter;
import hung.deptrai.comicapp.views.fragment.home.journey.Shounen.ShounenAdapter;
import hung.deptrai.comicapp.views.fragment.profile.ProfileAdapter;

public class SectionAdapterWithTitle extends RecyclerView.Adapter<SectionAdapterWithTitle.SectionWithTitleViewHolder> {
    private List<Section> list;
    public static final int TYPE_PROPOSAL = 2;
    public static final int TYPE_RANK = 3;
    public static final int TYPE_SHOUNEN = 4;
    public static final int TYPE_CATEGORY = 5;
    public static final int TYPE_HISTORY = 6;
    private Context context;
    private IClickComic iClickComic;
    private IClickAuthor iClickAuthor;
    private IClickCategory iClickCategory;
    private IClickHistory iClickHistory;
    public void setData(List<Section> list,Context context,IClickComic iclickcomic,IClickCategory iClickCategory){
        this.iClickCategory = iClickCategory;
        this.iClickComic = iclickcomic;
        this.list = list;
        this.context = context;
        notifyDataSetChanged();
    }
    public void setData(List<Section> list,Context context,IClickAuthor iclickauthor){
        this.iClickAuthor = iclickauthor;
        this.list = list;
        this.context = context;
        notifyDataSetChanged();
    }
    public void setData(List<Section> list,Context context,IClickCategory iclickcategory){
        this.iClickCategory = iclickcategory;
        this.list = list;
        this.context = context;
        notifyDataSetChanged();
    }
    public void setData(List<Section> list,Context context,IClickHistory iClickHistory){
        this.iClickHistory = iClickHistory;
        this.list = list;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SectionWithTitleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_section_with_title,parent,false);
        return new SectionWithTitleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SectionWithTitleViewHolder holder, int position) {
        Section section = list.get(position);
        if(section == null){
            return;
        }
        else if(holder.getItemViewType() == TYPE_PROPOSAL){
            LinearLayoutManager lnm = new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
            holder.tv.setText(section.getTitle());
            holder.rcvtitle.setLayoutManager(lnm);

            ProposalAdapter out = new ProposalAdapter();
            out.setData((List<Comic>) section.getList(), new IClickComic() {
                @Override
                public void onClickComic(Comic comic) {
                    if(iClickComic != null){
                        iClickComic.onClickComic(comic);
                    }
                }
            });
            holder.rcvtitle.setAdapter(out);
        }
        else if(holder.getItemViewType() == TYPE_RANK){
            LinearLayoutManager lnm = new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
            holder.tv.setText(section.getTitle());
            holder.rcvtitle.setLayoutManager(lnm);

            RankAdapter out = new RankAdapter();
            out.setData((List<Comic>) section.getList(), new IClickComic() {
                @Override
                public void onClickComic(Comic comic) {
                    if(iClickComic != null){
                        iClickComic.onClickComic(comic);
                    }
                }
            },context);
            holder.rcvtitle.setAdapter(out);
        }
        else if(holder.getItemViewType() == TYPE_SHOUNEN){
            LinearLayoutManager lnm = new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
            holder.tv.setText(section.getTitle());
            holder.rcvtitle.setLayoutManager(lnm);

            ShounenAdapter out = new ShounenAdapter();
            out.setData((List<Comic>) section.getList(), new IClickComic() {
                @Override
                public void onClickComic(Comic comic) {
                    if(iClickComic != null){
                        iClickComic.onClickComic(comic);
                    }
                }
            });
            holder.rcvtitle.setAdapter(out);
        }
        else if(holder.getItemViewType() == TYPE_CATEGORY){
            LinearLayoutManager lnm = new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
            holder.tv.setText(section.getTitle());
            holder.rcvtitle.setLayoutManager(lnm);

            CategoryAdapter out = new CategoryAdapter();
            out.setData((List<Category>) section.getList(), new IClickCategory() {
                @Override
                public void onClickCategory(Category category) {
                    if(iClickCategory != null){
                        iClickCategory.onClickCategory(category);
                    }
                }
            });
            holder.rcvtitle.setAdapter(out);
        }
        else if(holder.getItemViewType() == TYPE_HISTORY){
            LinearLayoutManager lnm = new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
            holder.tv.setText(section.getTitle());
            holder.rcvtitle.setLayoutManager(lnm);

            ProfileAdapter out = new ProfileAdapter();
            out.setData((List<Comic>) section.getList(),(List<Chapter>) section.getList(), new IClickHistory() {
                @Override
                public void onClickHistory(Chapter chapter, Comic comic) {
                    if(iClickHistory != null){
                        iClickHistory.onClickHistory(chapter,comic);
                    }
                }

                @Override
                public void onClickDelHistory(Comic comic) {

                }
            });
            holder.rcvtitle.setAdapter(out);
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

    public class SectionWithTitleViewHolder extends RecyclerView.ViewHolder{
        private RecyclerView rcvtitle;
        private TextView tv;
        public SectionWithTitleViewHolder(@NonNull View itemView) {
            super(itemView);
            rcvtitle = itemView.findViewById(R.id.rcv_with_title);
            tv = itemView.findViewById(R.id.tvSectionName);
        }
    }
}
