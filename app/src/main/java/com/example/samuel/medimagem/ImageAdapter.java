package com.example.samuel.medimagem;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends BaseAdapter {

    private Context c;
    private List<Foto> listaImagens;
    private ImagesLoadedCallback callback;

    public ImageAdapter(Context context, List<Foto> fotos, ImagesLoadedCallback callback){
        c = context;
        this.listaImagens = fotos;
        this.callback = callback;
    }


    @Override
    public int getCount() {
        return listaImagens.size();
    }

    @Override
    public Object getItem(int position) {
        return listaImagens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        final FrameLayout layout = (FrameLayout) parent.getParent();


        ImageView imageView;
        if (convertView == null){
            gridView = inflater.inflate(R.layout.grid_item, null);
            imageView = (ImageView) gridView.findViewById(R.id.foto);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8,8,8,8);
            Picasso.get().load("file://"+listaImagens.get(position).getImagePath()).resize(200, 200).into(imageView, new Callback() {
                @Override
                public void onSuccess() {
                    Log.d("PICASSO", "Imagem carregada");
//                    if (position+1 == listaImagens.size()){
//                        callback.imagesHasLoaded();
//                        Log.d("PICASSO", "Remover imagem");
//                    }
                }

                @Override
                public void onError(Exception e) {

                }
            });
            
            ImageView check = gridView.findViewById(R.id.check);
            if (listaImagens.get(position).isBeingUsed()){
                check.setVisibility(View.VISIBLE);
            }
        }else {
            gridView = convertView;
        }

        return gridView;
    }

    public void updateBeingUsed(boolean beingUsed, int position){
        listaImagens.get(position).setBeingUsed(beingUsed);

    }
}
