package ai.main;

import java.awt.Frame;

import javax.swing.JFrame;

import botsystem.Bot;
import botsystem.Physics;
import botsystem.Truster;
import canvas.BotScreen;
import utils.Runnable;
import utils.SaveUtils;
import utils.Vector2;

public class Main {
	
	private final static String TITLE = "AiLab:RE";
	private final static Vector2 SCREEN = new Vector2(1000, 1000);
	
	
	public static void main(String args[]) {
		SaveUtils.load();
		Bot bot = new Bot(new Vector2(550, 500), new Vector2(0), 1);
		
		bot.addTruster(new Truster(new Vector2(50, 0), new Vector2(0,1), 1,5));
		bot.addTruster(new Truster(new Vector2(-50, 0), new Vector2(0,1), 1,5));

		BotScreen bs = new BotScreen(bot);
		JFrame f = new JFrame(TITLE);
		f.add(bs);
		f.setSize((int) SCREEN.getX(), (int) SCREEN.getY());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(true);
		f.setVisible(true);
		f.setEnabled(true);
		f.pack();
		
		
		new Runnable(0, 50) {
			Vector2 lastPos = bot.getPos();
			@Override
			public void run() {
				double[] input = new double[] {Vector2.SignedAngle(bot.getDir(), new Vector2(0,1))};
				System.out.println("RICK" +input.length);
				double[] res = SaveUtils.getNeuralNet().doSth(input);
				bot.getTruster(0).setCurrentTrust(res[0]);
				bot.getTruster(1).setCurrentTrust(res[1]);
				
				
				
				bs.paint(bs.getGraphics());
				Physics.calcPhysics(bot);
				
				if (bot.getPos().distance(lastPos)<1) {
					
				}
				if (Math.abs(bot.getPos().getY())>900 || Math.abs(bot.getPos().getX())>900) {
					
				}
				
				lastPos = bot.getPos();
			}
		};
	}

}
