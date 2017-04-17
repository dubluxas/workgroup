package petrol_station;

import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulator {

	protected static int numOfsteps = 1440;
	protected static double p = 0.2;
	protected static double q = 0.2;
	protected static double t = 0.5;
	// protected static int tillCount = 4;
	// protected static int pumpCount = 4;
	private Station station;
	private int seed;
	private int counter;
	private static int step;
	private Shop shop; // pumps, tills, p
	private double array[][] = { { 1, 1, 0.1 }, { 1, 1, 0.2 }, { 1, 1, 0.3 }, { 1, 1, 0.4 }, { 1, 1, 0.5 },
			{ 1, 2, 0.1 }, { 1, 2, 0.2 }, { 1, 2, 0.3 }, { 1, 2, 0.4 }, { 1, 3, 0.5 }, { 1, 4, 0.1 }, { 1, 4, 0.2 },
			{ 1, 4, 0.3 }, { 1, 4, 0.4 }, { 1, 4, 0.5 }, { 2, 1, 0.1 }, { 2, 1, 0.2 }, { 2, 1, 0.3 }, { 2, 1, 0.4 },
			{ 2, 1, 0.5 }, { 2, 2, 0.1 }, { 2, 2, 0.2 }, { 2, 2, 0.3 }, { 2, 2, 0.4 }, { 2, 2, 0.5 }, { 2, 4, 0.1 },
			{ 2, 4, 0.2 }, { 2, 4, 0.3 }, { 2, 4, 0.4 }, { 2, 4, 0.5 }, { 4, 1, 0.1 }, { 4, 1, 0.2 }, { 4, 1, 0.3 },
			{ 4, 1, 0.4 }, { 4, 1, 0.5 }, { 4, 2, 0.1 }, { 4, 2, 0.2 }, { 4, 2, 0.3 }, { 4, 2, 0.4 }, { 4, 2, 0.5 },
			{ 4, 4, 0.1 }, { 4, 4, 0.2 }, { 4, 4, 0.3 }, { 4, 4, 0.4 }, { 4, 4, 0.5 },

	};

	private List<Double> values;

	int e = 0;

	public static void main(String[] args) {

		int seed = 42; // By default, use a seed of 42
		Simulator s = new Simulator(seed);
		s.simulate(numOfsteps);

	}

	public Simulator(int seed) {
		this.seed = seed;
		values = new ArrayList<>();
	}

	// number of steps needed to run a program
	private void simulate(int num) {

		while (e <= array.length - 1) {

			if (step == num - 1 && e < array.length) {
				step = 0;
			}

			for (step = 0; step < num; step++) {
				simulateoneStep(seed);
			}

			e++;
		}
	}

	// Simulates one program step
	private void simulateoneStep(int seed) {

		// System.out.println(e);
		for (int x = 0; x < array[0].length - 1;) {

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
			Random rnd = new Random();
			double randomNumber = rnd.nextDouble();

			saveValues(values, array[e][x]);

			if (randomNumber <= array[e][x]) {
				station.getLeastOccupied().addtoQueue(new SmallCar());
			} // else
			if (randomNumber >= array[e][x] && randomNumber <= (array[e][x] * 2)) {
				station.getLeastOccupied().addtoQueue(new Motorbike());
			} // else
			if (randomNumber >= (2 * array[e][x]) && randomNumber <= (array[e][x] * 2) + q) {
				station.getLeastOccupied().addtoQueue(new FamilySedan());
			} // else
			if (station.getAllowTrucks()) {
				if (randomNumber >= ((2 * array[e][x]) + q) && randomNumber <= (array[e][x] * 2) + q + t) {
					station.getLeastOccupied().addtoQueue(new Truck());
				}
			} else {
				System.out.print("");
			}

			station.topUp();

			shop.addCustomer(station);

			shop.pay(station.getCustomers(), station.getPumps(), step, 10);

			print();

			if (step == numOfsteps - 1) {
				shop = null;
				station = null;
				counter = 0;
			}

		}

	}

	public void print() {

		if (step == (numOfsteps - 1)) {

			// I have used string builder not StringBuffer because it works
			// faster.
			// StringBuffer is synchronised, we do not need such a future.
			StringBuilder sb = new StringBuilder();
			sb.append("=========================\n");
			sb.append("Simulation number: ").append((e + 1)).append("\n");
			sb.append("Pumps: ").append(values.get(0)).append(" Tills: ").append(values.get(1)).append(" p cof: ")
					.append(values.get(2));
			sb.append("\n=========================\n\n");
			sb.append(getLostVehicles());
			sb.append("\nEarned money ").append(Functions.round(shop.toString()));
			sb.append("\nlost money: ").append(Functions.round(getLostmoney())).append("\n");
			System.out.println(sb.toString());

			values.clear();

		}

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
