package com.example.rc.fiesta;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class SQLVista extends Activity{

    TextView texto;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista);

        texto = (TextView) findViewById(R.id.tvTexto);

        Festejo info = new Festejo(this);
        try {
            info.abrir();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String datos = info.recibir();
        info.cerrar();
        texto.setText(datos);


    }
}
