package hung.deptrai.comicapp.views.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import hung.deptrai.comicapp.R;
import hung.deptrai.comicapp.model.ImageChapter;
import hung.deptrai.comicapp.service.ImageLoader;
import hung.deptrai.comicapp.views.ReadingActivity;

public class ReadingAdapter extends RecyclerView.Adapter<ReadingAdapter.ReadingViewHolder>{
    private List<ImageChapter> imglist ;
    private ReadingActivity readingActivity;
    public void setData(List<ImageChapter> list){
        this.imglist = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ReadingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_chapter_comic,parent,false);
//        ImageView imageView = view.findViewById(R.id.img_chapter);
//        imageView.setLayoutParams(new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT
//        ));
        return new ReadingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReadingViewHolder holder, int position) {
        ImageChapter img = imglist.get(position);
        if(img == null){
            return;
        }
        else{
//            Glide.with(holder.img_chapter).load(img.getLinkImage()).centerCrop().into(holder.img_chapter);
            holder.img_chapter.setImageResource(img.getResourceIds());
            ImageLoader.loadImage(holder.img_chapter, img.getLinkImage(), holder.img_chapter);
        }
    }

    @Override
    public int getItemCount() {
        if(imglist != null){
            return imglist.size();
        }
        else {
            return 0;
        }
    }

    public class ReadingViewHolder extends RecyclerView.ViewHolder{
        private ImageView img_chapter;
        public ReadingViewHolder(@NonNull View itemView) {
            super(itemView);
            img_chapter = itemView.findViewById(R.id.img_chapter);
        }
    }
}
