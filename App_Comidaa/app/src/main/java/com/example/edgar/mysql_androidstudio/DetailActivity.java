package com.example.edgar.mysql_androidstudio;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.edgar.mysql_androidstudio.utils.functions;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import static fragments.ComidasFragment.EXTRA_DESCRIPCION;
import static fragments.ComidasFragment.EXTRA_ID;
import static fragments.ComidasFragment.EXTRA_NOMBRE;
import static fragments.ComidasFragment.EXTRA_PRECIO;
import static fragments.ComidasFragment.EXTRA_URL;

public class DetailActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    EditText txtcantidadselct;
    Button cancel, acept;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent=getIntent();
        String imagenURL=intent.getStringExtra(EXTRA_URL);
        final String id =intent.getStringExtra(EXTRA_ID);
        String nombre=intent.getStringExtra(EXTRA_NOMBRE);
        String descripcion =intent.getStringExtra(EXTRA_DESCRIPCION);
        int precio =intent.getIntExtra(EXTRA_PRECIO,0);

        ImageView imagenView =findViewById(R.id.imagen_view_detail);
        TextView textViewNombre =findViewById(R.id.nombrecomida_detail);
        TextView textViewDescripcion =findViewById(R.id.descripcionComidaDetail);
        TextView textViewPrecio =findViewById(R.id.precioComidaDetail);
        TextView textViewid =findViewById(R.id.ordenComidaDetail);

        Picasso.with(this).load(imagenURL).fit().centerInside().into(imagenView);
        textViewNombre.setText(nombre);
        textViewDescripcion.setText(descripcion);
        textViewPrecio.setText("$ "+ precio);
        textViewid.setText(id);

        txtcantidadselct= findViewById(R.id.txtCantidad);
        cancel= findViewById(R.id.btnCancelar);
        acept= findViewById(R.id.btnAceptar);
        requestQueue = Volley.newRequestQueue(this.getApplicationContext());

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent welcome = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(welcome);
            }
        });

        acept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtcantidadselct.getText().toString().equals("")){
                    Toast.makeText(DetailActivity.this, "El campo cantidad no puede estar vacio", Toast.LENGTH_LONG).show();
                }else{
                    SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                    String url = "http://toxicosrest.000webhostapp.com/carrito.php?producto="+ id +"&cliente="+ prefs.getString("id_usuario", "0") +"&cantidad="+ txtcantidadselct.getText().toString() +"&fecha=" + functions.ObtenerFechaActual();
                    Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                                Toast.makeText(getApplicationContext(),"Producto añadido al carrito!", Toast.LENGTH_LONG).show();
                        }
                    };
                    Response.ErrorListener errorListener = new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(),"Algo inesperado sucedio, no se añadio al carrito " + error,Toast.LENGTH_LONG).show();
                        }
                    };
                    jsonObjectRequest =  new JsonObjectRequest(Request.Method.GET,url,null,listener,errorListener);
                    requestQueue.add(jsonObjectRequest);
                }

            }
        });




    }
    private void recibirDatos(){


    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {

    }
}
