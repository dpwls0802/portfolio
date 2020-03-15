package www.spring.com.framework.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static Date getYesterday() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		Date yesterday = cal.getTime();
		return yesterday;
	}
}