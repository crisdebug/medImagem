package com.example.samuel.medimagem;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.samuel.medimagem.ExamesFeitoFragment.OnExameFeitoInteractionListener;
import com.example.samuel.medimagem.dummy.DummyContent.DummyItem;

import java.util.ArrayList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnExameFeitoInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ExamesFeitosAdapter extends RecyclerView.Adapter<ExamesFeitosAdapter.ViewHolder> {

    private final ArrayList<Exam> exames;
    private final OnExameFeitoInteractionListener mListener;

    public ExamesFeitosAdapter(ArrayList<Exam> exames , OnExameFeitoInteractionListener listener) {
        this.exames = exames;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_examesfeito, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.exame = exames.get(position);
        holder.nomePacienteTV.setText(exames.get(position).getNomePaciente());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onExameFeitoInteraction(holder.exame);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return exames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView nomePacienteTV;
        public Exam exame;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            nomePacienteTV = view.findViewById(R.id.nome_paciente_feito);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + nomePacienteTV.getText() + "'";
        }
    }
}
