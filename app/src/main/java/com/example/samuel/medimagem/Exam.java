package com.example.samuel.medimagem;

import java.util.ArrayList;

public class Exam {
    private Paciente paciente;
    private String hora;

    public static ArrayList<Exam> createLista(){
        ArrayList<Exam> exams = new ArrayList<>();
        Exam A = new Exam();
        Paciente pA = new Paciente();
        pA.setNome("FULANO A");
        pA.setNomeMae("FULANA A");
        pA.setDataNascimento("12/12/12");
        A.setPaciente(pA);
        A.setHora("14:40");
        Exam B = new Exam();
        Paciente pB = new Paciente();
        pB.setNome("FULANO B");
        pB.setDataNascimento("12/12/12");
        pB.setNomeMae("FULANA B");
        B.setPaciente(pB);
        B.setHora("15:40");
        Exam C = new Exam();
        Paciente pC = new Paciente();
        pC.setNome("FULANO C");
        pC.setNomeMae("FULANA C");
        pC.setDataNascimento("12/12/12");
        C.setPaciente(pC);
        C.setHora("16:40");
        exams.add(A);
        exams.add(B);
        exams.add(C);
        return exams;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
