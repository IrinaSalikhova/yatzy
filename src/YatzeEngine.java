import java.util.HashMap;
import java.util.Map;

public class YatzeEngine {

/*
In YatzyEngine.java implement a set of helper function to capture the scoring 
of the game. This includes:

The score of a specific turn. 
The input should be a game and a score box 
and the output should be a score for that box 
(e.g. the sum of ones for the ones box.

Updating the overall score of the game. 
This should include the calculation of the bonus. 
The input should be a game 
and the output should that 
the provided game's overall score and bonus are properly calculated.
Commit these changes and push to GitHub.
*/
	
		public BoardElement[] scoreboard;
		private int upperscore = 0;
		private int lowerscore = 0;
		private int totalscore = 0;
		
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
	
