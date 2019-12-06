package utils;

import java.util.ArrayList;

import botsystem.Bot;

public class TrainerGiovanna extends TrainerEcho {

	
	public ArrayList<KnoContainer> train = new ArrayList<KnoContainer>();
	public TrainerGiovanna(Bot bot) {
		super(bot);
		// TODO Auto-generated constructor stub
	}

	double vel = bot.getVelocity().magnitude();
	double angle = bot.getMomentum();
	@Override
	public void onTickAdvanced(double[] in, double[] res) {
		// TODO Auto-generated method stub
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
				train.add(new KnoContainer(in, out));
				train.add(new KnoContainer(in, out));
				train.add(new KnoContainer(in, out));
				train.add(new KnoContainer(in, out));
				train.add(new KnoContainer(in, out));
				train.add(new KnoContainer(in, out));
				train.add(new KnoContainer(in, out));
				train.add(new KnoContainer(in, out));
				train.add(new KnoContainer(in, out));
				train.add(new KnoContainer(in, out));
				train.add(new KnoContainer(in, out));
				train.add(new KnoContainer(in, out));
				train.add(new KnoContainer(in, out));
				train.add(new KnoContainer(in, out));
				train.add(new KnoContainer(in, out));
				train.add(new KnoContainer(in, out));
				train.add(new KnoContainer(in, out));
				train.add(new KnoContainer(in, out));
				train.add(new KnoContainer(in, out));
				train.add(new KnoContainer(in, out));
				train.add(new KnoContainer(in, out));
				train.add(new KnoContainer(in, out));
				train.add(new KnoContainer(in, out));
				train.add(new KnoContainer(in, out));
				train.add(new KnoContainer(in, out));
				train.add(new KnoContainer(in, out));
				train.add(new KnoContainer(in, out));
				train.add(new KnoContainer(in, out));
				train.add(new KnoContainer(in, out));
				train.add(new KnoContainer(in, out));
				res = out;
			}
		}
		
	}

	@Override
	public void onDeathAdvanced() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		bot.setPos(new Vector2(500,500));
		bot.setDir(new Vector2(0,1));
		
		for (KnoContainer kc : train) {
			n.train(kc.in, kc.res);
		}
		
		train.clear();
	}

}
