package ai.main;

import java.awt.Frame;

import javax.swing.JFrame;

import botsystem.Bot;
import canvas.BotScreen;
import utils.SaveUtils;
import utils.Vector2;

public class Main {
	
	private final static String TITLE = "AiLab:RE";
	private final static Vector2 SCREEN = new Vector2(1000, 1000);
	
	
	public static void main(String args[]) {
		SaveUtils.load();
		Bot bot = new Bot();
		
		
		JFrame f = new JFrame(TITLE);
		f.add(new BotScreen(bot));
		f.setSize((int) SCREEN.getX(), (int) SCREEN.getY());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(true);
		f.setVisible(true);
		f.setEnabled(true);
		f.pack();
	}

}
