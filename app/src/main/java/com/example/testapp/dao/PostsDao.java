package com.example.testapp.dao;

import com.example.testapp.model.Posts;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PostsDao {
    @GET("posts")
    Call<List<Posts>> getAllPosts();
}
