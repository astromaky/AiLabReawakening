package utils;

public class Vector2 {

	
	
	double x;
	double y;
	
	
	
	public Vector2(double x,double y) {
		this.x = x;
		this.y = y;
	}

	public Vector2(double angle) {
		x = 0;
		y = 1;
		turnDeg(this, angle);
	}
	
	
	public double magnitude() {
		
		return Math.sqrt(x*x+y*y);
				
	}
	
	public Vector2 clone() {
		return new Vector2(x,y);
	}
	
	public double distance(Vector2 other) {
		
		Vector2 dis = sub(this,other);
		return dis.magnitude();
	}
	
	
	public static Vector2 sub(Vector2 v1,Vector2 v2) {
		
		return new Vector2(v1.getX()-v2.getX(),v1.getY()-v2.getY());
	}
	
	public static Vector2 add(Vector2 v1,Vector2 v2) {
		
		return new Vector2(v1.getX()+v2.getX(),v1.getY()+v2.getY());
	}
	
	public static Vector2 mult(Vector2 v1,double t) {
		
		return new Vector2(v1.getX()*t,v1.getY()*t);
	}	
	
	public static double getAngle(Vector2 v1,Vector2 v2) {
		
		return dotProduct(v1, v2)/(v1.magnitude()*v2.magnitude());
	}	
	
	public static Vector2 turnDeg(Vector2 v1,double angle) {
		
		double theta =  Math.toRadians(angle);
		return new Vector2(Math.cos(theta)*v1.x-Math.sin(theta)*v1.y,Math.sin(theta)*v1.x+Math.cos(theta)*v1.y);
	}
	
	public static double dotProduct(Vector2 v1,Vector2 v2) {
		return v1.x *v2.x + v1.y * v2.y;
	}
	
	
	public void add(Vector2 other) {
		
		x += other.getX();
		y += other.getY();
	}
	
	public void sub(Vector2 other) {
		
		x -= other.getX();
		y -= other.getY();
	}
	
	public void mult(double t) {
		x *= t;
		y *= t;
	}
	
	public double getX() {
		return x;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
}
