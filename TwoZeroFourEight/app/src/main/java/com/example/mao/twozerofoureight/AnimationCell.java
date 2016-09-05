package com.example.mao.twozerofoureight;

public class AnimationCell extends Cell {
    public final int[] extras;
    private final int animationType;
    private final long animationTime;
    private final long delayTime;
    private long timeElapsed;

    public AnimationCell(int x, int y, int animationType, long animationTime, long delayTime, int[] extras) {
        super(x, y);
        this.extras = extras;
        this.animationType = animationType;
        this.animationTime = animationTime;
        this.delayTime = delayTime;
    }

    public int getAnimationType() {
        return animationType;
    }

    public void tick(long timeElapsed) {
        this.timeElapsed = this.timeElapsed + timeElapsed;
    }

    public boolean animationDone() {
        return animationTime + delayTime < timeElapsed;
    }

    public double getPercentageDone() {
        return Math.max(0, 1.0 * (timeElapsed - delayTime) / animationTime);
    }

    public boolean isActive() {
        return (timeElapsed >= delayTime);
    }
}
