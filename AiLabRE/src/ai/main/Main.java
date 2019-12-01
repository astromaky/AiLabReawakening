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
import utils.Vector2;

public class Main {
	
	private final static String TITLE = "AiLab:RE";
	private final static Vector2 SCREEN = new Vector2(1000, 1000);
	public static ArrayList<KnoContainer> kno = new ArrayList<KnoContainer>();
	
	public static void main(String args[]) {
		SaveUtils.load();
		Bot bot = new Bot(new Vector2(550, 500), new Vector2(0), 1);
		
		bot.addTruster(new Truster(new Vector2(50, 0), new Vector2(0,1), 10,5));
		bot.addTruster(new Truster(new Vector2(-50, 0), new Vector2(0,1), 10,5));
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
			@Override
			public void run() {
				double[] input = new double[] {(Vector2.SignedAngle(bot.getDir(), new Vector2(0,1))+180)/360};
				
				double[] res = SaveUtils.getNeuralNet().doSth(input);
				bot.getTruster(0).setCurrentTrust(res[0]);
				bot.getTruster(1).setCurrentTrust(res[1]);
				KnoContainer kc = new KnoContainer(input, res);
				kc.bk = bot.getAngle();
				kno.add(kc);
				
				
				bs.paint(bs.getGraphics());
				Physics.calcPhysics(bot);
				
				//NORMALISIERN
				if (res[0]>1 && res[1]>1) {
				
					
					double[] plsRes = new double[] {1,1};
					SaveUtils.getNeuralNet().train(input, plsRes);
				}
				else if (res[0]>1) {
					
				
					double[] plsRes = new double[] {1,res[1]};
					SaveUtils.getNeuralNet().train(input, plsRes);
				}
				else if (res[1]>1) {
				
				
					double[] plsRes = new double[] {res[0],1};
					SaveUtils.getNeuralNet().train(input, plsRes);
				}
				if (res[0]<0 && res[1]<0) {
					SaveUtils.load();
					/*
					double[] plsRes = new double[] {0,0};
					SaveUtils.getNeuralNet().train(input, plsRes);
					SaveUtils.getNeuralNet().train(input, plsRes);
					SaveUtils.getNeuralNet().train(input, plsRes);
					*/
				}
				else if (res[0]<0) {
					SaveUtils.load();
					/*
					double[] plsRes = new double[] {0,res[1]};
					SaveUtils.getNeuralNet().train(input, plsRes);
					SaveUtils.getNeuralNet().train(input, plsRes);
					SaveUtils.getNeuralNet().train(input, plsRes);
					*/
				}
				else if (res[1]<0) {
					SaveUtils.load();
					/*
					double[] plsRes = new double[] {res[0],0};
					SaveUtils.getNeuralNet().train(input, plsRes);
					SaveUtils.getNeuralNet().train(input, plsRes);
					SaveUtils.getNeuralNet().train(input, plsRes);
					*/
				}
				
				//
				
				/*
					
					
					if (bot.getPos().getY()<899) {
						System.out.println("+ Belohnung +");
						SaveUtils.getNeuralNet().train(input, res);
						
					
					}
					
					if (bot.getAngle()<10) {
						SaveUtils.getNeuralNet().train(input, res);
					}
				*/
				System.out.println("DIST"+bot.getPos().distance(new Vector2(500,500)));
				
				if (bot.getPos().distance(new Vector2(500,500))>390) {
					System.out.println("- Bestrafung -");
					/*
					Random r = new Random();
					double[] plsRes = new double[] {r.nextDouble(),r.nextDouble()};
					SaveUtils.getNeuralNet().train(input, plsRes);
					SaveUtils.getNeuralNet().train(input, plsRes);
					SaveUtils.getNeuralNet().train(input, plsRes);*/
					bot.setPos(new Vector2(500,500));
					bot.setDir(new Vector2(0,1));
					
					int cut = 30;
					int smartEcho = 40;
					for (int index = kno.size()-1;index >= 0;index--) {
						
						
						KnoContainer k = kno.get(index);
						
						if (index > kno.size()-cut) {
							
							/*double[] plsRes2 = new double[] {r.nextDouble(),r.nextDouble()};
							SaveUtils.getNeuralNet().train(k.in, plsRes2);*/
						}else if (!(index < cut)) {
							
							for (int i = 0;i < (/*kno.size()-index / smartEcho*/1);i++) {
								SaveUtils.getNeuralNet().train(k.in, k.res);
							}
							
						}
						
						
						
					}
					kno.clear();
				}
				if (bot.getPos().distance(lastPos)<5) {
					SaveUtils.getNeuralNet().train(input, res);
					
				}
				if (bot.getPos().distance(new Vector2(500,500))<100) {
					System.out.println("+ Belohnung +");
					SaveUtils.getNeuralNet().train(input, res);
					SaveUtils.getNeuralNet().train(input, res);
					SaveUtils.getNeuralNet().train(input, res);
				}
				
				if (bot.getPos().getY()<500) {
					SaveUtils.getNeuralNet().train(input, res);
					SaveUtils.getNeuralNet().train(input, res);
					SaveUtils.getNeuralNet().train(input, res);
				}
				if (bot.getPos().getY()<300) {
					Random r = new Random();
					double[] plsRes = new double[] {0.2,0.2};
					SaveUtils.getNeuralNet().train(input, plsRes);
				}
				
				if (bot.getPos().getY()>700) {
					Random r = new Random();
					double[] plsRes = new double[] {r.nextDouble(),r.nextDouble()};
					SaveUtils.getNeuralNet().train(input, plsRes);
				}
				
				
				
				
				double man = 0.0001;
				if (bot.getPos().getX()<300) {
					double[] plsRes = new double[] {res[0]*(1-man),res[1]*(1+man)};
					SaveUtils.getNeuralNet().train(input, plsRes);
				}
				if (bot.getPos().getX()>700) {
					double[] plsRes = new double[] {res[0]*(1+man),res[1]*(1-man)};
					SaveUtils.getNeuralNet().train(input, plsRes);
				}
				
				if (bot.getPos().getY()>700) {
					Random r = new Random();
					double[] plsRes = new double[] {res[0]*(1+(man+res[1]*man)/2),res[1]*(1+(man+res[0]*man)/2)};
					SaveUtils.getNeuralNet().train(input, plsRes);
				}
				System.out.println("R1:"+res[0]+" R2:"+res[1]);
				lastPos = bot.getPos();
				lastAngle = bot.getAngle();
			}
		};
	}

	
	
}
