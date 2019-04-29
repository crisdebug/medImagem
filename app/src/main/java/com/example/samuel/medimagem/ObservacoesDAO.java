package com.example.samuel.medimagem;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class ObservacoesDAO {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static ObservacoesDAO instance;

    private ObservacoesDAO(Context context){
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static ObservacoesDAO getInstance(Context context){
        if (instance != null){
            instance = new ObservacoesDAO(context);
        }
        return instance;
    }

    public void abrir(){
        this.database = openHelper.getWritableDatabase();
    }

    public void fechar(){
        this.database.close();
    }

    public ArrayList<Observacao> getObservacoes(int exame){
        ArrayList<Observacao> observacoes = new ArrayList<>();

        Cursor cursor = database.query("Observacoes", null, "id_exame = ?", new String[]{String.valueOf(exame)}, null, null, null);
        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                Observacao observacao = new Observacao();
                observacao.setId(cursor.getLong(cursor.getColumnIndex("id")));
                observacao.setTipo(cursor.getString(cursor.getColumnIndex("Tipo")));
                observacao.setConteudo(cursor.getString(cursor.getColumnIndex("Conteudo")));
                observacoes.add(observacao);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return observacoes;
    }

    public Observacao getObservacao(int id){
        Observacao observacao = new Observacao();
        Cursor cursor = database.query("Observacoes", null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                observacao.setTipo(cursor.getString(cursor.getColumnIndex("Tipo")));
                observacao.setConteudo(cursor.getString(cursor.getColumnIndex("Conteudo")));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return observacao;
    }
}
