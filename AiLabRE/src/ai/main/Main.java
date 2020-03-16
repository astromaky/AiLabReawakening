package ai.main;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;
import java.util.Random;

import javax.swing.JFrame;

import botsystem.Bot;
import botsystem.Physics;
import botsystem.Truster;
import canvas.BotScreen;
import utils.KnoContainer;
import utils.Runnable;
import utils.SaveUtils;
import utils.Trainer;
import utils.TrainerGiovanna;
import utils.TrainerHeinz;
import utils.TrainerJoe;
import utils.TrainerJojo;
import utils.Vector2;

public class Main {
	
	private final static String TITLE = "AiLab:RE";
	private final static Vector2 SCREEN = new Vector2(1000, 1000);
	public static ArrayList<KnoContainer> kno = new ArrayList<KnoContainer>();
	public static ArrayList<KnoContainer> train = new ArrayList<KnoContainer>();
	
	public static void main(String args[]) {
		SaveUtils.load();
		Bot bot = new Bot(new Vector2(500, 500), new Vector2(0), 1);
		
		bot.addTruster(new Truster(new Vector2(50, 0), new Vector2(0,1), 2,5));
		bot.addTruster(new Truster(new Vector2(-50, 0), new Vector2(0,1), 2,5));
		bot.setDir(new Vector2(0,1));
		BotScreen bs = new BotScreen(bot);
		JFrame f = new JFrame(TITLE);
		f.add(bs);
		f.setSize((int) SCREEN.getX(), (int) SCREEN.getY());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(true);
		f.setVisible(true);
		f.setEnabled(true);
		f.pack();
		
		
		new Runnable(0, 1) {
			Vector2 lastPos = new Vector2(2000,2000);
			double lastAngle = 0;
			Trainer trainer = new TrainerJoe(bot);
			@Override
			public void run() {
				double[] input = new double[] {(Vector2.SignedAngle(bot.getDir(), new Vector2(0,1))+180)/360,bot.getPos().getY()/1000,bot.getPos().getX()/1000};
				
				double[] res = trainer.getNet().doSth(input);
				if (Double.isNaN(res[0]) || Double.isNaN(res[1])  ) {
					SaveUtils.load();
					trainer.setNet(SaveUtils.getNeuralNet());
					return;
				}
				bot.getTruster(0).setCurrentTrust(res[0]);
				bot.getTruster(1).setCurrentTrust(res[1]);
				KnoContainer kc = new KnoContainer(input, res);
				kc.bk = bot.getAngle();
				kno.add(kc);
				trainer.onTick(input, res);
				
				bs.paint(bs.getGraphics());
				Physics.calcPhysics(bot);
				
				//NORMALISIERN
				
				
				//
				
				/*
					
					
					if (bot.getPos().getY()<899) {
						System.out.println("+ Belohnung +");
						train.add(new KnoContainer(input,res));
						
					
					}
					
					if (bot.getAngle()<10) {
						train.add(new KnoContainer(input,res));
					}
				*/
				//System.out.println("DIST"+bot.getPos().distance(new Vector2(500,500)));
				
				if (bot.getPos().distance(new Vector2(500,500))>390) {
					trainer.onDeath();
				}
				/*
				
				*/
				
				//System.out.println("R1:"+res[0]+" R2:"+res[1]);
				
			}
		};
	}
	public static void normalise(double[] in,double[]res) {
		NeuralNetwork n = SaveUtils.getNeuralNet();
		for (int i = 0;i<res.length;i++) {
			if (res[i]<0) {
				SaveUtils.load();
				n = SaveUtils.getNeuralNet();
			}
				 
		
			if(res[i] > 1) {
				double[] out = res.clone();
				out[i] = 0.9;
				n.train(in, out);
				n.train(in, out);
				n.train(in, out);

				res = out;
			}
		}
		
	}
	
	
}
