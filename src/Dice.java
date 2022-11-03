import java.util.concurrent.ThreadLocalRandom;

public class Dice {
		
		private int die;
		private boolean fixed = false;
		
		
		public Dice() {
			die = 0;
		}
		
		public void setDie (int x) {
			die = x;
		}

		public int dieValue() {
			return die;
		}
		
		 void holdDie () {
			if (fixed) {fixed = false;}
			else {fixed = true;}
			
		}
			
		boolean isOnHold() {
			return fixed;
			}
		
		void roll () {
			if (!fixed) {
				 die = ThreadLocalRandom.current().nextInt(1, 7);
			} 
		}

		}
		


