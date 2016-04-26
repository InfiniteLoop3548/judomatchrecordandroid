package com.tangstudios.wilson.apptemplate;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class EditMatchRecord extends AppCompatActivity {

    private Toolbar mToolbar;

    private SQLiteDatabase matchRecordDB = null;

    EditText editMatchTitle, editOpponent, editBeltColor, editDojo, editDateMatchRecord,
        editTournamentMatchRecord, editScore, editComments;

    RadioGroup resultGroup;
    RadioButton selectedButton;

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_match_record);

        Intent getPosition = getIntent();
        position = getPosition.getIntExtra("position", -1);

        editMatchTitle = (EditText)findViewById(R.id.editMatchTitle);
        editOpponent = (EditText)findViewById(R.id.editOpponent);
        editBeltColor = (EditText)findViewById(R.id.editBeltColor);
        editDojo = (EditText)findViewById(R.id.editDojo);
        editDateMatchRecord = (EditText)findViewById(R.id.editDateMatchRecord);
        editTournamentMatchRecord = (EditText)findViewById(R.id.editTournamentMatchRecord);
        editScore = (EditText)findViewById(R.id.editScore);
        editComments = (EditText)findViewById(R.id.editComments);

        resultGroup = (RadioGroup) findViewById(R.id.result);

        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        mToolbar.setTitle("Match Record");
        setSupportActionBar(mToolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        matchRecordDB = this.openOrCreateDatabase("MatchRecord", MODE_PRIVATE, null);

        matchRecordDB.execSQL("CREATE TABLE IF NOT EXISTS matchRecord" +
                "(id integer primary key, " +
                "matchTitle VARCHAR, " +
                "opponent VARCHAR, " +
                "beltColor VARCHAR, " +
                "dojo VARCHAR, " +
                "date VARCHAR, " +
                "tournament VARCHAR, " +
                "result VARCHAR" +
                "score VARCHAR, " +
                "comments VARCHAR);");

        if (position != -1) {

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

            editMatchTitle.setText(cursor.getString(matchTitleColumn));
            editOpponent.setText(cursor.getString(opponentColumn));
            editBeltColor.setText(cursor.getString(beltColorColumn));
            editDojo.setText(cursor.getString(dojoColumn));
            editDateMatchRecord.setText(cursor.getString(dateColumn));
            editTournamentMatchRecord.setText(cursor.getString(tournamentColumn));
            editScore.setText(cursor.getString(scoreColumn));
            editComments.setText(cursor.getString(commentsColumn));

            String selected = cursor.getString(resultColumn);
            if (selected.equals("W")) {

                selectedButton = (RadioButton) findViewById(R.id.win);

            } else if (selected.equals("T")) {

                selectedButton = (RadioButton) findViewById(R.id.tie);

            } else if (selected.equals("L")) {

                selectedButton = (RadioButton) findViewById(R.id.loss);

            }
            if (selectedButton != null) selectedButton.setChecked(true);

        }

    }

    @Override
    protected void onPause() {
        //add things to database
        String matchTitle = editMatchTitle.getText().toString();
        String opponent = editOpponent.getText().toString();
        String beltColor = editBeltColor.getText().toString();
        String dojo = editDojo.getText().toString();
        String dateMatchRecord = editDateMatchRecord.getText().toString();
        String tournamentMatchRecord = editTournamentMatchRecord.getText().toString();
        String score = editScore.getText().toString();
        String comments = editComments.getText().toString();

        int selectedId = resultGroup.getCheckedRadioButtonId();
        selectedButton = (RadioButton)findViewById(selectedId);
        String result = "";

        if (selectedButton != null && selectedButton.getText().toString().equals("Win")) {

            result = "W";

        } else if (selectedButton != null && selectedButton.getText().toString().equals("Tie")) {

            result = "T";

        } else if (selectedButton != null && selectedButton.getText().toString().equals("Loss")) {

            result = "L";

        }

        if (position == -1) {
            matchRecordDB.execSQL("INSERT INTO matchRecord (matchTitle, opponent, beltColor, dojo, date, tournament, result, score, comments) VALUES " +
                            "('" + matchTitle + "'," +
                            "'" + opponent + "'," +
                            "'" + beltColor + "'," +
                            "'" + dojo + "'," +
                            "'" + dateMatchRecord + "'," +
                            "'" + tournamentMatchRecord + "'," +
                            "'" + result + "'," +
                            "'" + score + "'," +
                            "'" + comments + "');"
            );
        } else {

            ContentValues args = new ContentValues();
            args.put("matchTitle", matchTitle);
            args.put("opponent", opponent);
            args.put("beltColor", beltColor);
            args.put("dojo", dojo);
            args.put("date", dateMatchRecord);
            args.put("tournament", tournamentMatchRecord);
            args.put("result", result);
            args.put("score", score);
            args.put("comments", comments);

            matchRecordDB.update("matchRecord", args, "id=" + (position + 1), null);


        }

        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_match_record, menu);
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
