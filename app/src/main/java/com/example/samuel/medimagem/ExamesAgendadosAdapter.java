package com.example.samuel.medimagem;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

public class ExamesAgendadosAdapter extends RecyclerView.Adapter<ExamesAgendadosAdapter.BaseViewHolder> {

    private ArrayList<Exam> exames;
    private ExameAgendadosFragment.OnExameAgendadoInteractionListener listener;



    public ExamesAgendadosAdapter (ArrayList<Exam> exames, ExameAgendadosFragment.OnExameAgendadoInteractionListener listener){
        this.exames = exames;
        this.listener = listener;

        Collections.sort(exames, new Comparator<Exam>() {
            @Override
            public int compare(Exam o1, Exam o2) {
                return o1.getHoraData().getDate() - o2.getHoraData().getDate();
            }
        });

        Collections.sort(exames, new Comparator<Exam>() {
            @Override
            public int compare(Exam o1, Exam o2) {
                long value = o1.getHoraData().getTime() - o2.getHoraData().getTime();
                int ret = (int) value;
                return ret;
            }
        });


        for (int i = 0; i<exames.size(); i++){
            if (i == 0 || exames.get(i).getHoraData().getDate() - exames.get(i-1).getHoraData().getDate() > 0){
                Exam header = new Exam();
                header.setViewType(1);
                header.setHoraData(exames.get(i).getHoraData());
                header.setFeito(false);
                exames.add(i, header);
                Log.i("DEBUG", "header add");
            }
        }

    }

    public static abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder{

        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        public Exam exam;

        public abstract void bind(T exam);
    }


    public  class ExameViewHolder extends BaseViewHolder<Exam>{
        public Exam exam;
        public View view;
        public TextView nomePaciente;
        public TextView horaExame;

        public ExameViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;

            nomePaciente = itemView.findViewById(R.id.nome_paciente_agendado);
            horaExame = itemView.findViewById(R.id.hora_exame_agendado);



        }

        @Override
        public void bind(final Exam exam) {
            nomePaciente.setText(exam.getNomePaciente());
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm",  new Locale("pt", "BR"));
            horaExame.setText(dateFormat.format(exam.getHoraData().getTime()));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ExamesAgendadosAdapter.this.listener.onExameAgendadoInteraction(exam);
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ExamesAgendadosAdapter.this.listener.onExameAgendadoLongInteraction(getAdapterPosition());
                    return false;
                }
            });
        }
    }

    public static class HeaderViewHolder extends BaseViewHolder<Exam>{
        public Exam exam;

        public TextView dataExame;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            dataExame = itemView.findViewById(R.id.data_exame_header);
        }

        @Override
        public void bind(Exam exam) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("pt", "BR"));

            dataExame.setText(dateFormat.format(exam.getHoraData()));
        }
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        Log.i("DEBUG", "view type: " + i);
        if (i == 0){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.exame_agendado_item, viewGroup, false);
            Log.i("DEBUG", "item render");
            return new ExameViewHolder(view);
        }else if (i == 1) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.exame_agendado_header, viewGroup, false);
            Log.i("DEBUG", "header render");
            return new HeaderViewHolder(view);

        }

        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder, final int i) {

        Exam exame = exames.get(i);
        viewHolder.exam = exame;
        if (!exame.isFeito()){
            viewHolder.bind(exame);
        }
    }

    @Override
    public int getItemCount() {
        return exames.size();
    }

    public void mudarFeito(int position){
        exames.get(position).setFeito(true);

    }

    @Override
    public int getItemViewType(int position) {
        return exames.get(position).getItemViewType();
    }
}
