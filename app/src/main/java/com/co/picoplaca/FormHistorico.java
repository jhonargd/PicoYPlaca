package com.co.picoplaca;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.co.BusinessObject.ConfigBO;
import com.co.adapter.HistorialAdapter;
import com.co.model.Registro;

import java.util.ArrayList;

public class FormHistorico extends AppCompatActivity {

    ArrayList<Registro> listaRegistros;
    String placa;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_historico);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Historico");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        getExtras();
        cargarVista();

    }

    private void getExtras() {
        placa = getIntent().getExtras().getString("placa");
    }

    private void cargarVista() {

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(FormHistorico.this, LinearLayoutManager.VERTICAL, false));

        listaRegistros = ConfigBO.listaRegistros(placa);

        HistorialAdapter adapter = new HistorialAdapter(listaRegistros);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
    
}
