package com.djzass.medipoint.logic_database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.djzass.medipoint.entity.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deka on 28/3/2015.
 * Service class database helper
 */
public class ServiceDAO extends DbDAO{

    /**
     * service prefix
     */
    public static final String SERVICE_WITH_PREFIX = "ser.";

    /**
     * specialty prefix
     */
    public static final String SPECIALTY_WITH_PREFIX = "spec.";

    /**
     * database query for comparing id
     */
    private static final String WHERE_ID_EQUALS = DbContract.ServiceEntry.COLUMN_NAME_SERVICE_ID
            + " =?";

    /**
     * Service Database helper constructor
     * @param context Interface to global information about an application environment
     * @throws SQLException throw an SQLException
     */
    public ServiceDAO(Context context) throws SQLException {
        super(context);
        initializeDAO();
    }

    /**
     * insert Service to DB
     * @param service Service object to be inserted to DB
     * @return result of insertion
     */
    public long insertService(Service service){
        ContentValues values = new ContentValues();
        //values.put(DbContract.ServiceEntry.COLUMN_NAME_SERVICE_ID, getServiceCount());
        values.put(DbContract.ServiceEntry.COLUMN_NAME_SERVICE_NAME, service.getName());
        values.put(DbContract.ServiceEntry.COLUMN_NAME_SPECIALTY_ID, service.getSpecialtyId());
        values.put(DbContract.ServiceEntry.COLUMN_NAME_SERVICE_DURATION, service.getDuration());
        values.put(DbContract.ServiceEntry.COLUMN_NAME_PREAPPOINTMENT_ACTIONS, service.getPreAppointmentActions());

        // Inserting Row
        return database.insert(DbContract.ServiceEntry.TABLE_NAME, null, values);
    }

    /**
     * get a List of a specific Service
     * @param whereclause String object containing where to find the Service
     * @return List containing Service object
     */
    public List<Service> getServices(String whereclause) {
        List<Service> services = new ArrayList<Service>();

        /*String query = "SELECT " + DbContract.ServiceEntry.COLUMN_NAME_SERVICE_ID + ", "
                 + DbContract.ServiceEntry.COLUMN_NAME_SERVICE_NAME + ", "
                 + DbContract.ServiceEntry.COLUMN_NAME_SERVICE_DURATION + ", "
                 + DbContract.ServiceEntry.COLUMN_NAME_PREAPPOINTMENT_ACTIONS + ", "
                 + DbContract.SpecialtyEntry.COLUMN_NAME_SPECIALTY_NAME +
                " FROM " + DbContract.ServiceEntry.TABLE_NAME + ", " +
                DbContract.SpecialtyEntry.TABLE_NAME + " WHERE " + SERVICE_WITH_PREFIX +
                DbContract.ServiceEntry.COLUMN_NAME_SPECIALTY_ID + " = " + SPECIALTY_WITH_PREFIX +
                DbContract.SpecialtyEntry.COLUMN_NAME_SPECIALTY_ID + whereclause;*/

        String query = "SELECT " + SERVICE_WITH_PREFIX + DbContract.ServiceEntry.COLUMN_NAME_SERVICE_ID + ", " +
                SERVICE_WITH_PREFIX + DbContract.ServiceEntry.COLUMN_NAME_SERVICE_NAME + ", " +
                SERVICE_WITH_PREFIX + DbContract.ServiceEntry.COLUMN_NAME_SERVICE_DURATION + ", " +
                SERVICE_WITH_PREFIX + DbContract.ServiceEntry.COLUMN_NAME_PREAPPOINTMENT_ACTIONS + ", " +
                SPECIALTY_WITH_PREFIX + DbContract.SpecialtyEntry.COLUMN_NAME_SPECIALTY_NAME +
                " FROM " + DbContract.ServiceEntry.TABLE_NAME + " ser, " +
                DbContract.SpecialtyEntry.TABLE_NAME + " spec WHERE " + SERVICE_WITH_PREFIX +
                DbContract.ServiceEntry.COLUMN_NAME_SPECIALTY_ID + " = " + SPECIALTY_WITH_PREFIX +
                DbContract.SpecialtyEntry.COLUMN_NAME_SPECIALTY_ID + whereclause;

        Cursor cursor = database.rawQuery(query, null);

        while (cursor.moveToNext()) {
            Service service= new Service();
            service.setId(cursor.getInt(0));
            service.setName(cursor.getString(1));
            service.setDuration(cursor.getInt(2));
            service.setPreAppointmentActions(cursor.getString(3));
            service.setSpecialtyId(cursor.getInt(4));
            service.getDuration();
            services.add(service);
        }

        return services;
    }

