package hung.deptrai.comicapp.repository;

import android.app.Application;
import android.os.Build;
import android.os.ext.SdkExtensions;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.net.HttpURLConnection;

import hung.deptrai.comicapp.Utils.MessageStatusHTTP;
import hung.deptrai.comicapp.Utils.Status;
import hung.deptrai.comicapp.Utils.Tmp;
import hung.deptrai.comicapp.api.AccountService;
import hung.deptrai.comicapp.model.Account;
import hung.deptrai.comicapp.model.DataJSON;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountRepository {
    private Application application;
    private MutableLiveData<Integer> loginstatus = new MutableLiveData<>();
    private MutableLiveData<Integer> signupstatus = new MutableLiveData<>();
    private MutableLiveData<String> message = new MutableLiveData<>();
    private MutableLiveData<String> id = new MutableLiveData<>();

    public void setLoginstatus(int loginstatus) {
        this.loginstatus.setValue(loginstatus);
    }

    public void setSignupstatus(int signupstatus) {
        this.signupstatus.setValue(signupstatus);
    }

    public void setMessage(MutableLiveData<String> message) {
        this.message = message;
    }

    public AccountRepository(Application application) {
        this.application = application;
    }
    public AccountRepository(){}

    public MutableLiveData<Integer> getLoginstatus(Account account) {
        if(account.getUsername()!= null && account.getPassword()!= null){
            AccountService.accountService.checkLogin(account)
                    .enqueue(new Callback<DataJSON<Account>>() {
                        @Override
                        public void onResponse(Call<DataJSON<Account>> call, Response<DataJSON<Account>> response) {
                            if (response.isSuccessful()) {
                                DataJSON<Account> dataJSON = response.body();
                                if (dataJSON != null) {
                                    message.setValue(dataJSON.getMessage());
                                    if (dataJSON.isStatus()) {
                                        Account account1 = dataJSON.getData().get(0);
                                        loginstatus.setValue(Status.loginSuccess);
                                        Tmp.current_username = account.getUsername();
                                        Tmp.account_id = account1.getId();
                                    } else {
                                        loginstatus.setValue(Status.loginFail);
                                    }
                                } else {
                                    loginstatus.setValue(Status.loginFail);
                                }
                            } else if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                                // Xử lý khi mã trạng thái là 401 (Unauthorized)
                                message.setValue(MessageStatusHTTP.incorrectPassword);
                                loginstatus.setValue(Status.loginFail);
                            } else if(response.code() == HttpURLConnection.HTTP_NOT_FOUND){
                                // Xử lý các trường hợp khác
                                message.setValue(MessageStatusHTTP.notFoundUsername);
                                loginstatus.setValue(Status.loginFail);
                            } else if(response.code() == HttpURLConnection.HTTP_INTERNAL_ERROR){
                                message.setValue(MessageStatusHTTP.internalServerError);
                                loginstatus.setValue(Status.loginFail);
                            }
                        }

                        @Override
                        public void onFailure(Call<DataJSON<Account>> call, Throwable t) {
                            Log.e("ERROR", this.getClass().getName()+"onClickLogin()->onFailure: "+t.getMessage());
                            message.setValue("No internet connection");
                            loginstatus.setValue(Status.loginFail);
                        }
                    });
        }
        return loginstatus;
    }
    public MutableLiveData<Integer> getSignUpStatus(Account account) {
        if(account.getUsername()!= null && account.getPassword()!= null){
            AccountService.accountService.RegisterAccount(account)
                    .enqueue(new Callback<DataJSON<Account>>() {
                        @Override
                        public void onResponse(Call<DataJSON<Account>> call, Response<DataJSON<Account>> response) {
                            if (response.isSuccessful()) {
                                DataJSON<Account> dataJSON = response.body();
                                if (dataJSON != null) {
                                    message.setValue(dataJSON.getMessage());
                                    if (dataJSON.isStatus()) {
                                        signupstatus.setValue(Status.signUpSuccess);
                                    } else {
                                        loginstatus.setValue(Status.signUpFail);
                                    }
                                } else {
                                    signupstatus.setValue(Status.signUpFail);
                                }
                            } else if (response.code() == HttpURLConnection.HTTP_CONFLICT) {
                                // Xử lý khi mã trạng thái là 501 (Conflict)
                                message.setValue(MessageStatusHTTP.accountAlreadyExists);
                                signupstatus.setValue(Status.signUpFail);
                            } else if(response.code() == HttpURLConnection.HTTP_INTERNAL_ERROR){
                                message.setValue(MessageStatusHTTP.internalServerError);
                                signupstatus.setValue(Status.signUpFail);
                            }
                            else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                                message.setValue(MessageStatusHTTP.notImplemented);
                                signupstatus.setValue(Status.signUpFail);
                            }
                        }

                        @Override
                        public void onFailure(Call<DataJSON<Account>> call, Throwable t) {
                            Log.e("ERROR", this.getClass().getName()+"onClickLogin()->onFailure: "+t.getMessage());
                            message.setValue("No internet connection");
                            loginstatus.setValue(Status.loginFail);
                        }
                    });
        }
        return signupstatus;
    }
    public MutableLiveData<String> getMessage() {
        return message;
    }
}
