package com.swati.ebook;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Downloads extends AppCompatActivity {
    private View parentView;
    private ListView downloadListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloads);

        //assigning id
        parentView = findViewById(R.id.parentViewDownloads);
        downloadListView = findViewById(R.id.listview);

        // getting downloaded pdf
        List<File> fileNames = new ArrayList<>();
        File downloadFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if(downloadFolder.isDirectory()) {
            File[] files = downloadFolder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.getName().endsWith(".pdf")) {
                        fileNames.add(file);
                    }
                }
            }else{
                Toast.makeText(Downloads.this, "No books are added", Toast.LENGTH_SHORT).show();
            }
        }

        PDFListAdapter adapter = new PDFListAdapter(Downloads.this, fileNames);
        downloadListView.setAdapter(adapter);

        // Set the click listener for the ListView items
        downloadListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Open the selected PDF file in a PDF viewer app
                File selectedFile = fileNames.get(position);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(FileProvider.getUriForFile(Downloads.this,   BuildConfig.APPLICATION_ID + ".provider", selectedFile), "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(Downloads.this, "No book found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //------------------------------------------------------
        final int black = ContextCompat.getColor(this, R.color.black);
        final int white = ContextCompat.getColor(this, R.color.white);

        SharedPreferences sharedPreferences = getSharedPreferences(UserSettings.PREFERENCES , MODE_PRIVATE);
        String theme = sharedPreferences.getString(UserSettings.CUSTOM_THEME , UserSettings.LIGHT_THEME);

        if(theme.equals(UserSettings.DARK_THEME)){
            parentView.setBackgroundColor(black);
            downloadListView.setBackgroundColor(black);
        }else{
            parentView.setBackgroundColor(white);
            downloadListView.setBackgroundColor(white);
        }
    }

    //------------------------------------------------------
    //for moving back to last activity
    public void onBackPressed(){
        Intent intent = new Intent(this,Inner_page.class);
        startActivity(intent);
        finish();
    }



}