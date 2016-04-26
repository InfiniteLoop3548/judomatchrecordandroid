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
public class DeleteMatchDialog extends DialogFragment {

    private SQLiteDatabase matchRecordDB = null;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder theDialog = new AlertDialog.Builder(getActivity());

        theDialog.setTitle("Confirm");
        theDialog.setMessage("Are you sure you want to delete this match?");

        theDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                matchRecordDB = getActivity().openOrCreateDatabase("MatchRecord", Context.MODE_PRIVATE, null);

                int position = getArguments().getInt("position");

                Cursor cursor = matchRecordDB.rawQuery("SELECT * FROM matchRecord", null);

                int matchTitleColumn = cursor.getColumnIndex("matchTitle");
                int opponentColumn = cursor.getColumnIndex("opponent");
                int beltColorColumn = cursor.getColumnIndex("beltColor");
                int dojoColumn = cursor.getColumnIndex("dojo");
                int dateColumn = cursor.getColumnIndex("date");
                int tournamentColumn = cursor.getColumnIndex("tournament");
                int resultColumn = cursor.getColumnIndex("result");
                int scoreColumn = cursor.getColumnIndex("score");
                int commentsColumn = cursor.getColumnIndex("comments");

                cursor.moveToPosition(position);

                for (int i = position; i < cursor.getCount() - 1; i++) {

                    cursor.moveToNext();

                    String matchTitleNext = cursor.getString(matchTitleColumn);
                    String opponentNext = cursor.getString(opponentColumn);
                    String beltColorNext = cursor.getString(beltColorColumn);
                    String dojoNext = cursor.getString(dojoColumn);
                    String dateNext = cursor.getString(dateColumn);
                    String tournamentNext = cursor.getString(tournamentColumn);
                    String resultNext = cursor.getString(resultColumn);
                    String scoreNext = cursor.getString(scoreColumn);
                    String commentsNext = cursor.getString(commentsColumn);

                    ContentValues args = new ContentValues();
                    args.put("matchTitle", matchTitleNext);
                    args.put("opponent", opponentNext);
                    args.put("beltColor", beltColorNext);
                    args.put("dojo", dojoNext);
                    args.put("date", dateNext);
                    args.put("tournament", tournamentNext);
                    args.put("result", resultNext);
                    args.put("score", scoreNext);
                    args.put("comments", commentsNext);

                    matchRecordDB.update("matchRecord", args, "id=" + (i + 1), null);

                }

                matchRecordDB.execSQL("DELETE FROM matchRecord WHERE id = (SELECT MAX(id) FROM matchRecord);");

                Fragment1.mAdapter.delete(position);

            }
        });

        theDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return theDialog.create();
    }
}
