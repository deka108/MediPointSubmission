package com.djzass.medipoint.logic_database;


/**
 * Created by Ankur on 27/3/2015.
 * SpecialtyDAO is Data Access Object class for performing CRUD operations on specialty table in the
 * database.
 * @author Ankur
 * @version 1.0
 * @since 2015
 *
 * @see com.djzass.medipoint.entity.Specialty,com.djzass.medipoint.logic_manager.SpecialtyManager,com.djzass.medipoint.logic_database.DbHelper,com.djzass.medipoint.logic_database.DbContract
 *
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.djzass.medipoint.entity.Specialty;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SpecialtyDAO extends DbDAO{
    /**
     * database query for comparing Speciality ID
     */
    private static final String WHERE_ID_EQUALS = DbContract.SpecialtyEntry.COLUMN_NAME_SPECIALTY_ID
            + " =?";

    /**
     * Speciality Database helper constructor
     * @param context Interface to global information about an application environment
     * @throws SQLException throw an SQLException
     */

    public SpecialtyDAO(Context context) throws SQLException {
        super(context);
        initializeDAO();
    }

    /**
     * insert Object Speciality to DB
     * @param specialty Speciality object to be stored in DB
     * @return Long object containing info about the status of DB insertion
     */
    public long insertSpecialty(Specialty specialty){
        ContentValues values = new ContentValues();
        //values.put(DbContract.SpecialtyEntry.COLUMN_NAME_SPECIALTY_ID, getSpecialtyCount());
        values.put(DbContract.SpecialtyEntry.COLUMN_NAME_SPECIALTY_NAME, specialty.getName());

        return database.insert(DbContract.SpecialtyEntry.TABLE_NAME, null, values);
    }

    /**
     * Getting list of specialties from the table based on the condition passed
     * @param whereclause String object containing condition to find specific specialities
     * @return List of Specialities
     * */
    public List<Specialty> getSpecialties(String whereclause) {
        List<Specialty> specialties = new ArrayList<Specialty>();

        Cursor cursor = database.query(DbContract.SpecialtyEntry.TABLE_NAME,
                new String[] { DbContract.SpecialtyEntry.COLUMN_NAME_SPECIALTY_ID,
                        DbContract.SpecialtyEntry.COLUMN_NAME_SPECIALTY_NAME }, whereclause, null, null, null,
                null);

        while (cursor.moveToNext()) {
            Specialty specialty= new Specialty();
            specialty.setId(cursor.getInt(0));
            specialty.setName(cursor.getString(1));
            specialties.add(specialty);
        }

        return specialties;
    }

    /**
     * Get a list of all the Specialities
     * @return List of all Speciality Objects
     */
    public List<Specialty> getAllSpecialties() {
        return getSpecialties(null);
    }


    /**
     * Get list of specialities by speciality ID
     * @param id Specialty ID
     * @return List of Speciality Objects
     */
    public List<Specialty> getSpecialtiesByID(int id) {
        String whereclause = DbContract.SpecialtyEntry.COLUMN_NAME_SPECIALTY_ID + " = " + id;
        return getSpecialties(whereclause);
    }

    /**
     * Get list of specialities by Speciality Name
     * @param specialtyName Specialty Name
     * @return List of Speciality Objects
     */
    public List<Specialty> getSpecialtiesByName(String specialtyName){
        String whereClause = DbContract.SpecialtyEntry.COLUMN_NAME_SPECIALTY_NAME + "='" + specialtyName + "'";
        return getSpecialties(whereClause);
    }

    /**
     * Update the speciality info in the Database
     * @param specialty Speciality object to be updated
     * @return Long containing the result of update
     */
    public long update(Specialty specialty) {
        ContentValues values = new ContentValues();
        values.put(DbContract.SpecialtyEntry.COLUMN_NAME_SPECIALTY_NAME, specialty.getName());

        long result = database.update(DbContract.SpecialtyEntry.TABLE_NAME, values,
                WHERE_ID_EQUALS,
                new String[] { String.valueOf(specialty.getId()) });

        return result;
    }

    /**
     * Delete the Specialty object from Database
     * @param specialty Specialty object to be deleted
     * @return int containing the result of deletion
     */
    public int deleteSpecialty(Specialty specialty) {
        return database.delete(DbContract.SpecialtyEntry.TABLE_NAME,
                WHERE_ID_EQUALS, new String[] { specialty.getId() + "" });
    }

    /**
     * Count the total number of tuples in Speciality Relation
     * @return int containing the number of Specialities
     */
    public int getSpecialtyCount(){
        return getAllSpecialties().size();
    }

    /**
     * insert Specialities to be used in the app
     */
    private void initializeDAO(){
        if (getSpecialtyCount()==0){
            insertSpecialty(new Specialty("ENT")); //0
            insertSpecialty(new Specialty("Dental Services")); //1
            insertSpecialty(new Specialty("Women's Health")); //2
            insertSpecialty(new Specialty("General Medicine")); //3
        }
    }
}
