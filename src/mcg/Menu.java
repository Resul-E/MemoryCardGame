package mcg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {

	private ImageIcon gameIcon = new ImageIcon("src/images/icons/gameIcon.jpg");
	private ImageIcon gameIconSmall;
	
	private ImageIcon instructionIcon = new ImageIcon("src/images/icons/aboutIcon.jpg");
	private ImageIcon instructionIconSmall;
	
	public Menu() {
		//Set up Frame
		setTitle("Memory Card Game");
		setSize(1024, 768);
		setResizable(false);
		setLocationRelativeTo(null);

		//Set up Icons
		gameIconSmall = new ImageIcon(gameIcon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
		setIconImage(gameIcon.getImage());

		instructionIconSmall = new ImageIcon(instructionIcon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));

		//Create Label for background
		JLabel menuBackgroundLabel = new JLabel(new ImageIcon("src/images/background.jpg"));
		menuBackgroundLabel.setLayout(null);
		//

		//Create Menu Title
		JLabel menuTitle = new JLabel("Memory Card Game");
		menuTitle.setFont(menuTitle.getFont().deriveFont(50.0f));
		menuTitle.setForeground(new Color(174, 198, 207));
		menuTitle.setBounds(300, 100, 600, 50);
		menuBackgroundLabel.add(menuTitle);
		//

		//Create Start Button and add to Label
		JButton startButton = new JButton("Start Game");
		startButton.setBounds(400, 250, 200, 50);
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startGame(1);
			}
		});
        
		menuBackgroundLabel.add(startButton);
		//

        
		//Create Select Level Button and add to Label
		JButton levelButton = new JButton("Select Level");
		levelButton.setBounds(400, 325, 200, 50);
		levelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectLevel();
			}
		});
        
		menuBackgroundLabel.add(levelButton);
		//
        
		//Create Instructions Button and add to Label
		JButton instructionsButton = new JButton("Instructions");
		instructionsButton.setBounds(400, 400, 200, 50);
		instructionsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showInstructions();
			}
		});
        
		menuBackgroundLabel.add(instructionsButton);
		//
        
		//Create Exit Button and add to Label
		JButton exitButton = new JButton("Exit");
		exitButton.setBounds(400, 475, 200, 50);
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
        
		menuBackgroundLabel.add(exitButton);
		//
        

		setContentPane(menuBackgroundLabel);
		setVisible(true);
	}

	private void startGame(int level) {
		new Game(level, 0);
		dispose();
	}

	private void selectLevel() {
    	
		String[] levels = {"Level 1", "Level 2", "Level 3"};
		String selectedLevel = (String) JOptionPane.showInputDialog(this, "Select Level", "Select Level", JOptionPane.QUESTION_MESSAGE, gameIconSmall, levels, levels[0]);
		int level = 1;
		if (selectedLevel != null) {
			if (selectedLevel.equals("Level 2")) {
				level = 2;
			} else if (selectedLevel.equals("Level 3")) {
				level = 3;
			}
			dispose();
			new Game(level, 0);
		}
	}

	private void showInstructions() {
		String instruction = "Instructions:\nThere are 3 levels in the game. It gets gradually harder!\nMatch all pairs of the cards to win!";
		JOptionPane.showMessageDialog(this, instruction, "Instructions", JOptionPane.INFORMATION_MESSAGE, instructionIconSmall);
	}
}
