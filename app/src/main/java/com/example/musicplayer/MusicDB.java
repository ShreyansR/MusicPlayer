package com.example.musicplayer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MusicDB extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MusicDB";
    private static final String TABLE_NAME = "SONGS";
    private static final String SONG_ID = "id";
    private static final String SONG_TITLE = "name";
    private static final String SONG_LENGTH = "length";
    private static final String SONG_ARTIST = "artist";
    private static final String SONG_LINK = "link";

    public MusicDB(Context context) { super(context, DATABASE_NAME, null, DATABASE_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SONG_TABLE = "CREATE TABLE "+TABLE_NAME+"("+SONG_ID+" INTEGER PRIMARY KEY,"+SONG_TITLE+" TEXT,"+SONG_LINK+" TEXT)";
        db.execSQL(CREATE_SONG_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

    void addSong (SongClass song){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SONG_TITLE, song.getSongName());
        values.put(SONG_LINK, song.getSongLink());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    SongClass getSong(String title){
        SQLiteDatabase db = this.getReadableDatabase();
        SongClass song = new SongClass();

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+SONG_TITLE+" = ?",new String [] {title});
        c.moveToFirst();
        if(c.isNull(c.getColumnIndex(SONG_TITLE)))
        {
            db.close();
            return null;
        }
        song.setSongName(c.getString(c.getColumnIndex(SONG_TITLE)));
        song.setSongLink(c.getString(c.getColumnIndex(SONG_LINK)));
        db.close();
        return song;
    }

    void deleteSong(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        SongClass song = new SongClass();

        Cursor c = db.rawQuery("DELETE FROM "+TABLE_NAME+" WHERE "+SONG_TITLE+" = ?",new String [] {name});
        c.moveToFirst();
        song.setSongName(null);
        song.setSongLink(null);
        db.close();
    }

    void updateSong(String old_title, String new_title, String new_link){
        SQLiteDatabase db = this.getReadableDatabase();
        SongClass song = new SongClass();
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+SONG_TITLE+" = ?",new String [] {old_title});
        c.moveToFirst();
        if(c.isNull(c.getColumnIndex(SONG_TITLE)))
        {
            db.close();
        }
        db.execSQL("Update "+TABLE_NAME+" Set "+SONG_TITLE+" = '"+new_title+"',"+SONG_LINK+" = '"+new_link+"' WHERE "+SONG_TITLE+" = ?",new String [] {old_title});
        song.setSongName(c.getString(c.getColumnIndex(SONG_TITLE)));
        song.setSongLink(c.getString(c.getColumnIndex(SONG_LINK)));
        db.close();
    }

    ArrayList<String> getNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        String temptName = "";
        ArrayList<String> names = new ArrayList<>();
        String query = "SELECT "+ SONG_TITLE +" FROM "+TABLE_NAME;

        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        while(!(c.isAfterLast()))
        {
            temptName = c.getString(c.getColumnIndex(SONG_TITLE));
            names.add(temptName);
            c.moveToNext();
        }
        db.close();
        return names;
    }
}
