package com.example.jacob.proclaim;

import android.content.ClipData;
import android.content.Context;
import android.graphics.PorterDuff;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by jacob on 3/17/16.
 */
public class CardViewHolder extends RecyclerView.ViewHolder{

    private final String LOG_TAG = CardViewHolder.class.getSimpleName();

    CardView mCardView;
    TextView mAuthorView;
    TextView mQuoteView;
    TextView mReferenceView;

//    String mAuthorFirstName;
//    String mAuthorLastName;
//    String mAuthorGroupName;
//    String mQuoteText;
//    String mDate;
//    String mSource;

    ImageButton mFavorite;
    ImageButton mShare;


    public CardViewHolder(final View cardView) {
        super(cardView);

        mCardView = (CardView) itemView.findViewById(R.id.card_view);
        mAuthorView = (TextView) itemView.findViewById(R.id.author_view);
        mQuoteView = (TextView) itemView.findViewById(R.id.quote_view);
        mReferenceView = (TextView) itemView.findViewById(R.id.reference_view);
        mFavorite = (ImageButton) itemView.findViewById(R.id.favorite_button);

        mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String copyFullQuote = mQuoteView.getText().toString() + "\n- " +
                        mAuthorView.getText().toString() + ", " + mReferenceView.getText().toString();
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager)
                        v.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Quote", copyFullQuote);
                clipboard.setPrimaryClip(clip);

                Snackbar snackbar = Snackbar.make(v, "Copied to clipboard.", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });

        mFavorite.getDrawable().mutate().setColorFilter(cardView.getContext()
                .getColor(R.color.lightGray), PorterDuff.Mode.SRC_ATOP);





//        isFavorite = quotes.get(position).favorite;
//        Log.v(LOG_TAG, "Favorite position: " + String.valueOf(cardView..get(position)));


    }

}
