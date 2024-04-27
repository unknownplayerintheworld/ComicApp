package hung.deptrai.comicapp.views.fragment.home.journey;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import hung.deptrai.comicapp.R;
import hung.deptrai.comicapp.Utils.Tmp;
import hung.deptrai.comicapp.model.Account;
import hung.deptrai.comicapp.model.Category;
import hung.deptrai.comicapp.model.Comic;
import hung.deptrai.comicapp.viewmodel.CategoryViewModel;
import hung.deptrai.comicapp.viewmodel.ComicViewModel;
import hung.deptrai.comicapp.views.Interface.IClickCategory;
import hung.deptrai.comicapp.views.Interface.IClickComic;
import hung.deptrai.comicapp.views.MainActivity;
import hung.deptrai.comicapp.views.fragment.home.journey.Rank.Rank;
import hung.deptrai.comicapp.views.fragment.home.journey.Section.Section;
import hung.deptrai.comicapp.views.fragment.home.journey.Section.SectionAdapter;
import hung.deptrai.comicapp.views.fragment.home.journey.Section.SectionAdapterWithTitle;
import hung.deptrai.comicapp.views.fragment.home.journey.outstanding.Outstanding;
import me.relex.circleindicator.CircleIndicator2;
import hung.deptrai.comicapp.databinding.FragmentJourneyBinding;

public class JourneyFragment extends Fragment {
    private RecyclerView rcv;
    private RecyclerView rcv2;
    private CircleIndicator2 ci2;
    private SectionAdapterWithTitle sectionadpter;
    private SectionAdapter sec;
    private MainActivity mainactivity;
    private ComicViewModel comicViewModel;
    private CategoryViewModel categoryViewModel;
    private Timer timer;
    private int currentpage = 0;
    private FragmentJourneyBinding binding;
    private List<Comic> outstanding,proposal,ranking,shounen;
    private List<Category> categories;
    private List<Section> section,titlesection;
    public JourneyFragment(){

    }
    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_journey, container, false);

        comicViewModel = new ViewModelProvider(this).get(ComicViewModel.class);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        binding = FragmentJourneyBinding.inflate(inflater,container,false);
        binding.setLifecycleOwner(this);
        binding.setComicViewModel(comicViewModel);
        // recyclerview của saction ko có title
        ci2 = view.findViewById(R.id.ci2);
        mainactivity = (MainActivity) getActivity();
        rcv = view.findViewById(R.id.rcv_journey);
        sec = new SectionAdapter();

        LinearLayoutManager lnm = new LinearLayoutManager(mainactivity);
        rcv.setLayoutManager(lnm);


        sec.setData(getListSection(), mainactivity, new IClickComic() {
            @Override
            public void onClickComic(Comic comic) {
                mainactivity.openComicActivity(comic);
            }
        });
        rcv.setAdapter(sec);
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(rcv);
        ci2.attachToRecyclerView(rcv,pagerSnapHelper);
        sec.registerAdapterDataObserver(ci2.getAdapterDataObserver());

        // rcv có title
        rcv2 = view.findViewById(R.id.rcv_journey2);
        LinearLayoutManager lnm2 = new LinearLayoutManager(mainactivity);
        rcv2.setLayoutManager(lnm2);
        sectionadpter = new SectionAdapterWithTitle();
        sectionadpter.setData(getListTitleSection(), mainactivity, new IClickComic() {
            @Override
            public void onClickComic(Comic comic) {
                mainactivity.openComicActivity(comic);
            }
        }, new IClickCategory() {
            @Override
            public void onClickCategory(Category category) {
                mainactivity.openCategory(category);
            }
        });
        rcv2.setAdapter(sectionadpter);
        return view;
    }
    // fix cứng list
