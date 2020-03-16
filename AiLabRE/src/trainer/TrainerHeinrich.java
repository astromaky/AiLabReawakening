package trainer;

import ai.main.Main;
import botsystem.Bot;
import utils.TrainerAdvanced;
import utils.Vector2;

public class TrainerHeinrich extends TrainerAdvanced{

	Bot bot;
	public TrainerHeinrich(Bot bot) {
		super(bot);
		this.bot = bot;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onDeath() {
		// TODO Auto-generated method stub
		bot.setPos(Main.midpoint);
		bot.setDir(new Vector2(0,1));
		bot.resetVelocity();
		lastdistance = 0;
		lastangle = 0.5;
		System.out.print("Ticks survived"+ tick);
		tick = 0;
		lastvelocitymag = 0;
	}

	double optdistance = 0;
	double optangle = 0.5;
	
	double lastdistance = 0;
	double lastangle = 0.5;
	double lastvelocitymag = 0;
	int tick = 0;
	@Override
	public void onTick(double[] in, double[] res) {
		tick++;
		// TODO Auto-generated method stub
		double oldvalue = res[0];
		double learningRate = 0.3F;
		double discountFactor = 0.5F;
		
		double p1 = (Vector2.SignedAngle((bot.getPos().clone().sub(Main.midpoint)), new Vector2(0,1))+180)/360;
		double p2 = bot.getPos().clone().sub(Main.midpoint).magnitude()/Main.arenaSize;
		
		double[] input = new double[] {(Vector2.SignedAngle(bot.getDir(), new Vector2(0,1))+180)/360,p1,p2};
		
		double distance = (Main.midpoint.sub(bot.getPos()).magnitude())/Main.arenaSize;
		double angle = (Vector2.SignedAngle(bot.getDir(), new Vector2(0,1))+180)/360;
		
		
		double aFail = (0.5 -( Math.abs(optangle-angle) -  Math.abs(optangle-lastangle)))*2-1;
		
		double dFail =  (1 -( Math.abs(optdistance-distance) -  Math.abs(optdistance-lastdistance)))-1;
		
	
		
		double reward =  (-bot.getVelocity().magnitude()+  lastvelocitymag)*100;
		System.out.println("Reward "+reward);
		
		//Schlechter -100000000000
		//Gut = 1;
		
		double newQ = oldvalue + learningRate * (reward + discountFactor * getBestResults(input)[0] * -oldvalue);
		
		getNet().train(in, new double[] {newQ});
		
		lastangle = angle;
		lastdistance = distance;
		lastvelocitymag = bot.getVelocity().magnitude();
	}

}
