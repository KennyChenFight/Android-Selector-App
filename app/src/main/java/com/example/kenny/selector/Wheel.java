package com.example.kenny.selector;

public class Wheel extends Thread {

    interface WheelListener {
        void newImage(int img);
    }

    private static int[] imgs = {R.drawable.number1, R.drawable.number2, R.drawable.number3, R.drawable.number4,
            R.drawable.number5, R.drawable.number6, R.drawable.number7, R.drawable.number8, R.drawable.number9, R.drawable.number0};

    public int currentIndex;
    private WheelListener wheelListener;
    private long frameDuration;
    private long startIn;
    private boolean isStarted;

    public Wheel(WheelListener wheelListener, long frameDuration, long startIn) {
        this.wheelListener = wheelListener;
        this.frameDuration = frameDuration;
        this.startIn = startIn;
        currentIndex = 0;
        isStarted = true;
    }

    public void nextImg() {
        currentIndex++;

        if (currentIndex == imgs.length) {
            currentIndex = 0;
        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(startIn);
        } catch (InterruptedException e) {
        }


        while (isStarted) {
            try {
                Thread.sleep(frameDuration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            nextImg();
            if (wheelListener != null) {
                wheelListener.newImage(imgs[currentIndex]);
            }
        }
    }
    public void stopWheel(){
        isStarted = false;
    }

}

