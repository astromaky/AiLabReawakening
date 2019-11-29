package botsystem;

import utils.Vector2;

public interface TrusterInterface {

	
	
	
	
	
	
	public Vector2 getPos();
	public Vector2 getAbsolutePos();
	public Vector2 getDirection();
	public double getMaxTrust();
	public double getCurrentTrust();
	public double getWeight();
	
	
	public void setBot(Bot b);
	public void setPos(Vector2 pos);
	public void setDirection(Vector2 dir);
	public void setMaxTrust(double trust);
	public void setCurrentTrust(double trust);
	public void setWeight(double weight);
}
