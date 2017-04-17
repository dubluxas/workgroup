package petrol_station;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;


public class ProjectTest {
			
	private petrol_station.Vehicle v1 = new petrol_station.Truck();
	private petrol_station.Vehicle v2 = new petrol_station.SmallCar();
	private petrol_station.Vehicle v3 = new petrol_station.Truck();
	private petrol_station.Vehicle v4 = new petrol_station.Truck();
	private petrol_station.Station s = new petrol_station.Station(2);
	
	//@BeforeClass
	
	@Before
	public void before(){
		
		s.getLeastOccupied().addtoQueue(v1);
		s.getLeastOccupied().addtoQueue(v2);
		s.getLeastOccupied().addtoQueue(v1);
		s.getLeastOccupied().addtoQueue(v2);
		
		s.getLeastOccupied().addtoQueue(v4);
		s.getLeastOccupied().addtoQueue(v3);
		s.getLeastOccupied().addtoQueue(v4);
		s.getLeastOccupied().addtoQueue(v1);
	
	}
	
	@Test (timeout = 100)
	public void queuesize1() {
		
		Assert.assertTrue("", s.getPump1().getVehicleQueue().size()>0.75);
		Assert.assertFalse("ssssss", s.getPump1().getVehicleQueue().size()>0.75);
	
	}
	
	@Test (timeout = 100)
	public void queuesize2(){
				
		Assert.assertFalse("xxxxxxx",s.getPump2().getVehicleQueue().size()>0.75);
	}
}
