package com.example.samuel.medimagem;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;

public class LoginActivity extends AppCompatActivity implements TextWatcher {

    private TextView erro_user;
    private TextView erro_senha;
    private EditText userED;
    private EditText senhaED;
    private boolean editedU = false;
    private boolean editedS = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle("Login");

        userED = findViewById(R.id.user_ed);
        senhaED = findViewById(R.id.senha_ed);
        Button loginB = findViewById(R.id.login_button);
        erro_user = findViewById(R.id.erro_usuario);
        erro_senha = findViewById(R.id.erro_senha);

        userED.addTextChangedListener(this);
        senhaED.addTextChangedListener(this);

        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.login();
            }
        });

        senhaED.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND){
                    LoginActivity.this.login();
                    handled = true;
                }
                return handled;
            }
        });

        FFmpeg ffmpeg = FFmpeg.getInstance(this);
        try {
            ffmpeg.loadBinary(new LoadBinaryResponseHandler() {

                @Override
                public void onStart() {}

                @Override
                public void onFailure() {}

                @Override
                public void onSuccess() {}

                @Override
                public void onFinish() {}
            });
        } catch (FFmpegNotSupportedException e) {
            finish();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (editedU){
            erro_user.setVisibility(View.GONE);
            editedU = false;
        }
        if (editedS){
            erro_senha.setVisibility(View.GONE);
            editedS = false;
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void login(){
        String username = userED.getText().toString();
        String senha = senhaED.getText().toString();

        UsuarioDAO usuarioDAO = UsuarioDAO.getInstance(LoginActivity.this);
        usuarioDAO.abrir();
        int medicoID = usuarioDAO.authenticate(username, senha);
        if (medicoID > 0){
            Intent intent = new Intent(LoginActivity.this, ExameActivity.class);
            intent.putExtra("medico_id", medicoID);
            startActivity(intent);
            finish();
        }else if(medicoID == -1){
            erro_user.setVisibility(View.VISIBLE);
            editedU = true;

        }else if (medicoID == -2){
            erro_senha.setVisibility(View.VISIBLE);
            editedS = true;
        }
        usuarioDAO.fechar();
    }
}
