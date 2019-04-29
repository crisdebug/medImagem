package com.example.samuel.medimagem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ObservacoesAdapter extends RecyclerView.Adapter<ObservacoesAdapter.BaseViewHolder> {

    private ArrayList<Observacao> observacoes;

    public ObservacoesAdapter (ArrayList<Observacao> observacoes){
        this.observacoes = observacoes;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 1){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.observacoes_texto_card, parent, false);
            return new TextoViewHolder(view);
        }
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.observacoes_texto_card, parent, false);
        return new AudioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        Observacao observacao = observacoes.get(position);
        holder.observacao = observacao;
        holder.bind(observacao);
    }

    @Override
    public int getItemCount() {
        return observacoes.size();
    }
    @Override
    public int getItemViewType(int position) {
        if (observacoes.get(position).getTipo().equals(Observacao.TIPO_TEXTO)){
            return 1;
        }
        return 0;
    }

    public static abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder{

        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        public Observacao observacao;

        public abstract void bind(T observacao);
    }

    public class TextoViewHolder extends BaseViewHolder<Observacao>{

        private TextView conteudo;

        public TextoViewHolder(@NonNull View itemView) {
            super(itemView);

            conteudo = itemView.findViewById(R.id.content_text);
        }

        @Override
        public void bind(Observacao observacao) {
            conteudo.setText(observacao.getConteudo());
        }
    }

    public class AudioViewHolder extends BaseViewHolder<Observacao>{
        private ImageView play;
        private SeekBar seekBar;

        public AudioViewHolder(@NonNull View itemView) {
            super(itemView);

            play = itemView.findViewById(R.id.play_button);
            seekBar = itemView.findViewById(R.id.audio_seek_bar);
        }


        @Override
        public void bind(Observacao observacao) {

        }
    }


}
