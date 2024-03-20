package hung.deptrai.comicapp.views.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hung.deptrai.comicapp.R;
import hung.deptrai.comicapp.Utils.Tmp;
import hung.deptrai.comicapp.model.Chapter;
import hung.deptrai.comicapp.viewmodel.ChapterViewModel;
import hung.deptrai.comicapp.views.Interface.IClickChapter;
import hung.deptrai.comicapp.views.ReadingActivity;
import hung.deptrai.comicapp.views.adapter.SelectChapBottomSheetAdapter;

public class BottomSheetChapterFragment extends BottomSheetDialogFragment {
    private List<Chapter> chapterList,chapterHistory;
    private IClickChapter iClickChapter;
    private ChapterViewModel chapterViewModel;
    private SelectChapBottomSheetAdapter adap;
    private ReadingActivity readingActivity;
    public BottomSheetChapterFragment(List<Chapter> list,IClickChapter iclick){
        this.chapterList = list;
        iClickChapter = iclick;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_bottm_sheet_chapter,null);
        bottomSheetDialog.setContentView(view);

        RecyclerView rcv = view.findViewById(R.id.bottom_shee_chapter_list_rcv);
        LinearLayoutManager lnm = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(lnm);
        readingActivity = (ReadingActivity)getActivity();
        chapterViewModel = new ViewModelProvider(this).get(ChapterViewModel.class);

        adap = new SelectChapBottomSheetAdapter();
        adap.setData(chapterList,chapterHistory, chapter -> {
//                iClickChapter.onClickChapter(chapter);
//                Intent intent = new Intent("chap_selected");
//                intent.putExtra("chapter_pos", chapter.getChapter_pos());
//                Log.e("chapter bts",chapter.getChapter_pos());
//                getContext().sendBroadcast(intent);
//                dismiss();
            openAgainReadingActivity(chapter);
        });
        rcv.setAdapter(adap);
        getListChapter();
        getChapterHistory();
//        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
//        rcv.addItemDecoration(itemDecoration);
        return bottomSheetDialog;
    }

    private void openAgainReadingActivity(Chapter chapter) {
        // Tạo một Intent
        Intent intent = readingActivity.getIntent();

// Đính kèm dữ liệu vào Intent
        intent.putExtra("key", "value");

// Kết thúc Activity hiện tại
        readingActivity.finish();

// Khởi động lại Activity hiện tại bằng Intent đã được nhận
        startActivity(intent);

    }

    public void getListChapter() {
        chapterList = new ArrayList<>();
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("comicID",getComicID());
        chapterViewModel.getChapterList(hashMap).observe(this,chapters -> {
            chapterList = chapters;
            updateUI();
            chapterViewModel.getChapterList(hashMap).removeObservers(this);
        });
    }

    private void updateUI() {
        adap.setData(chapterList,chapterHistory, new IClickChapter() {
            @Override
            public void onClickChapter(Chapter chapter) {
                iClickChapter.onClickChapter(chapter);
            }
        });
    }

    public String getComicID(){
        Bundle bundle = getArguments();
        if(bundle == null){
            return "";
        }
        return bundle.getString("comicID");
    }
    public void getChapterHistory(){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("comicID",getComicID());
        hashMap.put("accountID", Tmp.account_id);
        chapterViewModel.getChapterHistoryComic(hashMap).observe(this,chapterList1 -> {
            chapterHistory = chapterList1;
            updateUI();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getListChapter();
        getChapterHistory();
    }
}
