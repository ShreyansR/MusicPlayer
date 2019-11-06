package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class Song extends AppCompatActivity{
    ImageButton saveB;
    EditText songName;
    EditText songArtist;
    EditText songLink;
    MusicDB musicDB;
    SongClass song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        musicDB = new MusicDB(this);
        saveB = findViewById(R.id.editBtn);
        songName = findViewById(R.id.songName);
        songArtist = findViewById(R.id.artistName);
        songLink = findViewById(R.id.songLink);

    }

    public void save(View v)
    {
        String title = songName.getText().toString();
        String artist = songArtist.getText().toString();
        String link = songLink.getText().toString();
        song = new SongClass(title, artist, link);
        musicDB.addSong(song);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}
