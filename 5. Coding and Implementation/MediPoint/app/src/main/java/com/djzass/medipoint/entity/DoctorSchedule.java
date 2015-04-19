package com.djzass.medipoint.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Deka on 26/3/2015.
 *
 * @author Joshua
 * @since 2015
 * @version 1.0
 */

public class DoctorSchedule implements Parcelable {
    /**
     * DoctorSchedule ID
     */
    private int scheduleId;
    /**
     * Doctor ID
     */
    private int doctorId;
    /**
     * Clinic ID
     */
    private int clinicId;
    /**
     * Day of week ("Monday", "Tuesday", ...)
     */
    private String day;
    /**
     * Timeframe for which the doctor is available
     */
    private Timeframe timeframe;

    /**
     * Empty DoctorSchedule constructor
     */
    public DoctorSchedule(){

    }

    /**
     * Constructor
     * @param doctorId DoctorID
     * @param clinicId ClinicID
     * @param day Date
     * @param timeframe Timeframe
     */
    public DoctorSchedule(int doctorId, int clinicId, String day, Timeframe timeframe) {
        this.doctorId = doctorId;
        this.clinicId = clinicId;
        this.day = day;
        this.timeframe = timeframe;
    }

    /**
     * Get DoctorSchedule from Parcelable Parcel
     * @param in specified parcel
     */
    public DoctorSchedule(Parcel in){
        readFromParcel(in);
    }
    /**
     * Convert DoctorSchedule object to a String
     * @return string containing all DoctorSchedule data
     */
    public String print(){
        String temp = "";
        temp+= scheduleId + " ";
        temp+= doctorId + " ";
        temp+= clinicId + " ";
        temp+= day + " ";
        temp+= timeframe.getStartTime() + "-" + timeframe.getEndTime();
        return temp;
    }

    /**
     * Get DoctorSchedule ID
     * @return DoctorSchedule ID
     */
    public int getId() {
        return scheduleId;
    }

    /**
     * Set DoctorSchedule ID
     * @param id DoctorSchedule ID
     */
    public void setId(int id) {
        this.scheduleId = id;
    }

    /**
     * Get DoctorId
     * @return DoctorId
     */
    public int getDoctorId() {
        return doctorId;
    }

    /**
     * Set DoctorId
     * @param doctorId DoctorId
     */
    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    /**
     * Get ClinicId
     * @return ClinicId
     */
    public int getClinicId() {
        return clinicId;
    }

    /**
     * Set ClinicId
     * @param clinicId ClinicId
     */
    public void setClinicId(int clinicId) {
        this.clinicId = clinicId;
    }

    /**
     * Get Day of week
     * @return Day of week
     */
    public String getDay() {
        return day;
    }

    /**
     * Set Day of week
     * @param day Day of week
     */
    public void setDay(String day) {
        this.day = day;
    }

    /**
     * Get Timeframe
     * @return Timeframe
     */
    public Timeframe getTimeframe() {
        return timeframe;
    }

    /**
     * Set Timeframe
     * @param timeframe Timeframe
     */
    public void setTimeframe(Timeframe timeframe) {
        this.timeframe = timeframe;
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
     * put Speciality info to parcel
     * @param desc The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     */
    @Override
    public void writeToParcel(Parcel desc, int flags) {
        desc.writeInt(this.doctorId);
        desc.writeInt(this.scheduleId);
        desc.writeInt(this.clinicId);
        desc.writeString(this.day);
        /*private Timeframe timeframe;*/
    }

    /**
     * Parcelable for creating Speciality object
     */
    public static final Parcelable.Creator<DoctorSchedule> CREATOR
            = new Parcelable.Creator<DoctorSchedule>() {
        public DoctorSchedule createFromParcel(Parcel in) {
            return new DoctorSchedule(in);
        }

        public DoctorSchedule[] newArray(int size) {
            return new DoctorSchedule[size];
        }
    };

    /**
     * get the info from a Parcel
     * @param in Parcel to be read
     */
    public void readFromParcel(Parcel in) {
        this.doctorId = in.readInt();
        this.scheduleId = in.readInt();
        this.clinicId = in.readInt();
        this.day = in.readString();
    }
}
