package ai.main;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Queue;
import java.util.Random;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JSlider;

import botsystem.Bot;
import botsystem.Physics;
import botsystem.Truster;
import canvas.BotScreen;

import trainer.TrainerHeinrich;
import utils.KnoContainer;
import utils.Runnable;
import utils.SaveUtils;
import utils.Trainer;
import utils.TrainerAdvanced;
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
	
	public static float arenaSize = 390;
	public static Vector2 midpoint = new Vector2(500,500);
	
	public static void main(String args[]) {
		SaveUtils.load();
		Bot bot = new Bot(new Vector2(500, 500), new Vector2(0), 1);
		
		bot.addTruster(new Truster(new Vector2(50, 0), new Vector2(0,1), 5,5));
		bot.addTruster(new Truster(new Vector2(-50, 0), new Vector2(0,1), 5,5));
		bot.setDir(new Vector2(0,1));
		BotScreen bs = new BotScreen(bot);
		JFrame f = new JFrame(TITLE);
		f.add(bs);
		f.setSize((int) SCREEN.getX(), (int) SCREEN.getY());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(true);
		f.setVisible(true);
		f.setEnabled(true);
		f.setAutoRequestFocus(true);
		f.addKeyListener(bs);
		f.pack();
		
		//Create the slider
		
		
		//Create the label table
		/*
		Hashtable labelTable = new Hashtable();
		labelTable.put( new Integer( 0 ), new JLabel("Stop") );
		labelTable.put( new Integer( FPS_MAX/10 ), new JLabel("Slow") );
		labelTable.put( new Integer( FPS_MAX ), new JLabel("Fast") );
		framesPerSecond.setLabelTable( labelTable );
		 */
		//framesPerSecond.setPaintLabels(true);
		//Smoething
		
		new Runnable(0, 1) {
			Vector2 lastPos = new Vector2(2000,2000);
			double lastAngle = 0;
			TrainerAdvanced trainer = new TrainerHeinrich(bot);
			@Override
			public void run() {
				//double[] input = new double[] {(Vector2.SignedAngle(bot.getDir(), new Vector2(0,1))+180)/360,bot.getPos().getY()/1000,bot.getPos().getX()/1000};
				
				
				double p1 = (Vector2.SignedAngle((bot.getPos().clone().sub(midpoint)), new Vector2(0,1))+180)/360;
				double p2 = bot.getPos().clone().sub(midpoint).magnitude()/arenaSize;
				
				double[] input = new double[] {(Vector2.SignedAngle(bot.getDir(), new Vector2(0,1))+180)/360,p1,p2};
				double[] res = trainer.getBestResults(input);
				
				if (Double.isNaN(res[0])  ) {
					SaveUtils.load();
					trainer.setNet(SaveUtils.getNeuralNet());
					return;
				}
				
				bot.getTruster(0).setCurrentTrust(res[0]);
				bot.getTruster(1).setCurrentTrust(res[1]);
				KnoContainer kc = new KnoContainer(input, res);
				kc.bk = bot.getAngle();
				kno.add(kc);
				
				
				
				Physics.calcPhysics(bot);
				bs.paint(bs.getGraphics());
				trainer.onTick(addNumberToStatus(input,res[0],res[1]), trainer.getNet().doSth(addNumberToStatus(input,res[0],res[1])));
			
				
				if (bot.getPos().distance(midpoint)>arenaSize) {
					trainer.onDeath();
				}
				if (bs.qPressed) {
					System.out.print("SLOW");
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				
			}
		};
		
	}
	
	
public static double[] addNumberToStatus(double[] status,double d1,double d2) {
		
		return new double[] {status[0],status[1],status[2],d1,d2};
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
	
	/*
	 * 
	 * 
	 * new Runnable(0, 1) {
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
				
			
					
					if (bot.getPos().getY()<899) {
						System.out.println("+ Belohnung +");
						train.add(new KnoContainer(input,res));
						
					
					}
					
					if (bot.getAngle()<10) {
						train.add(new KnoContainer(input,res));
					}
				
				//System.out.println("DIST"+bot.getPos().distance(new Vector2(500,500)));
				
				if (bot.getPos().distance(new Vector2(500,500))>390) {
					trainer.onDeath();
				}
				
				
				//System.out.println("R1:"+res[0]+" R2:"+res[1]);
				
			}
		};
	 */
	
	
}
