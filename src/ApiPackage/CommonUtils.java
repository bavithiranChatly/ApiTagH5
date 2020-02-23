package ApiPackage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class CommonUtils {

	// to generate current Date and Time
	public static String getCurrentDateAndTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

		LocalDateTime now = LocalDateTime.now();

		System.out.println(dtf.format(now));

		String formateDateAndTimeValue = now.format(dtf);

		return formateDateAndTimeValue;

	}

	// to generate UniqueId
	public static String getUniqueID() {
		String uniqueID = UUID.randomUUID().toString();
		return uniqueID;
	}

	//to Change String to Int
	public static int getStringToint(String Value) {
		int inum = Integer.parseInt(Value);
		return inum;

	}
}
