package hung.deptrai.comicapp.views.fragment.home.favourite;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hung.deptrai.comicapp.R;
import hung.deptrai.comicapp.Utils.Tmp;
import hung.deptrai.comicapp.model.Account;
import hung.deptrai.comicapp.model.Comic;
import hung.deptrai.comicapp.model.Favourite;
import hung.deptrai.comicapp.viewmodel.ComicViewModel;
import hung.deptrai.comicapp.views.Interface.IClickComic;
import hung.deptrai.comicapp.views.MainActivity;
import hung.deptrai.comicapp.views.fragment.home.journey.Section.Section;
import hung.deptrai.comicapp.views.fragment.home.journey.Section.SectionAdapter;
import hung.deptrai.comicapp.views.fragment.home.journey.Section.SectionAdapterWithTitle;

public class FavouriteFragment extends Fragment {
    private RecyclerView rcv2;
    private FavouriteAdapter fav_rcv;
    private MainActivity mainactivity;
    private ComicViewModel comicViewModel;
    private TextView txt_noRecord;
    private List<Comic> favcomics;
    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite,container,false);
        rcv2 = view.findViewById(R.id.rcv_fav);
        mainactivity = (MainActivity) getActivity();

        txt_noRecord = view.findViewById(R.id.txtnoComicFav);

        fav_rcv = new FavouriteAdapter();

        //vm
        comicViewModel = new ViewModelProvider(this).get(ComicViewModel.class);

        GridLayoutManager grid = new GridLayoutManager(mainactivity,3);
        rcv2.setLayoutManager(grid);
//        fav_rcv.setData(getListFavourites());
        fav_rcv.setFavourite(favcomics, comic -> mainactivity.openComicActivity(comic));
        rcv2.setAdapter(fav_rcv);

        //
        getFavouriteComics();

        return view;
    }

    private void getFavouriteComics() {
        favcomics = new ArrayList<>();
        Account account = new Account();
        account.setUsername(Tmp.current_username);
        comicViewModel.getFavouriteComics(account).observe(getViewLifecycleOwner(),comics -> {
            for (int i = 0; i < comics.size(); i++) {
                if (comics.get(0) != null) {
                    favcomics.add(new Comic(comics.get(i).getId(), comics.get(i).getComicName(), comics.get(i).getLinkPicture(), comics.get(i).getViews(), comics.get(i).getCreated_at(), comics.get(i).getUpdated_at(), comics.get(i).getViews_in_month(), comics.get(i).getViews_in_week()));
                    Log.e("comic by genre: ", comics.get(i).getComicName() + " " + comics.get(i).getLinkPicture());
                }
            }
            comicViewModel.getFavouriteComics(account).removeObservers(getViewLifecycleOwner());
            updateUI();
        });
    }

    private void updateUI() {
        if(favcomics.size()<1){
            rcv2.setVisibility(View.GONE);
            txt_noRecord.setVisibility(View.VISIBLE);
        }
        else{
            rcv2.setVisibility(View.VISIBLE);
            txt_noRecord.setVisibility(View.GONE);
        }
        fav_rcv.setFavourite(favcomics, new IClickComic() {
            @Override
            public void onClickComic(Comic comic) {
                mainactivity.openComicActivity(comic);
            }
        });
    }

//    private List<Comic> getListFavourite() {
//        List<Comic> list1 = new ArrayList<>();
//        list1.add(new Comic("1","Toàn cơ từ","https://img.wattpad.com/cover/109018367-144-k677651.jpg"));
//        list1.add(new Comic("2","Tân tác long hổ môn","https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEhsN1BpZ57qkr0nq2MiwEA7GEYlJqciuGKN_OS7hsuH0zdlOIuzN7x805CuhDzek4yRehYqQoubOzQYJL4gpqty9wITdFHrba1I2syf1Z5261P80yJslVe-iHco4nOpMSVaeyrjg89atSY/s1600/be441_001cover.jpg?imgmax=0","hung"));
//        return list1;
//    }

    @Override
    public void onResume() {
        super.onResume();
        getFavouriteComics();
    }

    @Override
    public void onPause() {
        super.onPause();
        getFavouriteComics();
    }
}
