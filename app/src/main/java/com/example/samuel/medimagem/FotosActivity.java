package com.example.samuel.medimagem;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

// TODO: Terminar tela das fotos

public class FotosActivity extends AppCompatActivity {

    private GridView gridFotos;
    private List<Bitmap> listaImagens;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotos);

        gridFotos = findViewById(R.id.grid_fotos);
        ImagensTask task = new ImagensTask();
        task.execute();
        ImageAdapter adapter = new ImageAdapter(this, listaImagens);

        gridFotos.setAdapter(adapter);

        gridFotos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(FotosActivity.this, ""+position, Toast.LENGTH_LONG).show();
            }
        });

    }

    private class ImagensTask extends AsyncTask<Void, Void, List<Bitmap>>{

        @Override
        protected List<Bitmap> doInBackground(Void... voids) {
            List<Bitmap> lista = new ArrayList<>();
            File path = new File(Environment.getExternalStorageDirectory(), "MedImagem");

            File[] imagens = path.listFiles();
            for(int i = 0; i<imagens.length; i++){
                if(imagens[i].isFile()){
                    Bitmap myBitmap = BitmapFactory.decodeFile(imagens[i].getAbsolutePath());
                    lista.add(myBitmap);
                }
            }
            listaImagens = lista;
            return null;
        }

    }
}
