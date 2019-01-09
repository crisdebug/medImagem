package com.example.samuel.medimagem;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// TODO: Terminar tela das fotos

public class FotosActivity extends AppCompatActivity implements FotosFragment.OnFragmentInteractionListener, EscolherFotoFragment.OnFragmentInteractionListener{
    private ArrayList<Bitmap> listaImagens;
    private ArrayList<Foto> listaFotos;
    private ProgressBar loading;
    private Foto imagemClicada;
    private int posicaoClicada;
    private FotosFragment fotosFragment;
    private EscolherFotoFragment escolherFotoFragment;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotos);
        loading = findViewById(R.id.loading);

        ImagensTask task = new ImagensTask();
        task.execute();


    }

    @Override
    public void onImageClicked(Foto foto, int position) {
        imagemClicada = foto;
        posicaoClicada = position;
        selectImage();
    }

    private void selectImage() {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        escolherFotoFragment = EscolherFotoFragment.newInstance(imagemClicada, posicaoClicada);
        fragmentTransaction.add(R.id.placeholder, escolherFotoFragment);
        if (fotosFragment.isAdded()) {
            fragmentTransaction.hide(fotosFragment);
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onButtonPressed(boolean aceito, int posicaoClicada) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fotosFragment.updateImagem(aceito, posicaoClicada);
        if (escolherFotoFragment.isAdded()){
            fragmentTransaction.remove(escolherFotoFragment);
        }
        fragmentTransaction.show(fotosFragment);
        fragmentTransaction.commit();

    }


    private class ImagensTask extends AsyncTask<Void, Void, ArrayList<Foto>>{

        @Override
        protected ArrayList<Foto> doInBackground(Void... voids) {
            listaFotos = new ArrayList<>();
            File path = new File(Environment.getExternalStorageDirectory(), "MedImagem");

            File[] imagens = path.listFiles();
            for(int i = 0; i<imagens.length; i++){
                if(imagens[i].isFile()){
                    Foto foto = new Foto();
                    foto.setImagePath(imagens[i]);
                    foto.setBeingUsed(false);
                    listaFotos.add(foto);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Foto> fotos) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fotosFragment = FotosFragment.newInstance(listaFotos);
            fragmentTransaction.add(R.id.placeholder, fotosFragment);
            fragmentTransaction.commit();
            loading.setVisibility(View.GONE);
            super.onPostExecute(fotos);
        }
    }
}
