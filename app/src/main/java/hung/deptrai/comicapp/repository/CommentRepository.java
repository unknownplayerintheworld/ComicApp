package hung.deptrai.comicapp.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hung.deptrai.comicapp.Utils.MessageStatusHTTP;
import hung.deptrai.comicapp.api.CommentService;
import hung.deptrai.comicapp.model.Account;
import hung.deptrai.comicapp.model.Chapter;
import hung.deptrai.comicapp.model.Comment;
import hung.deptrai.comicapp.model.DataJSON;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentRepository {
    private Application application;
    private MutableLiveData<List<Comment>> rootCommentsChapter = new MutableLiveData<>();
    private MutableLiveData<List<Account>> accountRootChapters = new MutableLiveData<>();
    private MutableLiveData<List<Comment>> rootCommentByReact = new MutableLiveData<>();
    private MutableLiveData<List<Comment>> rootCommentByTime = new MutableLiveData<>();
    private MutableLiveData<List<Account>> accountRootChaptersByReact = new MutableLiveData<>();
    private MutableLiveData<List<Account>> accountRootChaptersByTime = new MutableLiveData<>();

    private MutableLiveData<List<Comment>> rootCommentByReactComic = new MutableLiveData<>();
    private MutableLiveData<List<Comment>> rootCommentByTimeComic = new MutableLiveData<>();
    private MutableLiveData<List<Account>> accountRootComicByReact = new MutableLiveData<>();
    private MutableLiveData<List<Account>> accountRootComicByTime = new MutableLiveData<>();

    private MutableLiveData<List<Comment>> rootCommentsComic = new MutableLiveData<>();
    private MutableLiveData<List<Account>> accountRootComics = new MutableLiveData<>();
    private MutableLiveData<List<Integer>> childCommentcount = new MutableLiveData<>();
    private MutableLiveData<List<Integer>> childCommentIdCount = new MutableLiveData<>();

    private MutableLiveData<List<Integer>> childCommentcountComic = new MutableLiveData<>();
    private MutableLiveData<List<Integer>> childCommentIdCountComic = new MutableLiveData<>();

    private MutableLiveData<Boolean> writeStatus = new MutableLiveData<>();
    private MutableLiveData<Boolean> replyStatus = new MutableLiveData<>();
    private MutableLiveData<List<Comment>> childComments = new MutableLiveData<>();
    private MutableLiveData<List<Account>> accountChild = new MutableLiveData<>();

    private MutableLiveData<Boolean> updateLODIncStatus = new MutableLiveData<>();
    private MutableLiveData<Boolean> updateLODDescStatus = new MutableLiveData<>();
    private MutableLiveData<List<Integer>> checkAccountLOD = new MutableLiveData<>();
    private MutableLiveData<List<Boolean>> checkLODTypeLOD = new MutableLiveData<>();
    private MutableLiveData<List<Integer>> checkCommentIDLOD = new MutableLiveData<>();
    private MutableLiveData<List<Integer>> checkAccountLODComic = new MutableLiveData<>();
    private MutableLiveData<List<Boolean>> checkLODTypeLODComic = new MutableLiveData<>();
    private MutableLiveData<List<Integer>> checkCommentIDLODComic = new MutableLiveData<>();
    private MutableLiveData<List<Integer>> checkCommentWriteIDLOD = new MutableLiveData<>();
    private MutableLiveData<List<Boolean>> delCommentStatus = new MutableLiveData<>();
    private MutableLiveData<List<Comment>> CommentByCommentID = new MutableLiveData<>();
    private MutableLiveData<List<Account>> AccountByCommentID = new MutableLiveData<>();
    private MutableLiveData<List<Chapter>> chapterCommentComic = new MutableLiveData<>();
    private MutableLiveData<List<Chapter>> chapterCommentComicReact = new MutableLiveData<>();
    public CommentRepository(Application application){
        this.application = application;
    }
    public MutableLiveData<List<Comment>> getRootCommentsChapter(HashMap<String,String> hashMap){
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Log.d("HashMap Entry", "Key: " + key + ", Value: " + value);
        }
        CommentService.commentService.getAllRootCommentInTheChapter(hashMap).enqueue(new Callback<DataJSON<Comment>>() {
            @Override
            public void onResponse(Call<DataJSON<Comment>> call, Response<DataJSON<Comment>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()){
                    if(dataJSON!=null){
                        if(dataJSON.isStatus()){
                            List<Comment> commentList = dataJSON.getData();
                            rootCommentsChapter.setValue(commentList);
                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Toast.makeText(application, MessageStatusHTTP.notImplemented,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Comment>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return rootCommentsChapter;
    }
    public MutableLiveData<List<Comment>> getRootCommentsComic(HashMap<String,String> hashMap){
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Log.d("HashMap Entry", "Key: " + key + ", Value: " + value);
        }
        CommentService.commentService.getAllRootCommentInTheComic(hashMap).enqueue(new Callback<DataJSON<Comment>>() {
            @Override
            public void onResponse(Call<DataJSON<Comment>> call, Response<DataJSON<Comment>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()){
                    if(dataJSON!=null){
                        if(dataJSON.isStatus()){
                            List<Comment> commentList = dataJSON.getData();
                            rootCommentsComic.setValue(commentList);
                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Toast.makeText(application, MessageStatusHTTP.notImplemented,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Comment>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return rootCommentsComic;
    }
    public MutableLiveData<List<Integer>> getChildCommentCount(HashMap<String,String> hashMap){
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Log.d("HashMap Entry", "Key: " + key + ", Value: " + value);
        }
        CommentService.commentService.getChildCommentCount(hashMap).enqueue(new Callback<DataJSON<Integer>>() {
            @Override
            public void onResponse(Call<DataJSON<Integer>> call, Response<DataJSON<Integer>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()){
                    if(dataJSON!=null){
                        if(dataJSON.isStatus()){
                            List<Integer> commentList = dataJSON.getData();
                            childCommentcount.setValue(commentList);
                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Toast.makeText(application, MessageStatusHTTP.notImplemented,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Integer>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return childCommentcount;
    }
    public MutableLiveData<List<Integer>> getChildCommentCountComic(HashMap<String,String> hashMap){
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Log.d("HashMap Entry", "Key: " + key + ", Value: " + value);
        }
        CommentService.commentService.getChildCommentCountComic(hashMap).enqueue(new Callback<DataJSON<Integer>>() {
            @Override
            public void onResponse(Call<DataJSON<Integer>> call, Response<DataJSON<Integer>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()){
                    if(dataJSON!=null){
                        if(dataJSON.isStatus()){
                            List<Integer> commentList = dataJSON.getData();
                            childCommentcountComic.setValue(commentList);
                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Toast.makeText(application, MessageStatusHTTP.notImplemented,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Integer>> call, Throwable t) {

            }
        });

        return childCommentcountComic;
    }
    public MutableLiveData<List<Integer>> getChildCommentIDCount(HashMap hashMap){
        CommentService.commentService.getChildCommentIDCount(hashMap).enqueue(new Callback<DataJSON<Integer>>() {
            @Override
            public void onResponse(Call<DataJSON<Integer>> call, Response<DataJSON<Integer>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()){
                    if(dataJSON!=null){
                        if(dataJSON.isStatus()){
                            List<Integer> commentList = dataJSON.getData();
                            childCommentIdCount.setValue(commentList);
                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Toast.makeText(application, MessageStatusHTTP.notImplemented,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Integer>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();

            }
        });
        return childCommentIdCount;
    }
    public MutableLiveData<List<Integer>> getChildCommentIDCountComic(HashMap hashMap){
        CommentService.commentService.getChildCommentIDCountComic(hashMap).enqueue(new Callback<DataJSON<Integer>>() {
            @Override
            public void onResponse(Call<DataJSON<Integer>> call, Response<DataJSON<Integer>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()){
                    if(dataJSON!=null){
                        if(dataJSON.isStatus()){
                            List<Integer> commentList = dataJSON.getData();
                            childCommentIdCountComic.setValue(commentList);
                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Toast.makeText(application, MessageStatusHTTP.notImplemented,Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<DataJSON<Integer>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return childCommentIdCountComic;
    }
    public MutableLiveData<List<Comment>> getChildComment(HashMap<String,String> hashMap){
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Log.d("HashMap Entry", "Key: " + key + ", Value: " + value);
        }
        CommentService.commentService.getChildComment(hashMap).enqueue(new Callback<DataJSON<Comment>>() {
            @Override
            public void onResponse(Call<DataJSON<Comment>> call, Response<DataJSON<Comment>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()){
                    if(dataJSON!=null){
                        if(dataJSON.isStatus()){
                            List<Comment> commentList = dataJSON.getData();
                            childComments.setValue(commentList);
                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Toast.makeText(application, MessageStatusHTTP.notImplemented,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Comment>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return childComments;
    }
    public MutableLiveData<List<Account>> getAccountRootChapters(HashMap<String,String> hashMap) {
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Log.d("HashMap Entry", "Key: " + key + ", Value: " + value);
        }
        CommentService.commentService.getAccountRootCommentChapter(hashMap).enqueue(new Callback<DataJSON<Account>>() {
            @Override
            public void onResponse(Call<DataJSON<Account>> call, Response<DataJSON<Account>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()){
                    if(dataJSON!=null){
                        if(dataJSON.isStatus()){
                            List<Account> commentList = dataJSON.getData();
                            accountRootChapters.setValue(commentList);
                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Toast.makeText(application, MessageStatusHTTP.notImplemented,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Account>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return accountRootChapters;
    }
    public MutableLiveData<List<Account>> getAccountRootComics(HashMap<String,String> hashMap) {
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Log.d("HashMap Entry", "Key: " + key + ", Value: " + value);
        }
        CommentService.commentService.getAccountRootCommentComic(hashMap).enqueue(new Callback<DataJSON<Account>>() {
            @Override
            public void onResponse(Call<DataJSON<Account>> call, Response<DataJSON<Account>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()){
                    if(dataJSON!=null){
                        if(dataJSON.isStatus()){
                            List<Account> commentList = dataJSON.getData();
                            accountRootComics.setValue(commentList);
                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Toast.makeText(application, MessageStatusHTTP.notImplemented,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Account>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return accountRootComics;
    }
    public MutableLiveData<List<Account>> getAccountChild(HashMap<String,String> hashMap) {
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Log.d("HashMap Entry", "Key: " + key + ", Value: " + value);
        }
        CommentService.commentService.getAccountChildComment(hashMap).enqueue(new Callback<DataJSON<Account>>() {
            @Override
            public void onResponse(Call<DataJSON<Account>> call, Response<DataJSON<Account>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()){
                    if(dataJSON!=null){
                        if(dataJSON.isStatus()){
                            List<Account> commentList = dataJSON.getData();
                            accountChild.setValue(commentList);
                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Toast.makeText(application, MessageStatusHTTP.notImplemented,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Account>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return accountChild;
    }
    public MutableLiveData<Boolean> writeComment(HashMap<String,String> hashMap){
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Log.d("HashMap Entry", "Key: " + key + ", Value: " + value);
        }
        CommentService.commentService.writeComment(hashMap).enqueue(new Callback<DataJSON<Comment>>() {
            @Override
            public void onResponse(Call<DataJSON<Comment>> call, Response<DataJSON<Comment>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()){
                    if(dataJSON!=null){
                        if(dataJSON.isStatus()){
                            writeStatus.setValue(true);
                        }
                        else{
                            writeStatus.setValue(false);
                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Toast.makeText(application, MessageStatusHTTP.notImplemented,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Comment>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return writeStatus;
    }
    public MutableLiveData<Boolean> getUpdateLODIncStatus(HashMap hashMap){
        CommentService.commentService.updateLODIncrease(hashMap).enqueue(new Callback<DataJSON<Boolean>>() {
            @Override
            public void onResponse(Call<DataJSON<Boolean>> call, Response<DataJSON<Boolean>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()){
                    if(dataJSON!=null){
                        if(dataJSON.isStatus()){
                            updateLODIncStatus.setValue(true);
                        }
                        else{
                            updateLODIncStatus.setValue(false);
                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Toast.makeText(application, MessageStatusHTTP.notImplemented,Toast.LENGTH_SHORT).show();
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_FOUND){
                    Toast.makeText(application, "Cannot like also dislike",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Boolean>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return updateLODIncStatus;
    }
    public MutableLiveData<Boolean> getUpdateLODDescStatus(HashMap hashMap){
        CommentService.commentService.updateLODDecrease(hashMap).enqueue(new Callback<DataJSON<Boolean>>() {
            @Override
            public void onResponse(Call<DataJSON<Boolean>> call, Response<DataJSON<Boolean>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()){
                    if(dataJSON!=null){
                        if(dataJSON.isStatus()){
                            updateLODDescStatus.setValue(true);
                        }
                        else{
                            updateLODDescStatus.setValue(false);
                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Toast.makeText(application, MessageStatusHTTP.notImplemented,Toast.LENGTH_SHORT).show();
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_FOUND){
                    Toast.makeText(application, "Cannot like also dislike",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<DataJSON<Boolean>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return updateLODDescStatus;
    }
    public MutableLiveData<List<Integer>> checkAccountLOD(HashMap hashMap){
        CommentService.commentService.checkAccountLOD(hashMap).enqueue(new Callback<DataJSON<Integer>>() {
            @Override
            public void onResponse(Call<DataJSON<Integer>> call, Response<DataJSON<Integer>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()){
                    if(dataJSON!=null){
                        if(dataJSON.isStatus()){
                            checkAccountLOD.setValue(dataJSON.getData());
                        }
                        else{

                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Toast.makeText(application, MessageStatusHTTP.notImplemented,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Integer>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return checkAccountLOD;
    }
    public MutableLiveData<List<Integer>> getCheckAccountLODComic(HashMap hashMap){
        CommentService.commentService.checkAccountLODComic(hashMap).enqueue(new Callback<DataJSON<Integer>>() {
            @Override
            public void onResponse(Call<DataJSON<Integer>> call, Response<DataJSON<Integer>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()){
                    if(dataJSON!=null){
                        if(dataJSON.isStatus()){
                            checkAccountLODComic.setValue(dataJSON.getData());
                        }
                        else{

                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Toast.makeText(application, MessageStatusHTTP.notImplemented,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Integer>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return checkAccountLODComic;
    }
    public MutableLiveData<List<Boolean>> checkLODTypeLOD(HashMap hashMap){
        CommentService.commentService.checkLODTypeLOD(hashMap).enqueue(new Callback<DataJSON<Boolean>>() {
            @Override
            public void onResponse(Call<DataJSON<Boolean>> call, Response<DataJSON<Boolean>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()){
                    if(dataJSON!=null){
                        if(dataJSON.isStatus()){
                            checkLODTypeLOD.setValue(dataJSON.getData());
                        }
                        else{

                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Toast.makeText(application, MessageStatusHTTP.notImplemented,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Boolean>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return checkLODTypeLOD;
    }
    public MutableLiveData<List<Boolean>> getCheckLODTypeLODComic(HashMap hashMap){
        CommentService.commentService.checkLODTypeLODComic(hashMap).enqueue(new Callback<DataJSON<Boolean>>() {
            @Override
            public void onResponse(Call<DataJSON<Boolean>> call, Response<DataJSON<Boolean>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()){
                    if(dataJSON!=null){
                        if(dataJSON.isStatus()){
                            checkLODTypeLODComic.setValue(dataJSON.getData());
                        }
                        else{

                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Toast.makeText(application, MessageStatusHTTP.notImplemented,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Boolean>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return checkLODTypeLODComic;
    }
    public MutableLiveData<List<Integer>> checkCommentIDLOD(HashMap hashMap){
        CommentService.commentService.checkCommentIDLOD(hashMap).enqueue(new Callback<DataJSON<Integer>>() {
            @Override
            public void onResponse(Call<DataJSON<Integer>> call, Response<DataJSON<Integer>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()){
                    if(dataJSON!=null){
                        if(dataJSON.isStatus()){
                            checkCommentIDLOD.setValue(dataJSON.getData());
                        }
                        else{

                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Toast.makeText(application, MessageStatusHTTP.notImplemented,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Integer>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return checkCommentIDLOD;
    }
    public MutableLiveData<List<Integer>> getCheckCommentIDLODComic(HashMap hashMap){
        CommentService.commentService.checkCommentIDLODComic(hashMap).enqueue(new Callback<DataJSON<Integer>>() {
            @Override
            public void onResponse(Call<DataJSON<Integer>> call, Response<DataJSON<Integer>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()){
                    if(dataJSON!=null){
                        if(dataJSON.isStatus()){
                            checkCommentIDLODComic.setValue(dataJSON.getData());
                        }
                        else{

                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Toast.makeText(application, MessageStatusHTTP.notImplemented,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Integer>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return checkCommentIDLODComic;
    }
    public MutableLiveData<List<Integer>> checkCommentWriteIDLOD(HashMap hashMap){
        CommentService.commentService.checkCommentWriteIDLOD(hashMap).enqueue(new Callback<DataJSON<Integer>>() {
            @Override
            public void onResponse(Call<DataJSON<Integer>> call, Response<DataJSON<Integer>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()){
                    if(dataJSON!=null){
                        if(dataJSON.isStatus()){
                            checkCommentWriteIDLOD.setValue(dataJSON.getData());
                        }
                        else{

                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Toast.makeText(application, MessageStatusHTTP.notImplemented,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Integer>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return checkCommentWriteIDLOD;
    }
    public MutableLiveData<List<Comment>> getRootCommentByReact(HashMap hashMap){
        CommentService.commentService.getCommentByReact(hashMap).enqueue(new Callback<DataJSON<Comment>>() {
            @Override
            public void onResponse(Call<DataJSON<Comment>> call, Response<DataJSON<Comment>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()){
                    if(dataJSON!=null){
                        if(dataJSON.isStatus()){
                            rootCommentByReact.setValue(dataJSON.getData());
                        }
                        else{

                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Toast.makeText(application, MessageStatusHTTP.notImplemented,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Comment>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return rootCommentByReact;
    }
    public MutableLiveData<List<Comment>> getRootCommentByTime(HashMap hashMap){
        CommentService.commentService.getCommentByTime(hashMap).enqueue(new Callback<DataJSON<Comment>>() {
            @Override
            public void onResponse(Call<DataJSON<Comment>> call, Response<DataJSON<Comment>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()){
                    if(dataJSON!=null){
                        if(dataJSON.isStatus()){
                            rootCommentByTime.setValue(dataJSON.getData());
                        }
                        else{

                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Toast.makeText(application, MessageStatusHTTP.notImplemented,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Comment>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return rootCommentByTime;
    }
    public MutableLiveData<List<Account>> getAccountRootCommentByReact(HashMap hashMap){
        CommentService.commentService.getAccountCommentByReact(hashMap).enqueue(new Callback<DataJSON<Account>>() {
            @Override
            public void onResponse(Call<DataJSON<Account>> call, Response<DataJSON<Account>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()){
                    if(dataJSON!=null){
                        if(dataJSON.isStatus()){
                            accountRootChaptersByReact.setValue(dataJSON.getData());
                        }
                        else{

                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Toast.makeText(application, MessageStatusHTTP.notImplemented,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Account>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return accountRootChaptersByReact;
    }
    public MutableLiveData<List<Account>> getAccountRootCommentByTime(HashMap hashMap){
        CommentService.commentService.getAccountCommentByTime(hashMap).enqueue(new Callback<DataJSON<Account>>() {
            @Override
            public void onResponse(Call<DataJSON<Account>> call, Response<DataJSON<Account>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()){
                    if(dataJSON!=null){
                        if(dataJSON.isStatus()){
                            accountRootChaptersByTime.setValue(dataJSON.getData());
                        }
                        else{

                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Toast.makeText(application, MessageStatusHTTP.notImplemented,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Account>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return accountRootChaptersByTime;
    }
    public MutableLiveData<List<Boolean>> deleteComment(HashMap hashMap){
        CommentService.commentService.removeComment(hashMap).enqueue(new Callback<DataJSON<Boolean>>() {
            @Override
            public void onResponse(Call<DataJSON<Boolean>> call, Response<DataJSON<Boolean>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()) {
                    if(dataJSON!=null){
                        if(dataJSON.isStatus() == true){
                            List<Boolean> delStatus = dataJSON.getData();
                            if(delStatus!=null){
                                delCommentStatus.setValue(delStatus);
                            }
                        }
                        else{
                            // do api trên server,nếu thành công remove thì sẽ trả ra status = true
                            // còn ko thì false,và phần obj List sẽ ko có gì
                            // thành công thì obj List sẽ cx trả ra thêm 1 List<boolean> là true nữa
                            // thực ra là ko cần thiết,hơi thừa nhưng thêm vào cho chắc,nên nhìn nó mới hài hài như này
                            List<Boolean> st = new ArrayList<>();
                            st.add(false);
                            delCommentStatus.setValue(st);
                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Log.e("Status favourites remove repository:", MessageStatusHTTP.notImplemented);
                    Toast.makeText(application, MessageStatusHTTP.notImplemented, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Boolean>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return delCommentStatus;
    }
    public MutableLiveData<Boolean> replyComment(HashMap<String,String> hashMap){
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Log.d("HashMap Entry", "Key: " + key + ", Value: " + value);
        }
        CommentService.commentService.replyComment(hashMap).enqueue(new Callback<DataJSON<Boolean>>() {
            @Override
            public void onResponse(Call<DataJSON<Boolean>> call, Response<DataJSON<Boolean>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()){
                    if(dataJSON!=null){
                        if(dataJSON.isStatus()){
                            replyStatus.setValue(true);
                        }
                        else{
                            replyStatus.setValue(false);
                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Toast.makeText(application, MessageStatusHTTP.notImplemented,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Boolean>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return replyStatus;
    }
    public MutableLiveData<List<Comment>> getCommentByCommentID(HashMap hashMap){
        CommentService.commentService.getCommentByCommentID(hashMap).enqueue(new Callback<DataJSON<Comment>>() {
            @Override
            public void onResponse(Call<DataJSON<Comment>> call, Response<DataJSON<Comment>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()){
                    if(dataJSON!=null){
                        if(dataJSON.isStatus()){
                            List<Comment> comment = dataJSON.getData();
                            CommentByCommentID.setValue(comment);
                        }
                        else{

                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Toast.makeText(application, MessageStatusHTTP.notImplemented,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Comment>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return CommentByCommentID;
    }
    public  MutableLiveData<List<Account>> getAccountByCommentID(HashMap hashMap){
        CommentService.commentService.getAccountByCommentID(hashMap).enqueue(new Callback<DataJSON<Account>>() {
            @Override
            public void onResponse(Call<DataJSON<Account>> call, Response<DataJSON<Account>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()){
                    if(dataJSON!=null){
                        if(dataJSON.isStatus()){
                            List<Account> account = dataJSON.getData();
                            AccountByCommentID.setValue(account);
                        }
                        else{

                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Toast.makeText(application, MessageStatusHTTP.notImplemented,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Account>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return AccountByCommentID;
    }
    public MutableLiveData<List<Chapter>> getChapterCommentComic(HashMap hashMap){
        CommentService.commentService.getChapterCommentComic(hashMap).enqueue(new Callback<DataJSON<Chapter>>() {
            @Override
            public void onResponse(Call<DataJSON<Chapter>> call, Response<DataJSON<Chapter>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()){
                    if(dataJSON!=null){
                        if(dataJSON.isStatus()){
                            List<Chapter> account = dataJSON.getData();
                            chapterCommentComic.setValue(account);
                        }
                        else{

                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Toast.makeText(application, MessageStatusHTTP.notImplemented,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Chapter>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return chapterCommentComic;
    }
    public MutableLiveData<List<Chapter>> getChapterCommentComicReact(HashMap hashMap){
        CommentService.commentService.getChapterCommentComicReact(hashMap).enqueue(new Callback<DataJSON<Chapter>>() {
            @Override
            public void onResponse(Call<DataJSON<Chapter>> call, Response<DataJSON<Chapter>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()){
                    if(dataJSON!=null){
                        if(dataJSON.isStatus()){
                            List<Chapter> account = dataJSON.getData();
                            chapterCommentComicReact.setValue(account);
                        }
                        else{

                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Toast.makeText(application, MessageStatusHTTP.notImplemented,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Chapter>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return chapterCommentComicReact;
    }
    public MutableLiveData<List<Comment>> getRootCommentComicByReact(HashMap hashMap){
        CommentService.commentService.getCommentByReactComic(hashMap).enqueue(new Callback<DataJSON<Comment>>() {
            @Override
            public void onResponse(Call<DataJSON<Comment>> call, Response<DataJSON<Comment>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()){
                    if(dataJSON!=null){
                        if(dataJSON.isStatus()){
                            rootCommentByReactComic.setValue(dataJSON.getData());
                        }
                        else{

                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Toast.makeText(application, MessageStatusHTTP.notImplemented,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Comment>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return rootCommentByReactComic;
    }
    public MutableLiveData<List<Comment>> getRootCommentComicByTime(HashMap hashMap){
        CommentService.commentService.getCommentByTimeComic(hashMap).enqueue(new Callback<DataJSON<Comment>>() {
            @Override
            public void onResponse(Call<DataJSON<Comment>> call, Response<DataJSON<Comment>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()){
                    if(dataJSON!=null){
                        if(dataJSON.isStatus()){
                            rootCommentByTimeComic.setValue(dataJSON.getData());
                        }
                        else{

                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Toast.makeText(application, MessageStatusHTTP.notImplemented,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Comment>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return rootCommentByTimeComic;
    }
    public MutableLiveData<List<Account>> getAccountRootCommentComicByReact(HashMap hashMap){
        CommentService.commentService.getAccountCommentByReactComic(hashMap).enqueue(new Callback<DataJSON<Account>>() {
            @Override
            public void onResponse(Call<DataJSON<Account>> call, Response<DataJSON<Account>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()){
                    if(dataJSON!=null){
                        if(dataJSON.isStatus()){
                            accountRootComicByReact.setValue(dataJSON.getData());
                        }
                        else{

                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Toast.makeText(application, MessageStatusHTTP.notImplemented,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Account>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return accountRootComicByReact;
    }
    public MutableLiveData<List<Account>> getAccountRootCommentComicByTime(HashMap hashMap){
        CommentService.commentService.getAccountCommentByTimeComic(hashMap).enqueue(new Callback<DataJSON<Account>>() {
            @Override
            public void onResponse(Call<DataJSON<Account>> call, Response<DataJSON<Account>> response) {
                DataJSON dataJSON = response.body();
                if(response.isSuccessful()){
                    if(dataJSON!=null){
                        if(dataJSON.isStatus()){
                            accountRootComicByTime.setValue(dataJSON.getData());
                        }
                        else{

                        }
                    }
                }
                else if(response.code() == HttpURLConnection.HTTP_NOT_IMPLEMENTED){
                    Toast.makeText(application, MessageStatusHTTP.notImplemented,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataJSON<Account>> call, Throwable t) {
                Log.e("ERROR",this.getClass().getName()+" onFailure: "+t.getMessage());
                Toast.makeText(application.getApplicationContext(), "No internet connection!", Toast.LENGTH_SHORT).show();
            }
        });
        return accountRootComicByTime;
    }
}
