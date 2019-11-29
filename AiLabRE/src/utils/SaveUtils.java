package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import ai.main.NeuralNetwork;

public class SaveUtils {
	
	private final static String path = "theOne.net";
	private final static int[] struct = {4,4,4};
	private static NeuralNetwork net;
	
	public static NeuralNetwork getNeuralNet() {
		if (net != null) {
			load();
		}
		return net;
	}
	
	public static void load() {
		File f = new File(path);
		try {
			Scanner s = new Scanner(f);
			net = new NeuralNetwork(s.next());
			s.close();
		} catch (FileNotFoundException e) {
			net = new NeuralNetwork(struct);
		}
	}
	
	public static void save() {
		File f = new File(path);
		f.delete();
		try {
			BufferedWriter output = new BufferedWriter(new FileWriter(f, false));
			output.write(getNeuralNet().toString());
			output.close();
		} catch (IOException e) {
			System.err.println("Output failed: " + e.getMessage());
		}
	}

}
