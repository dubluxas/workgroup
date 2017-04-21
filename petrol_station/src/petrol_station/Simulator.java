package petrol_station;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulator {

	protected static int numOfsteps = 1440;
	// protected static double p = 0.02;
	// protected static double q = 0.02;
	protected static double t = 0.05;
	private int counter;
	private int e = 0;
	private static int step;
	private Station station;
	private Shop shop;
	private Random rnd;
	private double[][] array;
	private List<Double> values;
	private List<Double> information = new ArrayList<>();

	public static void main(String[] args) {

		Simulator s = new Simulator();
		s.simulate(numOfsteps);

	}

	public Simulator() {

		values = new ArrayList<>();
		// higher-quality random numbers than Random() class
		// Random() did not worked me in loop
		rnd = new SecureRandom();

	}

	// number of steps needed to run a program
	private void simulate(int num) {

		long start = System.currentTimeMillis();

		array = Functions.loadsettings(Functions.readSettings("text.txt", 5));
		// System.out.println(array);
		while (e < array.length) {

			if (step == num - 1 && e < array.length) {
				step = 0;
			}

			for (step = 0; step < num; step++) {
				simulateoneStep(array);
			}

			e++;
		}

		long end = System.currentTimeMillis();
		System.out.println();
		System.out.println("Execution time: " + (double) (end - start) / 1000 + " s.");
	}

	// Simulates one program step
	private void simulateoneStep(double[][] settings) {

		for (int x = 0; x < settings[0].length - 1;) {

			if (step == 0) {
				rnd.setSeed((int) settings[e][x]);
				saveValues(values, settings[e][x]);
			}

			double randomNumber = rnd.nextDouble();
			x++;

			if (station == null) {
				// System.out.println(array[e][x]);
				saveValues(values, settings[e][x]);
				station = new Station((int) settings[e][x]);

			}
			x++;

			if (shop == null) {
				saveValues(values, settings[e][x]);
				shop = new Shop((int) settings[e][x]);
			}
			x++;

			double p_ratio = settings[e][x];
			saveValues(values, settings[e][x]);
			x++;
			double q_ratio = settings[e][x];
			saveValues(values, settings[e][x]);

			if (randomNumber <= p_ratio) {
				station.getLeastOccupied().addtoQueue(new SmallCar());
				// System.out.println("small car" + step);
			}
			if (randomNumber >= p_ratio && randomNumber <= (p_ratio * 2)) {
				station.getLeastOccupied().addtoQueue(new Motorbike());
				// System.out.println("motobyke" + step);
			}
			if (randomNumber >= (2 * p_ratio) && randomNumber <= (p_ratio * 2) + q_ratio) {
				station.getLeastOccupied().addtoQueue(new FamilySedan());
				// System.out.println("family" + step);
			}
			if (station.getAllowTrucks()) {
				if (randomNumber >= ((2 * p_ratio) + q_ratio) && randomNumber <= (p_ratio * 2) + q_ratio + t) {
					station.getLeastOccupied().addtoQueue(new Truck());
					// System.out.println(step);
				}
			}

			station.topUp();

			shop.addCustomer(station);

			shop.pay(station.getDrivers(), station.getPumps(), step, 20);

			// testWait();

			// System.out.println();

			print(station, shop);

		}

	}

	public void print(Station sta, Shop sh) {

		if (step == (numOfsteps - 1)) {

			StringBuilder sb = new StringBuilder();
			sb.append("=========================\n");
			sb.append("Simulator Counter: ").append((e + 1)).append("\n");
			sb.append("Seed: ").append((int) values.get(0).doubleValue()).append("\n");
			sb.append("Pumps: ").append((int) values.get(1).doubleValue()).append(" Tills: ")
					.append((int) values.get(2).doubleValue());
			sb.append("\np cof: ").append(values.get(3)).append(" q cof: ").append(values.get(4));
			sb.append("\n=========================\n\n");
			sb.append("Lost Vehicles:\n");
			sb.append("MotoBike: ").append(sta.getLostVehiclesCount()[0]).append("\n");
			sb.append("Small Car: ").append(sta.getLostVehiclesCount()[1]).append("\n");
			sb.append("Family Sedan: ").append(sta.getLostVehiclesCount()[2]).append("\n");
			sb.append("Truck: ").append(sta.getLostVehiclesCount()[3]).append("\n");
			sb.append("Finance:");
			sb.append("\nEarned money ").append(Functions.round(String.valueOf(sh.getEarnedmoney())));
			sb.append("\nlost money: ").append(Functions.round(String.valueOf(sta.getLostmoney()))).append("\n");

			System.out.println(sb.toString());

			if (e == array.length - 1) {

				for (int i = 0; i < information.size() - 1; i += 3) {

					System.out.println("|seed:" + "|" + (int) information.get(i).doubleValue() + "|"
							+ Functions.round(String.valueOf(information.get(i + 1))) + "|"
							+ Functions.round(String.valueOf(information.get(i + 2))) + "|");

				}

			}

			try {
				getLostMoneyBySeed(array[e][0], information, station, shop);
				// Thread.sleep(500);
			} finally {
				clear();
			}

		}

	}

	private void getLostMoneyBySeed(double seed, List<Double> arr, Station sta, Shop sh) {

		double vehicle1Sum = 0;

		if (arr.contains(Double.valueOf(seed))) {

			double earnedSum = arr.get(arr.indexOf(Double.valueOf(seed)) + 1).doubleValue();
			double lostSum = arr.get(arr.indexOf(Double.valueOf(seed)) + 2).doubleValue();
			double earnedPerRound = sh.getEarnedmoney();
			double lostPerRound = sta.getLostmoney();
			earnedSum += earnedPerRound;
			lostSum += lostPerRound;
			arr.set(arr.indexOf(Double.valueOf(seed)) + 1, Double.valueOf(earnedSum));
			arr.set(arr.indexOf(Double.valueOf(seed)) + 2, Double.valueOf(lostSum));

		} else {

			Double s = Double.valueOf(seed);
			Double earnedSum = Double.valueOf(sh.getEarnedmoney());
			Double lostSum = Double.valueOf(sta.getLostmoney());
			arr.add(s);
			arr.add(earnedSum);
			arr.add(lostSum);

		}

		// if (e == array.length - 1) {
		// for (int i = 0; i < arr.size(); i++) {
		// System.out.println(arr.get(i));
		// }

		// }

		/*
		 * for (int i = 1; i < arr.size()-2;) { System.out.println(i);
		 * if(i!=1){i++;} Double earnResult =
		 * Double.valueOf(arr.get(i).doubleValue() / (arr.size() / 3));
		 * arr.set(i, earnResult); i++; Double lostResult =
		 * Double.valueOf(arr.get(i+1).doubleValue() / (arr.size() / 3));
		 * arr.set(i+1, lostResult);
		 * 
		 * 
		 * //System.out.println("lost " + lostResult);
		 * ///System.out.println("earned " + earnResult);
		 * 
		 * // System.out.println(i);
		 * 
		 * } }
		 */

	}

	// to slow down the function.
	public void testWait() {
		final long INTERVAL = 40;
		long start = System.nanoTime();
		long end = 0;
		do {
			end = System.nanoTime();
		} while (start + INTERVAL >= end);
		// System.out.println(end - start);
	}

	private void clear() {

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

}
