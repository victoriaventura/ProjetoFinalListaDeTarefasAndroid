package com.example.oddvicky.projetofinallistadetarefas.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.oddvicky.projetofinallistadetarefas.model.Category;
import com.example.oddvicky.projetofinallistadetarefas.model.Task;

import java.util.LinkedList;
import java.util.List;

public class TaskDAO {
    private SQLiteDatabase db;
    private DBOpenHelper banco;

    public TaskDAO(Context context) {
        banco = new DBOpenHelper(context);
    }

    private static final String TASK_TABLE = "task";
    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";
    private static final String DESCRIPTION_COLUMN = "description";
    private static final String CATEGORY_ID_COLUMN = "category_id";

    public String add(Task task){
        long result;
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME_COLUMN, task.getName());
        values.put(DESCRIPTION_COLUMN, task.getDescription());
        values.put(CATEGORY_ID_COLUMN, task.getCategory().getId());

        result = db.insert(TASK_TABLE, null, values);

        db.close();

        if(result == -1) {
            return "Error trying to insert user!";
        } else {
            return "User successfully inserted!";
        }
    }

    public String remove(int taskId){
        long result;
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();

        result = db.delete(TASK_TABLE, ID_COLUMN + "=" + taskId, null);

        db.close();

        if(result > 0) {
            return "Success!";
        } else {
            return "Error!";
        }
    }

    public List<Task> getAll() {
        List<Task> tasks = new LinkedList<>();

        String rawQuery = "SELECT t.*, c.name FROM " + TaskDAO.TASK_TABLE + " t INNER JOIN " + CategoryDAO.CATEGORY_TABLE
                + " c ON t." + TaskDAO.CATEGORY_ID_COLUMN + " = c." + CategoryDAO.ID_COLUMN +
                " ORDER BY " + TaskDAO.NAME_COLUMN  + " ASC";

        SQLiteDatabase db = banco.getReadableDatabase();

        Cursor cursor = db.rawQuery(rawQuery, null);

        Task task = null;
        if (cursor.moveToFirst()) {
            do {
                task = new Task();

                task.setId(cursor.getInt(0));
                task.setName(cursor.getString(2));
                task.setDescription(cursor.getString(3));
                task.setCategory(new Category(cursor.getInt(1), cursor.getString(5)));

                tasks.add(task);
            } while (cursor.moveToNext());
        }
        return tasks;
    }
}
