package com.example.samuel.medimagem.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samuel.medimagem.model.Observacao;
import com.example.samuel.medimagem.R;

import java.util.ArrayList;
//TODO: TESTAR ADAPTER
//TODO: IMPLEMENTAR PAUSA
//TODO: IMPLEMENTAR DELETE

public class ObservacoesAdapter extends RecyclerView.Adapter<ObservacoesAdapter.BaseViewHolder> {

    public ArrayList<Observacao> observacoes;
    private MediaPlayer player;
    private Context context;


    public ObservacoesAdapter (ArrayList<Observacao> observacoes, MediaPlayer player, Context context){
        this.observacoes = observacoes;
        this.player = player;
        this.context = context;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 1){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.observacoes_texto_card, parent, false);
            return new TextoViewHolder(view);
        } else if(viewType == 0){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.observacoes_audio_card, parent, false);
            return new AudioViewHolder(view);
        }
        return null;
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
        } else if(observacoes.get(position).getTipo().equals(Observacao.TIPO_AUDIO)){
            return 0;
        }
        return -1;
    }

    public static abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder{

        BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        Observacao observacao;

        public abstract void bind(T observacao);
    }

    public class TextoViewHolder extends BaseViewHolder<Observacao>{

        private TextView conteudo;

        TextoViewHolder(@NonNull View itemView) {
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

        private final int STATE_STOPPED = 0;
        private final int STATE_PAUSED = 1;
        private int state = STATE_STOPPED;


        AudioViewHolder(@NonNull View itemView) {
            super(itemView);

            play = itemView.findViewById(R.id.play_button);
            seekBar = itemView.findViewById(R.id.audio_seek_bar);
        }


        @Override
        public void bind(final Observacao observacao) {
            player.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    mp.reset();
                    if (extra == MediaPlayer.MEDIA_ERROR_IO){
                        Log.e("MEDIAPLAYER ERRO", "ERRO DE IO");
                    }
                    return false;
                }
            });


            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (state == STATE_STOPPED){
                        try{
                            player.setDataSource(observacao.getConteudo());
                            final Handler handler = new Handler();
                            final Runnable updateSeekbar = new Runnable() {
                                @Override
                                public void run() {
                                    seekBar.setProgress(player.getCurrentPosition());
                                    handler.postDelayed(this, 1000);
                                }
                            };
                            handler.postDelayed(updateSeekbar, 0);
                            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                @Override
                                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                    if(fromUser)
                                        player.seekTo(progress);
                                }

                                @Override
                                public void onStartTrackingTouch(SeekBar seekBar) {

                                }

                                @Override
                                public void onStopTrackingTouch(SeekBar seekBar) {

                                }
                            });

                            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    mp.reset();
                                    AudioViewHolder.this.play.setImageResource(R.drawable.play_black);
                                    handler.removeCallbacks(updateSeekbar);
                                    seekBar.setProgress(0);

                                }
                            });
                            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(final MediaPlayer mp) {
                                    AudioViewHolder.this.seekBar.setMax(mp.getDuration());

                                    mp.start();

                                    AudioViewHolder.this.play.setImageResource(R.drawable.pause_black);

                                }
                            });
                            player.prepareAsync();





                        }catch (Exception e){
                            Log.e("ERRO PLAYER", e.toString());
                        }

                    }else if (state == STATE_PAUSED){
                        player.start();
                    }
                    


                    if(player.isPlaying() && player != null){
                        player.pause();
                        state = STATE_PAUSED;
                    }



                }
            });
        }
    }


    public int addObservacao(Observacao observacao){
        this.observacoes.add(observacao);
        this.observacoes.trimToSize();
        for(int i = 0; i<observacoes.size(); i++)
            Log.d("DEBUG_ADAPTER", observacoes.get(i).toString());

        return this.observacoes.size() - 1;
    }


}
