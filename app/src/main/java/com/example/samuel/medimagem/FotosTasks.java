package com.example.samuel.medimagem;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.OutputStream;
import java.util.ArrayList;

public class FotosTasks {

    private ArrayList<File> fotos;
    private Exam exame;

    public static final int CARREGAR_CONTADOR_FOTOS = 1;
    public static final int CARREGAR_FOTOS = 2;
    public static final int SALVAR_FOTO = 3;
    public static final int SUCESSO_COUNT = 4;
    public static final int SUCESSO_SALVAR = 5;
    private static final int FALHA = -1;

    private int countResult;



    public void carregarContador(FotosServiceCallback callback, Exam exame){
        IntentServiceHandler handler = new IntentServiceHandler(callback);
        this.exame = exame;
        handler.sendMessage(obtemMessage(CARREGAR_CONTADOR_FOTOS));
    }


    public void carregarFotos(FotosServiceCallback callback, Exam exame){
        IntentServiceHandler handler = new IntentServiceHandler(callback);
        this.exame = exame;
        handler.sendMessage(obtemMessage(CARREGAR_FOTOS));
    }


    public Message obtemMessage(int msgWhat){
        Message message = new Message();
        message.what = msgWhat;
        return message;
    }

    private class IntentServiceHandler extends Handler{
        private final FotosServiceCallback callback;

        public IntentServiceHandler(FotosServiceCallback callback){
            this.callback = callback;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CARREGAR_CONTADOR_FOTOS:
                    try{
                        File medImagemDirectory = new File(Environment.getExternalStorageDirectory(), "MedImagem");
                        File mediaStorageDir = null;
                        if (!medImagemDirectory.exists()){
                            if (!medImagemDirectory.mkdirs()){
                                sendMessage(obtemMessage(FALHA));
                            }
                        }else{
                            mediaStorageDir = new File(medImagemDirectory, exame.getNomePaciente().toUpperCase()+exame.getId());
                            if(!mediaStorageDir.exists()){
                                if (!mediaStorageDir.mkdirs()){
                                    Log.d("Diretorio", "Falha ao criar a Pasta");
                                    sendMessage(obtemMessage(FALHA));
                                }
                            }
                        }

                        if (mediaStorageDir != null){
                           countResult = mediaStorageDir.listFiles(new FilenameFilter() {
                               @Override
                               public boolean accept(File dir, String name) {
                                   return name.toLowerCase().endsWith(".jpg");
                               }
                           }).length;
                            Log.d("CONTADOR", "Contador="+countResult);
                           sendMessage(obtemMessage(SUCESSO_COUNT));
                        }

                    }catch (Exception e){
                        sendMessage(obtemMessage(FALHA));
                    }
                    break;
                case SUCESSO_COUNT:
                    callback.onSucessoContador(countResult);
                    break;
                case FALHA:

                    break;
            }
        }
    }

}
