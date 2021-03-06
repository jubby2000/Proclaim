package com.example.jacob.proclaim;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.SearchRecentSuggestionsProvider;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class MyContentProvider extends SearchRecentSuggestionsProvider {

    private final String LOG_TAG = MyContentProvider.class.getSimpleName();

    private ExternalDbOpenHelper mOpenHelper;
    private static final String DBNAME = ExternalDbContract.DB_NAME;
    private SQLiteDatabase db;
    public static final String AUTHORITY = ExternalDbContract.CONTENT_AUTHORITY;
    public static final Uri CONTENT_URI = ExternalDbContract.QuoteEntry.CONTENT_URI;

//    private static final String[] SEARCH_SUGGEST_COLUMNS = new String[] {
//            "_id",
//            SearchManager.SUGGEST_COLUMN_TEXT_1,
//            SearchManager.SUGGEST_COLUMN_ICON_1,
//            SearchManager.SUGGEST_COLUMN_INTENT_ACTION,
//            SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID };


    private static final int ALL_ROWS = 1;
    private static final int SINGLE_ROW = 2;
    private static final int SEARCH_SUGGEST = 3;

//    private static final int AUTHOR_FIRST_NAME_LIST = 1;
//    private static final int AUTHOR_LAST_NAME_LIST = 2;
//    private static final int AUTHOR_GROUP_LIST = 3;
//    private static final int TOPIC_LIST = 5;
//    private static final int POPULARITY_LIST = 9;
//    private static final int FAVORITE_LIST = 10;
//    private static final int AUTHOR_FIRST_NAME_LIST_ID = 0;
//    private static final int AUTHOR_LAST_NAME_LIST_ID = 0;
//    private static final int AUTHOR_GROUP_LIST_ID = 0;
//    private static final int TOPIC_LIST_ID = 0;
//    private static final int POPULARITY_LIST_ID = 0;
//    private static final int FAVORITE_LIST_ID = 0;
    private static final UriMatcher URI_MATCHER;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

        URI_MATCHER.addURI(AUTHORITY, ExternalDbContract.QuoteEntry.TABLE_NAME, ALL_ROWS);
        URI_MATCHER.addURI(AUTHORITY, ExternalDbContract.QuoteEntry.TABLE_NAME + "/#", SINGLE_ROW);

        URI_MATCHER.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY, SEARCH_SUGGEST);
        URI_MATCHER.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY + "/*", SEARCH_SUGGEST);
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
//            case AUTHOR_FIRST_NAME_LIST:
//                return ExternalDbContract.QuoteEntry.CONTENT_TYPE;
//            case AUTHOR_LAST_NAME_LIST:
//                return ExternalDbContract.QuoteEntry.CONTENT_TYPE;
//            case AUTHOR_GROUP_LIST:
//                return ExternalDbContract.QuoteEntry.CONTENT_TYPE;
//            case TOPIC_LIST:
//                return ExternalDbContract.QuoteEntry.CONTENT_TYPE;
//            case POPULARITY_LIST:
//                return ExternalDbContract.QuoteEntry.CONTENT_TYPE;
//            case FAVORITE_LIST:
//                return ExternalDbContract.QuoteEntry.CONTENT_TYPE;
            case ALL_ROWS:
                return ExternalDbContract.QuoteEntry.CONTENT_TYPE;
            case SINGLE_ROW:
                return ExternalDbContract.QuoteEntry.CONTENT_ITEM_TYPE;
            case SEARCH_SUGGEST:
                return SearchManager.SUGGEST_MIME_TYPE;
//            case 0:
//                return ExternalDbContract.QuoteEntry.CONTENT_ITEM_TYPE;
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

//    private static final HashMap<String, String> SEARCH_AUTHOR_FULL_NAME_PROJECTION_MAP;
//    static {
//        SEARCH_AUTHOR_FULL_NAME_PROJECTION_MAP = new HashMap<String, String>();
//        SEARCH_AUTHOR_FULL_NAME_PROJECTION_MAP.put("_id", "_id");
//        SEARCH_AUTHOR_FULL_NAME_PROJECTION_MAP.put(SearchManager.SUGGEST_COLUMN_TEXT_1,
//                "\" " + ExternalDbContract.QuoteEntry.AUTHOR_FIRST_NAME + "\" || ' ' || \""
//                        + ExternalDbContract.QuoteEntry.AUTHOR_LAST_NAME
//                        + "\" AS " + SearchManager.SUGGEST_COLUMN_TEXT_1);
//    }
//
//    private static final HashMap<String, String> SEARCH_AUTHOR_GROUP_NAME_PROJECTION_MAP;
//    static {
//        SEARCH_AUTHOR_GROUP_NAME_PROJECTION_MAP = new HashMap<String, String>();
//        SEARCH_AUTHOR_GROUP_NAME_PROJECTION_MAP.put("_id", "_id");
//        SEARCH_AUTHOR_GROUP_NAME_PROJECTION_MAP.put(SearchManager.SUGGEST_COLUMN_TEXT_1,
//                "\" " + ExternalDbContract.QuoteEntry.AUTHOR_GROUP_NAME
//                        + "\" AS " + SearchManager.SUGGEST_COLUMN_TEXT_1);
//    }

