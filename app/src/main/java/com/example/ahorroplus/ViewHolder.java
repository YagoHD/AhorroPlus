package com.example.ahorroplus;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder{
    private  ImageView logoSupermercado;
    private final TextView nombreProducto;
    private final TextView precio;
    private final CheckBox checkBoxComprado;


    public ViewHolder(@NonNull View itemView){
        super(itemView);
        nombreProducto = (TextView) itemView.findViewById(R.id.nombre_Producto);
        logoSupermercado = (ImageView) itemView.findViewById(R.id.logo_Supermercado);
        precio = (TextView) itemView.findViewById(R.id.precio);
        checkBoxComprado = (CheckBox) itemView.findViewById(R.id.chek_Comprado);


        checkBoxComprado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBoxComprado.isChecked()) {
                    nombreProducto.setTextColor(Color.parseColor("#85bb65"));
                    nombreProducto.setPaintFlags(nombreProducto.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }else{
                    nombreProducto.setTextColor(Color.parseColor("#1d3557"));
                    nombreProducto.setPaintFlags(nombreProducto.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                }
            }
        });
    }

    public void showData(ShoppingItem item, Activity activity, int supermercado) {
        nombreProducto.setText(item.getNombre());
        precio.setText(item.MasBarato(item.getPrecioEroski(), item.getPrecioMercadona(), item.getPrecioCarrefour()));


        int Supermercado = supermercado;

        //MOSTRAR LA IMAGEN CORRESPONDIENTE SIN METODO
        String precioCarr = item.getPrecioCarrefour();
        String precioMerca = item.getPrecioMercadona();
        String precioEros = item.getPrecioEroski();

        double precioCarrefour = Double.parseDouble(precioCarr);
        double precioMercadona = Double.parseDouble(precioMerca);
        double precioEroski = Double.parseDouble(precioEros);


        switch (Supermercado){
            case 1:
                logoSupermercado.setBackgroundResource(R.drawable.carrefour);
                precio.setText(item.getPrecioCarrefour());
                break;
            case 2:
                logoSupermercado.setBackgroundResource(R.drawable.eroski);
                precio.setText(item.getPrecioEroski());
                break;
            case 3:
                logoSupermercado.setBackgroundResource(R.drawable.mercadona);
                precio.setText(item.getPrecioMercadona());
                break;
            case 0:
                if (precioCarrefour <= precioEroski && precioCarrefour <= precioMercadona) {
                    logoSupermercado.setBackgroundResource(R.drawable.carrefour);
                } else if (precioEroski <= precioCarrefour && precioEroski <= precioMercadona) {
                    logoSupermercado.setBackgroundResource(R.drawable.eroski);
                } else if (precioMercadona <= precioEroski && precioMercadona <= precioCarrefour) {
                    logoSupermercado.setBackgroundResource(R.drawable.mercadona);
                }
                break;
        }
    }
}
