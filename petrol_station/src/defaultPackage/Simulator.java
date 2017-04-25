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
	private int executionCount = 1;
	private int sameintructionCounter = 0;
	private double[][] array;
	private List<Double> values;
	private int[][] pumpsAndTills = { { 1, 1 }, { 1, 2 }, { 1, 4 }, { 2, 1 }, { 2, 2 }, { 2, 4 }, { 4, 1 }, { 4, 2 },
			{ 4, 4 } };
	private double[] earnedMoneyPerCombination = new double[pumpsAndTills.length];
	private double[] lostMoneyPerCombination = new double[pumpsAndTills.length];
	private double[] TotalearnedMoney = new double[pumpsAndTills.length];
	private double[] TotallostMoney = new double[pumpsAndTills.length];
	private double[][] ttttt = new double[pumpsAndTills.length][5];

	public static void main(String[] args) {

		Simulator s = new Simulator();
		s.simulate(numOfsteps);

	}

	public Simulator() {

		values = new ArrayList<>();
		rnd = new SecureRandom();
	}

	/**
	 * Simulate method is responsible to maintain all loops in simulation
	 * starting from outer: 1. For loop: How many times simulation is going to
	 * run. 2. While loop: pushes all combination from array. 3. For loop: how
	 * many steps simulation is going to run.
	 * 
	 * @param num
	 *            ticks/steps
	 */
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
					if (step == (numOfsteps - 1)) {
						getStatistics(values, pumpsAndTills, station, shop);
						printAllSimulations(station, shop);
						clear();
					}

				}
				simulationCount++;
			}

			getTotalStatistics();
			simulationCount = 0;
			executionCount++;
		}

		printStatistics();

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
		}
	}

	private void printAllSimulations(Station sta, Shop sh) {

		StringBuilder sb = new StringBuilder();
		sb.append("\nSimulator Counter: ").append((simulationCount + 1)).append("\n");
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
		sb.append("\nEarned money ").append(sh.getEarnedmoney());
		sb.append("\nlost money: ").append(sta.getLostmoney()).append("\n");
		System.out.println(sb.toString());

	}

	private void printStatistics() {

		Object columns[] = { "Pump/Till", "Earned money", "Lost money", "Net Gain" };
		printColumns(columns);
		for (int i = 0; i < earnedMoneyPerCombination.length; i++) {
			int[] l = pumpsAndTills[i].clone();
			Object[] data = { Integer.valueOf(l[0]), Integer.valueOf(l[1]), Double.valueOf((TotalearnedMoney[i])),
					Double.valueOf((TotallostMoney[i])), Double.valueOf(((TotalearnedMoney[i] - TotallostMoney[i]))) };
			System.out.format("|%-8s %s|%,-12.2f|%,-12.2f|%,-12.2f|%n", data);
		}
		printSeparator();

		Object columns1[] = { "Pump/Till", "p", "q", "net Gain" };
		printColumns(columns1);
		for (int y = 0; y < ttttt.length; y++) {
			Object[] data = { Double.valueOf(ttttt[y][0]), Double.valueOf(ttttt[y][1]), Double.valueOf(ttttt[y][2]),
					Double.valueOf(ttttt[y][3]), Double.valueOf(ttttt[y][4]) };
			System.out.format("|%-8.0f %.0f|%,-12.2f|%,-12.2f|%,-12.2f|%n", data);
		}
		printSeparator();

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
						if (ttttt[index][4] < (earnedPerRun - lostPerRun)) {
							double[] data = { combinations[x][y], combinations[x][y + 1],
									togetfrom.get(2).doubleValue(), togetfrom.get(3).doubleValue(),
									(earnedPerRun - lostPerRun) };
							for (int i = 0; i < ttttt[0].length; i++) {
								ttttt[index][i] = data[i];
							}
						}
						earnedMoneyPerCombination[index] += earnedPerRun;
						lostMoneyPerCombination[index] += lostPerRun;
						sameintructionCounter++;
					}
				}
				index++;
			}
			index = 0;
		}
	}

	private void getTotalStatistics() {
		// if ((simulationCount == (array.length - 1))) {
		for (int i = 0; i < earnedMoneyPerCombination.length; i++) {
			earnedMoneyPerCombination[i] = earnedMoneyPerCombination[i] / 25;
			lostMoneyPerCombination[i] = lostMoneyPerCombination[i] / 25;
		}
		for (int i = 0; i < TotalearnedMoney.length; i++) {
			TotalearnedMoney[i] += earnedMoneyPerCombination[i];
			TotallostMoney[i] += lostMoneyPerCombination[i];
		}
		Functions.clearArray(earnedMoneyPerCombination);
		Functions.clearArray(lostMoneyPerCombination);
		if (executionCount == 10) {
			for (int i = 0; i < earnedMoneyPerCombination.length; i++) {
				TotalearnedMoney[i] = TotalearnedMoney[i] / 10;
				TotallostMoney[i] = TotallostMoney[i] / 10;
			}
		}
	}

	private void clear() {
		values.clear();
		shop = null;
		station = null;
		counter = 0;
	}

	private void saveValues(List<Double> arr, double value) {
		if (counter < array[0].length) {
			arr.add(Double.valueOf(value));
			counter++;
		}
	}

	private void printColumns(Object[] obj) {
		printSeparator();
		System.out.format("|%-10s|%-12s|%-12s|%-12s|%n", obj);
		printSeparator();
	}

	private void printSeparator() {
		System.out.println("===================================================");
	}

}