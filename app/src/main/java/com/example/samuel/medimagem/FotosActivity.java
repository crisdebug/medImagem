package com.example.samuel.medimagem;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private int count;
    private Exam exame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotos);
        loading = findViewById(R.id.loading);

        count = getIntent().getIntExtra("count", 0);
        exame = (Exam) getIntent().getSerializableExtra("exame");
        listaFotos = (ArrayList<Foto>) getIntent().getSerializableExtra("fotos");

        ImagensTask task = new ImagensTask();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        task.execute();


    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent returningIntent = new Intent(FotosActivity.this, CameraActivity.class);
        returningIntent.putExtra("count", count);
        returningIntent.putExtra("exame", exame);
        returningIntent.putExtra("fotos", listaFotos);
        startActivity(returningIntent);
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent returningIntent = new Intent(FotosActivity.this, CameraActivity.class);
        returningIntent.putExtra("count", count);
        returningIntent.putExtra("exame", exame);
        returningIntent.putExtra("fotos", listaFotos);
        startActivity(returningIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.fotos_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.laudo:
                startActivity(new Intent(FotosActivity.this, ObservacoesActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

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
        listaFotos.get(posicaoClicada).setBeingUsed(true);
        if (escolherFotoFragment.isAdded()){
            fragmentTransaction.remove(escolherFotoFragment);
        }
        fragmentTransaction.show(fotosFragment);
        fragmentTransaction.commit();

    }


    private class ImagensTask extends AsyncTask<Void, Void, ArrayList<Foto>>{

        @Override
        protected ArrayList<Foto> doInBackground(Void... voids) {
                if (listaFotos == null) {
                    listaFotos = new ArrayList<>();
                    File medImagemDirectory = new File(Environment.getExternalStorageDirectory(), "MedImagem");
                    File path = null;
                    if (!medImagemDirectory.exists()) {

                    } else {
                        path = new File(medImagemDirectory, exame.getNomePaciente().toUpperCase() + exame.getId());

                    }

                    File[] imagens = path.listFiles();
                    for (int i = 0; i < imagens.length; i++) {
                        if (imagens[i].isFile()) {
                            Foto foto = new Foto();
                            foto.setImagePath(imagens[i]);
                            foto.setBeingUsed(false);
                            listaFotos.add(foto);
                        }
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
