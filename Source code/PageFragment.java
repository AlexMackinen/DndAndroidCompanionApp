package com.example.alexmackinen.dndapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


/**PageFragment.java
 * A simple {@link Fragment} subclass.
 * Page fragment is what gets shown when you select a monster information from that
 * monster is placed within text views of an XML layout I have made.
 *
 * Created by Alex Mackinen
 */

public class PageFragment extends Fragment {

    //<TextViews>
    TextView monsterSwipeNameTxtView;
    TextView monsterSizeTypeTxtView;
    TextView monsterAlignmentTxtView;
    TextView monsterHpTextView;
    TextView monsterStrTxtView;
    TextView monsterDexTxtView;
    TextView monsterConTxtView;
    TextView monsterIntTxtView;
    TextView monsterWisTxtView;
    TextView monsterChaTxtView;
    //</TextViews>

    ArrayList<String> monInfo;              //monInfo a Array List of strings that hold information on a certain monsters

//-----------------------------------------------------------------------------------------------------------------------------------------
    //PageFragment()
//empty required constructor

    public PageFragment() {
        // Required empty public constructor
    }

//-----------------------------------------------------------------------------------------------------------------------------------------
    //onCreateView
//this method forms connections to the XML files TextViews then sets their values with monster data

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.page_fragment_layout,container,false);     // Inflate the layout for this fragment

        //<TxtView Connections>
        monsterSwipeNameTxtView = view.findViewById(R.id.MonsterSwipeNameTxtView);
        monsterSizeTypeTxtView = view.findViewById(R.id.SizeTypeTextView);
        monsterHpTextView = view.findViewById(R.id.HitpointsTextView);
        monsterAlignmentTxtView = view.findViewById(R.id.AlignmentTextView);
        monsterStrTxtView = view.findViewById(R.id.StrValueTxtView);
        monsterDexTxtView = view.findViewById(R.id.DexValueTxtView);
        monsterConTxtView = view.findViewById(R.id.ConValueTxtView);
        monsterIntTxtView = view.findViewById(R.id.IntValueTxtView);
        monsterWisTxtView = view.findViewById(R.id.WisValueTxtView);
        monsterChaTxtView = view.findViewById(R.id.ChaValueTxtView);
        //</TxtView Connections>

        //monster info passed down from Swipe adapter
        Bundle bundle = getArguments();
        ArrayList<Monster> filmonsters = (ArrayList<Monster>) bundle.getSerializable("com.example.alexmackinen.FILMON");
        int index = bundle.getInt("com.example.alexmackinen.MON_INDEX",-1);



        Monster monster = filmonsters.get(index);                    //get the current monster
        monInfo = monster.getList();                                 //set the current monsters list


        //putParas extra parameter is to include additional info inside paras as well as to not repeat the action
        monInfo.set(2,putPara(monInfo.get(2),""));                                                                  //put this string into parenthesis
        monInfo.set(5,putPara(monInfo.get(5), (monster.getHpBonus()<0 ? "":"+") + ""  + monster.getHpBonus()));     //add a sign to hp bonus the put in para


        //<Set Texts>
        monsterSizeTypeTxtView.setText(monInfo.get(0) + " " + monInfo.get(1) + " " + monInfo.get(2) );
        monsterSwipeNameTxtView.setText(monster.getName());
        monsterAlignmentTxtView.setText(monInfo.get(3));
        monsterHpTextView.setText(monInfo.get(4) + monInfo.get(5));
        monsterStrTxtView.setText(monster.getModString(monster.getAbilityScores()[0]));
        monsterDexTxtView.setText(monster.getModString(monster.getAbilityScores()[1]));
        monsterConTxtView.setText(monster.getModString(monster.getAbilityScores()[2]));
        monsterIntTxtView.setText(monster.getModString(monster.getAbilityScores()[3]));
        monsterWisTxtView.setText(monster.getModString(monster.getAbilityScores()[4]));
        monsterChaTxtView.setText(monster.getModString(monster.getAbilityScores()[5]));
        //</Set Texts>

        return view;
    }

//-------------------------------------------------------------------------------------------------------------------------------------------
    //putPara
//Simply returns the string given to it with parenthesis
//second parameter used for any extra info you want to provide

    private String putPara(String value, String extra) {


        if(value != ""  && value.charAt(0) != '(') {         //only adds paras if string is not empty and not already para'd
            value = "(" + value + "" + extra + ")";
        }

        return value;
    }

}

//-------------------------------------------------------------------------------------------------------------------------------------------
