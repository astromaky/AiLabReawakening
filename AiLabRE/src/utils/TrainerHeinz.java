package utils;

import java.util.ArrayList;

import ai.main.NeuralNetwork;
import botsystem.Bot;

public class TrainerHeinz extends Trainer{

	
	public ArrayList<KnoContainer> train = new ArrayList<KnoContainer>();
	
	public TrainerHeinz(Bot bot) {
		super(bot);
		// TODO Auto-generated constructor stub
	}

	

	
	double vel = bot.getVelocity().magnitude();
	double angle = bot.getMomentum();
	@Override
	public void onTick(double[] in, double[] res) {
		//oldNorm(in, res);
		normalise(in,res);
		// TODO Auto-generated method stub
			
			double deltaV =bot.getVelocity().magnitude()-vel;
			double deltaM = Math.abs(angle - bot.getMomentum());
			
			
			if(deltaV < 1 && deltaM <1)
				train.add(new KnoContainer(in, res));
			
			/*
			if (vel > 6) {
				train.add(new KnoContainer(in, randRes(2)));
				System.out.println("Failvel"+vel);
			}
			*/
			
			vel = bot.getVelocity().magnitude();
			angle = bot.getMomentum();
			
			
		
	}

	public void normalise(double[] in,double[]res) {
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
				n.train(in, out);
				n.train(in, out);
				n.train(in, out);
				res = out;
			}
		}
		
	}

	
	public void oldNorm(double[] input,double[]res) {
		System.out.println("NORMALISER");
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
			/*
			double[] plsRes = new double[] {0,0};
			train.add(new KnoContainer(input,plsRes));
			train.add(new KnoContainer(input,plsRes));
			train.add(new KnoContainer(input,plsRes));
			*/
		}
		else if (res[0]<0) {
			SaveUtils.load();
			/*
			double[] plsRes = new double[] {0,res[1]};
			train.add(new KnoContainer(input,plsRes));
			train.add(new KnoContainer(input,plsRes));
			train.add(new KnoContainer(input,plsRes));
			*/
		}
		else if (res[1]<0) {
			SaveUtils.load();
			/*
			double[] plsRes = new double[] {res[0],0};
			train.add(new KnoContainer(input,plsRes));
			train.add(new KnoContainer(input,plsRes));
			train.add(new KnoContainer(input,plsRes));
			*/
		}
	}
	@Override
	public void onDeath() {
		// TODO Auto-generated method stub
		bot.setPos(new Vector2(500,500));
		bot.setDir(new Vector2(0,1));
		
		for (KnoContainer kc : train) {
			n.train(kc.in, kc.res);
		}
		train.clear();
	}
}
