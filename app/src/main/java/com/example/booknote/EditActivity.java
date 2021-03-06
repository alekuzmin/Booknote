package com.example.booknote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.booknote.adapter.Note;
import com.example.booknote.db.AppExecuter;
import com.example.booknote.db.Constants;
import com.example.booknote.db.DbManager;


public class EditActivity extends AppCompatActivity {
    private EditText editTitle, editDescription;
    private DbManager dbManager;
    private boolean isEditState = true;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        init();
        getNoteIntent();
    }

    private void init() {
        editTitle = findViewById(R.id.edTitle);
        editDescription = findViewById(R.id.edDesc);
        dbManager = new DbManager(this);
    }

    private void getNoteIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            note = (Note) intent.getSerializableExtra(Constants.NOTE_INTENT);
            isEditState = intent.getBooleanExtra(Constants.EDIT_STATE, true);
            if(!isEditState){
                editTitle.setText(note.getTitle());
                editDescription.setText(note.getDescription());
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        dbManager.openDb();
    }

    public void onClickSave(View view) {
        final String title = editTitle.getText().toString();
        final String description = editDescription.getText().toString();

        if (title.equals("") || description.equals("")) {
            Toast.makeText(this, R.string.text_epty_error, Toast.LENGTH_SHORT).show();
        } else {
            if(isEditState) {
                AppExecuter.getInstance().getSubIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        dbManager.insertToDb(title, description);
                    }
                });
                Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();
            } else {
                dbManager.updateDb(title, description, note.getId());
            }
            dbManager.closeDb();
            finish();
        }

    }
}