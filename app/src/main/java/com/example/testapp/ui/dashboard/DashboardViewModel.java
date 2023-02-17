package com.example.testapp.ui.dashboard;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testapp.dao.PostsDao;
import com.example.testapp.model.Posts;

import java.lang.annotation.Retention;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DashboardViewModel extends ViewModel {

    public static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private final MutableLiveData<List<Posts>> mPostsList;

    public DashboardViewModel() {
        mPostsList = new MutableLiveData<>();
    }

    public LiveData<List<Posts>> getPostsList(){
        return mPostsList;
    }

    public void loadDataFromServer(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostsDao dao = retrofit.create(PostsDao.class);

        dao.getAllPosts().enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(@NonNull Call<List<Posts>> call, Response<List<Posts>> response) {
                if (!response.isSuccessful()) return;

                List<Posts> posts = response.body();
                mPostsList.setValue(posts);
            }

            @Override
            public void onFailure(Call<List<Posts>> call, Throwable t) {
                System.err.println("Error");
                t.printStackTrace();
            }
        });
    }


}