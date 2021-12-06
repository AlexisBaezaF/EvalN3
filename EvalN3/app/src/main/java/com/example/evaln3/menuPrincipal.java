package com.example.evaln3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class menuPrincipal extends AppCompatActivity {

    private createBitacora bitacora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
    }

    public void crearSiniestro(View view){
        Intent intento = new Intent(getApplicationContext(), createFireEmergencyEntry.class);
        startActivity(intento);
    }

    public void bitacora(View view){
        Intent intento = new Intent(getApplicationContext(), bitacora.class);
        intento.putExtra("siniestros", bitacora.getNewSiniestro());
        startActivity(intento);
    }
}