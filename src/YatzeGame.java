
public class YatzeGame {
	public Dice[] dice;
	
	public int rollCount = 0;
	
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
	
	public String diceValues() {
		String values = "";
		for (int i = 0; i < dice.length; i++) {
			values = values + (i+1) + ": " + dieValue(i) + " " + isKept(i) + "\n"; 
		}
		return values;
			
	}
}
/*
In YatzyGame.java, implement a current state of a game.

A yatze game comprises of a turn, which includes

Which roll you are on (0, 1, 2, or 3)
Current value on each of the 5 dice
Keep / re-roll state of each dice
The YatzyGame should focus on tracking the state of the game without knowing much about the rules, that comes next!

Commit these changes and push to GitHub.
*/