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

public class Fragment2 extends Fragment {

    private RecyclerView recyclerView;

    public static AdapterCommService mAdapter;

    private SQLiteDatabase commServiceDB = null;

    static String[][] userData;

    @Override
    public void onResume() {
        
        Cursor cursor = commServiceDB.rawQuery("SELECT * FROM commService", null);

        int activityColumn = cursor.getColumnIndex("activity");
        int dateColumn = cursor.getColumnIndex("date");
        int timeColumn = cursor.getColumnIndex("time");

        cursor.moveToFirst();

        userData = new String[cursor.getCount()][4];

        if (cursor != null && (cursor.getCount() > 0)) {

            int i = 0;

            do {

                String activity = cursor.getString(activityColumn);
                String date = cursor.getString(dateColumn);
                String time = cursor.getString(timeColumn);

                userData[i][0] = activity;
                userData[i][1] = date;
                userData[i][2] = time;

                i++;

            } while (cursor.moveToNext());

        }

        mAdapter = new AdapterCommService(getActivity(), getData());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_2, container, false);

        recyclerView = (RecyclerView) layout.findViewById(R.id.commServiceRecyclerView);

        commServiceDB = getActivity().openOrCreateDatabase("CommService", Context.MODE_PRIVATE, null);

        commServiceDB.execSQL("CREATE TABLE IF NOT EXISTS commService" +
                "(id integer primary key, " +
                "activity VARCHAR, " +
                "date VARCHAR, " +
                "tournament VARCHAR, " +
                "time VARCHAR, " +
                "sensei VARCHAR);");

        Cursor cursor = commServiceDB.rawQuery("SELECT * FROM commService", null);

        int activityColumn = cursor.getColumnIndex("activity");
        int dateColumn = cursor.getColumnIndex("date");
        int timeColumn = cursor.getColumnIndex("time");

        cursor.moveToFirst();

        userData = new String[cursor.getCount()][4];

        if (cursor != null && (cursor.getCount() > 0)) {

            int i = 0;

            do {

                String activity = cursor.getString(activityColumn);
                String date = cursor.getString(dateColumn);
                String time = cursor.getString(timeColumn);

                userData[i][0] = activity;
                userData[i][1] = date;
                userData[i][2] = time;

                i++;

            } while (cursor.moveToNext());

        }

        mAdapter = new AdapterCommService(getActivity(), getData());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void OnClick(View view, int position) {
                Intent toEditCommService = new Intent(getActivity(), EditCommService.class);
                toEditCommService.putExtra("position", position);
                startActivity(toEditCommService);
            }

            @Override
            public void onLongClick(View view, int position) {

                DialogFragment deleteFragment = new DeleteCommDialog();
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                deleteFragment.setArguments(bundle);
                deleteFragment.show(getActivity().getFragmentManager(), "theDialog2");

            }

        }));

        return layout;
    }


    public static List<CommServiceInformation> getData() {
        List<CommServiceInformation> data = new ArrayList<>();

        for (int i = 0; i < userData.length; i++) {

            CommServiceInformation current = new CommServiceInformation();
            current.activity = userData[i][0];
            current.date = userData[i][1];
            current.time = userData[i][2];
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
