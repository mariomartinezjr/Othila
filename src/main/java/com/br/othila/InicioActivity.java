package com.br.othila;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InicioActivity extends AppCompatActivity {

//Mapeando a interface com os objetos java

    @BindView(R.id.ed_cadastro_titulo)
    EditText nomeEdit;

    @BindView(R.id.ed_cadastro_paginas)
    EditText paginasEdit;

    @BindView(R.id.edHora)
            EditText hora;

    @BindView(R.id.edminuto)
            EditText minuto;

    @BindView(R.id.edano)
            EditText ano;

    @BindView(R.id.edmes)
            EditText mes;

    @BindView(R.id.eddia)
            EditText dia;

// variavel para controlar adição ou edição

    Livro livroAtual = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        ButterKnife.bind(this);


    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        livroAtual = (Livro) intent.getSerializableExtra("livroAtual");

        if(livroAtual != null){
            nomeEdit.setText(livroAtual.getTitulo());
            paginasEdit.setText(livroAtual.getPaginas());



        }else {
            nomeEdit.setText("");
            paginasEdit.setText("");
        }
    }

    public void lembrete(String h , String m,String d,String me, String a, String mensagem){

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, Integer.parseInt(a), Calendar.MONTH, Integer.parseInt(me) ,Calendar.DAY_OF_MONTH, Integer.parseInt(d));

        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(h));
        calendar.set(Calendar.MINUTE, Integer.parseInt(m));


        Intent intent = new Intent(getApplicationContext(), Notification_reciever.class);
        intent.putExtra("mensagem", mensagem);
        PendingIntent pendingIntent =  PendingIntent.getBroadcast(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);



        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY,pendingIntent);

    }

    //Criando o metodo de click do botão salvar

    @OnClick(R.id.btn_cadastro_salvar)
    public void salvar(){
        //Retira o titulo/paginas digitado no Edit Text



        String titulo = nomeEdit.getText().toString();
        String paginas = paginasEdit.getText().toString();

        String hr = hora.getText().toString();
        String mi = minuto.getText().toString();
        String di = dia.getText().toString();
        String mm = mes.getText().toString();
        String an = ano.getText().toString();

        // faz a verificação para nova adição ou edição e instancia livroAtual

        if(livroAtual == null){
            livroAtual = new Livro();
        }

        //seta atributos de livroAtual

        if(titulo != null && !titulo.isEmpty()){
            livroAtual.setTitulo(titulo);
            livroAtual.setPaginas(paginas) ;

            livroAtual.setImagem(R.mipmap.ic_launcher);

// intent para ir para lista

            Intent irParaLista = new Intent(InicioActivity.this, LivroList.class);
            irParaLista.putExtra("livro", livroAtual);

            //volta livroAtual para null para proxima iteração

            livroAtual = null;

            if(hr != null && mi != null){

                lembrete(hr,mi,di,mm,an, titulo);
            }

            startActivity(irParaLista);

        //caso o usuario não informe o titulo do livre sera mostrada uma mensagem
        } else {
            Toast.makeText(InicioActivity.this, "Informe o Titulo do livro !", Toast.LENGTH_SHORT).show();
        }
    }
}
