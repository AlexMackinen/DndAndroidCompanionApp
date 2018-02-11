package com.example.alexmackinen.dndapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/*MainActivity.java
DndApp is intended to be a companion app for the rpg Dungeons and dragons. The app is quicker reference
for finding information on monsters/characters that can appear in the game. for now the app is a monster reference,
but I hope to include items and spells, and maybe even rudimentary battle system.

Created by Alex Mackinen
*/

public class MainActivity extends AppCompatActivity {

    ListView myListView;              //the Xml list view
    ArrayList<Monster> monsters;      //the complete list of monsters
    ArrayList<Monster> filMonsters;   //the search result list of monsters
    EditText monSearch;               //the Xml search bar





//-------------------------------------------------------------------------------------------------------------------------------------------------------
    //onCreate
//when the Activity(the current screen) is created connect my variables to their Xml counterpart
//populate the monster arrayList with the monsters and their attributes, and set up the Listview to use my XML layout
//include a text, and click listener

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        monsters = new ArrayList<>();                        //initialize monsters to be empty
        monSearch = findViewById(R.id.MonSearchEditText);    //find the XML search bar
        myListView = findViewById(R.id.MonsterListView);     //find the XML listview

        populate();                                          //populate the newly initialized list

        final MonsterAdapt monsterAdapt = new MonsterAdapt(this, monsters);      //my own version of baseAdapter

        myListView.setAdapter(monsterAdapt);                 //tell the list view to use my version of the Adapter

        myListView.setTextFilterEnabled(true);               //allows my list to be filtered

        monSearch.addTextChangedListener(new TextWatcher() {                       //When the content in my search bar changes
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                monsterAdapt.getFilter().filter(charSequence.toString());     //filter the list using the contents of my search bar


            }

            @Override
            public void afterTextChanged(Editable editable) {
                filMonsters = monsterAdapt.getFilMonsters();                 //once done retrieve the updated filtered list
            }
        });

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {   //When an item on the list is clicked
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(filMonsters == null){     //if filtered list doesn't set it to the full list
                    filMonsters = monsters;
                }

                Intent showMonDetail = new Intent(getApplicationContext(), SwipeView.class);        //when an item is clicked a new activity is opened
                showMonDetail.putExtra("com.example.alexmackinen.FILMONSTERS",filMonsters);   //send filtered monster list to new activity
                showMonDetail.putExtra("com.example.alexmackinen.MON_INDEX", i);              //tell the next window what monster was clicked

                startActivity(showMonDetail); //open activity
            }
        });
    }

//-------------------------------------------------------------------------------------------------------------------------------------------------------
    //populate
//populates the monster list using a json file
//creates a json array to loop through
//on each loop extract a json object with info on a single monster use it to create a monster and pass on that object to the class
    protected void populate() {
        try {
            InputStream inputStream = getAssets().open("monsters.json");    //find the json file in the assets folder

            byte[] buffer  = new byte[inputStream.available()];                     //create a buffer to store the info
            inputStream.read(buffer);                                               //interpret the information inside
            inputStream.close();                                                    //close when done

            String json = new String(buffer,"UTF-8");                    //convert it all into a String
            JSONArray monJs = new JSONArray(json);                                  //create the Json array

            for(int i = 0; i < monJs.length(); i++) {                               //loop through json array

                JSONObject monJsObj = (JSONObject) monJs.get(i);                    //extract json Object
                String nameTemp = (String) monJsObj.get("name");                    //get monster name from object
                int AcTemp = (int) monJsObj.get("armor_class");                     //get armor class from object
                monsters.add(new Monster(nameTemp,AcTemp));                         //create monster and add to list
                monsters.get(i).populate(monJsObj);                                 //give monster the object to get the rest of the info

            }

            filMonsters = monsters;                                                 //set the filter monster list so its not null;

        } catch (IOException e) {                                                   //catch Exceptions
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

//--------------------------------------------------------------------------------------------------------------------------------------------------------------
