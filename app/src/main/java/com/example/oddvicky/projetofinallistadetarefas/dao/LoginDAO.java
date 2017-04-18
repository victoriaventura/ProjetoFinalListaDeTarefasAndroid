package com.example.oddvicky.projetofinallistadetarefas.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.oddvicky.projetofinallistadetarefas.model.Login;

public class LoginDAO {

    private DBOpenHelper banco;
    public LoginDAO(Context context) {
        banco = new DBOpenHelper(context);
    }

    public static final String LOGIN_TABLE = "login";
    public static final String USER_COLUMN = "username";
    public static final String PW_COLUMN = "password";

    public Login getByUsername(String username) {
        SQLiteDatabase db = banco.getReadableDatabase();
        String columns[] = { USER_COLUMN, PW_COLUMN};
        String where = "username = " + "\"" + username + "\"";
        Cursor cursor = db.query(true, LOGIN_TABLE, columns, where, null, null,
                null, null, null);
        Login login = null;
        if(cursor != null && cursor.getCount() >= 1)
        {
            cursor.moveToFirst();
            login = new Login();
            login.setUsername(cursor.getString(cursor.getColumnIndex(USER_COLUMN)));
            login.setPassword(cursor.getString(cursor.getColumnIndex(PW_COLUMN)));
        }
        return login;
    }

    public String addUser(Login login){
        long result;
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_COLUMN, login.getUsername());
        values.put(PW_COLUMN, login.getPassword());
        result = db.insert(LOGIN_TABLE, null, values);
        db.close();
        if(result == -1) {
            return "Error trying to insert user!";
        } else {
            return "User successfully inserted!";
        }
    }
}
