package com.example.edgar.mysql_androidstudio.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class functions {
    public static String ObtenerFechaActual(){
        String id;
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat sdf,sdf2;
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        id = sdf.format(date);
        return id;
    }

}
