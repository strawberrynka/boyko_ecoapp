package com.example.ecoapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecoapp.Model.Coming;
import com.example.ecoapp.R;

import java.util.List;

public class ComingAdapter extends RecyclerView.Adapter<ComingAdapter.ComingViewHolder> {

    private List<Coming> comingList;
    public ComingAdapter(List<Coming> comingList){
        this.comingList = comingList;
    }
    @NonNull
    @Override
    public ComingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coming_layout , parent , false);
        return new ComingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComingViewHolder holder, int position) {
        holder.mName.setText(comingList.get(position).getName());
        holder.mImageview.setImageResource(comingList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return comingList.size();
    }

    public class ComingViewHolder extends RecyclerView.ViewHolder{

        private ImageView mImageview;
        private TextView mName;
        public ComingViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageview = itemView.findViewById(R.id.coming_image);
            mName = itemView.findViewById(R.id.coming_name_tv);
        }
    }
}