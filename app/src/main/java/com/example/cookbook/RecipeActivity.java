package com.example.cookbook;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.List;

public class RecipeActivity extends AppCompatActivity {


    List<Recipe> recipesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);

        //setup recipes
        recipesList = new ArrayList<>();

        EditText search = findViewById(R.id.editText);

        search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                String s = ((EditText)v).getText().toString();

                Log.d("HELLO", "Key Pressed :: " + s);

                updateRecipes(s);

                return false;
            }
        });
    }

    /**
     * Function to update the recipes displayed
     *
     * @param search
     */
    private void updateRecipes(String search) {



        GridLayout grid = findViewById(R.id.recipeGridLayout);

        grid.removeAllViews(); // clear

        /* for each item in list */
        for (int i = 0; i < 8; i++) { //Recipe r  : recipesList) {

            GridLayout.LayoutParams parem = new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 1f),      GridLayout.spec(GridLayout.UNDEFINED, 1f));
            parem.setMargins(25, 18, 18, 12);

            CardView anItem = (CardView) getLayoutInflater().inflate(R.layout.recipe_card, null);
            anItem.setLayoutParams(parem);
            anItem.setPadding(12,12,12,12);

            recursivelySet(anItem, "A", "LOL", "HEE", "HAA");


            grid.addView(anItem);
        }

    }


    /**
     * Dynamic method to set the 4 children of a CardView item
     *
     * @param v - the base card viewgroup
     * @param name
     * @param image
     * @param rating
     * @param cost
     */
    private void recursivelySet(ViewGroup v, String name, String image, String rating, String cost) {

        int items = v.getChildCount();

        // iterate through all child items
        for (int j = 0; j < items; j++) {
            // for layouts recurse downwards
            if (v.getChildAt(j) instanceof LinearLayout) {
                LinearLayout child = (LinearLayout) v.getChildAt(j);

                recursivelySet(child, name, image, rating, cost);
            }
            // for textviews check which it is and set the text property
            else if (v.getChildAt(j) instanceof TextView) {

                TextView child = (TextView) v.getChildAt(j);

                // cost or name
                if (child.getText().equals("$$")) {
                    child.setText(cost);
                } else {
                    child.setText(name);
                }
            }
            // for imageviews check which it is and set the image
            else if (v.getChildAt(j) instanceof ImageView) {

                ImageView child = (ImageView) v.getChildAt(j);

                // review or image
                if (child.getTag() != null && child.getTag().equals("##")) {
                    child.setImageResource(R.drawable.baseline_schedule_24);
                } else {
                    Drawable res = ResourcesCompat.getDrawable(getResources(), R.drawable.round_button, null);
                    child.setImageDrawable(res);
                }
            }
        }

    }


}
