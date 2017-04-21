package petrol_station;


/**
 * @author Justas Petrusonis
 * @author Jasmin Sabaka
 * @version v1.06
 */

public class SmallCar extends Vehicle {

	private Driver driver;
		
	/**
	 * This constructs a SmallCar with a random tank size and specific unit size. 
	 * It also constructs a driver for this SmallCar.
	 */

	public SmallCar() {
		
		UNITSIZE = 1;
		tanksize = Functions.getRandom(7,9);
		fuelInTank = Functions.getRandom(1,tanksize);
		driver = new CarDriver();

	}
	
	/**
	 * This constructs a SmallCar with a random tank size and specific unit size. 
	 * It also constructs a driver for this SmallCar.
	 * @param min The minimum size for the tank.
	 * @param max The maximum size for the tank.
	 */

	public SmallCar(int min, int max) {
		
		UNITSIZE = 1;
		tanksize = Functions.getRandom(min,max);
		fuelInTank = Functions.getRandom(1,tanksize);
		driver = new CarDriver();

	}

	/**
	 * Get the tank size.
	 * @return The tank size for this SmallCar.
	 */

	public int getTankSize() {
		return tanksize;
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

	public Driver getDriver() {
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
