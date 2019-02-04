package com.example.samuel.medimagem;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Exam implements Serializable {
    private long id;
    private String nomePaciente;
    private Date dataNascimento;
    private String nomeMae;
    private Date horaData;
    private long medico;

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
}

