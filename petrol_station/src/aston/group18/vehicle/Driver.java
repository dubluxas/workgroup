package aston.group18.vehicle;



/**
 * Driver abstract class is not really necessary at the moment
 * but it could be used in future software development.
 * @autor Justas Petrusonis
 * @version 1.0, 03/09/17
 * **/

public class Driver {
	
	private boolean isHappy;
	private double extraMoney;
	private int time;
	
	public Driver(){ 
		isHappy=false;
		time=0;
		//extraMoney=Functions.getRandom(min,max);
	}
	
	public boolean getMood(){
		return isHappy;
	}
	
	public void makeHappy(){
		isHappy=true;
	}
	
	public double getExtraMoney(){
		return extraMoney;
	}
	
	public int getTime(){
		return time;
	}
	
	public void incrementTime(){
		time++;
		
	}
	
	

}
