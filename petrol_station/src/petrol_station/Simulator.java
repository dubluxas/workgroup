package petrol_station;	

import java.util.Map.Entry;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulator {

	protected static int numOfsteps = 1440;
	protected static double p = 0.2;
	protected static double q = 0.2;
	protected static double t = 0.5;
	// protected static int tillCount = 4;//
	// protected static int pumpCount = 4;
	private Station station;
	private int counter;
	private static int step;
	private Random rnd;
	private Shop shop; // pumps, tills, p
	private double array[][] = { { 10, 1, 1, 0.1 }, { 10, 1, 1, 0.2 }, { 10, 1, 1, 0.3 }, { 10, 1, 1, 0.4 }, { 10, 1, 1, 0.5 },
								 { 10, 1, 2, 0.1 }, { 10, 1, 2, 0.2 }, { 10, 1, 2, 0.3 }, { 10, 1, 2, 0.4 }, { 10, 1, 2, 0.5 },
								 { 10, 1, 4, 0.1 }, { 10, 1, 4, 0.2 }, { 10, 1, 4, 0.3 }, { 10, 1, 4, 0.4 }, { 10, 1, 4, 0.5 },
								 { 10, 2, 1, 0.1 }, { 10, 2, 1, 0.2 }, { 10, 2, 1, 0.3 }, { 10, 2, 1, 0.4 }, { 10, 2, 1, 0.5 },
								 { 10, 2, 2, 0.1 }, { 10, 2, 2, 0.2 }, { 10, 2, 2, 0.3 }, { 10, 2, 2, 0.4 }, { 10, 2, 2, 0.5 },
								 { 10, 2, 4, 0.1 }, { 10, 2, 4, 0.2 }, { 10, 2, 4, 0.3 }, { 10, 2, 4, 0.4 }, { 10, 2, 4, 0.5 },
								 { 10, 4, 1, 0.1 }, { 10, 4, 1, 0.2 }, { 10, 4, 1, 0.3 }, { 10, 4, 1, 0.4 }, { 10, 4, 1, 0.5 },
								 { 10, 4, 2, 0.1 }, { 10, 4, 2, 0.2 }, { 10, 4, 2, 0.3 }, { 10, 4, 2, 0.4 }, { 10, 4, 2, 0.5 },
								 { 10, 4, 4, 0.1 }, { 10, 4, 4, 0.2 }, { 10, 4, 4, 0.3 }, { 10, 4, 4, 0.4 }, { 10, 4, 4, 0.5 },
			
								 { 15, 1, 1, 0.1 }, { 15, 1, 1, 0.2 }, { 15, 1, 1, 0.3 }, { 15, 1, 1, 0.4 }, { 15, 1, 1, 0.5 },
			 					 { 15, 1, 2, 0.1 }, { 15, 1, 2, 0.2 }, { 15, 1, 2, 0.3 }, { 15, 1, 2, 0.4 }, { 15, 1, 2, 0.5 },
			 					 { 15, 1, 4, 0.1 }, { 15, 1, 4, 0.2 }, { 15, 1, 4, 0.3 }, { 15, 1, 4, 0.4 }, { 15, 1, 4, 0.5 },
			 					 { 15, 2, 1, 0.1 }, { 15, 2, 1, 0.2 }, { 15, 2, 1, 0.3 }, { 15, 2, 1, 0.4 }, { 15, 2, 1, 0.5 },
			 					 { 15, 2, 2, 0.1 }, { 15, 2, 2, 0.2 }, { 15, 2, 2, 0.3 }, { 15, 2, 2, 0.4 }, { 15, 2, 2, 0.5 },
			 					 { 15, 2, 4, 0.1 }, { 15, 2, 4, 0.2 }, { 15, 2, 4, 0.3 }, { 15, 2, 4, 0.4 }, { 15, 2, 4, 0.5 },
			 					 { 15, 4, 1, 0.1 }, { 15, 4, 1, 0.2 }, { 15, 4, 1, 0.3 }, { 15, 4, 1, 0.4 }, { 15, 4, 1, 0.5 },
			 					 { 15, 4, 2, 0.1 }, { 15, 4, 2, 0.2 }, { 15, 4, 2, 0.3 }, { 15, 4, 2, 0.4 }, { 15, 4, 2, 0.5 },
			 					 { 15, 4, 4, 0.1 },	{ 15, 4, 4, 0.2 }, { 15, 4, 4, 0.3 }, { 15, 4, 4, 0.4 }, { 15, 4, 4, 0.5 },
			 					 
			 					 { 20, 1, 1, 0.1 }, { 20, 1, 1, 0.2 }, { 20, 1, 1, 0.3 }, { 20, 1, 1, 0.4 }, { 20, 1, 1, 0.5 },
								 { 20, 1, 2, 0.1 }, { 20, 1, 2, 0.2 }, { 20, 1, 2, 0.3 }, { 20, 1, 2, 0.4 }, { 20, 1, 2, 0.5 },
								 { 20, 1, 4, 0.1 }, { 20, 1, 4, 0.2 }, { 20, 1, 4, 0.3 }, { 20, 1, 4, 0.4 }, { 20, 1, 4, 0.5 },
								 { 20, 2, 1, 0.1 }, { 20, 2, 1, 0.2 }, { 20, 2, 1, 0.3 }, { 20, 2, 1, 0.4 }, { 20, 2, 1, 0.5 },
								 { 20, 2, 2, 0.1 }, { 20, 2, 2, 0.2 }, { 20, 2, 2, 0.3 }, { 20, 2, 2, 0.4 }, { 20, 2, 2, 0.5 },
								 { 20, 2, 4, 0.1 }, { 20, 2, 4, 0.2 }, { 20, 2, 4, 0.3 }, { 20, 2, 4, 0.4 }, { 20, 2, 4, 0.5 },
								 { 20, 4, 1, 0.1 }, { 20, 4, 1, 0.2 }, { 20, 4, 1, 0.3 }, { 20, 4, 1, 0.4 }, { 20, 4, 1, 0.5 },
								 { 20, 4, 2, 0.1 }, { 20, 4, 2, 0.2 }, { 20, 4, 2, 0.3 }, { 20, 4, 2, 0.4 }, { 20, 4, 2, 0.5 },
								 { 20, 4, 4, 0.1 },	{ 20, 4, 4, 0.2 }, { 20, 4, 4, 0.3 }, { 20, 4, 4, 0.4 }, { 20, 4, 4, 0.5 },
								 
								 { 25, 1, 1, 0.1 }, { 25, 1, 1, 0.2 }, { 25, 1, 1, 0.3 }, { 25, 1, 1, 0.4 }, { 25, 1, 1, 0.5 },
								 { 25, 1, 2, 0.1 }, { 25, 1, 2, 0.2 }, { 25, 1, 2, 0.3 }, { 25, 1, 2, 0.4 }, { 25, 1, 2, 0.5 },
								 { 25, 1, 4, 0.1 }, { 25, 1, 4, 0.2 }, { 25, 1, 4, 0.3 }, { 25, 1, 4, 0.4 }, { 25, 1, 4, 0.5 },
								 { 25, 2, 1, 0.1 }, { 25, 2, 1, 0.2 }, { 25, 2, 1, 0.3 }, { 25, 2, 1, 0.4 }, { 25, 2, 1, 0.5 },
								 { 25, 2, 2, 0.1 }, { 25, 2, 2, 0.2 }, { 25, 2, 2, 0.3 }, { 25, 2, 2, 0.4 }, { 25, 2, 2, 0.5 },
								 { 25, 2, 4, 0.1 }, { 25, 2, 4, 0.2 }, { 25, 2, 4, 0.3 }, { 25, 2, 4, 0.4 }, { 25, 2, 4, 0.5 },
								 { 25, 4, 1, 0.1 }, { 25, 4, 1, 0.2 }, { 25, 4, 1, 0.3 }, { 25, 4, 1, 0.4 }, { 25, 4, 1, 0.5 },
								 { 25, 4, 2, 0.1 }, { 25, 4, 2, 0.2 }, { 25, 4, 2, 0.3 }, { 25, 4, 2, 0.4 }, { 25, 4, 2, 0.5 },
								 { 25, 4, 4, 0.1 },	{ 25, 4, 4, 0.2 }, { 25, 4, 4, 0.3 }, { 25, 4, 4, 0.4 }, { 25, 4, 4, 0.5 },
								 
								 { 30, 1, 1, 0.1 }, { 30, 1, 1, 0.2 }, { 30, 1, 1, 0.3 }, { 30, 1, 1, 0.4 }, { 30, 1, 1, 0.5 },
								 { 30, 1, 2, 0.1 }, { 30, 1, 2, 0.2 }, { 30, 1, 2, 0.3 }, { 30, 1, 2, 0.4 }, { 30, 1, 2, 0.5 },
								 { 30, 1, 4, 0.1 }, { 30, 1, 4, 0.2 }, { 30, 1, 4, 0.3 }, { 30, 1, 4, 0.4 }, { 30, 1, 4, 0.5 },
								 { 30, 2, 1, 0.1 }, { 30, 2, 1, 0.2 }, { 30, 2, 1, 0.3 }, { 30, 2, 1, 0.4 }, { 30, 2, 1, 0.5 },
								 { 30, 2, 2, 0.1 }, { 30, 2, 2, 0.2 }, { 30, 2, 2, 0.3 }, { 30, 2, 2, 0.4 }, { 30, 2, 2, 0.5 },
								 { 30, 2, 4, 0.1 }, { 30, 2, 4, 0.2 }, { 30, 2, 4, 0.3 }, { 30, 2, 4, 0.4 }, { 30, 2, 4, 0.5 },
								 { 30, 4, 1, 0.1 }, { 30, 4, 1, 0.2 }, { 30, 4, 1, 0.3 }, { 30, 4, 1, 0.4 }, { 30, 4, 1, 0.5 },
								 { 30, 4, 2, 0.1 }, { 30, 4, 2, 0.2 }, { 30, 4, 2, 0.3 }, { 30, 4, 2, 0.4 }, { 30, 4, 2, 0.5 },
								 { 30, 4, 4, 0.1 },	{ 30, 4, 4, 0.2 }, { 30, 4, 4, 0.3 }, { 30, 4, 4, 0.4 }, { 30, 4, 4, 0.5 },
								 
								 { 35, 1, 1, 0.1 }, { 35, 1, 1, 0.2 }, { 35, 1, 1, 0.3 }, { 35, 1, 1, 0.4 }, { 35, 1, 1, 0.5 },
								 { 35, 1, 2, 0.1 }, { 35, 1, 2, 0.2 }, { 35, 1, 2, 0.3 }, { 35, 1, 2, 0.4 }, { 35, 1, 2, 0.5 },
								 { 35, 1, 4, 0.1 }, { 35, 1, 4, 0.2 }, { 35, 1, 4, 0.3 }, { 35, 1, 4, 0.4 }, { 35, 1, 4, 0.5 },
								 { 35, 2, 1, 0.1 }, { 35, 2, 1, 0.2 }, { 35, 2, 1, 0.3 }, { 35, 2, 1, 0.4 }, { 35, 2, 1, 0.5 },
								 { 35, 2, 2, 0.1 }, { 35, 2, 2, 0.2 }, { 35, 2, 2, 0.3 }, { 35, 2, 2, 0.4 }, { 35, 2, 2, 0.5 },
								 { 35, 2, 4, 0.1 }, { 35, 2, 4, 0.2 }, { 35, 2, 4, 0.3 }, { 35, 2, 4, 0.4 }, { 35, 2, 4, 0.5 },
								 { 35, 4, 1, 0.1 }, { 35, 4, 1, 0.2 }, { 35, 4, 1, 0.3 }, { 35, 4, 1, 0.4 }, { 35, 4, 1, 0.5 },
								 { 35, 4, 2, 0.1 }, { 35, 4, 2, 0.2 }, { 35, 4, 2, 0.3 }, { 35, 4, 2, 0.4 }, { 35, 4, 2, 0.5 },
								 { 35, 4, 4, 0.1 },	{ 35, 4, 4, 0.2 }, { 35, 4, 4, 0.3 }, { 35, 4, 4, 0.4 }, { 35, 4, 4, 0.5 },
								 
								 { 40, 1, 1, 0.1 }, { 40, 1, 1, 0.2 }, { 40, 1, 1, 0.3 }, { 40, 1, 1, 0.4 }, { 40, 1, 1, 0.5 },
								 { 40, 1, 2, 0.1 }, { 40, 1, 2, 0.2 }, { 40, 1, 2, 0.3 }, { 40, 1, 2, 0.4 }, { 40, 1, 2, 0.5 },
								 { 40, 1, 4, 0.1 }, { 40, 1, 4, 0.2 }, { 40, 1, 4, 0.3 }, { 40, 1, 4, 0.4 }, { 40, 1, 4, 0.5 },
								 { 40, 2, 1, 0.1 }, { 40, 2, 1, 0.2 }, { 40, 2, 1, 0.3 }, { 40, 2, 1, 0.4 }, { 40, 2, 1, 0.5 },
								 { 40, 2, 2, 0.1 }, { 40, 2, 2, 0.2 }, { 40, 2, 2, 0.3 }, { 40, 2, 2, 0.4 }, { 40, 2, 2, 0.5 },
								 { 40, 2, 4, 0.1 }, { 40, 2, 4, 0.2 }, { 40, 2, 4, 0.3 }, { 40, 2, 4, 0.4 }, { 40, 2, 4, 0.5 },
								 { 40, 4, 1, 0.1 }, { 40, 4, 1, 0.2 }, { 40, 4, 1, 0.3 }, { 40, 4, 1, 0.4 }, { 40, 4, 1, 0.5 },
								 { 40, 4, 2, 0.1 }, { 40, 4, 2, 0.2 }, { 40, 4, 2, 0.3 }, { 40, 4, 2, 0.4 }, { 40, 4, 2, 0.5 },
								 { 40, 4, 4, 0.1 },	{ 40, 4, 4, 0.2 }, { 40, 4, 4, 0.3 }, { 40, 4, 4, 0.4 }, { 40, 4, 4, 0.5 },
								 
								 { 45, 1, 1, 0.1 }, { 45, 1, 1, 0.2 }, { 45, 1, 1, 0.3 }, { 45, 1, 1, 0.4 }, { 45, 1, 1, 0.5 },
								 { 45, 1, 2, 0.1 }, { 45, 1, 2, 0.2 }, { 45, 1, 2, 0.3 }, { 45, 1, 2, 0.4 }, { 45, 1, 2, 0.5 },
								 { 45, 1, 4, 0.1 }, { 45, 1, 4, 0.2 }, { 45, 1, 4, 0.3 }, { 45, 1, 4, 0.4 }, { 45, 1, 4, 0.5 },
								 { 45, 2, 1, 0.1 }, { 45, 2, 1, 0.2 }, { 45, 2, 1, 0.3 }, { 45, 2, 1, 0.4 }, { 45, 2, 1, 0.5 },
								 { 45, 2, 2, 0.1 }, { 45, 2, 2, 0.2 }, { 45, 2, 2, 0.3 }, { 45, 2, 2, 0.4 }, { 45, 2, 2, 0.5 },
								 { 45, 2, 4, 0.1 }, { 45, 2, 4, 0.2 }, { 45, 2, 4, 0.3 }, { 45, 2, 4, 0.4 }, { 45, 2, 4, 0.5 },
								 { 45, 4, 1, 0.1 }, { 45, 4, 1, 0.2 }, { 45, 4, 1, 0.3 }, { 45, 4, 1, 0.4 }, { 45, 4, 1, 0.5 },
								 { 45, 4, 2, 0.1 }, { 45, 4, 2, 0.2 }, { 45, 4, 2, 0.3 }, { 45, 4, 2, 0.4 }, { 45, 4, 2, 0.5 },
								 { 45, 4, 4, 0.1 },	{ 45, 4, 4, 0.2 }, { 45, 4, 4, 0.3 }, { 45, 4, 4, 0.4 }, { 45, 4, 4, 0.5 },
								 
								 { 50, 1, 1, 0.1 }, { 50, 1, 1, 0.2 }, { 50, 1, 1, 0.3 }, { 50, 1, 1, 0.4 }, { 50, 1, 1, 0.5 },
								 { 50, 1, 2, 0.1 }, { 50, 1, 2, 0.2 }, { 50, 1, 2, 0.3 }, { 50, 1, 2, 0.4 }, { 50, 1, 2, 0.5 },
								 { 50, 1, 4, 0.1 }, { 50, 1, 4, 0.2 }, { 50, 1, 4, 0.3 }, { 50, 1, 4, 0.4 }, { 50, 1, 4, 0.5 },
								 { 50, 2, 1, 0.1 }, { 50, 2, 1, 0.2 }, { 50, 2, 1, 0.3 }, { 50, 2, 1, 0.4 }, { 50, 2, 1, 0.5 },
								 { 50, 2, 2, 0.1 }, { 50, 2, 2, 0.2 }, { 50, 2, 2, 0.3 }, { 50, 2, 2, 0.4 }, { 50, 2, 2, 0.5 },
								 { 50, 2, 4, 0.1 }, { 50, 2, 4, 0.2 }, { 50, 2, 4, 0.3 }, { 50, 2, 4, 0.4 }, { 50, 2, 4, 0.5 },
								 { 50, 4, 1, 0.1 }, { 50, 4, 1, 0.2 }, { 50, 4, 1, 0.3 }, { 50, 4, 1, 0.4 }, { 50, 4, 1, 0.5 },
								 { 50, 4, 2, 0.1 }, { 50, 4, 2, 0.2 }, { 50, 4, 2, 0.3 }, { 50, 4, 2, 0.4 }, { 50, 4, 2, 0.5 },
								 { 50, 4, 4, 0.1 }, { 50, 4, 4, 0.2 }, { 50, 4, 4, 0.3 }, { 50, 4, 4, 0.4 }, { 50, 4, 4, 0.5 },
								 
								 { 55, 1, 1, 0.1 }, { 55, 1, 1, 0.2 }, { 55, 1, 1, 0.3 }, { 55, 1, 1, 0.4 }, { 55, 1, 1, 0.5 },
								 { 55, 1, 2, 0.1 }, { 55, 1, 2, 0.2 }, { 55, 1, 2, 0.3 }, { 55, 1, 2, 0.4 }, { 55, 1, 2, 0.5 },
								 { 55, 1, 4, 0.1 }, { 55, 1, 4, 0.2 }, { 55, 1, 4, 0.3 }, { 55, 1, 4, 0.4 }, { 55, 1, 4, 0.5 },
								 { 55, 2, 1, 0.1 }, { 55, 2, 1, 0.2 }, { 55, 2, 1, 0.3 }, { 55, 2, 1, 0.4 }, { 55, 2, 1, 0.5 },
								 { 55, 2, 2, 0.1 }, { 55, 2, 2, 0.2 }, { 55, 2, 2, 0.3 }, { 55, 2, 2, 0.4 }, { 55, 2, 2, 0.5 },
								 { 55, 2, 4, 0.1 }, { 55, 2, 4, 0.2 }, { 55, 2, 4, 0.3 }, { 55, 2, 4, 0.4 }, { 55, 2, 4, 0.5 },
								 { 55, 4, 1, 0.1 }, { 55, 4, 1, 0.2 }, { 55, 4, 1, 0.3 }, { 55, 4, 1, 0.4 }, { 55, 4, 1, 0.5 },
								 { 55, 4, 2, 0.1 }, { 55, 4, 2, 0.2 }, { 55, 4, 2, 0.3 }, { 55, 4, 2, 0.4 }, { 55, 4, 2, 0.5 },
								 { 55, 4, 4, 0.1 }, { 55, 4, 4, 0.2 }, { 55, 4, 4, 0.3 }, { 55, 4, 4, 0.4 }, { 55, 4, 4, 0.5 },

	};

	private List<Double> values;

	int e = 0;

	public static void main(String[] args) {
		
		Simulator s = new Simulator();
		s.simulate(numOfsteps);

	}

	public Simulator() {
		
		values = new ArrayList<>();
		//higher-quality random numbers than Random() class
		//Random() did not worked me in loop
		rnd = new SecureRandom();
		
		
	}

	// number of steps needed to run a program
	private void simulate(int num) {
		
		long start = System.currentTimeMillis();
	  
		while (e < array.length) {

			if (step == num - 1 && e < array.length) {
				step = 0;
			}

			for (step = 0; step < num; step++) {
				simulateoneStep();
			}

			e++;
		}
		
		 long end = System.currentTimeMillis();
		 System.out.println("Execution time: " + (end - start)/1000 + "s");
	}

	// Simulates one program step
	private void simulateoneStep() {

		for (int x = 0; x < array[0].length-1;) {
			
			if(step == 0){
				rnd.setSeed((int) array[e][x]);
				saveValues(values, array[e][x]);
			}
			
			double randomNumber = rnd.nextDouble();
			x++;

			if (station == null) {
				// System.out.println(array[e][x]);
				saveValues(values, array[e][x]);
				station = new Station((int) array[e][x]);

			}
			x++;

			if (shop == null) {
				saveValues(values, array[e][x]);
				shop = new Shop((int) array[e][x]);
			}
			x++;
			
			//generates random number
			
			
			//System.out.println(randomNumber);
			saveValues(values, array[e][x]);

			if (randomNumber <= array[e][x]) {
				station.getLeastOccupied().addtoQueue(new SmallCar());
				//System.out.println("small car" + step);
			}  
			if (randomNumber >= array[e][x] && randomNumber <= (array[e][x] * 2)) {
				station.getLeastOccupied().addtoQueue(new Motorbike());
				//System.out.println("motobyke" + step);
			}  
			if (randomNumber >= (2 * array[e][x]) && randomNumber <= (array[e][x] * 2) + q) {
				station.getLeastOccupied().addtoQueue(new FamilySedan());
				//System.out.println("family" + step);
			}  
			if (station.getAllowTrucks()) {
				if (randomNumber >= ((2 * array[e][x]) + q) && randomNumber <= (array[e][x] * 2) + q + t) {
					station.getLeastOccupied().addtoQueue(new Truck());
					//System.out.println(step);
				}
			} 

			station.topUp();

			shop.addCustomer(station);

			shop.pay(station.getCustomers(), station.getPumps(), step, 20);

			print();
		
			if (step == numOfsteps - 1) {
				
				shop = null;
				station = null;
				
				counter = 0;
							
				
			}

		}

	}

	public void print() {

		if (step == (numOfsteps-1)) {

			// I have used string builder not StringBuffer because it works
			// faster.
			// StringBuffer is synchronised, we do not need such a future.
			StringBuilder sb = new StringBuilder();
			sb.append("=========================\n");
			sb.append("Simulation number: ").append((e + 1)).append("\n");
			sb.append("Seed: ").append(values.get(0)).append("\n");
			sb.append("Pumps: ").append(values.get(1)).append(" Tills: ").append(values.get(2)).append(" p cof: ")
					.append(values.get(3));
			sb.append("\n=========================\n\n");
			sb.append(getLostVehicles());
			sb.append("Finance:");
			sb.append("\nEarned money ").append(Functions.round(shop.toString()));
			sb.append("\nlost money: ").append(Functions.round(getLostmoney())).append("\n");
			System.out.println(sb.toString());

			clear();

		}

	}
	private void clear(){
		
		values.clear();
		station.clear();
		shop.clear();
		values.clear();
		shop = null;
		station = null;
		counter = 0;
	}

	@SuppressWarnings("boxing")
	private void saveValues(List<Double> arr, double value) {

		if (counter < array[0].length) {
			arr.add(value); 
			counter++;
		}
	}

	@SuppressWarnings("boxing")
	private String getLostmoney() {

		double sum = 0.00;

		for (Entry<Vehicle, Double> m : station.getLostVehicles().entrySet()) {

			sum += m.getValue();

		}

		return String.valueOf(sum);

	}

	private String getLostVehicles() {

		int w = 0;
		int x = 0;
		int y = 0;
		int z = 0;

		StringBuffer sb = new StringBuffer("Lost Vehicles:\n");

		for (Entry<Vehicle, Double> m : station.getLostVehicles().entrySet()) {

			if (m.getKey() instanceof Motorbike) {
				w++;
			}
			if (m.getKey() instanceof SmallCar) {
				x++;
			}
			if (m.getKey() instanceof FamilySedan) {
				y++;
			}
			if (m.getKey() instanceof Truck) {
				z++;
			}

		}
		
		sb.append("Motorbike: ").append(w).append("\n");
		sb.append("Small Car: ").append(x).append("\n");
		sb.append("Family Sedan: ").append(y).append("\n");
		sb.append("Truck: ").append(z).append("\n");

		return sb.toString();

	}

}
