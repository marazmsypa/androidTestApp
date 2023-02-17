package com.example.testapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp.databinding.ItemTodoBinding;
import com.example.testapp.model.Todo;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
    private List<Todo> data;

    private final LayoutInflater inflater;

    private OnTodoSelected onTodoSelected;

    public TodoAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public TodoAdapter(List<Todo> data, Context context) {
        this(context);
        this.data = data;
    }

    public void setOnTodoSelected(OnTodoSelected onTodoSelected) {
        this.onTodoSelected = onTodoSelected;
    }

    public void setData(List<Todo> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTodoBinding binding = ItemTodoBinding.inflate(inflater, parent, false);
        return new TodoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        Todo todo = data.get(position);

        ItemTodoBinding binding = holder.getBinding();

        binding.getRoot().setOnClickListener(v -> {
            if (onTodoSelected != null) {
                onTodoSelected.onSelected(todo);
            }
        });

        binding.title.setText(todo.getTitle());

        String completedText = todo.isCompleted() ? "Да" : "Нет";
        binding.completed.setText(completedText);
    }

    @Override
    public int getItemCount() {
        if (data == null) return 0;
        return data.size();
    }

    public static class TodoViewHolder extends RecyclerView.ViewHolder {
        private final ItemTodoBinding binding;

        public TodoViewHolder(@NonNull ItemTodoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ItemTodoBinding getBinding() {
            return binding;
        }
    }

    public interface OnTodoSelected {
        void onSelected(Todo todo);
    }
}
