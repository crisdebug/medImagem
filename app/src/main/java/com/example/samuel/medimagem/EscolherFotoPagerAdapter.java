package com.example.samuel.medimagem;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EscolherFotoPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<Foto> fotos;

    public EscolherFotoPagerAdapter(Context context, List<Foto> fotos){
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.fotos = fotos;
    }

    @Override
    public int getCount() {
        return fotos.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = inflater.inflate(R.layout.escolher_foto_item, container, false);
        ImageView imageView = itemView.findViewById(R.id.foto_escolher);
        Picasso.get().load("file://"+fotos.get(position).getImagePath()).noFade().resize(400, 400)
                .into(imageView);
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
