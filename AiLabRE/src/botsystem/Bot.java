package botsystem;

import java.util.ArrayList;

import utils.Vector2;

public class Bot implements BotInterface{
	
	Vector2 pos;
	Vector2 dir;
	double weight;
	ArrayList<TrusterInterface> trusters = new ArrayList<TrusterInterface>();
	
	
	public Bot(Vector2 pos,Vector2 dir,double weight) {
		this.pos = pos;
		this.dir = dir;
		this.weight = weight;
	}
	
	

	@Override
	public Vector2 getPos() {
		
		return pos;
	}

	@Override
	public void setPos(Vector2 pos) {
		this.pos = pos;
		
	}

	@Override
	public Vector2 getDir() {
		return dir;
	}

	@Override
	public void setDir(Vector2 dir) {
		this.dir = dir;	
	}

	@Override
	public double getWeight() {
		// TODO Auto-generated method stub
		return weight;
	}

	@Override
	public void setWeight(double d) {
		weight = d;
		
	}

	@Override
	public int getTrusterCount() {
		// TODO Auto-generated method stub
		return trusters.size();
	}

	@Override
	public TrusterInterface getTruster(int index) {
		// TODO Auto-generated method stub
		return trusters.get(index);
	}

	@Override
	public ArrayList<TrusterInterface> getAllTrusters() {
		// TODO Auto-generated method stub
		return trusters;
	}

	@Override
	public void removeTruster(int index) {
		trusters.remove(index);
		
	}

	@Override
	public void removeTruster(TrusterInterface truster) {
		trusters.remove(truster);
		
	}

	@Override
	public void addTruster(TrusterInterface truster) {
		// TODO Auto-generated method stub
		truster.setBot(this);
		trusters.add(truster);
	}
	
	

}
