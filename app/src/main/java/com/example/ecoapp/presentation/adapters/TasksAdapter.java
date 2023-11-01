package com.example.ecoapp.presentation.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecoapp.data.models.Task;
import com.example.ecoapp.R;
import com.example.ecoapp.databinding.TasksLayoutBinding;

import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TasksViewHolder> {
    private List<Task> taskList;
    private int theme;
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void OnItemClick(Task task);
    }

    public TasksAdapter(List<Task> taskList, int theme){
        this.taskList = taskList;
        this.theme = theme;
    }

    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TasksLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.tasks_layout , parent , false);
        binding.setThemeInfo(theme);
        return new TasksViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TasksViewHolder holder, int position) {
        holder.binding.taskTv.setText(taskList.get(position).getName());
        holder.binding.taskTv.setOnClickListener(View -> {
            if (listener != null && position != RecyclerView.NO_POSITION) listener.OnItemClick(taskList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class TasksViewHolder extends RecyclerView.ViewHolder{
        private TasksLayoutBinding binding;

        public TasksViewHolder(@NonNull TasksLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}