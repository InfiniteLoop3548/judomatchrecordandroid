package com.tangstudios.wilson.apptemplate;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class EditCommService extends AppCompatActivity {

    private Toolbar mToolbar;

    private SQLiteDatabase commServiceDB = null;

    EditText editActivity, editDate, editTournament, editTime, editSensei;

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_comm_service);

        Intent getPosition = getIntent();
        position = getPosition.getIntExtra("position", -1);

        editActivity = (EditText)findViewById(R.id.editActivity);
        editDate = (EditText)findViewById(R.id.editDateCommService);
        editTournament = (EditText)findViewById(R.id.editTournamentCommService);
        editTime = (EditText)findViewById(R.id.editTime);
        editSensei = (EditText)findViewById(R.id.editSensei);

        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        mToolbar.setTitle("Community Service");
        setSupportActionBar(mToolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        commServiceDB = this.openOrCreateDatabase("CommService", Context.MODE_PRIVATE, null);

        commServiceDB.execSQL("CREATE TABLE IF NOT EXISTS commService" +
                "(id integer primary key, " +
                "activity VARCHAR, " +
                "date VARCHAR, " +
                "tournament VARCHAR, " +
                "time VARCHAR, " +
                "sensei VARCHAR);");

        if (position != -1) {

            Cursor cursor = commServiceDB.rawQuery("SELECT * FROM commService", null);

            int activityColumn = cursor.getColumnIndex("activity");
            int dateColumn = cursor.getColumnIndex("date");
            int tournamentColumn = cursor.getColumnIndex("tournament");
            int timeColumn = cursor.getColumnIndex("time");
            int senseiColumn = cursor.getColumnIndex("sensei");

            cursor.moveToPosition(position);

            editActivity.setText(cursor.getString(activityColumn));
            editDate.setText(cursor.getString(dateColumn));
            editTournament.setText(cursor.getString(tournamentColumn));
            editTime.setText(cursor.getString(timeColumn));
            editSensei.setText(cursor.getString(senseiColumn));

        }

    }

    @Override
    protected void onPause() {

        String activity = editActivity.getText().toString();
        String date = editDate.getText().toString();
        String tournament = editTournament.getText().toString();
        String time = editTime.getText().toString();
        String sensei = editSensei.getText().toString();

        if (position == -1) {

            commServiceDB.execSQL("INSERT INTO commService (activity, date, tournament, time, sensei) VALUES " +
                            "('" + activity + "'," +
                            "'" + date + "'," +
                            "'" + tournament + "'," +
                            "'" + time + "'," +
                            "'" + sensei + "');"
            );

        } else {

            ContentValues args = new ContentValues();
            args.put("activity", activity);
            args.put("date", date);
            args.put("tournament", tournament);
            args.put("time", time);
            args.put("sensei", sensei);

            commServiceDB.update("commService", args, "id=" + (position + 1), null);

        }

        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_comm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {

            finish();

        }

        return super.onOptionsItemSelected(item);
    }
}
