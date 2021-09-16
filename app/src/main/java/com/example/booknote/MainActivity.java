package com.example.booknote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;


import com.example.booknote.adapter.MainAdapter;
import com.example.booknote.adapter.Note;
import com.example.booknote.db.AppExecuter;
import com.example.booknote.db.DbManager;
import com.example.booknote.db.OnDataReceived;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDataReceived {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.id_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String s) {
                readFromDb(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void init(){
        dbManager = new DbManager(this);
        edTitle = findViewById(R.id.edTitle);
        edDesc = findViewById(R.id.edDesc);
        recyclerView = findViewById(R.id.rcView);
        mainAdapter = new MainAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getItemTouchHelper().attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(mainAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbManager.openDb();
        readFromDb("");
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

    private ItemTouchHelper getItemTouchHelper(){
        return new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mainAdapter.removeItem(viewHolder.getAdapterPosition(), dbManager);
            }
        });
    }

    private void readFromDb(final String text){
        AppExecuter.getInstance().getSubIO().execute(new Runnable() {
            @Override
            public void run() {
                dbManager.selectFromDb(text, MainActivity.this);
            }
        });
    }

    @Override
    public void onReceived(List<Note> notes) {
        AppExecuter.getInstance().getMainIO().execute(new Runnable() {
            @Override
            public void run() {
                mainAdapter.updateAdapter(notes);
            }
        });
    }
}