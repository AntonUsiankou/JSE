package by.gsu.epamlab.utils.classes;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static by.gsu.epamlab.Constants.*;

public class Utils {

    public static String getDateToString(Date date) {
        Format formatter = new SimpleDateFormat(DATE_PATTERN);
        return formatter.format(date);
    }

    public static Date getStringToDate(String date) {
        DateTimeFormatter parserDtf = DateTimeFormatter.ofPattern(DATE_LOCALE);
        LocalDate ldt = LocalDate.parse(date, parserDtf);
        return java.sql.Date.valueOf(ldt);
    }

    public static java.sql.Date parseDate(String strDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
        java.sql.Date date = null;
        try{
            date = (java.sql.Date) dateFormat.parse(strDate);
        }catch (ParseException e){
            throw new IllegalArgumentException(e);
        }
        java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());
        return sqlStartDate;
    }
}

