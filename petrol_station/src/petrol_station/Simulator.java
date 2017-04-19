package petrol_station;

import java.util.Map.Entry;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Simulator {

	protected static int numOfsteps = 1440;
	protected static double p = 0.02;
	protected static double q = 0.02;
	protected static double t = 0.05;
	private int counter;
	int e = 0;
	private static int step;

	private Station station;
	private Shop shop;

	private Random rnd;

	private double[][] array;
	private List<Double> values;

	private double lostmoney;

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

		array = Functions.loadsettings(Functions.readSettings());
		System.out.println(array);
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

			shop.pay(station.getCustomers(), station.getPumps(), step, 20);

			print();

			// testWait();

		}

	}

	public void print() {
		
		//if (e == array.length-1) {
		//	System.out.println(lostmoney);
		//}


		if (step == (numOfsteps - 1)) {

			// BufferedWriter log = new BufferedWriter(new
			// OutputStreamWriter(System.out));
			StringBuilder sb = new StringBuilder();
			sb.append("=========================\n");
			sb.append("Simulator Counter: ").append((e + 1)).append("\n");
			sb.append("Seed: ").append(values.get(0)).append("\n");
			sb.append("Pumps: ").append(values.get(1)).append(" Tills: ").append(values.get(2));
			sb.append("\np cof: ").append(values.get(3)).append(" q cof: ").append(values.get(4));
			sb.append("\n=========================\n\n");
			sb.append(getLostVehicles());
			sb.append("Finance:");
			sb.append("\nEarned money ").append(Functions.round(shop.toString()));
			sb.append("\nlost money: ").append(Functions.round(String.valueOf(getLostmoney()))).append("\n");

			if (values.get(0).doubleValue() == 10) {

				lostmoney = lostmoney + getLostmoney();
				System.out.println(lostmoney);

			}
			
			if (values.get(0).doubleValue() == 20) {

				lostmoney = lostmoney + getLostmoney();
				System.out.println(lostmoney);

			}
			
			if (values.get(0).doubleValue() == 30) {

				lostmoney = lostmoney + getLostmoney();
				System.out.println(lostmoney);

			}
			
			System.out.println(sb.toString());

			clear();

		}
		
		

	}

	// test
	public void testWait() {
		final long INTERVAL = 8000;
		long start = System.nanoTime();
		long end = 0;
		do {
			end = System.nanoTime();
		} while (start + INTERVAL >= end);
		// System.out.println(end - start);
	}

	private void clear() {

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

	private double getLostmoney() {

		double sum = 0.00;
		sum = station.getLostVehicles().entrySet().stream().map(Map.Entry::getValue).mapToInt(Number::intValue).sum();
		return sum;

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
