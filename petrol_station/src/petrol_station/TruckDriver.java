package petrol_station;

public class TruckDriver extends Driver {
	

	public TruckDriver (){
	
		isHappy = false;
		
	}

	@Override
	public boolean getMood() {
		// TODO Auto-generated method stub
		return isHappy;
	}
	
	
		
}
