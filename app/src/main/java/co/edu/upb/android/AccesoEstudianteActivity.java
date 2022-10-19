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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import co.edu.upb.android.api.Api;

public class AccesoEstudianteActivity extends AppCompatActivity {
    Button btnAccederEst, btnCrearCuenta;
    ProgressBar pbEsperar;
    EditText etID_UPB, etContrasenaEst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceso_estudiante);
        inicializarCampos();
    }

    private void inicializarCampos() {
        btnAccederEst = findViewById(R.id.btnAccederEst);
        btnCrearCuenta = findViewById(R.id.btnCrearCuenta);
        etID_UPB = findViewById(R.id.etID_UPB);
        etContrasenaEst = findViewById(R.id.etContrasenaEst);
        pbEsperar = findViewById(R.id.pbEsperar);
        pbEsperar.setVisibility(View.GONE);
    }

    private void limpiarCampos() {
        etID_UPB.getText().clear();
        etContrasenaEst.getText().clear();
    }

    public void loginEstudiante(View view){
        btnAccederEst.setVisibility(View.GONE);
        pbEsperar.setVisibility(View.VISIBLE);

        //Solicitamos datos a una API REST-Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Api.URL_BASE+"/estudiantes/login";

        // Request a string response from the provided URL.
        try {
            JSONObject datosJson = new JSONObject();
            datosJson.put("id_UPB", etID_UPB.getText().toString());
            datosJson.put("contrasena", etContrasenaEst.getText().toString());

            JsonObjectRequest strRequest = new JsonObjectRequest(Request.Method.POST, url, datosJson,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String meta = response.getString("meta");
                                JSONObject data = (JSONObject) response.get("data");

                                String token = data.getString("token");
                                JSONObject estudiante = (JSONObject) data.get("estudiante");

                                long id_UPB = estudiante.getLong("id_UPB");
                                String nombre = estudiante.getString("nombre");
                                String apellido = estudiante.getString("apellido");
                                String correo = estudiante.getString("correo");
                                String telefono = estudiante.getString("telefono");
                                String pais = estudiante.getString("pais");
                                String ciudad = estudiante.getString("ciudad");

                                Toast.makeText(getApplicationContext(), "Bienvenido " + nombre + " " + apellido, Toast.LENGTH_SHORT).show();
                                SharedPreferences pre = getSharedPreferences("estudiante", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = pre.edit();
                                editor.putString("datos",response.toString());
                                editor.putLong("id_UPB",id_UPB);
                                editor.putString("nombre",nombre);
                                editor.putString("apellido",apellido);
                                editor.putString("correo",correo);
                                editor.putString("telefono",telefono);
                                editor.putString("pais",pais);
                                editor.putString("ciudad",ciudad);
                                editor.putString("token",token);
                                editor.putBoolean("logueado",true);

                                editor.commit();
                                Intent i = new Intent(getApplicationContext(),PerfilEstudianteActivity.class);
                                startActivity(i);
                                btnAccederEst.setVisibility(View.VISIBLE);
                                pbEsperar.setVisibility(View.GONE);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                limpiarCampos();
                                btnAccederEst.setVisibility(View.VISIBLE);
                                pbEsperar.setVisibility(View.GONE);
                                if(e.getMessage().contains("No value for data")){
                                    try {
                                        Toast.makeText(getApplicationContext(), response.getString("errors"), Toast.LENGTH_SHORT).show();
                                    } catch (JSONException jsonException) {
                                        jsonException.printStackTrace();
                                    }
                                }
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
            queue.add(strRequest);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void registrarNuevoEst(View view) {
        Intent intent = new Intent(getApplicationContext(),RegistrarEstudianteActivity.class);
        startActivity(intent);
    }
}