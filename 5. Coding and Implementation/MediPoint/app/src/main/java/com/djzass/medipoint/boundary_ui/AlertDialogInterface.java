package com.djzass.medipoint.boundary_ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.djzass.medipoint.entity.Appointment;
import com.djzass.medipoint.logic_manager.AppointmentManager;

/**
 * Created by Shreyas on 3/24/2015.
 *
 * Class for containing all the AlertDialogInterface called from various activities
 *
 * @author Shreyas
 * @version 1.0
 * @since 2015
 */
public class AlertDialogInterface {
    /**
     * Contains the custom AlertDialog
     */
    AlertDialog.Builder dlgAlert;

    /**
     * Constructor for creating Custom AlertDialog
     * @param title AlertDialog title
     * @param message Message for AlertDialog
     * @param context Current Application Context
     */
    public AlertDialogInterface(String title, String message, Context context){
        dlgAlert = new AlertDialog.Builder(context);
        dlgAlert.setMessage(message);
        dlgAlert.setTitle(title);
        dlgAlert.setCancelable(true);
    }

    /*
    public void incompleteForm(){
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.create().show();
    }
    */

    /**
     * Showing custom AlertDialog confirmation for Account Created
     * @param func
     */
    public void AccountCreated(final Runnable func){
        dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                func.run();
            }

        });
        dlgAlert.create().show();
    }

    /**
     * Showing custom AlertDialog confirmation for Edit Appointment
     * @param func
     */
    public void EditAppointmentConfirmed(final Runnable func){

        dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                func.run();
            }
        });

        dlgAlert.setNegativeButton("Cancel", null);

        dlgAlert.create().show();

    }

    /**
     * Showing custom AlertDialog notification for sign up when Account already exist
     * @param func
     */
    public void AccountAlreadyExists(final Runnable func){
        dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                func.run();
            }
        });
        dlgAlert.create().show();
    }

    /**
     * Showing custom AlertDialog notification for filling in the medical history form.
     * @param insertPatientDOB
     * @param goToLoginPage
     */
    public void BackToLogin(final Runnable insertPatientDOB,final Runnable goToLoginPage){
        dlgAlert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                insertPatientDOB.run();
                goToLoginPage.run();
            }
        });

        dlgAlert.setNegativeButton("NO",null);

        dlgAlert.create().show();

    }

    /**
     * Showing custom AlertDialog asking confirmation for appointment deletion
     * @param appointmentDeleter
     * @param appointment
     * @param context
     */
    public void deleteAppointmentDialog(final AppointmentManager appointmentDeleter,final Appointment appointment, final Context context){
        dlgAlert.setPositiveButton("YES",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            appointmentDeleter.cancelAppointment(appointment,context);
            }
        });
        dlgAlert.setNegativeButton("NO",null);
        dlgAlert.create().show();
    }

    /**
     * Showing custom AlertDialog asking confirmation for changing appointment details
     */
    public void editAppointmentDialog(){
        dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){

            }
        });
        dlgAlert.setNegativeButton("CANCEL", null);
        dlgAlert.create().show();
    }
}



