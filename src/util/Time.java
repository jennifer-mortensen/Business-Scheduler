package util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Utility class for business hours, time conversion, and formatting time in a
 * user-friendly fashion.
 * @author J. Mortensen
 */
public class Time 
{    
    /**
     * The time (EST) that the business opens.
     */
    private final static LocalDateTime BUSINESS_TIME_OPEN = LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 0));
    /**
     * The time (EST) that the business closes.
     */
    private final static LocalDateTime BUSINESS_TIME_CLOSE = LocalDateTime.of(LocalDate.now(), LocalTime.of(22, 0));
    /**
     * Formats date time into a time string, e.g. 10:30 AM.
     */
    private static final DateTimeFormatter TIME_DTF = DateTimeFormatter.ofPattern("hh:mm a");
    /**
     * Formats date time into a date string, e.g. 2021-03-04.
     */
    private static final DateTimeFormatter DATE_DTF = DateTimeFormatter.ofPattern("yyyy-mm-dd");
    /**
     * Formats date time into a date time string, e.g. 2021-03-4 10:30 AM.
     */
    private static final DateTimeFormatter DATE_TIME_DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
    /**
     * The timezone of the business (i.e. EST).
     */
    private static final ZoneId BUSINESS_TIMEZONE = ZoneId.of("America/New_York");

    /**
     * Converts the system's timezone to business timezone.
     * @param localDateTime the time to convert.
     * @return LocalDateTime
     */
    public static LocalDateTime LocalTimeToBusinessTime(LocalDateTime localDateTime)
    {
        return localDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(BUSINESS_TIMEZONE).toLocalDateTime();
    }
    
    /**
     * Returns the name of the given month.
     * @param month the month's number in the year, e.g. 12 for December.
     * @return the month's name as a String.
     */
    public static String IntToMonth(int month)
    {
        switch(month)
        {
            case 1:  return "January";
            case 2:  return "Feburary";
            case 3:  return "March";
            case 4:  return "April";
            case 5:  return "May";
            case 6:  return "June";
            case 7:  return "July";
            case 8:  return "August";
            case 9:  return "September";
            case 10: return "October";
            case 11: return "November";
            case 12: return "December";
            default: return "";
        }
    }
    
    /**
     * Converts the system's timezone to UTC time/
     * @param localDateTime the time to convert.
     * @return LocalDateTime
     */
    public static LocalDateTime LocalTimeToUTCTime(LocalDateTime localDateTime)
    {
        return localDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
    }    
     
    /**
     * Converts business timezone to the system's timezone.
     * @param localDateTime the time to convert.
     * @return LocalDateTime
     */
    public static LocalDateTime BusinessTimeToLocalTime(LocalDateTime localDateTime)
    {
        return localDateTime.atZone(BUSINESS_TIMEZONE).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }
    
    /**
     * Converts UTC time to the system's timezone.
     * @param localDateTime the time to convert.
     * @return LocalDateTime
     */
    public static LocalDateTime UTCTimeToLocalTime(LocalDateTime localDateTime)
    {
        return localDateTime.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }
    
    /**
     * Converts UTC time (as a SQL timestamp) to the system's timezone.
     * @param sqlTimestamp the timestamp to convert.
     * @return LocalDateTime.
     */
    public static LocalDateTime UTCTimeToLocalTime(Timestamp sqlTimestamp)
    {
        return UTCTimeToLocalTime(LocalDateTime.ofInstant(sqlTimestamp.toInstant(), ZoneId.systemDefault()));
    }
       
    /**
     * Formats the given date time as a time string.
     * @param localDateTime the time to format.
     * @return String
     */
    public static String FormatTime(LocalDateTime localDateTime)
    {
        return TIME_DTF.format(localDateTime);
    }    
    
    /**
     * Formats the given date time as a date time string.
     * @param localDateTime the time to format.
     * @return  String
     */
    public static String FormatDateTime(LocalDateTime localDateTime)
    {
        return DATE_TIME_DTF.format(localDateTime);
    }    
    
    /**
     * Formats the given date time string as a date string.
     * @param localDateTime the time to format.
     * @return String
     */
    public static String FormatDate(LocalDateTime localDateTime)
    {
        return DATE_DTF.format(localDateTime);
    }
    
    /**
     * Returns the time that the business opens.
     * @return LocalDateTime
     */
    
    public static LocalDateTime getBusinessTimeOpen() { return BUSINESS_TIME_OPEN; }
    /**
     * Returns the time that the business closes.
     * @return LocalDateTime
     */
    public static LocalDateTime getBusinessTimeClose() { return BUSINESS_TIME_CLOSE; }
}