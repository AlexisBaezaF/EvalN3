package com.example.evaln3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class bitacora extends AppCompatActivity {

    private ArrayList<Siniestro> siniestros;
    private RecyclerView recyclerView;
    private adapter a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitacora);

        siniestros = (ArrayList<Siniestro>) getIntent().getSerializableExtra("siniestros");
        recyclerView = findViewById(R.id.RecyclerViewFE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        a = new adapter(siniestros);
        recyclerView.setAdapter(a);

    }
}