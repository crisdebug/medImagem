package com.example.samuel.medimagem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ExameAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    private Context context;
    private ArrayList<Exam> exames;

    public ExameAdapter(Context context, ArrayList<Exam> exames){
        this.exames = exames;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return exames.size();
    }

    @Override
    public Object getItem(int position) {
        return exames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null)
            view = inflater.inflate(R.layout.item_exame, parent, false);
        TextView nome = view.findViewById(R.id.nome_exame);
        TextView hora = view.findViewById(R.id.hora_exame);
        Exam exam = exames.get(position);
        nome.setText(exam.getNomePaciente());
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", new Locale("pt", "BR"));
        hora.setText(dateFormat.format(exam.getHoraData().getTime()));
        return view;
    }
}
