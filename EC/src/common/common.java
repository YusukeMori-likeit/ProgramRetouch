package common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class common {

	public static Date convertDate(String str) {
        try {

            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdFormat.parse(str);
            return date;

        } catch (ParseException e) {
            e.printStackTrace();
        }
		return null;
	}


}
