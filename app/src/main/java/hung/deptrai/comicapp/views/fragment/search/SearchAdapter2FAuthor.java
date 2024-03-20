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
import hung.deptrai.comicapp.model.Author;
import hung.deptrai.comicapp.model.Comic;
import hung.deptrai.comicapp.views.Interface.IClickAuthor;

public class SearchAdapter2FAuthor extends RecyclerView.Adapter<SearchAdapter2FAuthor.AuthorSearchViewHolder>{
    private List<Author> listAuthors;
    private IClickAuthor iClickAuthor;
    public void setData(List<Author> listAuthors, IClickAuthor iClickAuthor){
        this.listAuthors = listAuthors;
        this.iClickAuthor = iClickAuthor;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public AuthorSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_author,parent,false);
        return new SearchAdapter2FAuthor.AuthorSearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorSearchViewHolder holder, int position) {
        Author author = listAuthors.get(position);
        if(author == null){
            return ;
        }
        else{
            Glide.with(holder.img).load(author.getLink_picture_author()).into(holder.img);
            holder.tv.setText(author.getAuthor_name());
            holder.item_layout_search_author.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(iClickAuthor != null){
                        iClickAuthor.onClickAuthor(author);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (listAuthors!=null){
            return listAuthors.size();
        }else{
            return 0;
        }
    }

    public class AuthorSearchViewHolder extends RecyclerView.ViewHolder{
        private ConstraintLayout item_layout_search_author;
        private ImageView img;
        private TextView tv;
        public AuthorSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            item_layout_search_author = itemView.findViewById(R.id.layoutItemAuthor);
            img = itemView.findViewById(R.id.img_author_search);
            tv = itemView.findViewById(R.id.tv_Author_Title);
            item_layout_search_author.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(iClickAuthor != null){
                        iClickAuthor.onClickAuthor(listAuthors.get(getAdapterPosition()));
                    }
                }
            });
        }
    }
}
