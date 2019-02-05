package com.example.samuel.medimagem;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ExamesPagerAdapter extends FragmentStatePagerAdapter {

    int tabsNum;
    int medicoID;
    ExameAgendadosFragment exameAgendadosFragment;


    public ExamesPagerAdapter(FragmentManager fm, int tabsNum, int medicoID) {
        super(fm);
        this.tabsNum = tabsNum;
        this.medicoID = medicoID;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                exameAgendadosFragment = ExameAgendadosFragment.newInstance(medicoID);
                return exameAgendadosFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabsNum;
    }

    public void mudarFeito(int position){
        exameAgendadosFragment.mudarFeito(position);
    }
}
