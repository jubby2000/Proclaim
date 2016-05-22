package com.example.jacob.proclaim;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

/**
 * Created by jacob on 5/18/16.
 */
public class QueryDataTask extends AsyncTask<Object, Void, Cursor> {

    Context mContext;
    public ProgressDialog progress;

    public QueryDataTask(Context context, ProgressDialog progress) {
        this.mContext = context;
        this.progress = progress;
        this.progress.setMessage("Getting you that sweet data...");
    }

//    private final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
//
//    // can use UI thread here
//    protected void onPreExecute() {
//        this.dialog.setMessage("Inserting data...");
//        this.dialog.show();
//    }


//    int result=-1;
//    String Data;

    public void onPreExecute() {
        progress.show();
    }

    @Override
    protected Cursor doInBackground(Object[] params) {

        ExternalDbOpenHelper mDbHelper = new ExternalDbOpenHelper(mContext);
        SQLiteDatabase db;

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

        String query = (String) params[0];
        String[] args = (String[]) params[1];

        Cursor cursor = db.rawQuery(query, args);
        cursor.close();

        progress.dismiss();
        return cursor;
    }
}
