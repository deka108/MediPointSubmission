package com.djzass.medipoint.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Joshua on 25/3/2015.
 *
 * @author Joshua
 * @since 2015
 * @version 1.0
 *
 * This class represents time of day (hour/min) in form of integer (0..47)
 * 0 represents 0:00, 47 represents 23:30
 * Used to simplify things since the app calculates time in 30-min increments
 */

public class Timeframe implements Parcelable{

    /**
     * Starting time (0..47, 0:00-23:30 in 30-min increments)
     */
    private int startTime;

    /**
     * Ending time (0..47, 0:00-23:30 in 30-min increments)
     */
    private int endTime;

    /**
     * Constructor from two integers
     * @param startTime start time
     * @param endTime end time
     */
    public Timeframe(int startTime, int endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Constructor from string
     * @param inp Time period in string format ("XX:00/30 - YY:00/30")
     */
    public Timeframe(String inp){
        int s1,s2,e1,e2;
        s1 = Integer.parseInt(inp.split(" - ")[0].split(":")[0]);
        s2 = Integer.parseInt(inp.split(" - ")[0].split(":")[1]);
        e1 = Integer.parseInt(inp.split(" - ")[1].split(":")[0]);
        e2 = Integer.parseInt(inp.split(" - ")[1].split(":")[1]);
        this.startTime  = s1*2 + (s2/30);
        this.endTime    = e1*2 + (e2/30);
    }

    /**
     * Constructor from Parcelable Parcel
     * @param in specified parcel
     */
    public Timeframe(Parcel in){
        readFromParcel(in);
    }

    /**
     * Get StartTime
     * @return StartTime
     */
    public int getStartTime(){
        return startTime;
    }

    /**
     * Set StartTime
     * @param startTime StartTime
     */
    public void setStartTime (int startTime){
        this.startTime = startTime;
    }

    /**
     * Get EndTime
     * @return EndTime
     */
    public int getEndTime(){
        return endTime;
    }

    /**
     * Set EndTime
     * @param endTime EndTime
     */
    public void setEndTime (int endTime){
        this.endTime = endTime;
    }

    /**
     * Converts a 48-time format into an Hour string ("XX:00/30")
     * @param tim input number in 48-time format
     */
    public static String getString(int tim){
        if (tim % 2 == 0)
            return String.format("%02d:%02d", tim/2, 00);
        else
            return String.format("%02d:%02d", tim/2, 30);
    }

    /**
     * Returns this timeframe in string format ("XX:00/30 - YY:00/30")
     */
    public String getTimeLine(){
        return getString(startTime) + " - " + getString(endTime);
    }

    /**
     * Returns the Hour value from a 48-time format
     * @param time input number in 48-time format
     * @return hour
     */
    public static int getHour(int time) {
        return time/2;
    }

    /**
     * Returns the Minute value from a 48-time format
     * @param time input number in 48-time format
     * @return minute
     */
    public static int getMinute(int time) {
        return 30*(time%2);
    }

    /**
     * override method in Parcelable
     * @return a bitmask indicating the set of special object types marshaled by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * put TimeFrame info to parcel
     * @param time The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     */
    @Override
    public void writeToParcel(Parcel time, int flags) {
        time.writeInt(this.startTime);
        time.writeInt(this.endTime);
    }

    /**
     * Parcelable for creating Speciality object
     */
    public static final Parcelable.Creator<Timeframe> CREATOR
            = new Parcelable.Creator<Timeframe>() {
        public Timeframe createFromParcel(Parcel in) {
            return new Timeframe(in);
        }

        public Timeframe[] newArray(int size) {
            return new Timeframe[size];
        }
    };

    /**
     * get the info from a Parcel
     * @param in Parcel to be read
     */
    public void readFromParcel(Parcel intime) {
        this.startTime = intime.readInt();
        this.endTime = intime.readInt();
    }

}
