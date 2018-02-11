package com.example.alexmackinen.dndapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

/** SwipeView.java
 sets the activities adapter with SwipeAdapter
 sets the layout I created
 sets the page fragment page
 */

public class SwipeView extends FragmentActivity {
    ViewPager swipeView;                                   //the Activities XML Viewpager
    int index = 0;                                         //the index
    ArrayList<Monster> monsters = new ArrayList<>();       //intended to hold monster lists

//------------------------------------------------------------------------------------------------------------------------------------------------------
    //onCreate
//sets up my XML and adapter as well as setting the correct pageFragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent in = getIntent();                                                                  //pulling monster list and index from main activity
        if(in != null) {
            index = in.getIntExtra("com.example.alexmackinen.MON_INDEX", -1);

            monsters = (ArrayList<Monster>) in.getSerializableExtra("com.example.alexmackinen.FILMONSTERS");
        }


        super.onCreate(savedInstanceState);                                                       //calls supers constructor



        setContentView(R.layout.activity_swipe_view);                                             //sets this activities layout
        swipeView = findViewById(R.id.SwipeView);                                                 //finds the viewPager
        SwipeAdapter swipeAdapter = new SwipeAdapter(getSupportFragmentManager(),monsters);       //creates the new adapter passes the monster list


        swipeView.setAdapter(swipeAdapter);                                                       //sets this activities adapter as the newly created one
        swipeView.setCurrentItem(index);                                                          //goes to the page specified (the current monster's index)

    }
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------
