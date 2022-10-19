package co.edu.upb.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
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

import co.edu.upb.android.datos.AdapterVacantes;
import co.edu.upb.android.modelo.Vacantes;

public class VacantesEstudianteActivity extends AppCompatActivity {
    TextView txtVacante,txtJson2;
    LinearLayout panelVacantes;
    ProgressBar pBarApi;
    SharedPreferences sharedPref;

    RecyclerView mRVListaVacantes;
    AdapterVacantes mAdapter;
    ArrayList<Vacantes> listaVacantesApi;
    Vacantes em = new Vacantes();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacantes_estudiante);

        txtJson2 = findViewById(R.id.txtJson2);
        txtVacante = findViewById(R.id.txtVacante);
        panelVacantes = findViewById(R.id.panelVacantes);
        pBarApi = findViewById(R.id.pBarApi);
        pBarApi.setVisibility(View.GONE);
        mRVListaVacantes = findViewById(R.id.rvLista);
        mRVListaVacantes.setHasFixedSize(true);

        // Nuestro RecyclerView usará un linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRVListaVacantes.setLayoutManager(layoutManager);

        // Asociamos un adapter (ver más adelante cómo definirlo)
        listaVacantesApi = new ArrayList<>();
        datosApi();

        sharedPref = getSharedPreferences("estudiante", Context.MODE_PRIVATE);

        txtVacante.setText(sharedPref.getString("nombre", "Sin datos"));
        txtJson2.setText(sharedPref.getString("datos", "Sin datos JSON"));
    }

    public void datosApi (){
        //Solicitamos datos a una API REST - Instantiate the RequestQueue.
        pBarApi.setVisibility(View.VISIBLE);
        panelVacantes.setVisibility(View.VISIBLE);
        SharedPreferences sharedPref = getSharedPreferences("estudiante", Context.MODE_PRIVATE);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://vacantesappupb.000webhostapp.com/api/vacantes?token="+sharedPref.getString("token", "");

        try {
            JSONObject datosJson = new JSONObject();
            datosJson.put("token", sharedPref.getString("token", ""));

            JsonObjectRequest strRequest = new JsonObjectRequest(Request.Method.GET, url, datosJson,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray datos = response.getJSONArray("data");
                                if (datos.length()>0){
                                    listaVacantesApi = new ArrayList<>();
                                    for(int posicion=0; posicion<datos.length(); posicion++){
                                        JSONObject registro = datos.getJSONObject(posicion);
                                        Vacantes vTemp = new Vacantes();
                                        vTemp.setNombre(registro.getString("nombre"));
                                        vTemp.setDetalle(registro.getString("detalle"));
                                        vTemp.setProfesiones_asociadas(registro.getString("profesiones_asociadas"));
                                        vTemp.setSalario(registro.getString("salario"));
                                        listaVacantesApi.add(vTemp);
                                    }
                                    mAdapter = new AdapterVacantes(listaVacantesApi);
                                    GridLayoutManager layoutManager=new GridLayoutManager(getApplicationContext(),2);
                                    mRVListaVacantes.setLayoutManager(layoutManager);

                                    mRVListaVacantes.setAdapter(mAdapter);
                                    pBarApi.setVisibility(View.GONE);
                                    panelVacantes.setVisibility(View.GONE);
                                }
                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(),"oh omg",Toast.LENGTH_LONG).show();
                                pBarApi.setVisibility(View.GONE);
                                panelVacantes.setVisibility(View.GONE);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                            pBarApi.setVisibility(View.GONE);
                            panelVacantes.setVisibility(View.GONE);
                        }
                    });
            queue.add(strRequest);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void recargarData(View view) {
        pBarApi.setVisibility(View.VISIBLE);
        panelVacantes.setVisibility(View.VISIBLE);
        datosApi();
    }
}