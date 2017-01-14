package com.xinyu.mwp.view.banner;

import android.os.Handler;
import android.os.Looper;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2015-10-06 11:48
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class AutoPlayer {

    public interface Playable {

        void playTo(int to);

        void playNext();

        void playPrevious();

        int getTotal();

        int getCurrent();
    }

    public enum PlayDirection {
        to_left, to_right
    }

    public enum PlayRecycleMode {
        repeat_from_start, play_back
    }

    private PlayDirection direction = PlayDirection.to_right;
    private PlayRecycleMode playRecycleMode = PlayRecycleMode.repeat_from_start;
    private int timeInterval = 5000;
    private Playable playable;

    private Runnable timerTask;

    private boolean skipNext = false;
    private int total;
    private boolean playing = false;
    private boolean paused = false;

    public AutoPlayer(Playable playable) {
        this.playable = playable;
    }

    public void play() {
        play(0, PlayDirection.to_right);
    }

    public void skipNext() {
        skipNext = true;
    }

    public void play(int start, PlayDirection direction) {
        if (playing) {
            playTo(start);
            return;
        }
        total = playable.getTotal();
        if (total <= 1) {
            return;
        }
        playing = true;
        playTo(start);

        final Handler handler = new Handler(Looper.myLooper());
        timerTask = new Runnable() {
            @Override
            public void run() {
                if (!paused) {
                    playNextFrame();
                }
                if (playing) {
                    handler.postDelayed(timerTask, timeInterval);
                }
            }
        };
        handler.postDelayed(timerTask, timeInterval);
    }

    public void play(int start) {
        play(start, PlayDirection.to_right);
    }

    public void stop() {
        if (!playing) {
            return;
        }

        playing = false;
    }

    public AutoPlayer setTimeInterval(int timeInterval) {
        this.timeInterval = timeInterval;
        return this;
    }

    public AutoPlayer setPlayRecycleMode(PlayRecycleMode playRecycleMode) {
        this.playRecycleMode = playRecycleMode;
        return this;
    }

    private void playNextFrame() {
        if (skipNext) {
            skipNext = false;
            return;
        }
        int current = playable.getCurrent();
        if (direction == PlayDirection.to_right) {
            if (current == total - 1) {
                if (playRecycleMode == PlayRecycleMode.play_back) {
                    direction = PlayDirection.to_left;
                    playNextFrame();
                } else {
                    playTo(0);
                }
            } else {
                playNext();
            }
        } else {
            if (current == 0) {
                if (playRecycleMode == PlayRecycleMode.play_back) {
                    direction = PlayDirection.to_right;
                    playNextFrame();
                } else {
                    playTo(total - 1);
                }
            } else {
                playPrevious();
            }
        }
    }

    public void pause() {
        paused = true;
    }

    public void resume() {
        paused = false;
    }

    private void playTo(int to) {
        playable.playTo(to);
    }

    private void playNext() {
        playable.playNext();
    }

    private void playPrevious() {
        playable.playPrevious();
    }
}
