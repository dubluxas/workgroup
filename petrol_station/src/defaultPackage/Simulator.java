package defaultPackage;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import aston.group18.shop.Shop;
import aston.group18.station.Station;
import aston.group18.vehicle.FamilySedan;
import aston.group18.vehicle.Motorbike;
import aston.group18.vehicle.SmallCar;
import aston.group18.vehicle.Truck;

public class Simulator {

	private Station station;
	private Shop shop;
	private Random rnd;

	private static int step;
	protected static int numOfsteps = 1440;
	private int counter;
	private int simulationCount = 0;
	private int sameintructionCounter = 0;

	private double[][] array;
	private List<Double> values;
	private int[][] pumpsAndTills = { { 1, 1 }, { 1, 2 }, { 1, 4 }, { 2, 1 }, { 2, 2 }, { 2, 4 }, { 4, 1 }, { 4, 2 },
			{ 4, 4 } };
	double[] earnedMoneyPerCombination = new double[pumpsAndTills.length];
	double[] lostMoneyPerCombination = new double[pumpsAndTills.length];

	public static void main(String[] args) {

		Simulator s = new Simulator();
		s.simulate(numOfsteps);

	}

	public Simulator() {

		values = new ArrayList<>();
		rnd = new SecureRandom();
	}

	private void simulate(int num) {

		long start = System.currentTimeMillis();
		array = Functions.loadsettings(Functions.readSettings("text.txt", 5));

		for (int y = 0; y < 10; y++) {
			while (simulationCount < array.length) {
				if (step == num - 1 && simulationCount < array.length) {
					step = 0;
				}
				for (step = 0; step < num; step++) {
					simulateoneStep(array);
				}
				simulationCount++;
			}
			simulationCount = 0;
		}

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

			if (step == (numOfsteps - 1)) {
				print(station, shop);
				getStatistics(values, pumpsAndTills, station, shop);
				clear();
			}

		}

	}

	public void print(Station sta, Shop sh) {

		if (step == (numOfsteps - 1)) {

			StringBuilder sb = new StringBuilder();
			sb.append("=========================\n");
			sb.append("Simulator Counter: ").append((simulationCount + 1)).append("\n");
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
			System.out.println(sb.toString());

			if (simulationCount == array.length - 1) {

				Object columns[] = { "Pump/Till", "Earned money", "Lost money", "Net Gain" };
				System.out.println("===================================================");
				System.out.format("|%-10s|%-12s|%-12s|%-12s|%n", columns);
				System.out.println("===================================================");

			}

		}

	}

	private void getStatistics(List<Double> togetfrom, int[][] combinations, Station sta, Shop sh) {

		int index = 0;
		double earnedPerRun;
		double lostPerRun;

		if (step == (numOfsteps - 1)) {
			for (int x = 0; x < combinations.length; x++) {

				for (int y = 0; y < combinations[0].length; y += 2) {
					if (togetfrom.get(0).intValue() == combinations[x][y + 0]
							&& togetfrom.get(1).intValue() == combinations[x][y + 1]) {
						earnedPerRun = sh.getEarnedmoney();
						lostPerRun = sta.getLostmoney();
						earnedMoneyPerCombination[index] += earnedPerRun;
						lostMoneyPerCombination[index] += lostPerRun;
						sameintructionCounter++;
					}

				}

				index++;
			}

			index = 0;

			if (simulationCount == (array.length - 1)) {

				for (int i = 0; i < earnedMoneyPerCombination.length; i++) {

					int[] l = combinations[i].clone();
					Double[] data = { (double) l[0], (double) l[1], (earnedMoneyPerCombination[i] / 10),
							(lostMoneyPerCombination[i] / 10),
							((earnedMoneyPerCombination[i] - lostMoneyPerCombination[i]) / 10) };

					System.out.format("|%-6s %s|%,-12.2f|%,-12.2f|%,-12.2f|%n", data);

				}

				System.out.println("===================================================");

			}

		}

	}

	public void runSimulation(List<Double> togetfrom, List<Double> arrtosave, Shop sh, Station sta, double pump,
			double till) {
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
			// try {
			Double[] data = { Double.valueOf(pump), Double.valueOf(till), earnedSum, lostSum };
			for (int i = 0; i < 4; i++) {
				if (data.length <= 0) {
					arrtosave.set(i, data[i]);
				}
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
