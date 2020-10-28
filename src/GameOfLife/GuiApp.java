package GameOfLife;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class GuiApp extends JFrame implements ActionListener {
	
	//Some variables
	private JButton startButton, stopButton, previousBoard, clearBoard;
	private JMenuBar menuBar;
	private JMenu settings, colorSettings, musicSettings, presetSettings;
	private JMenuItem colorWhite, colorRed, colorBlue, colorGreen, musicOff, music1, music2, music3;
	private JMenuItem preset1, preset2, preset3;
	//private JTextArea width;
	private static Clip clip;
	board boardGUI;
	
	
	
	//Main function. Calls function to create the GUI
	public static void main(String[] args) {
		createAndShowGUI();
	}
	
	//Creating some settings for the GUI.
	public GuiApp() {
		super("Game Of Life");
		setResizable(false);
	}
	
	//Adds all of the components to the GUI.
	public void addComponents(Container window) {
		
		
		// Configuring the row layouts
		boardGUI = new board();
		JPanel buttons1 = new JPanel(new GridLayout(0,5));
		
		//Setting up the settings menu bar
		menuBar = new JMenuBar();
		menuBar.setForeground(Color.RED);
		settings = new JMenu("Settings");
		colorSettings = new JMenu("Color Settings");
		musicSettings = new JMenu("Music Settings");
		presetSettings = new JMenu("Presets");
		//Now the options for each menu on the bar
		colorRed = new JMenuItem("Red");
		colorRed.addActionListener(this);
		colorWhite = new JMenuItem("White");
		colorWhite.addActionListener(this);
		colorBlue = new JMenuItem("Blue");
		colorBlue.addActionListener(this);
		colorGreen = new JMenuItem("Green");
		colorGreen.addActionListener(this);
		musicOff = new JMenuItem("Music: Off");
		musicOff.addActionListener(this);
		music1 = new JMenuItem("Music Beat 1");
		music1.addActionListener(this);
		music2 = new JMenuItem("Music Beat 2");
		music2.addActionListener(this);
		music3 = new JMenuItem("Music Beat 3");
		music3.addActionListener(this);
		preset1 = new JMenuItem("Random Fill");
		preset1.addActionListener(this);
		preset2 = new JMenuItem("Exploder");
		preset2.addActionListener(this);
		preset3 = new JMenuItem("Glider");
		preset3.addActionListener(this);
		//Adding each menu item to the menus
		colorSettings.add(colorWhite);
		colorSettings.add(colorRed);
		colorSettings.add(colorBlue);
		colorSettings.add(colorGreen);
		musicSettings.add(musicOff);
		musicSettings.add(music1);
		musicSettings.add(music2);
		musicSettings.add(music3);
		presetSettings.add(preset1);
		presetSettings.add(preset2);
		presetSettings.add(preset3);
		
		
		//Adding the major components to the GUI
		//First the settings menus
		settings.add(colorSettings);
		settings.add(musicSettings);
		settings.add(presetSettings);
		menuBar.add(settings);
		// Setting up the first row of buttons and settings
		startButton = new JButton("Start Game");
		startButton.addActionListener(this);
		stopButton = new JButton("Stop Game");
		stopButton.addActionListener(this);
		previousBoard = new JButton("Previous Board");
		previousBoard.addActionListener(this);
		clearBoard = new JButton("Clear Board");
		clearBoard.addActionListener(this);
		// Add the panel of buttons to the frame
		buttons1.add(startButton);
		buttons1.add(stopButton);
		buttons1.add(menuBar);
		buttons1.add(new JLabel(""));
		buttons1.add(previousBoard);
		buttons1.add(new JLabel(""));
		buttons1.add(new JLabel(""));
		buttons1.add(new JLabel(""));
		buttons1.add(new JLabel(""));
		buttons1.add(clearBoard);
		buttons1.add(new JLabel(""));
		buttons1.add(new JLabel(""));
		
		//Add panels to the frame
		window.add(boardGUI, BorderLayout.NORTH);
		boardGUI.setPreferredSize(new Dimension(750,800));
		window.add(buttons1, BorderLayout.CENTER);
		
	}
	
	//Create, pack, show the GUI
	private static void createAndShowGUI() {
		GuiApp GUI = new GuiApp();
		GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GUI.addComponents(GUI.getContentPane());
		GUI.pack();
		GUI.setVisible(true);
	}
	
	//Adding actions for each button on the GUI.
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startButton) {
			boardGUI.startGame();
		}
		else if (e.getSource() == stopButton) {
			boardGUI.stopGame();
		}
		else if (e.getSource() == previousBoard) {
			boardGUI.loadPreviousBoard();
		}
		else if (e.getSource() == clearBoard) {
			boardGUI.clearBoard();
		}
		else if (e.getSource() == colorWhite) {
			boardGUI.setColor(Color.white);
		}
		else if (e.getSource() == colorRed) {
			boardGUI.setColor(Color.red);
		}
		else if (e.getSource() == colorBlue) {
			boardGUI.setColor(Color.blue);
		}
		else if (e.getSource() == colorGreen) {
			boardGUI.setColor(Color.green);
		}
		else if (e.getSource() == musicOff) {
			if (clip != null) {
				clip.stop();
			}
		}
		else if (e.getSource() == music1) {
			if (clip != null) {
				clip.stop();
			}
			loadMusic("src/Music/music1.wav");
		}
		else if (e.getSource() == music2) {
			if (clip != null) {
				clip.stop();
			}
			loadMusic("src/Music/music2.wav");
		}
		else if (e.getSource() == music3) {
			if (clip != null) {
				clip.stop();
			}
			loadMusic("src/Music/music3.wav");
		}
		else if (e.getSource() == preset1) {
			boardGUI.getPreset(0);
		}
		else if (e.getSource() == preset2) {
			boardGUI.getPreset(1);
		}
		else if (e.getSource() == preset3) {
			boardGUI.getPreset(2);
		}
	
	}
	
	//Credit to Kassidy's boyfriend for the great music
	//This loads and plays the music.
	public static void loadMusic(String filepath) {
		try {
			System.out.println("Loaded clip!");
			File musicPath = new File(filepath);
			if (musicPath.exists()) {
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
				clip = AudioSystem.getClip();
				clip.open(audioInput);
				clip.start();
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			}
			else {
				System.out.println("Could not file music file.");
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
}