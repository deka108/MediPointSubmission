package com.djzass.medipoint.logic_manager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.djzass.medipoint.entity.Account;
import com.djzass.medipoint.entity.Appointment;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Stefan on 3/30/2015.
 */

/**
 * set reminder Alarm depending on the date of each appointment or cancel reminder Alarm.
 * @author Stefan Artaputra Indriawan.
 *@version 1.
 * @since 2015.
 */
public class AlarmSetter {
    /**
     * This method is used for setting an alarm in order to remind the user for upcoming appointments.
     * The time is set 1 day before {@link Appointment} date and time.
     * @param context		{@link Context} object from which this method is called
     * @param appointment	 {@link Appointment} object which will be passed to the {@link PendingIntent} object to be broadcasted by Alarm Manager.
     * @param account {@link Account} object which will also be passed to the {@link PendingIntent} object to be broadcasted by Alarm Manager.
     * @see android.app.AlarmManager
     */
    public void setAlarm(Context context,Appointment appointment,Account account){
        // Intent to run when the alarm is broadcasted
        Intent reminderIntent = new Intent(context,AlarmBroadcastReceiver.class);
        reminderIntent.putExtra("appointment",appointment);
        reminderIntent.putExtra("account", account);
        int reminderId = appointment.getId();

        // Use the same alarmId and the FLAG_CANCEL_CURRENT to overwrite the old Alarm when the new one is set.
        PendingIntent reminder = PendingIntent.getBroadcast(context,reminderId,reminderIntent,PendingIntent.FLAG_CANCEL_CURRENT);
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        //the alarm will be executed approximately 24 hours before appointment.
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Prevent multiple notification
        if (appointment.getDate().getTimeInMillis() - 24 * 3600 * 1000 >= cal.getTimeInMillis()) {
            am.set(AlarmManager.RTC_WAKEUP, appointment.getDate().getTimeInMillis() - 24 * 3600 * 1000, reminder);
        }
    }

    /**
     * This method is used for cancelling current appointment.
     * @param context		{@link Context} object from which this method is called
     * @param appointment	 {@link Appointment} object which will be passed to the {@link PendingIntent} object to be broadcasted by Alarm Manager.
     * @param account {@link Account} object which will also be passed to the {@link PendingIntent} object to be broadcasted by Alarm Manager.
     */
    public void cancelAlarm(Context context,Appointment appointment,Account account){
        Intent cancelIntent = new Intent(context,AlarmBroadcastReceiver.class);
        cancelIntent.putExtra("appointment",appointment);
        cancelIntent.putExtra("account", account);

        int reminderId = appointment.getId();
        // Use the same alarmId and the FLAG_UPDATE_CURRENT to cancel the ongoing Alarm when the new one is set.
        PendingIntent cancel = PendingIntent.getBroadcast(context, reminderId, cancelIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(cancel);
    }

}
