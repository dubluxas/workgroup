package aston.group18.vehicle;

/**
 * Vehicle is an abstract class which have several concrete classes.
 * It does not contain a code for concrete classes
 * Only method signatures
 * @author Justas Petrusonis
 * @version 1.0, 03/09/17
 * **/

public abstract class Vehicle {
		
	protected int fuelInTank;
	protected int tanksize;
	protected static double UNITSIZE;
		
	public abstract int getFuelInTank();
	public abstract int getTankSize();
	public abstract double getSizeInQueue();
	public abstract boolean haveSpaceInTank();
	public abstract Driver getDriver();	
	
	//03/18/2017
	public abstract void fillfuel();
	
	
	
}
