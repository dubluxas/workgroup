package petrol_station;

/**
 * Familysedan  is concrete class.
 * @author Justas Petrusonis
 * @author Jasmin Sabaka
 * @version 1.0
 * */

public class FamilySedan extends Vehicle {
		
	private CarDriver driver;
	
	/**
	 * This constructs a FamilySedan with a random tank size and specific unit size.
	 * It also constructs a driver.
	 */

	public FamilySedan() {

		UNITSIZE = 1.5;
		TANKSIZE = Functions.getRandom(12,18);
		fuelInTank = Functions.getRandom(1,TANKSIZE);
		driver = new CarDriver();
		
	}

	/**
	 * This constructs a FamilySedan with a random tank size and specific unit size.
	 * It also constructs a driver.
	 * @param min The minimum size for the tank.
	 * @param max The maximum size for the tank.
	 */
	
	public FamilySedan(int min, int max) {

		UNITSIZE = 1.5;
		TANKSIZE = Functions.getRandom(12,18);
	    fuelInTank = Functions.getRandom(1,TANKSIZE);
	    driver = new CarDriver();
	}

	/**
	 * Get the tank size.
	 * @return The tank size for this FamilySedan.
	 */

	public int getTankSize() {
		return TANKSIZE;
	}

	/**
	 * Get the size the FamilySedan occupies in the queue.
	 * @return FamilySedan size in the queue.
	 */
	
	public double getSizeInQueue() {
		return UNITSIZE;
	}

	/**
	 * Get the current amount of fuel in the tank.
	 * @return The current fuel for the tank of this FamilySedan.
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
