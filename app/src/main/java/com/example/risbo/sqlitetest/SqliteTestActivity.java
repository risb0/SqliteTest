package com.example.risbo.sqlitetest;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import stanford.androidlib.SimpleActivity;
import stanford.androidlib.data.SimpleDatabase;
import stanford.androidlib.data.SimpleRow;

public class SqliteTestActivity extends SimpleActivity {
    private String query;
    private SQLiteDatabase db;
    private String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_test);

        db = openOrCreateDatabase("example", MODE_PRIVATE, null);

        SimpleDatabase.with(this)
                .executeSqlFile(db, R.raw.babynamess);

        handleEnterKeyPress(findEditText(R.id.querytext));


    }

    @Override
    public void onEnterKeyPress(View editText){
        doQuery();

    }

    public void buttonClick(View view) {
        doQuery();



    }
    private void doQuery(){
        EditText editText = (EditText) findViewById(R.id.querytext);
        TextView textView = (TextView) findViewById(R.id.result);
        Switch sexSwitch = (Switch) findViewById(R.id.sexswitch);

        String name = editText.getText().toString();


/*       if( sexSwitch.isChecked()){
           sex = sexSwitch.getTextOn().toString();
       } else sex = sexSwitch.getTextOff().toString();*/
        String sex = sexSwitch.isChecked() ? "F": "M";


        Log.v("sex",String.valueOf(sex));

        query = "SELECT year, rank FROM names_ranks " + "WHERE name = '" + name + "' AND sex = '" + sex + "'";


        Log.v("query", query);


        Cursor cr = db.rawQuery(
                query,null
        );
        textView.setText("year" + ";" + "rank\n" );

        if(!cr.moveToFirst()){
            toast("not found");
            return;

        } do {
            int year = cr.getInt(cr.getColumnIndex("year"));
            int rank = cr.getInt(cr.getColumnIndex("rank"));

            textView.append(String.valueOf(year) + ";" + String.valueOf(rank) + "\n");
        }while (cr.moveToNext());



    }

}
