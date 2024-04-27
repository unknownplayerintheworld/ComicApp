package hung.deptrai.comicapp.views.fragment.home.journey.outstanding;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;

import hung.deptrai.comicapp.R;
import hung.deptrai.comicapp.Transformation.BlurSidesTransformation;
import hung.deptrai.comicapp.model.Comic;
import hung.deptrai.comicapp.service.ImageLoader;
import hung.deptrai.comicapp.views.Interface.IClickComic;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class OutstandingAdapter extends RecyclerView.Adapter<OutstandingAdapter.OutstandingViewHolder>{
    private List<Comic> list;
    private IClickComic listener;
    public void setData(List<Comic> list,IClickComic iClickComic){
        this.listener = iClickComic;
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OutstandingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_outstanding,parent,false);
        return new OutstandingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OutstandingViewHolder holder, int position) {
        Comic out = list.get(position);
        if(out == null){
            return;
        }
        Glide.with(holder.itemView.getContext())
                .asBitmap()
                .load(out.getLinkPicture())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        // Cắt ảnh thành ba phần
                        int width = resource.getWidth();
                        int height = resource.getHeight();
                        Log.e("res size:","res height:"+String.valueOf(resource.getHeight())+"|res width:"+String.valueOf(resource.getWidth()));
                        int partWidth = width / 3;

                        // Tạo hai ảnh bitmap cho phần trái và phải
                        // test
                        Bitmap leftBitmap = Bitmap.createBitmap(resource, 0, 0, partWidth, height);
                        Bitmap rightBitmap = Bitmap.createBitmap(resource, width - partWidth, 0, partWidth, height);
                        applyBlurTransformation(holder.itemView.getContext(), leftBitmap, holder.imgleft);
                        applyBlurTransformation(holder.itemView.getContext(), rightBitmap, holder.imgright);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                    private void applyBlurTransformation(Context context, Bitmap bitmap, ImageView imageView) {
                        // Áp dụng hiệu ứng mờ
                        Glide.with(context)
                                .load(bitmap)
                                .apply(RequestOptions.bitmapTransform(new BlurTransformation()))
                                .into(imageView);
                    }
                });
        Glide.with(holder.itemView.getContext()).load(out.getLinkPicture()).into(holder.imgOutstanding);
        ImageLoader.loadImage2(holder.itemView,out.getLinkPicture(),holder.imgOutstanding);
        holder.item_outstanding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onClickComic(out);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(list != null){
            return list.size();
        }
        return 0;
    }

    public class OutstandingViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgOutstanding,imgleft,imgright;
        private CardView item_outstanding;

        public OutstandingViewHolder(@NonNull View itemView) {
            super(itemView);
            imgOutstanding = itemView.findViewById(R.id.img_outstanding);
            imgleft = itemView.findViewById(R.id.img_left_outstanding);
            imgright = itemView.findViewById(R.id.img_right_outstanding);
            item_outstanding = itemView.findViewById(R.id.item_outstanding);
            item_outstanding.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.onClickComic(list.get(getAdapterPosition()));
                    }
                }
            });
        }
    }
}
