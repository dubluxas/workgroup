package petrol_station;
import java.security.SecureRandom;
import java.text.DecimalFormat;

public final class Functions {
	
	public Functions () {}
	
	public static String round(String value) {

		double v = Double.parseDouble(value.toString());
		DecimalFormat decim = new DecimalFormat("#.00");
		return decim.format(v);
	}
	
		public static int getRandom(){
		
		SecureRandom rnd = new SecureRandom();
		//rnd.setSeed((int) array[e][x]);
		int randomNumber = rnd.nextInt(18 - 12 + 1) + 12;
		
		return randomNumber;
		
	}
	
	public static void main(String[] args){
		
		for(int i = 0; i<100; i++){
		
			getRandom();
			
			
		}
		
	}
	
}
