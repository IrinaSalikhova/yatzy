package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import models.Leader;
import models.YatzeEngine;
import models.YatzeGame;
import play.Environment;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import javax.inject.Inject;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {
	@Inject
	private Environment environment;
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

	public Result leaderboard() {
		return ok(views.html.leaderboard.render());
	}

	public Result loadleaders() {
		ArrayList<Leader> existingLeaders = getResults();
		return ok(Json.toJson(existingLeaders));
	}
	public Result newleader(Http.Request request) throws IOException {
		JsonNode newleader = request.body().asJson();
		String n = newleader.get("name").asText("");
		int s = newleader.get("score").asInt(0);
		Leader thisLeader = new Leader(n, s);
		ArrayList<Leader> existingLeaders = getResults();
		existingLeaders.add(thisLeader);
		sortleaders(existingLeaders);

		List<JsonNode> listOfLeaders = new ArrayList<>();
		for (int i = 0; i<existingLeaders.size(); ++i) {
			JsonNode result = Json.toJson(existingLeaders.get(i));
			listOfLeaders.add(result);
		}
		ObjectNode finalresult = Json.newObject();
		finalresult.set("leaders", Json.toJson(listOfLeaders));

		FileWriter fileWriter = new FileWriter("public/json/leaders.json");
		fileWriter.write(finalresult.toString());
		fileWriter.flush();
		fileWriter.close();
		return ok(finalresult);
	}

		private ArrayList<Leader> getResults () {
			InputStream is = environment.resourceAsStream("public/json/leaders.json");
			JsonNode json = Json.parse(is);
			JsonNode results = json.get("leaders");
			ArrayList<Leader> existingLeaders = new ArrayList<>();
			for (int i = 0; i< results.size(); i++ ) {
				int r = results.get(i).get("rank").asInt(0);
				String n = results.get(i).get("name").asText("");
				int s = results.get(i).get("score").asInt(0);
				Leader thisLeader = new Leader(r, n, s);
				existingLeaders.add(thisLeader);
			}
			sortleaders(existingLeaders);

		return existingLeaders;
		}

	private void sortleaders(ArrayList<Leader> existingLeaders) {
		Collections.sort(existingLeaders);
		for (int i = 0; i< existingLeaders.size(); i++ ) {
			int r = i + 1;
			existingLeaders.get(i).setRank(r);
		};
		if (existingLeaders.size() == 11) {
			existingLeaders.remove(10);
		}
	}
}




