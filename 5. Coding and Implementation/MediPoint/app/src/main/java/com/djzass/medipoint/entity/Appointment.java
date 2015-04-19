package com.djzass.medipoint.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.lang.String;
import java.util.Comparator;

/**
 * Created by Joshua on 20/3/2015.
 *
 * @author Joshua
 * @since 2015
 * @version 1.0
 */

public class Appointment implements Parcelable{
    /**
     * Appointment ID
     */
    private int appointmentId;
    /**
     * Patient ID
     */
    private int patientId;
    /**
     * Clinic ID
     */
    private int clinicId;
    /**
     * Referrer ID
     */
    private int referrerId;
    /**
     * Speciality ID
     */
    private int specialtyId;
    /**
     * Service ID
     */
    private int serviceId;
    /**
     * Doctor ID
     */
    private int doctorId;
    /**
     * Appointment Date
     */
    private Calendar date;
    /**
     * Appointment Timeframe
     */
    private Timeframe timeframe;
    /**
     * Pre-appointment Actions
     */
    private String preAppointmentActions;

    /**
     * Empty Appointment constructor
     */
    public Appointment() {}

    /**
     * Constructor - No referral, no pre-appointment action
     * @param patientId PatientID
     * @param clinicId ClinicID
     * @param specialtyId SpecialtyID
     * @param serviceId ServiceID
     * @param doctorId DoctorID
     * @param date Date
     * @param timeframe Timeframe
     */
    public Appointment(int patientId, int clinicId, int specialtyId, int serviceId, int doctorId,
                       Calendar date, Timeframe timeframe) {
        //this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.clinicId = clinicId;
        this.specialtyId = specialtyId;
        this.serviceId = serviceId;
        this.doctorId = doctorId;
        this.referrerId = -1;
        this.date = date;
        this.timeframe = timeframe;
        timeframe.getStartTime();
        this.date.set(Calendar.HOUR_OF_DAY, timeframe.getStartTime() / 2);
        this.date.set(Calendar.MINUTE, 30 * (timeframe.getStartTime() % 2));
        this.preAppointmentActions = "None";
    }

    /**
     * Constructor - No referral
     * @param patientId PatientID
     * @param clinicId ClinicID
     * @param specialtyId SpecialtyID
     * @param serviceId ServiceID
     * @param doctorId DoctorID
     * @param date Date
     * @param timeframe Timeframe
     * @param preAppointmentActions Pre-appointment Actions
     */
    public Appointment(int patientId, int clinicId, int specialtyId, int serviceId, int doctorId,
                       Calendar date, Timeframe timeframe, String preAppointmentActions) {
        //this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.clinicId = clinicId;
        this.specialtyId = specialtyId;
        this.serviceId = serviceId;
        this.doctorId = doctorId;
        this.referrerId = -1;
        this.date = date;
        this.timeframe = timeframe;
        this.date.set(Calendar.HOUR_OF_DAY, timeframe.getStartTime() / 2);
        this.date.set(Calendar.MINUTE, 30 * (timeframe.getStartTime() % 2));
        this.preAppointmentActions = preAppointmentActions;
    }

    /**
     * Constructor - Referral, no pre-appointment action
     * @param patientId PatientID
     * @param clinicId ClinicID
     * @param specialtyId SpecialtyID
     * @param serviceId ServiceID
     * @param doctorId DoctorID
     * @param referrerId ReferrerID
     * @param date Date
     * @param timeframe Timeframe
     */
    public Appointment(int patientId, int clinicId, int specialtyId, int serviceId, int doctorId, int referrerId,
                       Calendar date, Timeframe timeframe) {
        //this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.clinicId = clinicId;
        this.specialtyId = specialtyId;
        this.serviceId = serviceId;
        this.doctorId = doctorId;
        this.referrerId = referrerId;
        this.date = date;
        this.timeframe = timeframe;
        this.date.set(Calendar.HOUR_OF_DAY, timeframe.getStartTime() / 2);
        this.date.set(Calendar.MINUTE, 30 * (timeframe.getStartTime() % 2));
        this.preAppointmentActions = "None";
    }

