package ua.southwall.vkbookmarksfeed.util;

import android.content.Context;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import ua.southwall.vkbookmarksfeed.R;

/**
 * Created by mac on 5/25/15.
 */
public class UnixTimeConverter {

    private static Context context;

    private static String TAG = "UnixTimeConverter";
    public static String ConvertUnixToDate(long unixSeconds, Context mContext){
        context = mContext;
        return recentTime(unixSeconds);
    }

    private static String recentTime(long unixSeconds){
        long unixSecondsThis = System.currentTimeMillis() / 1000L;
        long diff = unixSecondsThis-unixSeconds;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(unixSeconds*1000L));

       // Log.e(TAG, String.valueOf(rightNow.get(Calendar.HOUR_OF_DAY)));
       // Log.e(TAG, String.valueOf(rightNow.get(Calendar.HOUR)));
       // Log.e(TAG, String.valueOf(rightNow.get(Calendar.MINUTE)));
       // Log.e(TAG, String.valueOf(rightNow.get(Calendar.DAY_OF_MONTH)));
       // Log.e(TAG, String.valueOf(rightNow.get(Calendar.MONTH)));
       // Log.e(TAG, String.valueOf(rightNow.get(Calendar.YEAR)));


        if((diff)<(60))
            return String.valueOf(diff) + " " + context.getResources().getString(R.string.seconds_ago);//less then a minute
        else if(diff<(60*60))
            return String.valueOf(diff/60) + " " + context.getResources().getString(R.string.minutes_ago);//less then an hour
        else if(diff<(60*60*2))
            return String.valueOf(diff/60/60) + " " + context.getResources().getString(R.string.hour_ago);//less then 2 hours(singular form)
        else if(diff<(60*60*3))
            return String.valueOf(diff/60/60) + " " + context.getResources().getString(R.string.hours_ago);//less then 3 hours(plural form)
        //TODO 2 and start finally with the feed, goddammit
        else
            return formFullDate(calendar);
    }

    private static String formFullDate(Calendar calendar) {
        Calendar rightNow = Calendar.getInstance();
       // Log.e(TAG, "HERE " + rightNow.get(Calendar.YEAR) +  calendar.get(Calendar.YEAR) );

        String builder;
        if(((rightNow.get(Calendar.DAY_OF_YEAR)-1) == calendar.get(Calendar.DAY_OF_YEAR) && (rightNow.get(Calendar.YEAR) == calendar.get(Calendar.YEAR))))
            builder = context.getResources().getString(R.string.yesterday);
        else {
            builder = getFromCalendar(calendar, Calendar.DAY_OF_MONTH) + " " + getFromCalendar(calendar, Calendar.MONTH);
            if (rightNow.get(Calendar.YEAR) != calendar.get(Calendar.YEAR))
                builder+=", " + getFromCalendar(calendar, Calendar.YEAR);
        }
        builder+= " " + context.getResources().getString(R.string.at) + " " + getFromCalendar(calendar, Calendar.HOUR) + ":" +
                getFromCalendar(calendar, Calendar.MINUTE);
       // builder = getFromCalendar(calendar, Calendar.DAY_OF_MONTH) + " " + getFromCalendar(calendar, Calendar.MONTH) + " " + context.getResources().getString(R.string.at) + " " + getFromCalendar(calendar, Calendar.HOUR) + ":" +
       //         getFromCalendar(calendar, Calendar.MINUTE);//TODO 3 build string effectively;currently no year and no yesterday!
       // Log.e(TAG, "HERE");
        //Log.e(TAG, builder);
        return builder;
    }
    private static  String getFromCalendar(Calendar calendar, int field){
        if(field==Calendar.MONTH){
            final String [] months = context.getResources().getStringArray(R.array.month);
            return months[calendar.get(field)];
        }
        else if(field==Calendar.MINUTE) {
            if (calendar.get(field) < 10)
                return "0" + String.valueOf(calendar.get(field));
        }
            return String.valueOf(calendar.get(field));
    }
}
