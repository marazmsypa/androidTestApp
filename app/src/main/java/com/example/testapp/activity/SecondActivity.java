package com.example.testapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testapp.R;
import com.example.testapp.dao.UsersDao;
import com.example.testapp.databinding.ActivitySecondBinding;
import com.example.testapp.model.Posts;
import com.example.testapp.model.Users;
import com.example.testapp.ui.dashboard.DashboardViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SecondActivity extends AppCompatActivity {
    public static final String DATA_KEY = "important_data";

    private ActivitySecondBinding binding;

    private Posts post;

    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent != null) {
            post = (Posts) intent.getSerializableExtra(DATA_KEY);
            binding.title.setText(post.getTitle());
            binding.content.setText(post.getBody());

            displayPostAuthor();
        }

        setupToolbar();
    }

    public void setupToolbar() {
        setTitle(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_post_details, menu);
        return true;
    }

    public void displayPostAuthor() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DashboardViewModel.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UsersDao dao = retrofit.create(UsersDao.class);

        dao.getUserById(post.getUserId()).enqueue(new Callback<Users>() {
            @Override
            public void onResponse(@NonNull Call<Users> call, Response<Users> response) {
                if (!response.isSuccessful()) return;

                Users user = response.body();
                binding.author.setText(user.getName());
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                System.err.println("Error");
                t.printStackTrace();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.favorite:
                isFavorite = !isFavorite;

                if (isFavorite) item.setIcon(R.drawable.baseline_favorite_24);
                else item.setIcon(R.drawable.baseline_favorite_border_24);
        }

        return super.onOptionsItemSelected(item);
    }


}