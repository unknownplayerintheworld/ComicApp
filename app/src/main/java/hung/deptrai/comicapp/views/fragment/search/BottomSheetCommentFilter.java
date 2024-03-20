package hung.deptrai.comicapp.views.fragment.search;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import hung.deptrai.comicapp.R;
import hung.deptrai.comicapp.viewmodel.CommentViewModel;
import hung.deptrai.comicapp.views.CommentActivity;
import hung.deptrai.comicapp.views.Interface.iFilterComment;

public class BottomSheetCommentFilter extends BottomSheetDialogFragment {
    private RadioButton newest_comment_btn;
    private RadioButton most_react_btn;
    private RadioGroup radioGroup;
    private CommentViewModel commentViewModel;
    private static Boolean filterStatus;
    private CommentActivity commentActivity;
    private iFilterComment listener;
    public BottomSheetCommentFilter(iFilterComment listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bts = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_bottom_sheet_comment_filter,null);
        bts.setContentView(view);
//        commentActivity = (CommentActivity) getActivity();

        //vm
        commentViewModel = new ViewModelProvider(this).get(CommentViewModel.class);

        radioGroup = view.findViewById(R.id.radioGroup);
        newest_comment_btn = view.findViewById(R.id.newest_comment_btn);
        most_react_btn = view.findViewById(R.id.most_react_comment_btn);

        if(getFilterStatus() == true){
            radioGroup.check(R.id.newest_comment_btn);
        }else{
            radioGroup.check(R.id.most_react_comment_btn);
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Kiểm tra xem RadioButton nào được chọn và xử lý tương ứng
                if(checkedId == R.id.newest_comment_btn){
                    if(listener!=null){
                        listener.onFilterStatus(true);
                    }
                }
                else if(checkedId == R.id.most_react_comment_btn){
                    if(listener!=null){
                        listener.onFilterStatus(false);
                    }
                }
            }
        });
        return bts;
    }
    public Boolean getFilterStatus(){
        Bundle bundle = getArguments();
        if (bundle != null) {
            return bundle.getBoolean("statusFilter");
        }
        else{
            return null;
        }
    }
}
