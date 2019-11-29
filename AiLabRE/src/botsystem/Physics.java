package botsystem;

import utils.Vector2;

public class Physics {

	
	
	
	
	public double gravity = 0.001;
	public double inertia = 1;
	
	
	
	
	
	
	
	public void calcBot(Bot bot) {
		
		// CALC WEIGHT
		Vector2 centerOfMass = new Vector2(0,0);
		double totalMass = 1;
		
		Vector2 comMovement = centerOfMass.clone();
		comMovement.add(new Vector2(0,-totalMass*gravity));
		
		double angle = Vector2.getAngle(centerOfMass, comMovement);
		
		
		bot.setDir(Vector2.turnDeg(bot.getDir(),angle));
		double comMag = centerOfMass.magnitude();
		
		//bot.setPos(comMovement.m);
		
		
		
		
		
		
		/*
		for (TrusterInterface t : bot.getAllTrusters()) {
	
			centerOfMass.add(Vector2.mult(t.getPos(), t.getWeight()));
		}
		*/
		
		
		
	}
	
	
	
	
	
}
