package petrol_station;

import java.util.Map.Entry;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
				simulateoneStep();
			}

			e++;
		}

		long end = System.currentTimeMillis();
		System.out.println("Execution time: " + (end - start) / 1000 + "s");
	}

	// Simulates one program step
	private void simulateoneStep() {

		for (int x = 0; x < array[0].length - 1;) {

			if (step == 0) {
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

			double p_ratio = array[e][x];
			saveValues(values, array[e][x]);
			x++;
			double q_ratio = array[e][x];
			saveValues(values, array[e][x]);

			if (randomNumber <= p_ratio) {
				station.getLeastOccupied().addtoQueue(new SmallCar());
				// System.out.println("small car" + step);
			}
			if (randomNumber >= p_ratio && randomNumber <= (p_ratio * 2)) {
				station.getLeastOccupied().addtoQueue(new Motorbike());
				// System.out.println("motobyke" + step);
			}
			if (randomNumber >= (2 * p_ratio) && randomNumber <= (array[e][x] * 2) + q_ratio) {
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

		}

	}

	public void print() {

		if (step == (numOfsteps - 1)) {

			// I have used string builder not StringBuffer because it works
			// faster.
			// StringBuffer is synchronised, we do not need such a future.
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
			sb.append("\nlost money: ").append(Functions.round(getLostmoney())).append("\n");
			System.out.println(sb.toString());

			clear();

		}

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
