package controllers;

import play.mvc.*;
import play.libs.Json;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.util.concurrent.ThreadLocalRandom;

import javax.naming.spi.DirStateFactory.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

	ArrayList<Integer> 	dicelist = new ArrayList<>(); 
	private YatzeGame game;

	YatzeEngine tablescore;
	int moveCount = 0;
	
    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok(views.html.index.render());
    }

	public Result rollMany(int n) {
	/*	ObjectNode rollResult = Json.newObject();
		ArrayNode rolling = Json.newArray();
		for (int i = 0; i<n; i++) {
			 int newdie = ThreadLocalRandom.current().nextInt(1,7);
			 rolling.add(newdie);
		}
		rollResult.put("diceroller", rolling);
        return ok(rollResult);
        */
		dicelist.clear();
		for (int i = 0; i<n; i++) {
			 int newdie = ThreadLocalRandom.current().nextInt(1,7);
			 dicelist.add(newdie);
		}
		
        return ok(Json.toJson(dicelist));
    }

    public Result version() {
    	ObjectNode versionResult = Json.newObject();
    	versionResult.put("appname", "diceroller");
    	versionResult.put("version", "v0.2.0");
        return ok(versionResult);
    }
    
    public Result gotoyahtzee() {
		tablescore = new YatzeEngine();
 		game = new YatzeGame();
    	 return ok(views.html.version.render());
    }
	public Result showrules(){

	}
	public Result aboutgame(){
		
	}
    public Result roll() {

		game.rollDice();

           return ok(Json.toJson(game.diceValues()));
       }

	public Result count() {

		tablescore.countScore(game);

		return ok(Json.toJson(tablescore.scoreValues()));
	}

	public Result countfor0() {
		YatzeGame zerogame = new YatzeGame();
		tablescore.countScore(zerogame);

		return ok(Json.toJson(tablescore.scoreValues()));
	}

	public Result hold(int k) {
		game.keepDie(k);
		return ok();
	}
	public Result fix(int k) {
		moveCount++;
		tablescore.get(k).holdBoardElement();
		if (moveCount == 13) {
			game.stopGame();
		}
		else {
			game.renewGame();
		}

		return ok(Json.toJson(moveCount));
	}

	public Result totalscore() {
		int totalscore = tablescore.get(17).boardElementValue();

		return ok(Json.toJson(totalscore));
	}
}

 class Dice {
	
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
	

 class YatzeGame {
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
	
	public ArrayList<Integer> diceValues() {
		ArrayList<Integer> 	dicelist = new ArrayList<>();
		for (int i = 0; i < dice.length; i++) {
			dicelist.add(dieValue(i)); 
		}
		dicelist.add(3-rollCount);
		return dicelist;
			
	}
}


class YatzeEngine {
	public BoardElement[] scoreboard;
	private int upperscore = 0;
	private int lowerscore = 0;
	private int totalscore = 0;
	private int yahtzeechecker = 0;

	public YatzeEngine() {
		scoreboard = new BoardElement[18];
		for (int i = 0; i < scoreboard.length; i++) {
			scoreboard[i] = new BoardElement();
		}
	}

	public BoardElement get(int i) {
		return scoreboard[i];
	}

	public void countScore(YatzeGame gameDice) {
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
		ArrayList<Integer> 	scorelist = new ArrayList<>();
		for (int i = 0; i < scoreboard.length; i++) {
			scorelist.add(scoreboard[i].boardElementValue());
		}
		scorelist.add(yahtzeechecker);
		return scorelist;

	}
}
class BoardElement {

	private boolean fixed;

	private int element;


	public BoardElement (int x) {
		element = x;
	}

	public BoardElement() {
		element = 0;
	}

	void setElement (int x) {
		element = x;
	}

	int boardElementValue() {
		return element;
	}

	void holdBoardElement () {
		fixed = true;
	}

	boolean isOnHold() {
		return fixed;
	}


}



