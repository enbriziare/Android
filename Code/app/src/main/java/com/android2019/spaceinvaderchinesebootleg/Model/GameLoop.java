package com.android2019.spaceinvaderchinesebootleg.Model;

import com.android2019.spaceinvaderchinesebootleg.Manager.GameManager;
import com.android2019.spaceinvaderchinesebootleg.View.AnimatedView;

public class GameLoop extends Thread {
    private boolean terminated;
    private int timeToWait;
    private GameManager gm;
    private AnimatedView av;

    public GameLoop(int timeToWait, GameManager gm, AnimatedView av){
        this.timeToWait = timeToWait;
        this.gm = gm;
        this.av = av;
        terminated = false;
    }

    @Override
    public void run(){
        while(!terminated){
            gm.updatePositions();
            av.updateView();

            try{
                sleep(timeToWait);
            }
            catch(Exception e){
                terminateThread();
            }
        }
    }

    public void terminateThread(){
        terminated = true;
    }
}
