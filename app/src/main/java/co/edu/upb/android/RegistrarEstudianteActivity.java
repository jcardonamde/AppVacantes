package co.edu.upb.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import co.edu.upb.android.api.Api;

public class RegistrarEstudianteActivity extends AppCompatActivity {
    EditText etIdUpb, etNombre, etApellido, etCorreo, etTelefono, etPais, etCiudad, etContrasena, etConfirmaContrasena;
    Button btnRegistrarEst;
    ProgressBar pbEsperar;

    private void inicializarCampos() {
        etIdUpb = findViewById(R.id.etIdUpb);
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etCorreo = findViewById(R.id.etCorreo);
        etTelefono = findViewById(R.id.etTelefono);
        etContrasena = findViewById(R.id.etContrasena);
        btnRegistrarEst = findViewById(R.id.btnRegistrarEst);
        pbEsperar = findViewById(R.id.pbEsperar);
        pbEsperar.setVisibility(View.GONE);
    }

    private void limpiarCampos() {
        etIdUpb.getText().clear();
        etNombre.getText().clear();
        etApellido.getText().clear();
        etCorreo.getText().clear();
        etTelefono.getText().clear();
        etPais.getText().clear();
        etCiudad.getText().clear();
        etContrasena.getText().clear();
        etConfirmaContrasena.getText().clear();
        btnRegistrarEst.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_estudiante);
        inicializarCampos();
        btnRegistrarEst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarEstudiante();
            }
        });
    }

    public void registrarEstudiante() {
        btnRegistrarEst.setVisibility(View.GONE);
        pbEsperar.setVisibility(View.VISIBLE);

        // Solicitamos datos a una API Rest
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Api.URL_BASE + "/estudiantes";

        // Request a string response from the provided URL.
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        btnRegistrarEst.setVisibility((View.VISIBLE));
                        pbEsperar.setVisibility(View.GONE);

                        try {
                            JSONObject respObj = new JSONObject(response);
                            Toast.makeText(getApplicationContext(),respObj.getString("Mensaje"),Toast.LENGTH_LONG).show();
                            limpiarCampos();
                            btnRegistrarEst.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getApplicationContext(),"Se ha registrado exitosamente", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getApplicationContext(),AccesoEstudianteActivity.class);
                        startActivity(i);
                        finish();
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                btnRegistrarEst.setVisibility(View.VISIBLE);
                pbEsperar.setVisibility(View.GONE);
                if (error.getMessage().contains("Connection reset")) {
                    Toast.makeText(getApplicationContext(), "Upps, verifique su conexión a Wifi o Datos", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Upps, Intente más tarde! Falla al crear la empresa" + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key and value pair to our parameters.
                params.put("id_UPB",etIdUpb.getText().toString());
                params.put("nombre",etNombre.getText().toString());
                params.put("apellido",etApellido.getText().toString());
                params.put("correo", etCorreo.getText().toString());
                params.put("telefono", etTelefono.getText().toString());
                params.put("contrasena", etContrasena.getText().toString());
                // at last we are returning our params.
                return params;
            }
        };
        // below line is to make a json object request and Add the request to the RequestQueue.
        queue.add(request);
    }
}