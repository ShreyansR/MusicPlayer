package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button newSongB;
    ListView songsList;
    MusicDB music;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newSongB = findViewById(R.id.newSongBtn);
        songsList = findViewById(R.id.songsList);
        ArrayList<String> names = new ArrayList<>();
        music = new MusicDB(this);
        names = music.getNames();
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,names);
        songsList.setAdapter(adapter);
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
