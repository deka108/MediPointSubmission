package com.djzass.medipoint.logic_manager;

import android.content.Context;

import com.djzass.medipoint.entity.DoctorSchedule;
import com.djzass.medipoint.logic_database.DoctorScheduleDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joshua on 10/4/2015.
 *
 * @author Joshua
 * @since 2015
 * @version 1.0
 */
public class DoctorScheduleManager {
    /**
     * An instance of {@link DoctorScheduleDAO}. This is to be re-instated with context before use.
     */
    private DoctorScheduleDAO doctorScheduleDao;

    /**
     * An instance of {@link DoctorScheduleManager}. Use this to promote singleton design pattern.
     */
    private static DoctorScheduleManager instance = new DoctorScheduleManager();

    /**
     * returns DoctorScheduleManager instance
     */
    public static DoctorScheduleManager getInstance() {
        return instance;
    }

    /**
     * Re-initializes the DoctorScheduleDAO with the given context
     * @param context Application context
     */
    private void updateDoctorScheduleDao(Context context){
        try {
            doctorScheduleDao = new DoctorScheduleDAO(context);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get all DoctorSchedules
     * @param context Application context
     * @return List of doctorSchedule objects
     */
    public List<DoctorSchedule> getDoctorSchedules(Context context){
        updateDoctorScheduleDao(context);
        return doctorScheduleDao.getAllDoctorSchedules();
    }

    /**
     * Get list of DoctorSchedule by DoctorScheduleID
     * @param id DoctorSchedule ID
     * @param context Application context
     * @return List of DoctorSchedule Objects
     */
    public List<DoctorSchedule> getDoctorSchedulesByID(int id, Context context) {
        updateDoctorScheduleDao(context);
        return doctorScheduleDao.getDoctorSchedulesByID(id);
    }

    /**
     * Get list of DoctorSchedule by DoctorID
     * @param doctorId DoctorID
     * @param context Application context
     * @return List of DoctorSchedule Objects
     */
    public List<DoctorSchedule> getDoctorSchedulesByDoctorID(int doctorId, Context context) {
        updateDoctorScheduleDao(context);
        return doctorScheduleDao.getDoctorSchedulesByDoctorID(doctorId);
    }

    /**
     * Get list of DoctorSchedule by ClinicID
     * @param clinicId clinicID
     * @param context Application context
     * @return List of DoctorSchedule Objects
     */
    public List<DoctorSchedule> getDoctorSchedulesByClinicID(int clinicId, Context context) {
        updateDoctorScheduleDao(context);
        return doctorScheduleDao.getDoctorSchedulesByClinicID(clinicId);
    }

    /**
     * Get list of DoctorSchedule by DoctorID and ClinicID
     * @param doctorId DoctorID
     * @param clinicId clinicID
     * @param context Application context
     * @return List of DoctorSchedule Objects
     */
    public List<DoctorSchedule> getDoctorSchedulesByDoctorClinicID(int doctorId, int clinicId, Context context) {
        updateDoctorScheduleDao(context);
        return doctorScheduleDao.getDoctorSchedulesByDoctorClinicID(doctorId,clinicId);
    }

    /**
     * insert doctorSchedule to database with context context
     * @param doctorSchedule doctorSchedule
     * @param context Application context
     * @return row no, -1 if fail
     */
    public long createDoctorSchedule(DoctorSchedule doctorSchedule, Context context){
        updateDoctorScheduleDao(context);
        long ret = doctorScheduleDao.insertDoctorSchedule(doctorSchedule);
        return ret;
    }

    /**
     * edit doctorschedule in database based on id with context context
     * @param doctorSchedule doctorSchedule
     * @param context Application context
     * @return row no, -1 if fail
     */
    public long editDoctorSchedule(DoctorSchedule doctorSchedule, Context context){
        // update doctorSchedule according to its id in database
        updateDoctorScheduleDao(context);
        long ret = doctorScheduleDao.update(doctorSchedule);
        return ret;
    }

    /**
     * delete doctorSchedule in database based on id with context context
     * @param doctorSchedule doctorSchedule
     * @param context Application context
     * @return row no, -1 if fail
     */
    public long cancelDoctorSchedule(DoctorSchedule doctorSchedule, Context context){
        // delete doctorSchedule according to its id in database 
        long ret = doctorScheduleDao.deleteDoctorSchedule(doctorSchedule);
        updateDoctorScheduleDao(context);
        return ret;
    }
}
