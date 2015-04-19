package com.djzass.medipoint.logic_manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;

import com.djzass.medipoint.entity.Account;
import com.djzass.medipoint.entity.Appointment;
import com.djzass.medipoint.entity.Email;
import com.djzass.medipoint.entity.Notification;
import com.djzass.medipoint.entity.SMS;
import com.djzass.medipoint.logic_manager.Container;

/**
 * Created by Stefan on 3/30/2015.
 */

/**
 * Receives broadcasts from Alarm Manager for notification purpose (the type of notification depends on the user's choice).
 * @author Stefan Artaputra Indriawan.
 * @version 1.
 * @since 2015.
 */
public class AlarmBroadcastReceiver extends BroadcastReceiver {

    /**
     *  This method definition is overriding the onReceive method in BroadcastReceiver. The method
     *  defines what to do when an it receives the broadcast. It notifies the user through local notification when
     *  there is an appointment , then SMS, or/ and email depending on what the user chooses.
     *  @param context		{@link Context} object from which this method is called
     * @param intent {@link Intent} object from which this method is called.
     *@see android.content.BroadcastReceiver .
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        //
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
        //Acquire the lock to prevent system to sleep
        wl.acquire();
        //get the object which is passed from the intent
        Bundle extras = intent.getExtras();
        Appointment appointment = extras.getParcelable("appointment");
        Account account = extras.getParcelable("account");
        String extra = new String();

       //if there is no actions needed before appointment, then no need to add it into the message.
        if(appointment.getPreAppointmentActions().equalsIgnoreCase("none")){
            extra = " ";
        }
        else{
            extra = " Please do the following actions before you go to appointment:\n"
                    + appointment.getPreAppointmentActions();
        }

        //create the message template for notification.
        String message = "Dear " + account.getName()+ ",\n"
                + Container.getSpecialtyManager().getSpecialtyNameBySpecialtyId(appointment.getSpecialtyId(), context)
                + " Appointment is on:\n" + appointment.getDateString()
                + " with " + Container.getDoctorManager().getDoctorNameByDoctorId(appointment.getDoctorId(), context) +".\n"
                + extra + "\n"
                +"This is an automated message.Please do not reply";

        if ( appointment !=null){

            //user will get pushbar notification regardless of user choice ( Email or SMS).
            Notification mNotification = new Notification();
            mNotification.buildNotification(context,"you have an appointment tomorrow",appointment);

            //if user selects to get notified by email
            if(account.getNotifyEmail()==1) {
                Email mEmail = new Email();
                mEmail.sendMail(account.getEmail(),message);
            }

            //if user selects to get notified by SMS
            if(account.getNotifySMS()==1) {
                SMS mSMS = new SMS();
                mSMS.sendSms(context, account.getPhoneNumber(),message,false);
            }

        }
        //Release the lock for CPU.
        wl.release();
    }
}