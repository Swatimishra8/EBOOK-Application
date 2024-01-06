package com.swati.ebook;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Books extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        final int black = ContextCompat.getColor(this, R.color.black);
        final int white = ContextCompat.getColor(this, R.color.white);

        //assigning id
        View parentView = findViewById(R.id.parentViewBooks);

        SharedPreferences sharedPreferences = getSharedPreferences(UserSettings.PREFERENCES , MODE_PRIVATE);
        String theme = sharedPreferences.getString(UserSettings.CUSTOM_THEME , UserSettings.LIGHT_THEME);

        if(theme.equals(UserSettings.DARK_THEME)){
            parentView.setBackgroundColor(black);

        }else{
            parentView.setBackgroundColor(white);

        }

        //retrieving data from server
        GridView gridView = findViewById(R.id.grid_view);
        new JSONDownloader(Books.this).retrieve(gridView);

    }

    //get books
    public class JSONDownloader {

        final String url = "http://192.168.16.68/PDF";
        private final Context context;

        public JSONDownloader(Context context) {
            this.context = context;
        }

        public void retrieve(GridView gridView) {
            List<BookModel> books = new ArrayList<>();

            JsonArrayRequest request = new JsonArrayRequest(
                    url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    //process the json array of pdf path
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = response.getJSONObject(i);
                            //extract the relevant info from json object
                            String BookName = obj.getString("BookName");
                            String BookCategory = obj.getString("BookCategory");
                            //String BookDescription = obj.getString("BookDescription");
                            String PdfUrl = obj.getString("PdfUrl");
                            String PdfIconUrl = obj.getString("PdfIconUrl");
                            String BookAuthor = obj.getString("BookAuthor");

                            //create a book to collect the data and add it to list
                            BookModel book = new BookModel(BookName, BookCategory, BookAuthor, url+PdfUrl, url+PdfIconUrl);
                            books.add(book);
                        }

                        //pass the list of books to the bookAdapter
                        BookAdapter bookAdapter = new BookAdapter(context, books);
                        gridView.setAdapter(bookAdapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Books.this, "Error loading books", Toast.LENGTH_LONG).show();
                        }
                    });

            // add the request to volley request queue
            Volley.newRequestQueue(getApplicationContext()).add(request);
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

