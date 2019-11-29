package com.wowItemsAPI.helper;

import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@NoArgsConstructor
@Configuration
public class DateValidation {
    DateFormat sdf = new SimpleDateFormat("yyyy=MM-dd");

    public boolean isValid(Date date){
        String dateString = String.valueOf(date);
        sdf.setLenient(false);
        try {
            sdf.parse(dateString);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public Date stringToDate(){
        Date date = new Date();
        String dateString = sdf.format(date);
        try {
            date =  sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }
}
