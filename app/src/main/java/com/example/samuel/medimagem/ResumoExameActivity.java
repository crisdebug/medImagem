package com.example.samuel.medimagem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ResumoExameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo_exame);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Exam exame = (Exam) getIntent().getSerializableExtra("exame");
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
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
