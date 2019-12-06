package utils;

import ai.main.NeuralNetwork;
import botsystem.Bot;

public abstract class TrainerEcho extends Trainer{

	public TrainerEcho(Bot bot) {
		super(bot);
		// TODO Auto-generated constructor stub
	}
	
	NeuralNetwork last;
	NeuralNetwork lastB;
	int lastTime = 0;
	int tick = 0;
	@Override
	public void onDeath() {
		// TODO Auto-generated method stub
	
	
		if (tick > lastTime) {
			
			lastTime = tick;
			last = new NeuralNetwork(n.toString());
			
		}
		else {
			n = last;
			last = new NeuralNetwork(n.toString());
		}
		System.out.println("Current " +tick+" Last "+ lastTime);
		
		
		if (tick > lastTime) {
			onDeathAdvanced();
			
		}
		
		bot.setPos(new Vector2(500,500));
		bot.setDir(new Vector2(0,1));
		bot.resetVelocity();
		tick = 0;
	}

	@Override
	public void onTick(double[] in, double[] res) {
		
		tick++;
		onTickAdvanced(in, res);
		
	}
	public abstract void onTickAdvanced(double[] in, double[] res);
	public abstract void onDeathAdvanced();
	
}
