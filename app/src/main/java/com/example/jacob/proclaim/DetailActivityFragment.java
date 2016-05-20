package com.example.jacob.proclaim;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailActivityFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailActivityFragment#} factory method to
 * create an instance of this fragment.
 */
public class DetailActivityFragment extends Fragment {

    final String LOG_TAG = DetailActivityFragment.class.getSimpleName();

    private OnFragmentInteractionListener mListener;
//    private SQLiteDatabase database;
    DetailCardViewAdapter mAdapter;
    private ArrayList<Quote> quotes;
    RecyclerView mRecyclerView;

    public DetailActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(LOG_TAG, "detail fragment created");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.v(LOG_TAG, "made it to oncreate");

        final View view = inflater.inflate(R.layout.fragment_detail, container, false);

        addQuotes();

        mAdapter = new DetailCardViewAdapter(this.getContext(), quotes);
//        DetailCardViewAdapter adapter = new DetailCardViewAdapter(quotes);

//        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.quote_list);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.quote_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);

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

    private void addQuotes() {
        quotes = new ArrayList<Quote>();

        Cursor quoteCursor = getContext().getContentResolver().query(ExternalDbContract.QuoteEntry.CONTENT_URI, null, null, null, null);
//        QueryDataTask queryTask = new QueryDataTask(this.getContext());
//        queryTask.execute("SELECT * FROM " + ExternalDbContract.QuoteEntry.TABLE_NAME, null);
//        Cursor quoteCursor = database.rawQuery("SELECT * FROM " + ExternalDbContract.QuoteEntry.TABLE_NAME, null);
        String currentTopic = getActivity().getIntent().getStringExtra("Topic");

        if (quoteCursor != null) {
            quoteCursor.moveToFirst();
            if (!quoteCursor.isAfterLast()) {
                do {
                    //                long id = quoteCursor.getLong(quoteCursor.getColumnIndex(ExternalDbContract.QuoteEntry.QUOTE_ID));
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

                    if (currentTopic == null && favorite) {
                        quotes.add(new Quote(id, firstName, lastName, groupName, topic, quote, reference, date, favorite));
                    }

                    if (currentTopic != null) {
                        Log.v(LOG_TAG, "From intent " + currentTopic + firstName + " " + lastName);
                        if (currentTopic.equals(topic)) {
                            quotes.add(new Quote(id, firstName, lastName, groupName, topic, quote, reference, date, favorite));
                        } else if (currentTopic.equals(firstName + " " + lastName)) {
                            quotes.add(new Quote(id, firstName, lastName, groupName, topic, quote, reference, date, favorite));
                        } else if (currentTopic.equals(groupName)) {
                            quotes.add(new Quote(id, firstName, lastName, groupName, topic, quote, reference, date, favorite));
                        }
                    }


                } while (quoteCursor.moveToNext());
                quoteCursor.close();
            }
        }
//        quoteCursor.close();
//        database.close();
    }

}
