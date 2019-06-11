package com.example.samuel.medimagem.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.samuel.medimagem.adapter.EscolherFotoPagerAdapter;
import com.example.samuel.medimagem.model.Foto;
import com.example.samuel.medimagem.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EscolherFotoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EscolherFotoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EscolherFotoFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String IMAGEM = "imagem";
    private static final String POSITION = "position";

    private ArrayList<Foto> listaFotos;
    private int position;

    private OnFragmentInteractionListener mListener;

    public EscolherFotoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment EscolherFotoFragment.
     */
    public static EscolherFotoFragment newInstance(ArrayList<Foto> listaFotos, int position) {
        EscolherFotoFragment fragment = new EscolherFotoFragment();
        Bundle args = new Bundle();
        args.putSerializable(IMAGEM, listaFotos);
        args.putInt(POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            listaFotos = (ArrayList<Foto>) getArguments().getSerializable(IMAGEM);
            position = getArguments().getInt(POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_escolher_foto, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager pager = view.findViewById(R.id.imagem_pager);
        EscolherFotoPagerAdapter adapter = new EscolherFotoPagerAdapter(getActivity(), listaFotos);
        pager.setAdapter(adapter);
        pager.setCurrentItem(position);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                changePosition(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        Button negarBt = view.findViewById(R.id.nao);
        Button aceitarBt = view.findViewById(R.id.sim);
        negarBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonPressed(false, position);
            }
        });
        aceitarBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonPressed(true, position);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    private void changePosition(int position){
        this.position = position;

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onButtonPressed(boolean aceito, int position);
    }
}
