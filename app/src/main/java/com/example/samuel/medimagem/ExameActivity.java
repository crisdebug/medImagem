package com.example.samuel.medimagem;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toolbar;

import java.util.ArrayList;

public class ExameActivity extends AppCompatActivity implements ExameAgendadosFragment.OnListFragmentInteractionListener{


    private ViewPager viewPager;
    private int medico;
    private ArrayList<Exam> listaExames;
    ExamesPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exame);
        getSupportActionBar().hide();

        Toolbar toolbar = findViewById(R.id.toolbar);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Agendados"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        medico = getIntent().getIntExtra("medico_id", 0);

        final ViewPager viewPager = findViewById(R.id.exames_viewPager);
        pagerAdapter = new ExamesPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), medico);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {


                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void atualizarLista(){

    }


    @Override
    protected void onResume() {
        super.onResume();
        atualizarLista();


    }

    @Override
    public void onListFragmentInteraction(int position) {
        pagerAdapter.mudarFeito(position);

    }
}
