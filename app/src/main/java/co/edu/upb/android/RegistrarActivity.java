package co.edu.upb.android;

// import co.edu.upb.android.modelo.Empresa;
// import android.content.Intent;
// import android.widget.RadioButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
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


public class RegistrarActivity extends AppCompatActivity {
    EditText txtRazonSocial, txtDireccion, txtNit, txtCorreo, txtContrasena, txtConfirmarContrasena;
    Button btnRegistrar;
    ProgressBar pbEsperar;

    private void inicializarGUI() {
        txtRazonSocial = findViewById(R.id.etApellido);
        txtNit = findViewById(R.id.etIdUpb);
        txtDireccion = findViewById(R.id.etDireccion);
        txtCorreo = findViewById(R.id.etCorreo);
        txtContrasena = findViewById(R.id.etContrasena);
        txtConfirmarContrasena =  findViewById(R.id.etConfirmaContrasena);
        btnRegistrar = findViewById(R.id.btnRegistrarEst);
        pbEsperar = findViewById(R.id.pbEsperar);
        pbEsperar.setVisibility(View.GONE);
    }

    private void limpiarCampos() {
        txtRazonSocial.getText().clear();
        txtNit.getText().clear();
        txtDireccion.getText().clear();
        txtCorreo.getText().clear();
        txtContrasena.getText().clear();
        txtConfirmarContrasena.getText().clear();
        btnRegistrar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        inicializarGUI();
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarEmpresa();
            }
        });
    }

    public void loginEmpresa(View view){
        btnRegistrar.setVisibility(View.GONE);
        pbEsperar.setVisibility(View.VISIBLE);

        //Solicitamos datos a una API REST
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Api.URL_BASE+"/empresas/login";

        String raz = txtRazonSocial.getText().toString();
        // Request a string response from the provided URL.
        try {

            JSONObject obj = new JSONObject();
            obj.put("nit", "1234");
            obj.put("contrasena", "1234");

            JsonObjectRequest strRequest = new JsonObjectRequest(Request.Method.POST, url, obj,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //
                            try {
                                String meta = response.getString("meta");

                                JSONObject data = (JSONObject) response.get("data");

                                String token = data.getString("token");
                                JSONObject empresa = (JSONObject) data.get("empresa");

                                String razon_social = empresa.getString("razon_social");
                                long nit = empresa.getLong("nit");

                                Toast.makeText(getApplicationContext(), razon_social, Toast.LENGTH_SHORT).show();
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
        }catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        // Add the request to the RequestQueue.
        //queue.add(json);
    }

    public void registrarEmpresa() {
        btnRegistrar.setVisibility(View.GONE);
        pbEsperar.setVisibility(View.VISIBLE);

        // Solicitamos datos a una API Rest
        //RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Api.URL_BASE + "/empresas";

        // Request a string response from the provided URL.
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        btnRegistrar.setVisibility((View.VISIBLE));
                        pbEsperar.setVisibility(View.GONE);

                        try {
                            JSONObject respObj = new JSONObject(response);
                            Toast.makeText(getApplicationContext(),respObj.getString("Mensaje"),Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        limpiarCampos();
                        btnRegistrar.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(),"Se Ha registrado exitosamente", Toast.LENGTH_LONG).show();

                        Intent intension = new Intent(getApplicationContext(),LoginActivity.class);
                        //ejecuto la intension de una pantalla a otro
                        startActivity(intension);
                        //finaliza la pantalla si le dan atras
                        finish();
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                btnRegistrar.setVisibility(View.VISIBLE);
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
            params.put("razon_social",txtRazonSocial.getText().toString());
            params.put("nit",txtNit.getText().toString());
            params.put("direccion",txtDireccion.getText().toString());
            params.put("correo", txtCorreo.getText().toString());
            params.put("contrasena", txtContrasena.getText().toString());

            // at last we are returning our params.
            return params;
            }
        };
        // below line is to make a json object request and Add the request to the RequestQueue.
        queue.add(request);
    }


    public void guardarEstudiante(View v){

    }

    public void consultar(View v){

    }
}

/*
        CountDownTimer con = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                btnRegistrar.setVisibility((View.VISIBLE));
                pbEsperar.setVisibility(view.GONE);
                Toast.makeText(getApplicationContext(), "Finalizó la petición", Toast.LENGTH_LONG).show();
                //Intent in = new Intent(MainActivity.this, RegistrarActivity.class);
                //startActivity(in);
                //finish();
            }
        };
        con.start();
        */

/*
    private boolean validateForm() {
        if(txtName.getText().toString().isEmpty()) {
            txtName.setError("Debe ingresar un nombre");
            return false;
        } else if (txtName.getText().toString().length()<5) {
            txtName.setError("Debe ingresar un nombre");
            return false;
        } else {
            return true;
        }
    }
    */

/*
    public void saveStudent(View v) {
        if(validateForm()) {
            //recoger los datos del formulario
            Estudiante e = new Estudiante();
            e.setName(txtName.getText().toString());
            e.setLastname(txtLastname.getText().toString());
            e.setAddress(txtAddress.getText().toString());
            e.setPhone(txtPhone.getText().toString());
            e.setEmail(txtEmail.getText().toString());
            e.setAge(txtAge.getText().toString());
            e.setPassword(txtPassword.getText().toString());

            /*
        Empresa e = new Empresa();
        e.setRazon_social(etRazonSocial.getText().toString());
        e.setNit(etNit.getText().toString());
        e.setDireccion(etDireccion.getText().toString());
        e.setCorreo(etCorreo.getText().toString());
        e.setContrasena(etContrasena.getText().toString());
        e.setConfirmar(etConfirmar.getText().toString());
         */

/*
public void registrarEmpresa(View view) {
        btnRegistrar.setVisibility((View.GONE));
        pbEsperar.setVisibility(view.VISIBLE);

        // Solicitamos datos a una API Rest
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Api.URL_BASE + "/empresas";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        btnRegistrar.setVisibility((View.VISIBLE));
                        pbEsperar.setVisibility(view.GONE);
                        //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), "Se Ha registrado exitosamente", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                btnRegistrar.setVisibility((View.VISIBLE));
                pbEsperar.setVisibility(view.GONE);
                if (error.getMessage().contains("Connection reset")) {
                    Toast.makeText(getApplicationContext(), "Upps, verifique su conexión a Wifi o Datos", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Upps, Intente más tarde!" + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
 */