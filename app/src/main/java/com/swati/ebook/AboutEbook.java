package com.swati.ebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AboutEbook extends AppCompatActivity {
    private Button submitButton;
    private EditText feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_ebook);

        TextView tv = findViewById(R.id.Contents);
        submitButton = findViewById(R.id.buttonFeedback);
        feedback =findViewById(R.id.editTextFeedback);
        Intent intent = getIntent();

        if(intent.getStringExtra("Content").startsWith("If")) {
            String content = intent.getStringExtra("Content");
            tv.setText(content);
        }

        else if(intent.getStringExtra("Content").startsWith("Hi")) {
            String content = intent.getStringExtra("Content");
            tv.setText(content);
        }

        else if(intent.getStringExtra("Content").startsWith("Thank")) {
            String content = intent.getStringExtra("Content");
            tv.setText(content);
        }

        else{
            String content = intent.getStringExtra("Content");
            tv.setText(content);
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(feedback.getText().toString().trim().length() == 0) {
                    Toast.makeText(AboutEbook.this, "Please enter your response..", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(AboutEbook.this, "Thanks! Your feedback is recorded", Toast.LENGTH_SHORT).show();
                    feedback.setText(" ");
                }
            }
        });

    }

    public void onBackPressed(){
        Intent intent = new Intent(this,Settings.class);
        startActivity(intent);
        finish();
    }

}