package com.example.samuel.medimagem;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

public class ImageAdapter extends BaseAdapter {

    private Context c;
    private List<Bitmap> listaImagens;

    public ImageAdapter(Context context, List<Bitmap> listaImagens){
        c = context;
        this.listaImagens = listaImagens;
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
        ImageView imageView;
        if (convertView == null){
            imageView = new ImageView(c);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8,8,8,8);
        }else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageBitmap(listaImagens.get(position));


        return imageView;
    }
}
