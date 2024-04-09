package hung.deptrai.comicapp.views.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hung.deptrai.comicapp.R;
import hung.deptrai.comicapp.Utils.MessageStatusHTTP;
import hung.deptrai.comicapp.Utils.Tmp;
import hung.deptrai.comicapp.api.ComicService;
import hung.deptrai.comicapp.api.CommentService;
import hung.deptrai.comicapp.model.Comic;
import hung.deptrai.comicapp.model.DataJSON;
import hung.deptrai.comicapp.viewmodel.ComicViewModel;
import hung.deptrai.comicapp.views.ComicActivity;
import hung.deptrai.comicapp.views.Interface.iUpdateFavouriteStatus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomSheetAddtoFavComic extends BottomSheetDialogFragment {
    private ConstraintLayout add,no_add;
    private ComicActivity comicActivity;
    private ComicViewModel comicViewModel;
    private static TextView tvFav;
    private static TextView tvUnFav;
    private static Boolean statusfav;
    private iUpdateFavouriteStatus listener;
    private Boolean addToFav;
    public BottomSheetAddtoFavComic(iUpdateFavouriteStatus listener){
        this.listener = listener;
    }
    @SuppressLint("MissingInflatedId")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bts = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_bottm_sheet_addto_fav,null);
        bts.setContentView(view);
        comicActivity = (ComicActivity) getActivity();

        //
        comicViewModel = new ViewModelProvider(this).get(ComicViewModel.class);

        //tv favourite
        tvFav = view.findViewById(R.id.tvAddFav);
        tvUnFav = view.findViewById(R.id.tvConfirm_no);

        add = view.findViewById(R.id.bottom_sheet_options_Fav);
        no_add = view.findViewById(R.id.bottom_sheet_options_Favorite_No);
        setTextFavourite(statusfav);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // thực hiện xử lý thêm vào favourite ở đây
