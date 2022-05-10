package com.example.ahorroplus;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder{
    private  ImageView logoSupermercado;
    private final TextView nombreProducto;
    private final TextView precio;
    private final CheckBox checkBoxComprado;


    public void setLogoSupermercado(ImageView logoSupermercado) {
        this.logoSupermercado = logoSupermercado;
    }

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


        //MOSTRAR LA IMAGEN CORRESPONDIENTE SIN METODO
        String precioCarr = item.getPrecioCarrefour();
        String precioMerca = item.getPrecioMercadona();
        String precioEros = item.getPrecioEroski();

        int precioCarrefour = Integer.parseInt(precioCarr);
        int precioMercadona = Integer.parseInt(precioMerca);
        int precioEroski = Integer.parseInt(precioEros);

        ImageView iconoEroski;
        iconoEroski.setImageIcon(R.drawable.eroski);
        ImageView iconoMercadona;
        iconoMercadona.setImageIcon(R.drawable.mercadona);
        ImageView iconoCarrefour;
        iconoCarrefour.setImageIcon(R.drawable.carrefour);


        if (precioCarrefour <= precioEroski && precioCarrefour <= precioMercadona) {
            logoSupermercado = setLogoSupermercado(iconoCarrefour);
        } else if (precioEroski <= precioCarrefour && precioEroski <= precioMercadona) {
            logoSupermercado = setLogoSupermercado(iconoEroski);
        } else if (precioMercadona <= precioEroski && precioMercadona <= precioCarrefour) {
            logoSupermercado = setLogoSupermercado(iconoMercadona);
        }



    }


}
