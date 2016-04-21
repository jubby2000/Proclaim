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
import java.util.LinkedHashSet;
import java.util.List;

public class MainActivityFragment extends Fragment {
    private final String LOG_TAG = MainActivityFragment.class.getSimpleName();


    AToZAdapter mAdapter;

    private SQLiteDatabase database;
    private OnFragmentInteractionListener mListener;
    private ArrayList<Topic> topics;
    Bundle extras;

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
        extras = getArguments();
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

//        recyclerView.addItemDecoration(dividerItemDecoration);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());

        final List<AToZList> aToZ;

        aToZ = addTopics();

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


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

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

        if (extras != null) {
            topicCursor = database.rawQuery("SELECT * FROM " + ExternalDbContract.QuoteEntry.TABLE_NAME, null);
        }

        String firstLetter = "";

        try {
            for (topicCursor.moveToFirst(); !topicCursor.isAfterLast(); topicCursor.moveToNext()) {
                //In this scenario, Authors was no clicked, thus the assignment will be to topics
                //The topicCursor will be iterating through the topics column
                if (extras == null) {
                    firstLetter = topicCursor.getString(0).toUpperCase().substring(0, 1);

                //Else, getArguments isn't empty, which means we now need to know how to deal with Authors
                //Check if group name is empty
                } else if (topicCursor.getString(3) == null) {
                        firstLetter = topicCursor.getString(2).toUpperCase().substring(0, 1);

                //Check if group name starts with The, and exclude it from firstLetter selection
                    } else if (topicCursor.getString(3).substring(0, 2).equals("The")) {
                        firstLetter = topicCursor.getString(3).substring(4, 5);
                }

                switch (firstLetter) {
                    case "A":
                        if(A == null) {
                            if (extras == null) {
                                A = new AToZList("A", getTopics(firstLetter));
                            } else {
                                A = new AToZList("A", addAuthors(firstLetter));
                            }

                            //subText.setText(A.getChildItemList());
                            //aToZ.add(A);
                        }
                        break;
                    case "B":
                        if(B == null) {
                            if (extras == null) {
                                B = new AToZList("B", getTopics(firstLetter));
                            } else {
                                B = new AToZList("B", addAuthors(firstLetter));
                            }
                            //aToZ.add(B);
                        }
                        break;
                    case "C":
                        if(C == null) {
                            if (extras == null) {
                                C = new AToZList("C", getTopics(firstLetter));
                            } else {
                                C = new AToZList("C", addAuthors(firstLetter));
                            }
                        }
                        break;
                    case "D":
                        if(D == null) {
                            if (extras == null) {
                                D = new AToZList("D", getTopics(firstLetter));
                            } else {
                                D = new AToZList("D", addAuthors(firstLetter));
                            }
                        }
                        break;
                    case "E":
                        if(E == null) {
                            if (extras == null) {
                                E = new AToZList("E", getTopics(firstLetter));
                            } else {
                                E = new AToZList("E", addAuthors(firstLetter));
                            }
                        }
                        break;
                    case "F":
                        if(F == null) {
                            if (extras == null) {
                                F = new AToZList("F", getTopics(firstLetter));
                            } else {
                                F = new AToZList("F", addAuthors(firstLetter));
                            }
                        }
                        break;
                    case "G":
                        if(G == null) {
                            if (extras == null) {
                                G = new AToZList("G", getTopics(firstLetter));
                            } else {
                                G = new AToZList("G", addAuthors(firstLetter));
                            }
                        }
                        break;
                    case "H":
                        if(H == null) {
                            if (extras == null) {
                                H = new AToZList("H", getTopics(firstLetter));
                            } else {
                                H = new AToZList("H", addAuthors(firstLetter));
                            }
                        }
                        break;
                    case "I":
                        if(I == null) {
                            if (extras == null) {
                                I = new AToZList("I", getTopics(firstLetter));
                            } else {
                                I = new AToZList("I", addAuthors(firstLetter));
                            }
                        }
                        break;
                    case "J":
                        if(J == null) {
                            if (extras == null) {
                                J = new AToZList("J", getTopics(firstLetter));
                            } else {
                                J = new AToZList("J", addAuthors(firstLetter));
                            }
                        }
                        break;
                    case "K":
                        if(K == null) {
                            if (extras == null) {
                                K = new AToZList("K", getTopics(firstLetter));
                            } else {
                                K = new AToZList("K", addAuthors(firstLetter));
                            }
                        }
                        break;
                    case "L":
                        if(L == null) {
                            if (extras == null) {
                                L = new AToZList("L", getTopics(firstLetter));
                            } else {
                                L = new AToZList("L", addAuthors(firstLetter));
                            }
                        }
                        break;
                    case "M":
                        if(M == null) {
                            if (extras == null) {
                                M = new AToZList("M", getTopics(firstLetter));
                            } else {
                                M = new AToZList("M", addAuthors(firstLetter));
                            }
                        }
                        break;
                    case "N":
                        if(N == null) {
                            if (extras == null) {
                                N = new AToZList("N", getTopics(firstLetter));
                            } else {
                                N = new AToZList("N", addAuthors(firstLetter));
                            }
                        }
                        break;
                    case "O":
                        if(O == null) {
                            if (extras == null) {
                                O = new AToZList("O", getTopics(firstLetter));
                            } else {
                                O = new AToZList("O", addAuthors(firstLetter));
                            }
                        }
                        break;
                    case "P":
                        if(P == null) {
                            if (extras == null) {
                                P = new AToZList("P", getTopics(firstLetter));
                            } else {
                                P = new AToZList("P", addAuthors(firstLetter));
                            }
                        }
                        break;
                    case "Q":
                        if(Q == null) {
                            if (extras == null) {
                                Q = new AToZList("Q", getTopics(firstLetter));
                            } else {
                                Q = new AToZList("Q", addAuthors(firstLetter));
                            }
                        }
                        break;
                    case "R":
                        if(R == null) {
                            if (extras == null) {
                                R = new AToZList("R", getTopics(firstLetter));
                            } else {
                                R = new AToZList("R", addAuthors(firstLetter));
                            }
                        }
                        break;
                    case "S":
                        if(S == null) {
                            if (extras == null) {
                                S = new AToZList("S", getTopics(firstLetter));
                            } else {
                                S = new AToZList("S", addAuthors(firstLetter));
                            }
                        }
                        break;
                    case "T":
                        if(T == null) {
                            if (extras == null) {
                                T = new AToZList("T", getTopics(firstLetter));
                            } else {
                                T = new AToZList("T", addAuthors(firstLetter));
                            }
                        }
                        break;
                    case "U":
                        if(U == null) {
                            if (extras == null) {
                                U = new AToZList("U", getTopics(firstLetter));
                            } else {
                                U = new AToZList("U", addAuthors(firstLetter));
                            }
                        }
                        break;
                    case "V":
                        if(V == null) {
                            if (extras == null) {
                                V = new AToZList("V", getTopics(firstLetter));
                            } else {
                                V = new AToZList("V", addAuthors(firstLetter));
                            }
                        }
                        break;
                    case "W":
                        if(W == null) {
                            if (extras == null) {
                                W = new AToZList("W", getTopics(firstLetter));
                            } else {
                                W = new AToZList("W", addAuthors(firstLetter));
                            }
                        }
                        break;
                    case "X":
                        if(X == null) {
                            if (extras == null) {
                                X = new AToZList("X", getTopics(firstLetter));
                            } else {
                                X = new AToZList("X", addAuthors(firstLetter));
                            }
                        }
                        break;
                    case "Y":
                        if(Y == null) {
                            if (extras == null) {
                                Y = new AToZList("Y", getTopics(firstLetter));
                            } else {
                                Y = new AToZList("Y", addAuthors(firstLetter));
                            }
                        }
                        break;
                    case "Z":
                        if(Z == null) {
                            if (extras == null) {
                                Z = new AToZList("Z", getTopics(firstLetter));
                            } else {
                                Z = new AToZList("Z", addAuthors(firstLetter));
                            }
                        }
                        break;

                }
            }
        }finally {
            Log.v(LOG_TAG, "I got to the finally block!");
            aToZ = Arrays.asList(A, B);//, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z);

            //TODO convert this to a mutable list to remove nulls

            //aToZ.removeAll(Collections.singleton(null));

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
        LinkedHashSet<Topic> tempTopicsByLetter = new LinkedHashSet<Topic>();

        for(topicCursor.moveToFirst(); !topicCursor.isAfterLast(); topicCursor.moveToNext()) {
            if (topicCursor.getString(0).toUpperCase().substring(0, 1).equals(firstLetter)) {
                topicsByLetter.add(new Topic(topicCursor.getString(0)));
//                        Log.v(LOG_TAG, "Duplicate check: " + topicsByLetter.get(0));

            }
        }

        //Necessary to use a linkedhashset (see above) to not allow duplicate topics and also keep them alphabetical
        tempTopicsByLetter.addAll(topicsByLetter);
        topicsByLetter.clear();
        topicsByLetter.addAll(tempTopicsByLetter);

        //To add a subtext preview below the letter to see topics without actually having to tap
//        StringBuilder builder = new StringBuilder();
//        for (Topic topic : topicsByLetter) {
//            if(builder.length() != 0) {
//                builder.append(", ");
//            }
//            builder.append(topic.getName());
//        }


//        mSubText.setText(builder.toString());

        topicCursor.close();
        return topicsByLetter;
    }

