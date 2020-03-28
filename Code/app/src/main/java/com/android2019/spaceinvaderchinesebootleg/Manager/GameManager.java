package com.android2019.spaceinvaderchinesebootleg.Manager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.SensorEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android2019.spaceinvaderchinesebootleg.Activity.LoseActivity;
import com.android2019.spaceinvaderchinesebootleg.Metier.Enemy;
import com.android2019.spaceinvaderchinesebootleg.Metier.Player;
import com.android2019.spaceinvaderchinesebootleg.Metier.Shot;
import com.android2019.spaceinvaderchinesebootleg.Model.GameLoop;
import com.android2019.spaceinvaderchinesebootleg.Model.Wave;
import com.android2019.spaceinvaderchinesebootleg.R;
import com.android2019.spaceinvaderchinesebootleg.View.AnimatedView;

import java.util.ArrayList;
import java.util.Collection;

public class GameManager {
    private static final int PLAYER_RADIUS = 50; //pixels
    private static final int SHOT_WIDTH = 50;
    private static final int TIME_BETWEEN_FRAME = 5;
    private static final int NB_MORE = 5; //nombre d'enemies a rajouter a chaque vague
    private static final int DELTA_X = 50;
    private static final int NEXT_X = 10;
    private static final double PROBABILITY_MOVABLE = 0.5;

    private Object lock;

    //Valeur de depart
    private static final float START_BULLET_SPEED= 15;
    private static final float START_PLAYER_SPEED = 5;
    private static final int START_BULLETS_NB = 10;

    //valeur d'augmentation a chaque click
    private static final float UPGRADE_SPEED_BULLET = 4;
    private static final float UPGRADE_SPEED_PLAYER = 1;
    private static final int UPGRADE_BULLET_NB = 1;

    //parametre ameliorable durant une partie
    private float bulletNb = START_BULLETS_NB;

    private Collection<Shot> shots;
    private Player player;
    private AnimatedView av;
    private Activity ac;
    private float nextPosForPlayer;
    private GameLoop gl;
    private Wave wave;
    private LinearLayout linearlayout;
    private int numWave = 1;
    private int timeBetweenShot; //variable qui aide a gerer le nombre de shot par seconde
    private int timeBetweenEnemy;
    private int score;

    public GameManager(AnimatedView av, Activity ac, Object lock){
        this.av = av;
        this.ac = ac;
        this.lock = lock;
    }

    private void start(float x, float y){
        gl = new GameLoop(TIME_BETWEEN_FRAME,this,av);
        shots = new ArrayList<>();
        shots = new ArrayList<>();
        player = new Player(x, y,PLAYER_RADIUS,START_PLAYER_SPEED);
        nextPosForPlayer = x;
        addShot();
        numWave = 1;
        score = 0;
        wave = new Wave(NB_MORE, DELTA_X, NEXT_X, PROBABILITY_MOVABLE);
        SpriteManager sm = new SpriteManager(ac);
        sm.loadSprites();
        av.setObjects(player,shots);
        gl.start(); //On demarre la loop après les instanciations des objets (receiver du OnSizeChanged)
        LayoutInflater inflater = LayoutInflater.from(av.getContext());
        View v = inflater.inflate(R.layout.in_game_menu,(ViewGroup) av.getRootView()); //inflate le menu in Game
        linearlayout = (LinearLayout) v.findViewById(R.id.layout_game_menu);
        linearlayout.setVisibility(View.GONE);
    }


    private void restart(){
        float h = av.getHeight();
        float w = av.getWidth();
        start(w/2f, h - (w / 4f));
    }


    public void listenerOnSizeChanged(float h, float w){
        h = av.getHeight();
        w = av.getWidth();
        start(w/2f, h-(w/4f));
    }

    public void updatePositions() {
        synchronized (lock) {
            timeBetweenShot += TIME_BETWEEN_FRAME;
            timeBetweenEnemy += TIME_BETWEEN_FRAME;

            if (timeBetweenShot >= 1000 / bulletNb) { // /!\ attention au division par 0
                addShot(); //lorque le temps est depasser, ajouter un nouveau shot
                timeBetweenShot = 0;
            }

            if (timeBetweenEnemy >= 1000 / bulletNb && wave.getNbEnemies() != -1) { // /!\ attention au division par 0
                addEnemy(); //lorque le temps est depasser, ajouter un nouveau shot
                timeBetweenEnemy = 0;
            }

            for (Shot shot : shots) {
                if (shot.isEnable()) {
                    shot.moveBullet();
                    if (shot.getY() <= 0) {
                        shot.disable(); //on le desactive, car on ne veux plus l'afficher, il "n'existe plus" pour le jeu;
                    }
                }
            }

            for (Enemy enemy : wave.getEnemiesList()) {
                if (enemy.isEnable()) {
                    enemy.moveEnemy();
                    if (enemy.getY() >= av.getHeight()) { //mort du player
                        EndGame(enemy);
                    }
                }
            }

            player.movePlayer(nextPosForPlayer);
            //Permet de ne pas dessiner hors de la limite de l'écran (out of bounds)
            if (player.getX() <= player.getRadius()) {
                player.blockPlayer(0);
            } else if (player.getX() >= av.getWidth() - player.getRadius()) {
                player.blockPlayer(av.getWidth());
            }

            collision();
            checkWave();
        }
    }

