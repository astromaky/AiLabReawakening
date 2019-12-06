package utils;

import java.util.ArrayList;
import java.util.Random;

import ai.main.NeuralNetwork;
import botsystem.Bot;

public abstract class Trainer{

	Bot bot;
	NeuralNetwork n;
	
	
	
	public Trainer(Bot bot) {
		this.bot = bot;
		n = SaveUtils.getNeuralNet();
	}
	
	public abstract void onDeath();
	public abstract void onTick(double[] in,double[]res);
	
	
	public double[] randRes(int i) {
		Random r = new Random();
		double[] plsRes = new double[i];
		for (int x = 0;x>i;x++) {
			plsRes[x] = r.nextDouble();
		}
		return plsRes;
	}
	
	public NeuralNetwork getNet() {
		return n;
		
	}
	public void setNet(NeuralNetwork net) {
		n = net;
		
	}
	
}