    /**
     * Constructor - Referral, pre-appointment action
     * @param patientId PatientID
     * @param clinicId ClinicID
     * @param specialtyId SpecialtyID
     * @param serviceId ServiceID
     * @param doctorId DoctorID
     * @param referrerId ReferrerID
     * @param date Date
     * @param timeframe Timeframe
     * @param preAppointmentActions Pre-appointment Actions
     */
    public Appointment(int patientId, int clinicId, int specialtyId, int serviceId, int doctorId, int referrerId,
                       Calendar date, Timeframe timeframe, String preAppointmentActions) {
        //this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.clinicId = clinicId;
        this.specialtyId = specialtyId;
        this.serviceId = serviceId;
        this.doctorId = doctorId;
        this.referrerId = referrerId;
        this.date = date;
        this.timeframe = timeframe;
        this.date.set(Calendar.HOUR_OF_DAY, timeframe.getStartTime() / 2);
        this.date.set(Calendar.MINUTE, 30 * (timeframe.getStartTime() % 2));
        this.preAppointmentActions = preAppointmentActions;
    }

    /**
     * Get Appointment from Parcelable Parcel
     * @param in specified parcel
     */
    public Appointment(Parcel in){
        readFromParcel(in);
    }

    /**
     * Convert Appointment object to a String
     * @return string containing all Appointment data
     */
    public String toString(){
        String temp = "";
        temp+= "Appointment Id: " + appointmentId + "\n";
        temp+= "Patient Id: " + patientId + "\n";
        temp+= "Clinic Id: " + clinicId + "\n";
        temp+= "Referrer Id: " + referrerId + "\n";
        temp+= "Specialty Id: " + specialtyId + "\n";
        temp+= "Service Id: " + serviceId + "\n";
        temp+= "Doctor Id: " + doctorId + "\n";
        temp+= "Date: " + String.valueOf(date) + "\n";
        temp+= "Time: " + timeframe.getStartTime() + "-" + timeframe.getEndTime() + "\n";
        temp+= "Preappointment Actions: " + preAppointmentActions + "\n";
        return temp;
    }

    /**
     * Get Appointment ID
     * @return int Appointment ID
     */
    public int getId() {
        return appointmentId;
    }

    /**
     * Set Appointment ID
     * @param appointmentId Appointment ID
     */
    public void setId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Get Patient ID
     * @return int Patient ID
     */
    public int getPatientId() {
        return this.patientId;
    }

    /**
     * Set Patient ID
     * @param patientId  int Patient ID
     */
    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    /**
     * Get Specialty ID
     * @return int Specialty ID
     */
    public int getSpecialtyId() {
        return specialtyId;
    }

    /**
     * Set Specialty ID
     * @param specialtyId  int Specialty ID
     */
    public void setSpecialtyId(int specialtyId) {
        this.specialtyId = specialtyId;
    }

    /**
     * Get Clinic ID
     * @return int Clinic ID
     */
    public int getClinicId() {
        return this.clinicId;
    }

    /**
     * Set Clinic ID
     * @param clinicId  int Clinic ID
     */
    public void setClinicId(int clinicId) {
        this.clinicId = clinicId;
    }

    /**
     * Get Doctor ID
     * @return int Doctor ID
     */
    public int getDoctorId() {
        return this.doctorId;
    }

    /**
     * Set Doctor ID
     * @param doctorId  int Doctor ID
     */
    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    /**
     * Get Referrer ID
     * @return int Referrer ID
     */
    public int getReferrerId() {
        return referrerId;
    }

    /**
     * Set Referrer ID
     * @param referrerId  int Referrer ID
     */
    public void setReferrerId(int referrerId) {
        this.referrerId = referrerId;
    }

    /**
     * Get Service ID
     * @return int Service ID
     */
    public int getServiceId() {
        return serviceId;
    }

    /**
     * Set Service ID
     * @param serviceId  int Service ID
     */
    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * Get Appointment Date
     * @return Appointment Date
     */
    public Calendar getDate() {
        return date;
    }

