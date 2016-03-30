package com.example.jacob.proclaim;

import android.provider.BaseColumns;

/**
 * Created by jacob on 3/24/16.
 */
public final class ExternalDbContract {

    public static final String DB_NAME = "quotes.db";
    public static final int DB_VERSION = 1;

    private ExternalDbContract() {}

    public static abstract class QuoteEntry implements BaseColumns {

        public static final String TABLE_NAME = "quotes";

        public static final String QUOTE_ID = "_id";
        public static final String AUTHOR_FIRST_NAME = "Author First Name";
        public static final String AUTHOR_LAST_NAME = "Author Last Name";
        public static final String AUTHOR_GROUP_NAME = "Author Group Name";
        public static final String QUOTE = "Quote";
        public static final String TOPIC = "Topic";
        public static final String REFERENCE = "Reference";
        public static final String DATE = "Date";
        public static final String PAGE_NUMBER = "Page Number";
        public static final String POPULARITY = "Popularity";
        public static final String FAVORITE = "Favorite";
        public static final String USER_SUBMITTED = "User Submitted";
        public static final String FLAGGED = "Flagged";
    }
}
