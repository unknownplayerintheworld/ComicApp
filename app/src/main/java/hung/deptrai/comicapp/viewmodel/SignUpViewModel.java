package hung.deptrai.comicapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import hung.deptrai.comicapp.Utils.Status;
import hung.deptrai.comicapp.Utils.ValidatorUtil;
import hung.deptrai.comicapp.model.Account;
import hung.deptrai.comicapp.repository.AccountRepository;

public class SignUpViewModel extends AndroidViewModel {
    private AccountRepository accountRepository;
    private String confirmPassword = new String();
    private MutableLiveData<Boolean> login = new MutableLiveData<>();
    Account acc = new Account();
    public SignUpViewModel(@NonNull Application application) {
        super(application);
        accountRepository = new AccountRepository();
    }

    public Account getAcc() {
        return acc;
    }
    public LiveData<Integer> getSignupStatus(){
        return accountRepository.getSignUpStatus(acc);
    }
    public LiveData<String> getMessage(){
        return accountRepository.getMessage();
    }
    public void onClickSignup(){
        if(validateData()){
            getSignupStatus();
            getMessage();
        }
    }
    public void onCLickLogin(){
        login.setValue(true);
    }

    public MutableLiveData<Boolean> getLogin() {
        return login;
    }

    public boolean validateData () {
        if (ValidatorUtil.emptyValue(acc.getUsername())) {
            accountRepository.setSignupstatus(Status.emptyUsername);
            return false;
        }
        else if (ValidatorUtil.emptyValue(acc.getPassword())) {
            accountRepository.setSignupstatus(Status.emptyPassword);
            return false;
        }
        else if (ValidatorUtil.emptyValue(confirmPassword)) {
            accountRepository.setSignupstatus(Status.emptyConfirmPassword);
            return false;
        }
        else if (acc.getPassword().equals(confirmPassword) == false) {
            accountRepository.setSignupstatus(Status.incorrectConfirmPassword);
            return false;
        }
        return true;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
