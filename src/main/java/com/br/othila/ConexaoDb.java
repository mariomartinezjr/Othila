package com.br.othila;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mario on 24/11/2017.
 */

public class ConexaoDb extends SQLiteOpenHelper {

    public static final String NOME_BANCO = "othilaDB";
    public static final String TABELA = "tb_livro";
    public static final int VERSAO_BANCO = 1;

    public ConexaoDb(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String sql = "CREATE TABLE tb_livro  (id integer PRIMARY KEY, titulo text,  paginas text,imagem blob)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE IF EXISTS tb_livro";
        db.execSQL(sql);
        onCreate(db);
    }

    //Métodos para salvar, consultar  e remover livros

    public List<Livro> buscarTodos(){
        SQLiteDatabase db = getReadableDatabase();

        List<Livro> livroList = new ArrayList<>();
        String sql = "SELECT * FROM tb_livro"  ;

        Cursor cursor = db.rawQuery(sql, null);

        if(cursor.moveToFirst()){
            do{

                Livro livro = new Livro();

                livro.setId(cursor.getLong(0));
                livro.setTitulo(cursor.getString(1));
                livro.setPaginas(cursor.getString(2));
                livro.setImagem(cursor.getBlob(3));

                livroList.add(livro);

            }while (cursor.moveToNext());
        }

        db.close();
        return livroList;
    }

    public void salvar(Livro livro){

        ContentValues values = new ContentValues();
        values.put("titulo", livro.getTitulo());
        values.put("paginas", livro.getPaginas());
        values.put("imagem", livro.getImagem());

        if(livro.getId() == null){
            adicionar(livro,values);
        } else {
            editar(livro,values);
        }
    }

    public void editar(Livro livro, ContentValues values){
        SQLiteDatabase db = this.getWritableDatabase();

        String[] arg = new String[1];
        arg[0] = livro.getId().toString();
        db.update(TABELA, values, "id = ?", arg);

        db.close();
    }


    public void adicionar(Livro livro, ContentValues values){
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABELA, null, values);
        db.close();
    }

    public void excluir(Livro livro){

        SQLiteDatabase db = getWritableDatabase();
        String[] arg = new String[1];
        arg[0] = livro.getId().toString();
        db.delete(TABELA,  "id = ?", arg);

        db.close();

    }
}