package com.example.jacob.proclaim;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivityFragment extends Fragment {
    private final String LOG_TAG = MainActivityFragment.class.getSimpleName();


    AToZAdapter mAdapter;

    private SQLiteDatabase database;
    private OnFragmentInteractionListener mListener;
    private ArrayList<Topic> topics;

    public MainActivityFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
//    public static MainActivityFragment newInstance() {
//        MainActivityFragment fragment = new MainActivityFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_main, container, false);

        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this.getContext());

        try {
            dbOpenHelper.createDataBase();
        } catch (Exception e) {
            throw new Error("Unable to create database");
        }

        try {
            dbOpenHelper.openDataBase();
        }catch(android.database.SQLException sqle) {
            throw sqle;
        }

        database = dbOpenHelper.getReadableDatabase();



//        addTopics();

//        Topic atonement = new Topic("Atonement");
//        Topic abrahamicCovenant = new Topic("Abrahamic Covenant");
//        Topic adam = new Topic("Adam");
//
//        Topic baptism = new Topic("Baptism");
//        Topic bethlehem = new Topic("Bethlehem");
//        Topic bethesda = new Topic("Bethesda");
//
//        AToZList A = new AToZList("A", Arrays.asList(adam, abrahamicCovenant, atonement));
//        AToZList B = new AToZList("B", Arrays.asList(baptism, bethesda, bethlehem));
//        final List<AToZList> aToZ = Arrays.asList(A, B);

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.topic_list);

//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(layoutManager);
//
//        Log.v(LOG_TAG, String.valueOf(recyclerView.findContainingItemView(getContext())));

        SimpleDividerItemDecoration dividerItemDecoration =
                new SimpleDividerItemDecoration(getContext());

