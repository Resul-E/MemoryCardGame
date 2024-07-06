package mcg;

import java.util.ArrayList;
import java.util.Collections;

public class Shuffler implements Runnable {
	private ArrayList<Card> cards;

	public Shuffler(ArrayList<Card> cards) {
		this.cards = cards;
	}

	@Override
	public void run() {
		Collections.shuffle(cards);
	}
}
