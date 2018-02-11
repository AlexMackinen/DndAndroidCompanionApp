package com.example.alexmackinen.dndapp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**Monster.java
 * manipulates data from the json file to ensure I get all the data I need to the right place
 * holds all information needed to fully understand a monster in the game
 * Created by AlexMackinen
 */

public class Monster implements Serializable {

    private String name;                        //monster name duh
    private int AC;                             //monster Armor class(will probably be replaced with challenge rating in due time)
    private int index;                          //index of the monster
    private int hpBonus;                        //monsters get a hp bonus based on stats so it can have a good amount of health in case of bad rolls
    private int[] abilityScores = new int[6];   //0:Strength,1:Dexterity,2:constitution,3:Intelligence,4:Wisdom,5:Charisma

    private ArrayList<String> monsterDesc; //0:Size,1:type,2:subtype,3:alignment,4:hitPoints,5:hit_dice,6:speed,7:senses,8:languages,9:challenge rating

    private JSONObject actions;                 //unused (for now)
    private JSONObject specialAbilities;        //unused (for now)
    private JSONObject legendaryActions;        //unused (for now)

//--------------------------------------------------------------------------------------------------------------------------------------------
    //Monster
//this constructor takes only a String and an int
//sets name, int, and initializes the monsterDesc array list

    public Monster(String n, int a) {
        name = n;
        AC = a;
        monsterDesc = new ArrayList<>();
    }

//--------------------------------------------------------------------------------------------------------------------------------------------
    //Monster
//this constructor takes in all the usable(Currently) information and sets it
//sets name, Ac, AbilityScores, MonsterDesc List, and the HP bonus

    public Monster(String n, int ac, int[] scores, ArrayList<String> list, int bonus) {
        name = n;
        AC = ac;
        abilityScores = scores;
        monsterDesc = list;
        hpBonus = bonus;
    }

//---------------------------------------------------------------------------------------------------------------------------------------------
    //populate
//finds values for Lists, Arrays, and single variables
    public void populate(JSONObject json) {
        try {
            index = json.getInt("index") - 1;
            String input = (String)json.get("hit_dice");

            abilityScores[0] = json.getInt("strength");
            abilityScores[1] = json.getInt("dexterity");
            abilityScores[2] = json.getInt("constitution");
            abilityScores[3] = json.getInt("intelligence");
            abilityScores[4] = json.getInt("wisdom");
            abilityScores[5] = json.getInt("charisma");
            hpBonus = extractNum(input) * getModInt(abilityScores[2]);
            monsterDesc.add((String) json.get("size"));
            monsterDesc.add((String) json.get("type"));
            monsterDesc.add((String) json.get("subtype"));
            monsterDesc.add((String) json.get("alignment"));
            monsterDesc.add( json.get("hit_points")+"");
            monsterDesc.add((String) json.get("hit_dice"));
            monsterDesc.add((String) json.get("speed"));
            monsterDesc.add((String) json.get("senses"));
            monsterDesc.add((String) json.get("languages"));
            monsterDesc.add( json.get("challenge_rating")+"");

            if( json.has("actions")){

                actions = json.getJSONObject("actions");

            }
            if( json.has("special_abilities")) {

                specialAbilities = json.getJSONObject("special_abilities");

            }
            if( json.has("legendary_actions")) {

                legendaryActions = json.getJSONObject("legendary_actions");

            }

        } catch (JSONException e) {

            e.printStackTrace();

        }
    }

//--------------------------------------------------------------------------------------------------------------------------------------------
    //extractNum
//Some Text inputs have numbers in strings like this 10d4
//this method extracts that first number

    private int extractNum(String text) {

        int num;
        int i = 0;
        String result = "";

        while(text.charAt(i) > 47 && text.charAt(i) < 58){

            result += text.charAt(i);
            i++;

        }
        num = Integer.parseInt(result);
        return num;
    }

//----------------------------------------------------------------------------------------------------------------------------------------------
    //getModInt
//returns mod as an Integer Ability scores have modulators
//this is need as an int for calculations

    public int getModInt(int num) {
        num = num - 10;

        if(num%2 != 0) {     //if odd round down

            num = num - 1;

        }
        num = num / 2;
        return num;
    }

//-----------------------------------------------------------------------------------------------------------------------------------------------
    //getModString
//returns mod as a string adds para and symbol

    public String getModString(int num) {
        String result;
        int modifier = getModInt(num);

        if(modifier >= 0 ) {

            result = num + "(+" + modifier + ")";

        }
        else {

            result = num + "(-" + modifier + ")";

        }

        return result;
    }

//--------------------------------------------------------------------------------------------------------------------------------------------------
    //getters/setters
//gets and sets variables not all are used

    public ArrayList<String> getList() {return  monsterDesc;}

    public void setList(ArrayList<String> list) { monsterDesc = list; }

    public void setAbilityScores(int[] scores) { abilityScores = scores; }

    public void setHpBonus(int bonus) { hpBonus = bonus; }

    public int getIndex() {return index;}

    public int getHpBonus() {return hpBonus;}

    public int[] getAbilityScores() { return abilityScores; }

    public JSONObject getActions() {
        return actions;
    }

    public JSONObject getLegendaryActions() {
        return legendaryActions;
    }

    public JSONObject getSpecialAbilities() {
        return specialAbilities;
    }

    public String getName() {
        return name;
    }

    public int getAC() {
        return AC;
    }
}

//--------------------------------------------------------------------------------------------------------------------------------------------------------
