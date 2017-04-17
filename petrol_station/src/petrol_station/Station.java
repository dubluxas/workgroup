package petrol_station;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Justas Petrusonis
 * @author Jasmin Sabaka
 * @version V1.00, 03/10/17
 */

public class Station {

	private Pump pump;
	private List<Pump> pumps;
	private boolean trucks = false;
	private Map<Driver, Integer> drivers;

	/**
	 * This creates a specific number of pumps.
	 * 
	 * @param pumpSize
	 *            the number of pumps.
	 * @param trucks
	 */

	public Station(int pumpSize, boolean trucks) {

		pumps = new ArrayList<>();

		for (int i = 1; i <= pumpSize; i++) {
			pump = new Pump();
			pumps.add(pump);

			this.trucks = trucks;
		}

	}

	/**
	 * This creates a specific number of pumps.
	 * 
	 * @param pumpSize
	 *            the number of pumps.
	 */

	public Station(int pumpSize) {

		pumps = new ArrayList<>();

		for (int i = 1; i <= pumpSize; i++) {
			pump = new Pump();
			pumps.add(pump);

			this.trucks = false;
		}

	}

	public Station() {

		pumps = new ArrayList<>();
		pump = new Pump();
		pumps.add(pump);
		trucks = true;

	}

	//////////////////
	// test methods
	public Pump getPump1() {
		return pumps.get(0);
	}

	public Pump getPump2() {
		return pumps.get(1);
	}
	////////

	/**
	 * Get the pump which is least occupied.
	 * 
	 * @return least occupied pump.
	 */

	public Pump getLeastOccupied() {

		int index = 0;

		for (int i = 0; i < pumps.size(); i++) {

			if (i != 0) {

				if (pumps.get(i - 1).checkQueueSize() < pumps.get(i).checkQueueSize()) {

				} else if (pumps.get(i - 1).checkQueueSize() > pumps.get(i).checkQueueSize()) {

					index = pumps.indexOf(pumps.get(i));
				}
			} else {

				index = pumps.indexOf(pumps.get(i));
			}
		}

		return pumps.get(index);
	}

	/**
	 * Get the information from all pumps about driver
	 * 
	 * 
	 * @return map which contains driver and fuel in gallons.
	 */

	public Map<Driver, Integer> getCustomers() {

		drivers = new HashMap<>();

		for (Pump p : pumps) {

			for (Entry<Driver, Integer> entry : p.getDriverInfo().entrySet()) {
				Driver key = entry.getKey();
				Integer value = entry.getValue();
				if (!drivers.containsKey(key)) {
					drivers.put(key, value);
				}
			}
		}

		return drivers;

	}

	public List<Pump> getPumps() {
		return pumps;
	}

	public void removeVehicles(Map<Driver, Integer> driverInfo) {

		if (!driverInfo.isEmpty()) {

			// Iterator<Pump> itr = pumps.iterator();

			for (Pump p : pumps) {

				if (p.getVehicleQueue().peek() != null) {

					if (driverInfo.containsKey(p.getVehicleQueue().peek().getDriver())) {

						// if(pump.getVehicleQueue().poll() != null){

						p.getVehicleQueue().poll();

					}

				}

			}

		}

	}

	@SuppressWarnings("boxing")
	public Map<Vehicle, Double> getLostVehicles() {

		Map<Vehicle, Double> lostMoney = new HashMap<>();

		for (Pump p : pumps) {

			for (Vehicle v : p.getLostVehicles()) {
				lostMoney.put(v, Shop.getPricePerDriver(v.getTankSize() - v.getFuelInTank(), Shop.getPrice()));
			}

		}

		return lostMoney;

	}

	public void topUp() {

		for (Pump p : pumps) {

			if (!p.getVehicleQueue().isEmpty()) {

				p.topUpTank(p.getVehicleQueue().peek());

			}
		}

	}

	public void setAllowtrucks(boolean trucks) {
		if (trucks != this.trucks) {
			this.trucks = trucks;
		}
	}

	public boolean getAllowTrucks() {
		return trucks;
	}
	
	public void clear(){
		pump = null;
		pumps = null;
		trucks = false;
		drivers = null;
	}

}
