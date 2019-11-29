package botsystem;

import utils.Vector2;

public class Truster implements TrusterInterface{
	
	Bot bot;
	Vector2 pos;
	Vector2 dir;
	double maxTrust;
	double currentTrust;
	double weight;
	
	
	public Truster(Vector2 pos,Vector2 dir,double maxTrust,double weight) {
		
		
		this.pos = pos;
		this.dir = dir;
		this.maxTrust = maxTrust;
		this.weight = weight;
		
	}
	
	
	
	public void setBot(Bot bot) {
		this.bot = bot;
	}
	
	@Override
	public Vector2 getPos() {
		
		return pos;
	}

	@Override
	public Vector2 getAbsolutePos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector2 getDirection() {
		return dir;
	}

	@Override
	public double getMaxTrust() {
		return maxTrust;
	}

	@Override
	public double getCurrentTrust() {
		return currentTrust;
	}

	@Override
	public double getWeight() {
		return weight;
	}

	@Override
	public void setPos(Vector2 pos) {
		this.pos = pos;	
	}

	@Override
	public void setDirection(Vector2 dir) {
		this.dir = dir;	
	}

	@Override
	public void setMaxTrust(double trust) {
		maxTrust = trust;		
	}

	@Override
	public void setCurrentTrust(double trust) {
		currentTrust = trust;
	}

	@Override
	public void setWeight(double weight) {
		this.weight = weight;
	}

}
