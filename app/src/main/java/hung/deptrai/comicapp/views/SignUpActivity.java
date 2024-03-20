package hung.deptrai.comicapp.views;

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
import hung.deptrai.comicapp.databinding.ActivityLoginBinding;
import hung.deptrai.comicapp.databinding.ActivitySignupBinding;
import hung.deptrai.comicapp.viewmodel.LoginViewModel;
import hung.deptrai.comicapp.viewmodel.SignUpViewModel;

public class SignUpActivity extends AppCompatActivity {
    private AppCompatButton loginbtn,signupbtn;
    private SignUpViewModel signup;
    private ActivitySignupBinding binding;
    private TextInputEditText txtUsername;
    private TextInputEditText txtPassword;

    private TextInputEditText txtConfirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        signup = new ViewModelProvider(this).get(SignUpViewModel.class);
//        binding = DataBindingUtil.setContentView(SignUpActivity.this,R.layout.activity_signup);
//        binding.setLifecycleOwner(this);
//        binding.set(signup);
//        setContentView(R.layout.activity_login);
        signup = new ViewModelProvider(this).get(SignUpViewModel.class);
        binding = DataBindingUtil.setContentView(SignUpActivity.this,R.layout.activity_signup);
        binding.setLifecycleOwner(this);
        binding.setSignUpViewModel(signup);
        processLogin();
        processSignUp();
    }

    private void processSignUp() {
        signup.getSignupStatus().observe(this, status -> {
            switch (status) {
                case Status.signUpSuccess:
                    //xu ly dang nhap thanh cong
                    Toast.makeText(SignUpActivity.this, R.string.toast_signup_success, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
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
                case Status.emptyConfirmPassword:
                    binding.txtConfirmPassword.setError(getString(R.string.error_input_confirm_password));
                    binding.txtConfirmPassword.requestFocus();
                    break;
                case Status.incorrectConfirmPassword:
                    binding.txtConfirmPassword.setError(getString(R.string.error_confirm_password_incorrect));
                    binding.txtConfirmPassword.requestFocus();
                    break;
                case Status.signUpFail:
                    String message = signup.getMessage().getValue();
//                    if (message.contains(getString(R.string.sub_str_error_php_api)) == true) {
//                        Toast.makeText(LoginActivity.this, getString(R.string.toast_login_failed)+". "+getString(R.string.error_php_api), Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
//                    }
                    Toast.makeText(SignUpActivity.this,message.toString(),Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    private void processLogin() {
        signup.getLogin().observe(this,status ->{
            if(status){
                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
