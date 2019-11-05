package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class SongPlayer extends AppCompatActivity {

    private ImageButton btn;
    private ImageButton stopBtn;
    private boolean playPause;
    private MediaPlayer mediaPlayer;
    private ProgressDialog progressDialog;
    private boolean initialStage = true;
    private String songName;
    private String songLink;
    private SeekBar songProgress;
    private int mediaFileLength;
    private int realTimeLength;
    final Handler handler = new Handler();
    TextView songPlaying;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_player);
        btn = (ImageButton) findViewById(R.id.playPauseBtn);
        stopBtn = (ImageButton) findViewById(R.id.stopBtn);
        songPlaying = (TextView) findViewById(R.id.songPlaying);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        intent = getIntent();
        songLink = intent.getStringExtra("songLink");
        songName = intent.getStringExtra("songName");
        songPlaying.setText("Now Playing: " + songName);
        System.out.println(songLink);
        progressDialog = new ProgressDialog(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!playPause){
                    btn.setImageResource(R.drawable.ic_pause);

                    if (initialStage) {
                        new Player().execute(songLink);
                    }
                    else {
                        if (!mediaPlayer.isPlaying())
                            mediaPlayer.start();
                    }
                    playPause = true;
                }
                else {
                    btn.setImageResource(R.drawable.ic_play);

                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                    }
                    playPause = false;
                }
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    initialStage = true;
                    playPause = false;
                    btn.setImageResource(R.drawable.ic_play);
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    System.out.println("Stopped");
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void stop(View v){
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            Toast.makeText(this, "MediPlayer released", Toast.LENGTH_SHORT);
        }
    }

    class Player extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            Boolean prepared = false;

            try {
                mediaPlayer.setDataSource(strings[0]);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        initialStage = true;
                        playPause = false;
                        btn.setImageResource(R.drawable.ic_play);
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });
                mediaPlayer.prepare();
                prepared = true;
            } catch (Exception e){
                Log.e("MyAudioStreamingApp", e.getMessage());
                prepared = false;
            }
            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {

            super.onPostExecute(aBoolean);
            mediaFileLength = mediaPlayer.getDuration();
            realTimeLength = mediaFileLength;
            if (progressDialog.isShowing()){
                progressDialog.cancel();
            }
            mediaPlayer.start();
            initialStage = false;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("Buffering....");
            progressDialog.show();
        }
    }


}
