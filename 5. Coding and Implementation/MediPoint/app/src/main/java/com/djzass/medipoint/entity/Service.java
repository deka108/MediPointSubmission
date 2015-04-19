package com.djzass.medipoint.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Deka on 26/3/2015.
 * Service class
 */
public class Service implements Parcelable {
    /**
     * service id
     */
    private int serviceId;

    /**
     * service specialty identifier
     */
    private int specialtyId;

    /**
     * service name
     */
    private String name;

    /**
     * service duration
     */
    private int duration;

    /**
     * service preppointment action
     */
    private String preAppointmentActions;

    /**
     * Service constructor
     */
    public Service(){
        this.duration = 1;
        this.preAppointmentActions = "None";
    }

    /**
     * Service Constructor
     * @param name name of Service
     * @param specialtyId id of the specialty
     */
    public Service(String name, int specialtyId) {
        this.name = name;
        this.specialtyId = specialtyId;

        //The regular timing is 30 minutes
        this.duration = 1;
        this.preAppointmentActions = "None";
    }

    /**
     * Service Constructor
     * @param name name of Service
     * @param specialtyId id of the specialty
     * @param duration duration of Service
     */
    public Service(String name, int specialtyId, int duration) {
        this.name = name;
        this.specialtyId = specialtyId;
        this.duration = duration;
        this.preAppointmentActions = "None";
    }

    /**
     * Service Constructor
     * @param name name of Service
     * @param specialtyId id of the specialty
     * @param duration duration of Service
     * @param preAppointmentAction action before the service
     */
    public Service(String name, int specialtyId, int duration, String preAppointmentAction) {
        this.name = name;
        this.specialtyId = specialtyId;
        this.duration = duration;
        this.preAppointmentActions = preAppointmentAction;
    }

    /**
     * return String object containing Service details
     * @return details of Service
     */
    public String print(){
        String temp = "";
        temp+= serviceId + " ";
        temp+= specialtyId + " ";
        temp+= name + " ";
        temp+= duration + " ";
        temp+= preAppointmentActions + " ";
        return temp;
    }

    /**
     * get int object which will be used to identify the Service
     * @return service id
     */
    public int getId() {
        return serviceId;
    }

    /**
     * set the identifier of the Service
     * @param serviceId int object containing id of the Service
     */
    public void setId(int serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * get String object containing Service name
     * @return service name
     */
    public String getName() {
        return name;
    }

    /**
     * set the Service name
     * @param name String object containing Service name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get int object containing the service duration
     * @return service duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * set the duration for the service
     * @param duration int object containing service duration
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * get int object containing identifier for specialty
     * @return specialty id
     */
    public int getSpecialtyId() {
        return specialtyId;
    }

    /**
     * set the id of the specialty
     * @param specialtyId int object containing specialty id
     */
    public void setSpecialtyId(int specialtyId) {
        this.specialtyId = specialtyId;
    }

    /**
     * return String object containing preappointment actions
     * @return preappointment actions
     */
    public String getPreAppointmentActions() {
        return preAppointmentActions;
    }

    /**
     * set preappointment actions for Service
     * @param preAppointmentActions String object containing preappointment actions
     */
    public void setPreAppointmentActions(String preAppointmentActions) {
        this.preAppointmentActions = preAppointmentActions;
    }

    /**
     * get Service object from Parcelable
     * @param in specified Parcel
     */
    public Service(Parcel in){
        readFromParcel(in);
    }

    /**
     * override method in Parcelable
     * @return a bitmask indicating the set of special object types marshalled by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * put Service info to parcel
     * @param desc The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     */
    @Override
    public void writeToParcel(Parcel desc, int flags) {
        desc.writeInt(this.serviceId);
        desc.writeInt(this.specialtyId);
        desc.writeString(this.name);
        desc.writeInt(this.duration);
        desc.writeString(this.preAppointmentActions);
        /*private Timeframe timeframe;*/
    }

    /**
     * Parcelable for creating Service object
     */
    public static final Parcelable.Creator<Service> CREATOR
            = new Parcelable.Creator<Service>() {
        public Service createFromParcel(Parcel in) {
            return new Service(in);
        }

        public Service[] newArray(int size) {
            return new Service[size];
        }
    };

    /**
     * get the info from a Parcel
     * @param in Parcel to be read
     */
    public void readFromParcel(Parcel in) {
        this.serviceId = in.readInt();
        this.specialtyId = in.readInt();
        this.name = in.readString();
        this.duration = in.readInt();
        this.preAppointmentActions = in.readString();
    }
}
