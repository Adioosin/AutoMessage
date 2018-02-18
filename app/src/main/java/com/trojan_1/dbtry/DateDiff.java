package com.trojan_1.dbtry;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Aditya Sinha on 30-08-2017.
 */

public class DateDiff {

    public int daysLeft(String date){
        SimpleDateFormat dfDate  = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date d = null;
        java.util.Date d1 = null;
        Calendar cal = Calendar.getInstance();
        try {
            d = dfDate.parse(date);
            d1 = dfDate.parse(dfDate.format(cal.getTime()));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        int diffInDays = (int) ((d.getTime() - d1.getTime())/ (1000 * 60 * 60 * 24));
        return diffInDays;
    }

}
