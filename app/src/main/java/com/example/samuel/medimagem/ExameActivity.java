package com.example.samuel.medimagem;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

public class ExameActivity extends AppCompatActivity implements ExameAgendadosFragment.OnExameAgendadoInteractionListener, ExamesFeitoFragment.OnExameFeitoInteractionListener {


    private ViewPager viewPager;
    private int medico;
    ExamesPagerAdapter pagerAdapter;
    FloatingActionButton fabExame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exame);
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
        pagerAdapter = new ExamesPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), medico);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 1){
                    fabExame.hide();
                }
                if (tab.getPosition() == 0){
                    fabExame.show();
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
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onExameAgendadoInteraction(int position) {
        pagerAdapter.mudarFeito(position);
    }

    @Override
    public void onExameFeitoInteraction(Exam exame) {

    }
}
