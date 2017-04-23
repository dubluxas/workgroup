package defaultPackage;

/**
 * This class holds a static methods
 * @author Justas Petrusonis
 */


import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.stream.Stream;

public final class Functions {

	public Functions() {
	}

	/**
	 * 
	 * @param value takes a String that that going
	 * to be formatted in appropriate format that requires currency.
	 * @return returns String in "0.00" format.
	 * 
	 */
	public static String round(String value) {

		double v = Double.parseDouble(value.toString());
		DecimalFormat decim = new DecimalFormat("0.00");
		return decim.format(v);
	}

	/**
	 * Method returns Random int value with given minimum and maximum values. 
	 * @param min minimum value.
	 * @param max maximum value.
	 * @return int random value.
	 */
	
	public static int getRandom(int min, int max) {

		Random rnd = new SecureRandom();
		int randomNumber = rnd.nextInt(max - min + 1) + min;

		return randomNumber;

	}
	/**
	 * Method reads lines, split them into words and returns them to array
	 * @param file takes file path as string
	 * @param split how many words it will read separated by space per line
	 * @return returns 2d array of String.
	 */

	public static String[][] readSettings(String file, int split) {

		Path path = FileSystems.getDefault().getPath(file);
		try (Stream<String> lines = Files.lines(path)) {
			String[][] string = lines.map(s -> s.split(" ", split)).toArray(String[][]::new);

			return string;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}
	
	/**
	 * Method converts 2d array of string to 2d array of doubles.
	 * @param string accepts 2d arrays of string
	 * @return returns 2d array of doubles.
	 */
	
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
