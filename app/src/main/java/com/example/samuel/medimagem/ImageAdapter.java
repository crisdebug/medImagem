package com.example.samuel.medimagem;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.List;

public class ImageAdapter extends BaseAdapter {

    private Context c;
    private List<Foto> listaImagens;

    public ImageAdapter(Context context, List<Foto> fotos){
        c = context;
        this.listaImagens = fotos;
    }


    @Override
    public int getCount() {
        return listaImagens.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        ImageView imageView;
        if (convertView == null){
            gridView = new View(c);
            gridView = inflater.inflate(R.layout.grid_item, null);
            imageView = (ImageView) gridView.findViewById(R.id.foto);
            imageView.setLayoutParams(new RelativeLayout.LayoutParams(100, 100));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8,8,8,8);
            Bitmap imagem = BitmapFactory.decodeFile(listaImagens.get(position).getImagePath().getAbsolutePath());
            imageView.setImageBitmap(imagem);
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
