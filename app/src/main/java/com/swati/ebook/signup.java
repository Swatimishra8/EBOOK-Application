package com.swati.ebook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import java.util.Timer;
import java.util.TimerTask;

public class signup extends AppCompatActivity{

    //VALIDATION---------------------------------------------------------------------------
    Button requestButton;
    private AwesomeValidation awesomeValidation;
    //-------------------------------------------------------------------------------------
    //DB connectivity--------------------------------------------------------------------
    EditText dataName; // a text field to display the request response
    EditText dataEmail; // a text field where the data to be sent is entered
    EditText dataPassword; // a text field where the data to be sent is entered
    RequestQueue requestQueue;
    ProgressBar progressBar;
    String UsernameHolder, EmailHolder, PasswordHolder ;

    //----------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //VALIDATION-------------------------------------------------------------------------------
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        requestButton = findViewById(R.id.create_account);
        requestButton.setOnClickListener(mMyListener);

        String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";
        //adding validation to edit texts
        awesomeValidation.addValidation(this, R.id.PersonName, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.EmailAddress, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.TextPassword, regexPassword, R.string.passworderror);
//------------------------------------------------------------------------------------------------

        //Db connectivity-----------------------------------------------------------------
        dataName = findViewById(R.id.PersonName);
        dataEmail = findViewById(R.id.EmailAddress);
        dataPassword = findViewById(R.id.TextPassword);

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(signup.this);
        progressBar = findViewById(R.id.progressBar);

//------------------------------------------------------------------------------------------
        }


    private final View.OnClickListener mMyListener = new View.OnClickListener() {
        //VALIDATION------------------------------------------------------------------------
        public void onClick(View v) {
            if(v.getId()==R.id.create_account){
                progressBar.setVisibility(View.VISIBLE);
                if(awesomeValidation.validate()){
                    regUser();
                }
            }

        }
    };

//---------------------------------------------------------------------------------------------
    public void regUser(){

    UsernameHolder = dataName.getText().toString().trim();
    EmailHolder = dataEmail.getText().toString().trim();
    PasswordHolder = dataPassword.getText().toString().trim();

    final String url = "http://192.168.16.68/Dbconnection/signup.php?Username=" + UsernameHolder + "&Email=" + EmailHolder + "&Password=" + PasswordHolder;

    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
            ServerResponse -> {
                // Hiding the progress dialog after all task complete.
                // Showing response message coming from server.
                if(ServerResponse.equals("Successful!")){
                    Toast.makeText(signup.this, ServerResponse, Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(), loginPage.class);
                    startActivity(i);
                    finish();
                }

                else{
                    Toast.makeText(signup.this, ServerResponse, Toast.LENGTH_LONG).show();
                }

            },
            volleyError -> {
                // Showing error message if something goes wrong.
                Toast.makeText(signup.this, volleyError.toString(), Toast.LENGTH_LONG).show();
            });
      queue.add(stringRequest);
     }
//--------------------------------------------------------------------------------------

}