package hung.deptrai.comicapp.views.fragment.home.journey.Category;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hung.deptrai.comicapp.R;
import hung.deptrai.comicapp.model.Category;
import hung.deptrai.comicapp.views.Interface.IClickCategory;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{
    private List<Category> list;
    private IClickCategory Iclick;
    public void setData(List<Category> list,IClickCategory Iclick){
        this.Iclick = Iclick;
        this.list = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category cat = list.get(position);
        if(cat ==null){
            return;
        }
        else{
            holder.btn.setTextColor(cat.getColorResources());
            holder.btn.setText(cat.getCat_name());
            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Iclick != null){
                        Iclick.onClickCategory(cat);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(list != null){
            return list.size();
        }
        return 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        private Button btn;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            btn = itemView.findViewById(R.id.btn);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Iclick != null){
                        Iclick.onClickCategory(list.get(getAdapterPosition()));
                    }
                }
            });
        }
    }
}
