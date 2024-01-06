package com.swati.ebook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputLayout;

public class Userprofile extends AppCompatActivity {
    private View parentView;
    private EditText name, email;
    private TextView userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);

        parentView = findViewById(R.id.parentViewUserProfile);
        name = findViewById(R.id.Name);
        email = findViewById(R.id.Email);
        userProfile= findViewById(R.id.textView_userProfile);

        //retriving the data
        SharedPreferences sf = getSharedPreferences("myPref",MODE_PRIVATE);
        String userName = sf.getString("Name","");
        String Email = sf.getString("Email","");

        name.setText(userName);
        email.setText(Email);
        //------------------------------------------------------
        final int black = ContextCompat.getColor(this, R.color.black);
        final int white = ContextCompat.getColor(this, R.color.white);

        SharedPreferences sharedPreferences = getSharedPreferences(UserSettings.PREFERENCES , MODE_PRIVATE);
        String theme = sharedPreferences.getString(UserSettings.CUSTOM_THEME , UserSettings.LIGHT_THEME);

        if(theme.equals(UserSettings.DARK_THEME)){
            parentView.setBackgroundColor(black);
            userProfile.setTextColor(black);

        }else{
            parentView.setBackgroundColor(white);
            userProfile.setTextColor(white);
        }
        //---------------------------------------------------

    }
    //------------------------------------------------------
    //for moving back to last activity
    public void onBackPressed(){
        Intent intent = new Intent(this,Inner_page.class);
        startActivity(intent);
        finish();
    }

}
