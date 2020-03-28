package com.android2019.spaceinvaderchinesebootleg.Manager;

import android.content.Context;

import com.android2019.spaceinvaderchinesebootleg.Data.Save;
import com.android2019.spaceinvaderchinesebootleg.Data.SaveIntoFile;
import com.android2019.spaceinvaderchinesebootleg.Metier.Score;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ScoreManager {
    private List<Score> scores;
    private Save sauveur;
    private static final String FILENAME = "save_scores";


    public ScoreManager(Context context){
        scores = new ArrayList<>();
        sauveur =  new SaveIntoFile(context,FILENAME,scores);
    }

    public void Deserialize(){
        scores = sauveur.Deserialize();
        if(scores != null) Tri();
    }

    public void Serialize(){
        sauveur.Serialize(FILENAME,scores);
    }

    public List<Score> getScores(){
        return scores;
    }
    public void add(Score s){
        if(scores == null) scores = new ArrayList<>();
        scores.add(s);
        Tri();
    }

    private void Tri(){
        Collections.sort(scores, new Comparator<Score>() {
            @Override
            public int compare(Score s1, Score s2)
            {
                return  Integer.valueOf(s2.getNumber()).compareTo(s1.getNumber());
            }
        });
    }

}
