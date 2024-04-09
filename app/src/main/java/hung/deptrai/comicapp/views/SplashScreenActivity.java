package hung.deptrai.comicapp.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.progressindicator.LinearProgressIndicator;

import hung.deptrai.comicapp.R;

public class SplashScreenActivity extends AppCompatActivity {
    private LinearProgressIndicator linearProgress;
    private static final String PREFS_NAME = "MyPrefs";
    private static final int SPLASH_DISPLAY_LENGTH = 2000; // Splash screen display time in milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear(); // Xóa tất cả các giá trị
        editor.apply();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        LinearProgressIndicator progressIndicator = findViewById(R.id.progressBar_horizontal);

        // Bắt đầu hiển thị tiến trình
        progressIndicator.setVisibility(View.VISIBLE);

        // Giả sử progressMax là giá trị tối đa của tiến trình
        int progressMax = 100;

        // Mô phỏng tiến trình tải trong 2 giây
        int durationMillis = 2000;
        ValueAnimator animator = ValueAnimator.ofInt(0, progressMax);
        animator.setDuration(durationMillis);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int progress = (int) animation.getAnimatedValue();
                progressIndicator.setProgressCompat(progress, true);
            }
        });
        animator.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Kết thúc hiển thị splash screen và tiến trình
                progressIndicator.setVisibility(View.GONE);
                Intent mainIntent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, durationMillis);
    }
}
