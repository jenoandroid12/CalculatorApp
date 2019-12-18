package com.example.calculatorapp;

class KeyJDO {
    String text;
    int color;
    int backGround;

    public KeyJDO(String text, int color, int backGround) {
        this.text = text;
        this.color = color;
        this.backGround = backGround;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getBackGround() {
        return backGround;
    }

    public void setBackGround(int backGround) {
        this.backGround = backGround;
    }
}
