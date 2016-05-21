package com.example.jacob.proclaim;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

/**
 * Created by jacob on 5/16/16.
 */
public class QuoteWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory, Loader.OnLoadCompleteListener<Cursor> {

    private static final String LOG_TAG = QuoteWidgetRemoteViewsFactory.class.getSimpleName();

    Context context = null;
    int appWidgetId;
    CursorLoader mCursorLoader;
    private static final int LOADER = 0;

    private ArrayList widgetList = new ArrayList();
//    private ExternalDbOpenHelper dbhelper;

    public QuoteWidgetRemoteViewsFactory(Context context, Intent intent)
    {
        Log.v(LOG_TAG, "QuoteWidgetRemoteViewsFactory called.");
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
//        Log.d("AppWidgetId", String.valueOf(appWidgetId));
//        populateListItem();

        Log.v(LOG_TAG, "Cursor loader started.");
        String[] projection = new String[] {
                ExternalDbContract.QuoteEntry._ID,
                "\"" + ExternalDbContract.QuoteEntry.AUTHOR_FIRST_NAME + "\"",
                "\"" + ExternalDbContract.QuoteEntry.AUTHOR_LAST_NAME + "\"",
                "\"" + ExternalDbContract.QuoteEntry.AUTHOR_GROUP_NAME + "\"",
                ExternalDbContract.QuoteEntry.TOPIC,
                ExternalDbContract.QuoteEntry.QUOTE,
                ExternalDbContract.QuoteEntry.REFERENCE};
        String sortOrder = "RANDOM() LIMIT 1";
        mCursorLoader = new CursorLoader(
                context,
                ExternalDbContract.QuoteEntry.CONTENT_URI,
                projection,
                null,
                null,
                sortOrder);
        mCursorLoader.registerListener(LOADER, this);
        mCursorLoader.startLoading();

    }

    public void populateListItem(Cursor cursor) {

        Log.v(LOG_TAG, "populateListItem called.");

        String mBold = context.getResources().getString(R.string.format_text_bold);
        String mItalics = context.getResources().getString(R.string.format_text_italics);
        String mUnderline = context.getResources().getString(R.string.format_text_underline);
        String mQuotationMark = context.getResources().getString(R.string.format_text_quotation_mark);

        String first = "";
        String last = "";
        String group = "";
        String topic = "";
        String quote = "";
        String reference = "";

//        String[] projection = new String[] {
//                ExternalDbContract.QuoteEntry._ID,
//                "\"" + ExternalDbContract.QuoteEntry.AUTHOR_FIRST_NAME + "\"",
//                "\"" + ExternalDbContract.QuoteEntry.AUTHOR_LAST_NAME + "\"",
//                "\"" + ExternalDbContract.QuoteEntry.AUTHOR_GROUP_NAME + "\"",
//                ExternalDbContract.QuoteEntry.TOPIC,
//                ExternalDbContract.QuoteEntry.QUOTE,
//                ExternalDbContract.QuoteEntry.REFERENCE
//        };
//
//        String sortOrder = "RANDOM() LIMIT 1";
//
//        Cursor cursor = context.getContentResolver().query(
//                ExternalDbContract.QuoteEntry.CONTENT_URI,
//                projection,
//                null,
//                null,
//                sortOrder);

        if(cursor != null) {


            if (cursor.moveToFirst()) {
                do {
                    long id = cursor.getLong(0);
                    first = cursor.getString(1);
                    last = cursor.getString(2);
                    group = cursor.getString(3);
                    topic = cursor.getString(4);
                    quote = cursor.getString(5);
                    reference = cursor.getString(6);
//                Log.v(LOG_TAG, "First: " + first + ". Last: " + last + ". Group: " + group + ". Quote: " + quote + ". And topic is: " + topic);
                } while (cursor.moveToNext());
            }


            cursor.close();
        }

        String author = "";
        if (group == null) {
            author = first + " " + last;
        } else {
            author = group;
        }

        quote = quote.replace(mQuotationMark, "\"");
        CharSequence widgetText = quote;
        widgetText = DetailCardViewAdapter.setSpanBetweenTokens(widgetText, mBold);
        widgetText = DetailCardViewAdapter.setSpanBetweenTokens(widgetText, mItalics);
        widgetText = DetailCardViewAdapter.setSpanBetweenTokens(widgetText, mUnderline);
//                context.getString(R.string.appwidget_text);

        Log.v(LOG_TAG, widgetText.toString());

        for (int i = 0; i < 1; i++) {
            WidgetListItem listItem = new WidgetListItem();
            listItem.content = widgetText + "\n\n- " + author + ", " + reference;
            widgetList.add(listItem);
        }

    }

