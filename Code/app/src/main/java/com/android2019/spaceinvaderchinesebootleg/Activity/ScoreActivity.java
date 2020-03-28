package com.android2019.spaceinvaderchinesebootleg.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ListView;

import com.android2019.spaceinvaderchinesebootleg.Metier.Score;
import com.android2019.spaceinvaderchinesebootleg.Model.ListAdapter;
import com.android2019.spaceinvaderchinesebootleg.Manager.ScoreManager;
import com.android2019.spaceinvaderchinesebootleg.R;

import java.util.ArrayList;

public class ScoreActivity extends AppCompatActivity {

    private ScoreManager scoreManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.menu_scores);
        scoreManager = new ScoreManager(this);
        scoreManager.Deserialize();

        Bundle b = getIntent().getExtras();
        if(b!=null && b.getString(LoseActivity.NAME_PLAYER) != null){
            scoreManager.add(new Score(b.getString(LoseActivity.NAME_PLAYER),b.getInt(LoseActivity.NUMBER)));
        }

        ListAdapter la;
        if(scoreManager.getScores() == null){
            ArrayList<Score> tmp = new ArrayList<>(); tmp.add(new Score ("Aucune donnée trouvée",0));
            la = new ListAdapter(this, R.layout.layout_listview,tmp);
        }
        else{
            la = new ListAdapter(this, R.layout.layout_listview,scoreManager.getScores());
        }


        ListView lv = findViewById(R.id.list_scores);
        lv.setAdapter(la);
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(scoreManager.getScores() != null) scoreManager.Serialize();
    }
}
