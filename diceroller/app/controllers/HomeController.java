package controllers;

import play.mvc.*;
import play.libs.Json;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;


/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

	ArrayList<Integer> 	dicelist = new ArrayList<>(); 
	YatzeGame game;
	
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

 game = new YatzeGame();
    	 return ok(views.html.version.render());
    }
 
    public Result roll() {

     game.rollDice();
     game.diceValues().add(game.rollCount);

           return ok(Json.toJson(game.diceValues()));
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

