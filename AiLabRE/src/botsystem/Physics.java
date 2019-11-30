package botsystem;

import utils.Vector2;

public class Physics {

	
	
	
	
	public static double gravity = 0.5;
	public static double inertia = 0.2;
	
	
	
	
	
	
	
	public static void calcPhysics(Bot bot) {
		
		// CALC WEIGHT
		
		calcGravity(bot);
		
		
		// CALC TRUST
		
		
		
		//calcTrust(bot);
		
		/*
		for (TrusterInterface t : bot.getAllTrusters()) {
	
			centerOfMass.add(Vector2.mult(t.getPos(), t.getWeight()));
		}
		*/
		
		if (bot.getPos().getY() > 900) {
			Vector2 pos = bot.getPos();
			pos.setY(900);
			bot.setPos(pos);
		}
		System.out.println(bot.getDir().getNormalized());
		
		
	}
	
	
	public static void calcGravity(Bot bot) {
		
		/*
		// Vector2.turnDeg(bot.getCenterOfMass(),botAngle).sub(bot.getCenter());
		Vector2 centerOfMass = bot.getCenterOfMass();
		double totalMass = bot.totalWeight;
		
		
		Vector2 comMovement = centerOfMass.clone();
		
		comMovement = comMovement.add(new Vector2(0,totalMass*gravity));
		
		double angle = Vector2.getAngle(comMovement, centerOfMass);
		
		//System.out.println("COMMove "+comMovement.toString()+"   CenterOfMass"+centerOfMass.toString());
		//bot.setDir(Vector2.turnDeg(bot.getDir(),angle));
		double comMag = centerOfMass.magnitude();
		//System.out.println("MAG"+comMag);
		
		//Vector2 point = comMovement.mult(-1).getNormalized().mult(comMag).add(comMovement);
		
		*/
		double totalMass = bot.totalWeight;
		Vector2 center = bot.getAbsoluteCenter();
		Vector2 absCOM = bot.getAbsoluteCenterOfMass();
		Vector2 aV = absCOM.sub(center).getNormalized();
		Vector2 aV2 = aV.add(new Vector2(0,totalMass*gravity*inertia)).getNormalized();
		double angle = Vector2.SignedAngle(aV, aV2);
		if (Math.abs(angle) < 0.5) {
			angle = 0;
		}
		System.out.println(angle);
		bot.turnAroundMiddle(angle);
		//bot.turnAroundMiddle(1);
		bot.setPos(bot.getPos().add(new Vector2(0,totalMass*gravity)));
		
		
		
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
		//
	}
	
	
	
}
