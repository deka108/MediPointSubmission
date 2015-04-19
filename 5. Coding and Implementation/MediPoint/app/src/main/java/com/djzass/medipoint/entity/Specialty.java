package com.djzass.medipoint.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ankur on 26/3/2015.
 * Entity class for Speciality
 *
 * @author Ankur
 * @version 1.0
 * @since 2015
 *
 * @see com.djzass.medipoint.logic_database.SpecialtyDAO,com.djzass.medipoint.logic_manager.SpecialtyManager
 */
public class Specialty implements Parcelable{
    /**
     * Speciality ID
     */
    private int id;

    /**
     * Speciality Name
     */
    private String name;

    /**
     * Empty Speciality constructor
     */
    public Specialty(){
    }

    /**
     * Speciality Class constructor
     * Creates a Speciality object
     * @param name Speciality Name
     */
    public Specialty(String name){
        this.name = name;
    }

    /**
     * Get Speciality using Parcelable
     * @param in specified parcel
     */
    public Specialty(Parcel in){
        readFromParcel(in);
    }

    /**
     * Convert Specialty object to a String
     * @return string containing speciality ID and Name
     */
    public String print(){
        String temp = "";
        temp+= id + " ";
        temp+= name + " \n";
        return temp;
    }

    /**
     * Get Speciality Name
     * @return speciality name
     */
    public String getName() {
        return name;
    }

    /**
     * Set Speciality Name
     * @param name Speciality name string
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get Speciality ID
     * @return speciality ID
     */
    public int getId() {
        return this.id;
    }

    /**
     * Set Speciality ID
     * @param id Speciality ID
     */
    public void setId(int id) {
        this.id = id;
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
        desc.writeInt(this.id);
        desc.writeString(this.name);
    }

    /**
     * Parcelable for creating Speciality object
     */
    public static final Parcelable.Creator<Specialty> CREATOR
            = new Parcelable.Creator<Specialty>() {
        public Specialty createFromParcel(Parcel in) {
            return new Specialty(in);
        }

        public Specialty[] newArray(int size) {
            return new Specialty[size];
        }
    };

    /**
     * get the info from a Parcel
     * @param in Parcel to be read
     */
    public void readFromParcel(Parcel in) {
        this.name = in.readString();
        this.id = in.readInt();
    }
}
