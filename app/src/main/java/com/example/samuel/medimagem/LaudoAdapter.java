package com.example.samuel.medimagem;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class LaudoAdapter extends RecyclerView.Adapter<LaudoAdapter.CampoViewHolder> {

    private ArrayList<Campo> campos;
    private Context context;

    public LaudoAdapter(ArrayList<Campo> campos, Context context){
        this.campos = campos;
        this.context = context;
    }

    @NonNull
    @Override
    public LaudoAdapter.CampoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View cardView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_campo, viewGroup, false);
        CampoViewHolder campoViewHolder = new CampoViewHolder(cardView);
        return campoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CampoViewHolder campoViewHolder, int i) {

        Campo campo = campos.get(i);
        if (campo.getFieldType().equals(Campo.TYPE_TEXT)){
            campoViewHolder.field_text.setVisibility(View.VISIBLE);
            campoViewHolder.field_text.setId(i);
            campoViewHolder.label.setText(campo.getLabel());
            Log.d("LABEL", campo.getLabel());
        }else if (campo.getFieldType().equals(Campo.TYPE_CHOICE)){
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, campo.getChoices());
            campoViewHolder.field_choice.setAdapter(spinnerArrayAdapter);
            campoViewHolder.field_choice.setVisibility(View.VISIBLE);
            campoViewHolder.field_choice.setId(i);
            campoViewHolder.label.setText(campo.getLabel());
            Log.d("LABEL", campo.getLabel());
        }else if (campo.getFieldType().equals(Campo.TYPE_RADIO)){
            final RadioButton[] rb = new RadioButton[campo.getChoices().size()];
            for (int j = 0; j < rb.length; j++){
                rb[j] = new RadioButton(context);
                rb[j].setText(campo.getChoices().get(j));
                rb[j].setId(j+100);
                campoViewHolder.field_radio.addView(rb[j]);
            }
            campoViewHolder.field_radio.setVisibility(View.VISIBLE);
            campoViewHolder.field_radio.setId(i);
            campoViewHolder.label.setText(campo.getLabel());
            Log.d("LABEL", campo.getLabel());
        }
    }

    @Override
    public int getItemCount() {
        return campos.size();
    }

    public class CampoViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView label;
        EditText field_text;
        Spinner field_choice;
        RadioGroup field_radio;

        public CampoViewHolder(View itemView){
            super(itemView);
            cv = itemView.findViewById(R.id.card);
            label = itemView.findViewById(R.id.label);
            field_text = itemView.findViewById(R.id.field_text);
            field_choice = itemView.findViewById(R.id.field_choice);
            field_radio = itemView.findViewById(R.id.field_radio);
        }

    }
}
