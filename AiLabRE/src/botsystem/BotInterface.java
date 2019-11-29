package botsystem;

import java.awt.Graphics2D;
import java.util.ArrayList;

import utils.Vector2;

public interface BotInterface {

	
	
	
	
	
	
	
	
	
	
	public Vector2 getPos();
	public void setPos(Vector2 pos);
	
	public Vector2 getDir();
	public void setDir(Vector2 dir);
	
	public double getWeight();
	public void setWeight(double d);
	
	public int getTrusterCount();
	public TrusterInterface getTruster(int index);
	public ArrayList<TrusterInterface> getAllTrusters();
	
	public void removeTruster(int index);
	public void removeTruster(TrusterInterface truster);
	public void addTruster(TrusterInterface truster);
	
}
