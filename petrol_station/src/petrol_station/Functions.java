package petrol_station;


import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.stream.Stream;

public final class Functions {

	public Functions() {
	}

	public static String round(String value) {

		double v = Double.parseDouble(value.toString());
		DecimalFormat decim = new DecimalFormat("0.00");
		return decim.format(v);
	}

	public static int getRandom() {

		SecureRandom rnd = new SecureRandom();
		// rnd.setSeed((int) array[e][x]);
		int randomNumber = rnd.nextInt(18 - 12 + 1) + 12;

		return randomNumber;

	}

	public static String[][] readSettings() {

		Path path = FileSystems.getDefault().getPath("text.txt");
		try (Stream<String> lines = Files.lines(path)) {
			String[][] string = lines.map(s -> s.split(" ", 5)).toArray(String[][]::new);

			return string;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}
	
	@SuppressWarnings("boxing")
	public static double[][] loadsettings(String string[][]){
		
		//System.out.println(string.length);
		double[][] a  = new  double[string.length][string[0].length];
		for (int i = 0; i < string.length; i++) {
			for (int j = 0; j < string[0].length; j++) {
				a[i][j] = Double.valueOf(string[i][j].toString());
			}
		}
			
		return a;
		
	}

}
