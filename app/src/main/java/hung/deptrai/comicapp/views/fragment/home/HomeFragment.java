package hung.deptrai.comicapp.views.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import hung.deptrai.comicapp.R;
import hung.deptrai.comicapp.views.MainActivity;
import hung.deptrai.comicapp.views.adapter.Viewpager2AdapterForHome;

public class HomeFragment extends Fragment {
    private ViewPager2 vp2;
    private TabLayout tablayout;
    private MainActivity main;
    public HomeFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        vp2 = view.findViewById(R.id.vp2_home);
        main = (MainActivity) getActivity();
        tablayout = view.findViewById(R.id.tab_layout_home);
        vp2.setUserInputEnabled(false);
        Viewpager2AdapterForHome adapter = new Viewpager2AdapterForHome(this.getActivity());
        vp2.setAdapter(adapter);

        new TabLayoutMediator(tablayout, vp2, (tab, position) -> {
            switch (position){
                case 0:
                    tab.setText("khám phá");
                    break;
                case 1:
                    tab.setText("Yêu thích");
                    break;
            }
        }).attach();
        return view;
    }
}