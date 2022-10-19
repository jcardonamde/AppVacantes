package co.edu.upb.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import co.edu.upb.android.api.Api;
import co.edu.upb.android.datos.AdapterEmpresas;
import co.edu.upb.android.modelo.Empresa;

public class InicioActivity extends AppCompatActivity {
    TextView txtRazon,txtJson;
    ProgressBar pbApi;
    LinearLayout panel;
    Button btnCerrar;
    SharedPreferences sharedPref;

    RecyclerView mRVLista;
    AdapterEmpresas mAdapter;
    ArrayList<Empresa> listaEmpresasApi;
    Empresa em=new Empresa();

    private void obtenerDatosApi() {
        listaEmpresasApi = new ArrayList<>();
        em=new Empresa();
        em.setRazon("UPB");
        em.setNit("10887765");
        listaEmpresasApi.add(em);

        em=new Empresa();
        em.setRazon("UPB 2");
        em.setNit("1088776544");
        listaEmpresasApi.add(em);

        em=new Empresa();
        em.setRazon("UPB 33");
        em.setNit("10887765fsf");
        listaEmpresasApi.add(em);
    };
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Definir objetos">

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        btnCerrar=findViewById(R.id.btnCerrar);
        txtJson = findViewById(R.id.txtJson);
        txtRazon = findViewById(R.id.txtRazon);
        panel = findViewById(R.id.panel);
        pbApi = findViewById(R.id.progressBarApi);
        pbApi.setVisibility(View.GONE);
        mRVLista = findViewById(R.id.rvLista);
        mRVLista.setHasFixedSize(true);

        // Nuestro RecyclerView usará un linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRVLista.setLayoutManager(layoutManager);

        // Asociamos un adapter (ver más adelante cómo definirlo)
        listaEmpresasApi = new ArrayList<>();
        obtenerDatosApi();
        datosApiii();///real


        sharedPref = getSharedPreferences("empresa", Context.MODE_PRIVATE);

        txtRazon.setText(sharedPref.getString("razon_social", "Sin datos"));
        txtJson.setText(sharedPref.getString("datos", "Sin datos JSON"));
    }


    public void CerrarS (View view){
        //Solicitamos datos a una API REST
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Api.URL_BASE+"/empresas/logout";

        try {
            JSONObject datosJson = new JSONObject();
            datosJson.put("nit", sharedPref.getLong("nit", 0));
            datosJson.put("token", sharedPref.getString("token", ""));

            JsonObjectRequest strRequest = new JsonObjectRequest(Request.Method.POST, url, datosJson,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //
                            Intent i;
                            try {
                                boolean respuesta = response.getBoolean("respuesta");
                                if (respuesta){
                                    SharedPreferences pre = getSharedPreferences("empresa", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = pre.edit();
                                    editor.clear();
                                    //editor.apply();
                                    editor.commit();

                                    i = new Intent(getApplicationContext(),LoginActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(),"sapo",Toast.LENGTH_LONG).show();
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
    //</editor-fold>

    public void datosApiii (){
        //Solicitamos datos a una API REST - Instantiate the RequestQueue.
        pbApi.setVisibility(View.VISIBLE);
        panel.setVisibility(View.VISIBLE);
        SharedPreferences sharedPref = getSharedPreferences("empresa",Context.MODE_PRIVATE);

        RequestQueue queue = Volley.newRequestQueue(this);
        //String url = "https://testupb.fabricasoftware.co/api/empresas?token="+sharedPref.getString("token", "");
        String url = "https://vacantesappupb.000webhostapp.com/api/empresas?token="+sharedPref.getString("token", "");

        try {
            JSONObject datosJson = new JSONObject();
            //datosJson.put("nit", sharedPref.getLong("nit", 0));

            datosJson.put("token", sharedPref.getString("token", ""));

            JsonObjectRequest strRequest = new JsonObjectRequest(Request.Method.GET, url, datosJson,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray datos = response.getJSONArray("data");
                                if (datos.length()>0){
                                    listaEmpresasApi = new ArrayList<>();
                                    for(int posicion=0;posicion<datos.length();posicion++){
                                        JSONObject registro = datos.getJSONObject(posicion);
                                        Empresa eTemp = new Empresa();
                                        eTemp.setNit(registro.getString("nit"));
                                        eTemp.setRazon(registro.getString("razon_social"));
                                        eTemp.setDireccion(registro.getString("direccion"));
                                        listaEmpresasApi.add(eTemp);
                                    }
                                    mAdapter = new AdapterEmpresas(listaEmpresasApi);
                                    GridLayoutManager layoutManager=new GridLayoutManager(getApplicationContext(),2);
                                    mRVLista.setLayoutManager(layoutManager);

                                    mRVLista.setAdapter(mAdapter);
                                    pbApi.setVisibility(View.GONE);
                                    panel.setVisibility(View.GONE);
                                }
                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(),"sapo",Toast.LENGTH_LONG).show();
                                pbApi.setVisibility(View.GONE);
                                panel.setVisibility(View.GONE);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                            pbApi.setVisibility(View.GONE);
                            panel.setVisibility(View.GONE);
                        }
                    });
            queue.add(strRequest);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        // Add the request to the RequestQueue.
        //queue.add(json);
    }

    public void recargar(View view) {
        datosApiii();
    }
}
