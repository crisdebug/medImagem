package com.example.samuel.medimagem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class ExameActivity extends AppCompatActivity {

    ListView lista_exame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exame);

        lista_exame = findViewById(R.id.lista_exame);
        ArrayList<Exam> listaExames = Exam.createLista();
        ExameAdapter adapter = new ExameAdapter(this, listaExames);
        lista_exame.setAdapter(adapter);
    }
}
