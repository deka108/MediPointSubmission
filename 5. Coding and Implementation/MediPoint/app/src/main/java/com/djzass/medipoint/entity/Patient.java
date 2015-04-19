package com.djzass.medipoint.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Calendar;

/**
 * Created by Deka on 25/3/2015.
 */

/**
 * contains the information of the doctor.
 * @author Stefan Artaputra Indriawan.
 *@version 1.
 * @since 2015.
 * @see android.os.Parcelable
 */
public class Patient implements Parcelable{
    private int patientId;
    private Calendar dob;
    private int age;
    private String allergy;
    private String medicalHistory;
    private String listOfTreatments;
    private String listOfMedications;

    //Empty Patient constructor
    public Patient(){
        super();
    };

    /**
     * Constructor for Patient
     * @param id {@link Integer} Id of the patient..
     * @param dob {@link Calendar} date of birth of the patient.
     * @param medicalHistory {@link String} medical history of the Patient.
     * @param listOfTreatments {@link String} list of the patient treatments.
     * @param listOfMedications {@link String} list of the patient medications.
     * @param allergy {@link String} allergy of the patients.
     */
    public Patient(int id, Calendar dob, String medicalHistory, String listOfTreatments, String listOfMedications, String allergy) {
        this.patientId = id;
        this.dob = dob;
        this.medicalHistory = medicalHistory;
        this.age = getAge();
        this.listOfTreatments = listOfTreatments;
        this.listOfMedications = listOfMedications;
        this.allergy = allergy;
    }

    /**
     * Constructor for Patient without id.
     * @param dob {@link Calendar} date of birth of the patient.
     * @param medicalHistory {@link String} medical history of the Patient.
     * @param listOfTreatments {@link String} list of the patient treatments.
     * @param listOfMedications {@link String} list of the patient medications.
     * @param allergy {@link String} allergy of the patients.
     */
    public Patient(Calendar dob, String medicalHistory, String listOfTreatments, String listOfMedications, String allergy) {
        this.dob = dob;
        this.age = getAge();
        this.medicalHistory = medicalHistory;
        this.listOfTreatments = listOfTreatments;
        this.listOfMedications = listOfMedications;
        this.allergy = allergy;
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
     * @param patientID {@link Integer}Patient ID
     */
    public void setPatientId(int patientID) {
        this.patientId = patientID;
    }

    /**
     * Get Patient age
     * @return int age
     */
    public int getAge( ) {
        Calendar now = Calendar.getInstance();
        int age =  now.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if ( (dob.get(Calendar.MONTH) > now.get(Calendar.MONTH)) || ((dob.get(Calendar.MONTH) == now.get(Calendar.MONTH)) && (dob.get(Calendar.DATE) > now.get(Calendar.DATE))) ){
            age--;
        }

        return age;
    }

    /**
     * Get Patient date of birth.
     * @return int dob.
     */
    public Calendar getDob() {
        return dob;
    }

    /**
     * set Patient date of birth.
     *  @param dob {@link Calendar}Patient date of birth
     */
    public void setDob(Calendar dob) {
        this.dob = dob;
    }

    /**
     * print the patient info.
     * @return temp{@link String}
     */
    public String print(){
        String temp = "";
        temp+= patientId + " ";
        temp+= String.valueOf(dob) + " ";
        temp+= allergy + " ";
        temp+= medicalHistory + " ";
        temp+= listOfTreatments + " ";
        temp+= listOfMedications + " ";
        return temp;
    }

    /**
     * Set patient age
     * @param age {@link Integer}Patient age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Get Patient list of treatments.
     * @return int listOfTreatments.
     */
    public String getListOfTreatments() {
        return listOfTreatments;
    }

    /**
     * Set patient treatments
     * @param listOfTreatments {@link String}Patient list of treatments
     */
    public void setListOfTreatments(String listOfTreatments) {
        this.listOfTreatments = listOfTreatments;
    }

    /**
     * Get Patient list of medications.
     * @return int listOfMedications.
     */
    public String getListOfMedications() {
        return listOfMedications;
    }

    /**
     * Set patient medications
     * @param listOfMedications {@link String}Patient list of medications.
     */
    public void setListOfMedications(String listOfMedications) {
        this.listOfMedications = listOfMedications;
    }
    /**
     * Get Patient allergy.
     * @return int allergy.
     */
    public String getAllergy() {
        return allergy;
    }

    /**
     * Set patient allergy.
     * @param allergy {@link String}Patient allergy.
     */
    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    /**
     * Get Patient medical history.
     * @return int medicalHistory.
     */
    public String getMedicalHistory() {
        return medicalHistory;
    }

    /**
     * Set patient medical history
     * @param medicalHistory {@link String}Patient medical history.
     */
    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    /**
     * Get patient from Parcelable Parcel
     * @param in specified parcel
     */
    public Patient(Parcel in){
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
     * put patient info to parcel
     * @param desc The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     */
    @Override
    public void writeToParcel(Parcel desc, int flags) {
        desc.writeInt(this.patientId);
        desc.writeSerializable(this.dob);
        desc.writeInt(this.age);
        desc.writeString(this.allergy);
        desc.writeString(this.medicalHistory);
        desc.writeString(this.listOfTreatments);
        desc.writeString(this.listOfMedications);
        /*private Timeframe timeframe;*/
    }

    /**
     * Parcelable for creating patient object
     */
    public static final Parcelable.Creator<Patient> CREATOR
            = new Parcelable.Creator<Patient>() {
        public Patient createFromParcel(Parcel in) {
            return new Patient(in);
        }

        public Patient[] newArray(int size) {
            return new Patient[size];
        }
    };

    /**
     * get the info from a Parcel
     * @param in Parcel to be read
     */
    public void readFromParcel(Parcel in) {
        this.patientId = in.readInt();
        this.dob = (Calendar)in.readSerializable();
        this.age = in.readInt();
        this.allergy = in.readString();
        this.medicalHistory = in.readString();
        this.listOfTreatments = in.readString();
        this.listOfMedications = in.readString();
    }
}
