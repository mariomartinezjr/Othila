package com.br.othila;


import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mario on 24/11/2017.
 */

public class Repeating_activity  extends AppCompatActivity{

    @BindView(R.id.lalerta)
    TextView alerta;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.repeating_ativity_layout);
        ButterKnife.bind(this);



    }

    @OnClick(R.id.bCompleta)

    public void completaLembrete(){
        Intent irParaLista = new Intent(this, LivroList.class);

        startActivity(irParaLista);
    }
}