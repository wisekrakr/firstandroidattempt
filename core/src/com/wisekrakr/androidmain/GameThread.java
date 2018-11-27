package com.wisekrakr.androidmain;

import com.wisekrakr.androidmain.screens.PlayScreen;

public class GameThread extends Thread {

    private AndroidGame game;
    private PlayScreen playScreen;
    private boolean running;


    public GameThread(AndroidGame game, PlayScreen playScreen) {
        this.game = game;
        this.playScreen = playScreen;
    }

    @Override
    public void run() {
        long startTime = System.nanoTime();

        while(running)  {

            long now = System.nanoTime() ;


            long waitTime = (now - startTime)/1000000;
            if(waitTime < 10)  {
                waitTime= 10; // Millisecond.
            }
            System.out.print(" Wait Time="+ waitTime);

            try {
                // Sleep.
                this.sleep(waitTime);
            } catch(InterruptedException e)  {

            }
            startTime = System.nanoTime();
            System.out.print(".");
        }
    }
    public void setRunning(boolean running)  {
        this.running= running;
    }

}
