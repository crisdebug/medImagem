package com.example.samuel.medimagem;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class CameraActivity extends AppCompatActivity {

    private ImageSurfaceView mImageSurfaceView;
    private Camera camera;

    private FrameLayout cameraPreviewLayout;


    private int count = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_camera);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        cameraPreviewLayout = findViewById(R.id.camera_preview);

        camera = checkDeviceCamera();
        mImageSurfaceView = new ImageSurfaceView(this, camera);
        cameraPreviewLayout.addView(mImageSurfaceView);

        Button captureButton = findViewById(R.id.tirar_foto);
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.takePicture(null, null, pictureCallback);

            }
        });

        Button voltar = findViewById(R.id.voltar);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button proximo = findViewById(R.id.preencher);
        proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CameraActivity.this, FotosActivity.class));
            }
        });

    }

    private Camera checkDeviceCamera(){
        Camera mCamera = null;
        try{
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                mCamera = Camera.open();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return mCamera;
    }

    Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            SalvarTask salvarTask = new SalvarTask();
            salvarTask.execute(data);
        }
    };

    private class SalvarTask extends AsyncTask<byte[], Void, Void>{

        @Override
        protected Void doInBackground(byte[]... bytes) {
            File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "MedImagem");
            if(!mediaStorageDir.exists()){
                if (!mediaStorageDir.mkdirs()){
                    Log.d("Diretorio", "Falha ao criar a Pasta");
                    return null;
                }
            }
            File mediaFile;
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + count + ".jpg");
            OutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(mediaFile);
                outputStream.write(bytes[0]);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try{
                    if(outputStream != null){
                        outputStream.close();
                        Log.d("Fotos", "Foto salva");
                        count++;
                        camera.startPreview();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            return null;
        }
    }
}
