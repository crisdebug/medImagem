package com.example.samuel.medimagem.model;

import com.example.samuel.medimagem.util.BaseModel;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Exam implements Serializable, BaseModel {
    private long id;
    private String nomePaciente;
    private Date dataNascimento;
    private String nomeMae;
    private Date horaData;
    private int viewType;

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public void setHoraData(Date horaData) {
        this.horaData = horaData;
    }

    private long medico;
    private boolean feito;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isFeito() {
        return feito;
    }

    public void setFeito(boolean feito) {
        this.feito = feito;
    }

    public long getMedico() {
        return medico;
    }

    public void setMedico(long medico) {
        this.medico = medico;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDataNascimento(){
        return this.dataNascimento;
    }

    public void setDataNascimento(String dataNascimento, String formato){
        try {
            DateFormat format = new SimpleDateFormat(formato, new Locale("pt", "BR"));
            this.dataNascimento = format.parse(dataNascimento);
        }catch (ParseException e){
            e.printStackTrace();
        }
    }

    public Date getHoraData(){
        return this.horaData;
    }

    public void setHoraData(String horaData, String formato){
        try{
            DateFormat format = new SimpleDateFormat(formato, new Locale("pt", "BR"));
            this.horaData = format.parse(horaData);
        }catch (ParseException e){
            e.printStackTrace();
        }
    }

    public String getNomePaciente() {
        return nomePaciente;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }


    @Override
    public int getItemViewType() {
        return this.viewType;
    }
}

