package com.example.ahorroplus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MyListActivity extends AppCompatActivity {

    AutoCompleteTextView Buscador;
    ArrayList<String> productos =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);

        ImageButton imageButonAtras = findViewById(R.id.imageButonAtras);
        imageButonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        //Buscador = findViewById(R.id.Buscador);
        //
        try {
            JSONArray arraydedatos = new JSONArray(LoadJsonFromAsset());

            for(int i=0;i<arraydedatos.length();i++){
                JSONObject userData = arraydedatos.getJSONObject(i);
                productos.add(userData.getString("nombre"));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        List<String> responseList = new ArrayList<String>();



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, productos);
        AutoCompleteTextView editText = findViewById(R.id.Buscador);
        editText.setAdapter(adapter);



    }
    //CARGAR JSON LOCAL DESDE ASSET
    public String LoadJsonFromAsset(){
        String json=null;
        try{
            InputStream in=this.getAssets().open("productos.json");
            int size=in.available();
            byte[] bbuffer=new byte[size];
            in.read(bbuffer);
            in.close();
            json=new String(bbuffer,"UTF-8");
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
        return json;
    }
}