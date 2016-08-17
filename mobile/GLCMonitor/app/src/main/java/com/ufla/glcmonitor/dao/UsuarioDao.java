package com.ufla.glcmonitor.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufla.glcmonitor.conection.LocalDatabaseConection;
import com.ufla.glcmonitor.modelo.Usuario;

public class UsuarioDao {

    private SQLiteDatabase db;

    public UsuarioDao(Context context) {
        LocalDatabaseConection localDatabase = new LocalDatabaseConection(context);
        this.db = localDatabase.getWritableDatabase();
    }

    public void insertLocal(Usuario usuario){

        ContentValues cValues = new ContentValues();
        cValues.put("login",usuario.getLogin());
        cValues.put("senha",usuario.getSenha());
        cValues.put("nome",usuario.getNome());
        cValues.put("telefone",usuario.getTelefone());
        cValues.put("email",usuario.getEmail());
        cValues.put("rg",usuario.getRg());
        cValues.put("cpf",usuario.getCpf());

       /* ContentValues não suporta enum e tipo data
        cValues.put("sexo",usuario.getSexo());
        cValues.put("dataCadastramento",usuario.getDataDeCadastramento());       */

        db.insert("usuario",null,cValues);
    }

    public void updateLocal(Usuario usuario){
        deleteLocal(usuario);
        insertLocal(usuario);
    }

    public void deleteLocal(Usuario usuario){
        db.delete("usuario", " login = '" + usuario.getLogin() + "';" , null);
    }

    public Usuario buscaLocal(String login){
        String[] colunas = new String[]{"login","senha","nome","telefone","email","rg","cpf"};
        Cursor cursor = db.query("usuario",colunas," login = '" + login + "';",null,null,null,null);

        Usuario usuario = new Usuario();
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            usuario.setLogin(cursor.getString(0));
            usuario.setSenha(cursor.getString(1));
            usuario.setNome(cursor.getString(2));
            usuario.setTelefone(cursor.getLong(3));
            usuario.setEmail(cursor.getString(4));
            usuario.setRg(cursor.getLong(5));
            usuario.setCpf(cursor.getLong(6));
        }
        return usuario;
    }


}
