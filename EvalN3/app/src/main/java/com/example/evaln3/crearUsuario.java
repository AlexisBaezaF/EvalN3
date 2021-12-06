package com.example.evaln3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.sql.Date;
import java.util.ArrayList;

public class crearUsuario extends AppCompatActivity {

    private TextView email, password, name;
    private Spinner genre;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);

        email = findViewById(R.id.createEmail);
        password = findViewById(R.id.createPassword);
        name = findViewById(R.id.createName);
        genre = (Spinner) findViewById(R.id.createGenre);
        mAuth = FirebaseAuth.getInstance();

        ArrayList<String> spinnerElements = new ArrayList<String>();
        spinnerElements.add("Masculino");
        spinnerElements.add("Femenino");
        spinnerElements.add("No especifica");
        spinnerElements.add("Otros");

        ArrayAdapter adp = new ArrayAdapter(crearUsuario.this, android.R.layout.simple_spinner_dropdown_item, spinnerElements);
        genre.setAdapter(adp);
        genre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String element = (String) genre.getAdapter().getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
    }

    public void crearNuevaCuenta(View view){
        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser newuser = mAuth.getCurrentUser();
                    mensajeOK();

                    Intent intento = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intento);
                }else{
                    mensajeERROR();
                }
            }
        });
    }

    private void mensajeOK(){
        Toast.makeText(this, "¡Usuario creado satisfactoriamente!", Toast.LENGTH_SHORT).show();
    }

    private void mensajeERROR(){
        Toast.makeText(this, "Error al crear nuevo usuario", Toast.LENGTH_SHORT).show();
    }
}