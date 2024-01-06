package com.swati.ebook;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

public class Inner_page extends AppCompatActivity implements  View.OnClickListener{

    private View parentView;
    private TextView greetTv, userProfTv, booksTv, setTv, logoutTv, journalTv, downloadTv;
    private String userName,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inner_page);

        CardView D1 = findViewById(R.id.d1);
        CardView D2 = findViewById(R.id.d2);
        CardView D3 = findViewById(R.id.d3);
        CardView D4 = findViewById(R.id.d4);
        CardView D5 = findViewById(R.id.d5);
        CardView D6 = findViewById(R.id.d6);

        D1.setOnClickListener(this);
        D2.setOnClickListener(this);
        D3.setOnClickListener(this);
        D4.setOnClickListener(this);
        D5.setOnClickListener(this);
        D6.setOnClickListener(this);

        //obtaining id of the attributes
        parentView = findViewById(R.id.parentViewInnerPage);
        userProfTv = findViewById(R.id.user_prof);
        booksTv = findViewById(R.id.book);
        setTv = findViewById(R.id.setting);
        logoutTv = findViewById(R.id.logout);
        journalTv = findViewById(R.id.journal);
        downloadTv = findViewById(R.id.download);

        //color
        final int black = ContextCompat.getColor(this, R.color.black);
        final int white = ContextCompat.getColor(this, R.color.white);
        final int bluishNew = ContextCompat.getColor(this, R.color.cardViewsNew);
        final int bluishOld = ContextCompat.getColor(this, R.color.cardViewsOld);

        //-------------------------------------------------------------------------------
        greetTv = findViewById(R.id.greet);
        SharedPreferences sf = getSharedPreferences("myPref",MODE_PRIVATE);
        userName = sf.getString("Name","");
        email = sf.getString("Email","");

        greetTv.setText(getString(R.string.Welcome).concat("! ").concat(userName));

        //-------------------------------------------------------------------------------

        //getting theme from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences(UserSettings.PREFERENCES, MODE_PRIVATE);
        String theme = sharedPreferences.getString(UserSettings.CUSTOM_THEME, UserSettings.LIGHT_THEME);

        if (theme.equals(UserSettings.DARK_THEME)) {
            parentView.setBackgroundColor(black);
            greetTv.setTextColor(white);
            userProfTv.setTextColor(white);
            booksTv.setTextColor(white);
            setTv.setTextColor(white);
            logoutTv.setTextColor(white);
            journalTv.setTextColor(white);
            downloadTv.setTextColor(white);
            D1.setCardBackgroundColor(bluishNew);
            D2.setCardBackgroundColor(bluishNew);
            D3.setCardBackgroundColor(bluishNew);
            D4.setCardBackgroundColor(bluishNew);
            D5.setCardBackgroundColor(bluishNew);
            D6.setCardBackgroundColor(bluishNew);
        } else {
            greetTv.setTextColor(black);
            userProfTv.setTextColor(white);
            booksTv.setTextColor(white);
            setTv.setTextColor(white);
            logoutTv.setTextColor(white);
            journalTv.setTextColor(white);
            downloadTv.setTextColor(white);
            parentView.setBackgroundColor(white);
            D1.setCardBackgroundColor(bluishOld);
            D2.setCardBackgroundColor(bluishOld);
            D3.setCardBackgroundColor(bluishOld);
            D4.setCardBackgroundColor(bluishOld);
            D5.setCardBackgroundColor(bluishOld);
            D6.setCardBackgroundColor(bluishOld);
        }


    }

    @Override
    public void onClick(View v){
        Intent i;
        switch (v.getId()){

            case (R.id.d1) :
                //to pass the data to user profile activity
                i = new Intent(this , Userprofile.class);
                startActivity(i);
                finish();
                break;

            case (R.id.d2) :
                i = new Intent(this , Books.class);
                startActivity(i);
                finish();
                break;

            case (R.id.d3) :
                i = new Intent(this , Settings.class);
                startActivity(i);
                finish();
                break;

            case (R.id.d4) :
                new AlertDialog.Builder(this)
                        .setTitle("LOG OUT")
                        .setMessage("Do you want to Log out?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                redLogin();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                       .show();
                break;

            case (R.id.d5) :
                i = new Intent(this , Journal.class);
                startActivity(i);
                finish();
                break;

            case (R.id.d6) :
                i = new Intent(this , Downloads.class);
                startActivity(i);
                finish();
                break;
        }

    }

    //redirect to login
    public void redLogin(){
        SharedPreferences sp = getSharedPreferences(loginPage.PREFS_EMAIL,MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(Inner_page.this, loginPage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}