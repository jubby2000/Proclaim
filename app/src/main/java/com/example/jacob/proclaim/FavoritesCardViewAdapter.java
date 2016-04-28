package com.example.jacob.proclaim;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by jacob on 4/11/16.
 */
public class FavoritesCardViewAdapter extends RecyclerView.Adapter<CardViewHolder> {

    private final String LOG_TAG = DetailCardViewAdapter.class.getSimpleName();

    List<Quote> quotes;
    Context context;
    String mBold;
    String mItalics;
    String mUnderline;
    String mQuotationMark;
    ExternalDbOpenHelper mDbHelper;
    SQLiteDatabase db;

    ContentValues values = new ContentValues();

    FavoritesCardViewAdapter(List<Quote> quotes) {
        this.quotes = quotes;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        CardViewHolder cardViewHolder = new CardViewHolder(view);
        context = parent.getContext();
        mBold = context.getResources().getString(R.string.format_text_bold);
        mItalics = context.getResources().getString(R.string.format_text_italics);
        mUnderline = context.getResources().getString(R.string.format_text_underline);
        mQuotationMark = context.getResources().getString(R.string.format_text_quotation_mark);
        mDbHelper = new ExternalDbOpenHelper(context);

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

        db = mDbHelper.getWritableDatabase();

        return cardViewHolder;
    }

    @Override
    public void onBindViewHolder(final CardViewHolder holder, final int position) {

        String authorConcat = quotes.get(position).firstName + " " + quotes.get(position).lastName;
        String sourceConcat;

        //Check if a date exists, and if it does append it to the reference, if not, don't.
        if (quotes.get(position).date != null){
            sourceConcat = quotes.get(position).reference + ", " + quotes.get(position).date;
        } else {
            sourceConcat = quotes.get(position).reference;
        }

        //Check if a group author name exists, it it does use it, otherwise, use the concatenated author first and last name.
        if (quotes.get(position).groupName != null) {
            holder.mAuthorView.setText(quotes.get(position).groupName);
        } else {
            holder.mAuthorView.setText(authorConcat);
        }

        // To remove special characters in the quoted text that were inserted to format the text at this stage.
        CharSequence text = quotes.get(position).quote.replace(mQuotationMark, "\"");

        text = setSpanBetweenTokens(text, mBold);
        text = setSpanBetweenTokens(text, mItalics);
        text = setSpanBetweenTokens(text, mUnderline);

        holder.mQuoteView.setText(text);
        holder.mReferenceView.setText(sourceConcat);

        //Set image as Favorite if already favorited in the database
        if (quotes.get(position).favorite) {
            holder.mFavorite.setImageResource(R.drawable.ic_favorite_black_24dp);
            holder.mFavorite.getDrawable()
                    .mutate()
                    .setColorFilter(ContextCompat.getColor(context,
                            R.color.favorite), PorterDuff.Mode.SRC_ATOP);
        } else {
            holder.mFavorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            holder.mFavorite.getDrawable()
                    .mutate()
                    .setColorFilter(ContextCompat.getColor(context,
                            R.color.gray), PorterDuff.Mode.SRC_ATOP);
        }

        //Switch image on click with a Snackbar to explain what's happening
        holder.mFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                    holder.mFavorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    holder.mFavorite.getDrawable()
                            .mutate()
                            .setColorFilter(ContextCompat.getColor(v.getContext(),
                                    R.color.gray), PorterDuff.Mode.SRC_ATOP);

                    values.put(ExternalDbContract.QuoteEntry.FAVORITE, "false");
                    db.update(ExternalDbContract.QuoteEntry.TABLE_NAME, values, "_id="
                            + quotes.get(holder.getAdapterPosition()).id, null);

                    Snackbar snackbar = Snackbar.make(v, "Removed from your Favorites.", Snackbar.LENGTH_SHORT);
                    snackbar.show();

                    quotes.get(holder.getAdapterPosition()).favorite = false;
                    removeItem(holder.getAdapterPosition());

//                    notifyDataSetChanged();
//                }
            }
        });
    }

    public void addItem(int position) {
        if (position > getItemCount()) return;

        quotes.add(position, new Quote(quotes.get(position).id,
                quotes.get(position).firstName,
                quotes.get(position).lastName,
                quotes.get(position).groupName,
                quotes.get(position).topic,
                quotes.get(position).quote,
                quotes.get(position).reference,
                quotes.get(position).date,
                quotes.get(position).favorite));
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        if (position >= getItemCount()) return;

        quotes.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {

        return quotes.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static CharSequence setSpanBetweenTokens(CharSequence text, String token)
    {
        int tokenLen = token.length();
        int start = text.toString().indexOf(token) + tokenLen;
        int end = text.toString().indexOf(token, start);


        //Ensure that multiple tokens within text will be formatted, not just one instance or the last.
        while (start > -1 && end > -1)
        {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);

            switch(token){
                case "###":
                    spannableStringBuilder.setSpan(new StyleSpan(Typeface.BOLD), start, end, 0);
                    break;
                case "***":
                    spannableStringBuilder.setSpan(new StyleSpan(Typeface.ITALIC), start, end, 0);
                    break;
                case "___":
                    spannableStringBuilder.setSpan(new UnderlineSpan(), start, end, 0);
            }

            // Delete the tokens before and after the span
            spannableStringBuilder.delete(end, end + tokenLen);
            spannableStringBuilder.delete(start - tokenLen, start);
            text = spannableStringBuilder;

            start = text.toString().indexOf(token, end - tokenLen - tokenLen) + tokenLen;
            end = text.toString().indexOf(token, start);
        }

        return text;
    }

}