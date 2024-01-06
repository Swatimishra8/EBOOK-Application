package com.swati.ebook;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Journal extends AppCompatActivity {
    private View parentView;
    private EditText editTextTitle, editTextBody;
    private Button buttonSave;
    private ListView listViewNotes;
    private SharedPreferences sp;
    private TextView heading;
    private List<String> notesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);

        //assigning id
        parentView = findViewById(R.id.parentViewJournal);
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextBody = findViewById(R.id.editTextBody);
        buttonSave = findViewById(R.id.buttonSave);
        heading = findViewById(R.id.textViewListTitle);
        listViewNotes = findViewById(R.id.listViewNotes);

        //------------------------------------------------------
        final int black = ContextCompat.getColor(this, R.color.black);
        final int white = ContextCompat.getColor(this, R.color.white);
        final int light_blue =  ContextCompat.getColor(this, R.color.cardViewsOld);

        SharedPreferences sharedPreferences = getSharedPreferences(UserSettings.PREFERENCES , MODE_PRIVATE);
        String theme = sharedPreferences.getString(UserSettings.CUSTOM_THEME , UserSettings.LIGHT_THEME);

        if(theme.equals(UserSettings.DARK_THEME)){
            parentView.setBackgroundColor(black);
            editTextTitle.setTextColor(white);
            editTextTitle.setBackgroundColor(light_blue);
            editTextBody.setTextColor(black);
            editTextBody.setBackgroundColor(white);
            heading.setTextColor(white);
            listViewNotes.setBackgroundColor(light_blue);

        }else{
            parentView.setBackgroundColor(white);
            editTextTitle.setTextColor(black);
            editTextBody.setTextColor(black);
            heading.setTextColor(black);
            listViewNotes.setBackgroundColor(white);
        }
//---------------------------------------------------------------------------------------
         sp = getSharedPreferences("NOTES_PREFS",MODE_PRIVATE);

        //Load saved notes from SharedPreferences
        Set<String> savedNotesSet = sp.getStringSet("NOTES_PREFS", null);
        if (savedNotesSet != null) {
            notesList.addAll(savedNotesSet);
        }

        //Set Adapter for List View\
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notesList);
        listViewNotes.setAdapter(adapter);

       //Set On Click Listener for Save Button
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTextTitle.getText().toString();
                String body = editTextBody.getText().toString();
                if (title.isEmpty() || body.isEmpty()) {
                    Toast.makeText(Journal.this, "Please fill in all fields",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                String note = title + "\n" + body;
                notesList.add(note);
                adapter.notifyDataSetChanged();
                editTextTitle.getText().clear();
                editTextBody.getText().clear();
                saveNotes();
            }
        });

        //Set On Item Long Click Listener for List View
        listViewNotes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int
                    position, long id) {
                new AlertDialog.Builder(Journal.this, R.style.MyAlertDialogStyle)
                        .setTitle("Delete Note")
                        .setMessage("Are you sure you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                notesList.remove(position);
                                adapter.notifyDataSetChanged();
                                saveNotes();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        });

    }

    private void saveNotes(){
        //Get Editor for SharedPreferences instance
        SharedPreferences.Editor editor = sp.edit();
        //Convert List to Set
        Set<String> notesSet = new HashSet<>(notesList);

        //Save Set in SharedPreferences
        editor.putStringSet("NOTES_PREFS", notesSet);
        editor.apply();
    }

    //------------------------------------------------------
    //for moving back to last activity
    public void onBackPressed(){
        Intent intent = new Intent(this,Inner_page.class);
        startActivity(intent);
        finish();
    }

}