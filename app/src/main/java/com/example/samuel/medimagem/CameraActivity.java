package com.example.samuel.medimagem;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;

public class CameraActivity extends AppCompatActivity {

    private ImageSurfaceView mImageSurfaceView;
    private Camera camera;

    private FrameLayout cameraPreviewLayout;
    private FrameLayout flash;

    private Exam exame;
    ArrayList<Foto> fotos;
    SpeechRecognizer mSpeechRecognizer;
    Intent mSpeechRecognizerIntent;
    AudioManager audioManager;
    int current_volume;


    private int count = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_camera);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        current_volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        exame = (Exam) getIntent().getSerializableExtra("exame");
        count = getIntent().getIntExtra("count", 0);
        flash = findViewById(R.id.flash);

        fotos = (ArrayList<Foto>) getIntent().getSerializableExtra("fotos");

        cameraPreviewLayout = findViewById(R.id.camera_preview);
        camera = null;
        camera = checkDeviceCamera();

        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {

            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                Log.i("DEBUG", "FALOU");

                if (matches.get(0).equals("foto")){
                    tirarFoto();
                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });



        ImageButton captureButton = findViewById(R.id.tirar_foto);
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tirarFoto();

            }
        });

        ImageButton voltar = findViewById(R.id.voltar);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageButton proximo = findViewById(R.id.preencher);
        proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CameraActivity.this, FotosActivity.class);
                intent.putExtra("count", count);
                intent.putExtra("exame", exame);
                intent.putExtra("fotos", fotos);
                startActivity(intent);
                finish();


            }
        });

    }

    private void tirarFoto() {
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0,  AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
        mSpeechRecognizer.stopListening();
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, current_volume, AudioManager.FLAG_PLAY_SOUND);
        Log.i("DEBUG", "Parou de escutar");
        flash.setVisibility(View.VISIBLE);
        AlphaAnimation fade = new AlphaAnimation(1, 0);
        fade.setDuration(50);
        fade.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                flash.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        flash.startAnimation(fade);
        camera.takePicture(null, null, pictureCallback);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0,  AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, current_volume, AudioManager.FLAG_PLAY_SOUND);
        Log.i("DEBUG", "Escutando");
    }

    @Override
    protected void onResume() {
        try{
            camera = null;
            camera = checkDeviceCamera();
            if (camera != null){
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0,  AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, current_volume, AudioManager.FLAG_PLAY_SOUND);
                Log.i("DEBUG", "Escutando");
                mImageSurfaceView = null;
                mImageSurfaceView = new ImageSurfaceView(this, camera);
                cameraPreviewLayout.addView(mImageSurfaceView);
                Log.i("DEBUG", "camera adicionada");
            }
        } catch (Exception e){
            e.printStackTrace();
            finish();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0,  AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
        mSpeechRecognizer.stopListening();
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, current_volume, AudioManager.FLAG_PLAY_SOUND);

        if (camera != null){
            camera.stopPreview();
            camera.release();
            camera = null;
            mImageSurfaceView = null;
      }


        super.onPause();

    }



    Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            SalvarTask salvarTask = new SalvarTask();
            salvarTask.execute(data);
        }
    };

    private Camera checkDeviceCamera(){
        Camera mCamera = null;
        camera = null;
        try{
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                mCamera = Camera.open();
                Log.i("DEBUG", "camera aberta");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return mCamera;
    }
    private class SalvarTask extends AsyncTask<byte[], Void, Void>{

        @Override
        protected Void doInBackground(byte[]... bytes) {
            File medImagemDirectory = new File(Environment.getExternalStorageDirectory(), "MedImagem");
            File mediaStorageDir = null;
            if (!medImagemDirectory.exists()){
                if (!medImagemDirectory.mkdirs()){
                    finish();
                }
            }else{
                mediaStorageDir = new File(medImagemDirectory, exame.getNomePaciente().toUpperCase()+exame.getId());
                if(!mediaStorageDir.exists()){
                    if (!mediaStorageDir.mkdirs()){
                        Log.d("Diretorio", "Falha ao criar a Pasta");
                        return null;
                    }
                }
            }
            if (mediaStorageDir != null) {
                File mediaFile;
                mediaFile = new File(mediaStorageDir.getPath() + File.separator + count + ".jpg");
                OutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(mediaFile);
                    outputStream.write(bytes[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (outputStream != null) {
                            outputStream.close();
                            Log.d("Fotos", "Foto salva");
                            count++;
                            camera.startPreview();


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 1){
            if (resultCode == Activity.RESULT_OK){
                count = data.getIntExtra("count", 0);
            }
        }
    }
}
