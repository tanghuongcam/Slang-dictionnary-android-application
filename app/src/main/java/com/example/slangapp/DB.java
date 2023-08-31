package com.example.slangapp;

public class DB {
    public static String[] getData(int id){
        if (id == R.id.action_eng_fr){
            return getEngFr();
        }
        else if (id == R.id.action_eng_vn) {
            return getEngVn();
        }
        else if (id == R.id.action_fr_vn) {
            return getFrVn();
        }
        return new String[0];
    }

    public static String[] getEngFr(){
        String[] source = new String[]{
                "Awesome ",
                "Bail ",
                "Babe ",
                "Cool ",
                "Dude ",
                "Pissed",
                "Suck",
                "Bae",
                "Bestie",
                "Bro ",
                "Crush ",
                "Dump",
                "Fam ",
                "Ghosted ",
                "Tight",
                "YOLO",
                "Hang out",
                "OG",
                "BFF"

        };
        return source;
    }


    public static String[] getFrVn(){
        String[] source = new String[]{
                "Chelou",
                "Cimer",
                "Boloss",
                "Kiffer",
                "Relou",
                "Dar",
                "Chanmé",
                "Meuf",
                "Gavé",
                "Zbeul",
                "Pélo",
                "Flamber",
                "Taffer",
                "Zoner",
                "Bolide",
                "Bagnole",
                "Brico",
                "Choper",
                "Oseille"

        };
        return source;
    }


    public static String[] getEngVn(){
        String[] source = new String[]{
                "IRL ",
                "Cringe ",
                "Cushy ",
                "Dope ",
                "Lame ",
                "Lit",
                "Low-Key",
                "Salty",
                "Shady",
                "Sick ",
                "Swag ",
                "Sweet",
                "Thirsty ",
                "Hangry",
                "Vanilla",
                "YOLO",
                "Hang out",
                "OG",
                "BFF"

        };
        return source;
    }

}
