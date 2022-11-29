package models;

import java.util.ArrayList;

public class YatzeGame {
    public Dice[] dice;

    public static int rollCount = 0;

    private boolean[] diceState;

    public YatzeGame() {
        dice = new Dice[5];
        diceState = new boolean[5];
        for (int i = 0; i < dice.length; i++) {
            dice[i] = new Dice();
            diceState[i] = false;
        }

    }

    public void renewGame() {
        this.rollCount = 0;
        for (int i = 0; i < dice.length; i++) {
            if (diceState[i]) {
                diceState[i] = !diceState[i];
            }
        }
    }

    public void stopGame() {
        this.rollCount = 3;
        for (int i = 0; i < dice.length; i++) {
            if (diceState[i]) {
                diceState[i] = !diceState[i];
            }
        }
    }
    public Dice[] rollDice() {
        if (rollCount < 3) {
            for (int i = 0; i < dice.length; i++) {
                if (!diceState[i]) {
                    dice[i].roll();
                }
            }
            rollCount ++;
        }
        return dice;
    }

    public void keepDie(int i) {
        diceState[i] = !diceState[i];
    }

    public boolean isKept (int i) {
        return diceState[i];
    }

    public int dieValue (int i) {
        return dice[i].dieValue();
    }
// update the arrayList on die values
    public ArrayList<Integer> diceValues() {
        ArrayList<Integer> 	dicelist = new ArrayList<>();
        for (int i = 0; i < dice.length; i++) {
            dicelist.add(dieValue(i));
        }
        dicelist.add(3-rollCount);
        return dicelist;

    }
}