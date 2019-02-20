package com.example.samuel.medimagem;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CadastrarExameActivity extends AppCompatActivity {

    private EditText nomePacienteED;
    private EditText dataNascimentoED;
    private EditText nomeMaeED;
    private EditText horaDataED;
    private Button salvarB;
    private Calendar myCalendar;
    private String dataNascimento;
    private String dataHora;
    private int medico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_exame);

        nomePacienteED = findViewById(R.id.nome_paciente_ed);
        dataNascimentoED = findViewById(R.id.data_nascimento_ed);
        nomeMaeED = findViewById(R.id.nome_mae_ed);
        horaDataED = findViewById(R.id.data_hora_ed);
        salvarB = findViewById(R.id.salvar_exame_button);

        dataHora = "";
        dataNascimento = "";

        medico = getIntent().getIntExtra("medico_id", 0);

        myCalendar = Calendar.getInstance();

        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);
                String format = "dd/MM/yyyy - HH:mm";
                SimpleDateFormat dateFormat = new SimpleDateFormat(format, new Locale("pt", "BR"));
                dataHora = dateFormat.format(myCalendar.getTime());
                horaDataED.setText(dataHora);
            }
        };

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar = Calendar.getInstance();
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelData();
            }
        };
        final DatePickerDialog.OnDateSetListener dateTime = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar = Calendar.getInstance();
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                new TimePickerDialog(CadastrarExameActivity.this, time, 8, 0, true).show();
            }
        };


        dataNascimentoED.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    new DatePickerDialog(CadastrarExameActivity.this, date, myCalendar.get(Calendar.YEAR),
                            myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
                return false;
            }
        });

        horaDataED.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    myCalendar = Calendar.getInstance();
                    new DatePickerDialog(CadastrarExameActivity.this, dateTime,
                            myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
                return true;
            }
        });

        salvarB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean correct;
                Exam exam = new Exam();
                exam.setNomePaciente(nomePacienteED.getText().toString());
                exam.setDataNascimento(dataNascimentoED.getText().toString(), "dd/MM/yyyy");
                exam.setNomeMae(nomeMaeED.getText().toString());
                exam.setHoraData(horaDataED.getText().toString(), "dd/MM/yyyy - HH:mm");
                exam.setMedico(medico);

                correct = nomePacienteED.getText().toString().equals("") ||
                        dataNascimento.equals("") ||
                        nomeMaeED.getText().toString().equals("") ||
                        dataHora.equals("");

                if (!correct){
                    ExameDAO exameDAO = ExameDAO.getInstance(CadastrarExameActivity.this);
                    exameDAO.abrir();
                    if (exameDAO.addExame(exam)){
                        finish();
                    }else{
                        new AlertDialog.Builder(CadastrarExameActivity.this)
                                .setTitle("Erro")
                                .setMessage("Ocorreu um erro ao salvar o exame. Verifique os dados e tente novamente")
                                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                    }
                }else{
                    new AlertDialog.Builder(CadastrarExameActivity.this)
                            .setTitle("Erro")
                            .setMessage("Um ou mais campos estão vazios. Verifique os campos e tente novamente")
                            .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                }
            }
        });

    }


    private void updateLabelData(){
        String format = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, new Locale("pt", "BR"));
        dataNascimento = dateFormat.format(myCalendar.getTime());
        dataNascimentoED.setText(dataNascimento);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Log.i("DEBUG", "Return support");
        finish();
        return true;
    }
}
