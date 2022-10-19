package co.edu.upb.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void pantallaEmpresa(View view) {
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
    }

    public void pantallaEstudiante(View view) {
        Intent intent = new Intent(getApplicationContext(),AccesoEstudianteActivity.class);
        startActivity(intent);
    }
}