    private void EndGame(Enemy enemy){
        wave.enemyDeath(enemy);
        gl.terminateThread();
        Intent intent = new Intent(ac, LoseActivity.class);
        intent.putExtra(LoseActivity.NUMBER,score);
        ac.startActivity(intent);
        ac.finish();
    }

    private void collision() {
        for (Enemy enemy : wave.getEnemiesList()) {
            if(enemy.isEnable()){
                for (Shot shot : shots) {
                    float x1 = shot.getX() - shot.getWidth()/2;
                    float x2 = shot.getX() + shot.getWidth()/2;
                    float y = shot.getY() + shot.getHeight()/2;
                    if ((relativeDistance(enemy,x1, y) <= 0 || relativeDistance(enemy, x2, y) <=0) && shot.isEnable()) {
                        wave.enemyDeath(enemy);
                        shot.disable();
                        score += 10;
                    }
                }
            }
        }
    }

    //Calcule la distance relative entre un enemie et un shot
    private float relativeDistance(Enemy enemy, float x , float y){
        double d;
        d = Math.sqrt(
                carre(enemy.getX() - x)
                        +
                        carre(enemy.getY() -y)
        );

        d -= enemy.getRadius();
        return (float) d;
    }

    private float carre(float a){return a*a;}

    public void onSensorEvent (SensorEvent event) {
        nextPosForPlayer = event.values[0]; //A peaufiner ? moyennes des valeurs entre Event et refresh Thread
    }

    //methode qui rajout un shot
    private void addShot() {
        for(Shot shot : shots) {
            if(!shot.isEnable()){ //check s'il n'existe pas un shot desactiver et reutilisable
                shot.resetPos(player.getX(), player.getY());
                shot.enable();      //si oui, reset les position du shot et l'active
                return;
            }
        }
        shots.add(new Shot(player.getX(), player.getY(), SHOT_WIDTH, START_BULLET_SPEED));
    }

    private void addEnemy() {
        for(Enemy enemy : wave.getEnemiesList()) {
            if(!enemy.isEnable() && enemy.getY() <= 0){ //check s'il n'existe pas un enemy desactiver et non utiliser (y < 0)
                enemy.setXRandom(av.getWidth());
                enemy.enable();
                break;
            }
        }
    }

    public Wave getWave(){return wave;}

    private void checkWave() {
        if (wave.isEnd()){
            wave.nextWave();
            score += 100;
            player.addUpgradePoint();
            SetVisibleUpgrade();
        }
    }

    private void SetVisibleUpgrade(){
        ac.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                linearlayout.setVisibility(View.VISIBLE);
            }
        });
    }

    public void upgradeBulletSpeed() {
        if(player.getUpgradePoint() > 0){
            for(Shot shot : shots){
                shot.setBulletSpeed(shot.getBulletSpeed() + UPGRADE_SPEED_BULLET);
            }
            Upgraded();
        }
    }

    public void upgradePlayerSpeed() {
        if(player.getUpgradePoint() > 0){
            player.setSpeed(player.getSpeed() + UPGRADE_SPEED_PLAYER);
            Upgraded();
        }
    }

    public void upgradeBulletNb() {
        if(player.getUpgradePoint() > 0 ){
            bulletNb += UPGRADE_BULLET_NB;
            Upgraded();
        }
    }

    private void Upgraded(){
        player.lessUpgradePoint();
        if(player.getUpgradePoint() <= 0 ){
            linearlayout.setVisibility(View.GONE);
        }
    }

    public Bitmap getPlayerSprite(){
        if((int)nextPosForPlayer == 0) return SpriteManager.getPlayerIdle();
        if(nextPosForPlayer > 1) return SpriteManager.getPlayerLeft();
        return SpriteManager.getPlayerRight();
    }
}