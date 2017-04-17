package petrol_station;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * <h3>Pump class</h3>
 * <p>
 * This class represents a pump in the station. Pump is responsible to top up a
 * tank for a {@link Vehicle}. Pump class has a {@link #queue} that holds a
 * vehicles.
 * </p>
 * 
 * @author Justas Petrusonis
 * @author Jasmin Sabaka
 * @version v1.05
 */

public class Pump {

	private int gallons = 0;
	private HashMap<Driver, Integer> driverInfo = null;
	private List<Vehicle> lostVehicles;
	/**
	 * A {@link #queue} is Collection type {@link Queue} that holds
	 * {@link Vehicle}s. {@link #queue} is limited up to 3 units per current
	 * {@link #queue}.
	 */
	private Queue<Vehicle> queue;
	/**
	 * {@link #QUEUESIZE} is a static value for all pumps if more than one
	 * exist.
	 * 
	 * @return Returns double value.
	 */
	private static double QUEUESIZE = 3;

	/**
	 * <h3>Pump constructor</h3>
	 * <p>
	 * Creates new {@link #queue} as LinkedList that holds {@link Vehicle}s for
	 * every Pump.
	 * </p>
	 */

	public Pump() {

		queue = new LinkedList<>();
		driverInfo = new HashMap<>();
		lostVehicles = new ArrayList<>();

	}

	///////////////////////////////
	// add and gets info about driver for shop
	////////////////////////////////
	public void addDriverInfo(Driver o, Integer galls) {

		driverInfo.put(o, galls);

	}

	public HashMap<Driver, Integer> getDriverInfo() {

		return driverInfo;

	}

	public int getGallons() {
		return gallons;
	}

	/**
	 * Returns all {@link Vehicle}s in the queue.
	 * 
	 * @return {@link #queue}
	 */
	public Queue<Vehicle> getVehicleQueue() {
		return queue;

	}

	/**
	 * <h3>checkQueueSize method</h3>
	 * <p>
	 * This method returns a size of current {@link #queue}.
	 * </p>
	 * 
	 * @return Returns {@link #queue} size.
	 */
	public double checkQueueSize() {

		double size = 0;

		for (Vehicle obj : queue) {
			size += obj.getSizeInQueue();

		}

		return size;

	}

	/**
	 * <h3>canFit method</h3>
	 * <p>
	 * This boolean type method takes {@link Vehicle} as a parameter and checks
	 * if it can be accepted to the queue.
	 * </p>
	 * 
	 * @param o
	 *            {@link Vehicle} type object.
	 * @return Returns <code>true</code> if {@link Vehicle} can be accepted.
	 */
	private boolean canFit(Vehicle o) {

		if ((checkQueueSize() + o.getSizeInQueue()) <= QUEUESIZE) {

			return true;

		}
		return false;

	}

	/**
	 * <h3>addtoQueue method</h3> Adds {@link Vehicle} type object to the
	 * {@link #queue} if it can be accepted.
	 * 
	 * @param o
	 *            {@link Vehicle} type object.
	 * @return Returns true if {@link Vehicle} added to the queue.
	 */
	public boolean addtoQueue(Vehicle o) {

		if (canFit(o)) {
			queue.add(o);
			return true;
		} else if (!canFit(o)) {
			lostVehicles.add(o);
		}

		return false;

	}
	
	
	/**
	 * <h3>topUpTank method</h3> Boolean method that tops up the tank and stops
	 * when the tank is full. Also, pump sends information about the driver and
	 * how many gallons he toped up.
	 * 
	 * @param Vehicle
	 *            accepts any type of vehicle.
	 * @return returns true if {@link Vehicle} tank is full.
	 */

	public boolean topUpTank(Vehicle vehicle) {

		if (vehicle.haveSpaceInTank()) {

			vehicle.fillfuel();
			gallons++;
			return true;

		}
		if (!this.getDriverInfo().containsKey(vehicle.getDriver())) {
			if (vehicle.getFuelInTank() == vehicle.getTankSize()) {
				addDriverInfo(vehicle.getDriver(), gallons);
				gallons = 0;
			}
		}
		return false;
	}
	
	public List<Vehicle> getLostVehicles(){
		return lostVehicles;
	}

}
