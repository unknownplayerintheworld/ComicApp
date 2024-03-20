package hung.deptrai.comicapp.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

import hung.deptrai.comicapp.R;
import hung.deptrai.comicapp.Utils.Status;
import hung.deptrai.comicapp.Utils.Tmp;
import hung.deptrai.comicapp.viewmodel.LoginViewModel;
import hung.deptrai.comicapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private AppCompatButton loginbtn,signupbtn;
    private LoginViewModel login;
    private ActivityLoginBinding binding;
    private TextInputEditText txtUsername;
    private TextInputEditText txtPassword;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        login = new ViewModelProvider(this).get(LoginViewModel.class);
        binding = DataBindingUtil.setContentView(LoginActivity.this,R.layout.activity_login);
        binding.setLifecycleOwner(this);
        binding.setLoginViewModel(login);
//        setContentView(R.layout.activity_login);
        processLogin();
        processSignUp();
    }

    private void processSignUp() {
        login.getSignup().observe(this,isClick ->{
            if(isClick){
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void processLogin() {
        login.getLoginStatus().observe(this, status -> {
            switch (status) {
                case Status.loginSuccess:
                    //xu ly dang nhap thanh cong
                    Toast.makeText(LoginActivity.this, R.string.toast_login_success, Toast.LENGTH_SHORT).show();
                    Tmp.current_username = binding.txtUsername.toString();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
                case Status.emptyUsername:
                    //thong bao Toast message hoac hien thi loi Username trong
                    binding.txtUsername.setError(getString(R.string.emptyUsername));
                    binding.txtUsername.requestFocus();
                    break;
                case Status.emptyPassword:
                    //tuong tu cho password
                    binding.txtPassword.setError(getString(R.string.emptypassword));
                    binding.txtPassword.requestFocus();
                    break;
                case Status.loginFail:
                    String message = login.getMessage().getValue();
//                    if (message.contains(getString(R.string.sub_str_error_php_api)) == true) {
//                        Toast.makeText(LoginActivity.this, getString(R.string.toast_login_failed)+". "+getString(R.string.error_php_api), Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
//                    }
                    Toast.makeText(LoginActivity.this,message.toString(),Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }
}