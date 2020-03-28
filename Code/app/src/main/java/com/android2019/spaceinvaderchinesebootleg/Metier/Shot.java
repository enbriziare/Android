package com.android2019.spaceinvaderchinesebootleg.Metier;

public class Shot {

    private float x;
    private float y;
    private int height;
    private int width;
    private float bulletSpeed;
    private boolean enable = true; // boolean qui definie si il est activer ou non

    //constructeur basique on est considere que la hauteur d'un shot et 2 fois ça largeur
    public Shot(float x, float y, int width, float bulletSpeed){
        this.x = x;
        this.y = y;
        this.width = width;
        height = width*2;
        this.bulletSpeed = bulletSpeed;
    }

    //constructeur qui permet de derterminer la hauteur et la largeur
    public Shot(float x, float y, int height,int width, float bulletSpeed){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bulletSpeed = bulletSpeed;
    }




    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
    public float getHeight(){return height;}
    public float getWidth(){return width;}

    public float getBulletSpeed(){return bulletSpeed;}

    public void setBulletSpeed(float bulletSpeed){
        this.bulletSpeed = bulletSpeed;
    }

    public boolean isEnable(){return enable; }
    public void disable(){enable = false;}
    public void enable(){enable = true;}

    public void moveBullet(){
        y = y - bulletSpeed;
    }

    public void resetPos(float x, float y){
        this.x = x;
        this.y = y;
    }


}
