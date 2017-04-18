package com.example.oddvicky.projetofinallistadetarefas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.oddvicky.projetofinallistadetarefas.dao.LoginDAO;
import com.example.oddvicky.projetofinallistadetarefas.model.Login;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout tilUser;
    private TextInputLayout tilPass;
    private CheckBox cbKeepConnected;
    public static final String KEY_APP_PREFERENCES = "APP_PREFERENCES";
    public static final String KEY_LOGIN = "login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tilUser = (TextInputLayout) findViewById(R.id.tilLogin);
        tilPass = (TextInputLayout) findViewById(R.id.tilSenha);
        cbKeepConnected = (CheckBox) findViewById(R.id.cbKeepConnected);

        if (isConnected())
            startApp();
    }

    private boolean isConnected() {
        SharedPreferences shared = getSharedPreferences(KEY_APP_PREFERENCES, MODE_PRIVATE);
        String login = shared.getString(KEY_LOGIN, "");
        if (login.equals(""))
            return false;
        else
            return true;
    }

    private void startApp() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void keepConnected() {
        String login = tilUser.getEditText().getText().toString();
        SharedPreferences pref = getSharedPreferences(KEY_APP_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_LOGIN, login);
        editor.apply();
    }

    public void doLogin(View v) {
        if (isLoginCorrect()) {
            if (cbKeepConnected.isChecked()) {
                keepConnected();
            }
            startApp();
        } else {
            Toast.makeText(this, "User or password invalid!", (Toast.LENGTH_LONG)).show();
        }
    }


    private boolean isLoginCorrect() {
        String user = tilUser.getEditText().getText().toString();
        String pass = tilPass.getEditText().getText().toString();

        LoginDAO loginDAO = new LoginDAO(this);
        Login login = loginDAO.getByUsername(user);

        if(login != null) {
            if (pass.equals(login.getPassword())) {
                return true;
            } else
                return false;
        } else {
            return false;
        }
    }
}
