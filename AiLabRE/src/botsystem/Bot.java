package botsystem;

import java.util.ArrayList;

import utils.Vector2;

public class Bot implements BotInterface{
	
	Vector2 pos;
	Vector2 dir;
	double weight;
	double totalWeight;
	
	ArrayList<TrusterInterface> trusters = new ArrayList<TrusterInterface>();
	
	
	public Bot(Vector2 pos,Vector2 dir,double weight) {
		this.pos = pos;
		this.dir = dir;
		this.weight = weight;
		totalWeight = calcTotalWeight();
		
	}
	
	public void turnAroundMiddle(double d) {
		Vector2 cent = getAbsoluteCenter();
		Vector2 turn = pos.sub(cent);
		turn = Vector2.turnDeg(turn, d);
		/*
		Vector2 turn2 = Vector2.add(pos, dir.getNormalized()).sub(cent);
		turn2 = Vector2.turnDeg(turn2, d);
		dir = Vector2.add(turn.mult(-1), turn2).getNormalized();
		*/
		pos = turn.add(cent);
		dir = Vector2.turnDeg(dir,-d);
		
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
	
	
	public Vector2 calcCenterOfMass() {
		System.out.println("WAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
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
	public Vector2 getAbsoluteCenter() {
		Vector2 center =pos;
		for (TrusterInterface t : getAllTrusters()) {
			
			center = center.add(t.getAbsolutePos());
		}
		return center.div(getAllTrusters().size()+1);
	}
	
	public Vector2 getAbsoluteCenterOfMass() {
		Vector2 subVec = getAbsoluteCenter();
		Vector2 centerOfMass = getAbsoluteCenter();
		
		centerOfMass = centerOfMass.add(pos.sub(subVec).mult((getWeight()/totalWeight)));
		
		for (TrusterInterface t : getAllTrusters()) {
			
			centerOfMass = centerOfMass.add(t.getAbsolutePos().sub(subVec).mult((t.getWeight()/totalWeight)));
		}
		System.out.println("CALC CENTER"+ centerOfMass);
		return centerOfMass;
	}
	

}
