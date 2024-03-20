package hung.deptrai.comicapp.views.fragment.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hung.deptrai.comicapp.R;
import hung.deptrai.comicapp.model.Author;
import hung.deptrai.comicapp.model.Comic;
import hung.deptrai.comicapp.viewmodel.AuthorViewModel;
import hung.deptrai.comicapp.viewmodel.ComicViewModel;
import hung.deptrai.comicapp.views.Interface.IClickAuthor;
import hung.deptrai.comicapp.views.Interface.IClickComic;
import hung.deptrai.comicapp.views.MainActivity;

public class SearchFragment extends Fragment {
    private List<Comic> comic;
    private List<Author> authorList;
    private SearchAdapter searchadpter;
    private SearchAdapter2FAuthor searchAdapter2FAuthor;
    private ProgressBar progressBar_search;
    private SearchView search;
    private RecyclerView rcv,rcvAuthor;
    private ComicViewModel comicViewModel;
    private AuthorViewModel authorViewModel;
    private MainActivity main;
    public SearchFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search,container,false);

        //viewmodel
        comicViewModel = new ViewModelProvider(this).get(ComicViewModel.class);
        authorViewModel = new ViewModelProvider(this).get(AuthorViewModel.class);

        // adapter rcv for comics

        rcv = view.findViewById(R.id.rcvSearchItems);
        main = (MainActivity) getActivity();
        search = view.findViewById(R.id.searchView);
        LinearLayoutManager lnm = new LinearLayoutManager(main);
        rcv.setLayoutManager(lnm);
        progressBar_search = view.findViewById(R.id.pb_search);
        searchadpter = new SearchAdapter();
        searchadpter.setData(comic, new IClickComic() {
            @Override
            public void onClickComic(Comic comic) {
                main.openComicActivity(comic);
            }
        });
        rcv.setAdapter(searchadpter);

        // adapter rcv for author(transteam)
        rcvAuthor = view.findViewById(R.id.rcvTransSearchItems);
        LinearLayoutManager lnm2 = new LinearLayoutManager(main);
        rcvAuthor.setLayoutManager(lnm2);
        searchAdapter2FAuthor = new SearchAdapter2FAuthor();
        searchAdapter2FAuthor.setData(authorList, new IClickAuthor() {
            @Override
            public void onClickAuthor(Author author) {
                main.openAuthorActivity(author);
            }
        });
        rcvAuthor.setAdapter(searchAdapter2FAuthor);



        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getListComic(query);
                getListAuthor(query);
                search.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return view;
    }

    private void getListAuthor(String query) {
        authorList = new ArrayList<>();
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("transname",query);
        authorViewModel.getAuthorSearch(hashMap).observe(this,authors -> {
            for (int i = 0; i < authors.size(); i++) {
                authorList.add(new Author(authors.get(i).getId(),authors.get(i).getAuthor_name(),authors.get(i).getDescription(),authors.get(i).getLink_picture_author()));
            }
            updateUIAuthor();
            authorViewModel.getAuthorSearch(hashMap).removeObservers(this);
        });
    }

    private void getListComic(String query) {
        comic = new ArrayList<>();
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("comicName",query);
        comicViewModel.getComicSearch(hashMap).observe(this,comics -> {
            for (int i = 0; i < comics.size(); i++) {
                comic.add(new Comic(comics.get(i).getId(), comics.get(i).getComicName(),comics.get(i).getLinkPicture()));
            }
            updateUIComic();
            comicViewModel.getComicSearch(hashMap).removeObservers(this);
        });
    }
    private void updateUIComic(){
    searchadpter.setData(comic, new IClickComic() {
        @Override
        public void onClickComic(Comic comic) {
            main.openComicActivity(comic);
        }
    });
    }
    private void updateUIAuthor(){
        searchAdapter2FAuthor.setData(authorList, new IClickAuthor() {
            @Override
            public void onClickAuthor(Author artist) {
                main.openAuthorActivity(artist);
            }
        });
    }
}
