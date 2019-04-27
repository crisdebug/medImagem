package com.example.samuel.medimagem;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FotosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FotosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FotosFragment extends Fragment implements ImagesLoadedCallback{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String IMAGES = "images";


    private ArrayList<Foto> fotos;
    private ImageAdapter adapter;
    private GridView grid_fotos;

    private OnFragmentInteractionListener mListener;
    private RemoveLoadingCallback callback;

    public FotosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
.
     * @return A new instance of fragment FotosFragment.
     */
    public static FotosFragment newInstance(ArrayList<Foto> fotos) {
        FotosFragment fragment = new FotosFragment();
        Bundle args = new Bundle();
        args.putSerializable(IMAGES, fotos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fotos = (ArrayList<Foto>) getArguments().getSerializable(IMAGES);
            adapter = new ImageAdapter(getActivity(), fotos, this);
        }
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fotos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        grid_fotos = view.findViewById(R.id.grade_fotos);

        grid_fotos.setAdapter(adapter);
        grid_fotos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Foto foto = fotos.get(position);
                mListener.onImageClicked(foto, position);
                Toast.makeText(getActivity(), ""+position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateImagem(boolean aceito, int position){
        adapter.updateBeingUsed(aceito,position);
        adapter.notifyDataSetChanged();
        grid_fotos.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener && context instanceof RemoveLoadingCallback) {
            mListener = (OnFragmentInteractionListener) context;
            callback = (RemoveLoadingCallback) context;
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
        public void onImageClicked(Foto foto, int position);
    }

    @Override
    public void imagesHasLoaded() {
        grid_fotos.setVisibility(View.VISIBLE);
        callback.removerLoading();
    }
}
