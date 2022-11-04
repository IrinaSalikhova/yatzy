
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
