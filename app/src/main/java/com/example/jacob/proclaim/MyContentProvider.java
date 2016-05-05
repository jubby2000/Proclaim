package com.example.jacob.proclaim;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class MyContentProvider extends ContentProvider {

    private final String LOG_TAG = MyContentProvider.class.getSimpleName();

    private ExternalDbOpenHelper mOpenHelper;
    private static final String DBNAME = ExternalDbContract.DB_NAME;
    private SQLiteDatabase db;

    private static final int AUTHOR_FIRST_NAME_LIST = 1;
    private static final int AUTHOR_LAST_NAME_LIST = 2;
    private static final int AUTHOR_GROUP_LIST = 3;
    private static final int TOPIC_LIST = 5;
    private static final int POPULARITY_LIST = 9;
    private static final int FAVORITE_LIST = 10;
    private static final int AUTHOR_FIRST_NAME_LIST_ID = 0;
    private static final int AUTHOR_LAST_NAME_LIST_ID = 0;
    private static final int AUTHOR_GROUP_LIST_ID = 0;
    private static final int TOPIC_LIST_ID = 0;
    private static final int POPULARITY_LIST_ID = 0;
    private static final int FAVORITE_LIST_ID = 0;
    private static final UriMatcher URI_MATCHER;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(ExternalDbContract.CONTENT_AUTHORITY,
                "quotes/" + SearchManager.SUGGEST_URI_PATH_QUERY + "/*",
                0);
        URI_MATCHER.addURI(ExternalDbContract.CONTENT_AUTHORITY,
                "quotes/",
                AUTHOR_FIRST_NAME_LIST);
//        URI_MATCHER.addURI(ExternalDbContract.CONTENT_AUTHORITY,
//                "Author First Name/*",
//                AUTHOR_FIRST_NAME_LIST_ID);
//        URI_MATCHER.addURI(ExternalDbContract.CONTENT_AUTHORITY,
//                "Author Last Name",
//                AUTHOR_LAST_NAME_LIST);
//        URI_MATCHER.addURI(ExternalDbContract.CONTENT_AUTHORITY,
//                "Author Last Name/#",
//                AUTHOR_LAST_NAME_LIST_ID);
//        URI_MATCHER.addURI(ExternalDbContract.CONTENT_AUTHORITY,
//                "Author Group Name",
//                AUTHOR_GROUP_LIST);
//        URI_MATCHER.addURI(ExternalDbContract.CONTENT_AUTHORITY,
//                "Author Group Name/#",
//                AUTHOR_GROUP_LIST_ID);
//        URI_MATCHER.addURI(ExternalDbContract.CONTENT_AUTHORITY,
//                "Topic",
//                TOPIC_LIST);
//        URI_MATCHER.addURI(ExternalDbContract.CONTENT_AUTHORITY,
//                "Topic/*",
//                TOPIC_LIST_ID);
//        URI_MATCHER.addURI(ExternalDbContract.CONTENT_AUTHORITY,
//                "Popularity",
//                POPULARITY_LIST);
//        URI_MATCHER.addURI(ExternalDbContract.CONTENT_AUTHORITY,
//                "Popularity/#",
//                POPULARITY_LIST_ID);
//        URI_MATCHER.addURI(ExternalDbContract.CONTENT_AUTHORITY,
//                "Favorite",
//                FAVORITE_LIST);
//        URI_MATCHER.addURI(ExternalDbContract.CONTENT_AUTHORITY,
//                "Favorite/#",
//                FAVORITE_LIST_ID);
//        URI_MATCHER.addURI(ExternalDbContract.CONTENT_AUTHORITY,
//                SearchManager.SUGGEST_URI_PATH_QUERY,
//                FAVORITE_LIST_ID);
    }

    public MyContentProvider() {
    }

    @Override
    public boolean onCreate() {

        /*
         * Creates a new helper object. This method always returns quickly.
         * Notice that the database itself isn't created or opened
         * until SQLiteOpenHelper.getWritableDatabase is called
         */
        mOpenHelper = new ExternalDbOpenHelper(
                getContext()        // the application context
                //add these back in if I migrate things from the dbopenhelper to the content provider
//                DBNAME,              // the name of the database)
//                null,                // uses the default SQLite cursor
//                1                    // the version number
        );

        return true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        switch (URI_MATCHER.match(uri)) {
            case AUTHOR_FIRST_NAME_LIST:
                return ExternalDbContract.QuoteEntry.CONTENT_TYPE;
            case AUTHOR_LAST_NAME_LIST:
                return ExternalDbContract.QuoteEntry.CONTENT_TYPE;
            case AUTHOR_GROUP_LIST:
                return ExternalDbContract.QuoteEntry.CONTENT_TYPE;
            case TOPIC_LIST:
                return ExternalDbContract.QuoteEntry.CONTENT_TYPE;
            case POPULARITY_LIST:
                return ExternalDbContract.QuoteEntry.CONTENT_TYPE;
            case FAVORITE_LIST:
                return ExternalDbContract.QuoteEntry.CONTENT_TYPE;
            case 0:
                return ExternalDbContract.QuoteEntry.CONTENT_ITEM_TYPE;
            default:
                return null;
        }
//        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
//        throw new UnsupportedOperationException("Not yet implemented");

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

        db = mOpenHelper.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        boolean useAuthorityUri = false;
        Log.v(LOG_TAG, uri.toString());
        switch (URI_MATCHER.match(uri)) {
            case AUTHOR_FIRST_NAME_LIST:
                builder.setTables(ExternalDbContract.QuoteEntry.TABLE_NAME);
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = ExternalDbContract.QuoteEntry.SORT_ORDER_DEFAULT;
                }
                useAuthorityUri = true;
                break;
            case AUTHOR_LAST_NAME_LIST:
                builder.setTables(ExternalDbContract.QuoteEntry.TABLE_NAME);
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = ExternalDbContract.QuoteEntry.SORT_ORDER_DEFAULT;
                }
                useAuthorityUri = true;
                break;
            case AUTHOR_GROUP_LIST:
                builder.setTables(ExternalDbContract.QuoteEntry.TABLE_NAME);
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = ExternalDbContract.QuoteEntry.SORT_ORDER_DEFAULT;
                }
                useAuthorityUri = true;
                break;
            case TOPIC_LIST:
                builder.setTables(ExternalDbContract.QuoteEntry.TABLE_NAME);
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = ExternalDbContract.QuoteEntry.SORT_ORDER_DEFAULT;
                }
                useAuthorityUri = true;
                break;
            case POPULARITY_LIST:
                builder.setTables(ExternalDbContract.QuoteEntry.TABLE_NAME);
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = ExternalDbContract.QuoteEntry.SORT_ORDER_DEFAULT;
                }
                break;
            case FAVORITE_LIST:
                builder.setTables(ExternalDbContract.QuoteEntry.TABLE_NAME);
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = ExternalDbContract.QuoteEntry.SORT_ORDER_DEFAULT;
                }
                break;
            case 0:
                builder.setTables(ExternalDbContract.QuoteEntry.TABLE_NAME);
                // limit query to one row at most:
                builder.appendWhere(ExternalDbContract.QuoteEntry._ID + " = " +
                        uri.getLastPathSegment());
                useAuthorityUri = true;
            default:
                throw new IllegalArgumentException(
                        "Unsupported URI: " + uri);
        }
        Cursor cursor =
                builder.query(
                        db,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
        // if we want to be notified of any changes:
        if (useAuthorityUri) {
            cursor.setNotificationUri(
                    getContext().getContentResolver(),
                    ExternalDbContract.BASE_CONTENT_URI);
        }
        else {
            cursor.setNotificationUri(
                    getContext().getContentResolver(),
                    uri);
        }
        return cursor;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        db = mOpenHelper.getWritableDatabase();
//        throw new UnsupportedOperationException("Not yet implemented");
        return 0;
    }

//    private static Class[] contracts = new Class[]{TopicSearch.class};
//
//    @Override
//    public SQLiteOpenHelper openHelper(Context context) {
//        return new ProviGenOpenHelper(getContext(), DBNAME, null, 1, contracts);
//    }
//
//    @Override
//    public Class[] contractClasses() {
//        return contracts;
//    }
//
//    public interface TopicSearch extends ProviGenBaseContract {
//
//        @Column(Column.Type.TEXT)
//        String TOPIC = "string";
//
//        @ContentUri
//        Uri CONTENT_URI = Uri.parse(ExternalDbContract.BASE_CONTENT_URI + "/" + ExternalDbContract.QuoteEntry.TABLE_NAME);
//    }

//    public interface AuthorSearch extends ProviGenBaseContract {
//
//        @Column(Column.Type.TEXT)
//        String AUTHOR = "string";
//
//        @ContentUri
//        Uri CONTENT_URI = Uri.parse(ExternalDbContract.BASE_CONTENT_URI + "/" + ExternalDbContract.QuoteEntry.TABLE_NAME);
//    }
}
