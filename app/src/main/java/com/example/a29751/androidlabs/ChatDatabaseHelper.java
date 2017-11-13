package com.example.a29751.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by 29751 on 2017-10-11.
 */

public class ChatDatabaseHelper extends SQLiteOpenHelper{
    public final static String DATABASE_NAME = "Message.db";
    public final static int VERSION_NUM = 1;
    public final static String TABLE_NAME = "MYTable";
    public final static String ID_HEADER = "ID" ;
    public final static String MESSAGE_HEADER = "MESSAGE";

    public ChatDatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME,null,VERSION_NUM);

        Log.i("ChatDatabaseHelper ", "new ChatDatabaseHelper ");
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_MSG = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_HEADER  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MESSAGE_HEADER + " TEXT " + ")";
        db.execSQL(CREATE_TABLE_MSG);

       /* db.execSQL("CREATE TABLE " + TABLE_NAME+
           "("+ID_HEADER +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MESSAGE_HEADER + "text)"+");"
        );*/
        Log.i("ChatDatabaseHelper", "Calling onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + "newVersion="  + newVersion);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); //delete what was there previously
        onCreate(db);
        Log.i("ChatDatabaseHelper", "Calling onCreate");
        Log.i("ChatDatabaseHelper", "Calling onDowngrade, newVersion=" + newVersion + "oldVersion=" + oldVersion);

    }

    @Override
    public void onOpen(SQLiteDatabase db)
    {
        Log.i("ChatDatabaseHelper ", "onOpen was called");
    }
}
