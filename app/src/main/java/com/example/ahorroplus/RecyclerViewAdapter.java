package com.example.ahorroplus;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder>  {

    private List<ShoppingItem> allTheData;
    private Activity activity;

    public RecyclerViewAdapter(List<ShoppingItem> dataSet, Activity activity) {
        this.allTheData = dataSet;
        this.activity = activity;
    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder, parent, false);
        return new ViewHolder(view);
    }
    
    public void onBindViewHolder( ViewHolder holder, int position){
        ShoppingItem dataInPositionToBeRendered = allTheData.get(position);
        holder.showData(dataInPositionToBeRendered, activity);
    }

    public int getItemCount(){
        return allTheData.size();
    }

}
