package petrol_station;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulator {

	protected static int numOfsteps = 1440;
	private int counter;
	private int simulationCount = 0;
	private static int step;
	private Station station;
	private Shop shop;
	private Random rnd;
	private double[][] array;
	private List<Double> values;
	private List<Double> information;
	private int[][] pumpsAndTills = { { 1, 1 }, { 1, 2 }, { 1, 4 }, { 2, 1 }, { 2, 2 }, { 2, 4 }, { 4, 1 }, { 4, 2 },
			{ 4, 4 } };

	public static void main(String[] args) {

		Simulator s = new Simulator();
		s.simulate(numOfsteps);

	}

	public Simulator() {

		values = new ArrayList<>();
		information = new ArrayList<>();
		// higher-quality random numbers than Random() class
		// Random() did not worked me in loop
		rnd = new SecureRandom();

	}

	// number of steps needed to run a program
	private void simulate(int num) {

		long start = System.currentTimeMillis();
		array = Functions.loadsettings(Functions.readSettings("text.txt", 5));

		// for (int y = 0; y < 10; y++) {

		while (simulationCount < array.length) {

			if (step == num - 1 && simulationCount < array.length) {
				step = 0;
			}

			for (step = 0; step < num; step++) {
				simulateoneStep(array);
			}

			simulationCount++;
		}

		// simulationCount = 0;

		// }

		long end = System.currentTimeMillis();
		System.out.println();
		System.out.println("Execution time: " + (double) (end - start) / 1000 + " s.");
	}

	private void simulateoneStep(double[][] settings) {

		for (int x = 0; x < settings[0].length - 1;) {

			double randomNumber = rnd.nextDouble();

			double currentpump = settings[simulationCount][x];
			if (station == null) {

				saveValues(values, currentpump);
				station = new Station((int) currentpump);
			}
			x++;
			double currenttill = settings[simulationCount][x];
			if (shop == null) {

				saveValues(values, currenttill);
				shop = new Shop((int) currenttill);
			}
			x++;
			double p_ratio = settings[simulationCount][x];
			saveValues(values, settings[simulationCount][x]);
			x++;
			double q_ratio = settings[simulationCount][x];
			saveValues(values, settings[simulationCount][x]);

			if (randomNumber <= p_ratio) {
				station.getLeastOccupied().addtoQueue(new SmallCar());
			}
			if (randomNumber >= p_ratio && randomNumber <= (p_ratio * 2)) {
				station.getLeastOccupied().addtoQueue(new Motorbike());
			}
			if (randomNumber >= (2 * p_ratio) && randomNumber <= (p_ratio * 2) + q_ratio) {
				station.getLeastOccupied().addtoQueue(new FamilySedan());
			}
			if (station.getAllowTrucks()) {
				if (randomNumber >= ((2 * p_ratio) + q_ratio)
						&& randomNumber <= (p_ratio * 2) + q_ratio/* + t */) {
					station.getLeastOccupied().addtoQueue(new Truck());
				}
			}

			station.topUp();
			shop.addCustomer(station);
			shop.pay(station.getDrivers(), station.getPumps(), step, 20);

			getStatistics(pumpsAndTills, values, information, station, shop, currentpump, currenttill);

		}

	}

	public void print(Station sta, Shop sh) {

		if (step == (numOfsteps - 1)) {

			StringBuilder sb = new StringBuilder();
			sb.append("=========================\n");
			sb.append("Simulator Counter: ").append((simulationCount + 1)).append("\n");
			// sb.append("Seed: ").append((int)
			// values.get(0).doubleValue()).append("\n");
			sb.append("Pumps: ").append((int) values.get(0).doubleValue()).append(" Tills: ")
					.append((int) values.get(1).doubleValue());
			sb.append("\np cof: ").append(values.get(2)).append(" q cof: ").append(values.get(3));
			sb.append("\n\n");
			sb.append("Lost Vehicles:\n");
			sb.append("MotoBike: ").append(sta.getLostVehiclesCount()[0]).append("\n");
			sb.append("Small Car: ").append(sta.getLostVehiclesCount()[1]).append("\n");
			sb.append("Family Sedan: ").append(sta.getLostVehiclesCount()[2]).append("\n");
			sb.append("Truck: ").append(sta.getLostVehiclesCount()[3]).append("\n");
			sb.append("Finance:");
			sb.append("\nEarned money ").append(Functions.round(String.valueOf(sh.getEarnedmoney())));
			sb.append("\nlost money: ").append(Functions.round(String.valueOf(sta.getLostmoney()))).append("\n");
			System.out.println("kk" + values.size());
			System.out.println(sb.toString());

			if (simulationCount == array.length - 1) {

				Object columns[] = { "Earned money", "Lost money" };
				System.out.println("================================");
				System.out.format("|%-12s|%-12s|%n", columns);
				System.out.println("================================");

				for (int i = 0; i < information.size() - 1; i += 3) {

					Object[] data = { information.get(i), information.get(i + 1) };
					System.out.format("|%-4.0f|%,-12.2f|%,-12.2f|%n", data);

				}

				System.out.println("================================");

			}

			clear();

		}

		// clear();

	}

	private void getStatistics(int[][] combinations, List<Double> togetfrom, List<Double> arrtosave, Station sta,
			Shop sh, double pump, double till) {

		if (step == (numOfsteps - 1)) {

	/*		if ((int) pump == combinations[0][0] && (int) till == combinations[0][1]) { // #
				if (arrtosave.contains(togetfrom.get(2))) {
					double earnedSum = arrtosave.get(arrtosave.indexOf(togetfrom.get(2))).doubleValue();
					double lostSum = arrtosave.get(arrtosave.indexOf(togetfrom.get(3))).doubleValue();
					double earnedPerRound = sh.getEarnedmoney();
					double lostPerRound = sta.getLostmoney();
					earnedSum += earnedPerRound;
					lostSum += lostPerRound;
					arrtosave.set(arrtosave.indexOf(togetfrom.get(2)), Double.valueOf(earnedSum));
					arrtosave.set(arrtosave.indexOf(togetfrom.get(3)), Double.valueOf(lostSum));
				} else {
					Double earnedSum = Double.valueOf(sh.getEarnedmoney());
					Double lostSum = Double.valueOf(sta.getLostmoney());
					try {
						Double[] data = { pump, till, earnedSum, lostSum };
						for (int i = 0; i < 4; i++) {
							arrtosave.add(i, data[i]);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				clear();
			}*/
			if ((int) pump == combinations[1][0] && (int) till == combinations[1][1]) {

			}

			if (pump == combinations[2][0] && till == combinations[2][1]) {

			}

			// ===================================================
			if (pump == combinations[3][0] && till == combinations[3][1]) {

			}
			if (pump == combinations[4][0] && till == combinations[4][1]) {

			}
			if (pump == combinations[5][0] && till == combinations[5][1]) {

			}

			// ===================================================
			if (pump == combinations[6][0] && till == combinations[6][1]) {

			}
			if (pump == combinations[7][0] && till == combinations[7][1]) {

			}
			//====================================================
			if ((int) pump == combinations[8][0] && (int) till == combinations[8][1]) { // #
				if (arrtosave.contains(togetfrom.get(2))) {
					double earnedSum = arrtosave.get(arrtosave.indexOf(togetfrom.get(2))).doubleValue();
					double lostSum = arrtosave.get(arrtosave.indexOf(togetfrom.get(3))).doubleValue();
					double earnedPerRound = sh.getEarnedmoney();
					double lostPerRound = sta.getLostmoney();
					earnedSum += earnedPerRound;
					lostSum += lostPerRound;
					arrtosave.set(arrtosave.indexOf(togetfrom.get(2)), Double.valueOf(earnedSum));
					arrtosave.set(arrtosave.indexOf(togetfrom.get(3)), Double.valueOf(lostSum));
				} else {
					Double earnedSum = Double.valueOf(sh.getEarnedmoney());
					Double lostSum = Double.valueOf(sta.getLostmoney());
					try {
						Double[] data = { pump, till, earnedSum, lostSum };
						for (int i = 0; i < 4; i++) {
							arrtosave.add(i, data[i]);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				clear();
			}
			// ===================================================

			if (simulationCount == (array.length - 1)) {
				
				System.out.println("Pump:" + arrtosave.get(0));
				System.out.println("Till:" + arrtosave.get(1));
				System.out.println("earned:" + arrtosave.get(2));
				System.out.println("lost:" + arrtosave.get(3));

			}

		}

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
