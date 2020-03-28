package com.example.jeusetetmatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class Stats extends AppCompatActivity {

    private SQLiteDatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        db = new SQLiteDatabaseHandler(this);

       /* Match m = new Match(1,2, 4);
        db.addMatch(m);*/

        //m.toString();

        List<Match> matchs = db.allMatch();

        if (matchs != null) {
            String[] itemsNames = new String[matchs.size()];

            for (int i = 0; i < matchs.size(); i++) {
                itemsNames[i] = matchs.get(i).toString();
            }

            // display like string instances
            ListView list = (ListView) findViewById(R.id.list_view);
            list.setAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, itemsNames));

        }

    }
}

