package com.android2019.spaceinvaderchinesebootleg.Model;

import com.android2019.spaceinvaderchinesebootleg.Metier.Enemy;

import java.util.ArrayList;
import java.util.List;

public class Wave {

    private int nbEnemies;          //nombre d'enemies par seconde
    private int nbTotalEnemies;     //nombre total d'enemies pendant la vague
    private int nbEnemyDead;
    private int numWave;
    private int nbMoreEnemies;
    private List<Enemy> enemiesList;
    private int deltaX;
    private int nextX;
    private double probabilityMovable;


    public Wave(int nbmore, int deltaX, int nextX, double probabilityMovable){
        nbEnemies = 0;
        nbTotalEnemies = 0;
        numWave = 0;
        nbMoreEnemies = nbmore;
        enemiesList = new ArrayList<>();
        this.deltaX = deltaX;
        this.nextX = nextX;
        this.probabilityMovable = probabilityMovable;
        nextWave();
    }

    public void enemyDeath(Enemy e){
        e.disable();
        nbEnemyDead++;
    }

    public List<Enemy> getEnemiesList(){
        return enemiesList;
    }
    public int getNbEnemies(){
        return nbEnemies;
    }
    public boolean isEnd() {
        return (nbTotalEnemies == nbEnemyDead);
    }

    public void nextWave(){
        numWave ++;
        nbTotalEnemies += nbMoreEnemies;
        nbEnemies++;
        nbEnemyDead = 0;
        deltaX += nextX;
        for(Enemy enemy: enemiesList){
            enemy.resetPos(0,0);
            enemy.disable();
        }
        for(int i =0; i < nbMoreEnemies ; i++){
            int value = (int)(Math.random() * 50);
            double x = Math.random();
            boolean movable;
            if(x <= probabilityMovable){
                movable = true;
            }else {
                movable = false;
            }
            enemiesList.add(new Enemy(0,0 - (30 + value), 30+ value, 1 + (int)(value/10), deltaX, movable));
        }
    }


}
