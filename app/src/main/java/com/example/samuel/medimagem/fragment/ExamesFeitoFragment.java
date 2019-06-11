package com.example.samuel.medimagem.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samuel.medimagem.model.Exam;
import com.example.samuel.medimagem.adapter.ExamesFeitosAdapter;
import com.example.samuel.medimagem.R;
import com.example.samuel.medimagem.database.ExameDAO;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnExameFeitoInteractionListener}
 * interface.
 */
public class ExamesFeitoFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_MEDICO_ID= "medico_id";
    // TODO: Customize parameters
    private int medico = 1;
    private ArrayList<Exam> listaExames;
    private OnExameFeitoInteractionListener mListener;
    private RecyclerView recyclerView;
    private ExamesFeitosAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ExamesFeitoFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ExamesFeitoFragment newInstance(int medicoID) {
        ExamesFeitoFragment fragment = new ExamesFeitoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MEDICO_ID, medicoID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            medico = getArguments().getInt(ARG_MEDICO_ID);
        }

        carregarExame();
    }

    private void carregarExame() {
        ExameDAO exameDAO = ExameDAO.getInstance(getActivity());
        exameDAO.abrir();
        listaExames = exameDAO.getExames(medico, 1);
        exameDAO.fechar();
    }

    public void atualizarLista(){
        AtualizarListaTask task = new AtualizarListaTask();
        task.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_examesfeito_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (medico <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, medico));
            }
            recyclerView.setAdapter(new ExamesFeitosAdapter(listaExames, mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnExameFeitoInteractionListener) {
            mListener = (OnExameFeitoInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnExameAgendadoInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnExameFeitoInteractionListener {
        // TODO: Update argument type and name
        void onExameFeitoInteraction(Exam exame);
    }

    private class AtualizarListaTask extends AsyncTask<Void, Void, ArrayList<Exam>> {

        @Override
        protected ArrayList<Exam> doInBackground(Void... voids) {
            ExamesFeitoFragment.this.carregarExame();
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Exam> aVoid) {
            adapter = new ExamesFeitosAdapter(listaExames, mListener);
            recyclerView.setAdapter(adapter);
            super.onPostExecute(aVoid);
        }
    }
}