//    private List<Section> getListSectionwithtile() {
//        List<Section> section2 = new ArrayList<>();
//        List<Comic> proposal2 = new ArrayList<>();
//        List<Comic> shounen = new ArrayList<>();
//        List<Comic> rank = new ArrayList<>();
//        List<Category> cat = new ArrayList<>();
//        cat.add(new Category("1","18+","it's so erotic",getContext()));
//        rank.add(new Comic("1","Tân tác long hổ môn 1","https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEhsN1BpZ57qkr0nq2MiwEA7GEYlJqciuGKN_OS7hsuH0zdlOIuzN7x805CuhDzek4yRehYqQoubOzQYJL4gpqty9wITdFHrba1I2syf1Z5261P80yJslVe-iHco4nOpMSVaeyrjg89atSY/s1600/be441_001cover.jpg?imgmax=0","Bất hủ tông"));
//        shounen.add(new Comic("2","Toàn cơ từ","https://img.wattpad.com/cover/109018367-144-k677651.jpg","hung"));
//        proposal2.add(new Comic("3","Tân tác long hổ môn 2","https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEi2B8X-ti5Ao95lVvmaMcMVqfrWubYxBAVx3yiuuKOgZrHCooxXKtLudhyBqp7Rjs2Pb5-bVuJ_ySJU1gPZN5Z32o-YbbywGZvTNrzllpGfnxNelvujhO0BS9ZM1pfYIYrk-fImGCSVA60/s1600/be441_002.jpg?imgmax=0","hung"));
//        proposal2.add(new Comic("4","Tân tác long hổ môn 3","https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEi2B8X-ti5Ao95lVvmaMcMVqfrWubYxBAVx3yiuuKOgZrHCooxXKtLudhyBqp7Rjs2Pb5-bVuJ_ySJU1gPZN5Z32o-YbbywGZvTNrzllpGfnxNelvujhO0BS9ZM1pfYIYrk-fImGCSVA60/s1600/be441_002.jpg?imgmax=0","hung"));
//        section2.add(new Section(proposal2,SectionAdapterWithTitle.TYPE_PROPOSAL,"Proposal"));
//        section2.add(new Section(rank,SectionAdapterWithTitle.TYPE_RANK,"Rank"));
//        section2.add(new Section(shounen,SectionAdapterWithTitle.TYPE_SHOUNEN,"Shounen"));
//        section2.add(new Section(cat,SectionAdapterWithTitle.TYPE_CATEGORY,"Category"));
//        return section2;
//    }

    // load mềm
    private List<Section> getListTitleSection() {
        titlesection = new ArrayList<>();
        Account account = new Account();
        account.setUsername(Tmp.current_username);

        proposal = getProposalComic(account);
        ranking = getRankingComic();
        shounen = getShounenComic();
        categories = getCategories();
        titlesection.add(new Section(proposal,SectionAdapterWithTitle.TYPE_PROPOSAL,"Proposal"));
        titlesection.add(new Section(ranking,SectionAdapterWithTitle.TYPE_RANK,"Rank"));
        titlesection.add(new Section(shounen,SectionAdapterWithTitle.TYPE_SHOUNEN,"Shounen"));
        titlesection.add(new Section(categories,SectionAdapterWithTitle.TYPE_CATEGORY,"Category"));
        return titlesection;
    }

    private List<Section> getListSection() {
        section = new ArrayList<>();
//        List<Comic> out = new ArrayList<>();
//        List<Comic> proposal = new ArrayList<>();
//        out.add(new Comic("5","Asukaziye","https://chuduchung24062002.000webhostapp.com/api/img_store/asukaziye.png","hung"));
//        out.add(new Comic("6","Asukaziye 2","https://chuduchung24062002.000webhostapp.com/api/img_store/asukaziye.png","hung"));

        section.add(new Section(getOutstandingComic(),SectionAdapter.TYPE_OUTSTANDING));
        return section;
    }
    public List<Comic> getOutstandingComic(){
        outstanding = new ArrayList<>();
        comicViewModel.getOutstanding().observe(getViewLifecycleOwner(),comics -> {
            for (int i = 0; i < comics.size(); i++) {
                outstanding.add(new Comic(comics.get(i).getId(),comics.get(i).getComicName(),comics.get(i).getLinkPicture(),comics.get(i).getViews(),comics.get(i).getCreated_at(),comics.get(i).getUpdated_at(),comics.get(i).getViews_in_month(),comics.get(i).getViews_in_week()));
                Log.e("Outstanding: ",comics.get(i).getComicName()+ " " + comics.get(i).getLinkPicture());
            }
            updateUI();
//            comicViewModel.getOutstanding().removeObservers(getViewLifecycleOwner());
        });
        return outstanding;
    }
    public List<Comic> getProposalComic(Account account){
        proposal = new ArrayList<>();
        comicViewModel.getProposal(account).observe(getViewLifecycleOwner(),comics -> {
            for (int i = 0; i < comics.size(); i++) {
                proposal.add(new Comic(comics.get(i).getId(),comics.get(i).getComicName(),comics.get(i).getLinkPicture(),comics.get(i).getViews(),comics.get(i).getCreated_at(),comics.get(i).getUpdated_at(),comics.get(i).getViews_in_month(),comics.get(i).getViews_in_week()));
                Log.e("Proposal: ",comics.get(i).getComicName()+ " " + comics.get(i).getLinkPicture());
            }
            updateUI();
//            comicViewModel.getProposal(account).removeObservers(getViewLifecycleOwner());
        });
        return proposal;
    }
    public List<Comic> getRankingComic(){
        ranking = new ArrayList<>();
        comicViewModel.getRank().observe(getViewLifecycleOwner(),comics -> {
            for (int i = 0; i < comics.size(); i++) {
                ranking.add(new Comic(comics.get(i).getId(),comics.get(i).getComicName(),comics.get(i).getLinkPicture(),comics.get(i).getViews(),comics.get(i).getCreated_at(),comics.get(i).getUpdated_at(),comics.get(i).getViews_in_month(),comics.get(i).getViews_in_week()));
                Log.e("Ranking: ",comics.get(i).getComicName()+ " " + comics.get(i).getLinkPicture());
            }
            updateUI();
//            comicViewModel.getRank().removeObservers(getViewLifecycleOwner());
        });
        return ranking;
    }
    public List<Comic> getShounenComic(){
        shounen = new ArrayList<>();
        comicViewModel.getShounenComic().observe(getViewLifecycleOwner(),comics -> {
            for (int i = 0; i < comics.size(); i++) {
                shounen.add(new Comic(comics.get(i).getId(),comics.get(i).getComicName(),comics.get(i).getLinkPicture(),comics.get(i).getViews(),comics.get(i).getCreated_at(),comics.get(i).getUpdated_at(),comics.get(i).getViews_in_month(),comics.get(i).getViews_in_week()));
                Log.e("Shounen: ",comics.get(i).getComicName()+ " " + comics.get(i).getLinkPicture());
            }
            updateUI();
//            comicViewModel.getShounenComic().removeObservers(getViewLifecycleOwner());
        });
        return shounen;
    }
    public List<Category> getCategories(){
        categories = new ArrayList<>();
        categoryViewModel.getAllGenre().observe(getViewLifecycleOwner(),comics -> {
            for (int i = 0; i < comics.size(); i++) {
                categories.add(new Category(comics.get(i).getId(),comics.get(i).getCat_name(),comics.get(i).getDescription(),getContext()));
                Log.e("Cate: ",comics.get(i).getCat_name()+ " " + comics.get(i).getDescription()+",context:" + getContext().toString());
            }
            updateUI();
//            categoryViewModel.getAllGenre().removeObservers(getViewLifecycleOwner());
        });
        return categories;
    }

    private void updateUI() {
        sec.setData(section, getContext(), new IClickComic() {
            @Override
            public void onClickComic(Comic comic) {
                mainactivity.openComicActivity(comic);
            }
        });
        sectionadpter.setData(titlesection, getContext(), new IClickComic() {
                    @Override
                    public void onClickComic(Comic comic) {
                        mainactivity.openComicActivity(comic);
                    }
                }
                , new IClickCategory() {
                    @Override
                    public void onClickCategory(Category category) {
                        mainactivity.openCategory(category);
                    }
                });
    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        updateUI();
//    }
    //    private void scrollToNextItem(RecyclerView.LayoutManager layoutManager,) {
//        int currentItem = layoutManager.findFirstVisibleItemPosition();
//        int nextItem = currentItem + 1;
//        if (nextItem < adapter.getItemCount()) { // Kiểm tra xem có mục tiếp theo không
//            recyclerView.smoothScrollToPosition(nextItem);
//        } else { // Nếu không, quay lại mục đầu tiên
//            recyclerView.smoothScrollToPosition(0);
//        }
//    }
//        vp2 = view.findViewById(R.id.vp2_home_img_slider);
//        ci3 = view.findViewById(R.id.ci3);
//        list = getListPhoto();
//
//        Viewpager2AdapterForPhoto vp2fp = new Viewpager2AdapterForPhoto(list);
//        vp2.setAdapter(vp2fp);
//        ci3.setViewPager(vp2);
//
//        vp2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//                hand.removeCallbacks(run);
//                hand.postDelayed(run,2000);
//            }
//        });
//        return view;
//    }
//    private List<Photo> getListPhoto(){
//        List<Photo> list = new ArrayList<>();
//        list.add(new Photo(R.drawable.asukaziye));
//        list.add(new Photo(R.drawable.banner));
//        return list;
//    }
    @Override
    public void onPause() {
        super.onPause();
        updateUI();
    }
//
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
}
