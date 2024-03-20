package hung.deptrai.comicapp.views.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import hung.deptrai.comicapp.R;

public class BottomSheetAddToFavourite extends BottomSheetDialogFragment {
    private ConstraintLayout add,no_add;
    @SuppressLint("MissingInflatedId")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bts = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_bottm_sheet_addto_fav,null);
        bts.setContentView(view);
        add = view.findViewById(R.id.bottom_sheet_options_Fav);
        no_add = view.findViewById(R.id.bottom_sheet_options_Favorite_No);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // thực hiện xử lý thêm vào favourite ở đây
                // sau đó là thoát khỏi activty đọc kèm dismiss thằng fragment bts ở đây
                dismiss();
                if(getActivity() != null){
                    getActivity().finish();
                }
            }
        });
        no_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do không thêm vào nên chỉ thoát
                // thoát khỏi activty đọc kèm dismiss thằng fragment bts ở đây
                dismiss();
                if(getActivity() != null){
                    getActivity().finish();
                }
            }
        });
        return bts;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
    }
}
