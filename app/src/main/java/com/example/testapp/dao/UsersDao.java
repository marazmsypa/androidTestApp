package com.example.testapp.dao;


import com.example.testapp.model.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UsersDao {

    @GET("users")
    Call<List<Users>> getAllUsers();

    @GET("users/{id}")
    Call<Users> getUserById(@Path("id") int id);
}
