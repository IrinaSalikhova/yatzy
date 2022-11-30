package controllers;

import play.mvc.*;
import play.libs.Json;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import models.*;
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
		moveCount = 0;
 		if (game == null) {
			game = new YatzeGame();
			tablescore = new YatzeEngine();
		} else {
			game.renewGame();
			tablescore.renewBoard();
		};
    	 return ok(views.html.version.render());
    }

	public Result newGame() {
		moveCount = 0;
		tablescore.renewBoard();
		game.renewGame();
		return ok();
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




