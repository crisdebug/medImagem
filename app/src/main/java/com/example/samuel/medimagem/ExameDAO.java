package com.example.samuel.medimagem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ExameDAO {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static ExameDAO instance;

    private ExameDAO(Context context){
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static ExameDAO getInstance(Context context){
        if (instance == null){
            instance = new ExameDAO(context);
        }
        return instance;
    }

    public void abrir(){
        this.database = openHelper.getWritableDatabase();
    }

    public void fechar(){
        this.database.close();
    }

    public ArrayList<Exam> getExames(int medico, int feito){
        ArrayList<Exam> exams = new ArrayList<>();
        Cursor cursor = database.query("Exames", null, "Medico = ? AND Feito = ?", new String[]{String.valueOf(medico), String.valueOf(feito)}, null, null, null);
        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                Exam exam = new Exam();
                exam.setId(cursor.getLong(cursor.getColumnIndex("id")));
                exam.setNomePaciente(cursor.getString(cursor.getColumnIndex("NomePaciente")));
                exam.setDataNascimento(cursor.getString(cursor.getColumnIndex("DataNascimento")), "ddMMyyyy");
                exam.setNomeMae(cursor.getString(cursor.getColumnIndex("NomeMae")));
                exam.setHoraData(cursor.getString(cursor.getColumnIndex("DataHora")), "ddMMyyyy - HHmm");
                exam.setMedico(cursor.getLong(cursor.getColumnIndex("Medico")));
                exam.setFeito(cursor.getInt(cursor.getColumnIndex("Feito")) == 1);
                exam.setType(0);
                exams.add(exam);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return exams;
    }

    public Exam getExame(long id){
        Exam exame = new Exam();
        Cursor cursor = database.query("Exames", null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                exame.setId(cursor.getLong(cursor.getColumnIndex("id")));
                exame.setNomePaciente(cursor.getString(cursor.getColumnIndex("NomePaciente")));
                exame.setDataNascimento(cursor.getString(cursor.getColumnIndex("DataNascimento")), "ddMMyyyy");
                exame.setNomeMae(cursor.getString(cursor.getColumnIndex("NomeMae")));
                exame.setHoraData(cursor.getString(cursor.getColumnIndex("DataHora")), "ddMMyyyy - HHmm");
                exame.setMedico(cursor.getLong(cursor.getColumnIndex("Medico")));
                exame.setFeito(cursor.getInt(cursor.getColumnIndex("Feito")) == 1);
                exame.setType(0);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return exame;
    }


    public boolean addExame(Exam exam){
        SimpleDateFormat dateFormatData = new SimpleDateFormat("ddMMyyy", new Locale("pt", "BR"));
        SimpleDateFormat dateFormatHora = new SimpleDateFormat("ddMMyyy - HHmm", new Locale("pt", "BR"));
        ContentValues values = new ContentValues();
        values.put("NomePaciente", exam.getNomePaciente());
        values.put("DataNascimento", dateFormatData.format(exam.getDataNascimento().getTime()));
        values.put("NomeMae", exam.getNomeMae());
        values.put("DataHora", dateFormatHora.format(exam.getHoraData().getTime()));
        values.put("Medico", exam.getMedico());

        long result = database.insert("Exames", null, values);

        if (result == -1){
            return false;
        }
        return true;

    }

    public void atualizarFeito(Exam exam, boolean feito){
        ContentValues values = new ContentValues();
        values.put("Feito", feito ? "1" : "0");
        database.update("Exames", values, "id = ?", new String[]{String.valueOf(exam.getId())});
    }

}
