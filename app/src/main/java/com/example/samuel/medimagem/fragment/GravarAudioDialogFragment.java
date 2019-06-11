package com.example.samuel.medimagem.fragment;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.samuel.medimagem.model.Exam;
import com.example.samuel.medimagem.model.Observacao;
import com.example.samuel.medimagem.R;
import com.example.samuel.medimagem.activity.ObservacoesActivity;
import com.github.clans.fab.FloatingActionButton;
import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.lassana.recorder.AudioRecorder;
import com.github.lassana.recorder.AudioRecorderBuilder;

import java.io.File;

public class GravarAudioDialogFragment extends DialogFragment {

    private FloatingActionButton gravar;
    private Button salvar;
    private Button cancelar;
    private TextView textoGravando;
    private boolean gravando;
    private Exam exame;
    AudioRecorder recorder;
    private String outputFile;
    private String audioPath;
    int record_count;

    public GravarAudioDialogFragment(){

    }

    public static GravarAudioDialogFragment getInstance(Exam exame, int record_count){
        GravarAudioDialogFragment fragment = new GravarAudioDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("exame", exame);
        args.putInt("record_count", record_count);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.gravar_audio_dialog, container, false);
    }

        @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gravar = view.findViewById(R.id.record_button);
        this.exame = (Exam) getArguments().getSerializable("exame");
        this.record_count = getArguments().getInt("record_count");
        textoGravando = view.findViewById(R.id.text_gravar_audio);
        audioPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator+ "MedImagem"+ File.separator + exame.getNomePaciente().toUpperCase() + exame.getId() + File.separator+ "audio";
        outputFile = audioPath + File.separator + record_count + "_TEMP"+ ".mp4";
        recorder = AudioRecorderBuilder.with(getActivity()).fileName(outputFile).config(AudioRecorder.MediaRecorderConfig.DEFAULT).loggable().build();

        gravar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        if (!isGravando()) {
                                recorder.start(new AudioRecorder.OnStartListener() {
                                    @Override
                                    public void onStarted() {
                                        setGravando(true);
                                        salvar.setEnabled(false);
                                        cancelar.setEnabled(false);
                                        textoGravando.setText("Gravando...");
                                    }

                                    @Override
                                    public void onException(Exception e) {
                                        e.printStackTrace();
                                        Log.d("DEBUG RECORD", e.getMessage());
                                    }
                                });


                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        if (isGravando()){

                                recorder.pause(new AudioRecorder.OnPauseListener() {
                                    @Override
                                    public void onPaused(String activeRecordFileName) {
                                        setGravando(false);
                                        salvar.setEnabled(true);
                                        cancelar.setEnabled(true);
                                        textoGravando.setText("Toque e segure no botão para gravar o áudio");
                                    }

                                    @Override
                                    public void onException(Exception e) {
                                        e.printStackTrace();
                                    }
                                });

                        }
                        break;
                }
                return false;
            }
        });

        salvar = view.findViewById(R.id.salvar_gravacao);
        cancelar = view.findViewById(R.id.cancelar_gravacao);

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recorder = null;
                FFmpeg fFmpeg = FFmpeg.getInstance(getActivity());
                try {
                    fFmpeg.execute(new String[]{"-i" ,outputFile , "-vn", audioPath + File.separator + record_count+ ".wav"}, new ExecuteBinaryResponseHandler(){
                        @Override
                        public void onSuccess(String message) {
                            super.onSuccess(message);
                            Log.d("FFMPEG", message);
                            File delete = new File(outputFile);
                            if(delete.exists()){
                                delete.delete();

                            }
                        }


                        @Override
                        public void onFailure(String message) {
                            super.onFailure(message);
                            Log.d("FFMPEG", message);

                        }
                    });
                }catch (FFmpegCommandAlreadyRunningException e){
                    e.printStackTrace();
                }

                if(getActivity() instanceof ObservacoesActivity){
                    ((ObservacoesActivity) getActivity()).updateCount();
                    Observacao observacao = new Observacao();
                    observacao.setTipo(Observacao.TIPO_AUDIO);
                    observacao.setConteudo(audioPath + File.separator + record_count+ ".wav");
                    observacao.setExame(exame);
                    ((ObservacoesActivity) getActivity()).addObservacao(observacao);
                }
                recorder = null;


                dismiss();
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File delete = new File(outputFile);
                if(delete.exists()){
                    delete.delete();
                }
                recorder = null;
                dismiss();
            }
        });
    }

    public boolean isGravando() {
        return gravando;
    }

    public void setGravando(boolean gravando) {
        this.gravando = gravando;
    }

}
