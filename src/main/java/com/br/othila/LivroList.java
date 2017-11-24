package com.br.othila;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mario on 24/11/2017.
 */

public class LivroList extends AppCompatActivity {


    @BindView(R.id.livro_lista)
    ListView lista;

    ConexaoDb conexaoDb = new ConexaoDb(LivroList.this);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livro);

        ButterKnife.bind(this);


        //Recebe parametros/valores da actvity inicio

        Intent intent = getIntent();
        Livro livro = (Livro) intent.getSerializableExtra("livro");

        conexaoDb.salvar(livro);

        //Função de Click simples para enviar o item para tela de cadastro para edicao

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Livro livro = (Livro) parent.getItemAtPosition(position);

                    Intent voltarParaCadastro = new Intent(LivroList.this, InicioActivity.class);
                    voltarParaCadastro.putExtra("livroAtual", livro);
                    startActivity(voltarParaCadastro);
                }
            });

        //Função de click longo para excluir item da lista


        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Livro livro = (Livro) parent.getItemAtPosition(position);

                conexaoDb.excluir(livro);
                atualiza();

                return true;
            }
        });


        Adaptador adapter = new Adaptador(this, conexaoDb.buscarTodos());
        lista.setAdapter(adapter);
    }

    public void atualiza(){
        Adaptador adapter = new Adaptador(this, conexaoDb.buscarTodos());
        lista.setAdapter(adapter);
    }

    @OnClick(R.id.btn_lista_fechar)
    public void fechar(){
        finish();
   }

}
