package com.djzass.medipoint.logic_manager;

import android.content.Context;

import com.djzass.medipoint.entity.Doctor;
import com.djzass.medipoint.logic_database.DoctorDAO;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Joshua on 10/4/2015.
 */

/**
 * manager for doctor entity and activity which will used the doctor entity.
 * @author Stefan Artaputra Indriawan.
 *@version 1.
 * @since 2015.
 */
public class DoctorManager {
    /**
     * An instance of {@link DoctorDAO}. This is to be re-instated with context before use.
     */
    private DoctorDAO doctorDao;

    /**
     * An instance of {@link DoctorManager}. Use this to promote singleton design pattern.
     */
    private static DoctorManager instance = new DoctorManager();

    /**
     * returns DoctorManager instance
     */
    public static DoctorManager getInstance() {
        return instance;
    }
    /**
     * Re-initializes the DoctorDAO with the given context
     * @param context {@link Context} object from which the method is called.
     */
    private void updateDoctorDao(Context context){
        try {
            doctorDao = new DoctorDAO(context);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets all doctor
     * @return List<Doctor> return the doctor object in form of list.
     * @param context {@link Context} object from which the method is called.
     */
    public List<Doctor> getDoctors(Context context){
        updateDoctorDao(context);
        return doctorDao.getAllDoctors();
    }

    /**
     * Gets doctor with specialization id
     * @param specializationId {@link java.lang.Integer} integer object used to obtain the list of the doctor.
     * @param context {@link Context} object from which the method is called.
     * @return List<Doctor>
     */
    public List<Doctor> getDoctorBySpecialization(int specializationId, Context context) {
        updateDoctorDao(context);
        return doctorDao.getDoctorBySpecialization(specializationId);
    }

    /**
     * Gets doctor with specific specialization id and clinic id
     * @param specializationId {@link java.lang.Integer} integer object used to obtain the list of the doctor.
     * @param context {@link Context} object from which the method is called.
     * @param clinicId {@link java.lang.Integer} integer object used to obtain the list of the doctor.
     * @return List<Doctor>
     */
    public List<Doctor> getDoctorsByClinicAndSpecialization(int clinicId,int specializationId, Context context) {
        updateDoctorDao(context);
        return doctorDao.getDoctorsByClinicAndSpecialization(clinicId,specializationId);
    }


    /**
     * insert doctor to database with context
     * @param doctor {@link com.djzass.medipoint.entity.Doctor} object to be inserted into database.
     * @param context {@link Context} object from which the method is called.
     * @return row no, -1 if fail
     */
    public long createDoctor(Doctor doctor, Context context){
        updateDoctorDao(context);
        long ret = doctorDao.insertDoctor(doctor);
        return ret;
    }

    /**
     * edit doctor in database based on id with context.
     * @param  doctor {@link com.djzass.medipoint.entity.Doctor} object to be manipulated in database.
     * @param context {@link Context} object from which the method is called.
     * @return row no, -1 if fail
     */
    public long editDoctor(Doctor doctor, Context context){
        // update doctor according to its id in database
        updateDoctorDao(context);
        long ret = doctorDao.update(doctor);
        return ret;
    }

    /**
     * delete doctor in database based on id with context.
     * @param doctor {@link com.djzass.medipoint.entity.Doctor} object to be deleted from database.
     * @param context {@link Context} object from which the method is called.
     * @return row no, -1 if fail
     */
    public long cancelDoctor(Doctor doctor, Context context){
        // delete doctor according to its id in database 
        long ret = doctorDao.deleteDoctor(doctor);
        updateDoctorDao(context);
        return ret;
    }

    /**
     * Gets doctor name with id.
     * @param doctorId {@link Integer} object used to get the list of the doctor.
     * @param context {@link Context} object from which the method is called.
     * @return String doctor name
     */
    public String getDoctorNameByDoctorId(int doctorId, Context context) {
        updateDoctorDao(context);
        return doctorDao.getDoctorById(doctorId).get(0).getName();
    }
}
