package ru.academits.alekseev.minesweeper.game;

class Cell {
    private boolean isMine;
    private boolean isOpen;
    private boolean isFlag;

    Cell() {
    }

    boolean isMine() {
        return isMine;
    }

    void setMine(boolean mine) {
        isMine = mine;
    }

    boolean isOpen() {
        return isOpen;
    }

    void setOpen() {
        isOpen = true;
    }

    boolean isFlag() {
        return isFlag;
    }

    void setFlag(boolean flag) {
        isFlag = flag;
    }
}