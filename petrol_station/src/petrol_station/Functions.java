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
		DecimalFormat decim = new DecimalFormat("#.00");
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
			System.err.println(e.getMessage() +"========================================");
		}

		return null;

	}

}
