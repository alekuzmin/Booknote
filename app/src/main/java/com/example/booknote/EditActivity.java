package com.example.booknote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.booknote.db.DbManager;

public class EditActivity extends AppCompatActivity {
    private EditText editTitle, editDescription;
    private DbManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        init();
    }

    private void init(){
        editTitle = findViewById(R.id.edTitle);
        editDescription = findViewById(R.id.edDesc);
        dbManager = new DbManager(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbManager.openDb();
    }

    public void onClickSave(View view) {
        String title = editTitle.getText().toString();
        String description = editDescription.getText().toString();

        if (title.equals("") || description.equals("")) {
            Toast.makeText(this, R.string.text_epty_error, Toast.LENGTH_SHORT).show();
        } else {
            dbManager.insertToDb(title, description);
            Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();
            finish();
            dbManager.closeDb();
        }

    }
}