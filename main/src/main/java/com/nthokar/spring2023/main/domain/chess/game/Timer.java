package com.nthokar.spring2023.main.domain.chess.game;

import lombok.AllArgsConstructor;

import java.awt.*;

@AllArgsConstructor
public class  Timer {
    /**
     * actions that would be executed when white's or black's time spent
     */
    public ActiveEvent onTimeEnd;

    float whitePlayerTime;
    float blackPlayerTime;
    float whiteExtraTime;
    float blackExtraTime;

    private float measure;

    public Timer(float playerTime, float extraTime) {
        this.whitePlayerTime = playerTime;
        this.blackPlayerTime = playerTime;
        this.whiteExtraTime = extraTime;
        this.blackExtraTime = extraTime;
    }

    //measure time of runnable function
    public float measureTime(Runnable runnable){
        startMeasure();
        runnable.run();
        return endMeasure();
    }
    //measure time until player apply correct move
    public void measureTimeForPlayer(Runnable awaitMove, Color color){
        var spendTime = measureTime(awaitMove);
        if (color == Color.WHITE)
            whitePlayerTime -= spendTime + whiteExtraTime;
        else
            blackPlayerTime -= spendTime + blackExtraTime;
    }

    private void startMeasure(){
        measure = System.currentTimeMillis();
    }

    private float endMeasure(){
        return System.currentTimeMillis() - measure;
    }
}
