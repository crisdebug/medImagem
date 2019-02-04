package com.example.samuel.medimagem;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ExameActivity extends AppCompatActivity {

    private ListView lista_exame;
    private FloatingActionButton fab;
    private int medico;
    private ArrayList<Exam> listaExames;
    private ExameAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exame);
        lista_exame = findViewById(R.id.lista_exame);
        fab = findViewById(R.id.add_paciente_fab);
        medico = getIntent().getIntExtra("medico_id", 0);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExameActivity.this, CadastrarExameActivity.class);
                intent.putExtra("medico_id", medico);
                startActivity(intent);
            }
        });
    }

    private void atualizarLista(){
        ExameDAO exameDAO = ExameDAO.getInstance(this);
        exameDAO.abrir();
        listaExames = exameDAO.getExames(medico);
        exameDAO.fechar();
    }


    @Override
    protected void onResume() {
        super.onResume();
        atualizarLista();
        adapter = new ExameAdapter(this, listaExames);
        lista_exame.setAdapter(adapter);
        lista_exame.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ExameActivity.this, ResumoExameActivity.class);
                intent.putExtra("exame", listaExames.get(position));
                startActivity(intent);
            }
        });
    }
}
