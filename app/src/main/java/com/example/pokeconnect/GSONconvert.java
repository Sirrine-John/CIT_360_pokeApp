package com.example.pokeconnect;

import com.google.gson.*;

public class GSONconvert {

    private String id;
    private String name;
    private String height;
    private String weight;
    private String base_xp;
    private String type1;
    private String type2;
    private String imgID;

    public void setAll(String gsonString){
        JsonObject poke;
        JsonParser parser = new JsonParser();
        poke = parser.parse(gsonString).getAsJsonObject();
        id = poke.get("id").getAsString();
        name = poke.get("name").getAsString();
        height = poke.get("height").getAsString();
        weight = poke.get("weight").getAsString();
        base_xp = poke.get("basexp").getAsString();
        type1 = poke.get("type1").getAsString();
        type2 = poke.get("type2").getAsString();
        imgID = poke.get("spriteloc").getAsString();
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public String getBase_xp() {
        return base_xp;
    }

    public String getType1() {
        return type1;
    }

    public String getType2() {
        return type2;
    }

    public String getImgID() {
        return imgID;
    }


}
