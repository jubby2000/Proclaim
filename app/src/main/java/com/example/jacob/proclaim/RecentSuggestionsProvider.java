package com.example.jacob.proclaim;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by jacob on 5/4/16.
 */
public class RecentSuggestionsProvider extends SearchRecentSuggestionsProvider {

    public static final String AUTHORITY = RecentSuggestionsProvider.class.getName();

    public static final int MODE = DATABASE_MODE_QUERIES;

    public RecentSuggestionsProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}