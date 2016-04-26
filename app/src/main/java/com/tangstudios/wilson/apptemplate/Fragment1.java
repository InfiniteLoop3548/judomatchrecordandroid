package com.tangstudios.wilson.apptemplate;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class Fragment1 extends Fragment {

    private RecyclerView recyclerView;

    public static Adapter mAdapter;

    private SQLiteDatabase matchRecordDB = null;

    static String[][] userData;

    @Override
    public void onResume() {
        Cursor cursor = matchRecordDB.rawQuery("SELECT * FROM matchRecord", null);

        int matchTitleColumn = cursor.getColumnIndex("matchTitle");
        int opponentColumn = cursor.getColumnIndex("opponent");
        int beltColorColumn = cursor.getColumnIndex("beltColor");
        int tournamentColumn = cursor.getColumnIndex("tournament");
        int resultColumn = cursor.getColumnIndex("result");

        cursor.moveToFirst();

        userData = new String[cursor.getCount()][5];

        if (cursor != null && (cursor.getCount() > 0)) {

            int i = 0;

            do {

                String matchTitle = cursor.getString(matchTitleColumn);
                String opponent = cursor.getString(opponentColumn);
                String beltColor = cursor.getString(beltColorColumn);
                String tournament = cursor.getString(tournamentColumn);
                String result = cursor.getString(resultColumn);

                userData[i][0] = matchTitle;
                userData[i][1] = opponent;
                userData[i][2] = beltColor;
                userData[i][3] = tournament;
                userData[i][4] = result;


                i++;

            } while (cursor.moveToNext());

        }

        mAdapter = new Adapter(getActivity(), getData());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_1, container, false);

        recyclerView = (RecyclerView) layout.findViewById(R.id.matchRecordRecyclerView);

        matchRecordDB = getActivity().openOrCreateDatabase("MatchRecord", Context.MODE_PRIVATE, null);

        matchRecordDB.execSQL("CREATE TABLE IF NOT EXISTS matchRecord" +
                "(id integer primary key, " +
                "matchTitle VARCHAR, " +
                "opponent VARCHAR, " +
                "beltColor VARCHAR, " +
                "dojo VARCHAR, " +
                "date VARCHAR, " +
                "tournament VARCHAR, " +
                "result VARCHAR, " +
                "score VARCHAR, " +
                "comments VARCHAR);");

        Cursor cursor = matchRecordDB.rawQuery("SELECT * FROM matchRecord", null);

        int matchTitleColumn = cursor.getColumnIndex("matchTitle");
        int opponentColumn = cursor.getColumnIndex("opponent");
        int beltColorColumn = cursor.getColumnIndex("beltColor");
        int tournamentColumn = cursor.getColumnIndex("tournament");
        int resultColumn = cursor.getColumnIndex("result");

        cursor.moveToFirst();
        
        userData = new String[cursor.getCount()][5];

        if (cursor != null && (cursor.getCount() > 0)) {

            int i = 0;

            do {

                String matchTitle = cursor.getString(matchTitleColumn);
                String opponent = cursor.getString(opponentColumn);
                String beltColor = cursor.getString(beltColorColumn);
                String tournament = cursor.getString(tournamentColumn);
                String result = cursor.getString(resultColumn);

                userData[i][0] = matchTitle;
                userData[i][1] = opponent;
                userData[i][2] = beltColor;
                userData[i][3] = tournament;
                userData[i][4] = result;

                i++;

            } while (cursor.moveToNext());

        }

        mAdapter = new Adapter(getActivity(), getData());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void OnClick(View view, int position) {
                Intent toEditMatchRecord = new Intent(getActivity(), EditMatchRecord.class);
                toEditMatchRecord.putExtra("position", position);
                startActivity(toEditMatchRecord);
            }

            @Override
            public void onLongClick(View view, int position) {

                DialogFragment deleteFragment = new DeleteMatchDialog();
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                deleteFragment.setArguments(bundle);
                deleteFragment.show(getActivity().getFragmentManager(), "theDialog");

            }

        }));

        return layout;
    }


    public static List<MatchRecordInformation> getData() {

        List<MatchRecordInformation> data = new ArrayList<>();

        for (int i = 0; i < userData.length; i++) {

            MatchRecordInformation current = new MatchRecordInformation();
            current.matchTitle = userData[i][0];
            current.opponentName = userData[i][1];
            current.beltColor = userData[i][2];
            current.tournament = userData[i][3];
            current.result = userData[i][4];
            data.add(current);

        }

        return data;

    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {

            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }


                @Override
                public void onLongPress(MotionEvent e) {

                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());

                    if (child != null && clickListener != null) {

                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));

                    }

                }

            });

        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {

                clickListener.OnClick(child, rv.getChildPosition(child));

            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean b) {

        }

    }

    public static interface ClickListener {

        public void OnClick(View view, int position);
        public void onLongClick(View view, int position);

    }

}
