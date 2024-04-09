package hung.deptrai.comicapp.views.fragment.profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.checkerframework.checker.units.qual.h;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hung.deptrai.comicapp.R;
import hung.deptrai.comicapp.Utils.MessageStatusHTTP;
import hung.deptrai.comicapp.Utils.Tmp;
import hung.deptrai.comicapp.api.AccountService;
import hung.deptrai.comicapp.model.Account;
import hung.deptrai.comicapp.model.Author;
import hung.deptrai.comicapp.model.Chapter;
import hung.deptrai.comicapp.model.Comic;
import hung.deptrai.comicapp.model.DataJSON;
import hung.deptrai.comicapp.model.History;
import hung.deptrai.comicapp.viewmodel.ChapterViewModel;
import hung.deptrai.comicapp.viewmodel.ComicViewModel;
import hung.deptrai.comicapp.viewmodel.SignUpViewModel;
import hung.deptrai.comicapp.views.Interface.IClickComic;
import hung.deptrai.comicapp.views.Interface.IClickHistory;
import hung.deptrai.comicapp.views.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    private RecyclerView rcv ;
    private ProfileAdapter prof;
    private FloatingActionButton btnChangeUsername;
    private ShapeableImageView imgAccount;
    private MainActivity main;
    private TextView profile_tv,username,seeall_history;
    private LinearLayout layout;
    private RelativeLayout relativelayout;
    private ImageView img;
    private Button logout_btn;
    private ComicViewModel comicViewModel;
    private ChapterViewModel chapterViewModel;
    private SignUpViewModel signUpViewModel;
    private List<Comic> comicList;
    private List<Chapter> chapterList;
    private TextInputEditText current_password,new_password,renew_password;
    private SeekBar language;
    private Uri selectedImageUri;
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 100;
    private ActivityResultLauncher<String> read_file_external;
    public ProfileFragment(){

    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);

        comicViewModel = new ViewModelProvider(this).get(ComicViewModel.class);
        chapterViewModel = new ViewModelProvider(this).get(ChapterViewModel.class);
        signUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        //
        imgAccount = view.findViewById(R.id.profile_image);

        rcv = view.findViewById(R.id.rcv_profile);
        main = (MainActivity) getActivity();
        profile_tv = view.findViewById(R.id.textView3);
        username = view.findViewById(R.id.username);
        img = view.findViewById(R.id.imgHistory);
        seeall_history = view.findViewById(R.id.history_seeker);
        logout_btn = view.findViewById(R.id.btn_logout);

        // view component dialog_change_password
        current_password = view.findViewById(R.id.edtCurrentPassword);
        new_password = view.findViewById(R.id.edtNewPassword);
        renew_password = view.findViewById(R.id.edtPasswordReNew);
        btnChangeUsername =view.findViewById(R.id.btnChangeUsername);
        btnChangeUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View viewDialog = LayoutInflater.from(main).inflate(R.layout.dialog_change_password,null);
                current_password = viewDialog.findViewById(R.id.edtCurrentPassword);
                new_password = viewDialog.findViewById(R.id.edtNewPassword);
                renew_password = viewDialog.findViewById(R.id.edtPasswordReNew);
                AlertDialog alertDialog = new MaterialAlertDialogBuilder(main)
                        .setTitle("Change Password")
                        .setView(viewDialog)
                        .setPositiveButton("Change",(dialog, which) -> {
                            String strCurrentPassword = current_password.getText().toString();
                            String strNewPassword = new_password.getText().toString();
                            String strPasswordIncorrect = renew_password.getText().toString();
                            HashMap<String,String> hashMap = new HashMap<>();
                            hashMap.put("username",Tmp.current_username);
                            hashMap.put("oldpassword",strCurrentPassword);
                            hashMap.put("newpassword",strNewPassword);
                            Log.e("str",strCurrentPassword+" "+strNewPassword+" "+strPasswordIncorrect);
                            if(!strNewPassword.equals(strPasswordIncorrect)){
                                Toast.makeText(main,R.string.error_confirm_password_incorrect,Toast.LENGTH_SHORT).show();
                            }
                            else if (strNewPassword.equals("")){
                                Toast.makeText(main,R.string.error_input_new_password,Toast.LENGTH_SHORT).show();
                            }
                            else{
                                AccountService.accountService.updateAccount(hashMap).enqueue(new Callback<DataJSON<Account>>() {
                                    @Override
                                    public void onResponse(Call<DataJSON<Account>> call, Response<DataJSON<Account>> response) {
                                        if(response.isSuccessful()){
                                            DataJSON<Account> dj = response.body();
                                            if(dj!=null){
                                                if(dj.isStatus() == true){
                                                    Toast.makeText(main,dj.getMessage(),Toast.LENGTH_SHORT).show();
                                                }
                                                else{
                                                    Toast.makeText(main,dj.getMessage(),Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                            else{
                                                Toast.makeText(main,R.string.wrongPassword,Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        else if(response.code() == HttpURLConnection.HTTP_INTERNAL_ERROR || response.code() == HttpURLConnection.HTTP_BAD_REQUEST){
                                            Toast.makeText(main,"something went wrong:((",Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<DataJSON<Account>> call, Throwable t) {
                                        Log.e("ERROR", this.getClass().getName() + "onClickLogin()->onFailure: " + t.getMessage());
                                        Toast.makeText(main, "Tại sao ko chạy vào!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).setNegativeButton("Cancel",(dialog, which) -> {
                            dialog.dismiss();
                        })
                        .create();
                alertDialog.show();
            }
        });

        LinearLayoutManager linear = new LinearLayoutManager(main,LinearLayoutManager.HORIZONTAL,false);
        rcv.setLayoutManager(linear);

        getListChapter();
        getListComic();

        prof = new ProfileAdapter();
        prof.setData(comicList,chapterList, new IClickHistory() {
            @Override
            public void onClickHistory(Chapter history,Comic comic) {
                main.openReadingActivity(history,comic);
            }

            @Override
            public void onClickDelHistory(Comic comic) {

            }
        });

        rcv.setAdapter(prof);

        //
        seeall_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main.openHistoryActivty();
            }
        });

        //
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main.Logout();
            }
        });

        username.setText(Tmp.current_username);
        FirebaseApp.initializeApp(main);

        //
        ActivityResultLauncher<String> pickImageLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri o) {
                if (o != null) {
                    selectedImageUri = o;
                    Log.e("Image",o.toString());
                    // Hiển thị hình ảnh được chọn (tùy theo yêu cầu của ứng    dụng)
                    imgAccount.setImageURI(selectedImageUri);
                    uploadToFireBase(selectedImageUri);
                }
            }
        });
        imgAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageLauncher.launch("image/*");
            }
        });
        getImageUser();

        return view;
    }
    private void getImageUser() {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("id",Tmp.account_id);
        AccountService.accountService.getAccountByID(hashMap).enqueue(new Callback<DataJSON<Account>>() {
            @Override
            public void onResponse(Call<DataJSON<Account>> call, Response<DataJSON<Account>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()){
                    if(dataJSON!=null){
                        if(dataJSON.isStatus()){
                            List<Account> accounts = dataJSON.getData();
                            Glide.with(imgAccount).load(accounts.get(0).getAvatarAccount()).into(imgAccount);
                        }
                        else{

                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Toast.makeText(requireContext(), "Something went wrong when loading image!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Account>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(requireContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadToFireBase(Uri imageURL) {
        if(imageURL != null){
            FirebaseApp.initializeApp(requireContext());
            if(FirebaseApp.getInstance() != null) {
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("accountImage");
                final StorageReference imageRef = storageReference.child(imageURL.getLastPathSegment());
                imageRef.putFile(imageURL).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String imageURI = uri.toString();
                                Log.e("ERROR",imageURI.toString());
                                HashMap<String,String> hashMap = new HashMap<>();
                                hashMap.put("avatarAccount",imageURI);
                                hashMap.put("id",Tmp.account_id);
                                signUpViewModel.getUpdateUserImage(hashMap).observe(getViewLifecycleOwner(),aBoolean -> {
                                    Toast.makeText(main, "Upload Image Successfully!", Toast.LENGTH_SHORT).show();
                                });
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("ERROR",e.getMessage());
                        Toast.makeText(main, "Some thing went wrong when upload this image!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else{
                Toast.makeText(main, "Firebase chưa được khởi tạo", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private List<History> getHistoryList() {
        List<History> list = new ArrayList<>();
        list.add(new History("1","Toàn cơ từ","chuduchung","https://img.wattpad.com/cover/109018367-144-k677651.jpg"));
//        list.add(new History("2","Toàn cơ từ","chuduchung","https://img.wattpad.com/cover/109018367-144-k677651.jpg"));
        return list;
    }
    public void getListComic(){
        comicList = new ArrayList<>();
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("username", Tmp.current_username);
        comicViewModel.getComicHistory(hashMap).observe(getViewLifecycleOwner(),comics -> {
            comicList = new ArrayList<>();
            for (int i = 0; i < comics.size(); i++) {
                comicList.add(new Comic(comics.get(i).getId(),comics.get(i).getComicName(),comics.get(i).getLinkPicture(),comics.get(i).getViews(),comics.get(i).getCreated_at(),comics.get(i).getUpdated_at(),comics.get(i).getViews_in_month(),comics.get(i).getViews_in_week()));
            }
            updateUI();
//            comicViewModel.getComicHistory(hashMap).removeObservers(getViewLifecycleOwner());
        });
    }

    private void updateUI() {
        prof.setData(comicList, chapterList, new IClickHistory() {
            @Override
            public void onClickHistory(Chapter chapter,Comic comic) {
                main.openReadingActivity(chapter,comic);
            }

            @Override
            public void onClickDelHistory(Comic comic) {

            }
        });
    }

    public void getListChapter(){
        chapterList = new ArrayList<>();
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("username", Tmp.current_username);
        chapterViewModel.getChapterHistory(hashMap).observe(getViewLifecycleOwner(),list -> {
            chapterList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                chapterList.add(new Chapter(list.get(i).getChapterID(),list.get(i).getChapter_pos(),list.get(i).getComicIDfk(),list.get(i).getUpdated_at()));
                Log.e("chapter:",String.valueOf(list.get(i).getChapterID())+list.get(i).getChapter_pos()+String.valueOf(list.get(i).getComicIDfk())+String.valueOf(list.get(i).getUpdated_at()));
            }
            updateUI();
//            chapterViewModel.getChapterHistory(hashMap).removeObservers(getViewLifecycleOwner());
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        getListChapter();
        getListComic();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
        getListChapter();
        getListComic();
    }

    @Override
    public void onResume() {
        super.onResume();
        getListChapter();
        getListComic();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
