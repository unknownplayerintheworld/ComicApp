package hung.deptrai.comicapp.service;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class ImageLoader {
    public static void loadImage(View view, String url, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL); // Cache hình ảnh

        Glide.with(view)
                .load(url)
                .apply(requestOptions)
                .into(imageView);
    }
}
