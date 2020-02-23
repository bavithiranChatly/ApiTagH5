package ApiPackage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommonUtils {

	public static String getCurrentDateAndTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

		LocalDateTime now = LocalDateTime.now();

		System.out.println(dtf.format(now));

		String formateDateAndTimeValue = now.format(dtf);

		return formateDateAndTimeValue;
	}

}
