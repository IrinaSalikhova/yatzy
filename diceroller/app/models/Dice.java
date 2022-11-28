package models;

import java.util.concurrent.ThreadLocalRandom;

public class Dice {

    private int die;

    public Dice() {
        die = 0;
    }

    public int dieValue() {
        return die;
    }

    public void roll () {
        die = ThreadLocalRandom.current().nextInt(1, 7);
    }
}

