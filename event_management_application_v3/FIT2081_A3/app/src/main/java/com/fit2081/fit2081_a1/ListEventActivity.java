package com.fit2081.fit2081_a1;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

public class ListEventActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    FragmentListEvent fragmentListEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.list_events_bar_layout);

        Toolbar eventToolbar = findViewById(R.id.toolbarListEvents);
        setSupportActionBar(eventToolbar);
        getSupportActionBar().setTitle("Events Page");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        eventToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fragmentManager = getSupportFragmentManager();
        fragmentListEvent = new FragmentListEvent();
    }
}