//        LayoutTransition lt = new LayoutTransition();
//        lt.disableTransitionType(LayoutTransition.DISAPPEARING);
//        recyclerView.setLayoutTransition(lt);

        recyclerView.addItemDecoration(dividerItemDecoration);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());

        final List<AToZList> aToZ = addTopics();

        mAdapter = new AToZAdapter(getContext(), aToZ);

        mAdapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
            @Override
            public void onListItemExpanded(int position) {
                AToZList expandedList = aToZ.get(position);
                //view.findViewById(R.id.a_to_z_list_sub_text).setVisibility(View.GONE);
                Log.v(LOG_TAG, String.valueOf(position));
            }

            @Override
            public void onListItemCollapsed(int position) {
                AToZList collapsedList = aToZ.get(position);
                //view.findViewById(R.id.a_to_z_list_sub_text).setVisibility(View.VISIBLE);
            }
        });

        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

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
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            mListener = (OnFragmentInteractionListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

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

    private List<AToZList> addTopics() {
        topics = new ArrayList<Topic>();
        List<AToZList> aToZ;
        AToZList A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z;
        A=B=C=D=E=F=G=H=I=J=K=L=M=N=O=P=Q=R=S=T=U=V=W=X=Y=Z = null;

        Cursor topicCursor = database.rawQuery("SELECT " + ExternalDbContract.QuoteEntry.TOPIC +
                " FROM " + ExternalDbContract.QuoteEntry.TABLE_NAME, null);

        try {
            for (topicCursor.moveToFirst(); !topicCursor.isAfterLast(); topicCursor.moveToNext()) {
                String firstLetter = topicCursor.getString(0).toUpperCase().substring(0, 1);
                //Topic topicVar = new Topic(topicCursor.getString(5));
                Log.v(LOG_TAG, "After cursor: " + firstLetter + " - " + topicCursor.getString(0) + " - " + topicCursor.getCount());

                switch (firstLetter) {
                    case "A":
                        if(A == null) {
                            A = new AToZList("A", getTopics(firstLetter));
                            //aToZ.add(A);
                        }
                        break;
                    case "B":
                        if(B == null) {
                            B = new AToZList("B", getTopics(firstLetter));
                            //aToZ.add(B);
                        }
                        break;
                    case "C":
                        if(C == null) {
                            C = new AToZList("C", getTopics(firstLetter));
                            //aToZ.add(C);
                        }
                        break;
                    case "D":
                        if(D == null) {
                            D = new AToZList("D", getTopics(firstLetter));
                        }
                        break;
                    case "E":
                        if(E == null) {
                            E = new AToZList("E", getTopics(firstLetter));
                        }
                        break;
                    case "F":
                        if(F == null) {
                            F = new AToZList("F", getTopics(firstLetter));
                        }
                        break;
                    case "G":
                        if(G == null) {
                            G = new AToZList("G", getTopics(firstLetter));
                        }
                        break;
                    case "H":
                        if(H == null) {
                            H = new AToZList("H", getTopics(firstLetter));
                        }
                        break;
                    case "I":
                        if(I == null) {
                            I = new AToZList("I", getTopics(firstLetter));
                        }
                        break;
                    case "J":
                        if(J == null) {
                            J = new AToZList("J", getTopics(firstLetter));
                        }
                        break;
                    case "K":
                        if(K == null) {
                            K = new AToZList("K", getTopics(firstLetter));
                        }
                        break;
                    case "L":
                        if(L == null) {
                            L = new AToZList("L", getTopics(firstLetter));
                        }
                        break;
                    case "M":
                        if(M == null) {
                            M = new AToZList("M", getTopics(firstLetter));
                        }
                        break;
                    case "N":
                        if(N == null) {
                            N = new AToZList("N", getTopics(firstLetter));
                        }
                        break;
                    case "O":
                        if(O == null) {
                            O = new AToZList("O", getTopics(firstLetter));
                        }
                        break;
                    case "P":
                        if(P == null) {
                            P = new AToZList("P", getTopics(firstLetter));
                        }
                        break;
                    case "Q":
                        if(Q == null) {
                            Q = new AToZList("Q", getTopics(firstLetter));
                        }
                        break;
                    case "R":
                        if(R == null) {
                            R = new AToZList("R", getTopics(firstLetter));
                        }
                        break;
                    case "S":
                        if(S == null) {
                            S = new AToZList("S", getTopics(firstLetter));
                        }
                        break;
                    case "T":
                        if(T == null) {
                            T = new AToZList("T", getTopics(firstLetter));
                        }
                        break;
                    case "U":
                        if(U == null) {
                            U = new AToZList("U", getTopics(firstLetter));
                        }
                        break;
                    case "V":
                        if(V == null) {
                            V = new AToZList("V", getTopics(firstLetter));
                        }
                        break;
                    case "W":
                        if(W == null) {
                            W = new AToZList("W", getTopics(firstLetter));
                        }
                        break;
                    case "X":
                        if(X == null) {
                            X = new AToZList("X", getTopics(firstLetter));
                        }
                        break;
                    case "Y":
                        if(Y == null) {
                            Y = new AToZList("Y", getTopics(firstLetter));
                        }
                        break;
                    case "Z":
                        if(Z == null) {
                            Z = new AToZList("Z", getTopics(firstLetter));
                        }
                        break;

                }
            }
        }finally {
            Log.v(LOG_TAG, "I got to the finally block!");
            aToZ = Arrays.asList(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z);
        }

//        for(topicCursor.moveToFirst(); !topicCursor.isAfterLast(); topicCursor.moveToNext()) {
//            String firstLetter = topicCursor.getString(5).toUpperCase().substring(0, 1);
//            Topic topicVar = new Topic(topicCursor.getString(5));
//
//            switch (firstLetter) {
//                case "A":
//                    A = new AToZList("A", getTopics(firstLetter));
//
//            }
//
//            AToZList letter = new AToZList(firstLetter, getTopics(firstLetter));
//
//
//            topics.add(topicVar);
//        }
//
//        final List<AToZList> aToZ = Arrays.asList(A);
//                long id = topicCursor.getLong(0);
//                Log.v(LOG_TAG, String.valueOf(topicCursor.getInt(0)) + " " + topicCursor.getString(1));
//                String topic = topicCursor.getString(5);
//
//                String firstLetter = topic.toUpperCase().substring(0, 1);


//                    Topic bethesda = new Topic("Bethesda");
//
//                    AToZList A = new AToZList("A", Arrays.asList(adam, abrahamicCovenant, atonement));
//                    AToZList B = new AToZList("B", Arrays.asList(baptism, bethesda, bethlehem));
//                    final List<AToZList> aToZ = Arrays.asList(A, B);
//                    topics.add(new Topic(id, topic));

        //Log.v(LOG_TAG, "I got to the topicCursor.close!");
        topicCursor.close();
        database.close();
        return aToZ;
        }
//        quoteCursor.close();




    private ArrayList<Topic> getTopics(String firstLetter) {
        Cursor topicCursor = database.rawQuery("SELECT " + ExternalDbContract.QuoteEntry.TOPIC +
                " FROM " + ExternalDbContract.QuoteEntry.TABLE_NAME, null);

        ArrayList<Topic> topicsByLetter = new ArrayList<Topic>();

        for(topicCursor.moveToFirst(); !topicCursor.isAfterLast(); topicCursor.moveToNext()) {
            if (topicCursor.getString(0).toUpperCase().substring(0, 1).equals(firstLetter)) {
                topicsByLetter.add(new Topic(topicCursor.getString(0)));
                Log.v(LOG_TAG, "Get topics method: " + topicCursor.getString(0));
            }
        }


        topicCursor.close();
        return topicsByLetter;
    }
}
