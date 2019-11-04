package com.example.musicplayer;

public class SongClass {
    private String songName;
    private String songLink;

    public SongClass(){};

    public SongClass(String songName, String songLink){
        this.songName = songName;
        this.songLink = songLink;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongLink(){
        return songLink;
    }

    public void setSongLink (String songLink) {
        this.songLink = songLink;
    }


}
