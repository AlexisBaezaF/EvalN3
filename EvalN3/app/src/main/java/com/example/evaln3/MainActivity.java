package com.example.evaln3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private TextView email, password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
        mAuth = FirebaseAuth.getInstance();
    }

    public void iniciarSesion(View view){
        mAuth.signInWithEmailAndPassword(email.getText().toString(),
                                         password.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    Toast.makeText(MainActivity.this, "Usuario: "+user.getUid(), Toast.LENGTH_SHORT).show();

                    Intent intento = new Intent(getApplicationContext(), menuPrincipal.class);
                    startActivity(intento);
                }else{
                    Toast.makeText(MainActivity.this, "Error al iniciar sesión. Usuario o contraseña incorrectos.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void crearUsuario(View view){
        Intent intento = new Intent(getApplicationContext(), crearUsuario.class);
        startActivity(intento);
    }
}