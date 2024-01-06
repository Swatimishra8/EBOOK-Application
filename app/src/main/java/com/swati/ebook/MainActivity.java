package com.swati.ebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                SharedPreferences sharedPreferences = getSharedPreferences(loginPage.PREFS_EMAIL , 0);
                boolean hasLoggedIn = sharedPreferences.getBoolean("hasLoggedIn" , false);

                if(hasLoggedIn) {
                    Intent intent = new Intent(MainActivity.this, Inner_page.class);
                    startActivity(intent);
                    finishScreen();
                }
                else{
                    Intent intent = new Intent(MainActivity.this, loginPage.class);
                    startActivity(intent);
                    finishScreen();
                }
            }
        };

        Timer t = new Timer();
        t.schedule(task,3000);

    }

    private void finishScreen(){
        this.finish();
    }



}