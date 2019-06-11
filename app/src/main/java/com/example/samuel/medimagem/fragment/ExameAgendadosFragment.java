package com.example.samuel.medimagem.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samuel.medimagem.model.Exam;
import com.example.samuel.medimagem.adapter.ExamesAgendadosAdapter;
import com.example.samuel.medimagem.R;
import com.example.samuel.medimagem.util.RecyclerSectionItemDecoration;
import com.example.samuel.medimagem.database.ExameDAO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnExameAgendadoInteractionListener}
 * interface.
 */
public class ExameAgendadosFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnExameAgendadoInteractionListener mListener;
    private ArrayList<Exam> listaExames;
    private int medico;
    private ExamesAgendadosAdapter adapter;
    private RecyclerView recyclerView;
    private boolean canRunBG = true;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ExameAgendadosFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ExameAgendadosFragment newInstance(int medicoID) {
        ExameAgendadosFragment fragment = new ExameAgendadosFragment();
        Bundle args = new Bundle();
        args.putInt("medico", medicoID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            medico = getArguments().getInt("medico");
        }

        carregarExame();
        logCompare();

        setRetainInstance(true);
    }

    private void carregarExame() {
        ExameDAO exameDAO = ExameDAO.getInstance(getActivity());
        exameDAO.abrir();
        listaExames = exameDAO.getExames(medico, 0);
        exameDAO.fechar();
    }

    public void atualizarLista(){
        AtualizarListaTask task = new AtualizarListaTask();
        task.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.exames_agendados_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            adapter = new ExamesAgendadosAdapter(listaExames, mListener);

            recyclerView.setAdapter(adapter);

            RecyclerSectionItemDecoration sectionItemDecoration = new RecyclerSectionItemDecoration(getResources().getDimensionPixelSize(R.dimen.header), true, getSectionCallback(listaExames));



        }
        Log.i("DEBUG", "view created");


        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnExameAgendadoInteractionListener) {
            mListener = (OnExameAgendadoInteractionListener) context;
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

    @Override
    public void onPause() {
        super.onPause();
        Log.i("DEBUG", "pausado");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("DEBUG", "iniciado");
    }


    public void mudarFeito(int position){
        ExameDAO dao = ExameDAO.getInstance(getActivity());
        dao.abrir();
        dao.atualizarFeito(listaExames.get(position), true);
        dao.fechar();
        listaExames.remove(position);
        atualizarLista();

    }

    @Override
    public void onResume() {
        super.onResume();
        atualizarLista();
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
    public interface OnExameAgendadoInteractionListener {
        // TODO: Update argument type and name
        void onExameAgendadoInteraction(Exam exam);
        void onExameAgendadoLongInteraction(int position);
    }

    private RecyclerSectionItemDecoration.SectionCallback getSectionCallback(final ArrayList<Exam> listaExames){
        return new RecyclerSectionItemDecoration.SectionCallback() {
            @Override
            public boolean isSection(int position) {
                return position == 0 || listaExames.get(position).getHoraData().compareTo(listaExames.get(position-1).getHoraData()) != 0;
            }

            @Override
            public CharSequence getSectionHeader(int position) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("pt", "BR"));

                return dateFormat.format(listaExames.get(position).getHoraData());
            }
        };
    }

    private void logCompare(){
        for (int i = 0; i<listaExames.size(); i++){
                if (i != 0){
                    Log.i("DEBUG", String.valueOf(listaExames.get(i).getHoraData().getDate() - listaExames.get(i).getHoraData().getDate()));
                }
        }
    }

    private class AtualizarListaTask extends AsyncTask<Void, Void, ArrayList<Exam>>{

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected ArrayList<Exam> doInBackground(Void... voids) {
            if (canRunBG){
                canRunBG = false;
                ExameAgendadosFragment.this.carregarExame();
                canRunBG = true;
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Exam> aVoid) {

            adapter = new ExamesAgendadosAdapter(listaExames, mListener);
            recyclerView.setAdapter(adapter);

            super.onPostExecute(aVoid);
        }
    }


}
