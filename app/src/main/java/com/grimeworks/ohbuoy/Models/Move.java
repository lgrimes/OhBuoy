package com.grimeworks.ohbuoy.Models;

public class Move {
    private int modifier;
    private char direction;

    public void setModifier(int modifier) {
        this.modifier = modifier;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public char getDirection() {
        return direction;
    }

    public int getModifier() {
        return modifier;
    }
}
