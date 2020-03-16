package demo;

import java.util.Random;

public class Dice {
	
	public int throwDie() {
		Random random = new Random();
		
		int dice1 = random.nextInt(6) + 1;
		int dice2 = random.nextInt(6) + 1;
		
		int face = dice1 + dice2;
		
		return face;
	}

}
