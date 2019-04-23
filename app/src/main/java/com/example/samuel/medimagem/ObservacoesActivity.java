package com.example.samuel.medimagem;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ObservacoesActivity extends AppCompatActivity {

    private FloatingActionButton fabOpen;
    private FloatingActionButton fabClose;
    private FloatingActionButton fabCreateText;
    private FloatingActionButton fabCreateAudio;

    private boolean isFabOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observacoes);

        fabOpen = findViewById(R.id.fab_open_menu);
        fabClose = findViewById(R.id.fab_close_menu);
        fabCreateText = findViewById(R.id.fab_create_text);
        fabCreateAudio = findViewById(R.id.fab_create_audio);

        hideFabs();

        fabOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFabOpen){
                    showFabMenu();
                }
            }
        });

        fabClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFabOpen){
                    closeFabMenu();
                }
            }
        });
    }

    private void hideFabs() {
        fabClose.hide();
        fabCreateText.hide();
        fabCreateAudio.hide();
    }

    private void showFabMenu(){
        isFabOpen = true;
        fabOpen.hide();
        showFabs();
        fabCreateText.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fabCreateAudio.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
    }

    private void showFabs() {
        fabClose.show();
        fabCreateText.show();
        fabCreateAudio.show();
    }

    private void closeFabMenu(){
        isFabOpen = false;
        fabOpen.show();
        hideFabs();
        fabCreateText.animate().translationY(0);
        fabCreateAudio.animate().translationY(0);
    }



}
