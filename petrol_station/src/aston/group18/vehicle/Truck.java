package aston.group18.vehicle;

import defaultPackage.Functions;

/** 
 * @author Justas Petrusonis
 * @author Jasmin Sabaka
 * @version V1.00, 03/10/17
 */


public class Truck extends Vehicle {
	
	
	private Driver driver;
	
	/**
	 * This constructs a Truck with a random tank size and a specified unit size.
	 * It also constructs a driver for this Truck. 
	 */

	public Truck() {

		UNITSIZE = 2;
		tanksize = Functions.getRandom(30,40);
		fuelInTank = Functions.getRandom(1,tanksize);
		driver = new Driver();
	}
	
	/**
	 * This constructs a Truck with a random tank size and a specified unit size.
	 * It also constructs a driver for this Truck. 
	 * @param min The minimum size for the tank.
	 * @param max The maximum size for the tank.
	 */

	public Truck(int min, int max) {
		
		tanksize = Functions.getRandom(min,max);
		fuelInTank = Functions.getRandom(1,tanksize);
		driver = new Driver();

	}

	/**
	 * Get the tank size.
	 * @return The tank size for this Truck.
	 */

	public int getTankSize() {
		return tanksize;
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
	
	public Driver getDriver(){
		return driver;
	}

	/**
	 * Returns true if fuelInTank is equal to TANKSIZE.
	 * 
	 * @return boolean value 
	 */
	
	public boolean haveSpaceInTank() {
		if (fuelInTank < tanksize) {
			return true;
		}
		return false;
	}

}
