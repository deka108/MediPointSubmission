package com.djzass.medipoint.boundary_ui;

import android.support.v4.app.FragmentActivity;
import android.widget.Button;

import java.text.DateFormatSymbols;

/**
 * Created by Shreyas on 4/6/2015.
 * Passing datepicker data from fragment to Activity
 *
 * @author Shreyas
 * @version 1.0
 * @since 2015
 */

public class onDataPass extends FragmentActivity{
    /**
     * Passing data from DatePickerFragment to Activity button
     * @param date chosen from DatePickerFragment
     * @param month chosen from DatePickerFragment
     * @param year chosen from DatePickerFragment
     * @param button from which Activity the DatePickerFragment is called
     */
    public void DatePickerFragmentToActivity(int date,int month,int year,Button button){
        if(button!=null){
            //string representation for month
            String[] month_str = new DateFormatSymbols().getMonths();
            button.setText(date + " " + month_str[month] + " " + year);
        }
    }

}
