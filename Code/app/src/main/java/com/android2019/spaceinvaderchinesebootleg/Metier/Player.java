package com.android2019.spaceinvaderchinesebootleg.Metier;

public class Player {

    private float x;
    private float y;
    private float radius;
    private float speed;
    private int upgradePoint;

    public Player(float x, float y, float radius, float speed){
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.speed = speed;
    }

    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
    public float getRadius(){
        return radius;
    }
    public float getSpeed(){return speed;}
    public void setSpeed(float speed){this.speed = speed;}
    public int getUpgradePoint(){return upgradePoint;}
    public void addUpgradePoint(){upgradePoint++;}
    public void lessUpgradePoint(){upgradePoint--;}

    public void movePlayer(float valSensor){
        x = x - (speed * valSensor);
    }

    public void blockPlayer(float offset){
        x = Math.abs(offset - radius); //Valeur absolu sinon le x = -radius dans le cas du gauche
    }

}
