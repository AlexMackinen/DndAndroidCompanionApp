package com.example.alexmackinen.dndapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**SwipeAdapter.java
 * Uses FragmentStatePagesAdapter for a dynamic number of pages
 * keeps track of fragments(pages) in count and position
 * Created by AlexMackinen
 */

public class SwipeAdapter extends FragmentStatePagerAdapter {

    int listLength;                         //Length of the given monster arrayList
    ArrayList<Monster> filMonsters;         //list of filtered monsters

//------------------------------------------------------------------------------------------------------------------------
    //SwipeAdapter
//fragment goes to FragmentStatePagerAdapter class constructor
//also receives up to date filtered monster list

    public SwipeAdapter(FragmentManager fm, ArrayList<Monster> filMon) {
        super(fm);
        listLength = filMon.size();
        filMonsters = filMon;
    }

//------------------------------------------------------------------------------------------------------------------------
    //getItem
//creates a new page fragment stores information into a bundle and sends it to fragment
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new PageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("com.example.alexmackinen.MON_INDEX",position);             //sends the page position
        bundle.putSerializable("com.example.alexmackinen.FILMON",filMonsters);    //sends filtered monster list
        fragment.setArguments(bundle);
        return  fragment;
    }



//-------------------------------------------------------------------------------------------------------------------------
    //getcount
//returns length of list, used to set number of pages
    @Override
    public int getCount() {
        return listLength;
    }
}

//-------------------------------------------------------------------------------------------------------------------------
