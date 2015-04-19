package com.djzass.medipoint.logic_database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.lang.String;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.djzass.medipoint.entity.Timeframe;
import com.djzass.medipoint.entity.Appointment;

/**
 * Created by Deka on 26/3/2015.
 *
 * @author Joshua
 * @since 2015
 * @version 1.0
 */
public class AppointmentDAO extends DbDAO{

    /**
     * database query for comparing Appointment ID
     */
    private static final String WHERE_ID_EQUALS = DbContract.AppointmentEntry.COLUMN_NAME_APPOINTMENT_ID + " =?";

    /**
     * Appointment Database helper constructor
     * @param context Interface to global information about an application environment
     */
    public AppointmentDAO(Context context) throws SQLException {
        super(context);
    }

    /**
     * insert Object Appointment to DB
     * @param appointment Appointment object to be stored in DB
     * @return Long object containing info about the status of DB insertion
     */
    public long insertAppointment(Appointment appointment) {
        ContentValues values = new ContentValues();
        //values.put(DbContract.AppointmentEntry.COLUMN_NAME_APPOINTMENT_ID, getAppointmentCount());
        values.put(DbContract.AppointmentEntry.COLUMN_NAME_CLINIC_ID, appointment.getClinicId());
        values.put(DbContract.AppointmentEntry.COLUMN_NAME_PATIENT_ID, appointment.getPatientId());
        values.put(DbContract.AppointmentEntry.COLUMN_NAME_DOCTOR_ID, appointment.getDoctorId());
        values.put(DbContract.AppointmentEntry.COLUMN_NAME_REFERRER_ID, appointment.getReferrerId());
       // values.put(DbContract.AppointmentEntry.COLUMN_NAME_DATE_TIME, appointment.getDateString());

        values.put(DbContract.AppointmentEntry.COLUMN_NAME_DATE_TIME, appointment.getDate().getTimeInMillis());
        values.put(DbContract.AppointmentEntry.COLUMN_NAME_SERVICE_ID, appointment.getServiceId());
        values.put(DbContract.AppointmentEntry.COLUMN_NAME_SPECIALTY_ID,appointment.getSpecialtyId());
        values.put(DbContract.AppointmentEntry.COLUMN_NAME_PREAPPOINTMENT_ACTIONS, appointment.getPreAppointmentActions());
        values.put(DbContract.AppointmentEntry.COLUMN_NAME_START_TIME, appointment.getTimeframe().getStartTime());
        values.put(DbContract.AppointmentEntry.COLUMN_NAME_END_TIME, appointment.getTimeframe().getEndTime());

        return database.insert(DbContract.AppointmentEntry.TABLE_NAME, null, values);
    }

    /**
     * Getting list of Appointments from the table based on the condition passed
     * @param whereclause String object containing condition to find specific specialities
     * @return List of Appointments
     * */
    public List<Appointment> getAppointments(String whereclause) {
        List<Appointment> appointments = new ArrayList<Appointment>();

        Cursor cursor = database.query(DbContract.AppointmentEntry.TABLE_NAME,
                new String[] {DbContract.AppointmentEntry.COLUMN_NAME_APPOINTMENT_ID,
                        DbContract.AppointmentEntry.COLUMN_NAME_CLINIC_ID,
                        DbContract.AppointmentEntry.COLUMN_NAME_PATIENT_ID,
                        DbContract.AppointmentEntry.COLUMN_NAME_DOCTOR_ID,
                        DbContract.AppointmentEntry.COLUMN_NAME_REFERRER_ID,
                        DbContract.AppointmentEntry.COLUMN_NAME_DATE_TIME,
                        DbContract.AppointmentEntry.COLUMN_NAME_SERVICE_ID,
                        DbContract.AppointmentEntry.COLUMN_NAME_SPECIALTY_ID,
                        DbContract.AppointmentEntry.COLUMN_NAME_PREAPPOINTMENT_ACTIONS,
                        DbContract.AppointmentEntry.COLUMN_NAME_START_TIME,
                        DbContract.AppointmentEntry.COLUMN_NAME_END_TIME
                         }, whereclause, null, null, null, null);

        while (cursor.moveToNext()) {
            Appointment appointment= new Appointment();
            appointment.setId(cursor.getInt(0));
            appointment.setClinicId(cursor.getInt(1));
            appointment.setPatientId(cursor.getInt(2));
            appointment.setDoctorId(cursor.getInt(3));
            appointment.setReferrerId(cursor.getInt(4));

            Calendar cal = Calendar.getInstance();
            Long c = cursor.getLong(5);
            cal.setTimeInMillis(c);

            appointment.setDate(cal);
            appointment.setServiceId(cursor.getInt(6));
            appointment.setSpecialtyId(cursor.getInt(7));
            appointment.setPreAppointmentActions(cursor.getString(8));
            Timeframe timeframe= new Timeframe(cursor.getInt(9),cursor.getInt(10));
            appointment.setTimeframe(timeframe);
            appointments.add(appointment);
        }
        return appointments;
    }

