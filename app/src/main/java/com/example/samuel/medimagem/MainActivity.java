package com.example.samuel.medimagem;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST = 200;
    final Calendar myCalendar = Calendar.getInstance();
    private EditText nome;
    private EditText data;
    private EditText nomeMae;
    private EditText nomePai;
    private Button proximo;
    private boolean continuar = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST);
        }else{
            init();
        }



    }

    private void init() {
        setContentView(R.layout.activity_main);
        nome = findViewById(R.id.nome);
        data = findViewById(R.id.data);
        nomeMae = findViewById(R.id.nomeMae);
        nomePai = findViewById(R.id.nomePai);
        proximo = findViewById(R.id.proximo);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        data.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    new DatePickerDialog(MainActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            }
        });

        proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(nome.getText().toString().equals("")) &&
                        !(data.getText().toString().equals("")) &&
                        !(nomeMae.getText().toString().equals(""))
                        ) {
                    Paciente paciente = new Paciente();
                    paciente.setNome(nome.getText().toString());
                    paciente.setDataNascimento(nome.getText().toString());
                    paciente.setNomeMae(nomeMae.getText().toString());
                    paciente.setNomePai(nomePai.getText().toString());

                    startActivity(new Intent(MainActivity.this, CameraActivity.class));
                }


            }
        });
    }

    private void updateLabel(){

        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt", "BR"));

        data.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if(grantResults[1] == PackageManager.PERMISSION_GRANTED){
                    init();
                    Log.d("PERMISSION", "Permissão dada");
                }

            }else{
                Toast.makeText(MainActivity.this, "Este aplicativo precisa de permissões que não foram concedidas", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