//    private static final HashMap<String, String> SEARCH_PROJECTION_MAP;
//    static {
//
//        SEARCH_PROJECTION_MAP = new HashMap<String, String>();
//        SEARCH_PROJECTION_MAP.put("_id", "_id");
//        SEARCH_PROJECTION_MAP.put(SearchManager.SUGGEST_COLUMN_TEXT_1,
//                "SELECT CASE WHEN \"" + ExternalDbContract.QuoteEntry.AUTHOR_FIRST_NAME + "\" LIKE ? THEN \""
//                        + ExternalDbContract.QuoteEntry.AUTHOR_FIRST_NAME + "\" || ' ' || \"" + ExternalDbContract.QuoteEntry.AUTHOR_LAST_NAME
//                        + "\" WHEN \"" + ExternalDbContract.QuoteEntry.AUTHOR_LAST_NAME + "\" LIKE ? THEN \""
//                        + ExternalDbContract.QuoteEntry.AUTHOR_FIRST_NAME + "\" || ' ' || \"" + ExternalDbContract.QuoteEntry.AUTHOR_LAST_NAME
//                        + "\" WHEN \"" + ExternalDbContract.QuoteEntry.AUTHOR_GROUP_NAME + "\" LIKE ? THEN \""
//                        + ExternalDbContract.QuoteEntry.AUTHOR_GROUP_NAME
//                        + "\" WHEN " + ExternalDbContract.QuoteEntry.TOPIC + " LIKE ? THEN "
//                        + ExternalDbContract.QuoteEntry.TOPIC
//                        + " END AS " + SearchManager.SUGGEST_COLUMN_TEXT_1
//                        + ", _id FROM " + ExternalDbContract.QuoteEntry.TABLE_NAME
//                        + " WHERE (" + ExternalDbContract.QuoteEntry.TOPIC + " LIKE ? OR \""
//                        + ExternalDbContract.QuoteEntry.AUTHOR_FIRST_NAME + "\" LIKE ? OR \""
//                        + ExternalDbContract.QuoteEntry.AUTHOR_LAST_NAME + "\" LIKE ? OR \""
//                        + ExternalDbContract.QuoteEntry.AUTHOR_GROUP_NAME + "\" LIKE ?)"
//                        + " GROUP BY " + SearchManager.SUGGEST_COLUMN_TEXT_1
//                        + " ORDER BY " + SearchManager.SUGGEST_COLUMN_TEXT_1 + " ASC");

//                "\"" + ExternalDbContract.QuoteEntry.AUTHOR_FIRST_NAME + "\" || ' ' || \"" + ExternalDbContract.QuoteEntry.AUTHOR_LAST_NAME
//                        + "\", \"" + ExternalDbContract.QuoteEntry.AUTHOR_GROUP_NAME
//                        + "\", " + ExternalDbContract.QuoteEntry.TOPIC
//                        + "\" AS " + SearchManager.SUGGEST_COLUMN_TEXT_1);
                //        from quotes where firstname like '%bert%' OR `lastname` like '%bert%'"

//        "\"" + ExternalDbContract.QuoteEntry.AUTHOR_FIRST_NAME + "\" || ' ' || \"" + ExternalDbContract.QuoteEntry.AUTHOR_LAST_NAME
//        + "\" AS " + SearchManager.SUGGEST_COLUMN_TEXT_1 + ", _id FROM quotes"
//        + " UNION SELECT \"" + ExternalDbContract.QuoteEntry.AUTHOR_GROUP_NAME
//        + "\" AS " + SearchManager.SUGGEST_COLUMN_TEXT_1 + ", _id FROM quotes"
//        + " UNION SELECT " + ExternalDbContract.QuoteEntry.TOPIC
//        + " AS " + SearchManager.SUGGEST_COLUMN_TEXT_1);



