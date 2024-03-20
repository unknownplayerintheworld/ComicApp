package hung.deptrai.comicapp.Transformation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

public class BlurSidesTransformation extends BitmapTransformation {
    private static final int Version = 1;
    private static final String ID = "BlurSidesTransformation." + Version;
    private static final int BLUR_RADIUS = 25;

    public BlurSidesTransformation() {
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        int width = toTransform.getWidth();
        int height = toTransform.getHeight();

        // Tạo một bitmap mới để vẽ
        Bitmap outBitmap = pool.get(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(outBitmap);

        // Tạo paint để làm mờ
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
        paint.setAntiAlias(true);

        // Tính toán kích thước và vị trí của phần bên trái
        int margin = 70; // margin 70dp
        int totalWidth = width;
        int leftPartWidth = (width - margin * 2) / 3;
        Bitmap leftPart = Bitmap.createBitmap(toTransform, 0, 0, leftPartWidth, height);
        leftPart = applyBlur(leftPart);
        canvas.drawBitmap(leftPart, margin, 0, paint);

        // Tính toán kích thước và vị trí của phần bên phải
        int rightPartStartX = totalWidth - leftPartWidth - margin;
        int rightPartWidth = leftPartWidth;
        Bitmap rightPart = Bitmap.createBitmap(toTransform, width - rightPartWidth, 0, rightPartWidth, height);
        rightPart = applyBlur(rightPart);
        canvas.drawBitmap(rightPart, rightPartStartX, 0, paint);

        // Vẽ ảnh gốc vào giữa
        Bitmap originalPart = Bitmap.createBitmap(toTransform, leftPartWidth, 0, width - (2 * leftPartWidth), height);
        canvas.drawBitmap(originalPart, margin + leftPartWidth, 0, paint);

        return outBitmap;
    }

    private Bitmap applyBlur(Bitmap bitmap) {
        // Áp dụng Gaussian Blur
        // CODE ĐỂ ÁP DỤNG BLUR Ở ĐÂY

        return bitmap;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update((ID).getBytes(CHARSET));
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof BlurSidesTransformation;
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }
}
