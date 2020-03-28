package com.android2019.spaceinvaderchinesebootleg.View;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

import com.android2019.spaceinvaderchinesebootleg.Metier.Enemy;
import com.android2019.spaceinvaderchinesebootleg.Metier.Player;
import com.android2019.spaceinvaderchinesebootleg.Metier.Shot;
import com.android2019.spaceinvaderchinesebootleg.Manager.GameManager;
import com.android2019.spaceinvaderchinesebootleg.Manager.SpriteManager;

import java.util.Collection;

public class AnimatedView extends View {
    private Collection<Shot> shots;
    private Player player;
    private GameManager gm;
    private Activity ac;
    private Bitmap playerSprite;
    private Object lock;

    public AnimatedView(Context context, Object lock) {
        super(context);
        ac = (Activity) context;
        playerSprite = SpriteManager.getPlayerIdle();
        this.lock = lock;
    }

    public void setObjects(Player p, Collection<Shot> s){
        player = p;
        shots = s;
    }

    public void setGM(GameManager gm){
        this.gm = gm;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        gm.listenerOnSizeChanged(w,h);
    }

    @Override
    protected void onDraw(Canvas canvas){
        synchronized (lock){
            canvas.drawBitmap(SpriteManager.getBackground(),0,0,null);

            for(Shot shot : shots) {
                if(shot.isEnable()) { //n'affiche que les shot activer
                    canvas.drawBitmap(Bitmap.createScaledBitmap(SpriteManager.getShotSprite(),(int) shot.getWidth(), (int)shot.getHeight(), false),shot.getX(), shot.getY(), null);
                }
            }

            for(Enemy enemy: gm.getWave().getEnemiesList()){
                if(enemy.isEnable()){
                    canvas.drawBitmap(Bitmap.createScaledBitmap(SpriteManager.getEnSprite(),(int) enemy.getRadius()*2, (int)enemy.getRadius()*2, false),enemy.getX(), enemy.getY(),null);
                }
            }

            if(playerSprite != null) canvas.drawBitmap(playerSprite,player.getX(), player.getY(),null);
        }
    }

    public void DrawExplosion(Enemy en){
        //AnimationDrawable anim = setBackground(R.drawable.animation_explosion);
        //anim.start();
    }

    public void updateView() {
        ac.runOnUiThread(new Runnable() { //Permet d'appeller invalidate depuis le Thread 1
            @Override
            public void run() {
                playerSprite = gm.getPlayerSprite();
                invalidate();
            }
        });
        //Sinon le invalidate est appellé depuis un Thread plus bas, et pas d'autorisation
    }
}