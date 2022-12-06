package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class YatzeEngine {
    public BoardElement[] scoreboard;
    private int upperscore = 0;
    private int lowerscore = 0;
    private int totalscore = 0;
    private int yahtzeechecker = 0;
    public YatzeEngine() {
        //Initialize  the scoreboard array
        scoreboard = new BoardElement[18];
        for (int i = 0; i < scoreboard.length; i++) {
            scoreboard[i] = new BoardElement();
        }
    }

    public BoardElement get(int i) {
        return scoreboard[i];
    }

    public void countScore(YatzeGame gameDice) {
        // set all score values to 0
        upperscore = 0;
        lowerscore = 0;
        totalscore = 0;
        yahtzeechecker = 0;

        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i<gameDice.dice.length; i++) {
            int valueAsKey = gameDice.dieValue(i);

            if (map.containsKey(valueAsKey)) {
                int k = map.get(valueAsKey) + 1;
                map.replace(valueAsKey, k);
            }
            else {
                map.put(valueAsKey, 1);
            }
        }

        // count upperboard
        for (int i = 0; i < 6; i++) {
            if (!scoreboard[i].isOnHold()) {
                scoreboard[i].setElement(map.getOrDefault(i+1, 0)*(i+1));
            }
            else {
                upperscore += scoreboard[i].boardElementValue();
            }
        }
        if (upperscore >= 63) {
            scoreboard[6].setElement(35);
        }
        scoreboard[7].setElement(upperscore + scoreboard[6].boardElementValue());

        // count lowerboard
        // 3 of a kind
        if 	(!scoreboard[8].isOnHold()) {
            if (map.containsValue(3) || map.containsValue(4) || map.containsValue(5)) {
                scoreboard[8].setElement(countAll(gameDice));
            }
            else {
                scoreboard[8].setElement(0);
            }
        }
        else {
            lowerscore += scoreboard[8].boardElementValue();
        }

        // 4 of a kind
        if 	(!scoreboard[9].isOnHold()) {
            if (map.containsValue(4) || map.containsValue(5)) {
                scoreboard[9].setElement(countAll(gameDice));
            }
            else {
                scoreboard[9].setElement(0);
            }
        }
        else {
            lowerscore += scoreboard[9].boardElementValue();
        }

        // full house
        if 	(!scoreboard[10].isOnHold()) {
            if (map.keySet().size() == 2 && map.containsValue(3)) {
                scoreboard[10].setElement(25);
            }
            else {
                scoreboard[10].setElement(0);
            }
        }
        else {
            lowerscore += scoreboard[10].boardElementValue();
        }

        // small straight
        if 	(!scoreboard[11].isOnHold()) {
            if ((map.containsKey(1) && map.containsKey(2) && map.containsKey(3) && map.containsKey(4)) ||
                    (map.containsKey(4) && map.containsKey(5) && map.containsKey(2) && map.containsKey(3)) ||
                    (map.containsKey(4) && map.containsKey(5) && map.containsKey(6) && map.containsKey(3))) {
                scoreboard[11].setElement(30);
            }
            else {
                scoreboard[11].setElement(0);
            }
        }
        else {
            lowerscore += scoreboard[11].boardElementValue();
        }

        // large straight
        if 	(!scoreboard[12].isOnHold()) {
            if ((map.containsKey(5) && map.containsKey(6) && map.containsKey(2) && map.containsKey(3) && map.containsKey(4)) ||
                    (map.containsKey(4) && map.containsKey(5) && map.containsKey(2) && map.containsKey(3) && map.containsKey(1))) {
                scoreboard[12].setElement(40);
            }
            else {
                scoreboard[12].setElement(0);
            }
        }
        else {
            lowerscore += scoreboard[12].boardElementValue();
        }

        // yahtzee
        if 	(!scoreboard[13].isOnHold()) {
            if (map.keySet().size() == 1 && !map.containsKey(0)) {
                scoreboard[13].setElement(50);
                yahtzeechecker = 1;
            }
            else {
                scoreboard[13].setElement(0);
            }
        }
        else {
            lowerscore += scoreboard[13].boardElementValue();
        }

        // chance
        if 	(!scoreboard[14].isOnHold()) {
            scoreboard[14].setElement(countAll(gameDice));;
        }
        else {
            lowerscore += scoreboard[14].boardElementValue();
        }


        // yahtzee bonus
        if 	(scoreboard[13].isOnHold()) {
            if (map.keySet().size() == 1 && !map.containsKey(0)) {
                int yahtzeeBonus = scoreboard[15].boardElementValue() + 100;
                scoreboard[15].setElement(yahtzeeBonus);
            }
        }

        scoreboard[16].setElement(lowerscore + scoreboard[15].boardElementValue());

        totalscore = scoreboard[7].boardElementValue() + scoreboard[16].boardElementValue();
        scoreboard[17].setElement(totalscore);

    }

    public void renewBoard() {
        // resets values of scoreboard
        for (int i = 0; i < scoreboard.length; i++) {
            scoreboard[i].setElement(0);
        }
    }

    private int countAll (YatzeGame gameDice) {
        int allCounted = 0;
        for (int i = 0; i<gameDice.dice.length; i++) {
            allCounted += gameDice.dieValue(i);
        }
        return allCounted;
    }

    public String toString() {
        String board = "";
        for (int i = 0; i < scoreboard.length; i++) {
            board += i +  ": " + scoreboard[i].boardElementValue() + "\n";
        }
        return board;
    }

    public ArrayList<Integer> scoreValues() {
        // pulls up all score values form the arrayList
        ArrayList<Integer> 	scorelist = new ArrayList<>();
        for (int i = 0; i < scoreboard.length; i++) {
            scorelist.add(scoreboard[i].boardElementValue());
        }
        scorelist.add(yahtzeechecker);
        return scorelist;

    }
}
