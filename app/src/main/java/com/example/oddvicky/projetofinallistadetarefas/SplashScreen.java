package com.example.oddvicky.projetofinallistadetarefas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oddvicky.projetofinallistadetarefas.dao.LoginDAO;
import com.example.oddvicky.projetofinallistadetarefas.model.Login;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SplashScreen extends AppCompatActivity {

    //Display time for the splash screen to be visible
    private final int SPLASH_DISPLAY_LENGTH = 5000;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //Setting logo font
        tv = (TextView) findViewById(R.id.tv_LogoName);
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/emmasophia.ttf");
        tv.setTypeface(type);

        new GetLoginData().execute("http://www.mocky.io/v2/58b9b1740f0000b614f09d2f");
        loadAnimation();
    }

    private void loadAnimation(){

        Animation anim = AnimationUtils.loadAnimation(this,R.anim.animacao_splash);
        anim.reset();

        //Getting logo image
        ImageView iv = (ImageView) findViewById(R.id.splash);
        if (iv != null) {
            iv.clearAnimation();
            iv.startAnimation(anim);
        }
        if(tv != null){
            tv.clearAnimation();
            tv.startAnimation(anim);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                SplashScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }


    private class GetLoginData extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                URL url = new URL(params[0]);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setReadTimeout(15000);
                conn.setConnectTimeout(10000);

                conn.setRequestMethod("GET");
                conn.setDoOutput(true);

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream is = conn.getInputStream();
                    BufferedReader buffer = new BufferedReader(new InputStreamReader((is)));

                    StringBuilder result = new StringBuilder();
                    String linha;

                    while ((linha = buffer.readLine()) != null) {
                        result.append(linha);
                    }

                    conn.disconnect();
                    return result.toString();
                }
            } catch (MalformedURLException e) {

            } catch (IOException ioe) {

            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if(s == null){
                Toast.makeText(SplashScreen.this, "Something went wrong!", Toast.LENGTH_LONG).show();
            } else {
                try {

                    JSONObject json = new JSONObject(s);

                    Login login = new Login();
                    login.setUsername(json.getString("usuario"));
                    login.setPassword(json.getString("senha"));

                    //Persist login info to database
                    createUserOnDatabase(login);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void createUserOnDatabase(Login login){
        LoginDAO loginDAO = new LoginDAO(this);
        loginDAO.addUser(login);
    }
}
