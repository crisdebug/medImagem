package com.example.samuel.medimagem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ResumoExameActivity extends AppCompatActivity {

    Exam exame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo_exame);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        exame = (Exam) getIntent().getSerializableExtra("exame");
        TextView nomePacienteTV = findViewById(R.id.nome_paciente_tv);
        TextView dataNascimentoTV = findViewById(R.id.data_nascimento_tv);
        TextView nomeMaeTV = findViewById(R.id.nome_mae_tv);
        TextView dataExameTV = findViewById(R.id.data_tv);
        TextView horaExameTV = findViewById(R.id.hora_exame_tv);

        nomePacienteTV.setText(exame.getNomePaciente());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));
        dataNascimentoTV.setText(simpleDateFormat.format(exame.getDataNascimento().getTime()));

        nomeMaeTV.setText(exame.getNomeMae());

        dataExameTV.setText(simpleDateFormat.format(exame.getHoraData().getTime()));

        simpleDateFormat = new SimpleDateFormat("HH:mm", new Locale("pt", "BR"));
        horaExameTV.setText(simpleDateFormat.format(exame.getHoraData()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.resumo_exame_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.confirmar_menu:
                Intent intent = new Intent(ResumoExameActivity.this, CameraActivity.class);
                intent.putExtra("exame", exame);
                startActivity(intent);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigateUp() {
        finish();
        return super.onNavigateUp();
    }

    @Override
    public void onBackPressed() {
        finish();

    }
}
