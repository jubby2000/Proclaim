package com.example.jacob.proclaim;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

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
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            String[] projection = new String[]{ExternalDbContract.QuoteEntry._ID, ExternalDbContract.QuoteEntry.TOPIC};

            ContentResolver resolver = getContentResolver();
            Cursor cursor = resolver.query(
                    MyContentProvider.TopicSearch.CONTENT_URI,
                    projection,
                    ExternalDbContract.QuoteEntry.TOPIC + " = ?",//MyContentProvider.TopicSearch._ID + " = ? ",
                    new String[]{query},
                    null);

            if (cursor.moveToFirst()) {
                do {
                    long id = cursor.getLong(0);
                    String topic = cursor.getString(1);
                    Log.v(LOG_TAG, "ID is: " + id + ". And topic is: " + topic);
                    // do something meaningful
                } while (cursor.moveToNext());
            }

//            Intent detailIntent = new Intent(this, DetailActivity.class);
//            detailIntent.putExtra("Topic", query);
//            startActivity(detailIntent);
//            finish();
            //TODO perform a search on this string query
            if (cursor != null) {
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
