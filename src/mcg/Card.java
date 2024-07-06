package mcg;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Card extends JButton {
	private int level;
	private int cardValue;
	private boolean isFlipped;
	private Game game;

	public Card(int level, int cardValue, Game game) {
		this.level = level;
		this.cardValue = cardValue;
		this.game = game;
		this.isFlipped = false;

		setPreferredSize(new Dimension(120, 120));
		setIcon(new ImageIcon(getCardBackImagePath()));
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.cardClicked(Card.this);
			}
		});
	}

	public int getCardValue() {
		return cardValue;
	}

	public void setCardValue(int cardValue) {
		this.cardValue = cardValue;
	}

	private String getCardBackImagePath() {
		switch (level) {
			case 1:
				return "src/images/Level1-InternetAssets/no_image.png";
			case 2:
				return "src/images/Level2-CyberSecurityAssets/no_image.png";
			case 3:
				return "src/images/Level3-GamingComputerAssets/no_image.png";
			default:
				return "";
		}
	}

	private String getCardImagePath() {
		switch (level) {
			case 1:
				return "src/images/Level1-InternetAssets/" + cardValue + ".png";
			case 2:
				return "src/images/Level2-CyberSecurityAssets/" + cardValue + ".png";
			case 3:
				return "src/images/Level3-GamingComputerAssets/" + cardValue + ".png";
			default:
				return "";
		}
	}

	public void showCard() {
    	
		setIcon(new ImageIcon(getCardImagePath()));

		isFlipped = true;
	}

	public void hideCard() {
		setIcon(new ImageIcon(getCardBackImagePath()));
		isFlipped = false;
	}

	public boolean isFlipped() {
		return isFlipped;
	}
}
