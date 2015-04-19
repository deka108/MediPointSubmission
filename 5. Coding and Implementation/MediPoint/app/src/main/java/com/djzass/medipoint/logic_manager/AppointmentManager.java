package com.djzass.medipoint.logic_manager;

import android.content.Context;
import android.util.Log;

import com.djzass.medipoint.logic_database.DbContract;
import com.djzass.medipoint.entity.Appointment;
import com.djzass.medipoint.entity.DoctorSchedule;
import com.djzass.medipoint.entity.Timeframe;
import com.djzass.medipoint.logic_database.AppointmentDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Created by Joshua on 4/4/2015.
 *
 * @author Joshua
 * @since 2015
 * @version 1.0
 *
 * @see android.app.Activity
 */

public class AppointmentManager {
    /**
     * An instance of {@link AppointmentDAO}. This is to be re-instated with context before use.
     */
    private AppointmentDAO appointmentDao;

    /**
     * An arraylist of {@link Appointment} for use
     */
    public List<Appointment> appointments;

    /**
     * An instance of {@link AppointmentManager}. Use this to promote singleton design pattern.
     */
    private static AppointmentManager instance = new AppointmentManager();

    /**
     * returns AppointmentManager instance
     */
    public static AppointmentManager getInstance(){
        return instance;
    }

