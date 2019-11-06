package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.security.KeyStore;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

public class ViewSong extends AppCompatActivity{
    TextView songName;
    TextView songArtist;
    TextView songLink;
    ImageButton back;
    Intent intent;
    private static final String KEY_NAME_VALUE = "nameValue";
    private static final String KEY_LINK_VALUE = "nameValue";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_song);
        intent = getIntent();
        songName = findViewById(R.id.songName);
        songArtist = findViewById(R.id.artistName);
        songLink = findViewById(R.id.songLink);
        back = findViewById(R.id.back);
        String old_title = intent.getStringExtra("songName");
        SongClass song = new SongClass();
        MusicDB musicDB = new MusicDB(this);
        song = musicDB.getSong(old_title);
        songName.setText(song.getSongName());
        songArtist.setText((song.getSongArtist()));
        songLink.setText(song.getSongLink());


        if (savedInstanceState != null) {
            CharSequence savedText1 = savedInstanceState.getCharSequence(KEY_NAME_VALUE);
            CharSequence savedText2 = savedInstanceState.getCharSequence(KEY_LINK_VALUE);
            songName.setText(savedText1);
            songLink.setText(savedText2);
        }
    }

    public void edit(View v){
        Intent intent = new Intent(this, EditSong.class);
        intent.putExtra("songName", songName.getText().toString());
        intent.putExtra("songArtist", songArtist.getText().toString());
        intent.putExtra("songLink", songLink.getText().toString());
        startActivity(intent);
    }

    public void play(View v){
        Intent intent = new Intent(this, SongPlayer.class);
        intent.putExtra("songName", songName.getText().toString());
        intent.putExtra("songArtist", songArtist.getText().toString());
        intent.putExtra("songLink", songLink.getText().toString());
        startActivity(intent);
    }

    public void back (View v){
        setResult(RESULT_OK, intent);
        finish();
    }

}
