package com.example.samuel.medimagem.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.samuel.medimagem.fragment.EscolherFotoFragment;
import com.example.samuel.medimagem.model.Exam;
import com.example.samuel.medimagem.model.Foto;
import com.example.samuel.medimagem.fragment.FotosFragment;
import com.example.samuel.medimagem.R;
import com.example.samuel.medimagem.callback.RemoveLoadingCallback;

import java.io.File;
import java.util.ArrayList;

// TODO: Terminar tela das fotos

public class FotosActivity extends AppCompatActivity implements FotosFragment.OnFragmentInteractionListener, EscolherFotoFragment.OnFragmentInteractionListener, RemoveLoadingCallback {
    private ArrayList<Bitmap> listaImagens;
    private ArrayList<Foto> listaFotos;

    private Foto imagemClicada;
    private int posicaoClicada;
    private FotosFragment fotosFragment;
    private EscolherFotoFragment escolherFotoFragment;
    private FragmentTransaction fragmentTransaction;
    private ProgressBar loading;
    private int count;
    private Exam exame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotos);


        exame = (Exam) getIntent().getSerializableExtra("exame");
        loading = findViewById(R.id.loading);



    }

    @Override
    protected void onResume() {
        ImagensTask task = new ImagensTask();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        task.execute();
        super.onResume();
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (getSupportFragmentManager().findFragmentByTag("escolher foto") != null){
            getSupportFragmentManager().popBackStack("fotos_escolher", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }else{

            Intent returningIntent = new Intent(FotosActivity.this, CameraActivity.class);
            returningIntent.putExtra("count", count);
            returningIntent.putExtra("exame", exame);
            returningIntent.putExtra("fotos", listaFotos);
            startActivity(returningIntent);
            finish();
            super.onBackPressed();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentByTag("escolher foto") != null){
            getSupportFragmentManager().popBackStack("fotos_escolher", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }else{

            Intent returningIntent = new Intent(FotosActivity.this, CameraActivity.class);
            returningIntent.putExtra("count", count);
            returningIntent.putExtra("exame", exame);
            returningIntent.putExtra("fotos", listaFotos);
            startActivity(returningIntent);
            finish();
            super.onBackPressed();
        }
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
                Intent observacoesIntent = new Intent(FotosActivity.this, ObservacoesActivity.class);
                observacoesIntent.putExtra("count", count);
                observacoesIntent.putExtra("exame", exame);
                observacoesIntent.putExtra("fotos", listaFotos);
                startActivity(observacoesIntent);
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
        escolherFotoFragment = EscolherFotoFragment.newInstance(listaFotos, posicaoClicada);
        fragmentTransaction.add(R.id.placeholder, escolherFotoFragment, "escolher foto");
        if (fotosFragment.isAdded()) {
            fragmentTransaction.hide(fotosFragment);
        }
        fragmentTransaction.addToBackStack("fotos_escolher");
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

    @Override
    public void removerLoading() {
        loading.setVisibility(View.GONE);
    }

    private class ImagensTask extends AsyncTask<Void, Void, ArrayList<Foto>>{

        @Override
        protected ArrayList<Foto> doInBackground(Void... voids) {
            if (listaFotos == null) {
                listaFotos = new ArrayList<>();
                File medImagemDirectory = new File(Environment.getExternalStorageDirectory(), "MedImagem");
                File path = null;
                if (!medImagemDirectory.exists()) {
                    if (!medImagemDirectory.mkdirs()){
                        finish();
                    }
                } else {
                    path = new File(medImagemDirectory, exame.getNomePaciente().toUpperCase() + exame.getId());

                }

                File[] imagens = path.listFiles();
                for (int i = 0; i < imagens.length; i++) {
                    if (imagens[i].isFile()) {
                        Foto foto = new Foto();
                        foto.setImagePath(imagens[i].getPath());
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
            fragmentTransaction.add(R.id.placeholder, fotosFragment, "Fotos");
            fragmentTransaction.addToBackStack("nada_fotos");
            fragmentTransaction.commit();
            super.onPostExecute(fotos);
        }
    }
}
