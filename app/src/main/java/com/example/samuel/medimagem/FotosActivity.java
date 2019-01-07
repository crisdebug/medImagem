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
import java.util.List;

// TODO: Terminar tela das fotos

public class FotosActivity extends AppCompatActivity implements FotosFragment.OnFragmentInteractionListener, EscolherFotoFragment.OnFragmentInteractionListener{
    private ArrayList<Bitmap> listaImagens;
    private ProgressBar loading;
    private Bitmap imagemClicada;
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
    public void onImageClicked(Bitmap bitmap) {
        imagemClicada = bitmap;
        selectImage();
    }

    private void selectImage() {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        escolherFotoFragment = EscolherFotoFragment.newInstance(imagemClicada);
        fragmentTransaction.add(R.id.placeholder, escolherFotoFragment);
        if (fotosFragment.isAdded()) {
            fragmentTransaction.hide(fotosFragment);
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onButtonPressed(Bitmap bitmap) {

    }


    private class ImagensTask extends AsyncTask<Void, Void, List<Bitmap>>{

        @Override
        protected List<Bitmap> doInBackground(Void... voids) {
            ArrayList<Bitmap> lista = new ArrayList<>();
            File path = new File(Environment.getExternalStorageDirectory(), "MedImagem");

            File[] imagens = path.listFiles();
            for(int i = 0; i<imagens.length; i++){
                if(imagens[i].isFile()){
                    Bitmap myBitmap = BitmapFactory.decodeFile(imagens[i].getAbsolutePath());
                    lista.add(myBitmap);
                }
            }
            listaImagens = lista;
            return lista;
        }

        @Override
        protected void onPostExecute(List<Bitmap> bitmaps) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fotosFragment = FotosFragment.newInstance(listaImagens);
            fragmentTransaction.add(R.id.placeholder, fotosFragment);
            fragmentTransaction.commit();
            loading.setVisibility(View.GONE);
            super.onPostExecute(bitmaps);
        }
    }
}
