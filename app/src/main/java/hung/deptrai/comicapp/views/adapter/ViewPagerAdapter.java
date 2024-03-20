package hung.deptrai.comicapp.views.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import hung.deptrai.comicapp.views.fragment.home.HomeFragment;
import hung.deptrai.comicapp.views.fragment.profile.ProfileFragment;
import hung.deptrai.comicapp.views.fragment.search.SearchFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentactivity) {
        super(fragmentactivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        switch (position){
            case 1:
                fragment = new SearchFragment();
                break;
            case 2:
                fragment = new ProfileFragment();
                break;
            default:
                fragment = new HomeFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
