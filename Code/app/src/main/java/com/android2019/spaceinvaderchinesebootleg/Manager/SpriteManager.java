package com.android2019.spaceinvaderchinesebootleg.Manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.android2019.spaceinvaderchinesebootleg.R;

public class SpriteManager {

    private static Bitmap enSprite,shotSprite,explosion_1,explosion_2,explosion_3;
    private static Bitmap playerIdle,playerLeft,playerRight, background;
    private Context activityContext;

    public SpriteManager(Context activityContext){
        this.activityContext = activityContext;
    }

    public void loadSprites(){
        background = BitmapFactory.decodeResource(activityContext.getResources(),R.drawable.background);
        enSprite = BitmapFactory.decodeResource(activityContext.getResources(),R.drawable.enemy);
        shotSprite = BitmapFactory.decodeResource(activityContext.getResources(),R.drawable.shot);
        explosion_1 = BitmapFactory.decodeResource(activityContext.getResources(),R.drawable.explosion_1);
        explosion_2 = BitmapFactory.decodeResource(activityContext.getResources(),R.drawable.explosion_2);
        explosion_3 = BitmapFactory.decodeResource(activityContext.getResources(),R.drawable.explosion_3);

        playerIdle = BitmapFactory.decodeResource(activityContext.getResources(),R.drawable.idle);
        playerLeft = BitmapFactory.decodeResource(activityContext.getResources(),R.drawable.left);
        playerRight = BitmapFactory.decodeResource(activityContext.getResources(),R.drawable.right);
    }

    public static Bitmap getEnSprite(){
        return enSprite;
    }
    public static Bitmap getShotSprite(){
        return shotSprite;
    }
    public static Bitmap getBackground(){
        return background;
    }
    public static Bitmap getExplosion_1(){
        return explosion_1;
    }
    public static Bitmap getExplosion_2(){
        return explosion_2;
    }
    public static Bitmap getExplosion_3(){
        return explosion_3;
    }
    public static Bitmap getPlayerIdle(){
        return playerIdle;
    }
    public static Bitmap getPlayerLeft(){
        return playerLeft;
    }
    public static Bitmap getPlayerRight(){
        return playerRight;
    }

}
