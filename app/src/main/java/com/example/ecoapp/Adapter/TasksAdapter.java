package com.example.ecoapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecoapp.Model.Tasks;
import com.example.ecoapp.R;

import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TasksViewHolder> {

    private List<Tasks> tasksList;
    public TasksAdapter(List<Tasks> tasksList){
        this.tasksList = tasksList;
    }
    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tasks_layout , parent , false);
        return new TasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TasksViewHolder holder, int position) {
        holder.mName.setText(tasksList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    public class TasksViewHolder extends RecyclerView.ViewHolder{

        private TextView mName;
        public TasksViewHolder(@NonNull View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.task_tv);
        }
    }
}