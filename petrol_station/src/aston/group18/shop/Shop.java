package aston.group18.shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import aston.group18.station.Pump;
import aston.group18.station.Station;
import aston.group18.vehicle.Driver;

/**
 * @author Justas Petrusonis
 * @author Jasmin Sabaka
 * @version v1.05
 */

public class Shop {

	private Till till;
	private final static double pricePerGallon = 1.20;
	private List<Till> tills;
	private Map<Driver, Double> bills;
	private List<Driver> customers = new ArrayList<>();
	private int stepsToSkipp = 0;

	/**
	 * This method creates a specified number of tills.
	 * 
	 * @param tillSize
	 *            the number of tills.
	 */

	public Shop(int tillSize) {

		tills = new ArrayList<>();
		bills = new HashMap<>();

		for (int i = 1; i <= tillSize; i++) {
			till = new Till();
			tills.add(till);
		}

	}

	public Shop() {

		tills = new ArrayList<>();
		bills = new HashMap<>();
		till = new Till();
		tills.add(till);

	}

	/**
	 * Get the total price the customer needs to pay.
	 * 
	 * @return the price.
	 */

	public static double pricePerGallon() {
		return pricePerGallon;
	}

	// add driver to to least occupied queue
	/**
	 * Method is responsible to add new customers to one of least occupied queues in the shop.
	 * @param station Takes station as a parameter to get drivers from station.
	 */
	public void addCustomer(Station station) {

		if (!station.getDrivers().isEmpty()) {

			for (Entry<Driver, Integer> driver : station.getDrivers().entrySet()) {
				// d arrayList is used to make sure that the same customer not
				// going to be added to the queue?!
				if (!customers.contains(driver.getKey())) {
					getLeastOccupied().addtoQueue(driver.getKey());
					customers.add(driver.getKey());

				}

			}

		}

	}

	/**
	 * The method is responsible to return least occupied shop queue. 
	 * @return returns Till that is least occupied.
	 */
	public Till getLeastOccupied() {

		int index = 0;

		for (int i = 0; i < tills.size(); i++) {
			if (i > 0) {
				if (tills.get(i - 1).getDriverQueue().size() < tills.get(i).getDriverQueue().size()) {
					index = tills.indexOf(tills.get(i - 1));
				} else if (tills.get(i - 1).getDriverQueue().size() > tills.get(i).getDriverQueue().size()) {
					index = tills.indexOf(tills.get(i));
				}
			} else {
				index = tills.indexOf(tills.get(i));
			}
		}

		return tills.get(index);
	}
	
	/**
	 * 
	 * @param driverInfo
	 * @param pumps
	 * @param step
	 * @param stepstoSkip
	 */

	@SuppressWarnings("boxing")
	public void pay(Map<Driver, Integer> driverInfo, List<Pump> pumps, int step, int stepstoSkip) {

		if (stepsToSkipp == 0) {
			stepsToSkipp = step;
		}

		if (((stepsToSkipp + stepstoSkip) - 1) == step) {

			for (Iterator<Till> itr = tills.iterator(); itr.hasNext();) {

				Till t = itr.next();

				if (driverInfo.containsKey(t.getDriverQueue().peek())) {

					if (!bills.containsKey(t.getDriverQueue().peek())) {

						bills.put(t.getDriverQueue().peek(),
								getPricePerDriver(driverInfo.get(t.getDriverQueue().peek()), pricePerGallon()));
					}
				}

				removeCustomerInfo(pumps, driverInfo, t);

			}

			stepsToSkipp = 0;

		}

	}

	private void removeCustomerInfo(List<Pump> pumps, Map<Driver, Integer> driverInfo, Till t) {

		for (Iterator<Pump> itr2 = pumps.iterator(); itr2.hasNext();) {

			Pump pump = itr2.next();

			if (pump.getVehicleQueue().peek() != null) {

				if (pump.getVehicleQueue().peek().getDriver().equals(t.getDriverQueue().peek())) {

					pump.getVehicleQueue().poll();
					t.getDriverQueue().poll();
					driverInfo.remove(t.getDriverQueue().peek());

				}

			}
		}
	}

	public static double getPricePerDriver(int gallons, double price) {
		return gallons * price;
	}

	public Map<Driver, Double> getBills() {
		return bills;
	}

	public double getEarnedmoney() {
		
		double sum = getBills().values().stream().mapToDouble(Double::doubleValue).sum();
		
		if (sum > 0){
			return sum;
		}
		
		return 0;
		
	}
	
}
