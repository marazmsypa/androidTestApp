package com.example.testapp.dao;

import com.example.testapp.model.Todo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TodoDao {
    @GET("todos")
    Call<List<Todo>> getAllTodos();
}
