package com.djzass.medipoint.logic_database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.djzass.medipoint.entity.Timeframe;
import com.djzass.medipoint.entity.DoctorSchedule;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deka on 28/3/2015.
 *
 * @author Joshua
 * @since 2015
 * @version 1.0
 */
public class DoctorScheduleDAO extends DbDAO{
    /**
     * database query for comparing DoctorSchedule ID
     */
    private static final String WHERE_ID_EQUALS = DbContract.DoctorScheduleEntry.COLUMN_NAME_DOCTOR_SCHEDULE_ID + " =?";

    /**
     * DoctorSchedule Database helper constructor
     * @param context Interface to global information about an application environment
     */
    public DoctorScheduleDAO(Context context) throws SQLException {
        super(context);
    }

    /**
     * insert Object DoctorSchedule to DB
     * @param doctorSchedule DoctorSchedule object to be stored in DB
     * @return Long object containing info about the status of DB insertion
     */
    public long insertDoctorSchedule(DoctorSchedule doctorSchedule){
        ContentValues values = new ContentValues();
        //values.put(DbContract.DoctorScheduleEntry.COLUMN_NAME_DOCTOR_SCHEDULE_ID, getDoctorScheduleCount());
        values.put(DbContract.DoctorScheduleEntry.COLUMN_NAME_DOCTOR_ID, doctorSchedule.getDoctorId());
        values.put(DbContract.DoctorScheduleEntry.COLUMN_NAME_CLINIC_ID, doctorSchedule.getClinicId());
        values.put(DbContract.DoctorScheduleEntry.COLUMN_NAME_DAY, doctorSchedule.getDay());
        values.put(DbContract.DoctorScheduleEntry.COLUMN_NAME_START_TIME, doctorSchedule.getTimeframe().getStartTime());
        values.put(DbContract.DoctorScheduleEntry.COLUMN_NAME_END_TIME, doctorSchedule.getTimeframe().getEndTime());

        // Inserting Row
        return database.insert(DbContract.DoctorScheduleEntry.TABLE_NAME, null, values);
    }

    /**
     * Getting list of DoctorSchedules from the table based on the condition passed
     * @param whereclause String object containing condition to find specific specialities
     * @return List of DoctorSchedules
     * */
    public List<DoctorSchedule> getDoctorSchedules(String whereclause) {
        List<DoctorSchedule> doctorSchedules = new ArrayList<DoctorSchedule>();

        Cursor cursor = database.query(DbContract.DoctorScheduleEntry.TABLE_NAME,
                new String[] { DbContract.DoctorScheduleEntry.COLUMN_NAME_DOCTOR_SCHEDULE_ID,
                        DbContract.DoctorScheduleEntry.COLUMN_NAME_DOCTOR_ID,
                        DbContract.DoctorScheduleEntry.COLUMN_NAME_CLINIC_ID,
                        DbContract.DoctorScheduleEntry.COLUMN_NAME_DAY,
                        DbContract.DoctorScheduleEntry.COLUMN_NAME_START_TIME,
                        DbContract.DoctorScheduleEntry.COLUMN_NAME_END_TIME}, whereclause, null, null, null,
                null);

        while (cursor.moveToNext()) {
            DoctorSchedule doctorSchedule= new DoctorSchedule();
            doctorSchedule.setId(cursor.getInt(0));
            doctorSchedule.setDoctorId(cursor.getInt(1));
            doctorSchedule.setClinicId(cursor.getInt(2));
            doctorSchedule.setDay(cursor.getString(3));
            doctorSchedule.setTimeframe(new Timeframe(cursor.getInt(4), cursor.getInt(5)));
            doctorSchedules.add(doctorSchedule);
        }

        return doctorSchedules;
    }

    /**
     * Get a list of all the DoctorSchedules
     * @return List of all DoctorSchedule Objects
     */
    public List<DoctorSchedule> getAllDoctorSchedules() {
        return getDoctorSchedules(null);
    }

