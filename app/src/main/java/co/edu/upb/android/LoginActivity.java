package co.edu.upb.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import co.edu.upb.android.api.Api;

public class LoginActivity extends AppCompatActivity {
    Button btnAcceder;
    ProgressBar pbEsperar;
    EditText etNit, etContrasena;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inicializarCampos();
    }

    private void inicializarCampos() {
        btnAcceder = findViewById(R.id.btnRegistrarEst);
        etContrasena = findViewById(R.id.etContrasena);
        etNit = findViewById(R.id.etIdUpb);
        pbEsperar = findViewById(R.id.pbEsperar);
        pbEsperar.setVisibility(View.GONE);
    }

    public void loginEmpresa(View view){
        btnAcceder.setVisibility(View.GONE);
        pbEsperar.setVisibility(View.VISIBLE);

        //Solicitamos datos a una API REST-Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Api.URL_BASE+"/empresas/loginEmpresa";

        //String raz = txtRazonSocial.getText().toString();
        // Request a string response from the provided URL.
        try {
            JSONObject datosJson = new JSONObject();
            datosJson.put("nit", etNit.getText().toString());
            datosJson.put("contrasena", etContrasena.getText().toString());

            JsonObjectRequest strRequest = new JsonObjectRequest(Request.Method.POST, url, datosJson,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String meta = response.getString("meta");
                                JSONObject data = (JSONObject) response.get("data");

                                String token = data.getString("token");
                                JSONObject empresa = (JSONObject) data.get("empresa");

                                String razon_social = empresa.getString("razon_social");
                                long nit = empresa.getLong("nit");

                                Toast.makeText(getApplicationContext(), razon_social, Toast.LENGTH_SHORT).show();
                                SharedPreferences pre = getSharedPreferences("empresa", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = pre.edit();
                                editor.putString("datos",response.toString());
                                editor.putString("razon_social",razon_social);
                                editor.putLong("nit",nit);
                                editor.putString("token",token);
                                editor.putBoolean("logueado",true);

                                //editor.apply();
                                editor.commit();
                                Intent i = new Intent(getApplicationContext(),InicioActivity.class);
                                startActivity(i);
                                finish();

                                /*
                               for (int i=0;i<objetoHijo.length();i++){
                                    JSONObject datos = (JSONObject) data.getJSONObject(i);
                                    String razon_social =  datos.getString("razon_social");
                                }
                                */

                            } catch (JSONException e) {
                                e.printStackTrace();
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
        // Add the request to the RequestQueue.
        //queue.add(json);
    }

    public void registrarNuevaEmp(View view) {
        Intent intent = new Intent(getApplicationContext(),RegistrarActivity.class);
        startActivity(intent);
    }
}





