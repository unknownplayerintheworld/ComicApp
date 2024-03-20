package hung.deptrai.comicapp.views.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import hung.deptrai.comicapp.views.fragment.home.favourite.FavouriteFragment;
import hung.deptrai.comicapp.views.fragment.home.journey.JourneyFragment;

public class Viewpager2AdapterForHome extends ViewPagerAdapter{
    public Viewpager2AdapterForHome(@NonNull FragmentActivity fragmentactivity) {
        super(fragmentactivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        switch (position){
            case 1:
                return new FavouriteFragment();
            case 0:
                return new JourneyFragment();
            default:
                return new JourneyFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
