package com.example.ahorroplus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MyListActivity extends AppCompatActivity {

    AutoCompleteTextView Buscador;
    ArrayList<String> productos =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);

        //BOTON QUE NOS MANDA  ALA PANTALLA ANTERIOR
        ImageButton imageButonAtras = findViewById(R.id.imageButonAtras);
        imageButonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivityForResult(intent, 0);
            }
        });
        //BOTON QUE BORRA EL HASHSHET



        try {
            JSONArray arraydedatos = new JSONArray(LoadJsonFromAsset());

            for(int i=0;i<arraydedatos.length();i++){
                JSONObject userData = arraydedatos.getJSONObject(i);
                productos.add(userData.getString("nombre"));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, productos);
        AutoCompleteTextView editText = findViewById(R.id.Buscador);
        editText.setAdapter(adapter);

        //RECYCLERVIEW




        CargarDatos(null);


        /////////////////////////////////////////////
        //SPINNER CON TIPO DE COMPRA
        Spinner spinner = (Spinner) findViewById(R.id.spinnerTipoCompra);
        String[] datos = new String[] {"UNICO", "VARIOS"};
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, datos);
        spinner.setAdapter(adapterSpinner);

        String seleccion = spinner.getSelectedItem().toString();
        if(seleccion.equals("VARIOS")){

        }else if(seleccion.equals("UNICO")){

        }



        /////////////////////////////////////////////
        editText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {

                //TOAST QUE INDICA AÑADIDO TRAS CLICAR EN UN PRODUCTO/
                String selected;
                selected = parent.getAdapter().getItem(i).toString();
                Toast.makeText(MyListActivity.this, "Añadido "+ selected, Toast.LENGTH_SHORT).show();
                //////////////////////////////////////////////////////

                editText.setText("");
                //CERRAMOS EL TACLADO AL CLICAR UN PRODUCTO Y LIMPIAMOS EL TEXTO ESCRITO///////////
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                    editText.setText("");
                //////////////////////////////////////////////////////


                //LEEMOS LAS SHARED PREFERENCES Y OBTENEMOS EL STRINGSET
                   String valorLeido;
                   SharedPreferences prefs = getSharedPreferences("miLista", Context.MODE_PRIVATE);
                   HashSet<String> hashSet = new HashSet<String>();
                   hashSet.addAll(prefs.getStringSet("miLista", new HashSet<>()));
                //////////////////////////////////////////////////////

                //AÑADIMOS EL NOMBRE AL HASHSET
                   CargarDatos(selected);
                //////////////////////////////////////////////////////


                //GUARDAR EL NUEVO HASHSET EN UN SHAREDPREFERENCES EDITOR
                  //  editor.putStringSet("miLista",hashSet);
                  //  editor.commit();
                  //  editor.apply();
                //////////////////////////////////////////////////////

                //CARGAR SUGERENCIA AL RECYCLERVIEW AL CLICAR EN ELLA

                //////////////////////////////////////////////////////

            }
        });
    }
//FUNCION CARGAR DATOS


    public void CargarDatos(String selected){
        RecyclerView recyclerView = findViewById(R.id.recyclerViewLista);
        Activity activity = this;
        String valorLeido;
        SharedPreferences prefs = getSharedPreferences("miLista", Context.MODE_PRIVATE);
        HashSet<String> hashSet = new HashSet<String>();
        hashSet.addAll(prefs.getStringSet("miLista", new HashSet<>()));
        //////////////////////////////////////////////////////

        //AÑADIMOS EL NOMBRE AL HASHSET
        if(selected!=null) {
            hashSet.add(selected);
        }

        Iterator it = hashSet.iterator();
        List<ShoppingItem> listaDeItems = new ArrayList<ShoppingItem>();
        try {
            JSONArray arraydedatos = new JSONArray(LoadJsonFromAsset());
            while(it.hasNext()){
                String variablGuardadaHashSet = it.next().toString();


                for( int x=0;x<arraydedatos.length();x++){
                    JSONObject userData = arraydedatos.getJSONObject(x);
                    String productos2;
                    productos2=(userData.getString("nombre"));

                    if (variablGuardadaHashSet.equals(productos2)){

                        ShoppingItem item = new ShoppingItem(productos2,userData.getString("precioEroski"),userData.getString("precioMercadona"),userData.getString("precioCarrefour"));
                        listaDeItems.add(item);
                        break;
                    }

                    RecyclerViewAdapter adaptador = new RecyclerViewAdapter(listaDeItems, activity);
                    recyclerView.setAdapter(adaptador);
                    recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                }
            }
        }catch (JSONException e){ e.printStackTrace();}

        SharedPreferences.Editor editor = prefs.edit();


        //GUARDAR EL NUEVO HASHSET EN UN SHAREDPREFERENCES EDITOR
        editor.putStringSet("miLista",hashSet);
        editor.commit();
        editor.apply();
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