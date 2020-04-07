package com.example.jeusetetmatch;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Stats extends AppCompatActivity {

    ArrayList<String> listMatch;
    ArrayAdapter adapter;
    SQLiteDatabaseHandler db;
    ListView matchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        matchList = findViewById(R.id.list);

        db = new SQLiteDatabaseHandler(this);
        listMatch = new ArrayList<>();

        viewData();

        matchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                String text = matchList.getItemAtPosition(i).toString();
                Toast.makeText(Stats.this, ""+text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void viewData() {
        Cursor cursor = db.viewData();

        if(cursor.getCount() == 0){
            Toast.makeText(this, "Aucune donnée", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                listMatch.add(cursor.getString(0) + "  -  " + cursor.getString(1) + "   VS   " + cursor.getString(3) + "\n\n      "
                        + cursor.getString(5) +"  " + cursor.getString(6) +"  " + cursor.getString(7) +"\n      "
                        + cursor.getString(8) +"  " + cursor.getString(9) +"  " + cursor.getString(10)
                        + "\n\nDurée : " + cursor.getString(13));
            }
            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listMatch);
            matchList.setAdapter(adapter);
        }
    }

    private String gagnant(boolean gagnant){
        String winner = "";
        if(gagnant){
            winner = "ok";
            return winner;
        }else
            return winner;
    }
}