    @Override
    public void onCreate() {
//        Log.v(LOG_TAG, "onCreate called.");
//        String[] projection = new String[] {
//                ExternalDbContract.QuoteEntry._ID,
//                "\"" + ExternalDbContract.QuoteEntry.AUTHOR_FIRST_NAME + "\"",
//                "\"" + ExternalDbContract.QuoteEntry.AUTHOR_LAST_NAME + "\"",
//                "\"" + ExternalDbContract.QuoteEntry.AUTHOR_GROUP_NAME + "\"",
//                ExternalDbContract.QuoteEntry.TOPIC,
//                ExternalDbContract.QuoteEntry.QUOTE,
//                ExternalDbContract.QuoteEntry.REFERENCE};
//        String sortOrder = "RANDOM() LIMIT 1";
//        mCursorLoader = new CursorLoader(
//                context,
//                ExternalDbContract.QuoteEntry.CONTENT_URI,
//                projection,
//                null,
//                null,
//                sortOrder);
//        mCursorLoader.registerListener(LOADER, this);
//        mCursorLoader.startLoading();

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return widgetList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Log.v(LOG_TAG, "RemoteViews called.");
//
        final RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.quote_widget_list_item);
        WidgetListItem listItem = (WidgetListItem) widgetList.get(position);
        remoteView.setTextViewText(R.id.appwidget_text, listItem.content);
//
        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public void onLoadComplete(Loader<Cursor> loader, Cursor data) {
        Log.v(LOG_TAG, "Cursor loader complete.");
        populateListItem(data);
    }

//    @Override
//    public void onLoadComplete(Loader<Cursor> loader, Cursor cursor) {
//        Log.v(LOG_TAG, "onLoadComplete called.");


//        String mBold = context.getResources().getString(R.string.format_text_bold);
//        String mItalics = context.getResources().getString(R.string.format_text_italics);
//        String mUnderline = context.getResources().getString(R.string.format_text_underline);
//        String mQuotationMark = context.getResources().getString(R.string.format_text_quotation_mark);
//
//        String first = "";
//        String last = "";
//        String group = "";
//        String topic = "";
//        String quote = "";
//        String reference = "";
//
//        if(cursor != null) {
//
//
//            if (cursor.moveToFirst()) {
//                do {
//                    long id = cursor.getLong(0);
//                    first = cursor.getString(1);
//                    last = cursor.getString(2);
//                    group = cursor.getString(3);
//                    topic = cursor.getString(4);
//                    quote = cursor.getString(5);
//                    reference = cursor.getString(6);
//              } while (cursor.moveToNext());
//            }
//
//
//            cursor.close();
//        }
//
//        String author = "";
//        if (group == null) {
//            author = first + " " + last;
//        } else {
//            author = group;
//        }
//
//        quote = quote.replace(mQuotationMark, "\"");
//        CharSequence widgetText = quote;
//        widgetText = DetailCardViewAdapter.setSpanBetweenTokens(widgetText, mBold);
//        widgetText = DetailCardViewAdapter.setSpanBetweenTokens(widgetText, mItalics);
//        widgetText = DetailCardViewAdapter.setSpanBetweenTokens(widgetText, mUnderline);
//
//        Log.v(LOG_TAG, widgetText.toString());
//
//        for (int i = 0; i < 1; i++) {
//            WidgetListItem listItem = new WidgetListItem();
//            listItem.content = widgetText + "\n\n- " + author + ", " + reference;
//            widgetList.add(listItem);
//        }
//
//    }
}
