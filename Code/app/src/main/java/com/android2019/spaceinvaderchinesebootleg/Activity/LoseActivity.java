package com.android2019.spaceinvaderchinesebootleg.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.android2019.spaceinvaderchinesebootleg.R;

public class LoseActivity extends Activity {

    private static final int MAX_CHARS = 30;
    private static final String IS_HIGHSCORE = "is in layout highscore ?";
    public static final String NAME_PLAYER = "name_player";
    public static final String NUMBER = "number";
    private boolean is_highscore = false;
    private String name_player = "";
    private int number_score;
    private EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Bundle b = getIntent().getExtras();
        if(b!=null)number_score = b.getInt(NUMBER);
        if(savedInstanceState != null){
            is_highscore = savedInstanceState.getBoolean(IS_HIGHSCORE);
            name_player = savedInstanceState.getString(NAME_PLAYER);
        }
        setGoodContent();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(IS_HIGHSCORE, is_highscore);
        if(name != null) savedInstanceState.putString(NAME_PLAYER, name.getText().toString());
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        is_highscore = savedInstanceState.getBoolean(IS_HIGHSCORE);
        name_player = savedInstanceState.getString(NAME_PLAYER);
    }

    private void setGoodContent(){
        if(!is_highscore){
            setContentView(R.layout.activity_lose);
        }
        else{
            setContentView(R.layout.activity_highscore);
            name = (EditText) findViewById(R.id.name);
            name.setText(name_player);
        }
    }

    public void RestartGame(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void FinishGame(View view) {
        setContentView(R.layout.activity_highscore);
        name = (EditText) findViewById(R.id.name);
        is_highscore = true;
    }


    public void GoToMenu(View view) {
        if(name == null) return;
        if(checkText(name.getText().toString())){
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(NAME_PLAYER, name.getText().toString());
            intent.putExtra(NUMBER,number_score);
            startActivity(intent);
            this.finish();
        }
    }

    private boolean checkText(String name){
        return (name.length() != 0 && name.length() <= MAX_CHARS);
    }

}
