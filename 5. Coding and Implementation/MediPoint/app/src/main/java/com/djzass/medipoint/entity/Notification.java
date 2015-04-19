package com.djzass.medipoint.entity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;

import com.djzass.medipoint.R;
import com.djzass.medipoint.boundary_ui.ViewAppointmentActivity;

/**
 * Created by Stefan on 3/30/2015.
 */

/**
 * Create a reminder for the user appointment through pushbar.
 * @author Stefan Artaputra Indriawan.
 * @version 1.
 * @since 2015.
 */

public class Notification {
    /**
     *  This method will create a pushbar notification when the function is called.
     *  @param context {@link Context} object from which this method is called
     * @param body {@link String} the message to be sent to the user.
     * @param appointment {@link Appointment} appointment object which is passed from which this method is called.
     */
    public void buildNotification(Context context,String body,Appointment appointment) {
        NotificationManagerCompat NM= NotificationManagerCompat.from(context);

        // Open the ViewAppointmentActivity when the notification is pressed
        Intent intent = new Intent(context, ViewAppointmentActivity.class);
        intent.putExtra("appObj",appointment);

        //pending intent for later use
        PendingIntent pI = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        //create Notification
        android.app.Notification.Builder notify = new android.app.Notification.Builder(context)
                .setContentTitle("Medipoint Reminder")
                .setContentText(body)
                .setSmallIcon(R.mipmap.iconbw_medipoint)
                .setContentIntent(pI)

        //by set this to true, the notification will disappear after clicked
                .setAutoCancel(true);

        //build the notification so that it appears in the user's phone.
        NM.notify(0,notify.build());
    }
}
