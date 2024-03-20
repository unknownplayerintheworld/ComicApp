package hung.deptrai.comicapp.views.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.HashMap;

import hung.deptrai.comicapp.R;
import hung.deptrai.comicapp.viewmodel.CommentViewModel;
import hung.deptrai.comicapp.views.CommentActivity;
import hung.deptrai.comicapp.views.Interface.iOptionComment;

public class BottomSheetOptionComment extends BottomSheetDialogFragment {
    private ConstraintLayout item_del_comment;
    private CommentViewModel commentViewModel;
//    private CommentActivity commentActivity;
    private iOptionComment listener;
    public BottomSheetOptionComment(iOptionComment listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bts = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_option_comment,null);
        bts.setContentView(view);
//        commentActivity = (CommentActivity) getActivity();
        item_del_comment = view.findViewById(R.id.bottom_sheet_options_Comment_No);
        commentViewModel = new ViewModelProvider(this).get(CommentViewModel.class);

        item_del_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteComment();
            }
        });
        return bts;
    }

    private void deleteComment() {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("commentID",getCommentID());
        commentViewModel.removeComment(hashMap).observe(this,delStatus -> {
            if(delStatus.get(0) == true){
                Toast.makeText(requireContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                if(listener!=null){
                    listener.onLongClick(true);
                    Log.e("cmt remove result listener:","true");
                }
                Log.e("cmt remove result:","true");
            }
            else{
                Log.e("cmt remove result:","false");
                Toast.makeText(requireContext(), "Xóa thất bại", Toast.LENGTH_SHORT).show();
                listener.onLongClick(false);
                Log.e("cmt remove result listener:","false");
            }
            dismiss();
//            comicViewModel.removeFromFavourite(hashMap).removeObservers(this);
        });
    }
    private String getCommentID() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            return bundle.getString("commentID");
        }
        else{
            return null;
        }
    }
}
