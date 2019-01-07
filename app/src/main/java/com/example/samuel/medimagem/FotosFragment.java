package com.example.samuel.medimagem;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
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
public class FotosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String IMAGES = "images";


    // TODO: Rename and change types of parameters
    private ArrayList<Bitmap> images;
    private ImageAdapter adapter;

    private OnFragmentInteractionListener mListener;

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
    // TODO: Rename and change types and number of parameters
    public static FotosFragment newInstance(ArrayList<Bitmap> bitmaps) {
        FotosFragment fragment = new FotosFragment();
        Bundle args = new Bundle();
        args.putSerializable(IMAGES, bitmaps);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            images = (ArrayList<Bitmap>) getArguments().getSerializable(IMAGES);
            adapter = new ImageAdapter(getActivity(), images);
        }
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
        GridView grid_fotos = view.findViewById(R.id.grade_fotos);
        grid_fotos.setAdapter(adapter);
        grid_fotos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView imagemClicada = (ImageView) view;
                Bitmap bitmap = ((BitmapDrawable) imagemClicada.getDrawable()).getBitmap();
                mListener.onImageClicked(bitmap);
                Toast.makeText(getActivity(), ""+position, Toast.LENGTH_SHORT).show();
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
        public void onImageClicked(Bitmap bitmap);
    }
}
