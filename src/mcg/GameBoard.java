package mcg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameBoard extends JPanel {
	private ArrayList<Card> cards;
	private Card firstCard, secondCard;

	public GameBoard(Game game) {
		this.cards = new ArrayList<>();
        
		//Set up Game Board
		setLayout(new GridLayout(4, 4, 10, 10));

		ArrayList<Integer> cardValues = generateCardValues();
		for (int value : cardValues) {
			Card card = new Card(game.getLevel(), value, game);
			cards.add(card);
		}
		//

		shuffleCards();
        
		//Show cards to user at start
		for(Card card : cards) {
			card.showCard();
		}
    	
		Timer timer = new Timer(3000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for(Card card : cards) {
					card.hideCard();
				}				
			}
		});
    	
		timer.setRepeats(false);
		timer.start();
		//
	}

	private ArrayList<Integer> generateCardValues() {
		ArrayList<Integer> values = new ArrayList<>();
		for (int i = 1; i <= 8; i++) {
			values.add(i);
			values.add(i);
		}
		return values;
	}

	public void shuffleCards() {
		Thread shufflerThread = new Thread(new Shuffler(cards));
		shufflerThread.start();
		try {
			shufflerThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
		this.removeAll();
        
		for (Card card : cards) {
			add(card);
		}
	}

	public boolean selectCard(Card card) {

		if (firstCard == null) {
			firstCard = card;
			return false;
		} else if (secondCard == null && !isCardSelected(card)) {
			secondCard = card;
			return true;
		}
		return false;
	}

	public boolean isCardSelected(Card card) {
		return card == firstCard || card == secondCard;
	}
    
	public boolean bothCardsSelected() {
		return (firstCard != null && secondCard != null);
	}

	public boolean isMatchFound() {
		if (firstCard != null && secondCard != null) {
			if (firstCard.getCardValue() == secondCard.getCardValue()) {
				firstCard = null;
				secondCard = null;
				return true;
			}
		}
		return false;
	}

	public void hideMismatchedCards() {
		firstCard.hideCard();
		secondCard.hideCard();
		firstCard = null;
		secondCard = null;
	}

}
