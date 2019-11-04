package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.security.KeyStore;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

public class ViewSong extends AppCompatActivity{
    EditText songName;
    EditText songLink;
    Button back;
    Intent intent;
    private static final String KEY_NAME_VALUE = "nameValue";
    private static final String KEY_LINK_VALUE = "nameValue";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_song);
        intent = getIntent();
        songName = findViewById(R.id.songName);
        songLink = findViewById(R.id.songLink);
        back = findViewById(R.id.back);
        String old_title = intent.getStringExtra("songName");
        SongClass song = new SongClass();
        MusicDB musicDB = new MusicDB(this);
        song = musicDB.getSong(old_title);
        songName.setText(song.getSongName());
        songLink.setText(song.getSongLink());


        if (savedInstanceState != null) {
            CharSequence savedText1 = savedInstanceState.getCharSequence(KEY_NAME_VALUE);
            CharSequence savedText2 = savedInstanceState.getCharSequence(KEY_LINK_VALUE);
            songName.setText(savedText1);
            songLink.setText(savedText2);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence(KEY_NAME_VALUE, songName.getText());
        outState.putCharSequence(KEY_LINK_VALUE, songLink.getText());
    }
    public void back (View v){
        setResult(RESULT_OK, intent);
        finish();
    }
    public void delete(View v) {
        View parent = (View) v.getParent();
        EditText songName = parent.findViewById(R.id.songName);
        String task = String.valueOf(songName.getText());
        MusicDB db = new MusicDB(this);
        db.deleteSong(task);
        db.close();
        finish();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void update(View v){
        String old_title = intent.getStringExtra("songName");
        System.out.println(old_title);
        MusicDB updateSong = new MusicDB(this);
        String new_title = songName.getText().toString();
        String new_link = songLink.getText().toString();
        updateSong.updateSong(old_title, new_title, new_link);
        updateSong.close();
        finish();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void play(View v){
        Intent intent = new Intent(this, SongPlayer.class);
        startActivity(intent);
    }

}
