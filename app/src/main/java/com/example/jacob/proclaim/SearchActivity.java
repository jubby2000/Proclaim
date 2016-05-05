package com.example.jacob.proclaim;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ListView;

/**
 * Created by jacob on 4/23/16.
 */
public class SearchActivity extends ListActivity {

    private final String LOG_TAG = SearchActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        handleIntent(getIntent());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        Intent intent = new Intent(this, DetailActivity.class);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            String[] projection = new String[]{
                    ExternalDbContract.QuoteEntry._ID,
                    "\"" + ExternalDbContract.QuoteEntry.AUTHOR_FIRST_NAME + "\"",
                    "\"" + ExternalDbContract.QuoteEntry.AUTHOR_LAST_NAME + "\"",
                    "\"" + ExternalDbContract.QuoteEntry.AUTHOR_GROUP_NAME + "\"",
                    ExternalDbContract.QuoteEntry.TOPIC};

            ContentResolver resolver = getContentResolver();



//            Cursor cursor = resolver.query(
//                    MyContentProvider.TopicSearch.CONTENT_URI,
//                    projection,
//                    ExternalDbContract.QuoteEntry.TOPIC + " = ?" + " GROUP BY " + ExternalDbContract.QuoteEntry.TOPIC,//MyContentProvider.TopicSearch._ID + " = ? ",
//                    new String[]{query},
//                    null);

//            Cursor cursor = resolver.query(
//                    Uri.withAppendedPath(MyContentProvider.TopicSearch.CONTENT_URI, query),
//                    null, "", null, "");


            //Didn't realize that spaces in table column names are bad news.
            //Employing laziness by escaping quotes instead of fixing spaces.
            Cursor cursor = resolver.query(
                    ExternalDbContract.QuoteEntry.CONTENT_URI,
                    projection,
                    ExternalDbContract.QuoteEntry.TOPIC + " LIKE ?"
                            + " OR \"" + ExternalDbContract.QuoteEntry.AUTHOR_FIRST_NAME + "\" LIKE ?"
                            + " OR \"" + ExternalDbContract.QuoteEntry.AUTHOR_LAST_NAME + "\" LIKE ?"
                            + " OR \"" + ExternalDbContract.QuoteEntry.AUTHOR_GROUP_NAME + "\" LIKE ?",
                    new String[] {query},
                    null);

            if (cursor.moveToFirst()) {
                do {
                    long id = cursor.getLong(0);
                    String first = cursor.getString(1);
                    String last = cursor.getString(2);
                    String group = cursor.getString(3);
                    String topic = cursor.getString(4);
                    Log.v(LOG_TAG, "First: " + first + ". Last: " + last + ". Group: " + group + ". And topic is: " + topic);
                    // do something meaningful
                } while (cursor.moveToNext());
            }
//
////            Intent detailIntent = new Intent(this, DetailActivity.class);
////            detailIntent.putExtra("Topic", query);
////            startActivity(detailIntent);
////            finish();
//            //TODO perform a search on this string query
            if (cursor != null) {

                //Save the previous query to be provided as a suggestion later.
                SearchRecentSuggestions suggestions =
                        new SearchRecentSuggestions(this,
                                RecentSuggestionsProvider.AUTHORITY,
                                RecentSuggestionsProvider.MODE);
                suggestions.saveRecentQuery(query, null);

                cursor.close();
            }
        }
    }

    //Might need this later if I decide to include the search feature as a fragment.
//    @Override
//    public void onFragmentInteraction(Uri uri) {
//
//    }
}