//        ExternalDbContract.QuoteEntry.TOPIC
//                        + " OR \"" + ExternalDbContract.QuoteEntry.AUTHOR_FIRST_NAME
//                        + "\" || \" \" || \"" + ExternalDbContract.QuoteEntry.AUTHOR_LAST_NAME
//                        + "\" OR \"" + ExternalDbContract.QuoteEntry.AUTHOR_GROUP_NAME
//                        + " AS "
//                        + SearchManager.SUGGEST_COLUMN_TEXT_1);
//        SEARCH_PROJECTION_MAP.put(SearchManager.SUGGEST_COLUMN_INTENT_DATA,
//                ExternalDbContract.QuoteEntry.QUOTE_ID
//                        + " AS "
//                        + SearchManager.SUGGEST_COLUMN_INTENT_DATA);

//    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

//        final HashMap<String, String> SEARCH_PROJECTION_MAP;
//
//            SEARCH_PROJECTION_MAP = new HashMap<String, String>();
//            SEARCH_PROJECTION_MAP.put("_id", "_id AS _id");
//            SEARCH_PROJECTION_MAP.put(SearchManager.SUGGEST_COLUMN_TEXT_1,
//                    "CASE WHEN \"" + ExternalDbContract.QuoteEntry.AUTHOR_FIRST_NAME + "\" LIKE '%" + selectionArgs[0] + "%' THEN \""
//                            + ExternalDbContract.QuoteEntry.AUTHOR_FIRST_NAME + "\" || ' ' || \"" + ExternalDbContract.QuoteEntry.AUTHOR_LAST_NAME
//                            + "\" WHEN \"" + ExternalDbContract.QuoteEntry.AUTHOR_LAST_NAME + "\" LIKE '%" + selectionArgs[0] + "%' THEN \""
//                            + ExternalDbContract.QuoteEntry.AUTHOR_FIRST_NAME + "\" || ' ' || \"" + ExternalDbContract.QuoteEntry.AUTHOR_LAST_NAME
//                            + "\" WHEN \"" + ExternalDbContract.QuoteEntry.AUTHOR_GROUP_NAME + "\" LIKE '%" + selectionArgs[0] + "%' THEN \""
//                            + ExternalDbContract.QuoteEntry.AUTHOR_GROUP_NAME
//                            + "\" WHEN " + ExternalDbContract.QuoteEntry.TOPIC + " LIKE '%" + selectionArgs[0] + "%' THEN "
//                            + ExternalDbContract.QuoteEntry.TOPIC
//                            + " END AS " + SearchManager.SUGGEST_COLUMN_TEXT_1);

//        SELECT CASE WHEN "Author First Name" LIKE '%th%' THEN "Author First Name" || ' ' || "Author Last Name"
//        WHEN "Author Last Name" LIKE '%th%' THEN "Author First Name" || ' ' || "Author Last Name"
//        WHEN "Author Group Name" LIKE '%th%' THEN "Author Group Name"
//        WHEN Topic LIKE '%th%' THEN Topic END AS suggest_text_1, _id FROM quotes
//        WHERE (Topic LIKE '%th%' OR "Author First Name" LIKE '%a%'
//        OR "Author Group Name" LIKE '%th%' OR "Author Last Name" LIKE '%th%') GROUP BY suggest_text_1 ORDER BY suggest_text_1 ASC

//        SELECT CASE WHEN "Author First Name" LIKE '%a%' THEN "Author First Name" || ' ' || "Author Last Name"
//        WHEN "Author Last Name" LIKE '%a%' THEN "Author First Name" || ' ' || "Author Last Name"
//        WHEN "Author Group Name" LIKE '%a%' THEN "Author Group Name"
//        WHEN Topic LIKE '%a%' THEN Topic END AS suggest_text_1, _id FROM quotes
//        WHERE (Topic LIKE ? OR "Author First Name" LIKE ? OR "Author Last Name" LIKE ?
//        OR "Author Group Name" LIKE ?) GROUP BY suggest_text_1 HAVING %a% ORDER BY suggest_text_1 ASC LIMIT 50

