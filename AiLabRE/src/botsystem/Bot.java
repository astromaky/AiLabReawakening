package botsystem;

import java.util.ArrayList;

import utils.Vector2;

public class Bot implements BotInterface{
	
	Vector2 pos;
	Vector2 dir;
	double weight;
	double totalWeight;
	Vector2 com;
	ArrayList<TrusterInterface> trusters = new ArrayList<TrusterInterface>();
	
	
	public Bot(Vector2 pos,Vector2 dir,double weight) {
		this.pos = pos;
		this.dir = dir;
		this.weight = weight;
		totalWeight = calcTotalWeight();
		com = calcCenterOfMass();
	}
	
	

	@Override
	public Vector2 getPos() {
		
		return pos;
	}

	@Override
	public void setPos(Vector2 pos) {
		//System.out.println(""+pos.toString());
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
		totalWeight = calcTotalWeight();
		com = calcCenterOfMass();
	}
	
	public double getTotalWeight() {
		
		return totalWeight;
		
	}
	public double calcTotalWeight() {
		double wt = weight;
		
		for (TrusterInterface t : getAllTrusters()) {
			
			wt += t.getWeight();
			
		}
		return wt;
	}
	public Vector2 getCenterOfMass() {
		return com;
	}
	
	public Vector2 calcCenterOfMass() {
		Vector2 centerOfMass = new  Vector2(0,0);
		Vector2 center = new  Vector2(0,0);
		for (TrusterInterface t : getAllTrusters()) {
			
			center = center.add(t.getPos());
		}
		centerOfMass = center.div(getAllTrusters().size()+1);
		
		
		for (TrusterInterface t : getAllTrusters()) {
			
			centerOfMass.add(t.getPos().mult((t.getWeight()/totalWeight)));
		}
		System.out.println("CALC CENTER"+ centerOfMass);
		return centerOfMass;
	}
	
	public Vector2 getCenter() {
		Vector2 center = new  Vector2(0,0);
		for (TrusterInterface t : getAllTrusters()) {
			
			center = center.add(t.getPos());
		}
		return center.div(getAllTrusters().size()+1);
	}
	
	

}
