package mcg;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class Game extends JFrame {
	
	private int level;
	private int points;
	private int triesLeft;
	private int matchedCards;
	private JLabel headerLabel;
	private GameBoard gameBoard;
	private HighScoreManager highScoreManager;
	
	private ImageIcon aboutIcon = new ImageIcon("src/images/icons/aboutIcon.jpg");
	private ImageIcon aboutIconSmall;
	
	private ImageIcon highscoreIcon = new ImageIcon("src/images/icons/highscoreIcon.png");
	private ImageIcon highscoreIconSmall;
	
	private ImageIcon sadIcon = new ImageIcon("src/images/icons/sadIcon.png");
	private ImageIcon sadIconSmall;
	
	public Game(int level, int points) {
		this.level = level;
		this.points = points;
		this.triesLeft = getInitialTries(level);
		this.matchedCards = 0;
		this.highScoreManager = new HighScoreManager();
		
		//Set up Icons
		aboutIconSmall = new ImageIcon(aboutIcon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
		
		highscoreIconSmall = new ImageIcon(highscoreIcon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
		
		sadIconSmall = new ImageIcon(sadIcon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
		
		//Set up Frame
		setTitle("Memory Card Game");
		setSize(530, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);

		//Set up Menu Bar
		JMenuBar menuBar = new JMenuBar();

		//Set up Menu Bar -> Game
		JMenu gameMenu = new JMenu("Game");

		//Set up Restart and add to Game
		JMenuItem itemRestart = new JMenuItem("Restart");
		itemRestart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				restartGame();
			}
		});
		gameMenu.add(itemRestart);
		//

		//Set up Highscore and add to Game
		JMenuItem itemHighscores = new JMenuItem("High Scores");
		itemHighscores.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showHighScores();
			}
		});
		gameMenu.add(itemHighscores);
		//

		//Add Game to Menu Bar
		menuBar.add(gameMenu);

		//Set up Menu Bar -> About
		JMenu aboutMenu = new JMenu("About");

		//Set up About Game and add to About
		JMenuItem itemAboutGame = new JMenuItem("About the Game");
		itemAboutGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showAboutGame();
			}
		});
		aboutMenu.add(itemAboutGame);
		//

		//Set up About Developer and add to About
		JMenuItem itemAboutDev = new JMenuItem("About the Developer");
		itemAboutDev.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showAboutDeveloper();
			}
		});
		aboutMenu.add(itemAboutDev);
		//

		//Add About to Menu Bar
		menuBar.add(aboutMenu);
		
		JMenu exitMenu = new JMenu("Exit");

		//Set up Exit Game and add to Menu
		JMenuItem itemExit = new JMenuItem("Exit to Desktop");
		itemExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				System.exit(0);
			}
		});
		//
		
		//Set up Exit Game and add to Menu
		JMenuItem itemExitToMenu = new JMenuItem("Exit to Menu");
		itemExitToMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Menu();
			}
		});
		//

		//Add Exit Items to Exit Menu
		exitMenu.add(itemExitToMenu);
		exitMenu.add(itemExit);

		//Add Exit to Menu Bar
		menuBar.add(exitMenu);
		
		setJMenuBar(menuBar);

		//Set up Header Label
		headerLabel = new JLabel("LEVEL " + level + "  Tries left: " + triesLeft, JLabel.CENTER);
		headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
		headerLabel.setOpaque(true);
		headerLabel.setBackground(getColor());
		headerLabel.setForeground(Color.WHITE);
		headerLabel.setPreferredSize(new Dimension(530, 50));
		add(headerLabel, BorderLayout.NORTH);

		//Set up GameBoard
		gameBoard = new GameBoard(this);
		add(gameBoard, BorderLayout.CENTER);
	}

	private Color getColor() {
		switch (level) {
		case 1:
			return new Color(65,105,225);
		case 2:
			return new Color(255,0,0);
		case 3:
			return new Color(148,0,211);
		default:
			return new Color(65,105,225);
		}
	}

	public void cardClicked(Card card) {
		if (gameBoard.bothCardsSelected() || gameBoard.isCardSelected(card) || card.isFlipped()) return; 
		
		card.showCard();

		boolean flag = gameBoard.selectCard(card);
		if (flag) checkMatch();
	}

	private void checkMatch() {
		boolean next = false;

		if (gameBoard.isMatchFound()) {
			matchedCards++;
			calculatePoints(true);
			showFeedback(true);

			if (matchedCards == 8) {
				levelFinished();
				next = true;
			}
		} else {
			Timer hideTimer = new Timer(400, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					gameBoard.hideMismatchedCards();
					showFeedback(false);
					

				}
			});
        
			hideTimer.setRepeats(false);
			hideTimer.start();

			calculatePoints(false);
			if (level == 3) {
				gameBoard.shuffleCards();
			}
			
            
		}
        
		triesLeft--;
		headerLabel.setText("LEVEL " + level + "  Tries left: " + triesLeft);
		if (triesLeft == 0 && !next) {
			outOfTries();
		}
        
	}

	private void showFeedback(boolean b) {
		String path;
		
		if (b) {path = "src/images/icons/correctIcon.png";}
		else {path = "src/images/icons/wrongIcon.png";}
		
		JDialog feedbackDialog = new JDialog(this, true);
		feedbackDialog.setUndecorated(true);
		feedbackDialog.setLayout(new BorderLayout());
		
		feedbackDialog.setBackground(new Color(0, 0, 0, 0));
		
		JLabel feedbackLabel = new JLabel(new ImageIcon(path));
		feedbackDialog.add(feedbackLabel, BorderLayout.CENTER);
		feedbackDialog.pack();
		
		feedbackDialog.setLocationRelativeTo(this);
		
		
		Timer feedbackTimer = new Timer(400, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				feedbackDialog.setVisible(false);
				feedbackDialog.dispose();

			}
		});
		
		feedbackTimer.setRepeats(false);
		feedbackTimer.start();
		feedbackDialog.setVisible(true);
		
	}

	private void levelFinished() {
		String youWon = "Congrats! You won!";
		int response = JOptionPane.showConfirmDialog(this, youWon, "You Won!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
        
		if(response == JOptionPane.OK_OPTION) {
			nextLevel();
		}
		
		
	}

	private void calculatePoints(boolean matched) {
		if (matched) {
			switch (level) {
				case 1:
					points += 5;
				case 2:
					points += 4;
				case 3:
					points += 3;
				default:
					points += 0;
			}
		} else {
			switch (level) {
				case 1:
					points -= 1;
				case 2:
					points -= 2;
				case 3:
					points -= 3;
				default:
					points -= 0;
			}
		}
	}

	private void nextLevel() {
		if (level < 3) {
			new Game(level + 1, points);
		} else {
			endGame();
		}
		dispose();
	}

	private void outOfTries() {
		JOptionPane.showMessageDialog(this, "You lost! Try again.", "You Lost!", JOptionPane.INFORMATION_MESSAGE);
		restartGame();
	}

	private void restartGame() {
		new Game(1, 0);
		dispose();
	}

	private void endGame() {
		String username = JOptionPane.showInputDialog(this, "Enter your username:");
		highScoreManager.saveHighScore(username, points);
		JOptionPane.showMessageDialog(this, "Your score: " + points, "Game Over", JOptionPane.INFORMATION_MESSAGE);
		new Menu();
		dispose();
	}

	private void showHighScores() {
		HashMap<String, Integer> highScores = highScoreManager.readHighScores();
		if (highScores.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Seems like nobody played this game before :(", "High Scores", JOptionPane.INFORMATION_MESSAGE, sadIconSmall);
		} else {
			StringBuilder message = new StringBuilder("High Scores:\n\n");
			highScores.entrySet().stream()
					.sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
					.forEach(entry -> message.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n"));
			JOptionPane.showMessageDialog(this, message.toString(), "High Scores", JOptionPane.INFORMATION_MESSAGE, highscoreIconSmall);
		}
	}

	private void showAboutGame() {
		String gameInfo = "Memory Card Game\n\n"
				+ "Match all the pairs of cards to win the game.\n"
				+ "Each level has a limited number of tries.\n"
				+ "Try to find all the pairs before you run out of tries!";
		JOptionPane.showMessageDialog(this, gameInfo, "About the Game", JOptionPane.INFORMATION_MESSAGE, aboutIconSmall);
	}

	private void showAboutDeveloper() {
		String developerInfo = "Made by Resul Eryurt\n20220702108";
		JOptionPane.showMessageDialog(this, developerInfo, "About the Developer", JOptionPane.INFORMATION_MESSAGE, aboutIconSmall);
	}

	private int getInitialTries(int level) {
		switch (level) {
			case 1:
				return 18;
			case 2:
				return 15;
			case 3:
				return 12;
			default:
				return 18;
		}
	}
    
	public int getLevel() {
		return level;
	}
}