//        Log.v(LOG_TAG, "CASE WHEN \"" + ExternalDbContract.QuoteEntry.AUTHOR_FIRST_NAME + "\" LIKE '%" + selectionArgs[0] + "%' THEN \""
//                + ExternalDbContract.QuoteEntry.AUTHOR_FIRST_NAME + "\" || ' ' || \"" + ExternalDbContract.QuoteEntry.AUTHOR_LAST_NAME
//                + "\" WHEN \"" + ExternalDbContract.QuoteEntry.AUTHOR_LAST_NAME + "\" LIKE '%" + selectionArgs[0] + "%' THEN \""
//                + ExternalDbContract.QuoteEntry.AUTHOR_FIRST_NAME + "\" || ' ' || \"" + ExternalDbContract.QuoteEntry.AUTHOR_LAST_NAME
//                + "\" WHEN \"" + ExternalDbContract.QuoteEntry.AUTHOR_GROUP_NAME + "\" LIKE '%" + selectionArgs[0] + "%' THEN \""
//                + ExternalDbContract.QuoteEntry.AUTHOR_GROUP_NAME
//                + "\" WHEN " + ExternalDbContract.QuoteEntry.TOPIC + " LIKE '%" + selectionArgs[0] + "%' THEN "
//                + ExternalDbContract.QuoteEntry.TOPIC
//                + " END AS " + SearchManager.SUGGEST_COLUMN_TEXT_1);

        Log.v(LOG_TAG, uri.toString());

        String groupBy = null;
        db = mOpenHelper.getReadableDatabase();

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(ExternalDbContract.QuoteEntry.TABLE_NAME);
        boolean useSuggest = false;

        switch (URI_MATCHER.match(uri)) {
            case SEARCH_SUGGEST:
                Log.v(LOG_TAG, "Query is: " + selectionArgs[0]);
                if (selectionArgs[0].length() < 1) {
                    break;
                } else {
                    if(TextUtils.isEmpty(sortOrder)) {
                        sortOrder = SearchManager.SUGGEST_COLUMN_TEXT_1 + " ASC";
                    }
                    selection = "LOWER("+ ExternalDbContract.QuoteEntry.TOPIC + ") LIKE LOWER(?)"
                        + " OR LOWER(\"" + ExternalDbContract.QuoteEntry.AUTHOR_FIRST_NAME + "\") LIKE LOWER(?)"
                        + " OR LOWER(\"" + ExternalDbContract.QuoteEntry.AUTHOR_LAST_NAME + "\") LIKE LOWER(?)"
                        + " OR LOWER(\"" + ExternalDbContract.QuoteEntry.AUTHOR_GROUP_NAME + "\") LIKE (?)";
                    selectionArgs = new String[] {"%" + selectionArgs[0] + "%"};
                    groupBy = SearchManager.SUGGEST_COLUMN_TEXT_1;
//                    builder.setProjectionMap(SEARCH_PROJECTION_MAP);
                    useSuggest = true;
                    break;
                }
            case ALL_ROWS:
                useSuggest = false;
//                selectionArgs = new String[] {"%" + selectionArgs[0] + "%"};
                Log.v(LOG_TAG, "I made it to ALL_ROWS!");
                break;
            case SINGLE_ROW:
                Log.v(LOG_TAG, "I made it to SINGLE_ROW!");
//                builder.appendWhere(ExternalDbContract.QuoteEntry.QUOTE_ID + "-" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException(
                        "Unsupported URI: " + uri);
        }

