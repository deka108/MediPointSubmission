package com.djzass.medipoint.logic_database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.djzass.medipoint.entity.Patient;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Deka on 28/3/2015.
 */

/**
 * contains the database of the patient.
 * @author Stefan Artaputra Indriawan.
 *@version 1.
 * @since 2015.
 * @see com.djzass.medipoint.logic_database.DbDAO
 */
public class PatientDAO extends DbDAO{
    /**
     * database query for comparing Patient ID
     */
    private static final String WHERE_ID_EQUALS = DbContract.PatientEntry.COLUMN_NAME_PATIENT_ID
            + " =?";
    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);

    /**
     * Patient Database helper constructor
     * @param context {@link Context} Interface to global information about an application environment
     */
    public PatientDAO(Context context) throws SQLException {
        super(context);
    }

    /*
    CREATE
     Inserting doctor schedule into doctor schedules table and return the row id if insertion successful,
     otherwise \-1 will be returned
    IMPORTANT: For doctor & patient, ID is received in the passed object, not auto-increment
     */

    /**
     * insert Object patient to DB
     * @param patient {@link com.djzass.medipoint.entity.Patient} Patient object to be stored in DB
     * @return Long object containing info about the status of DB insertion
     */
    public long insertPatient(Patient patient){
       // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       // String patientDob = sdf.format(patient.getDob().getTime());

        ContentValues values = new ContentValues();
        values.put(DbContract.PatientEntry.COLUMN_NAME_PATIENT_ID, patient.getPatientId());
        values.put(DbContract.PatientEntry.COLUMN_NAME_DOB, patient.getDob().getTimeInMillis());
        values.put(DbContract.PatientEntry.COLUMN_NAME_AGE, patient.getAge());
        values.put(DbContract.PatientEntry.COLUMN_NAME_MEDICAL_HISTORY, patient.getMedicalHistory());
        values.put(DbContract.PatientEntry.COLUMN_NAME_ALLERGIES, patient.getAllergy());
        values.put(DbContract.PatientEntry.COLUMN_NAME_TREATMENTS, patient.getListOfTreatments());
        values.put(DbContract.PatientEntry.COLUMN_NAME_MEDICATIONS, patient.getListOfMedications());
        // Inserting Row
        return database.insert(DbContract.PatientEntry.TABLE_NAME, null, values);
    }

    /*
        READ
      * Getting all patients from the table
     * returns list of patients
     * */

    /**
     * Getting list of Patient from the table based on the condition passed
     * @param whereclause String object containing condition to find specific specialities
     * @return List of Patient.
     * */
     public List<Patient> getPatients(String whereclause) {
        List<Patient> patients = new ArrayList<Patient>();

        //MUST JOIN
        Cursor cursor = database.query(DbContract.PatientEntry.TABLE_NAME,
                new String[] {
                    DbContract.PatientEntry.COLUMN_NAME_PATIENT_ID,
                    DbContract.PatientEntry.COLUMN_NAME_DOB,
                    DbContract.PatientEntry.COLUMN_NAME_AGE,
                    DbContract.PatientEntry.COLUMN_NAME_MEDICAL_HISTORY,
                    DbContract.PatientEntry.COLUMN_NAME_ALLERGIES,
                    DbContract.PatientEntry.COLUMN_NAME_TREATMENTS,
                    DbContract.PatientEntry.COLUMN_NAME_MEDICATIONS
                }, whereclause, null, null, null,
                null);

        while (cursor.moveToNext()) {
            Calendar dob = Calendar.getInstance();
            try {
                dob.setTime(formatter.parse(cursor.getString(cursor.getColumnIndex(DbContract.AccountEntry.COLUMN_NAME_DOB))));
            } catch (ParseException e) { dob = null; }

            Patient patient= new Patient();
            patient.setPatientId(cursor.getInt(0));

            Calendar cal = Calendar.getInstance();
            Long c = cursor.getLong(1);
            cal.setTimeInMillis(c);
            patient.setDob(cal);
            patient.setAge(patient.getAge());
            patient.setMedicalHistory(cursor.getString(3));
            patient.setAllergy(cursor.getString(4));
            patient.setListOfTreatments(cursor.getString(5));
            patient.setListOfMedications(cursor.getString(6));

            patients.add(patient);
        }
        return patients;
    }

    /**
     * Get a list of all the Patient
     * @return List of all Doctor Objects
     */
    public List<Patient> getAllPatients(){
        return getPatients(null);
    }

    /**
     * Get list of Patient by PatientID
     * @param patientId {@link Integer} Patient ID
     * @return List of Patient Objects
     */
    public List<Patient> getPatientById(long patientId) {
        String whereclause = DbContract.PatientEntry.COLUMN_NAME_PATIENT_ID + " = " + patientId;
        return getPatients(whereclause);
    }


    /*
        UPDATE
       returns the number of rows affected by the update
     */

    /**
     * Update the Patient info in the Database
     * @param patient {@link Patient} Patient object to be updated
     * @return Long containing the result of update
     */
    public long update(Patient patient) {
        ContentValues values = new ContentValues();
        values.put(DbContract.PatientEntry.COLUMN_NAME_AGE, patient.getAge());
        values.put(DbContract.PatientEntry.COLUMN_NAME_DOB, patient.getDob().getTimeInMillis());
        values.put(DbContract.PatientEntry.COLUMN_NAME_MEDICAL_HISTORY, patient.getMedicalHistory());
        values.put(DbContract.PatientEntry.COLUMN_NAME_ALLERGIES, patient.getAllergy());
        values.put(DbContract.PatientEntry.COLUMN_NAME_TREATMENTS, patient.getListOfTreatments());
        values.put(DbContract.PatientEntry.COLUMN_NAME_MEDICATIONS, patient.getListOfMedications());

        long result = database.update(DbContract.PatientEntry.TABLE_NAME, values,
                WHERE_ID_EQUALS,
                new String[] { String.valueOf(patient.getPatientId()) });

        return result;
    }

    /*
        DELETE
        returns the number of rows affected if a whereClause is passed in, 0 otherwise
     */

    /**
     * Delete the Patient info in the Database
     * @param patient {@link Patient} Patient object to be deleted
     * @return Long containing the result of deletion
     */
    public int deletePatient(Patient patient) {
        return database.delete(DbContract.PatientEntry.TABLE_NAME,
                WHERE_ID_EQUALS, new String[] { patient.getPatientId() + "" });
    }
    /*
        LOAD
        Load the initial values of the patients
     */

    /**
     * load all of the initial values of the patients.
     */
    public void loadPatients() {
        List<Patient> temp = getAllPatients();
        for (Patient tmp : temp) {
            tmp.print();
        }
    }

    /**
     * Count the total number of tuples in Patient Relation
     * @return int containing the number of Patient
     */
    public int getPatientCount(){
        return getAllPatients().size();
    }

    /**
     * Initialize the Database if the patient is empty in the beginning.
     */
    private void initializeDAO(){
        if (getPatientCount()==0){
        }
    }
}