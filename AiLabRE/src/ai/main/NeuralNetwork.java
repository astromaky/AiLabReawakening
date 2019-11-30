package ai.main;

import java.util.Random;

public class NeuralNetwork {
	
	private double net[][][];
	private int inputs;
	
	public NeuralNetwork(int[] layer_matrix1) {
		int index = 0;
		int[] layer_matrix = new int[layer_matrix1.length];
		for (int i : layer_matrix1) {
			layer_matrix[index] = i;
			index ++;
		}
		layer_matrix[0] = layer_matrix[0]+1;
		inputs = layer_matrix[0];
		net = new double[layer_matrix.length][][];
		
		net[0] = new double[inputs][1];
		for (int l=1;l < net.length;l++) {
			net[l] = new double[layer_matrix[l]][layer_matrix[l-1]];
		}
		
		for(int l=0;l<net.length;l++) {
			for (int m=0;m<net[l].length;m++) {
				for (int i=0;i <net[l][m].length;i++) {
					net[l][m][i] = createWeight();
				}
			}
		}
		
	}
	
	public NeuralNetwork(String s) {
		String[] layers = s.split("\\+");
		net = new double[layers.length][][];
		for (int l = 0; l < layers.length; l++) {
			String[] arry = layers[l].split("\\#");
			net[l] = new double[arry.length][];
			for (int m = 0; m < arry.length; m++) {
				String[] dou = arry[m].split("_");
				net[l][m] = new double[dou.length];
				for (int d = 0; d < dou.length; d++) {
					net[l][m][d] = Double.parseDouble(dou[d]);
				}
			}
		}
	}
	
	public double[] doSth(double sum[]) {
		
		print();
		double[] add = new double[sum.length+1];
		for (int i = 0; i < sum.length;i++) {
			add[i] = sum[i];
		}
		add[sum.length] = 1;
		
		for (int l = 1; l < net.length;l++) {
			
			sum = new double[net[l].length];
			
			for (int m = 0;m < net[l].length;m++) {
				double count = 0;
				for (int i = 0; i < net[l][m].length;i++) {
					count += (add[i]*net[l][m][i]);
				}
				
				sum[m] = leakyReLu(count);
			}
			
			add = sum.clone();
			
		}
		return sum;
	}
	
	public void train(double[] in, double[] out) {
		
		for(int l=1;l<net.length;l++) {
			for (int m=0;m<net[l].length;m++) {
				for (int i=0;i <net[l][m].length;i++) {
					net[l][m][i] = net[l][m][i]+(errorWeight(l, m, i, out, in)*0.1);
				}
				
			}
		}
	}
	
	private double errorWeight(int layer,int row,int x,double[] out, double[] in) {
		double[] flawed = doSth(in);
		for (int i = 0; i <flawed.length;i++) {
			flawed[i] = (flawed[i] - out[i]);
		}
		return -(sigma(layer,row,x,flawed,in)*getOuti(layer, row, in));
	}
	
	private double sigma(int layer,int row,int x,double[] flawed,double in[]) {
		double output = 0;
		if (layer == net.length-1) {
			output = flawed[row];
		} else {
			for (int r = 0;r < net[layer+1].length;r++) {
				output = (sigma(layer+1,r,row,flawed,in)*net[layer+1][r][row]);
			}
		}
		return output*ReLUPrime(getNeti(layer, row, in));
	}
	
	private double getNeti(int layer,int row,double sum[]) {
		return getOuti(layer, row, sum) > 0 ? getOuti(layer, row, sum) : getOuti(layer, row, sum)*100 ;
	}
	
	private double getOuti(int layer,int row,double sum[]) {
		double[] add = new double[sum.length+1];
		for (int i = 0; i < sum.length;i++) {
			add[i] = sum[i];
		}
		add[sum.length] = 1;
		
		for (int l = 1; l <layer+1 ;l++) {
			
			sum = new double[net[l].length];
			
			for (int m = 0;m < net[l].length;m++) {
				double count = 0;
				for (int i = 0; i < net[l][m].length;i++) {
					count += (add[i]*net[l][m][i]);
				}
				
				sum[m] = leakyReLu(count);
			}
			
			add = sum.clone();
			
		}
		return sum[row];
	}
	
	private double createWeight() {
		Random r = new Random();
		return r.nextDouble();
	}
	
	private double leakyReLu(double d) {
		return d > 0 ? d : 0.01*d;
	}
	
	private double ReLUPrime(double d) {
		return d > 0 ? 1 : 0.01;
	}
	
	public String toString() {
		String s = "";
		for(int l=0;l<net.length;l++) {
			for (int m=0;m<net[l].length;m++) {
				for (int i=0;i <net[l][m].length;i++) {
					s = s + net[l][m][i] + "_";
				}
				s = s + "#";
			}
			s = s + "+";
		}
		return s;
	}
	
	
	public void print() {
		for(int l=0;l<net.length;l++) {
			for (int m=0;m<net[l].length;m++) {
				for (int i=0;i <net[l][m].length;i++) {
					System.out.print(net[l][m][i] + "|");
				}
				System.out.println("");
			}
			System.out.println("");
		}
	}
}
