package com.example.user.childhoodclass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="CHStory";
    private static final int DB_VERSION=1;
    private static final String TABLE_NAME ="Story";
    private static final String COL_id="id";
    private static final String COL_content="content";
    private static final String COL_image="image";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " ( " +
                    COL_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "  +
                    COL_content + " TEXT, " +
                    COL_image + " BLOB); ";

    public  MySQLiteOpenHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public List<Story> getallstory(){
        SQLiteDatabase db = getReadableDatabase();
        String[] columns={COL_id,COL_content,COL_image};
        Cursor cursor = db.query(TABLE_NAME,columns,null,null,null,null,null);

        List<Story> storyList = new ArrayList<>();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String content = cursor.getString(1);
            byte[] image = cursor.getBlob(2);

            Story story = new Story(id,content,image);
            storyList.add(story);
        }
        cursor.close();
        return storyList;
    }

    public Story findbyid(int id){
        SQLiteDatabase db = getWritableDatabase();
        String[] columns = {COL_content,COL_image};
        String selection = COL_id + " = ?;";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query(TABLE_NAME,columns,selection,selectionArgs,null,null,null);
        Story story=null ;

        if (cursor.moveToNext()){
            String content = cursor.getString(0);
            byte[] image = cursor.getBlob(1);
            story = new Story(id,content,image);
        }
        cursor.close();
        return story;
    }

    public long insert(Story story){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_content,story.getContent());
        values.put(COL_image,story.getImage());
        //新成功回傳ID 新增失敗回傳-1
        return db.insert(TABLE_NAME,null,values);
    }

    public int update(Story story){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_content,story.getContent());
        values.put(COL_image,story.getImage());
        String whereClause = COL_id + " = ?;";
        String[] whereArgs ={Integer.toString(story.getId())};
        return db.update(TABLE_NAME,values,whereClause,whereArgs);
    }

    public int deletebyid(int id){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = COL_id + " = ?;";
        String[] whereArgs ={String.valueOf(id)};
        return db.delete(TABLE_NAME,whereClause,whereArgs);
    }
}
