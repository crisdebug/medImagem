package com.example.samuel.medimagem.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.samuel.medimagem.model.Exam;
import com.example.samuel.medimagem.fragment.ExameAgendadosFragment;
import com.example.samuel.medimagem.fragment.ExamesFeitoFragment;
import com.example.samuel.medimagem.adapter.ExamesPagerAdapter;
import com.example.samuel.medimagem.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class ExameActivity extends AppCompatActivity implements ExameAgendadosFragment.OnExameAgendadoInteractionListener, ExamesFeitoFragment.OnExameFeitoInteractionListener, ViewPager.OnPageChangeListener {


    private ViewPager viewPager;
    private int medico;
    ExamesPagerAdapter pagerAdapter;
    FloatingActionButton fabExame;
    private static final int REQUEST = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exame);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, REQUEST);
        }else{
            init();
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onExameAgendadoInteraction(Exam exam) {
        Intent intent = new Intent(ExameActivity.this, ResumoExameActivity.class);
        intent.putExtra("exame", exam);
        startActivity(intent);
    }

    @Override
    public void onExameAgendadoLongInteraction(final int position) {
        String[] opcoes = {"Marcar como feito"};
        new AlertDialog.Builder(this).setItems(opcoes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        pagerAdapter.mudarFeito(position);
                        break;
                }
            }
        }).show();
    }

    @Override
    public void onExameFeitoInteraction(Exam exame) {

    }

    private void init(){
        getSupportActionBar().hide();

        Toolbar toolbar = findViewById(R.id.toolbar);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Agendados"));
        tabLayout.addTab(tabLayout.newTab().setText("Feitos"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        fabExame = findViewById(R.id.fab_exame);

        medico = getIntent().getIntExtra("medico_id", 0);

        fabExame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExameActivity.this, CadastrarExameActivity.class);
                intent.putExtra("medico_id", medico);
                startActivity(intent);
            }
        });

        final ViewPager viewPager = findViewById(R.id.exames_viewPager);
        pagerAdapter = new ExamesPagerAdapter(getSupportFragmentManager(), medico);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setSaveFromParentEnabled(false);
        viewPager.setOffscreenPageLimit(1);
        viewPager.addOnPageChangeListener(this);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 1){
                    fabExame.hide();
                    pagerAdapter.atualizarFeito();
                }
                if (tab.getPosition() == 0){
                    fabExame.show();
                    pagerAdapter.atualizarAgendado();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if(grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    if (grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                        init();
                        Log.d("PERMISSION", "Permissão dada");
                    }
                }

            }else{
                Toast.makeText(ExameActivity.this, "Este aplicativo precisa de permissões que não foram concedidas", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        Fragment fragment = pagerAdapter.getFragment(i);
        if (fragment != null){
            fragment.onResume();
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
