package petrol_station;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Justas Petrusonis
 * @author Jasmin Sabaka
 * @version V1.00, 03/10/19
 */

public class Station {

	private Pump pump;
	private List<Pump> pumps;
	private Map<Driver, Integer> drivers;
	private Map<Vehicle, Double> lostVehicles;
	private boolean trucks = false;

	private boolean newVehicle;

	/**
	 * This creates a specific number of pumps.
	 * 
	 * @param pumpSize
	 *            the number of pumps.
	 * @param trucks
	 */

	public Station(int pumpSize, boolean trucks) {
		
		pumps = new ArrayList<>();
		lostVehicles = new HashMap<>();
		drivers = new HashMap<>();

		for (int i = 1; i <= pumpSize; i++) {
			pump = new Pump();
			pumps.add(pump);

		}
				
		this.trucks = trucks;

	}

	/**
	 * This creates a specific number of pumps.
	 * 
	 * @param pumpSize
	 *            the number of pumps.
	 */

	public Station(int pumpSize) {

		pumps = new ArrayList<>();
		drivers = new HashMap<>();
		lostVehicles = new HashMap<>();

		for (int i = 1; i <= pumpSize; i++) {
			pump = new Pump();
			pumps.add(pump);

		}
		
		this.trucks = false;

	}

	public Station() {

		pumps = new ArrayList<>();
		pump = new Pump();
		pumps.add(pump);
		trucks = false;
		drivers = new HashMap<>();
		lostVehicles = new HashMap<>();

	}

	/**
	 * Get the pump which is least occupied.
	 * 
	 * @return least occupied pump.
	 */

	public Pump getLeastOccupied() {

		// new vehicle boolean was created to reduce program run time.
		// get customers code will be executed if new vehicle arrived else just
		// return old list of drivers.
		// used program visualVM to check memory usage.
		// program run time was reduced from 16s to 6s
		newVehicle = true;

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

		if (newVehicle) {

			for (Pump p : pumps) {

				for (Entry<Driver, Integer> entry : p.getDriverInfo().entrySet()) {
					Driver key = entry.getKey();
					Integer value = entry.getValue();

					drivers.put(key, value);

				}
			}

			newVehicle = false;

		}

		return drivers;

	}

	public List<Pump> getPumps() {
		return pumps;
	}

	public void removeVehicles(Map<Driver, Integer> driverInfo) {

		if (!driverInfo.isEmpty()) {

			for (Pump p : pumps) {

				if (p.getVehicleQueue().peek() != null) {

					if (driverInfo.containsKey(p.getVehicleQueue().peek().getDriver())) {

						p.getVehicleQueue().poll();

					}

				}

			}

		}

	}

	@SuppressWarnings("boxing")
	public Map<Vehicle, Double> getLostVehicles() {

		for (Pump p : pumps) {

			for (Vehicle v : p.getLostVehicles()) {
				lostVehicles.put(v, Shop.getPricePerDriver(v.getTankSize() - v.getFuelInTank(), Shop.getPrice()));
			}

		}
		
		return lostVehicles;

	}
	
	public int[] getLostVehiclesCount() {

		int w, x, y, z;
		w = x = y = z = 0;

		for (Entry<Vehicle, Double> m : getLostVehicles().entrySet()) {

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

		int[] result = { w, x, y, z };

		return result;

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

	public void clear() {
		pump = null;
		pumps = null;

	}
	
	public double getLostmoney() {

		double sum = 0.00;
		sum = getLostVehicles().entrySet().stream().map(Map.Entry::getValue).mapToDouble(Double::doubleValue).sum();
		return sum;

	}

}
