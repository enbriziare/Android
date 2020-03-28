package com.android2019.spaceinvaderchinesebootleg.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import com.android2019.spaceinvaderchinesebootleg.R;

/**
 * Example activity that contains a view that reads accelerometer sensor input and
 * translates a circle based on the changes.
 */
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);
        Bundle b = getIntent().getExtras();
        if(b!= null && b.getString(LoseActivity.NAME_PLAYER) != null) {
            startMenuScores(b);
        }
    }

    public void onStartGame(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void onStartOption(View view) {
        startMenuScores(null);
    }

    private void startMenuScores(Bundle b){
        Intent intent = new Intent(this, ScoreActivity.class);
        if(b!=null) {
            intent.putExtra(LoseActivity.NAME_PLAYER, b.getString(LoseActivity.NAME_PLAYER));
            intent.putExtra(LoseActivity.NUMBER, b.getInt(LoseActivity.NUMBER));
        }
        startActivity(intent);
    }
}