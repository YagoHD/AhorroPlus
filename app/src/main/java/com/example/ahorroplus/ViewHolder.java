package com.example.ahorroplus;

import android.app.Activity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder{
    private final ImageView logoSupermercado;
    private final TextView nombreProducto;
    private final TextView precio;
    private final CheckBox checkBoxComprado;

    public ViewHolder(@NonNull View itemView){
        super(itemView);
        nombreProducto = (TextView) itemView.findViewById(R.id.nombre_Producto);
        logoSupermercado = (ImageView) itemView.findViewById(R.id.logo_Supermercado);
        precio = (TextView) itemView.findViewById(R.id.precio);
        checkBoxComprado = (CheckBox) itemView.findViewById(R.id.chek_Comprado);

    }

    public void showData(ShoppingItem item, Activity activity){
        nombreProducto.setText(item.getNombre());
        precio.setText(item.MasBarato(item.getPrecioEroski(), item.getPrecioMercadona(), item.getPrecioCarrefour()));


    }


}
