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

public class ListCategoryActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    FragmentListCategory fragmentListCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_list_category);
        setContentView(R.layout.list_categories_bar_layout);

        Toolbar categoryToolbar = findViewById(R.id.toolbarListCategories);
        setSupportActionBar(categoryToolbar);
        getSupportActionBar().setTitle("All Categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        categoryToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        fragmentManager = getSupportFragmentManager();
        fragmentListCategory = new FragmentListCategory();
    }
}