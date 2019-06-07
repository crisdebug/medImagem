package com.example.samuel.medimagem;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;

public class ObservacoesActivity extends AppCompatActivity {

    private FloatingActionMenu menu;
    private FloatingActionButton button_audio;
    private FloatingActionButton button_texto;
    private RecyclerView listaObservacoes;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Observacao> observacoes;
    private MediaPlayer mediaPlayer;
    private Exam exame;
    private int record_count = 0;
    private ObservacoesAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observacoes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        menu = findViewById(R.id.fab_menu);
        button_audio = findViewById(R.id.fab_audio);
        button_texto = findViewById(R.id.fab_texto);
        listaObservacoes = findViewById(R.id.lista_observacoes);

        exame = (Exam) getIntent().getSerializableExtra("exame");
        observacoes = new ArrayList<>();
        mediaPlayer = new MediaPlayer();

        layoutManager = new LinearLayoutManager(this);
        listaObservacoes.setLayoutManager(layoutManager);
        adapter = new ObservacoesAdapter(observacoes, mediaPlayer, this);
        listaObservacoes.setAdapter(adapter);


        button_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment prev = getSupportFragmentManager().findFragmentByTag("dialogAudio");
                if (prev != null){
                    ft.remove(prev);
                }
                ft.addToBackStack(null);

                Log.d("DEBUG", "CONTADOR: "+record_count);
                GravarAudioDialogFragment dialogFragment = GravarAudioDialogFragment.getInstance(exame, record_count);

                dialogFragment.show(ft, "dialogAudio");


            }
        });


    }


    @Override
    public boolean onSupportNavigateUp() {
        Intent returningIntent = new Intent(this, FotosActivity.class);
        returningIntent.putExtra("exame", exame);
        startActivity(returningIntent);
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent returningIntent = new Intent(this, FotosActivity.class);
        returningIntent.putExtra("exame", exame);
        startActivity(returningIntent);
        finish();
        super.onBackPressed();
    }

    public void updateCount(){
        record_count++;
    }

    public void addObservacao(Observacao observacao){
        Log.d("DEBUG", String.valueOf(observacoes.size()));
        adapter.notifyItemInserted(adapter.addObservacao(observacao));
        observacoes = adapter.observacoes;
    }
}
