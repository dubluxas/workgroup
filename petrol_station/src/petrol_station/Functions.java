package petrol_station;
import java.text.DecimalFormat;

public final class Functions {
	
	public Functions () {}
	//ddddlllll
	public static String round(String value) {

		double v = Double.parseDouble(value.toString());
		DecimalFormat decim = new DecimalFormat("#.00");
		return decim.format(v);
	}
	
}
