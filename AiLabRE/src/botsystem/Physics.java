package botsystem;

import utils.Vector2;

public class Physics {

	
	
	
	
	public static double gravity = 0.01;
	public static double inertia = 100;
	
	
	
	
	
	
	
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
		System.out.println(bot.getDir().getNormalized());
		
		
	}
	
	
	public static void calcGravity(Bot bot) {
		double botAngle = Vector2.getAngle(bot.getDir(), new Vector2(0,1));
		Vector2 centerOfMass = Vector2.turnDeg(bot.getCenterOfMass(),botAngle);
		double totalMass = bot.totalWeight;
		
		
		Vector2 comMovement = centerOfMass.clone();
		
		comMovement = comMovement.add(new Vector2(0,totalMass*gravity));
		
		double angle = Vector2.getAngle(comMovement, centerOfMass);
		
		//System.out.println("COMMove "+comMovement.toString()+"   CenterOfMass"+centerOfMass.toString());
		bot.setDir(Vector2.turnDeg(bot.getDir(),angle));
		double comMag = centerOfMass.magnitude();
		//System.out.println("MAG"+comMag);
		
		Vector2 point = comMovement.mult(-1).getNormalized().mult(comMag).add(comMovement);
		
		bot.setPos(bot.getPos().add(point));
		
		/*
		System.out.println("totalMass"+totalMass);
		System.out.println("ANG"+ angle);
		System.out.println("DIR"+ bot.getDir());
		System.out.println("A"+ comMovement.mult(-1).getNormalized());
		System.out.println("B"+ comMag);
		System.out.println("P"+point);
		System.out.println(point.toString());*/
	}
	
	
	public static void calcTrust(Bot bot) {
		double botAngle = Vector2.getAngle(bot.getDir(), new Vector2(0,1));
		Vector2 centerOfMass = Vector2.turnDeg(bot.getCenterOfMass(),botAngle);
	
		
		for (TrusterInterface t : bot.getAllTrusters()) {
			Vector2 comMovement = centerOfMass.clone();
			
			comMovement = comMovement.add(Vector2.turnDeg(t.getDirection(),botAngle).mult(t.getCurrentTrust()));
			
			double angle = Vector2.getAngle(comMovement, centerOfMass);
			
			//System.out.println("COMMove "+comMovement.toString()+"   CenterOfMass"+centerOfMass.toString());
			bot.setDir(Vector2.turnDeg(bot.getDir(),angle));
			double comMag = centerOfMass.magnitude();
			//System.out.println("MAG"+comMag);
			
			Vector2 point = comMovement.mult(-1).getNormalized().mult(comMag).add(comMovement);
			
			bot.setPos(bot.getPos().add(point));
			
		}
	}
	
	
	
}
