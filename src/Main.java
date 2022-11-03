
public class Main {

	public static void main(String[] args) {

System.out.println("Yatze");

YatzeGame game = new YatzeGame();

game.rollDice();

game.keepDie(0);
game.keepDie(3);
System.out.println(game.diceValues());
	

	game.rollDice();

	game.keepDie(0);
	game.keepDie(2);
	System.out.println(game.diceValues());
		


game.rollDice();

System.out.println(game.diceValues());
	

	game.rollDice();

	System.out.println(game.diceValues());
		}

}
