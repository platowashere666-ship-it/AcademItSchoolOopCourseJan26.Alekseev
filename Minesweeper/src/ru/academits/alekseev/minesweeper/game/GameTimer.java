package ru.academits.alekseev.minesweeper.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.*;

public class GameTimer implements ActionListener {
    private final Timer timer;
    private final Date date = new Date();
    private float gameTime;

    GameTimer() {
        timer = new Timer(100, this);
        gameTime = 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gameTime = new Date().getTime() - date.getTime();
    }

    float getGameTime() {
        return gameTime;
    }

    void start() {
        timer.start();
    }

    void stop() {
        timer.stop();
    }

    public String toString() {
        final int MINUTES = (int) gameTime / 1000 / 60;
        final int SECONDS = (int) gameTime / 1000 % 60;
        final int MILLISECONDS = (int) gameTime % 1000 / 10;
        return (String.format("%02d:%02d.%02d", MINUTES, SECONDS, MILLISECONDS));
    }
}