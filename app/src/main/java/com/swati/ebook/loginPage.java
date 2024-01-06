package com.swati.ebook;

import static com.swati.ebook.R.id.editTextTextEmailAddress2;
import static com.swati.ebook.R.id.editTextTextPassword3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import java.util.HashMap;
import java.util.Map;

public class loginPage extends AppCompatActivity {

    //VALIDATION--------------------------------------------------------------------------
    Button sign_inButton;
    Button sign_upButton;
    private AwesomeValidation awesomeValidation;

    // Db connectivity------------------------------------------------------------------
    EditText dataEmail; // a text field where the data to be sent is entered
    EditText dataPassword; // a text field where the data to be sent is entered
    String EmailHolder, PasswordHolder;

    //Keep me logged in----------------------------------------------------------------------------------
    public static String PREFS_EMAIL = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);

        //setting color for text
        EditText t1 = findViewById(R.id.editTextTextEmailAddress2);
        t1.setTextColor(Color.WHITE);
        EditText t2 = findViewById(R.id.editTextTextPassword3);
        t2.setTextColor(Color.WHITE);

        //db connectivity
        dataEmail = findViewById(editTextTextEmailAddress2);
        dataPassword = findViewById(editTextTextPassword3);

        //VALIDATION---------------------------------------------------------------------
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        sign_inButton = findViewById(R.id.sign_in);
        sign_upButton = findViewById(R.id.sign_up);
        sign_inButton.setOnClickListener(mMyListener);
        sign_upButton.setOnClickListener(mMyListener);

        awesomeValidation.addValidation(this, editTextTextEmailAddress2, Patterns.EMAIL_ADDRESS, R.string.emailerror);

//------------------------------------------------------------------------------------------


    }

    private final View.OnClickListener mMyListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case (R.id.sign_in):
                    if (awesomeValidation.validate()) {
                        regUser2();
                    }
                    break;

                case (R.id.sign_up):
                    Intent i = new Intent(getApplicationContext(), signup.class);
                    startActivity(i);
                    break;

                default:
                    break;
            }
        }
    };

    public void regUser2(){
        EmailHolder = dataEmail.getText().toString().trim();
        PasswordHolder = dataPassword.getText().toString().trim();

        String myUrl = "http://192.168.16.68/Dbconnection/login.php?Email="+ EmailHolder + "&Password=" + PasswordHolder;

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, myUrl,
                ServerResponse -> {
                    if (ServerResponse.equals("1")){
                        Toast.makeText(loginPage.this, "Login successful", Toast.LENGTH_LONG).show();
                        // keep me logged in
                        SharedPreferences sharedPreferences = getSharedPreferences(loginPage.PREFS_EMAIL , 0);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("hasLoggedIn" , true);
                        editor.commit();

                        //passing email to get name
                        String path = "http://192.168.16.68/Dbconnection/retrieve.php?Email=" + EmailHolder;
                        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, path, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                SharedPreferences sp = getSharedPreferences("myPref",MODE_PRIVATE);
                                SharedPreferences.Editor editor1 = sp.edit();
                                editor1.putString("Name",response);
                                editor1.putString("Email",EmailHolder);
                                editor1.apply();

                                Intent i = new Intent(getApplicationContext(), Inner_page.class);
                                startActivity(i);
                                finish();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(loginPage.this, error.toString(), Toast.LENGTH_LONG).show();
                            }
                        });

                        RequestQueue queue1 = Volley.newRequestQueue(loginPage.this);
                        queue1.add(stringRequest1);
                    }
                    else {
                        Toast.makeText(loginPage.this, "No user found", Toast.LENGTH_LONG).show();
                    }
                },
                volleyError -> {
                    // Showing error message if something goes wrong.
                    Toast.makeText(loginPage.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                });
        queue.add(stringRequest);
    }

}