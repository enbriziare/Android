package com.android2019.spaceinvaderchinesebootleg.Metier;


public class Enemy {
    private float x;
    private float originalX;
    private float y;
    private float radius;
    private float originRadius;
    private float enemySpeed;
    private int deltaX;
    private boolean enable = false; // boolean qui definie si il est activer ou non
    private boolean movable;

    public Enemy(float x, float y, float radius, float enemySpeed,int deltaX, boolean movable){
        this.x = x;
        originalX = x;
        this.y = y;
        this.radius = radius;
        originRadius = radius;
        this.deltaX = deltaX;
        this.enemySpeed = enemySpeed;
        this.movable = movable;
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

    public boolean isEnable(){
        return enable;
    }
    public void disable(){
        enable = false;
    }
    public void enable(){
        enable = true;
        radius = originRadius;
    }

    public void moveEnemy(){
        y = y + enemySpeed;
        x = originalX;
        if(movable){
            float delta =(float) (deltaX* Math.cos((double) y/150));
            x = originalX + delta;
        }
    }

    public void resetPos(float x, float y){
        originalX = x;
        this.y = y;
    }

    public void setXRandom(int width) {
        originalX = (int)(Math.random() * width);
    }
}
