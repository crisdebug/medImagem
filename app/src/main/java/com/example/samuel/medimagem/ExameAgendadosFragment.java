package com.example.samuel.medimagem;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

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

        ExameDAO exameDAO = ExameDAO.getInstance(getActivity());
        exameDAO.abrir();
        listaExames = exameDAO.getExames(medico, 0);
        exameDAO.fechar();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exames_agendados_list, container, false);

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

        }
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
        listaExames.remove(position);
        recyclerView.removeViewAt(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, listaExames.size());

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
    }
}
