package com.example.alexmackinen.dndapp;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

/**MonsterAdapt.java
 * The monster adapter class is used to set this activity's text views
 * filtering the monster list based on a searched term
 * highlighting the given searched term
 * Created by AlexMackinen
 */

public class MonsterAdapt extends BaseAdapter implements Filterable {

    private LayoutInflater mInflator;                           //inflater for future use
    private ArrayList<Monster> oriMonsters;                    //the original list of monsters
    private ArrayList<Monster> filMonsters;                    //the filtered list of monsters

    String searchText = "";                                    //holds the searched text

//----------------------------------------------------------------------------------------------------------------------
    //MonsterAdapt
//constructor sets both lists of monsters to the full list

    public MonsterAdapt(Context c, ArrayList<Monster> m){
        oriMonsters = m;
        filMonsters = m;
        mInflator = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

//----------------------------------------------------------------------------------------------------------------------
    //getCount
//returns the size of the filtered list

    @Override
    public int getCount() {
        return filMonsters.size();
    }

//---------------------------------------------------------------------------------------------------------------------
    //getItem
//returns an object(Monster) from the monster list

    @Override
    public Object getItem(int i) {
        return filMonsters.get(i);
    }

//---------------------------------------------------------------------------------------------------------------------
    //getFilMonsters
//returns the filtered monster list

    public ArrayList<Monster> getFilMonsters() { return filMonsters;   }

//---------------------------------------------------------------------------------------------------------------------
    //getItemId
//required overload just returns i

    @Override
    public long getItemId(int i) {
        return i;
    }

//---------------------------------------------------------------------------------------------------------------------
    //getView
//Sets the TextViews for this activity's layout and highlights occurances of searched
//term within the monster list

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        /**<Setting TextViews>******************************************/

        View v = mInflator.inflate(R.layout.monster_listview, null);
        TextView monsterNameTextView = v.findViewById(R.id.MonsteNameTxtView);
        TextView monsterAcTextView = v.findViewById(R.id.MonsterACTextView);


        String name = filMonsters.get(i).getName();
        String Ac = filMonsters.get(i).getAC() + "";

        monsterNameTextView.setText(name);
        monsterAcTextView.setText(Ac);

        /**</Setting TextViews>*****************************************/

        String fullText = filMonsters.get(i).getName();         //name string for reference

        if (searchText != null && !searchText.isEmpty()) {      //only check if something has been searched

            int startPos = fullText.toLowerCase(Locale.US).indexOf(searchText.toLowerCase(Locale.US));     //where the search text starts in the monster name
            int endPos = startPos + searchText.length();                                                   //where the search text ends in the monster name

            if (startPos != -1) {

                Spannable spannable = new SpannableString(fullText);                                                                        //set the name string as spannable
                ColorStateList blueColor = new ColorStateList(new int[][]{new int[]{}}, new int[]{Color.BLUE});                             //the color blue
                TextAppearanceSpan highlightSpan = new TextAppearanceSpan(null, Typeface.BOLD, -1, blueColor, null);      //Highlight fon/color settings
                spannable.setSpan(highlightSpan, startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);                                     //apply settings between start/end
                monsterNameTextView.setText(spannable);                                                                                     //set the text to the TextView

            } else {

                monsterNameTextView.setText(fullText);                  //set normal text

            }
        } else {

            monsterNameTextView.setText(fullText);                     //set normal text

        }

        return v;                                                      //return the view
    }

//------------------------------------------------------------------------------------------------------------------------------
    //get filter
//this method filters the full monster list and adds matching results to a
//new list that becomes the filtered list

    @Override
    public Filter getFilter() {

        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {       //takes in char sequence(Searched Text)

                FilterResults results = new FilterResults();
                searchText = (String)charSequence;                                      //makes the currently searched word global for highlighting the text

                if(charSequence == null || charSequence.length() == 0){                 //if nothing is searched show the whole list

                    results.values = oriMonsters;
                    results.count = oriMonsters.size();

                } else {

                    ArrayList<Monster> filterResults = new ArrayList<>();              //create a new monster array list to store matches

                    for(int i = 0; i < oriMonsters.size(); i++) {                      //loop through all the monsters

                        String data = oriMonsters.get(i).getName();                    //get the name to compare

                        if ( data.toUpperCase().contains(charSequence.toString().toUpperCase())) {        //if the name contains the searchable word add it to the list

                            filterResults.add(new Monster(oriMonsters.get(i).getName(), oriMonsters.get(i).getAC(), oriMonsters.get(i).getAbilityScores(),
                                    oriMonsters.get(i).getList(), oriMonsters.get(i).getHpBonus()));

                        }
                    }

                    filMonsters = filterResults;               //save the results for myself

                    results.values = filterResults;
                    results.count = filterResults.size();

                }

                return results;                               //return results

            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                notifyDataSetChanged();                //update

            }
        };
    }
}

//----------------------------------------------------------------------------------------------------------------------------------