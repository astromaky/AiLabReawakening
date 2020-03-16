package utils;

import botsystem.Bot;

public abstract class TrainerAdvanced extends Trainer{

	public TrainerAdvanced(Bot bot) {
		super(bot);
		
	}

	
	
	public double[] getBestResults(double[] status) {
		
		double[] max = addNumberToStatus(status, 0, 0);
		for (double i = 0;i<=1;i++) {
			for (double j = 0;j<=1;j++) {
				
				if (getNet().doSth(max)[0] < getNet().doSth(addNumberToStatus(status, i, j))[0]) {
					max = addNumberToStatus(status, i, j);
				}
				
			}
		}
		
		return new double[] {max[3],max[4]};
		
	}
	
	
	public double[] addNumberToStatus(double[] status,double d1,double d2) {
		
		return new double[] {status[0],status[1],status[2],d1,d2};
	}

}
