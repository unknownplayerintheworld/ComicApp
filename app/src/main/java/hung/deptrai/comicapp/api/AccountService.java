package hung.deptrai.comicapp.api;

import java.util.HashMap;

import hung.deptrai.comicapp.model.Account;
import hung.deptrai.comicapp.model.DataJSON;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AccountService {
    //comicapi-production.up.railway.app
    AccountService accountService = new Retrofit.Builder().baseUrl("https://comicapi-production.up.railway.app/api/v1/Accounts/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AccountService.class);
    @POST("login")
    Call<DataJSON<Account>> checkLogin(@Body Account account);

    @POST("insert")
    Call<DataJSON<Account>> RegisterAccount(@Body Account account);

    @POST("update")
    Call<DataJSON<Account>> updateAccount(@Body HashMap<String,String> hashMap);

    @POST("update/image")
    Call<DataJSON<Boolean>> updateUserIMage(@Body HashMap hashMap);

    @POST("accountid")
    Call<DataJSON<Account>> getAccountByID(@Body HashMap hashMap);

//    @POST("id")
//    Call<DataJSON<Account>> getAccountByAccountID(@Body HashMap<String,String> hashMap);
}
