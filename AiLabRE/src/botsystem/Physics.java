package botsystem;

import utils.Vector2;

public class Physics {

	
	
	
	
	public static double gravity = 1;
	public static double inertia = 1;
	
	
	
	
	
	
	
	public static void calcPhysics(Bot bot) {
		
		// CALC WEIGHT
		
		calcGravity(bot);
		
		
		// CALC TRUST
		
		
		
		calcTrust(bot);
		
		/*
		for (TrusterInterface t : bot.getAllTrusters()) {
	
			centerOfMass.add(Vector2.mult(t.getPos(), t.getWeight()));
		}
		*/
		
		
		
	}
	
	
	public static void calcGravity(Bot bot) {
		Vector2 centerOfMass = bot.getCenterOfMass();
		double totalMass = bot.totalWeight;
		// TODO BOT METHODS GET CENTER OF MASS AND GET TOTAL MASS
		Vector2 comMovement = centerOfMass.clone();
		comMovement = comMovement.add(new Vector2(0,-totalMass*gravity));
		
		double angle = Vector2.getAngle(centerOfMass, comMovement);
		
		//System.out.println("COMMove "+comMovement.toString()+"   CenterOfMass"+centerOfMass.toString());
		bot.setDir(Vector2.turnDeg(bot.getDir(),angle));
		double comMag = centerOfMass.magnitude();
		//System.out.println("MAG"+comMag);
		System.out.println("A"+ comMovement.mult(-1).getNormalized());
		System.out.println("B"+ comMag);
		Vector2 point = comMovement.mult(-1).getNormalized().mult(comMag).add(comMovement);
		System.out.println(point.toString());
		bot.setPos(bot.getPos().add(point));
	}
	
	
	public static void calcTrust(Bot bot) {
		Vector2 centerOfMass = bot.getCenterOfMass();
		double totalMass = bot.totalWeight;
		
		for (TrusterInterface t : bot.getAllTrusters()) {
			Vector2 comMovement = centerOfMass.clone();
			Vector2 dir = t.getDirection();
			comMovement = comMovement.add(dir.mult(t.getCurrentTrust()));
			double angle = Vector2.getAngle(centerOfMass, comMovement);
			bot.setDir(Vector2.turnDeg(bot.getDir(),angle));
			double comMag = centerOfMass.magnitude();
			
			Vector2 point = comMovement.mult(-1).getNormalized().mult(comMag).add(centerOfMass);
			bot.setPos(bot.getPos().add(point));
		}
	}
	
	
	
}
