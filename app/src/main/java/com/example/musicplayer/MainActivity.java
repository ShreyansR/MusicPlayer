package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ImageButton newSongB;
    ListView songsList;
    MusicDB music;
    EditText search;
    private ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newSongB = findViewById(R.id.newSongBtn);
        search = findViewById(R.id.searchBar);
        songsList = findViewById(R.id.songsList);
        ArrayList<String> names = new ArrayList<>();
        music = new MusicDB(this);
        names = music.getNames();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,names);
        songsList.setAdapter(adapter);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (MainActivity.this).adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        songsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String songName = (String) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, ViewSong.class);
                intent.putExtra("songName", songName);
                intent.putExtra("old", "old");
                int REQUEST_ID=13;
                startActivityForResult(intent, REQUEST_ID);
            }
        });
    }

    protected void onSaveInstanceState (Bundle outState){
        super.onSaveInstanceState(outState);
    }

    public void newSong(View v){
        Intent intent = new Intent(this, Song.class);
        startActivity(intent);
    }

}
