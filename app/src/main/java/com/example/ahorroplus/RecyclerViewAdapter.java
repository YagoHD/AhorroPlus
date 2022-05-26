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
    public int supermercado;

    public RecyclerViewAdapter(List<ShoppingItem> dataSet, Activity activity, int supermercado) {
        this.allTheData = dataSet;
        this.activity = activity;
        this.supermercado = supermercado;
    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder( ViewHolder holder, int position){
        this.supermercado = supermercado;
        ShoppingItem dataInPositionToBeRendered = allTheData.get(position);
        holder.showData(dataInPositionToBeRendered, activity, supermercado);
    }

    public int getItemCount(){
        return allTheData.size();
    }

}
