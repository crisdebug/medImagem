package com.example.samuel.medimagem;

import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;

public class Campo implements Serializable {

    public static final String TYPE_TEXT = "text";
    public static final String TYPE_CHOICE = "spinner";
    public static final String TYPE_RADIO = "radio";


    private String label;
    private String fieldType;
    private View field;
    private ArrayList<String> choices;

    public static ArrayList<Campo> gerarLista(){
        ArrayList<Campo> campos = new ArrayList<>();
        Campo campoA = new Campo();
        campoA.setLabel("A");
        campoA.setFieldType(Campo.TYPE_TEXT);
        campos.add(campoA);
        Campo campoB = new Campo();
        campoB.setLabel("B");
        campoB.setFieldType(Campo.TYPE_CHOICE);
        ArrayList<String> choices = new ArrayList<>();
        choices.add("A");
        choices.add("B");
        choices.add("C");
        campoB.setChoices(choices);
        campos.add(campoB);
        Campo campoC = new Campo();
        campoC.setLabel("C");
        campoC.setFieldType(Campo.TYPE_RADIO);
        campoC.setChoices(choices);
        campos.add(campoC);
        return campos;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public View getField() {
        return field;
    }

    public void setField(View field) {
        this.field = field;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ArrayList<String> getChoices() {
        return choices;
    }

    public void setChoices(ArrayList<String> choices) {
        this.choices = choices;
    }
}
