package ru.academits.alekseev.minesweeper.gui;

import ru.academits.alekseev.minesweeper.game.Game;
import ru.academits.alekseev.minesweeper.game.HighScoresTable;
import ru.academits.alekseev.minesweeper.game.ScoreRecord;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameView {
    private Game game;
    private GameFrame gameFrame;
    private final GameMouseClickListener mouseClickListener = new GameMouseClickListener();

    private int rowsCount;
    private int colsCount;

    private JButton[][] cells;
    private final HighScoresTable table = new HighScoresTable();

    private final String path = "Minesweeper/src/ru/academits/alekseev/minesweeper/resources/";

    private final ImageIcon bomb = new ImageIcon(path + "bomb.png");
    private final ImageIcon empty = new ImageIcon(path + "empty.png");
    private final ImageIcon one = new ImageIcon(path + "1.png");
    private final ImageIcon two = new ImageIcon(path + "2.png");
    private final ImageIcon three = new ImageIcon(path + "3.png");
    private final ImageIcon four = new ImageIcon(path + "4.png");
    private final ImageIcon five = new ImageIcon(path + "5.png");
    private final ImageIcon six = new ImageIcon(path + "6.png");
    private final ImageIcon seven = new ImageIcon(path + "7.png");
    private final ImageIcon eight = new ImageIcon(path + "8.png");
    private final ImageIcon flag = new ImageIcon(path + "flag.png");
    private final ImageIcon brokenFlag = new ImageIcon(path + "brokenFag.png");

    public GameView() {
        SwingUtilities.invokeLater(this::run);
    }

    private void run() {
        game = new Game();

        colsCount = game.getWidth();
        rowsCount = game.getHeight();
        gameFrame = new GameFrame(colsCount, rowsCount, game.getMinesCount(), this);

        cells = new JButton[rowsCount][colsCount];

        for (int i = 0; i < rowsCount; ++i) {
            for (int j = 0; j < colsCount; ++j) {
                JButton cell = new JButton();

                cell.setMaximumSize(new Dimension(30, 30));
                cell.addMouseListener(mouseClickListener);

                gameFrame.getGameField().add(cell);
                cells[i][j] = cell;
            }
        }
    }

    private class GameMouseClickListener extends MouseAdapter {
        private int down;
        private int across;
        private int prevDown;
        private int prevAcross;
        private int button;

        private void receiveCellCoordinates(MouseEvent e) {
            JButton cell = (JButton) e.getSource();
            down = 0;
            across = 0;

            for (int i = 0; i < rowsCount; ++i) {
                for (int j = 0; j < colsCount; ++j) {
                    if (cell == cells[i][j]) {
                        down = i;
                        across = j;
                        break;
                    }
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            receiveCellCoordinates(e);

            if (!SwingUtilities.isMiddleMouseButton(e)) {
                button = e.getButton();
            }

            if (SwingUtilities.isLeftMouseButton(e)) {
                if (prevDown == down && prevAcross == across) {
                    if (game.isOpened(down, across) && game.calcMines(down, across) > 0) {
                        findNeighboringFlags(down, across);
                    }
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            receiveCellCoordinates(e);

            MinesCountLabel minesCount = gameFrame.getMinesCountLabel();
            TimerLabel timerLabel = gameFrame.getTimerLabel();

            if (SwingUtilities.isRightMouseButton(e)) {
                if (!game.isOpened(down, across)) {
                    game.addFlag(down, across);
                    minesCount.setText(Integer.toString(game.getMinesCount() - game.getFlagsCount()));
                    update();
                }
            } else if (SwingUtilities.isLeftMouseButton(e)) {
                if (game.getClosedCellsCount() == rowsCount * colsCount) {
                    timerLabel.start();

                    if (game.isMine(down, across)) {
                        game.generateMines(1);
                        game.setMine(down, across, false);
                    }
                }

                if (game.isMine(down, across)) {
                    gameEnd();
                    youLose();
                } else {
                    game.openCell(down, across);
                    game.isVictory(down, across);

                    if (game.isWin()) {
                        gameEnd();
                        youWin();
                    }

                    update();
                }
            } else if (SwingUtilities.isMiddleMouseButton(e)) {
                if (game.isOpened(down, across) && game.calcMines(down, across) > 0) {
                    findNeighboringFlags(down, across);
                }
            }

            if (SwingUtilities.isLeftMouseButton(e)) {
                prevDown = down;
                prevAcross = across;
            }

            if (!SwingUtilities.isMiddleMouseButton(e)) {
                if (e.getButton() != button) {
                    if (game.isOpened(down, across) && game.calcMines(down, across) > 0) {
                        findNeighboringFlags(down, across);
                    }
                }
            }
        }
    }

    private void update() {
        Color color = new Color(195, 195, 195);

        for (int i = 0; i < rowsCount; ++i) {
            for (int j = 0; j < colsCount; ++j) {
                if (game.isOpened(i, j)) {
                    cells[i][j].setBackground(color);

                    if (game.isMine(i, j)) {
                        cells[i][j].setIcon(bomb);
                    } else {
                        switch (game.calcMines(i, j)) {
                            case 0:
                                cells[i][j].setIcon(empty);
                                break;
                            case 1:
                                cells[i][j].setIcon(one);
                                break;
                            case 2:
                                cells[i][j].setIcon(two);
                                break;
                            case 3:
                                cells[i][j].setIcon(three);
                                break;
                            case 4:
                                cells[i][j].setIcon(four);
                                break;
                            case 5:
                                cells[i][j].setIcon(five);
                                break;
                            case 6:
                                cells[i][j].setIcon(six);
                                break;
                            case 7:
                                cells[i][j].setIcon(seven);
                                break;
                            case 8:
                                cells[i][j].setIcon(eight);
                                break;
                        }
                    }
                } else {
                    if (game.isFlag(i, j)) {
                        cells[i][j].setIcon(flag);
                    } else {
                        cells[i][j].setIcon(null);
                        cells[i][j].setBackground(null);
                    }
                }
            }
        }
    }

    private void gameEnd() {
        int foundedMines = 0;
        for (int i = 0; i < rowsCount; ++i) {
            for (int j = 0; j < colsCount; ++j) {
                cells[i][j].removeMouseListener(mouseClickListener);

                if (game.isFlag(i, j)) {
                    if (game.isMine(i, j)) {
                        cells[i][j].setIcon(flag);
                        foundedMines++;
                    } else {
                        cells[i][j].setIcon(brokenFlag);
                    }
                } else if (game.isMine(i, j)) {
                    cells[i][j].setIcon(bomb);
                } else {
                    cells[i][j].setIcon(empty);
                }
            }
        }

        gameFrame.getMinesCountLabel().setText(Integer.toString(foundedMines));
        gameFrame.getTimerLabel().stop();
    }

    private void findNeighboringFlags(int down, int across) {
        int count = 0;

        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                if (!game.isOutOfBounds(i + down, j + across)) {
                    if (game.isMine(i + down, j + across)) {
                        if (game.isFlag(i + down, j + across)) {
                            count++;
                        }
                    } else if (game.isFlag(i + down, j + across)) {
                        return;
                    }
                }
            }
        }

        if (count == game.calcMines(down, across)) {
            game.openNeighboringFlagCells(down, across);
            game.isVictory(down, across);

            if (game.isWin()) {
                gameEnd();
                youWin();
            }

            update();
        }
    }

    private void youWin() {
        JOptionPane.showMessageDialog(gameFrame,
                "Вы победили!",
                "Победа!", JOptionPane.INFORMATION_MESSAGE);
        rememberRecord();
    }

    private void youLose() {
        JOptionPane.showMessageDialog(gameFrame,
                "Вы проиграли!",
                "Взрыв!", JOptionPane.INFORMATION_MESSAGE);
    }

    private void rememberRecord() {
        String userName;

        try {
            userName = JOptionPane.showInputDialog(gameFrame, "<html><h2>Введите ваше имя").trim();
        } catch (NullPointerException e) {
            return;
        }

        if (!userName.isEmpty()) {
            table.addRecord(new ScoreRecord(userName, gameFrame.getTimerLabel().getTime()));
        }
    }

    void startNewGame(int colsCount, int rowsCount, int minesCount) {
        SwingUtilities.invokeLater(() -> {
            game = new Game(rowsCount, colsCount, minesCount);

            this.colsCount = colsCount;
            this.rowsCount = rowsCount;

            gameFrame.getTimerLabel().startNewGame();
            gameFrame.getMinesCountLabel().setText(Integer.toString(game.getMinesCount()));

            gameFrame.setWidth(colsCount);
            gameFrame.setHeight(rowsCount);

            gameFrame.setNewGame(colsCount, rowsCount);

            cells = new JButton[rowsCount][colsCount];

            for (int i = 0; i < rowsCount; ++i) {
                for (int j = 0; j < colsCount; ++j) {
                    JButton cell = new JButton();
                    cell.setMaximumSize(new Dimension(30, 30));
                    cell.addMouseListener(mouseClickListener);
                    gameFrame.getGameField().add(cell);
                    cells[i][j] = cell;
                }
            }

            update();
        });
    }
}