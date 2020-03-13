package com.example.jeusetetmatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button newMatch = (Button)findViewById(R.id.New);
        Button oldMatch = (Button)findViewById(R.id.Old);

        newMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intNew = new Intent(Menu.this, Dashboard.class);
                startActivity(intNew);
            }
        });

       oldMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intOld = new Intent(Menu.this, Stats.class);
                startActivity(intOld);
            }
        });
    }
}
