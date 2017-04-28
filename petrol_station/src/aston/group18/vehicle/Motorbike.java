package aston.group18.vehicle;
import DefaultPackage.Functions;
/**
 * @author Justas Petrusonis
 * @author Jasmin Sabaka
 * @version 1.0
 * */

public class Motorbike extends Vehicle {
	
	private Driver driver;
	private static int TANKSIZE;
	/**
	 * This constructs a Motorbike with a random tank size and specific unit size. 
	 * It also constructs a driver for this Motorbike.
	 */

	public Motorbike() {
		
		UNITSIZE = 0.75;
		TANKSIZE = 5;
		fuelInTank = Functions.getRandom(1,TANKSIZE);
		driver = new Driver();

	}
	
	/**
	 * This constructs a Motorbike with a random tank size and specific unit size. 
	 * It also constructs a driver for this Motorbike.
	 * @param min The minimum size for the tank.
	 * @param max The maximum size for the tank.
	 */

	public Motorbike(int min, int max) {
		
		UNITSIZE = 0.75;
		TANKSIZE = Functions.getRandom(min,max);
		fuelInTank = Functions.getRandom(min,TANKSIZE);
		driver = new Driver();

	}

	
	/**
	 * Get the tank size.
	 * @return The tank size for this Motorbike.
	 */

	public int getTankSize() {
		return TANKSIZE;
	}

	/**
	 * Get size the Motorbike occupies in the queue.
	 * @return Motorbike size in the queue.
	 */

	public double getSizeInQueue() {
		return UNITSIZE;
	}

	/**
	 * Get the current amount of fuel in the tank.
	 * @return The current amount of fuel for the tank of this Motorbike.
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
		if (fuelInTank < TANKSIZE) {
			return true;
		}
		return false;
	}
	
	

}