    private ArrayList<Topic> addAuthors(String firstLetter) {
        Cursor authorCursor = database.rawQuery("SELECT * FROM " +
                ExternalDbContract.QuoteEntry.TABLE_NAME, null);

        ArrayList<Topic> authors = new ArrayList<Topic>();
        LinkedHashSet<Topic> tempAuthors = new LinkedHashSet<Topic>();
        String lastNameFirst;

        String firstName = authorCursor.getString(1);
        String lastName = authorCursor.getString(2);
        String groupName = authorCursor.getString(3);

        for (authorCursor.moveToFirst(); !authorCursor.isAfterLast(); authorCursor.moveToNext()) {
            if (groupName == null) {
                if (lastName.toUpperCase().substring(0, 1).equals(firstLetter)) {
                    authors.add(new Topic(lastName + ", " + firstName));
                }
            } else {
                if (groupName.substring(0, 3).equals("The ")) {
                    authors.add(new Topic(groupName.substring(4) + ", " + groupName.substring(0, 2)));
                } else {
                    authors.add(new Topic(groupName));
                }
            }

            //Necessary to use a linkedhashset (see above) to not allow duplicate topics and also keep them alphabetical
        }

        tempAuthors.addAll(authors);
        authors.clear();
        authors.addAll(tempAuthors);

        authorCursor.close();
        return authors;
    }
}
