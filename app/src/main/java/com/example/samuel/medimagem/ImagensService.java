package com.example.samuel.medimagem;


import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;



public class ImagensService extends IntentService implements FotosServiceCallback{

    private ResultReceiver resultReceiver;
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *

     */
    public ImagensService() {
        super("Service Fotos");
    }

    @Override
    protected void onHandleIntent(@androidx.annotation.Nullable Intent intent) {
        if (intent.hasExtra("solicitacao") && intent.hasExtra("exame") &&
                intent.hasExtra("result_receiver")){
            String solicitacao = intent.getStringExtra("solicitacao");
            Exam exame = (Exam) intent.getSerializableExtra("exame");
            resultReceiver = intent.getParcelableExtra("result_receiver");
            if (solicitacao != null){
                FotosTasks tasks = new FotosTasks();

                if (solicitacao.equals("contador")){
                    tasks.carregarContador(this, exame);
                }


            }
        }
    }



    @Override
    public void onSucessoContador(int count) {
        Bundle bundle = new Bundle();
        bundle.putInt("contador", count);
        resultReceiver.send(FotosTasks.SUCESSO_COUNT, bundle);
    }


    @Override
    public void onFalha() {

    }
}
