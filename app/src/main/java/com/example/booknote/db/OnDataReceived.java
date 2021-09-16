package com.example.booknote.db;

import com.example.booknote.adapter.Note;

import java.util.List;

public interface OnDataReceived {
    void onReceived(List<Note> notes);
}
