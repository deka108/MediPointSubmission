package com.djzass.medipoint.logic_manager;

import android.content.Context;

import com.djzass.medipoint.entity.Clinic;
import com.djzass.medipoint.logic_database.ClinicDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deka on 28/3/2015.
 */
public class ClinicManager {
    /**
     * An instance of {@link ClinicDAO}. This is to be re-instated with context before use.
     */
    private ClinicDAO clinicDao;

    /**
     * An instance of {@link ClinicManager}. Use this to promote singleton design pattern.
     */
    private static ClinicManager instance = new ClinicManager();

    /**
     * returns ClinicManager instance
     */
    public static ClinicManager getInstance() {
        return instance;
    }
    /**
     * Re-initializes the ClinicDAO with the given context
     */
    private void updateClinicDao(Context context){
        try {
            clinicDao = new ClinicDAO(context);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets all clinic
     * @return List<Clinic>
     */
    public List<Clinic> getClinics(Context context){
        updateClinicDao(context);
        return clinicDao.getAllClinics();
    }

    /**
     * Gets clinic with id @param id
     * @return List<Clinic>
     */
    public List<Clinic> getClinicsByID(int id, Context context) {
        updateClinicDao(context);
        return clinicDao.getClinicsByID(id);
    }

    /**
     * Gets clinic with country @param country
     * @return List<Clinic>
     */
    public List<Clinic> getClinicsByCountry(String country, Context context) {
        updateClinicDao(context);
        return clinicDao.getClinicsByCountry(country);
    }

    /**
     * Gets clinic with name @param name
     * @return List<Clinic>
     */
    public List<Clinic> getClinicsByName(String name, Context context) {
        updateClinicDao(context);
        return clinicDao.getClinicsByName(name);
    }

    /**
     * insert @param clinic to database with context @param context   
     * @return row no, -1 if fail
     */
    public long createClinic(Clinic clinic, Context context){
        updateClinicDao(context);
        long ret = clinicDao.insertClinic(clinic);
        return ret;
    }

    /**
     * edit @param clinic in database based on id with context @param context   
     * @return row no, -1 if fail
     */
    public long editClinic(Clinic clinic, Context context){
        // update clinic according to its id in database
        updateClinicDao(context);
        long ret = clinicDao.update(clinic);
        return ret;
    }

    /**
     * delete @param clinic in database based on id with context @param context   
     * @return row no, -1 if fail
     */
    public long cancelClinic(Clinic clinic, Context context){
        // delete clinic according to its id in database 
        long ret = clinicDao.deleteClinic(clinic);
        updateClinicDao(context);
        return ret;
    }

    /**
     * get country of the clinic
     * @param clinicId int object containing clinicId
     * @param context Interface to global information about an application environment
     * @return String object containing the country of clinic
     */
    public String getCountryByClinicId(int clinicId, Context context)
    {
        updateClinicDao(context);
        return clinicDao.getClinicsByID(clinicId).get(0).getCountry();
    }

    /**
     * get name of the clinic
     * @param clinicId int object containing clinicId
     * @param context Interface to global information about an application environment
     * @return String object containing the name of clinic
     */
    public String getClinicNameByClinicId(int clinicId,Context context)
    {
        updateClinicDao(context);
        return clinicDao.getClinicsByID(clinicId).get(0).getName();
    }

}