    /**
     * Re-initializes the AppointmentDAO with the given context
     * @param context Application context
     */
    private void updateAppointmentDao(Context context){
        try {
            appointmentDao = new AppointmentDAO(context);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get a list of all the Appointments
     * @param context Application context
     * @return List of all Appointment Objects
     */
    public List<Appointment> getAppointments(Context context){
        updateAppointmentDao(context);
        return appointmentDao.getAllAppointments();
    }

    /**
     * Get list of Appointment by AppointmentID
     * @param id Appointment ID
     * @param context Application context
     * @return List of Appointment Objects
     */
    public Appointment getAppointmentByID(int id, Context context){
        updateAppointmentDao(context);
        return appointmentDao.getAppointmentsByID(id).get(0);
    }

    /**
     * Get list of Appointment by PatientID
     * @param patient PatientID
     * @param context Application context
     * @return List of Appointment Objects
     */
    public List<Appointment> getPatientAppointmentList(int patient, Context context){
        updateAppointmentDao(context);
        return appointmentDao.getAppointmentsByPatientID(patient);
    }

    /**
     * Get list of Appointment by PatientID that has not yet started (StartTime > CurrentTime)
     * @param patient PatientID
     * @param currentTime Calendar instance to be considered "CurrentTime"
     * @param context Application context
     * @return List of Appointment Objects
     */
    public List<Appointment> getPatientFutureAppointmentList(int patient, Calendar currentTime, Context context){
        updateAppointmentDao(context);
        List<Appointment> ret = new ArrayList<Appointment>();

        appointments = appointmentDao.getAllAppointments();
        for (Appointment temp : appointments) {
            if (temp.getPatientId() == patient) {
                if (currentTime.compareTo(temp.getDate()) < 0) ret.add(temp);
            }
        }

        return ret;
    }

    /**
     * Get list of Appointment by PatientID that has already started (StartTime < CurrentTime)
     * @param patient PatientID
     * @param currentTime Calendar instance to be considered "CurrentTime"
     * @param context Application context
     * @return List of Appointment Objects
     */
    public List<Appointment> getPatientPastAppointmentList(int patient, Calendar currentTime, Context context){
        updateAppointmentDao(context);
        List<Appointment> ret = new ArrayList<Appointment>();

        appointments = appointmentDao.getAllAppointments();
        for (Appointment temp : appointments) {
            if (temp.getPatientId() == patient) {
                if (currentTime.compareTo(temp.getDate()) >= 0) ret.add(temp);
            }
        }

        return ret;
    }

    /**
     * Get list of Past Appointment by PatientID that started in the last 30 days
     * @param patient PatientID
     * @param currentTime Calendar instance to be considered "CurrentTime"
     * @param context Application context
     * @return List of Appointment Objects
     */
    public List<Appointment> getPatientRecentAppointments(int patient, Calendar currentTime, Context context){
        updateAppointmentDao(context);
        List<Appointment> ret = new ArrayList<Appointment>();

        appointments = appointmentDao.getAllAppointments();
        for (Appointment temp : appointments) {
            if (temp.getPatientId() == patient) {
                if (Container.daysBetween(temp.getDate(),currentTime)<30) ret.add(temp);
            }
        }

        return ret;
    }

    /**
     * Get list of Appointment by DoctorID that has not yet started (StartTime > CurrentTime)
     * @param doctor DoctorID
     * @param currentTime Calendar instance to be considered "CurrentTime"
     * @param context Application context
     * @return List of Appointment Objects
     */
    public List<Appointment> getDoctorFutureAppointmentList(int doctor, Calendar currentTime, Context context){
        updateAppointmentDao(context);
        List<Appointment> ret = new ArrayList<Appointment>();

        appointments = appointmentDao.getAllAppointments();
        for (Appointment temp : appointments) {
            if (temp.getDoctorId() == doctor) {
                if (currentTime.compareTo(temp.getDate()) < 0) ret.add(temp);
            }
        }

        return ret;
    }

    /**
     * Get list of Appointment by DoctorID that has already started (StartTime < CurrentTime)
     * @param doctor DoctorID
     * @param currentTime Calendar instance to be considered "CurrentTime"
     * @param context Application context
     * @return List of Appointment Objects
     */
    public List<Appointment> getDoctorPastAppointmentList(int doctor, Calendar currentTime, Context context){
        updateAppointmentDao(context);
        List<Appointment> ret = new ArrayList<Appointment>();

        appointments = appointmentDao.getAllAppointments();
        for (Appointment temp : appointments) {
            if (temp.getDoctorId() == doctor) {
                if (currentTime.compareTo(temp.getDate()) >= 0) ret.add(temp);
            }
        }

        return ret;
    }

    /**
     * Get string denoting the progress status of a given appointment
     * @param appointment Appointment
     * @return String status ("Upcoming", "Ongoing" or "Finished")
     */
    public String getStatus(Appointment appointment){
        Calendar startTime = appointment.getDate();
        Calendar currentTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR, (Timeframe.getHour(appointment.getTimeframe().getStartTime())/2));
        startTime.set(Calendar.MINUTE, 30*(Timeframe.getMinute(appointment.getTimeframe().getStartTime())%2));


        if (currentTime.compareTo(startTime) < 0) {
            //current time is before starttime
            return "Upcoming";
        } else {
            //current time is after starttime
            Calendar endTime = appointment.getDate();
            endTime.set(Calendar.HOUR, (Timeframe.getHour(appointment.getTimeframe().getStartTime())/2));
            endTime.set(Calendar.MINUTE, 30*(Timeframe.getMinute(appointment.getTimeframe().getStartTime())%2));
            if (currentTime.compareTo(endTime) < 0) return "Ongoing";
            else return "Finished";
        }
    }

    /**
     * returns array of boolean denoting whether or not each 30-minute frame in a given date is free
     * based on the availability of patient, doctor, and said doctor's schedule in the given clinic
     * @param date given date
     * @param patient patient id
     * @param doctor doctor id
     * @param clinic clinic id
     * @param context Application context
     * @return array of boolean[48] denoting whether each 30-minute frame from 00:00-00:30 till 23:30-24:00 is free
     */
    public List<Boolean> getAvailableTime(Calendar date, int patient, int doctor, int clinic, Context context){
        updateAppointmentDao(context);
        List<Boolean> ret = new ArrayList<Boolean>();
        appointments = appointmentDao.getAllAppointments();
        for (int i=0; i<48; ++i){
            ret.add(true);
            //ret.add(false); //use this once docsched is done
        }

        List<DoctorSchedule> sched = new ArrayList<DoctorSchedule>(); //DoctorScheduleDAO.getDoctorSchedulesByDoctorClinicID(doctor, clinic)

        for (DoctorSchedule temp : sched){
            for (int i=temp.getTimeframe().getStartTime(); i<=temp.getTimeframe().getEndTime(); ++i){
                ret.set(i,true);
            }
        }

        for (Appointment temp : appointments) {
            if ( temp.getDate().get(Calendar.YEAR) == date.get(Calendar.YEAR) &&
                    temp.getDate().get(Calendar.MONTH) == date.get(Calendar.MONTH) &&
                    temp.getDate().get(Calendar.DATE) == date.get(Calendar.DATE) &&
                    (temp.getPatientId() == patient || temp.getDoctorId() == doctor)) {
                for (int i=temp.getTimeframe().getStartTime(); i<temp.getTimeframe().getEndTime(); ++i){
                    ret.set(i,false);
                }
            }
        }

        return ret;
    }

    /**
     * returns array of boolean denoting whether or not each timeframe of given duration in a given date (from given starttime until endtime)
     * is free based on the availability of patient, doctor, and said doctor's schedule in the given clinic
     * @param date given date
     * @param patient patient id
     * @param doctor doctor id
     * @param clinic clinic id
     * @param startTime earliest timeframe start time
     * @param endTime latest timeframe end time
     * @param duration duration of the timeframe
     * @param context Application context
     * @return array of boolean denoting whether each duration*30-minute frame from starttime until endtime is free
     */
    public List<Boolean> getTimeTable(Calendar date, int patient, int doctor, int clinic, int startTime, int endTime, int duration, Context context){
        //returns array of boolean denoting the availability of timeslots
        //starting from Timeframe [starttime] (default = opening time) until [endTime] (closing time, last active time)
        //duration is in terms of 30 mins. 1 hour = 2 duration, 2.5 hours = 5 duration, etc.

        updateAppointmentDao(context);
        List<Boolean> ret = new ArrayList<Boolean>();
        List<Boolean> availableTime = getAvailableTime(date,patient,doctor,clinic,context);

        for (int i = startTime; i + duration <= endTime; ++i){
            Boolean temp = true;
            for (int j = 0; j < duration; ++j){
                temp = temp & availableTime.get(i+j);
            }
            ret.add(temp);
        }
        return ret;
    }

    /**
     * returns array of available timeframes of given duration in a given date (from given starttime until endtime)
     * availability is based on the availability of patient, doctor, and said doctor's schedule in the given clinic
     * @param date given date
     * @param patient patient id
     * @param doctor doctor id
     * @param clinic clinic id
     * @param startTime earliest timeframe start time
     * @param endTime latest timeframe end time
     * @param duration duration of the timeframe
     * @param context Application context
     * @return array of timeframes denoting available timeframes with given parameters
     */
    public List<Timeframe> getAvailableTimeSlot(Calendar date, int patient, int doctor, int clinic, int startTime, int endTime, int duration, Context context){
        updateAppointmentDao(context);

        ArrayList<Timeframe> availableTimeSlot = new ArrayList<Timeframe>();
        List<Boolean> availableTime = getTimeTable(date, patient, doctor, clinic, startTime, endTime, duration, context);

        for (int i = startTime; i + duration <= endTime; ++i){
            if (availableTime.get(i - startTime)){
                Timeframe slot = new Timeframe(i, i+duration);
                availableTimeSlot.add(slot);
            }
        }

        return availableTimeSlot;
    }


    /**
     * insert app to database with context context
     * @param app doctorSchedule
     * @param context Application context
     * @return row no, -1 if fail
     */
    public long createAppointment(Appointment app, Context context){
        //insert to database
        // update arraylist of appointment appointments = getAppointmentFromDatabase()
        updateAppointmentDao(context);
        long ret = appointmentDao.insertAppointment(app);
        appointments = getAppointments(context);
        return ret;
    }

    /**
     * edit app in database based on id with context context
     * @param app doctorSchedule
     * @param context Application context
     * @return row no, -1 if fail
     */
    public long editAppointment(Appointment app, Context context){
        // update appointment according to its id in database
        // update arraylist of appointment appointments = getAppointmentFromDatabase()
        updateAppointmentDao(context);
        long ret = appointmentDao.update(app);
        appointments = getAppointments(context);
        return ret;
    }

    /**
     * delete app in database based on id with context context
     * @param app doctorSchedule
     * @param context Application context
     * @return row no, -1 if fail
     */
    public long cancelAppointment(Appointment app, Context context){
        // delete appointment according to its id in database
        // update arraylist of appointment appointments = getAppointmentFromDatabase()
        updateAppointmentDao(context);
        long ret = appointmentDao.deleteAppointment(app);
        appointments = getAppointments(context);
        return ret;
    }

    /**
     * Sorts given list of appointments inp based on date
     * @param inp given list of appointments
     * @return sorted list of appointments
     */
    public List<Appointment> sortByDate(List<Appointment> inp){
        List<Appointment> ret = inp;
        Collections.sort(ret,Appointment.AppSortByDate);
        return ret;
    }

    /**
     * Sorts given list of appointments inp based on specialtyID
     * @param inp given list of appointments
     * @return sorted list of appointments
     */
    public List<Appointment> sortBySpecialtyID(List<Appointment> inp){
        List<Appointment> ret = inp;
        Collections.sort(ret,Appointment.AppSortBySpecialty);
        return ret;
    }



}