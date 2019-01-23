package com.example.samuel.medimagem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class LaudoActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mlayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laudo);
        mRecyclerView = findViewById(R.id.laudo_lista);
        final HashMap<String, String> respostas = new HashMap<>();


        mlayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mlayoutManager);


        final ArrayList<Campo> campos = Campo.gerarLista();

        mAdapter = new LaudoAdapter(campos, this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        Button salvar = findViewById(R.id.salvar);
        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < campos.size(); i++){
                    View view = mRecyclerView.getLayoutManager().findViewByPosition(i);
                    View field = view.findViewById(i);
                    TextView label = view.findViewById(R.id.label);
                    String resposta;
                    if (field instanceof EditText){
                        EditText editText = (EditText) field;
                        resposta = editText.getText().toString();
                        respostas.put(String.valueOf(label.getText()), resposta);
                    }else if (field instanceof Spinner){
                        Spinner spinner = (Spinner) field;
                        resposta = spinner.getSelectedItem().toString();
                        respostas.put(String.valueOf(label.getText()), resposta);
                    }else if (field instanceof RadioGroup){
                        RadioGroup rg = (RadioGroup) field;
                        RadioButton  rb = field.findViewById(rg.getCheckedRadioButtonId());
                        resposta = String.valueOf(rb.getText());
                        respostas.put(String.valueOf(label.getText()), resposta);

                    }
                }
            }
        });
    }
}
