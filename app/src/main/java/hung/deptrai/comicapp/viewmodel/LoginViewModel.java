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

public class LoginViewModel extends AndroidViewModel {
    private AccountRepository accountRepository;
    private MutableLiveData<Boolean> signup = new MutableLiveData<>();
    Account acc = new Account();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        accountRepository = new AccountRepository(application);
    }

    public Account getAcc() {
        return acc;
    }
    public LiveData<Integer> getLoginStatus(){
        return accountRepository.getLoginstatus(acc);
    }
    public LiveData<Boolean> getSignup(){
        return signup;
    }
    public LiveData<String> getMessage(){
        return accountRepository.getMessage();
    }
    public void onClickLogin(){
        if(validateData()){
            getLoginStatus();
            getMessage();
        }
    }

    public void onClickSignup(){
        signup.setValue(true);
    }
    public boolean validateData () {
        if (ValidatorUtil.emptyValue(acc.getUsername())) {
            accountRepository.setLoginstatus(Status.emptyUsername);
            return false;
        }
        else if (ValidatorUtil.emptyValue(acc.getPassword())) {
            accountRepository.setLoginstatus(Status.emptyPassword);
            return false;
        }
        return true;
    }
}