//                int
                if(statusfav== true){

                }
                else{
                    addToFavourite(getComicID(),getFavID());
                }
                dismiss();
            }
        });
        no_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do không thêm vào nên chỉ thoát
                if(statusfav == true){
                    removeFromFavourite(getComicID(),getFavID());
                }
                dismiss();
            }
        });
        return bts;
    }

    private String getFavID() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            return bundle.getString("favouriteID");
        }
        else{
            return null;
        }
    }
    private String getComicID() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            return bundle.getString("comicID");
        }
        else{
            return null;
        }
    }

    private void removeFromFavourite(String comicID,String favouriteID) {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("comicID",comicID);
        hashMap.put("favouriteID",favouriteID);
        Log.e("comicID and favID",comicID + favouriteID);
        ComicService.comicService.removeFromFavourite(hashMap).enqueue(new Callback<DataJSON<Boolean>>() {
            @Override
            public void onResponse(Call<DataJSON<Boolean>> call, Response<DataJSON<Boolean>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()) {
                    if(dataJSON!=null){
                        if(dataJSON.isStatus() == true){
                            List<Boolean> favStatus = dataJSON.getData();
                            if(favStatus!=null){
                                if(favStatus.get(0) == true){
                                    Toast.makeText(comicActivity, "Xóa khỏi yêu thích thành công", Toast.LENGTH_SHORT).show();
                                    if(listener!=null){
                                        listener.onFavouriteStatus(false);
                                        Log.e("fav remove result listener:","true");
                                    }
                                    Log.e("fav remove result:","true");
                                }
                            }
                        }
                        else{
                            // do api trên server,nếu thành công remove thì sẽ trả ra status = true
                            // còn ko thì false,và phần obj List sẽ ko có gì
                            // thành công thì obj List sẽ cx trả ra thêm 1 List<boolean> là true nữa
                            // thực ra là ko cần thiết,hơi thừa nhưng thêm vào cho chắc,nên nhìn nó mới hài hài như này
                            Log.e("fav remove result:","false");
                            Toast.makeText(comicActivity, "Xóa khỏi yêu thích thất bại", Toast.LENGTH_SHORT).show();
                            listener.onFavouriteStatus(true);
                            Log.e("fav remove result listener:","false");
                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Log.e("Status favourites remove repository:", MessageStatusHTTP.notImplemented);
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Boolean>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(comicActivity, "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
//        comicViewModel.removeFromFavourite(hashMap).observe(this,favourites -> {
//            if(favourites.get(0) == true){
//                Toast.makeText(requireContext(), "Xóa khỏi yêu thích thành công", Toast.LENGTH_SHORT).show();
//                if(listener!=null){
//                    listener.onFavouriteStatus(false);
//                    Log.e("fav remove result listener:","true");
//                }
//                Log.e("fav remove result:","true");
//            }
//            else{
//                Log.e("fav remove result:","false");
//                Toast.makeText(requireContext(), "Xóa khỏi yêu thích thất bại", Toast.LENGTH_SHORT).show();
//                listener.onFavouriteStatus(true);
//                Log.e("fav remove result listener:","false");
//            }
////            comicViewModel.removeFromFavourite(hashMap).removeObservers(this);
//        });
    }

    private void addToFavourite(String comicID,String favouriteID) {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("comicID",comicID);
        hashMap.put("favouriteID",favouriteID);
        Log.e("comicID and favID",comicID + favouriteID);
        ComicService.comicService.addToFavourite(hashMap).enqueue(new Callback<DataJSON<Boolean>>() {
            @Override
            public void onResponse(Call<DataJSON<Boolean>> call, Response<DataJSON<Boolean>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()) {
                    if(dataJSON!=null){
                        if(dataJSON.isStatus() == true){
                            List<Boolean> favStatus = dataJSON.getData();
                            if(favStatus!=null){
                                if(favStatus.get(0) == true){
                                    Log.e("fav add result:","true");
                                    Toast.makeText(comicActivity, "Thêm vào yêu thích thành công", Toast.LENGTH_SHORT).show();
                                    if(listener!=null){
                                        listener.onFavouriteStatus(true);
                                        Log.e("fav add listener result:","true");
                                    }
                                }
                            }
                        }
                        else{
                            // do api trên server,nếu thành công add thì sẽ trả ra status = true
                            // còn ko thì false,và phần obj List sẽ ko có gì
                            // thành công thì obj List sẽ cx trả ra thêm 1 List<boolean> là true nữa
                            // thực ra là ko cần thiết,hơi thừa nhưng thêm vào cho chắc,nên nhìn nó mới hài hài như này
                            Log.e("fav add result:","false");
                            Toast.makeText(requireContext(), "Thêm vào yêu thích thất bại", Toast.LENGTH_SHORT).show();
                            listener.onFavouriteStatus(false);
                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Log.e("Status favourites add repository:", MessageStatusHTTP.notImplemented);
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Boolean>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(comicActivity, "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
//        comicViewModel.addToFavourite(hashMap).observe(this,favourites -> {
//            if(favourites.get(0) == true){
//                Log.e("fav add result:","true");
//                Toast.makeText(requireContext(), "Thêm vào yêu thích thành công", Toast.LENGTH_SHORT).show();
//                if(listener!=null){
//                    listener.onFavouriteStatus(true);
//                    Log.e("fav add listener result:","true");
//                }
//            }
//            else{
//                Log.e("fav add result:","false");
//                Toast.makeText(requireContext(), "Thêm vào yêu thích thất bại", Toast.LENGTH_SHORT).show();
//                listener.onFavouriteStatus(false);
//            }
////            comicViewModel.addToFavourite(hashMap).removeObservers(this);
//        });
    }

    public static void setData(Boolean status){
//        Bundle args = new Bundle();
//        args.putSerializable("key_dt",status);
        statusfav = status;
    }
    public static void setTextFavourite(Boolean statusfav){
        if(statusfav == true){
            tvFav.setText("Dismiss");
            tvUnFav.setText("Remove from favourite?");
        }
        else{
            tvFav.setText("Add to favourite?");
            tvUnFav.setText("Dismiss");
        }
    }
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(this); // hoặc fragmentManager.popBackStack();
        transaction.commit();
    }
}
