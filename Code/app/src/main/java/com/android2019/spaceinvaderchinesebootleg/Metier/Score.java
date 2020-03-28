package com.android2019.spaceinvaderchinesebootleg.Metier;

public class Score {
    private int number;
    private String name;

    public Score(String name,int number){
        this.number = number;
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public int getNumber(){
        return number;
    }
}
