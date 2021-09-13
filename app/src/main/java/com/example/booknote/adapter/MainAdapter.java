package com.example.booknote.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booknote.EditActivity;
import com.example.booknote.R;
import com.example.booknote.db.Constants;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {
    private Context context;
    private List<Note> noteList;

    public MainAdapter(Context context) {
        this.context = context;
        noteList = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.item_list_layout, parent, false);
        return new MyViewHolder(view, context, noteList);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setData(noteList.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTitle;
        private Context context;
        private List<Note> noteList;

        public MyViewHolder(@NonNull View itemView, Context context, List<Note> noteList) {
            super(itemView);
            this.context = context;
            this.noteList = noteList;
            tvTitle = itemView.findViewById(R.id.tvTitle);
            itemView.setOnClickListener(this);
        }
        public void setData(String title){
            tvTitle.setText(title);

        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, EditActivity.class);
            intent.putExtra(Constants.NOTE_INTENT, noteList.get(getAdapterPosition()));
            intent.putExtra(Constants.EDIT_STATE, false);
            context.startActivity(intent);
        }
    }

    public void updateAdapter(List<Note> newNote){
        noteList.clear();
        noteList.addAll(newNote);
        notifyDataSetChanged();
    }
}
