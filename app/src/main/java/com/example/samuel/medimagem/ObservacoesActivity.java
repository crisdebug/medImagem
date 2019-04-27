package com.example.samuel.medimagem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class ObservacoesActivity extends AppCompatActivity {

    private FloatingActionMenu menu;
    private FloatingActionButton button;

    private Exam exame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observacoes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        menu = findViewById(R.id.fab_menu);
        button = findViewById(R.id.fab_audio);

        exame = (Exam) getIntent().getSerializableExtra("exame");



    }


    @Override
    public boolean onSupportNavigateUp() {
        Intent returningIntent = new Intent(this, FotosActivity.class);
        returningIntent.putExtra("exame", exame);
        startActivity(returningIntent);
        finish();
        return true;
    }
}
