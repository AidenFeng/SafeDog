package et199tool.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {

	public static String getCurrentDate()
	{
        return getCurrentDate("MM/dd/yyyy");
    }

	public static String getCurrentTime()
	{
		return getCurrentDate("MM/dd/yyyy/hh/mm/ss");
	}

    public static String getCurrentDate(String format)
    {
        Calendar day = Calendar.getInstance();
        day.add(Calendar.DATE, 0);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String date = sdf.format(day.getTime());
        return date;
    }

    public static String getCurrentTime(String format)
    {
        Calendar day = Calendar.getInstance();
        day.add(Calendar.DATE, 0);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String date = sdf.format(day.getTime());
        return date;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
    	//String today = getCurrentDate();
    	//System.out.println(getNextDate(today, SystemConstant.EXPIRE_TYPE_MONTH, -10));

    	String s1 = getCurrentTime();
    	try
    	{
    		Thread.sleep(3000);
    	}
    	catch(Exception e)
    	{

    	}
    	String s2 = getCurrentTime();

    	System.out.println(s1.compareTo(s2));
    }
}
