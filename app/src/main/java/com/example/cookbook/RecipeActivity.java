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

    List<Recipe> allRecipes;

    List<Recipe> recipesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);

        // TODO dynamically generate
        allRecipes = new ArrayList<>();
        allRecipes.add(new Recipe("Pizza", "img", 1, 4));
        allRecipes.add(new Recipe("Burgers", "img", 2, 3));
        allRecipes.add(new Recipe("Pasta", "img", 1, 2));
        allRecipes.add(new Recipe("Toastie", "img", 2, 1));
        allRecipes.add(new Recipe("Omlet", "img", 2, 2));
        allRecipes.add(new Recipe("Wraps", "img", 1, 3));
        allRecipes.add(new Recipe("Tachos", "img", 2, 2));
        allRecipes.add(new Recipe("Lentil Stew", "img", 1, 1));
        allRecipes.add(new Recipe("Tamato Soup", "img", 2, 1));
        allRecipes.add(new Recipe("Lamb Pie", "img", 1, 4));

        //setup recipes
        recipesList = new ArrayList<>(allRecipes);

        updateRecipes();


        EditText search = findViewById(R.id.editText);

        search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                String s = ((EditText)v).getText().toString();

                Log.d("HELLO", "Key Pressed :: " + s);

                loadRecipes(s);
                updateRecipes();

                return false;
            }
        });
    }

    /**
     * Function to search for recipes based upon a string and populate the recipeList
     *
     * @param search
     */
    private void loadRecipes(String search) {
        recipesList.clear();

        for (Recipe r : allRecipes) {
            if (r.check(search)) {
                recipesList.add(r);
            }
        }
    }

    /**
     * Function to update the recipes displayed
     *
     */
    private void updateRecipes() {

        GridLayout grid = findViewById(R.id.recipeGridLayout);

        grid.removeAllViews(); // clear

        int i = 0;
        /* for each item in list */
        for (Recipe r  : recipesList) {

            GridLayout.LayoutParams parem = new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 1f),      GridLayout.spec(GridLayout.UNDEFINED, 1f));
            parem.setMargins(25, 18, 18, 12);

            CardView anItem = (CardView) getLayoutInflater().inflate(R.layout.recipe_card, null);
            anItem.setLayoutParams(parem);
            anItem.setPadding(12,12,12,12);

            recursivelySet(anItem, r.name, r.image, r.cost, r.rating);

            grid.addView(anItem);

            i++;
        }

        // for 1 item we pad with an empty item
        if (i == 1) {
            GridLayout.LayoutParams parem = new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 1f),      GridLayout.spec(GridLayout.UNDEFINED, 1f));
            parem.setMargins(25, 18, 18, 12);

            CardView anItem = (CardView) getLayoutInflater().inflate(R.layout.recipe_card, null);
            anItem.setLayoutParams(parem);
            anItem.setPadding(12,12,12,12);
            anItem.setBackgroundResource(R.color.transparent);

            recursivelySet(anItem, "", null, 0, -1);

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
    private void recursivelySet(ViewGroup v, String name, String image, int cost, int rating) {

        int items = v.getChildCount();

        // iterate through all child items
        for (int j = 0; j < items; j++) {
            // for layouts recurse downwards
            if (v.getChildAt(j) instanceof LinearLayout) {
                LinearLayout child = (LinearLayout) v.getChildAt(j);

                recursivelySet(child, name, image, cost, rating);
            }
            // for textviews check which it is and set the text property
            else if (v.getChildAt(j) instanceof TextView) {

                TextView child = (TextView) v.getChildAt(j);

                // cost or name
                if (child.getTag().equals("$$")) {
                    //TODO probably replace this with an image
                    child.setText(  new String(new char[cost]).replace('\0', '$')  ); // NOTE: this is a cursed line that generates X number of $ symbols based upon int cost
                } else {
                    child.setText(name);
                }
            }
            // for imageviews check which it is and set the image
            else if (v.getChildAt(j) instanceof ImageView) {

                ImageView child = (ImageView) v.getChildAt(j);

                // review or image
                if (child.getTag().equals("##")) {
                    if (rating == -1) {
                        child.setImageResource(0);
                    } else {
                        child.setImageResource(R.drawable.baseline_schedule_24);
                    }
                } else {
                    if (image == null) {
                        child.setImageResource(0);
                    } else {
                        Drawable res = ResourcesCompat.getDrawable(getResources(), R.drawable.round_button, null);
                        child.setImageDrawable(res);
                    }
                }
            }
        }

    }


}
