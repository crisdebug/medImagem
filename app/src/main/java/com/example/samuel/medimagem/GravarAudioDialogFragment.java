package com.example.samuel.medimagem;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.github.clans.fab.FloatingActionButton;

import java.io.File;

public class GravarAudioDialogFragment extends DialogFragment {

    private FloatingActionButton gravar;
    private Button salvar;
    private Button cancelar;
    private boolean gravando;
    private Exam exame;
    private MediaRecorder mediaRecorder;
    private String outputFile;

    public GravarAudioDialogFragment(){

    }

    public static GravarAudioDialogFragment getInstance(int count, Exam exame){
        GravarAudioDialogFragment fragment = new GravarAudioDialogFragment();
        Bundle args = new Bundle();
        args.putInt("count", count);
        args.putSerializable("exame", exame);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.gravar_audio_dialog, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gravar = view.findViewById(R.id.recording_button);
        this.exame = (Exam) getArguments().getSerializable("exame");

        gravar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        if (!isGravando()) {
                            try {
                                File medImagemDirectory = new File(Environment.getExternalStorageDirectory(), "MedImagem");
                                File exameDir = null;
                                File audioDir = null;
                                if (!medImagemDirectory.exists()) {
                                    if (!medImagemDirectory.mkdirs()) {
                                    }
                                } else {
                                    exameDir = new File(medImagemDirectory, exame.getNomePaciente().toUpperCase() + exame.getId());
                                    if (!exameDir.exists()) {
                                        if (!exameDir.mkdirs()) {
                                            Log.d("Diretorio", "Falha ao criar a Pasta");
                                        }
                                    }
                                }
                                if (exameDir != null) {
                                    audioDir = new File(audioDir.getPath(), "audio");
                                    if (!audioDir.exists()) {
                                        if (!audioDir.mkdirs()) {
                                            Log.d("Diretorio", "Falha ao criar a pasta de áudio");
                                        }
                                    }
                                }
                                outputFile = audioDir.getAbsolutePath() + File.separator + getArguments().getInt("count") + ".aac";
                                mediaRecorder = new MediaRecorder();
                                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
                                mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AAC_ADTS);
                                mediaRecorder.setOutputFile(outputFile);
                                mediaRecorder.prepare();
                                mediaRecorder.start();
                                setGravando(true);
                                salvar.setEnabled(false);
                                cancelar.setEnabled(false);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        if (isGravando()){
                            try{
                                mediaRecorder.stop();
                                setGravando(false);
                                salvar.setEnabled(true);
                                cancelar.setEnabled(true);
                            }catch (Exception e){
                              e.printStackTrace();
                            }
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
                mediaRecorder.release();
                mediaRecorder = null;
                dismiss();
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaRecorder.release();
                mediaRecorder = null;
                File delete = new File(outputFile);
                delete.delete();
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
