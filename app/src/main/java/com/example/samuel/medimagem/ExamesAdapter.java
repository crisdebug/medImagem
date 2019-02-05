package com.example.samuel.medimagem;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.samuel.medimagem.ExameAgendadosFragment.OnListFragmentInteractionListener;
import com.example.samuel.medimagem.dummy.DummyContent.DummyItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ExamesAdapter extends RecyclerView.Adapter<ExamesAdapter.ViewHolder> {

    private final ArrayList<Exam> exames;
    private final OnListFragmentInteractionListener mListener;

    public ExamesAdapter(ArrayList<Exam> exames, OnListFragmentInteractionListener listener) {
        this.exames = exames;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_exame, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.exam = exames.get(position);
        holder.nomePacienteTV.setText(exames.get(position).getNomePaciente());
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", new Locale("pt", "BR"));
        holder.horaExameTV.setText(dateFormat.format(exames.get(position).getHoraData().getTime()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return exames.size();
    }

    public void mudarFeito(int position){
        exames.get(position).setFeito(true);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView nomePacienteTV;
        public final TextView horaExameTV;
        public Exam exam;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            nomePacienteTV = (TextView) view.findViewById(R.id.nome_exame);
            horaExameTV = (TextView) view.findViewById(R.id.hora_exame);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + nomePacienteTV.getText() + "'";
        }
    }

}