    /**
     * Get list of DoctorSchedule by DoctorScheduleID
     * @param id DoctorSchedule ID
     * @return List of DoctorSchedule Objects
     */
    public List<DoctorSchedule> getDoctorSchedulesByID(int id) {
        String whereclause = DbContract.DoctorScheduleEntry.COLUMN_NAME_DOCTOR_SCHEDULE_ID + " = " + id;
        return getDoctorSchedules(whereclause);
    }

    /**
     * Get list of DoctorSchedule by DoctorID
     * @param doctorId DoctorID
     * @return List of DoctorSchedule Objects
     */
    public List<DoctorSchedule> getDoctorSchedulesByDoctorID(int doctorId) {
        String whereclause = DbContract.DoctorScheduleEntry.COLUMN_NAME_DOCTOR_ID + " = " + doctorId;
        return getDoctorSchedules(whereclause);
    }

    /**
     * Get list of DoctorSchedule by ClinicID
     * @param clinicId clinicID
     * @return List of DoctorSchedule Objects
     */
    public List<DoctorSchedule> getDoctorSchedulesByClinicID(int clinicId) {
        String whereclause = DbContract.DoctorScheduleEntry.COLUMN_NAME_CLINIC_ID + " = " + clinicId;
        return getDoctorSchedules(whereclause);
    }

    /**
     * Get list of DoctorSchedule by DoctorID and ClinicID
     * @param doctorId DoctorID
     * @param clinicId clinicID
     * @return List of DoctorSchedule Objects
     */
    public List<DoctorSchedule> getDoctorSchedulesByDoctorClinicID(int doctorId, int clinicId) {
        String whereclause = DbContract.DoctorScheduleEntry.COLUMN_NAME_DOCTOR_ID + " = " + doctorId
                 + " AND " + DbContract.DoctorScheduleEntry.COLUMN_NAME_CLINIC_ID + " = " + clinicId;
        return getDoctorSchedules(whereclause);
    }
    /**
     * Update the DoctorSchedule info in the Database
     * @param doctorSchedule DoctorSchedule object to be updated
     * @return Long containing the result of update
     */
    public long update(DoctorSchedule doctorSchedule) {
        ContentValues values = new ContentValues();
        values.put(DbContract.DoctorScheduleEntry.COLUMN_NAME_DOCTOR_ID, doctorSchedule.getDoctorId());
        values.put(DbContract.DoctorScheduleEntry.COLUMN_NAME_CLINIC_ID, doctorSchedule.getClinicId());
        values.put(DbContract.DoctorScheduleEntry.COLUMN_NAME_DAY, doctorSchedule.getDay());
        values.put(DbContract.DoctorScheduleEntry.COLUMN_NAME_START_TIME, doctorSchedule.getTimeframe().getStartTime());
        values.put(DbContract.DoctorScheduleEntry.COLUMN_NAME_END_TIME, doctorSchedule.getTimeframe().getEndTime());

        long result = database.update(DbContract.DoctorScheduleEntry.TABLE_NAME, values,
                WHERE_ID_EQUALS,
                new String[] { String.valueOf(doctorSchedule.getId()) });

        return result;
    }

    /**
     * Delete the DoctorSchedule object from Database
     * @param doctorSchedule DoctorSchedule object to be deleted
     * @return int containing the result of deletion
     */
    public int deleteDoctorSchedule(DoctorSchedule doctorSchedule) {
        return database.delete(DbContract.DoctorScheduleEntry.TABLE_NAME,
                WHERE_ID_EQUALS, new String[] { doctorSchedule.getId() + "" });
    }
    /*
        LOAD
        Load the initial values of the doctorSchedules
     */

    /**
     * Count the total number of tuples in DoctorSchedule Relation
     * @return int containing the number of DoctorSchedules
     */
    public int getDoctorScheduleCount(){
        return getAllDoctorSchedules().size();
    }

    /**
     * insert DoctorSchedules to be used in the app
     */
    private void initializeDAO()
    {
        if(getAllDoctorSchedules().size() ==0)
        {
            /*Currently not yet implemented*/
        }
    }
}
