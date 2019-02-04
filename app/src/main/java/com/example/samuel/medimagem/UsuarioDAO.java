package com.example.samuel.medimagem;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UsuarioDAO {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static UsuarioDAO instance;

    private UsuarioDAO(Context context){
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static UsuarioDAO getInstance(Context context){
        if (instance == null){
            instance = new UsuarioDAO(context);
        }
        return instance;
    }

    public void abrir(){
        this.database = openHelper.getWritableDatabase();
    }

    public void fechar(){
        if (database != null){
            this.database.close();
        }
    }

    public int authenticate(String user, String senha){

        Cursor cursor = database.query("Medico", null, "Usuario = ?", new String[]{user}, null, null, null);
        if (cursor.moveToFirst()){
            String dbSenha = cursor.getString(cursor.getColumnIndex("Senha"));
            if (!dbSenha.equals(senha)){
                return -2;
            }
            return cursor.getInt(cursor.getColumnIndex("id"));
        }
        return -1;
    }
}