    /**
     * Get Appointment Date as String
     * @return String Appointment Date
     */
    public String getDateString(){
        //SimpleDateFormat sdfDate = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        SimpleDateFormat sdfDate = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        return sdfDate.format(this.date.getTime());
    }

    /**
     * Set Appointment Date
     * @param date input Appointment Date
     */
    public void setDate(Calendar date) {
        this.date = date;
    }

    /**
     * Get Appointment Timeframe
     * @return Timeframe Appointment Timeframe
     */
    public Timeframe getTimeframe() {
        return this.timeframe;
    }

    /**
     * Get Appointment Timeframe as String
     * @return String Appointment Timeframe
     */
    public String getTimeString(){
        return this.timeframe.getTimeLine();
    }

    /**
     * Set Appointment Timeframe
     * @param timeframe input Appointment Timeframe
     */
    public void setTimeframe(Timeframe timeframe) {
        this.timeframe = timeframe;
        this.date.set(Calendar.HOUR_OF_DAY, timeframe.getStartTime() / 2);
        this.date.set(Calendar.MINUTE, 30 * (timeframe.getStartTime() % 2));
    }

    /**
     * Get PreAppointment Actions
     * @return string PreAppointment Actions
     */
    public String getPreAppointmentActions() {
        return this.preAppointmentActions;
    }

    /**
     * Set PreAppointment Actions
     * @param preAppointmentActions Input pre-appointment actions
     */
    public void setPreAppointmentActions(String preAppointmentActions) {
        this.preAppointmentActions = preAppointmentActions;
    }

    /**
     * Comparator function
     * Used to sort Appointments based on date
     */
    public static Comparator<Appointment> AppSortByDate
            = new Comparator<Appointment>() {
        public int compare(Appointment app1, Appointment app2) {
            return app1.getDate().compareTo(app2.getDate());
            //returns negative is app1's date < app2's date
            //sort by earliest
        }
    };

    /**
     * Comparator function
     * Used to sort Appointments based on specialty
     */
    public static Comparator<Appointment> AppSortBySpecialty
            = new Comparator<Appointment>() {
        public int compare(Appointment app1, Appointment app2) {
            return app1.getSpecialtyId() - app2.getSpecialtyId();
            //returns negative is app1.specialtyID < app2.specialtyID
            //sort ascening
        }
    };

    /**
     * override method in Parcelable
     * @return a bitmask indicating the set of special object types marshaled by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * put Appointment info to parcel
     * @param dest The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.appointmentId);
        dest.writeInt(this.patientId);
        dest.writeInt(this.clinicId);
        dest.writeInt(this.specialtyId);
        dest.writeInt(this.serviceId);
        dest.writeInt(this.doctorId);
        dest.writeInt(this.referrerId);
        long cal = this.getDate().getTimeInMillis();
        dest.writeLong(cal);
        dest.writeInt(this.getTimeframe().getStartTime());
        dest.writeInt(this.getTimeframe().getEndTime());
        dest.writeString(this.preAppointmentActions);
    }

    /**
     * Parcelable for creating Appointment object
     */
    public static final Parcelable.Creator<Appointment> CREATOR
            = new Parcelable.Creator<Appointment>() {
        public Appointment createFromParcel(Parcel in) {
            return new Appointment(in);
        }

        public Appointment[] newArray(int size) {
            return new Appointment[size];
        }
    };

    /**
     * get the info from a Parcel
     * @param in Parcel to be read
     */
    public void readFromParcel(Parcel in){
        this.appointmentId = in.readInt();
        this.patientId = in.readInt();
        this.clinicId = in.readInt();
        this.specialtyId = in.readInt();
        this.serviceId = in.readInt();
        this.doctorId = in.readInt();
        this.referrerId = in.readInt();
        this.date = Calendar.getInstance();
        this.date.setTimeInMillis(in.readLong());
        this.timeframe = new Timeframe(in.readInt(),in.readInt());
        this.preAppointmentActions = in.readString();
    }

}