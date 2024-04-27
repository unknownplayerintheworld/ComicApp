package hung.deptrai.comicapp.api;

import java.util.HashMap;

import hung.deptrai.comicapp.model.Account;
import hung.deptrai.comicapp.model.Chapter;
import hung.deptrai.comicapp.model.Comment;
import hung.deptrai.comicapp.model.DataJSON;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.HTTP;
import retrofit2.http.POST;

public interface CommentService {
    CommentService commentService = new Retrofit.Builder().baseUrl("https://comicapi-production.up.railway.app/api/v1/comment/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CommentService.class);

    @POST("chapter/root")
    Call<DataJSON<Comment>> getAllRootCommentInTheChapter(@Body HashMap hashMap);

    @POST("child/count/comment")
    Call<DataJSON<Integer>> getChildCommentCount(@Body HashMap hashMap);
    @POST("child/count/commentid")
    Call<DataJSON<Integer>> getChildCommentIDCount(@Body HashMap hashMap);
    @POST("comic/child/count/comment")
    Call<DataJSON<Integer>> getChildCommentCountComic(@Body HashMap hashMap);
    @POST("comic/child/count/commentid")
    Call<DataJSON<Integer>> getChildCommentIDCountComic(@Body HashMap hashMap);

    @POST("comic/root")
    Call<DataJSON<Comment>> getAllRootCommentInTheComic(@Body HashMap hashMap);

    @POST("child")
    Call<DataJSON<Comment>> getChildComment(@Body HashMap hashMap);

    @POST("account/root/chapter")
    Call<DataJSON<Account>> getAccountRootCommentChapter(@Body HashMap hashMap);
    @POST("account/root/comic")
    Call<DataJSON<Account>> getAccountRootCommentComic(@Body HashMap hashMap);
    @POST("account/child")
    Call<DataJSON<Account>> getAccountChildComment(@Body HashMap hashMap);

    @POST("write/root")
    Call<DataJSON<Comment>> writeComment(@Body HashMap hashMap);

    @POST("react/increase")
    Call<DataJSON<Boolean>> updateLODIncrease(@Body HashMap hashMap);

    @POST("react/decrease")
    Call<DataJSON<Boolean>> updateLODDecrease(@Body HashMap hashMap);

    @POST("react/check/account")
    Call<DataJSON<Integer>> checkAccountLOD(@Body HashMap hashMap);
    @POST("react/check/comic/accountid")
    Call<DataJSON<Integer>> checkAccountLODComic(@Body HashMap hashMap);
    @POST("react/check/lodtype")
    Call<DataJSON<Boolean>> checkLODTypeLOD(@Body HashMap hashMap);
    @POST("react/check/comic/lodtpye")
    Call<DataJSON<Boolean>> checkLODTypeLODComic(@Body HashMap hashMap);
    @POST("react/check/commentlodid")
    Call<DataJSON<Integer>> checkCommentIDLOD(@Body HashMap hashMap);
    @POST("react/check/comic/commentlodid")
    Call<DataJSON<Integer>> checkCommentIDLODComic(@Body HashMap hashMap);
    @POST("react/check/commentid")
    Call<DataJSON<Integer>> checkCommentWriteIDLOD(@Body HashMap hashMap);
    //for chapter
    @POST("chapter/filter/react")
    Call<DataJSON<Comment>> getCommentByReact(@Body HashMap hashMap);
    @POST("chapter/filter/updatedtime")
    Call<DataJSON<Comment>> getCommentByTime(@Body HashMap hashMap);
    @POST("chapter/account/filter/react")
    Call<DataJSON<Account>> getAccountCommentByReact(@Body HashMap hashMap);
    @POST("chapter/account/filter/updatedtime")
    Call<DataJSON<Account>> getAccountCommentByTime(@Body HashMap hashMap);

    // for comic
    @POST("comic/filter/react")
    Call<DataJSON<Comment>> getCommentByReactComic(@Body HashMap hashMap);
    @POST("comic/filter/updatedtime")
    Call<DataJSON<Comment>> getCommentByTimeComic(@Body HashMap hashMap);
    @POST("comic/account/filter/react")
    Call<DataJSON<Account>> getAccountCommentByReactComic(@Body HashMap hashMap);
    @POST("comic/account/filter/updatedtime")
    Call<DataJSON<Account>> getAccountCommentByTimeComic(@Body HashMap hashMap);


    @HTTP(method = "DELETE", path = "remove", hasBody = true)
    Call<DataJSON<Boolean>> removeComment(@Body HashMap hashmap);

    @POST("reply")
    Call<DataJSON<Boolean>> replyComment(@Body HashMap hashMap);

    @POST("id")
    Call<DataJSON<Comment>> getCommentByCommentID(@Body HashMap hashMap);

    @POST("account/id")
    Call<DataJSON<Account>> getAccountByCommentID(@Body HashMap hashMap);

    @POST("comic/chapter")
    Call<DataJSON<Chapter>> getChapterCommentComic(@Body HashMap hashMap);

    @POST("comic/chapter/react")
    Call<DataJSON<Chapter>> getChapterCommentComicReact(@Body HashMap hashMap);
}
