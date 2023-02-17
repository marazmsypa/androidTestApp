package com.example.testapp.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testapp.activity.SecondActivity;
import com.example.testapp.adapter.PostsAdapter;
import com.example.testapp.adapter.TodoAdapter;
import com.example.testapp.databinding.ActivitySecondBinding;
import com.example.testapp.databinding.FragmentDashboardBinding;
import com.example.testapp.model.Posts;
import com.example.testapp.model.Todo;

public class DashboardFragment extends Fragment implements View.OnClickListener, PostsAdapter.OnPostSelected{

    private FragmentDashboardBinding binding;

    private DashboardViewModel dashboardViewModel;

    private PostsAdapter adapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);

        adapter = new PostsAdapter(requireContext());
        adapter.setOnPostSelected(this);

        binding.list.setAdapter(adapter);
        binding.list.setLayoutManager(new LinearLayoutManager(requireContext()));

        binding.btn.setOnClickListener(this::onClick);

        dashboardViewModel.getPostsList().observe(getViewLifecycleOwner(),
                posts -> {
                    adapter.setData(posts);
                    adapter.notifyDataSetChanged();

                    if (posts == null) {
                        binding.btn.setVisibility(View.VISIBLE);
                        binding.list.setVisibility(View.GONE);
                    } else {
                        binding.btn.setVisibility(View.GONE);
                        binding.list.setVisibility(View.VISIBLE);
                    }
                });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {
        dashboardViewModel.loadDataFromServer();

    }


    @Override
    public void onSelected(Posts post) {

        Intent intent = new Intent(requireContext(), SecondActivity.class);
        intent.putExtra(SecondActivity.DATA_KEY, post);
        startActivity(intent);
    }

}