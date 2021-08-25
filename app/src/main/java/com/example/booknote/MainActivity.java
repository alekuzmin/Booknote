package com.example.booknote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.booknote.db.DbManager;

public class MainActivity extends AppCompatActivity {
    private DbManager dbManager;
    private EditText edTitle, edDesc;
    private TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbManager = new DbManager(this);
        edTitle = findViewById(R.id.edTitle);
        edDesc = findViewById(R.id.edDesc);
        txtView = findViewById(R.id.txtView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbManager.openDb();
    }

    public void onClickSave(View view) {
        txtView.setText("");
        dbManager.insertToDb(edTitle.getText().toString(), edDesc.getText().toString());
        for(String title : dbManager.selectFromDb()){
            txtView.append(title);
            txtView.append("\n");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.closeDb();
    }
}