package com.example.oddvicky.projetofinallistadetarefas.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.oddvicky.projetofinallistadetarefas.model.Category;

import java.util.LinkedList;
import java.util.List;

public class CategoryDAO {

    private DBOpenHelper banco;

    public CategoryDAO(Context context) {
        banco = new DBOpenHelper(context);
    }

    public static final String CATEGORY_TABLE = "category";

    public static final String ID_COLUMN = "id";
    public static final String NAME_COLUMN = "name";

    public List<Category> getAll() {
        List<Category> categories = new LinkedList<>();

        String query = "SELECT  * FROM " + CATEGORY_TABLE + " ORDER BY " + CategoryDAO.NAME_COLUMN  + " ASC";;

        SQLiteDatabase db = banco.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Category category = null;

        if (cursor.moveToFirst()) {
            do {
                category = new Category();
                category.setId(cursor.getInt(cursor.getColumnIndex(ID_COLUMN)));
                category.setName(cursor.getString(cursor.getColumnIndex(NAME_COLUMN)));
                categories.add(category);
            } while (cursor.moveToNext());
        }
        return categories;
    }

    public Category getBy(int id) {
        SQLiteDatabase db = banco.getReadableDatabase();
        String columns[] = { ID_COLUMN, NAME_COLUMN};
        String where = "id = " + id;
        Cursor cursor = db.query(true, CATEGORY_TABLE, columns, where, null, null, null, null, null);

        Category category = null;

        if(cursor != null)
        {
            cursor.moveToFirst();
            category = new Category();
            category.setId(cursor.getInt(cursor.getColumnIndex(ID_COLUMN)));
            category.setName(cursor.getString(cursor.getColumnIndex(NAME_COLUMN)));
        }
        return category;
    }
}
