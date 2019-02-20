package com.example.samuel.medimagem;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ExamesPagerAdapter extends FragmentStatePagerAdapter {

    int tabsNum;
    int medicoID;
    ExameAgendadosFragment exameAgendadosFragment;
    ExamesFeitoFragment examesFeitoFragment;


    public ExamesPagerAdapter(FragmentManager fm, int medicoID) {
        super(fm);
        this.tabsNum = 2;
        this.medicoID = medicoID;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                exameAgendadosFragment = ExameAgendadosFragment.newInstance(medicoID);
                return exameAgendadosFragment;

            case 1:
                    examesFeitoFragment = ExamesFeitoFragment.newInstance(medicoID);
                    return examesFeitoFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabsNum;
    }

    public void atualizarAgendado(){
        exameAgendadosFragment.atualizarLista();
    }

    public void atualizarFeito(){
        examesFeitoFragment.atualizarLista();
    }

    public void mudarFeito(int position){
        exameAgendadosFragment.mudarFeito(position);
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Agendados";
            case 1:
                return "Feitos";
        }
        return super.getPageTitle(position);
    }


    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
