package petrol_station;

import java.util.Random;

/**
 * @author Justas Petrusonis
 * @author Jasmin Sabaka
 * @version v1.06
 */

public class SmallCar extends Vehicle {

	private static int TANKSIZE;
	private int fuelInTank;
	private final static double UNITSIZE = 1;
	private CarDriver driver;
	
	/**
	 * This constructs a SmallCar with a random tank size and specific unit size. 
	 * It also constructs a driver for this SmallCar.
	 */

	public SmallCar() {

		rnd = new Random();
		TANKSIZE = rnd.nextInt(9 - 7 + 1) + 7;
		fuelInTank = rnd.nextInt(TANKSIZE - 1) + 1;
		driver = new CarDriver();

	}
	
	/**
	 * This constructs a SmallCar with a random tank size and specific unit size. 
	 * It also constructs a driver for this SmallCar.
	 * @param min The minimum size for the tank.
	 * @param max The maximum size for the tank.
	 */

	public SmallCar(int min, int max) {

		rnd = new Random();
		TANKSIZE = rnd.nextInt(max - min + 1) + min;
		fuelInTank = rnd.nextInt(TANKSIZE - 1) + 1;
		driver = new CarDriver();

	}

	/**
	 * Get the tank size.
	 * @return The tank size for this SmallCar.
	 */

	public int getTankSize() {
		return TANKSIZE;
	}

	/**
	 * Get size the SmallCar occupies in the queue.
	 * @return SmallCar size in the queue.
	 */

	public double getSizeInQueue() {
		return UNITSIZE;
	}

	/**
	 * Get the current amount of fuel in the tank.
	 * @return The current fuel for the tank of this SmallCar.
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
	 * @return The driver of this SmallCar.
	 */

	public CarDriver getDriver() {
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
