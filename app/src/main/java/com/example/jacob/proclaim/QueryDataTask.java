package com.example.jacob.proclaim;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

/**
 * Created by jacob on 5/18/16.
 */
public class QueryDataTask extends AsyncTask {

    Context mContext;
    ExternalDbOpenHelper mDbHelper = new ExternalDbOpenHelper(mContext);
    SQLiteDatabase db;

//    private final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
//
//    // can use UI thread here
//    protected void onPreExecute() {
//        this.dialog.setMessage("Inserting data...");
//        this.dialog.show();
//    }

    @Override
    protected Object doInBackground(Object[] params) {

        try {
            mDbHelper.createDataBase();
        } catch (Exception e) {
            throw new Error("Unable to create database");
        }

        try {
            mDbHelper.openDataBase();
        }catch(android.database.SQLException sqle) {
            throw sqle;
        }

        db = mDbHelper.getReadableDatabase();


        return null;
    }
}