    /**
     * Get a list of all the Appointments
     * @return List of all Appointment Objects
     */
    public List<Appointment> getAllAppointments() {
        return getAppointments(null);
    }

    /**
     * Get list of Appointment by AppointmentID
     * @param id Appointment ID
     * @return List of Appointment Objects
    */
    public List<Appointment> getAppointmentsByID(int id) {
        String whereclause = DbContract.AppointmentEntry.COLUMN_NAME_APPOINTMENT_ID + " = " + id;
        return getAppointments(whereclause);
    }

    /**
     * Get list of Appointment by PatientID
     * @param pid PatientID
     * @return List of Appointment Objects
     */
    public List<Appointment> getAppointmentsByPatientID(int pid) {
        String whereclause = DbContract.AppointmentEntry.COLUMN_NAME_PATIENT_ID + " = " + pid;
        return getAppointments(whereclause);
    }

    /**
     * Get list of Appointment by DoctorID
     * @param did DoctorID
     * @return List of Appointment Objects
     */
    public List<Appointment> getAppointmentsByDoctorID(int did) {
        String whereclause = DbContract.AppointmentEntry.COLUMN_NAME_DOCTOR_ID + " = " + did;
        return getAppointments(whereclause);
    }
    /**
     * Update the Appointment info in the Database
     * @param appointment Appointment object to be updated
     * @return Long containing the result of update
     */
    public long update(Appointment appointment) {
        ContentValues values = new ContentValues();
        values.put(DbContract.AppointmentEntry.COLUMN_NAME_CLINIC_ID, appointment.getClinicId());
        values.put(DbContract.AppointmentEntry.COLUMN_NAME_PATIENT_ID, appointment.getPatientId());
        values.put(DbContract.AppointmentEntry.COLUMN_NAME_DOCTOR_ID, appointment.getDoctorId());
        values.put(DbContract.AppointmentEntry.COLUMN_NAME_REFERRER_ID, appointment.getReferrerId());
        values.put(DbContract.AppointmentEntry.COLUMN_NAME_DATE_TIME, appointment.getDate().getTimeInMillis());
        values.put(DbContract.AppointmentEntry.COLUMN_NAME_SERVICE_ID, appointment.getServiceId());
        values.put(DbContract.AppointmentEntry.COLUMN_NAME_SPECIALTY_ID,appointment.getSpecialtyId());
        values.put(DbContract.AppointmentEntry.COLUMN_NAME_PREAPPOINTMENT_ACTIONS, appointment.getPreAppointmentActions());
        values.put(DbContract.AppointmentEntry.COLUMN_NAME_START_TIME, appointment.getTimeframe().getStartTime());
        values.put(DbContract.AppointmentEntry.COLUMN_NAME_END_TIME, appointment.getTimeframe().getEndTime());

        long result = database.update(DbContract.AppointmentEntry.TABLE_NAME, values,
                WHERE_ID_EQUALS,
                new String[] { String.valueOf(appointment.getId()) });

        return result;
    }

    /**
     * Delete the Appointment object from Database
     * @param appointment Appointment object to be deleted
     * @return int containing the result of deletion
     */
    public int deleteAppointment(Appointment appointment) {
        return database.delete(DbContract.AppointmentEntry.TABLE_NAME,
                WHERE_ID_EQUALS, new String[] { appointment.getId() + "" });
    }

    /**
     * Count the total number of tuples in Appointment Relation
     * @return int containing the number of Appointments
     */
    public int getAppointmentCount(){
        return getAllAppointments().size();
    }

}