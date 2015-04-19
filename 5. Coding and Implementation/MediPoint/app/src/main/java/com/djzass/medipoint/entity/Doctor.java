package com.djzass.medipoint.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Deka on 26/3/2015.
 */

/**
 * contains the information of the doctor.
 * @author Stefan Artaputra Indriawan.
 *@version 1.
 * @since 2015.
 * @see android.os.Parcelable
 */
public class Doctor implements Parcelable{
    private int DoctorId;
    private String name;
    private int specializationId;
    private int practiceDuration;
    private int  clinicId;

    //Empty Doctor constructor
    public Doctor(){
    }

    /**
     * Constructor without clinicId
     * @param name {@link String} name of the doctor.
     * @param specialization {@link com.djzass.medipoint.entity.Specialty} specialty of the doctor.
     * @param clinic {@link com.djzass.medipoint.entity.Clinic} clinic of the doctor.
     * @param practiceDuration {@link Integer} how long is the doctor's practice duration.
     */
    public Doctor(String name, Specialty specialization,Clinic clinic,
                  int practiceDuration) {
        this.name = name;
        this.specializationId = specialization.getId();
        this.clinicId = clinic.getId();
    }

    /**
     * Constructor without clinic and specialization.
     * @param name {@link String} name of the doctor.
     * @param specializationId {@link Integer} id of specialty of the doctor.
     * @param clinicId {@link Integer} id of clinic of the doctor.
     * @param practiceDuration {@link Integer} how long is the doctor's practice duration.
     */
    public Doctor(String name, int specializationId, int practiceDuration, int clinicId) {
        this.name = name;
        this.specializationId = specializationId;
        this.practiceDuration = practiceDuration;
        this.clinicId = clinicId;
    }

    /**
     * Get Doctor ID
     * @return int Doctor ID
     */
    public int getDoctorId() {
        return this.DoctorId;
    }

    /**
     * Set Doctor ID
     * @param doctorId {@link Integer}doctor ID
     */
    public void setDoctorId(int doctorId) {
        this.DoctorId = doctorId;
    }

    /**
     * Get Name of the doctor.
     * @return name {@link String}
     */
    public String getName() {
        return name;
    }

    /**
     * set Name of the doctor.
     * @param name {@link String}
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get Specialty ID of the doctor.
     * @return specializationId {@link Integer}
     */
    public int getSpecializationId() {
        return this.specializationId;
    }

    /**
     * SetSpecialty ID of the doctor.
     * @param  specializationId {@link Integer}
     */
    public void setSpecializationId(int specializationId) {
        this.specializationId = specializationId;
    }

    /**
     * Get Duration of the doctor.
     * @return practiceDuration {@link Integer}
     */
    public int getPracticeDuration() {
        return practiceDuration;
    }

    /**
     * Set Duration of the doctor.
     * @param practiceDuration {@link Integer}
     */
    public void setPracticeDuration(int practiceDuration) {
        this.practiceDuration = practiceDuration;
    }


    /**
     * Get clinic ID of the doctor.
     * @return clinicId {@link Integer}
     */
    public int getClinicId()
    {
        return this.clinicId;
    }

    /**
     * Set Clinic ID of the doctor.
     * @param clinicId {@link Integer}
     */
    public void setClinicId(int clinicId)
    {
        this.clinicId = clinicId;
    }

    /**
     * print the doctor info.
     * @return doctor info {@link String}
     */
    public String print(){
        return 	"=== Printing Doctor Info ==="+"\n"+
                "ID: " + this.DoctorId + "\n" +
                "Name: " + this.name + "\n" +
                "Practice Duration: " + this.practiceDuration+ "\n" +
                "Specialization: " + this.specializationId + "\n"
                ;
    }

    /**
     * Get Doctor from Parcelable Parcel
     * @param in specified parcel
     */
    public Doctor(Parcel in){
        readFromParcel(in);
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
     * put Doctor info to parcel
     * @param desc The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     */
    @Override
    public void writeToParcel(Parcel desc, int flags) {
        desc.writeInt(this.DoctorId);
        desc.writeInt(this.specializationId);
        desc.writeInt(this.practiceDuration);
        desc.writeString(this.name);
    }

    /**
     * Parcelable for creating Doctor object
     */
    public static final Parcelable.Creator<Doctor> CREATOR
            = new Parcelable.Creator<Doctor>() {
        public Doctor createFromParcel(Parcel in) {
            return new Doctor(in);
        }

        public Doctor[] newArray(int size) {
            return new Doctor[size];
        }
    };

    /**
     * get the info from a Parcel
     * @param in Parcel to be read
     */
    public void readFromParcel(Parcel in) {
        this.DoctorId = in.readInt();
        this.specializationId = in.readInt();
        this.practiceDuration = in.readInt();
        this.name = in.readString();
    }
}
