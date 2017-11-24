package com.br.othila;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InicioActivity extends AppCompatActivity {

//Mapeando a interface com os objetos java

    @BindView(R.id.ed_cadastro_titulo)
    EditText nomeEdit;

    @BindView(R.id.ed_cadastro_paginas)
    EditText paginasEdit;

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

    //Criando o metodo de click do botão salvar

    @OnClick(R.id.btn_cadastro_salvar)
    public void salvar(){
        //Retira o titulo/paginas digitado no Edit Text

        String titulo = nomeEdit.getText().toString();
        String paginas = paginasEdit.getText().toString();


        // faz a verificação para nova adição ou edição e instancia livroAtual

        if(livroAtual == null){
            livroAtual = new Livro();
        }

        //seta atributos de livroAtual

        if(titulo != null && !titulo.isEmpty()){
            livroAtual.setTitulo(titulo);
            livroAtual.setPaginas(paginas) ;

            livroAtual.setImagem(R.drawable.user1_image);

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
