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

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private final String LOG_TAG = MainActivityFragment.class.getSimpleName();


    private static final int LOADER = 0;

    AToZAdapter mAdapter;

    private OnFragmentInteractionListener mListener;
    private ArrayList<Topic> topics;
    Bundle extras;
    RecyclerView mRecyclerView;

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_main, container, false);
        extras = getArguments();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.topic_list);

//        SimpleDividerItemDecoration dividerItemDecoration =
//                new SimpleDividerItemDecoration(getContext());

        final List<AToZList> mAToZ;

        Bundle args = new Bundle();
        args.putString("uri", ExternalDbContract.QuoteEntry.CONTENT_URI.toString());
        getLoaderManager().initLoader(LOADER, args, this);

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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Uri uri = Uri.parse(args.getString("uri"));

        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(getContext());
        dbOpenHelper.createDataBase();

        switch (id) {
            case LOADER:
                if(extras == null) {
                // Returns a new CursorLoader
                    return new CursorLoader(
                            getActivity(),   // Parent activity context
                            uri,        // Uri to query
                            new String[]{ExternalDbContract.QuoteEntry.TOPIC},     // Projection to return (topics column)
                            null,            // No selection clause
                            null,            // No selection arguments
                            null             // Default sort order
                    );
                } else {
                    return new CursorLoader(
                            getActivity(),   // Parent activity context
                            uri,        // Uri to query
                            null,     // Projection to return (all columns)
                            null,            // No selection clause
                            null,            // No selection arguments
                            null             // Default sort order
                    );
                }

            default:
                // An invalid id was passed in
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        switch (loader.getId()) {
            case LOADER:
                topics = new ArrayList<Topic>();
                List<AToZList> aToZ;
                final ArrayList<AToZList> clone;
//                Cursor cursor;
                AToZList A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z;
                A=B=C=D=E=F=G=H=I=J=K=L=M=N=O=P=Q=R=S=T=U=V=W=X=Y=Z = null;

                String firstLetter = "";


                try {
                    for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                        int currentPosition = cursor.getPosition();
                        //In this scenario, Authors was no clicked, thus the assignment will be to topics
                        //The topicCursor will be iterating through the topics column
                        if (extras == null) {
                            firstLetter = cursor.getString(0).toUpperCase().substring(0, 1);

                            //Else, getArguments isn't empty, which means we now need to know how to deal with Authors
                            //Check if group name is empty
                        } else if (cursor.getString(3) == null) {
                            firstLetter = cursor.getString(2).toUpperCase().substring(0, 1);
                            Log.v(LOG_TAG, cursor.getString(2) + " " + firstLetter);

                            //Check if group name starts with The, and exclude it from firstLetter selection
                        } else if (cursor.getString(3).substring(0, 3).equals("The")) {
                            firstLetter = cursor.getString(3).substring(4, 5);
                            Log.v(LOG_TAG, cursor.getString(3) + " " + firstLetter);
                        }

                        switch (firstLetter) {
                            case "A":
                                if(A == null) {
                                    if (extras == null) {
                                        A = new AToZList("A", getTopics(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    } else {
                                        A = new AToZList("A", addAuthors(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    }

                                    //subText.setText(A.getChildItemList());
                                    //aToZ.add(A);
                                }
                                break;
                            case "B":
                                if(B == null) {
                                    if (extras == null) {
                                        B = new AToZList("B", getTopics(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    } else {
                                        B = new AToZList("B", addAuthors(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    }
                                    //aToZ.add(B);
                                }
                                break;
                            case "C":
                                if(C == null) {
                                    if (extras == null) {
                                        C = new AToZList("C", getTopics(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    } else {
                                        C = new AToZList("C", addAuthors(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    }
                                }
                                break;
                            case "D":
                                if(D == null) {
                                    if (extras == null) {
                                        D = new AToZList("D", getTopics(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    } else {
                                        D = new AToZList("D", addAuthors(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    }
                                }
                                break;
                            case "E":
                                if(E == null) {
                                    if (extras == null) {
                                        E = new AToZList("E", getTopics(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    } else {
                                        E = new AToZList("E", addAuthors(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    }
                                }
                                break;
                            case "F":
                                if(F == null) {
                                    if (extras == null) {
                                        F = new AToZList("F", getTopics(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    } else {
                                        F = new AToZList("F", addAuthors(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    }
                                }
                                break;
                            case "G":
                                if(G == null) {
                                    if (extras == null) {
                                        G = new AToZList("G", getTopics(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    } else {
                                        G = new AToZList("G", addAuthors(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    }
                                }
                                break;
                            case "H":
                                if(H == null) {
                                    if (extras == null) {
                                        H = new AToZList("H", getTopics(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    } else {
                                        H = new AToZList("H", addAuthors(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    }
                                }
                                break;
                            case "I":
                                if(I == null) {
                                    if (extras == null) {
                                        I = new AToZList("I", getTopics(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    } else {
                                        I = new AToZList("I", addAuthors(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    }
                                }
                                break;
                            case "J":
                                if(J == null) {
                                    if (extras == null) {
                                        J = new AToZList("J", getTopics(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    } else {
                                        J = new AToZList("J", addAuthors(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    }
                                }
                                break;
                            case "K":
                                if(K == null) {
                                    if (extras == null) {
                                        K = new AToZList("K", getTopics(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    } else {
                                        K = new AToZList("K", addAuthors(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    }
                                }
                                break;
                            case "L":
                                if(L == null) {
                                    if (extras == null) {
                                        L = new AToZList("L", getTopics(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    } else {
                                        L = new AToZList("L", addAuthors(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    }
                                }
                                break;
                            case "M":
                                if(M == null) {
                                    if (extras == null) {
                                        M = new AToZList("M", getTopics(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    } else {
                                        M = new AToZList("M", addAuthors(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    }
                                }
                                break;
                            case "N":
                                if(N == null) {
                                    if (extras == null) {
                                        N = new AToZList("N", getTopics(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    } else {
                                        N = new AToZList("N", addAuthors(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    }
                                }
                                break;
                            case "O":
                                if(O == null) {
                                    if (extras == null) {
                                        O = new AToZList("O", getTopics(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    } else {
                                        O = new AToZList("O", addAuthors(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    }
                                }
                                break;
                            case "P":
                                if(P == null) {
                                    if (extras == null) {
                                        P = new AToZList("P", getTopics(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    } else {
                                        P = new AToZList("P", addAuthors(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    }
                                }
                                break;
                            case "Q":
                                if(Q == null) {
                                    if (extras == null) {
                                        Q = new AToZList("Q", getTopics(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    } else {
                                        Q = new AToZList("Q", addAuthors(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    }
                                }
                                break;
                            case "R":
                                if(R == null) {
                                    if (extras == null) {
                                        R = new AToZList("R", getTopics(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    } else {
                                        R = new AToZList("R", addAuthors(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    }
                                }
                                break;
                            case "S":
                                if(S == null) {
                                    if (extras == null) {
                                        S = new AToZList("S", getTopics(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    } else {
                                        S = new AToZList("S", addAuthors(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    }
                                }
                                break;
                            case "T":
                                if(T == null) {
                                    if (extras == null) {
                                        T = new AToZList("T", getTopics(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    } else {
                                        T = new AToZList("T", addAuthors(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    }
                                }
                                break;
                            case "U":
                                if(U == null) {
                                    if (extras == null) {
                                        U = new AToZList("U", getTopics(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    } else {
                                        U = new AToZList("U", addAuthors(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    }
                                }
                                break;
                            case "V":
                                if(V == null) {
                                    if (extras == null) {
                                        V = new AToZList("V", getTopics(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    } else {
                                        V = new AToZList("V", addAuthors(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    }
                                }
                                break;
                            case "W":
                                if(W == null) {
                                    if (extras == null) {
                                        W = new AToZList("W", getTopics(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    } else {
                                        W = new AToZList("W", addAuthors(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    }
                                }
                                break;
                            case "X":
                                if(X == null) {
                                    if (extras == null) {
                                        X = new AToZList("X", getTopics(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    } else {
                                        X = new AToZList("X", addAuthors(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    }
                                }
                                break;
                            case "Y":
                                if(Y == null) {
                                    if (extras == null) {
                                        Y = new AToZList("Y", getTopics(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    } else {
                                        Y = new AToZList("Y", addAuthors(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    }
                                }
                                break;
                            case "Z":
                                if(Z == null) {
                                    if (extras == null) {
                                        Z = new AToZList("Z", getTopics(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    } else {
                                        Z = new AToZList("Z", addAuthors(cursor, firstLetter));
                                        cursor.moveToPosition(currentPosition);
                                    }
                                }
                                break;

                        }
                    }
                }finally {
                    Log.v(LOG_TAG, "I got to the finally block!");
                    aToZ = Arrays.asList(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z);

                    //TODO convert this to a mutable list to remove nulls

//            aToZ.removeAll(Collections.singleton(null));

                    clone = new ArrayList<AToZList>();
                    clone.addAll(aToZ);
                    clone.removeAll(Collections.<AToZList>singleton(null));

                }

                cursor.close();

                mAdapter = new AToZAdapter(getContext(), clone);

                mAdapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
                    @Override
                    public void onListItemExpanded(int position) {
                        AToZList expandedList = clone.get(position);
                    }

                    @Override
                    public void onListItemCollapsed(int position) {
                        AToZList collapsedList = clone.get(position);
                    }
                });


                mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                mRecyclerView.setAdapter(mAdapter);
        }


    }



    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

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

//    private List<AToZList> addTopics() {
//        topics = new ArrayList<Topic>();
//        List<AToZList> aToZ;
//        ArrayList<AToZList> clone;
//        AToZList A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z;
//        A=B=C=D=E=F=G=H=I=J=K=L=M=N=O=P=Q=R=S=T=U=V=W=X=Y=Z = null;
//
//        Cursor parentTopicCursor;
//
//        if (extras != null) {
//            parentTopicCursor = getContext().getContentResolver().query(ExternalDbContract.QuoteEntry.CONTENT_URI, null, null, null, null);
//        } else {
//            String[] projection = new String[]{
//                    ExternalDbContract.QuoteEntry.TOPIC};
//            parentTopicCursor = getContext().getContentResolver()
//                    .query(ExternalDbContract.QuoteEntry.CONTENT_URI,
//                            projection,
//                            null,
//                            null,
//                            null);
//        }
//
//        String firstLetter = "";
//
//        try {
//            for (parentTopicCursor.moveToFirst(); !parentTopicCursor.isAfterLast(); parentTopicCursor.moveToNext()) {
//                //In this scenario, Authors was no clicked, thus the assignment will be to topics
//                //The topicCursor will be iterating through the topics column
//                if (extras == null) {
//                    firstLetter = parentTopicCursor.getString(0).toUpperCase().substring(0, 1);
//
//                //Else, getArguments isn't empty, which means we now need to know how to deal with Authors
//                //Check if group name is empty
//                } else if (parentTopicCursor.getString(3) == null) {
//                        firstLetter = parentTopicCursor.getString(2).toUpperCase().substring(0, 1);
//                    Log.v(LOG_TAG, parentTopicCursor.getString(2) + " " + firstLetter);
//
//                //Check if group name starts with The, and exclude it from firstLetter selection
//                    } else if (parentTopicCursor.getString(3).substring(0, 3).equals("The")) {
//                        firstLetter = parentTopicCursor.getString(3).substring(4, 5);
//                    Log.v(LOG_TAG, parentTopicCursor.getString(3) + " " + firstLetter);
//                }
//
//                switch (firstLetter) {
//                    case "A":
//                        if(A == null) {
//                            if (extras == null) {
//                                A = new AToZList("A", getTopics(firstLetter));
//                            } else {
//                                A = new AToZList("A", addAuthors(firstLetter));
//                            }
//                        }
//                        break;
//                    case "B":
//                        if(B == null) {
//                            if (extras == null) {
//                                B = new AToZList("B", getTopics(firstLetter));
//                            } else {
//                                B = new AToZList("B", addAuthors(firstLetter));
//                            }
//                        }
//                        break;
//                    case "C":
//                        if(C == null) {
//                            if (extras == null) {
//                                C = new AToZList("C", getTopics(firstLetter));
//                            } else {
//                                C = new AToZList("C", addAuthors(firstLetter));
//                            }
//                        }
//                        break;
//                    case "D":
//                        if(D == null) {
//                            if (extras == null) {
//                                D = new AToZList("D", getTopics(firstLetter));
//                            } else {
//                                D = new AToZList("D", addAuthors(firstLetter));
//                            }
//                        }
//                        break;
//                    case "E":
//                        if(E == null) {
//                            if (extras == null) {
//                                E = new AToZList("E", getTopics(firstLetter));
//                            } else {
//                                E = new AToZList("E", addAuthors(firstLetter));
//                            }
//                        }
//                        break;
//                    case "F":
//                        if(F == null) {
//                            if (extras == null) {
//                                F = new AToZList("F", getTopics(firstLetter));
//                            } else {
//                                F = new AToZList("F", addAuthors(firstLetter));
//                            }
//                        }
//                        break;
//                    case "G":
//                        if(G == null) {
//                            if (extras == null) {
//                                G = new AToZList("G", getTopics(firstLetter));
//                            } else {
//                                G = new AToZList("G", addAuthors(firstLetter));
//                            }
//                        }
//                        break;
//                    case "H":
//                        if(H == null) {
//                            if (extras == null) {
//                                H = new AToZList("H", getTopics(firstLetter));
//                            } else {
//                                H = new AToZList("H", addAuthors(firstLetter));
//                            }
//                        }
//                        break;
//                    case "I":
//                        if(I == null) {
//                            if (extras == null) {
//                                I = new AToZList("I", getTopics(firstLetter));
//                            } else {
//                                I = new AToZList("I", addAuthors(firstLetter));
//                            }
//                        }
//                        break;
//                    case "J":
//                        if(J == null) {
//                            if (extras == null) {
//                                J = new AToZList("J", getTopics(firstLetter));
//                            } else {
//                                J = new AToZList("J", addAuthors(firstLetter));
//                            }
//                        }
//                        break;
//                    case "K":
//                        if(K == null) {
//                            if (extras == null) {
//                                K = new AToZList("K", getTopics(firstLetter));
//                            } else {
//                                K = new AToZList("K", addAuthors(firstLetter));
//                            }
//                        }
//                        break;
//                    case "L":
//                        if(L == null) {
//                            if (extras == null) {
//                                L = new AToZList("L", getTopics(firstLetter));
//                            } else {
//                                L = new AToZList("L", addAuthors(firstLetter));
//                            }
//                        }
//                        break;
//                    case "M":
//                        if(M == null) {
//                            if (extras == null) {
//                                M = new AToZList("M", getTopics(firstLetter));
//                            } else {
//                                M = new AToZList("M", addAuthors(firstLetter));
//                            }
//                        }
//                        break;
//                    case "N":
//                        if(N == null) {
//                            if (extras == null) {
//                                N = new AToZList("N", getTopics(firstLetter));
//                            } else {
//                                N = new AToZList("N", addAuthors(firstLetter));
//                            }
//                        }
//                        break;
//                    case "O":
//                        if(O == null) {
//                            if (extras == null) {
//                                O = new AToZList("O", getTopics(firstLetter));
//                            } else {
//                                O = new AToZList("O", addAuthors(firstLetter));
//                            }
//                        }
//                        break;
//                    case "P":
//                        if(P == null) {
//                            if (extras == null) {
//                                P = new AToZList("P", getTopics(firstLetter));
//                            } else {
//                                P = new AToZList("P", addAuthors(firstLetter));
//                            }
//                        }
//                        break;
//                    case "Q":
//                        if(Q == null) {
//                            if (extras == null) {
//                                Q = new AToZList("Q", getTopics(firstLetter));
//                            } else {
//                                Q = new AToZList("Q", addAuthors(firstLetter));
//                            }
//                        }
//                        break;
//                    case "R":
//                        if(R == null) {
//                            if (extras == null) {
//                                R = new AToZList("R", getTopics(firstLetter));
//                            } else {
//                                R = new AToZList("R", addAuthors(firstLetter));
//                            }
//                        }
//                        break;
//                    case "S":
//                        if(S == null) {
//                            if (extras == null) {
//                                S = new AToZList("S", getTopics(firstLetter));
//                            } else {
//                                S = new AToZList("S", addAuthors(firstLetter));
//                            }
//                        }
//                        break;
//                    case "T":
//                        if(T == null) {
//                            if (extras == null) {
//                                T = new AToZList("T", getTopics(firstLetter));
//                            } else {
//                                T = new AToZList("T", addAuthors(firstLetter));
//                            }
//                        }
//                        break;
//                    case "U":
//                        if(U == null) {
//                            if (extras == null) {
//                                U = new AToZList("U", getTopics(firstLetter));
//                            } else {
//                                U = new AToZList("U", addAuthors(firstLetter));
//                            }
//                        }
//                        break;
//                    case "V":
//                        if(V == null) {
//                            if (extras == null) {
//                                V = new AToZList("V", getTopics(firstLetter));
//                            } else {
//                                V = new AToZList("V", addAuthors(firstLetter));
//                            }
//                        }
//                        break;
//                    case "W":
//                        if(W == null) {
//                            if (extras == null) {
//                                W = new AToZList("W", getTopics(firstLetter));
//                            } else {
//                                W = new AToZList("W", addAuthors(firstLetter));
//                            }
//                        }
//                        break;
//                    case "X":
//                        if(X == null) {
//                            if (extras == null) {
//                                X = new AToZList("X", getTopics(firstLetter));
//                            } else {
//                                X = new AToZList("X", addAuthors(firstLetter));
//                            }
//                        }
//                        break;
//                    case "Y":
//                        if(Y == null) {
//                            if (extras == null) {
//                                Y = new AToZList("Y", getTopics(firstLetter));
//                            } else {
//                                Y = new AToZList("Y", addAuthors(firstLetter));
//                            }
//                        }
//                        break;
//                    case "Z":
//                        if(Z == null) {
//                            if (extras == null) {
//                                Z = new AToZList("Z", getTopics(firstLetter));
//                            } else {
//                                Z = new AToZList("Z", addAuthors(firstLetter));
//                            }
//                        }
//                        break;
//
//                }
//            }
//        }finally {
//            Log.v(LOG_TAG, "I got to the finally block!");
//            aToZ = Arrays.asList(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z);
//
//            //TODO convert this to a mutable list to remove nulls
//
//            clone = new ArrayList<AToZList>();
//            clone.addAll(aToZ);
//            clone.removeAll(Collections.<AToZList>singleton(null));
//
//        }
//
//        parentTopicCursor.close();
//        return clone;
//        }




    private ArrayList<Topic> getTopics(Cursor childTopicCursor, String firstLetter) {



//        String[] projection = new String[]{
//                ExternalDbContract.QuoteEntry.TOPIC};
//        Cursor childTopicCursor = getContext().getContentResolver()
//                .query(ExternalDbContract.QuoteEntry.CONTENT_URI,
//                        projection,
//                        null,
//                        null,
//                        null);

//        Bundle args = new Bundle();
//        args.putString("uri", ExternalDbContract.QuoteEntry.CONTENT_URI.toString());
//        getLoaderManager().initLoader(CHILD_LOADER, args, null);

        ArrayList<Topic> topicsByLetter = new ArrayList<Topic>();
        LinkedHashSet<Topic> tempTopicsByLetter = new LinkedHashSet<Topic>();

        if (childTopicCursor != null) {
            for (childTopicCursor.moveToFirst(); !childTopicCursor.isAfterLast(); childTopicCursor.moveToNext()) {
                if (childTopicCursor.getString(0).toUpperCase().substring(0, 1).equals(firstLetter)) {
                    topicsByLetter.add(new Topic(childTopicCursor.getString(0)));
//                        Log.v(LOG_TAG, "Duplicate check: " + topicsByLetter.get(0));

                }
            }

            //Necessary to use a linkedhashset (see above) to not allow duplicate topics and also keep them alphabetical
            tempTopicsByLetter.addAll(topicsByLetter);
            topicsByLetter.clear();
            topicsByLetter.addAll(tempTopicsByLetter);

//            childTopicCursor.close();
        }
        return topicsByLetter;
    }

    private ArrayList<Topic> addAuthors(Cursor authorCursor, String firstLetter) {
//        Cursor authorCursor = getContext().getContentResolver()
//                .query(ExternalDbContract.QuoteEntry.CONTENT_URI,
//                        null,
//                        null,
//                        null,
//                        null);

        ArrayList<Topic> authors = new ArrayList<Topic>();
        LinkedHashSet<Topic> tempAuthors = new LinkedHashSet<Topic>();

        if(authorCursor != null) {
            for (authorCursor.moveToFirst(); !authorCursor.isAfterLast(); authorCursor.moveToNext()) {

                String firstName = authorCursor.getString(1);
                String lastName = authorCursor.getString(2);
                String groupName = authorCursor.getString(3);
                String lastNameFirst = lastName + ", " + firstName;

                //Check to make sure this isn't a group authored quote
                if (groupName == null) {
                    if (lastName.toUpperCase().substring(0, 1).equals(firstLetter)) {
                        authors.add(new Topic(lastNameFirst));
                    }
                } else {
                    //If it is, check that it starts with The and compensate
                    if (groupName.substring(0, 3).equals("The")) {
                        if (groupName.substring(4, 5).equals(firstLetter)) {
                            authors.add(new Topic(groupName.substring(4)
                                    + ", " + groupName.substring(0, 3)));
                        }

                    } else {
                        //Otherwise just add the group name
                        authors.add(new Topic(groupName));
                    }
                }
            }

            tempAuthors.addAll(authors);
            authors.clear();
            authors.addAll(tempAuthors);

//            authorCursor.close();
        }
        return authors;
    }

    public void scrollToTop() {
        mRecyclerView.smoothScrollToPosition(0);
    }
}