    /**
     * get a List of all Service
     * @return List containing Service object
     */
    public List<Service> getAllServices() {
        return getServices("");
    }

    /**
     * get Service using the id
     * @param serviceid int object containing the Id of the Service
     * @return List of Service object
     */
    public List<Service> getServicesByID(int serviceid) {
        String whereclause = " AND " + DbContract.ServiceEntry.COLUMN_NAME_SERVICE_ID + " = " + serviceid;
        return getServices(whereclause);
    }

    /**
     * get Service using the specialty id
     * @param specialtyId int object containg the Id of the specialty
     * @return List of Service object
     */
    public List<Service> getServicesBySpecialtyID(int specialtyId) {
        String whereclause = " AND " + SERVICE_WITH_PREFIX + DbContract.ServiceEntry.COLUMN_NAME_SPECIALTY_ID + " = " + specialtyId;
        return getServices(whereclause);
    }

    /**
     * Change the info of the Service
     * @param service Service Object to be updated to DB
     * @return Long containing the result of updating
     */
    public long update(Service service) {
        ContentValues values = new ContentValues();
        values.put(DbContract.ServiceEntry.COLUMN_NAME_SERVICE_NAME, service.getName());
        values.put(DbContract.ServiceEntry.COLUMN_NAME_SPECIALTY_ID, service.getSpecialtyId());
        values.put(DbContract.ServiceEntry.COLUMN_NAME_SERVICE_DURATION, service.getDuration());
        values.put(DbContract.ServiceEntry.COLUMN_NAME_PREAPPOINTMENT_ACTIONS, service.getPreAppointmentActions());

        long result = database.update(DbContract.ServiceEntry.TABLE_NAME, values,
                WHERE_ID_EQUALS,
                new String[] { String.valueOf(service.getId()) });
        Log.d("Update Result:", "=" + result);

        return result;
    }

    /**
     * Delete the Service from DB
     * @param service Service Object to be deleted from DB
     * @return int containing the result of deleting
     */
    public int deleteService(Service service) {
        return database.delete(DbContract.ServiceEntry.TABLE_NAME,
                WHERE_ID_EQUALS, new String[] { service.getId() + "" });
    }

    /**
     * print all available Service
     */
    public void loadServices() {
        List<Service> temp= getAllServices();
        for (Service tmp : temp) {
            tmp.print();
        }
    }

    /**
     * count the number of rows in Service table in DB
     * @return int object containing the number of Service
     */
    public int getServiceCount(){
        return getAllServices().size();
    }

    /**
     * initializing DB helper
     * insert Service info to be used in the app
     */
    private void initializeDAO(){
        if (getServiceCount()==0){ //0-4: ent, dental, women, gm
            insertService(new Service("General",1, 1));
            insertService(new Service("Periodic ENT", 1, 1));
            insertService(new Service("OSA", 1, 2,"1. Sleep Diary\n2.Avoid Alcohol"));
            insertService(new Service("Otology", 1, 4));

            insertService(new Service("Routine Scaling", 2, 1));
            insertService(new Service("Polishing", 2, 2));
            insertService(new Service("Fillings", 2, 2));
            insertService(new Service("Tooth Extraction", 2, 4,"1.X-Ray of Tooth"));
            insertService(new Service("Root Canal", 2, 6));

            insertService(new Service("Gynecologists", 3, 2,"1.Avoid Sex the Night Before\n2.Urine Test"));
            insertService(new Service("Obstetrician", 3, 2));

            insertService(new Service("Dietetic Services", 4, 2));
            insertService(new Service("Physiotherapy", 4, 2));
            insertService(new Service("Child Care", 4, 2));
            insertService(new Service("Chronic care", 4, 6));
        }
    }
}
