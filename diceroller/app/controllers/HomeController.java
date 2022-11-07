package controllers;

import play.mvc.*;
import javax.inject.Inject;
import play.libs.Json;
import com.fasterxml.jackson.databind.JsonNode;
inport com.fasterxml.jackson.databind.node.ArrayNode;




/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

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
        return ok(views.html.index.render());
    }
	
	public Result rollMany(int n) {
        return ok(views.html.index.render());
    }

    VersionClass result;
    
    public Result version() {
		result.setappname("diceroller");
		result.setversion("v0.1.0");
        return ok(Json.toJson(result));
    }
    
    class VersionClass {
    	String appname;
    	String version;
    	
    	public void setappname(String name) {
    		appname = name;
    	}
    	
    	public void setversion (String ver) {
    		version = ver;
    	}
    	
    	public String getappname() {
    		return appname;
    	}
    	
    	public String getversion() {
    		return version;
    	}
    }

}
