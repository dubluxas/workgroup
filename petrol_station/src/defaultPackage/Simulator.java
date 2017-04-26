/**
 * 
 * @author Justas Petrusonis
 */
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
	private static int numOfsteps = 1440;
	private int simulationCount = 0;
	private int executionCount = 1;
	private double[][] combinations;
	private List<Double> values;
	private final int[][] pumpsAndTills = { { 1, 1 }, { 1, 2 }, { 1, 4 }, { 2, 1 }, { 2, 2 }, { 2, 4 }, { 4, 1 },
			{ 4, 2 }, { 4, 4 } };
	private double[] earnedMoneyPerCombination;
	private double[] lostMoneyPerCombination;
	private double[] TotalearnedMoney;
	private double[] TotallostMoney;
	private double[][] bestCombinations;

	public static void main(String[] args) {

		System.out.println("Waiting for results.");
		System.out.println("Aprox time 7s.");

		Simulator s = new Simulator();
		s.simulate(numOfsteps);

	}

	public Simulator() {
		values = new ArrayList<>();
		rnd = new SecureRandom();
		earnedMoneyPerCombination = new double[pumpsAndTills.length];
		lostMoneyPerCombination = new double[pumpsAndTills.length];
		TotalearnedMoney = new double[pumpsAndTills.length];
		TotallostMoney = new double[pumpsAndTills.length];
		bestCombinations = new double[pumpsAndTills.length][5];

	}

	/**
	 * Simulate method is responsible to maintain all loops in simulation
	 * starting from outer: 1. For loop: How many times simulation is going to
	 * run. 2. While loop: pushes all combination from array. 3. For loop: how
	 * many steps simulation is going to run. Also, it executes various method
	 * on specific time.
	 * 
	 * @param num
	 *            ticks/steps that program must run per per one simulation.
	 */
	private void simulate(int num) {

		long start = System.currentTimeMillis();
		combinations = Functions.loadsettings(Functions.readSettings("text.txt", 5));

		for (int y = 0; y < 10; y++) {
			while (simulationCount < combinations.length) {
				if (step == num - 1 && simulationCount < combinations.length) {
					step = 0;
				}
				for (step = 0; step < num; step++) {
					simulateoneStep(combinations);

					if (step == (numOfsteps - 1)) {
						getStatistics(values, pumpsAndTills, station, shop);
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
		for (int y = 0; y < bestCombinations.length; y++) {
			Object[] data = { Double.valueOf(bestCombinations[y][0]), Double.valueOf(bestCombinations[y][1]),
					Double.valueOf(bestCombinations[y][2]), Double.valueOf(bestCombinations[y][3]),
					Double.valueOf(bestCombinations[y][4]) };
			System.out.format("|%-8.0f %.0f|%,-12.2f|%,-12.2f|%,-12.2f|%n", data);
		}
		printSeparator();

	}

	private void getStatistics(List<Double> togetfrom, int[][] combination, Station sta, Shop sh) {

		int index = 0;
		double earnedPerRun;
		double lostPerRun;
		
		for (int x = 0; x < combination.length; x++) {
			for (int y = 0; y < combination[0].length; y += 2) {
				if (togetfrom.get(0).intValue() == combination[x][y + 0]
						&& togetfrom.get(1).intValue() == combination[x][y + 1]) {
					earnedPerRun = sh.getEarnedmoney();
					lostPerRun = sta.getLostmoney();
					if (bestCombinations[index][4] < (earnedPerRun - lostPerRun)) {
						double[] data = { combination[x][y], combination[x][y + 1], togetfrom.get(2).doubleValue(),
								togetfrom.get(3).doubleValue(), (earnedPerRun - lostPerRun) };
						for (int i = 0; i < bestCombinations[0].length; i++) {
							bestCombinations[index][i] = data[i];
						}
					}
					earnedMoneyPerCombination[index] += earnedPerRun;
					lostMoneyPerCombination[index] += lostPerRun;
				}
			}
			index++;
		}
		index = 0;

	}

	private void getTotalStatistics() {

		getAVG(earnedMoneyPerCombination, lostMoneyPerCombination, earnedMoneyPerCombination.length, 25);

		for (int i = 0; i < TotalearnedMoney.length; i++) {
			TotalearnedMoney[i] += earnedMoneyPerCombination[i];
			TotallostMoney[i] += lostMoneyPerCombination[i];
		}

		Functions.clearArray(earnedMoneyPerCombination);
		Functions.clearArray(lostMoneyPerCombination);

		if (executionCount == 10) {
			getAVG(TotalearnedMoney, TotallostMoney, TotalearnedMoney.length, 10);
		}
	}

	private void clear() {

		values.clear();
		shop = null;
		station = null;

	}

	private void getAVG(double[] first, double[] second, int size, int divby) {

		for (int i = 0; i < size; i++) {
			first[i] = first[i] / divby;
			second[i] = second[i] / divby;
		}

	}

	private void saveValues(List<Double> arr, double value) {
		int counter = 0;
		if (counter < combinations[0].length) {
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