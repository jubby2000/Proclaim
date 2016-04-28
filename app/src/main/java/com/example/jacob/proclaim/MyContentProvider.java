package com.example.jacob.proclaim;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.tjeannin.provigen.ProviGenBaseContract;
import com.tjeannin.provigen.ProviGenOpenHelper;
import com.tjeannin.provigen.ProviGenProvider;
import com.tjeannin.provigen.annotation.Column;
import com.tjeannin.provigen.annotation.ContentUri;

public class MyContentProvider extends ProviGenProvider {

    private ExternalDbOpenHelper mOpenHelper;
    private static final String DBNAME = ExternalDbContract.DB_NAME;
    private SQLiteDatabase db;

    public MyContentProvider() {
    }

    private static Class[] contracts = new Class[]{TopicSearch.class};

    @Override
    public SQLiteOpenHelper openHelper(Context context) {
        return new ProviGenOpenHelper(getContext(), DBNAME, null, 1, contracts);
    }

    @Override
    public Class[] contractClasses() {
        return contracts;
    }

    public interface TopicSearch extends ProviGenBaseContract {

        @Column(Column.Type.TEXT)
        String TOPIC = "string";

        @ContentUri
        Uri CONTENT_URI = Uri.parse(ExternalDbContract.BASE_CONTENT_URI + "/" + ExternalDbContract.QuoteEntry.TABLE_NAME);
    }

//    public interface AuthorSearch extends ProviGenBaseContract {
//
//        @Column(Column.Type.TEXT)
//        String AUTHOR = "string";
//
//        @ContentUri
//        Uri CONTENT_URI = Uri.parse(ExternalDbContract.BASE_CONTENT_URI + "/" + ExternalDbContract.QuoteEntry.TABLE_NAME);
//    }

//    @Override
//    public int delete(Uri uri, String selection, String[] selectionArgs) {
//        // Implement this to handle requests to delete one or more rows.
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
//
//    @Override
//    public String getType(Uri uri) {
//        // TODO: Implement this to handle requests for the MIME type of the data
//        // at the given URI.
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
//
//    @Override
//    public Uri insert(Uri uri, ContentValues values) {
//        // TODO: Implement this to handle requests to insert a new row.
//
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
//
//    @Override
//    public boolean onCreate() {
//
//        /*
//         * Creates a new helper object. This method always returns quickly.
//         * Notice that the database itself isn't created or opened
//         * until SQLiteOpenHelper.getWritableDatabase is called
//         */
//        mOpenHelper = new ExternalDbOpenHelper(
//                getContext()        // the application context
//                //add these back in if I migrate things from the dbopenhelper to the content provider
////                DBNAME,              // the name of the database)
////                null,                // uses the default SQLite cursor
////                1                    // the version number
//        );
//
//        return true;
//    }
//
//    @Override
//    public Cursor query(Uri uri, String[] projection, String selection,
//                        String[] selectionArgs, String sortOrder) {
//        // TODO: Implement this to handle query requests from clients.
////        throw new UnsupportedOperationException("Not yet implemented");
//
//        db = mOpenHelper.getReadableDatabase();
//        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
//
//        sqLiteQueryBuilder.setTables(ExternalDbContract.QuoteEntry.TABLE_NAME);
//
//
//        selectionArgs[0] = "%" + selectionArgs[0] + "%";
//
//
//        Cursor cursor = sqLiteQueryBuilder.query(db, projection, selection, selectionArgs, sortOrder);
//
//    }
//
//    @Override
//    public int update(Uri uri, ContentValues values, String selection,
//                      String[] selectionArgs) {
//        // TODO: Implement this to handle requests to update one or more rows.
//        db = mOpenHelper.getWritableDatabase();
////        throw new UnsupportedOperationException("Not yet implemented");
//        return 0;
//    }
}
