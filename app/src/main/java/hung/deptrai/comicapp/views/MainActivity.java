package hung.deptrai.comicapp.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.ShapeAppearanceModel;

import hung.deptrai.comicapp.R;
import hung.deptrai.comicapp.Utils.Status;
import hung.deptrai.comicapp.Utils.Tmp;
import hung.deptrai.comicapp.model.Author;
import hung.deptrai.comicapp.model.Category;
import hung.deptrai.comicapp.model.Chapter;
import hung.deptrai.comicapp.model.Comic;
import hung.deptrai.comicapp.model.History;
import hung.deptrai.comicapp.views.adapter.ViewPagerAdapter;
import hung.deptrai.comicapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView btnv;
    private ViewPager2 mViewpager;
    private ActivityMainBinding activityMainBinding;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewpager = findViewById(R.id.viewpager);
        btnv = findViewById(R.id.bngv);
        setViewpager();


        btnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.home_menu){
                    mViewpager.setCurrentItem(0);
                }
                else if(id == R.id.search_menu){
                    mViewpager.setCurrentItem(1);
                }
                else if(id == R.id.profile_menu){
                    mViewpager.setCurrentItem(2);
                }
                return true;
            }
        });
    }

    private void setViewpager() {
        ViewPagerAdapter viewpageradapter = new ViewPagerAdapter(this);
        mViewpager.setAdapter(viewpageradapter);
        mViewpager.setUserInputEnabled(false);
        mViewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 1:
                        btnv.getMenu().findItem(R.id.search_menu).setChecked(true);
                        break;
                    case 2:
                        btnv.getMenu().findItem(R.id.profile_menu).setChecked(true);
                        break;
                    default:
                        btnv.getMenu().findItem(R.id.home_menu).setChecked(true);
                        break;
                }
            }
        });
    }
    public void openComicActivity(Comic comic){
        Intent intent = new Intent(MainActivity.this,ComicActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("comicID",comic.getId());
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void openAuthorActivity(Author author){
        Intent intent = new Intent(MainActivity.this,AuthorActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("transteamID",author.getId());
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void openCategory(Category category){
        Intent intent = new Intent(MainActivity.this,CategoryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("categoryName",category.getCat_name());
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void openHistoryActivty(){
        Intent intent = new Intent(MainActivity.this,HistoryActivity.class);
        startActivity(intent);
    }
    public void openReadingActivity(Chapter chapter,Comic comic){
        Intent intent = new Intent(MainActivity.this,ReadingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("comicID",comic.getId());
        bundle.putSerializable("chapter",chapter.getChapter_pos());
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void Logout(){
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        Tmp.current_username = "";
        startActivity(intent);
        finishAffinity();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if ((newConfig.uiMode & Configuration.UI_MODE_NIGHT_MASK) ==
                Configuration.UI_MODE_NIGHT_YES) {
            // Xử lý logic khi chuyển sang chế độ tối
        } else {
            // Xử lý logic khi chuyển sang chế độ sáng
        }
    }
}
