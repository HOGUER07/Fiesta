package com.example.rc.fiesta;


import android.app.Dialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements OnClickListener {

    EditText nombre, cumpleaños, ebuscar;
    Button insertar, ver, buscar, editar, eliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nombre = (EditText) findViewById(R.id.edNombre);
        cumpleaños = (EditText) findViewById(R.id.edCumpleaños);
        insertar = (Button) findViewById(R.id.btInsertar);
        ebuscar = (EditText) findViewById(R.id.edBuscar);
        buscar = (Button) findViewById(R.id.btBuscar);
        editar = (Button) findViewById(R.id.btEditar);
        eliminar = (Button) findViewById(R.id.btEliminar);
        ver = (Button) findViewById(R.id.btVer);
        insertar.setOnClickListener(this);
        ver.setOnClickListener(this);
        buscar.setOnClickListener(this);
        editar.setOnClickListener(this);
        eliminar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btInsertar:
                EditText nomEditText = (EditText) findViewById(R.id.edNombre);
                String snomEditText = nomEditText.getText().toString();
                EditText telEditText = (EditText) findViewById(R.id.edCumpleaños);
                String stelEditText = telEditText.getText().toString();
                if (snomEditText.matches("") && stelEditText.matches("")) {
                    Toast.makeText(this, "Debes de llenar todos los campos!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    boolean funciona = true;
                    try {
                        String nom = nombre.getText().toString();
                        String tel = cumpleaños.getText().toString();
                        nombre.setText("");
                        cumpleaños.setText("");
                        Festejo entrada = new Festejo(MainActivity.this);
                        entrada.abrir();
                        entrada.crearEntrada(nom, tel);
                        entrada.cerrar();
                    } catch (Exception e) {
                        funciona = false;
                        String error = e.toString();
                        Dialog d = new Dialog(this);
                        d.setTitle("No hemos podido guardar el registro");
                        TextView tv = new TextView(this);
                        tv.setText(error);
                        d.setContentView(tv);
                        d.show();

                    } finally {
                        if (funciona) {
                            Dialog d = new Dialog(this);
                            d.setTitle("Verificando ...");
                            TextView tv = new TextView(this);
                            tv.setText("Registro Guardado Correctamente");
                            d.setContentView(tv);
                            d.show();
                        }
                    }
                    break;
                }
            case R.id.btVer:
                Intent it = new Intent(this, SQLVista.class);
                it.putExtra("nombre", nombre.getText().toString());
                startActivity(it);
                break;
            case R.id.btBuscar:
                EditText busEditText = (EditText) findViewById(R.id.edBuscar);
                String sbusEditText = busEditText.getText().toString();

                if (sbusEditText.matches("")) {
                    Toast.makeText(this, "Debes de llenar el campo de buscar!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    boolean funciona2 = true;
                    if (ebuscar.getText().toString().equals(""))
                    {
                        Dialog d = new Dialog(this);
                        d.setTitle("Error...");
                        TextView tv = new TextView(this);
                        tv.setText("Ingrese Numero de Busqueda!!!!");
                        d.setContentView(tv);
                        d.show();
                    }
                    else {
                        try {
                            String b = ebuscar.getText().toString();
                            Long lb = Long.parseLong(b);
                            Festejo tel = new Festejo(this);
                            try {
                                tel.abrir();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            String bN = tel.getN(lb);
                            String bT = tel.getT(lb);
                            tel.cerrar();
                            nombre.setText(bN);
                            cumpleaños.setText(bT);
                        } catch (Exception e) {
                            funciona2 = false;
                            String error = e.toString();
                            Dialog d = new Dialog(this);
                            d.setTitle("Error...");
                            TextView tv = new TextView(this);
                            tv.setText("No se pudo realizar la busqueda!!!!");
                            d.setContentView(tv);
                            d.show();
                        } finally {
                            if (funciona2) {
                            }
                        }
                    }
                    break;
                }
            case R.id.btEditar:
                EditText nombEditText = (EditText) findViewById(R.id.edNombre);
                String snombEditText = nombEditText.getText().toString();
                EditText teleEditText = (EditText) findViewById(R.id.edCumpleaños);
                String steleEditText = teleEditText.getText().toString();
                if (snombEditText.matches("") && steleEditText.matches("")) {
                    Toast.makeText(this, "Debes de rellenar los campos Nombre y Telefono!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    boolean funciona3 = true;
                    try {
                        String eNom = nombre.getText().toString();
                        String eTel = cumpleaños.getText().toString();
                        String eFila = ebuscar.getText().toString();
                        nombre.setText("");
                        cumpleaños.setText("");
                        ebuscar.setText("");
                        long eFila1 = Long.parseLong(eFila);
                        Festejo editar = new Festejo(this);
                        editar.abrir();
                        editar.editar(eFila1, eNom, eTel);
                        editar.cerrar();
                    } catch (Exception e) {
                        funciona3 = false;
                        String error = e.toString();
                        Dialog d = new Dialog(this);
                        d.setTitle("No podemos editar sus datos!");
                        TextView tv = new TextView(this);
                        tv.setText(error);
                        d.setContentView(tv);
                        d.show();
                    } finally {
                        if (funciona3) {
                            Dialog d = new Dialog(this);
                            d.setTitle("Verificando ...");
                            TextView tv = new TextView(this);
                            tv.setText("Registro Actualizado Correctamente");
                            d.setContentView(tv);
                            d.show();
                        }
                    }
                    break;
                }
            case R.id.btEliminar:
                EditText noEditText = (EditText) findViewById(R.id.edNombre);
                String snoEditText = noEditText.getText().toString();
                EditText teEditText = (EditText) findViewById(R.id.edCumpleaños);
                String steEditText = teEditText.getText().toString();
                if (snoEditText.matches("") && steEditText.matches("")) {
                    Toast.makeText(this, "Debe de haber un registro seleccionado!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    boolean funcion4 = true;
                    try {
                        String elFila = ebuscar.getText().toString();
                        nombre.setText("");
                        cumpleaños.setText("");
                        ebuscar.setText("");
                        long elFilal = Long.parseLong(elFila);
                        Festejo borrar = new Festejo(this);
                        borrar.abrir();
                        borrar.borrar(elFilal);
                        borrar.cerrar();
                    } catch (Exception e) {
                        funcion4 = false;
                        String error = e.toString();
                        Dialog d = new Dialog(this);
                        d.setTitle("No podemos eliminar sus datos!");
                        TextView tv = new TextView(this);
                        tv.setText(error);
                        d.setContentView(tv);
                        d.show();
                    } finally {
                        if (funcion4) {
                            Dialog d = new Dialog(this);
                            d.setTitle("Verificando ...");
                            TextView tv = new TextView(this);
                            tv.setText("Registro Elimnado Correctamente");
                            d.setContentView(tv);
                            d.show();
                        }
                    }
                    break;
                }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}