package it.unipi.lsmd.utils;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class LocalDateAdapter {


    public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

}

