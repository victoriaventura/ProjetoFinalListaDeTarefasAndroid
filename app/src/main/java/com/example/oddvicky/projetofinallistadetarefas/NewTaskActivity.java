package com.example.oddvicky.projetofinallistadetarefas;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.oddvicky.projetofinallistadetarefas.dao.CategoryDAO;
import com.example.oddvicky.projetofinallistadetarefas.dao.TaskDAO;
import com.example.oddvicky.projetofinallistadetarefas.model.Category;
import com.example.oddvicky.projetofinallistadetarefas.model.Task;

import java.util.List;

public class NewTaskActivity extends AppCompatActivity {

    public final static int CODE_NEW_TASK = 1002;

    private TextInputLayout tilTaskName;
    private TextInputLayout tilTaskDescription;
    private Spinner spCategory;

    private List<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        tilTaskName = (TextInputLayout) findViewById(R.id.tilTaskName);
        tilTaskDescription = (TextInputLayout) findViewById(R.id.tilTaskDescription);
        spCategory = (Spinner) findViewById(R.id.spCategory);

        CategoryDAO categoryDAO = new CategoryDAO(this);
        categories = categoryDAO.getAll();

        ArrayAdapter<Category> adapter =
                new ArrayAdapter<Category>(getApplicationContext(), R.layout.category_spinner_item, categories);

        adapter.setDropDownViewResource(R.layout.category_spinner_item);
        spCategory.setAdapter(adapter);
    }

    public void createTask(View v) {
        TaskDAO taskDAO = new TaskDAO(this);
        Task task = new Task();
        task.setName(tilTaskName.getEditText().getText().toString());
        task.setDescription(tilTaskDescription.getEditText().getText().toString());
        //task.setDate(Double.valueOf(tilDate.getEditText().getText().toString()));
        task.setCategory((Category)spCategory.getSelectedItem());

        taskDAO.add(task);
        returnToPreviousIntent();
    }

    public void returnToPreviousIntent() {
        Intent intentMessage=new Intent();
        setResult(CODE_NEW_TASK, intentMessage);
        finish();
        Intent homepage = new Intent(NewTaskActivity.this, MainActivity.class);
        startActivity(homepage);
    }
}
