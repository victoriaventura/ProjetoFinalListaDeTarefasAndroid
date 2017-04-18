package com.example.oddvicky.projetofinallistadetarefas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.oddvicky.projetofinallistadetarefas.adapter.ListAndroidAdapter;
import com.example.oddvicky.projetofinallistadetarefas.dao.TaskDAO;
import com.example.oddvicky.projetofinallistadetarefas.listener.ClickListener;
import com.example.oddvicky.projetofinallistadetarefas.listener.RecyclerTouchListener;
import com.example.oddvicky.projetofinallistadetarefas.model.Task;

import java.util.List;

import static com.example.oddvicky.projetofinallistadetarefas.LoginActivity.KEY_APP_PREFERENCES;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button btnExcluir;
    private ListAndroidAdapter adapter;
    RecyclerView rvLista;
    TaskDAO taskDAO = new TaskDAO(this);
    List<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvLista = (RecyclerView) findViewById(R.id.rvList);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, NewTaskActivity.class), NewTaskActivity.CODE_NEW_TASK);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView)
                findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        tasks = taskDAO.getAll();

        rvLista.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvLista, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Task task = tasks.get(position);
                TaskDAO taskDAO = new TaskDAO(getApplicationContext());
                taskDAO.remove(task.getId());
                finish();
                startActivity(getIntent());
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        setUpList(tasks);
    }

    private void setUpList(List<Task> list) {
        adapter = new ListAndroidAdapter(this,list);
        rvLista.setLayoutManager(new LinearLayoutManager(this));
        rvLista.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED) {

            Toast.makeText(MainActivity.this, "Canceled", Toast.LENGTH_LONG).show();
        } else if(requestCode == NewTaskActivity.CODE_NEW_TASK) {
            TaskDAO taskDAO = new TaskDAO(this);
            List<Task> tasks = taskDAO.getAll();
            setUpList(tasks);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.nav_logout:
                doLogout();
                break;
            case R.id.nav_AboutApp:
                doRedirectToAboutApp();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void doRedirectToAboutApp() {
        startActivity(new Intent(this, AboutAppActivity.class));
    }

    private void doLogout() {
        SharedPreferences pref = getSharedPreferences(KEY_APP_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(LoginActivity.KEY_LOGIN, "");
        editor.apply();
        finish();
    }
}
