package com.example.ahorroplus;

public class ShoppingItem {

    private String nombre;
    private String precioEroski;
    private String precioMercadona;
    private String precioCarrefour;


        public  ShoppingItem(String nombre, String precioEroski, String precioMercadona, String precioCarrefour) {
            this.nombre = nombre;
            this.precioEroski = precioEroski;
            this.precioCarrefour = precioCarrefour;
            this.precioMercadona = precioMercadona;
        }

        public String getNombre(){
            return nombre;
        }
        public  String getPrecioEroski(){
            return precioEroski;
        }
        public String getPrecioMercadona(){
            return precioMercadona;
        }
        public  String getPrecioCarrefour(){
            return precioCarrefour;
        }

        public String MasBarato(String precioEroski, String precioMercadona, String precioCarrefour){
            this.precioMercadona=precioMercadona;
            this.precioCarrefour=precioCarrefour;
            this.precioEroski=precioEroski;

            double precioEros = Double.parseDouble(precioEroski);
            double precioMerc = Double.parseDouble(precioMercadona);
            double precioCarr = Double.parseDouble(precioCarrefour);

            if(precioCarr<=precioEros && precioCarr<=precioMerc){
                return precioCarrefour;
            }else if (precioEros<=precioCarr && precioEros<=precioMerc){
                return precioEroski;
            }else if (precioMerc<=precioEros && precioMerc<=precioCarr){
                return precioMercadona;
            }else return "ERROR";

        }

}