//        String query = builder.buildQuery(projection, selection, groupBy, selectionArgs[0], sortOrder, "50");
//        Log.v(LOG_TAG, query);

        Cursor cursor;

        if(useSuggest) {
            Log.v(LOG_TAG, "I made it to useSuggest!");
            //This first portion is to select first name+last name, group name, and topics all in one as suggestions
            cursor = db.rawQuery("SELECT CASE WHEN LOWER(\"" + ExternalDbContract.QuoteEntry.AUTHOR_FIRST_NAME + "\") LIKE LOWER(\"" + selectionArgs[0] + "\") THEN \""
                    + ExternalDbContract.QuoteEntry.AUTHOR_FIRST_NAME + "\" || ' ' || \"" + ExternalDbContract.QuoteEntry.AUTHOR_LAST_NAME
                    + "\" WHEN LOWER(\"" + ExternalDbContract.QuoteEntry.AUTHOR_LAST_NAME + "\") LIKE LOWER(\"" + selectionArgs[0] + "\") THEN \""
                    + ExternalDbContract.QuoteEntry.AUTHOR_FIRST_NAME + "\" || ' ' || \"" + ExternalDbContract.QuoteEntry.AUTHOR_LAST_NAME
                    + "\" WHEN LOWER(\"" + ExternalDbContract.QuoteEntry.AUTHOR_GROUP_NAME + "\") LIKE LOWER(\"" + selectionArgs[0] + "\") THEN \""
                    + ExternalDbContract.QuoteEntry.AUTHOR_GROUP_NAME
                    + "\" WHEN LOWER(" + ExternalDbContract.QuoteEntry.TOPIC + ") LIKE LOWER(\"" + selectionArgs[0] + "\") THEN "
                    + ExternalDbContract.QuoteEntry.TOPIC
                    + " END AS " + SearchManager.SUGGEST_COLUMN_TEXT_1 + ", "

                    //This portion is to select the same thing as above, but for the intent data,
                    //which the detail activity only knows how to handle as a string, not a column id
                    + "CASE WHEN LOWER(\"" + ExternalDbContract.QuoteEntry.AUTHOR_FIRST_NAME + "\") LIKE LOWER(\"" + selectionArgs[0] + "\") THEN \""
                    + ExternalDbContract.QuoteEntry.AUTHOR_FIRST_NAME + "\" || ' ' || \"" + ExternalDbContract.QuoteEntry.AUTHOR_LAST_NAME
                    + "\" WHEN LOWER(\"" + ExternalDbContract.QuoteEntry.AUTHOR_LAST_NAME + "\") LIKE LOWER(\"" + selectionArgs[0] + "\") THEN \""
                    + ExternalDbContract.QuoteEntry.AUTHOR_FIRST_NAME + "\" || ' ' || \"" + ExternalDbContract.QuoteEntry.AUTHOR_LAST_NAME
                    + "\" WHEN LOWER(\"" + ExternalDbContract.QuoteEntry.AUTHOR_GROUP_NAME + "\") LIKE LOWER(\"" + selectionArgs[0] + "\") THEN \""
                    + ExternalDbContract.QuoteEntry.AUTHOR_GROUP_NAME
                    + "\" WHEN LOWER(" + ExternalDbContract.QuoteEntry.TOPIC + ") LIKE LOWER(\"" + selectionArgs[0] + "\") THEN "
                    + ExternalDbContract.QuoteEntry.TOPIC
                    + " END AS " + SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID + ", _id"

                    //More flexible search parameters, sorting etc.
                    + " FROM " + ExternalDbContract.QuoteEntry.TABLE_NAME
                    + " WHERE (LOWER(" + ExternalDbContract.QuoteEntry.TOPIC + ") LIKE LOWER(\"" + selectionArgs[0] + "\") OR LOWER(\""
                    + ExternalDbContract.QuoteEntry.AUTHOR_FIRST_NAME + "\") LIKE LOWER(\"" + selectionArgs[0] + "\") OR LOWER(\""
                    + ExternalDbContract.QuoteEntry.AUTHOR_LAST_NAME + "\") LIKE LOWER(\"" + selectionArgs[0] + "\") OR LOWER (\""
                    + ExternalDbContract.QuoteEntry.AUTHOR_GROUP_NAME + "\") LIKE LOWER(\"" + selectionArgs[0] + "\"))"
                    + " GROUP BY " + SearchManager.SUGGEST_COLUMN_TEXT_1
                    + " ORDER BY " + SearchManager.SUGGEST_COLUMN_TEXT_1 + " ASC", null);
        } else {
            Log.v(LOG_TAG, "I made it to queryBuilder!");
            cursor = builder.query(
                    db,
                    projection,
                    selection,
                    selectionArgs,
                    groupBy,
                    null,
                    sortOrder);
        }

        // if we want to be notified of any changes:
//        if (useAuthorityUri) {
//            cursor.setNotificationUri(
//                    getContext().getContentResolver(),
//                    ExternalDbContract.BASE_CONTENT_URI);
//        }
//        else {
        Log.v(LOG_TAG, "I made it to setNotificationUri");
        cursor.setNotificationUri(
                    getContext().getContentResolver(),
                    uri);
//        }
        return cursor;

    }

//    public String buildUnionQuery (String[] subQueries, String sortOrder, String limit) {
//
//        return subQueries;
//    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        db = mOpenHelper.getWritableDatabase();
//        throw new UnsupportedOperationException("Not yet implemented");
        return 0;
    }

//    private Cursor getSuggestions(String query) {
//        query = query.toLowerCase();
//        String[] columns = new String[] { BaseColumns._ID,
//                ExternalDbOpenHelper.KEY_DATA,
//                SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID };
//        return ExternalDbOpenHelper.getRecordMatches(query, columns);
//    }
}
