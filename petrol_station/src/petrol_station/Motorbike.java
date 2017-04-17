package petrol_station;

import java.util.Random;

/**
 * @author Justas Petrusonis
 * @author Jasmin Sabaka
 * @version 1.0
 * */

public class Motorbike extends Vehicle {
	
	private static int TANKSIZE = 5;
	private int fuelInTank;
	private final static double UNITSIZE = 0.75;
	private CarDriver driver;
	
	/**
	 * This constructs a Motorbike with a random tank size and specific unit size. 
	 * It also constructs a driver for this Motorbike.
	 */

	public Motorbike() {

		rnd = new Random();
		fuelInTank = rnd.nextInt(TANKSIZE - 1) + 1;
		driver = new CarDriver();

	}
	
	/**
	 * This constructs a Motorbike with a random tank size and specific unit size. 
	 * It also constructs a driver for this Motorbike.
	 * @param min The minimum size for the tank.
	 * @param max The maximum size for the tank.
	 */

	public Motorbike(int min, int max) {

		rnd = new Random();
		TANKSIZE = rnd.nextInt(max - min + 1) + min;
		fuelInTank = rnd.nextInt(TANKSIZE - 1) + 1;
		driver = new CarDriver();

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
	
	public CarDriver getDriver(){
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