package com.example.testapp.ui.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testapp.dao.TodoDao;
import com.example.testapp.model.Todo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeViewModel extends ViewModel {
    public static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    private final MutableLiveData<List<Todo>> mTodoList;

    public HomeViewModel() {
        mTodoList = new MutableLiveData<>();
    }

    public LiveData<List<Todo>> getTodoList() {
        return mTodoList;
    }

    public void loadDataFromServer() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TodoDao dao = retrofit.create(TodoDao.class);

        dao.getAllTodos().enqueue(new Callback<List<Todo>>() {
            @Override
            public void onResponse(@NonNull Call<List<Todo>> call,
                                   @NonNull Response<List<Todo>> response) {
                if (!response.isSuccessful()) return;

                List<Todo> todos = response.body();
                mTodoList.setValue(todos);
            }

            @Override
            public void onFailure(@NonNull Call<List<Todo>> call,
                                  @NonNull Throwable t) {
                System.err.println("Error while data fetch");
                t.printStackTrace();
            }
        });
    }
}