package Periode;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.time.*;

/**
 *
 * @author Serero
 */
public class Time {
	private int hour;
	private int minute;
	private int second;
	
	public void setTime(int h, int m, int s)
	{
		hour=((h>=0 && h<24) ? h : 0);
		hour=((m>=0 && m<60) ? m : 0);
		hour=((s>=0 && s<60) ? s : 0);
	}
	
	public String toMilitary()
	{
		return String.format("%02d:%02:%02d",hour,minute,second);
	}

	@Override
	public String toString()
	{
		return String.format("%d:%02d:%02d %s",(( hour==0 || hour==12) ? 12 : hour % 12), minute, second, ((hour < 12 ? "AM" : "PM")));
	}
	
}
