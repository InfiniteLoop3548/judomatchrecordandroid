package com.tangstudios.wilson.apptemplate;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

/**
 * Created by Wilson on 3/8/2016.
 */
public class DeleteCommDialog extends DialogFragment {

    private SQLiteDatabase commServiceDB = null;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder theDialog2 = new AlertDialog.Builder(getActivity());

        theDialog2.setTitle("Confirm");
        theDialog2.setMessage("Are you sure you want to delete this community service?");

        theDialog2.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                commServiceDB = getActivity().openOrCreateDatabase("CommService", Context.MODE_PRIVATE, null);

                int position = getArguments().getInt("position");

                Cursor cursor = commServiceDB.rawQuery("SELECT * FROM commService", null);

                int activityColumn = cursor.getColumnIndex("activity");
                int dateColumn = cursor.getColumnIndex("date");
                int tournamentColumn = cursor.getColumnIndex("tournament");
                int timeColumn = cursor.getColumnIndex("time");
                int senseiColumn = cursor.getColumnIndex("sensei");

                cursor.moveToPosition(position);

                for (int i = position; i < cursor.getCount() - 1; i++) {

                    cursor.moveToNext();

                    String activityNext = cursor.getString(activityColumn);
                    String dateNext = cursor.getString(dateColumn);
                    String tournamentNext = cursor.getString(tournamentColumn);
                    String timeNext = cursor.getString(timeColumn);
                    String senseiNext = cursor.getString(senseiColumn);

                    ContentValues args = new ContentValues();
                    args.put("activity", activityNext);
                    args.put("date", dateNext);
                    args.put("tournament", tournamentNext);
                    args.put("time", timeNext);
                    args.put("sensei", senseiNext);

                    commServiceDB.update("commService", args, "id=" + (i + 1), null);

                }

                commServiceDB.execSQL("DELETE FROM commService WHERE id = (SELECT MAX(id) FROM commService);");

                Fragment2.mAdapter.delete(position);

            }
        });

        theDialog2.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return theDialog2.create();
    }

}
