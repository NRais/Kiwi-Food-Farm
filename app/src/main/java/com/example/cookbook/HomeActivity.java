package com.example.cookbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ImageButton recipeButton = findViewById(R.id.recipe_button);
        LinearLayout recipeLayout = findViewById(R.id.recipe_layout);

        recipeButton.setOnClickListener(this);
        recipeLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent i;

        switch (v.getId()) {
            case R.id.recipe_button:
            case R.id.recipe_layout:

                i = new Intent(this, RecipeActivity.class);

                startActivity(i);

                break;
        }
    }
}
