package co.edu.upb.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PerfilEstudianteActivity extends AppCompatActivity {
    TextView txvId_UPB, txvNombress, txvApellidos, txvCorreo, txvTelefono, txvPais, txvCiudad;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_estudiante);

        sharedPref = getSharedPreferences("estudiante", Context.MODE_PRIVATE);
        txvId_UPB = findViewById(R.id.txvId_UPB);
        txvNombress = findViewById(R.id.txvNombress);
        txvApellidos = findViewById(R.id.txvApellidos);
        txvCorreo = findViewById(R.id.txvCorreo);
        txvTelefono = findViewById(R.id.txvTelefono);
        txvPais = findViewById(R.id.txvPais);
        txvCiudad = findViewById(R.id.txvCiudad);

        long id = sharedPref.getLong("id_UPB", 0);
        txvId_UPB.setText(String.valueOf(id));
        txvNombress.setText(sharedPref.getString("nombre", ""));
        txvApellidos.setText(sharedPref.getString("apellido", ""));
        txvCorreo.setText(sharedPref.getString("correo", ""));
        txvTelefono.setText(sharedPref.getString("telefono", ""));
        txvPais.setText(sharedPref.getString("pais", ""));
        txvCiudad.setText(sharedPref.getString("ciudad", ""));
    }

    public void explorarVacantes(View view) {
        Intent intent = new Intent(getApplicationContext(),VacantesEstudianteActivity.class);
        startActivity(intent);
    }
}