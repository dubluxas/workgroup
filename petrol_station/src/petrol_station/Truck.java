package petrol_station;

import java.util.Random;

/** 
 * @author Justas Petrusonis
 * @author Jasmin Sabaka
 * @version V1.00, 03/10/17
 */


public class Truck extends Vehicle {
	
	private static int TANKSIZE;
	private int fuelInTank;
	private static double UNITSIZE = 2;
	private TruckDriver driver;
	
	/**
	 * This constructs a Truck with a random tank size and a specified unit size.
	 * It also constructs a driver for this Truck. 
	 */

	public Truck() {

		rnd = new Random();
		TANKSIZE = rnd.nextInt(40 - 30 + 1) + 30;
		fuelInTank = rnd.nextInt(TANKSIZE - 1) + 1;
		driver = new TruckDriver();
	}
	
	/**
	 * This constructs a Truck with a random tank size and a specified unit size.
	 * It also constructs a driver for this Truck. 
	 * @param min The minimum size for the tank.
	 * @param max The maximum size for the tank.
	 */

	public Truck(int min, int max) {

		rnd = new Random();
		TANKSIZE = rnd.nextInt(max - min + 1) + min;
		fuelInTank = rnd.nextInt(TANKSIZE - 1) + 1;
		driver = new TruckDriver();

	}

	/**
	 * Get the tank size.
	 * @return The tank size for this Truck.
	 */

	public int getTankSize() {
		return TANKSIZE;
	}

	/**
	 * Get the size the Truck occupies in the queue.
	 * @return Truck size in the queue.
	 */

	public double getSizeInQueue() {
		return UNITSIZE;
	}

	/**
	 * Get the current amount of fuel in the tank.
	 * @return The current amount of fuel in tank of this Truck.
	 */
	
	public int getFuelInTank() {
		return fuelInTank;
	}
	
	/**
	 * fills tank one gallon per tick.
	 */
	
	
	public void fillfuel(){
		fuelInTank++;
	}
	
	/**
	 * Get the driver.
	 * @return The driver of this FamilySedan.
	 */
	
	public TruckDriver getDriver(){
		return driver;
	}

	/**
	 * Returns true if fuelInTank is equal to TANKSIZE.
	 * 
	 * @return boolean value 
	 */
	
	public boolean haveSpaceInTank() {
		if (fuelInTank < TANKSIZE) {
			return true;
		}
		return false;
	}

}
