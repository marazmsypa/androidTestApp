package com.example.testapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp.databinding.ItemPostsBinding;
import com.example.testapp.model.Posts;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsViewHolder> {

    private List<Posts> data;

    private final LayoutInflater inflater;

    private OnPostSelected onPostSelected;

    public PostsAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public PostsAdapter(List<Posts> data, Context context) {
        this(context);
        this.data = data;
    }

    public void setOnPostSelected(OnPostSelected onPostSelected) {
        this.onPostSelected = onPostSelected;

    }

    public void setData(List<Posts> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPostsBinding binding = ItemPostsBinding.inflate(inflater, parent, false);
        return new PostsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsViewHolder holder, int position) {
        Posts post = data.get(position);

        ItemPostsBinding binding = holder.getBinding();

        binding.getRoot().setOnClickListener(v -> {
            if (onPostSelected != null){
                onPostSelected.onSelected(post);
            }
        });

        binding.title.setText(post.getTitle());
        binding.body.setText(post.getBody());
    }

    @Override
    public int getItemCount() {
        if (data == null) return 0;
        return data.size();
    }

    public static class PostsViewHolder extends RecyclerView.ViewHolder {

        private final ItemPostsBinding binding;

        public PostsViewHolder(@NonNull ItemPostsBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        public ItemPostsBinding getBinding() { return binding;}

    }

    public interface OnPostSelected {
        void onSelected(Posts post);
    }
}
