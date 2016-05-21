package com.example.jacob.proclaim;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by jacob on 4/11/16.
 */
public class FavoritesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    final String LOG_TAG = FavoritesFragment.class.getSimpleName();

    private OnFragmentInteractionListener mListener;
    private static final int LOADER = 0;
    FavoritesCardViewAdapter mAdapter;
    ArrayList<Quote> quotes;
    RecyclerView mRecyclerView;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(LOG_TAG, "favorites fragment created");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_detail, container, false);

        //Use a CursorLoader to query and set data on background thread
        Bundle args = new Bundle();
        args.putString("uri", ExternalDbContract.QuoteEntry.CONTENT_URI.toString());
        getLoaderManager().initLoader(LOADER, args, this);

        //Initialize RecyclerView to set data on later
        mRecyclerView = (RecyclerView) view.findViewById(R.id.quote_list);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Uri uri = Uri.parse(args.getString("uri"));

        switch (id) {
            case LOADER:
                // Returns a new CursorLoader
                return new CursorLoader(
                        getActivity(),   // Parent activity context
                        uri,        // Uri to query
                        null,     // Projection to return (all columns)
                        null,            // No selection clause
                        null,            // No selection arguments
                        null             // Default sort order
                );
            default:
                // An invalid id was passed in
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor quoteCursor) {

        quotes = new ArrayList<Quote>();

        if (quoteCursor != null) {
            quoteCursor.moveToFirst();
            if (!quoteCursor.isAfterLast()) {
                do {
                    long id = quoteCursor.getLong(0);
                    Log.v(LOG_TAG, String.valueOf(quoteCursor.getInt(0)) + " " + quoteCursor.getString(1));
                    String firstName = quoteCursor.getString(1);
                    String lastName = quoteCursor.getString(2);
                    String groupName = quoteCursor.getString(3);
                    String quote = quoteCursor.getString(4);
                    String topic = quoteCursor.getString(5);
                    String reference = quoteCursor.getString(6);
                    String date = quoteCursor.getString(7);
                    String pageNumber = quoteCursor.getString(8);
                    String popularity = quoteCursor.getString(9);
                    String strFavorite = quoteCursor.getString(10);
                    String userSubmitted = quoteCursor.getString(11);
                    String flagged = quoteCursor.getString(12);

                    boolean favorite = false;

                    if (strFavorite.equals("false")) {
                        favorite = false;
                    } else if (strFavorite.equals("true")) {
                        favorite = true;
                    }

                    if (getActivity().getIntent().getStringExtra("Topic") == null && favorite) {
                        quotes.add(new Quote(id, firstName, lastName, groupName, topic, quote, reference, date, favorite));
                    }


                } while (quoteCursor.moveToNext());
                quoteCursor.close();
            }
            mAdapter = new FavoritesCardViewAdapter(this.getContext(), quotes);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

//    @Override
//    public void onHiddenChanged(boolean hidden) {
////        if (!hidden) {
////            Log.v(LOG_TAG, "I'm at onHidden!");
////            addQuotes();
////
////        }
//        if (!hidden) {
//
//        }
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void scrollToTop() {
        mRecyclerView.smoothScrollToPosition(0);
    }

}
