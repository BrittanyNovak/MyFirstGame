package com.example.student.myfirstgame;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by student on 10/6/2015.
 */
public class MainThread extends Thread {

    private int FPS = 30;
    private double averageFPS;
    private boolean running;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private Canvas canvas;


    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel){
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    private void setRunning(boolean running){
        this.running = running;
    }

    @Override
    public void run(){
        long startTime;
        long timeMillis;
        long targetTime = 1000/FPS;
        long waitTime;
        long totalTime = 0;
        long averageFPS;
        int frameCount = 0;

        while (running){
            startTime = System.nanoTime();
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                }

            }catch (Exception ex){

            }finally{
                if (canvas!=null){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                }

            }

        timeMillis = (System.nanoTime() - startTime)/1000000;
            waitTime = targetTime - timeMillis;

            try{

                this.sleep(waitTime);
            } catch (Exception ex){

            }

            totalTime += System.nanoTime() - startTime;
            frameCount++;

            if(frameCount == 30){
                averageFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.println(averageFPS);
            }
        }
    }
}
