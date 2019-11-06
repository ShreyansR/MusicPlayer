package com.example.musicplayer;

public class SongClass {
    private String songName;
    private String songArtist;
    private String songLink;

    public SongClass(){};

    public SongClass(String songName, String songArtist, String songLink){
        this.songName = songName;
        this.songArtist = songArtist;
        this.songLink = songLink;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongArtist(){
        return songArtist;
    }

    public void setSongArtist (String songArtist) {
        this.songArtist = songArtist;
    }

    public String getSongLink(){
        return songLink;
    }

    public void setSongLink (String songLink) {
        this.songLink = songLink;
    }


}
