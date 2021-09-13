package com.example.booknote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


import com.example.booknote.adapter.MainAdapter;
import com.example.booknote.db.DbManager;

public class MainActivity extends AppCompatActivity {
    private DbManager dbManager;
    private EditText edTitle, edDesc;
    private RecyclerView recyclerView;
    private MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    private void init(){
        dbManager = new DbManager(this);
        edTitle = findViewById(R.id.edTitle);
        edDesc = findViewById(R.id.edDesc);
        recyclerView = findViewById(R.id.rcView);
        mainAdapter = new MainAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mainAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbManager.openDb();
        mainAdapter.updateAdapter(dbManager.selectFromDb());
    }

    public void onClickAdd(View view) {
        Intent intent = new Intent(MainActivity.this, EditActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.closeDb();
    }
}