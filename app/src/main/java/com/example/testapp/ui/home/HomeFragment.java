package com.example.testapp.ui.home;

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
import com.example.testapp.adapter.TodoAdapter;
import com.example.testapp.databinding.FragmentHomeBinding;
import com.example.testapp.model.Todo;

public class HomeFragment extends Fragment implements View.OnClickListener, TodoAdapter.OnTodoSelected {

    private FragmentHomeBinding binding;

    private HomeViewModel homeViewModel;

    private TodoAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        adapter = new TodoAdapter(requireContext());
        adapter.setOnTodoSelected(this);

        binding.list.setAdapter(adapter);
        binding.list.setLayoutManager(new LinearLayoutManager(requireContext()));

        binding.btn.setOnClickListener(this);

        homeViewModel.getTodoList().observe(getViewLifecycleOwner(),
                todos -> {
                    adapter.setData(todos);
                    adapter.notifyDataSetChanged();

                    if (todos == null) {
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
         homeViewModel.loadDataFromServer();
    }

    @Override
    public void onSelected(Todo todo) {
        Toast.makeText(requireContext(), todo.getTitle(), Toast.LENGTH_SHORT).show();
    }
}