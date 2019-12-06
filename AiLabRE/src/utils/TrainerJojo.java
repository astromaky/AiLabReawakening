package utils;

import java.util.ArrayList;
import java.util.Random;

import botsystem.Bot;

public class TrainerJojo extends TrainerEcho{


		public ArrayList<KnoContainer> train = new ArrayList<KnoContainer>();
		public ArrayList<KnoContainer> kno = new ArrayList<KnoContainer>();
		
		public TrainerJojo(Bot bot) {
			super(bot);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onDeathAdvanced() {
		
			
			Random r = new Random();
			double[] plsRes = new double[] {r.nextDouble(),r.nextDouble()};
			double[] plsIn = new double[] {r.nextDouble(),r.nextDouble(),r.nextDouble()};
			train.add(new KnoContainer(plsIn,plsRes));
			train.add(new KnoContainer(plsIn,plsRes));
			train.add(new KnoContainer(plsIn,plsRes));
			bot.setPos(new Vector2(500,670));
			bot.setDir(new Vector2(0,1));
			
			int cut = 30;
			int smartEcho = 40;
			for (int index = kno.size()-1;index >= 0;index--) {
				
				
				KnoContainer k = kno.get(index);
				
				if (index > kno.size()-cut) {
					
					double[] plsRes2 = new double[] {r.nextDouble(),r.nextDouble()};
					SaveUtils.getNeuralNet().train(k.in, plsRes2);
				}else if (!(index < cut)) {
					//(/*kno.size()-index / smartEcho*/1)
					for (int i = 0;i <1 ;i++) {
						n.train(k.in, k.res);
					}
					
				}
				
				
				
			}
			kno.clear();
		
			for (KnoContainer c : train) {
				n.train(c.in,c.res);
			}
			train.clear();
			
		}
		Vector2 lastPos = new Vector2(2000,2000);
		double lastAngle = 0;
		@Override
		public void onTickAdvanced(double[] input, double[] res) {
			
			if (res[0]>1 && res[1]>1) {
				
				
				double[] plsRes = new double[] {1,1};
				train.add(new KnoContainer(input,plsRes));
				
			}
			else if (res[0]>1) {
				
			
				double[] plsRes = new double[] {1,res[1]};
				train.add(new KnoContainer(input,plsRes));
			}
			else if (res[1]>1) {
			
			
				double[] plsRes = new double[] {res[0],1};
				train.add(new KnoContainer(input,plsRes));
			}
			if (res[0]<0 && res[1]<0) {
				SaveUtils.load();
				n = SaveUtils.getNeuralNet();
				/*
				double[] plsRes = new double[] {0,0};
				train.add(new KnoContainer(input,plsRes));
				train.add(new KnoContainer(input,plsRes));
				train.add(new KnoContainer(input,plsRes));
				*/
			}
			else if (res[0]<0) {
				SaveUtils.load();
				n = SaveUtils.getNeuralNet();
				/*
				double[] plsRes = new double[] {0,res[1]};
				train.add(new KnoContainer(input,plsRes));
				train.add(new KnoContainer(input,plsRes));
				train.add(new KnoContainer(input,plsRes));
				*/
			}
			else if (res[1]<0) {
				SaveUtils.load();
				n = SaveUtils.getNeuralNet();
				/*
				double[] plsRes = new double[] {res[0],0};
				train.add(new KnoContainer(input,plsRes));
				train.add(new KnoContainer(input,plsRes));
				train.add(new KnoContainer(input,plsRes));
				*/
			}
			// TODO Auto-generated method stub
			if (bot.getPos().distance(lastPos)<5) {
				train.add(new KnoContainer(input,res));
				
			}
			if (bot.getPos().distance(new Vector2(500,500))<100) {
			
				train.add(new KnoContainer(input,res));
				train.add(new KnoContainer(input,res));
				train.add(new KnoContainer(input,res));
			}
			
			if (bot.getPos().getY()<500) {
				train.add(new KnoContainer(input,res));
				train.add(new KnoContainer(input,res));
				train.add(new KnoContainer(input,res));
			}
			if (bot.getPos().getY()<300) {
				Random r = new Random();
				double[] plsRes = new double[] {0.2,0.2};
				train.add(new KnoContainer(input,plsRes));
			}
			
			if (bot.getPos().getY()>700) {
				Random r = new Random();
				double[] plsRes = new double[] {r.nextDouble(),r.nextDouble()};
				train.add(new KnoContainer(input,plsRes));
			}
			
			
			
			
			double man = 0.0001;
			if (bot.getPos().getX()<300) {
				double[] plsRes = new double[] {res[0]*(1-man),res[1]*(1+man)};
				train.add(new KnoContainer(input,plsRes));
			}
			if (bot.getPos().getX()>700) {
				double[] plsRes = new double[] {res[0]*(1+man),res[1]*(1-man)};
				train.add(new KnoContainer(input,plsRes));
			}
			
			if (bot.getPos().getY()>700) {
				Random r = new Random();
				double[] plsRes = new double[] {res[0]*(1+(man+res[1]*man)/2),res[1]*(1+(man+res[0]*man)/2)};
				train.add(new KnoContainer(input,plsRes));
			}
			lastPos = bot.getPos();
			lastAngle = bot.getAngle();
		}



		
		
		
	

}
