package com.mapledoum.pointscalc.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class PointsUtils {


    public static final String CUSTOMER_NOT_EXISTS = "CUSTOMER NOT EXISTS";
    public static final String NO_RECORD_FOUND = "No Record Found";

    public  LocalDate convertStToDate(String date){
        return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
