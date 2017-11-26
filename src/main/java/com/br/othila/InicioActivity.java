package com.br.othila;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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

    @BindView(R.id.user_image)
    ImageView imagem;

    private static final int CAMERA_REQUEST = 123;

// variavel para controlar adição ou edição

    Livro livroAtual = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        ButterKnife.bind(this);


    }

    @OnClick(R.id.user_image)
    public void changeImage(){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imagem.setImageBitmap(photo);

        } else {
            Toast.makeText(InicioActivity.this, "Imagem não carregada!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        livroAtual = (Livro) intent.getSerializableExtra("livroAtual");

        if(livroAtual != null){
            nomeEdit.setText(livroAtual.getTitulo());
            paginasEdit.setText(livroAtual.getPaginas());

            byte[] outimage = livroAtual.getImagem();
            ByteArrayInputStream imagestream = new ByteArrayInputStream(outimage);
            Bitmap imageBitmap = BitmapFactory.decodeStream(imagestream);
            imagem.setImageBitmap(imageBitmap);


        }else {
            nomeEdit.setText("");
            paginasEdit.setText("");
        }
    }

    public void lembrete  (int h , int m,int d,int mm, int a, String mensagem){

        Calendar calendar = Calendar.getInstance();

        if(h != 0  && m !=0){



       //calendar.set(Calendar.YEAR, a, Calendar.MONTH, me ,Calendar.DAY_OF_MONTH, d);

            calendar.set(Calendar.MONTH, mm -1);
            calendar.set(Calendar.YEAR, a);
            calendar.set(Calendar.DAY_OF_MONTH, d);
            calendar.set(Calendar.HOUR_OF_DAY , h);
            calendar.set(Calendar.MINUTE , m);


        Intent intent = new Intent(getApplicationContext(), Notification_reciever.class);
        intent.putExtra("mensagem", mensagem);
        PendingIntent pendingIntent =  PendingIntent.getBroadcast(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);



        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY,pendingIntent);

       }else {
            h=0;
        }
    }

    //Criando o metodo de click do botão salvar

    @OnClick(R.id.btn_cadastro_salvar)
    public void salvar(){
        //Retira o titulo/paginas digitado no Edit Text



        String titulo = nomeEdit.getText().toString();
        String paginas = paginasEdit.getText().toString();

        int hr = Integer.parseInt(hora.getText().toString());
        int mi = Integer.parseInt(minuto.getText().toString());
        int di = Integer.parseInt(dia.getText().toString());
        int mm = Integer.parseInt(mes.getText().toString());
        int an = Integer.parseInt(ano.getText().toString());

        // faz a verificação para nova adição ou edição e instancia livroAtual

        if(livroAtual == null){
            livroAtual = new Livro();
        }

        //seta atributos de livroAtual

        if(titulo != null && !titulo.isEmpty()){


            lembrete (hr,mi,di,mm,an, titulo);

            livroAtual.setTitulo(titulo);
            livroAtual.setPaginas(paginas) ;

            Bitmap bitmap = ((BitmapDrawable) imagem.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
            livroAtual.setImagem(stream.toByteArray());


        // intent para ir para lista

            Intent irParaLista = new Intent(InicioActivity.this, LivroList.class);
            irParaLista.putExtra("livro", livroAtual);

            //volta livroAtual para null para proxima iteração

            livroAtual = null;


            startActivity(irParaLista);

            //caso o usuario não informe o titulo do livre sera mostrada uma mensagem
        } else {
            Toast.makeText(InicioActivity.this, "Informe o Titulo do livro !", Toast.LENGTH_SHORT).show();
        }
    }
}
