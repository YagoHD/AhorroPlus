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
import android.widget.EditText;
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
    public int Supermercado = 0; //0 calcula el 1 carrefour, 2 mercadona, 3 eroski
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
        ImageButton botonBorrar = findViewById(R.id.BorrarLista);
        botonBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getSharedPreferences("miLista", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                HashSet<String> hashSet = new HashSet<>();
                editor.putStringSet("miLista",hashSet);
                  editor.commit();
                  editor.apply();
                finish(); startActivity(getIntent());
            }
        });


        ////////////////////////////
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


        //SPINNER CON TIPO DE COMPRA
        Spinner spinner = (Spinner) findViewById(R.id.spinnerTipoCompra);
        String[] datos = new String[] {"UNICO", "VARIOS"};
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos);
        spinner.setAdapter(adapterSpinner);
        spinner.setSelection(1);
        String seleccion = spinner.getSelectedItem().toString();
        //AÑADIMOS LOS PRODUCTOS AL INICIAR LA ACTIVIDAD Y USAMOS UN METODO O OTRO DEPENDIENDO DEL SPINNER
        if(seleccion.equals("VARIOS")){
            CargaUnico(null);
            //CargarDatos(null);
        }else if(seleccion.equals("UNICO")){
            CargaUnico(null);
        }
        /////////////////////////////////////////////

        //ON ITEM CLICK DENTRO DEL AUTOCOMPLETE
        editText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {

                //TOAST QUE INDICA AÑADIDO TRAS CLICAR EN UN PRODUCTO/
                String selected;
                selected = parent.getAdapter().getItem(i).toString();
                Toast.makeText(MyListActivity.this, "Añadido "+ selected, Toast.LENGTH_SHORT).show();
                //////////////////////////////////////////////////////

                editText.setText("");
                //CERRAMOS EL TECLADO AL CLICAR UN PRODUCTO Y LIMPIAMOS EL TEXTO ESCRITO///////////
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

                //AÑADIMOS EL PRODUCTO EN EL QUE CLICAMOS
                if(seleccion.equals("VARIOS")){
                    CargaUnico(selected);
                    //CargarDatos(selected);
                }else if(seleccion.equals("UNICO")){
                    CargaUnico(selected);
                }
                //////////////////////////////////////////////////////
            }
        });
        /////////////////////////////////////////////
    }

    //FUNCION CARGAR UN UNICO SUPERMERCADO
    public void CargaUnico(String selected){
        double precioTotalCarrefour = 0;
        double precioTotalMercadona = 0;
        double precioTotalEroski = 0;
        RecyclerView recyclerView = findViewById(R.id.recyclerViewLista);
        Activity activity = this;
        SharedPreferences prefs = getSharedPreferences("miLista", Context.MODE_PRIVATE);
        HashSet<String> hashSet = new HashSet<String>();
        hashSet.addAll(prefs.getStringSet("miLista", new HashSet<>()));

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
                        double precioCarrefour = Double.parseDouble(userData.getString("precioCarrefour"));
                        precioTotalCarrefour = precioTotalCarrefour + precioCarrefour;
                        precioTotalEroski = precioTotalEroski + Double.parseDouble(userData.getString("precioEroski"));
                        precioTotalMercadona = precioTotalMercadona + Double.parseDouble(userData.getString("precioMercadona"));


                        if(precioTotalCarrefour<=precioTotalEroski && precioTotalCarrefour<=precioTotalMercadona){
                            ShoppingItem item = new ShoppingItem(productos2,userData.getString("precioCarrefour"),userData.getString("precioCarrefour"),userData.getString("precioCarrefour"));
                            listaDeItems.add(item);
                            String precioTotalCarrefourstring = String.format("%3.2f" ,precioTotalCarrefour);
                            EditText precioFinal = findViewById(R.id.precio);
                            precioFinal.setText(precioTotalCarrefourstring);

                            Supermercado = 1;
                        }else if (precioTotalEroski<=precioTotalCarrefour && precioTotalEroski<=precioTotalMercadona){
                            ShoppingItem item = new ShoppingItem(productos2,userData.getString("precioEroski"),userData.getString("precioEroski"),userData.getString("precioEroski"));
                            listaDeItems.add(item);
                            String precioTotalEroskistring = String.format("%3.2f" ,precioTotalEroski);
                            EditText precioFinal = findViewById(R.id.precio);
                            precioFinal.setText(precioTotalEroskistring);
                            Supermercado = 2;
                        }else if (precioTotalMercadona<=precioTotalEroski && precioTotalMercadona<=precioTotalCarrefour){
                            ShoppingItem item = new ShoppingItem(productos2,userData.getString("precioMercadona"),userData.getString("precioMercadona"),userData.getString("precioMercadona"));
                            listaDeItems.add(item);
                            String precioTotalMercadonastring = String.format("%3.2f" ,precioTotalMercadona);
                            EditText precioFinal = findViewById(R.id.precio);
                            precioFinal.setText(precioTotalMercadonastring);
                            Supermercado = 3;
                        }

                        RecyclerViewAdapter adaptador = new RecyclerViewAdapter(listaDeItems, activity);
                        recyclerView.setAdapter(adaptador);
                        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                        break;
                    }

                }
            }
        }catch (JSONException e){ e.printStackTrace();}
        SharedPreferences.Editor editor = prefs.edit();

        //GUARDAR EL NUEVO HASHSET EN UN SHAREDPREFERENCES EDITOR
        editor.putStringSet("miLista",hashSet);
        editor.commit();
        editor.apply();


    }


    /////////////////////////////////////////////

    //FUNCION CARGAR DATOS PARA VARIOS LOCALES
    public void CargarDatos(String selected){
        double precioTotal=0;

        RecyclerView recyclerView = findViewById(R.id.recyclerViewLista);
        Activity activity = this;
        SharedPreferences prefs = getSharedPreferences("miLista", Context.MODE_PRIVATE);
        HashSet<String> hashSet = new HashSet<String>();
        hashSet.addAll(prefs.getStringSet("miLista", new HashSet<>()));

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
                        double precioCarrefour = Double.parseDouble(userData.getString("precioCarrefour"));
                        double precioEroski = Double.parseDouble(userData.getString("precioEroski"));
                        double precioMercadona = Double.parseDouble(userData.getString("precioMercadona"));

                        if (precioCarrefour <= precioEroski && precioCarrefour <= precioMercadona){
                            precioTotal = precioTotal + precioCarrefour;
                        }else if (precioEroski <= precioCarrefour && precioEroski <= precioMercadona){
                            precioTotal = precioTotal + precioEroski;
                        }else if(precioMercadona <= precioCarrefour && precioMercadona <= precioEroski){
                            precioTotal = precioTotal + precioMercadona;
                        }
                        RecyclerViewAdapter adaptador = new RecyclerViewAdapter(listaDeItems, activity);
                        recyclerView.setAdapter(adaptador);
                        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                        break;
                    }


                }
            }
        }catch (JSONException e){ e.printStackTrace();}
        SharedPreferences.Editor editor = prefs.edit();

        //GUARDAR EL NUEVO HASHSET EN UN SHAREDPREFERENCES EDITOR
        editor.putStringSet("miLista",hashSet);
        editor.commit();
        editor.apply();

        String precioFinalString = Double.toString(precioTotal);
        EditText precioFinal = findViewById(R.id.precio);
        precioFinalString = String.format("%3.2f" , precioTotal) ;
        precioFinal.setText(precioFinalString);

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