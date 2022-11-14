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
    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok(views.html.index.render());
    }


 public Result roll() {
	 int newRoll = ThreadLocalRandom.current().nextInt(1,7);
        return ok("" + newRoll);
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
    
   
}
