package com.br.othila;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mario on 24/11/2017.
 */

public class Adaptador extends ArrayAdapter<Livro> {


    public Adaptador(Context context, List<Livro> livroList) {
        super(context, R.layout.activity_item_lista, livroList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final ViewHolder viewHolder;

        if(convertView == null){
            convertView = View.inflate(getContext(), R.layout.activity_item_lista, null);

            viewHolder = new ViewHolder();
            ButterKnife.bind(viewHolder, convertView);
            convertView.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Livro livro = getItem(position);

        if(livro != null){
            viewHolder.titulo.setText(livro.getTitulo());
            viewHolder.paginas.setText(livro.getPaginas());


            byte[] outimage = livro.getImagem();
            ByteArrayInputStream imagestream = new ByteArrayInputStream(outimage);
            Bitmap imageBitmap = BitmapFactory.decodeStream(imagestream);
            viewHolder.imagem.setImageBitmap(imageBitmap);
        }

        return convertView;
    }

    public class ViewHolder{
        @BindView(R.id.tv_item_titulo)
        TextView titulo;

        @BindView(R.id.tv_item_pagina)
        TextView paginas;

        @BindView(R.id.image)
        ImageView imagem;
    }

}