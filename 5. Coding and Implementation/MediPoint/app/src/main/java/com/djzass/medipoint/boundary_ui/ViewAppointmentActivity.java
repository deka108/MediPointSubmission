package com.djzass.medipoint.boundary_ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.djzass.medipoint.R;
import com.djzass.medipoint.entity.Account;
import com.djzass.medipoint.entity.Appointment;
import com.djzass.medipoint.logic_manager.AlarmSetter;
import com.djzass.medipoint.logic_manager.Container;
import com.djzass.medipoint.logic_manager.SessionManager;

import java.sql.SQLException;
import java.text.ParseException;

/**
 * Created by Deka on 4/4/2015.
 *
 * This activity display the detailed information about the appointment.
 * @author Deka
 * @since 2015
 * @version 1.0
 *
 * @see android.app.Activity
 */
public class ViewAppointmentActivity extends Activity {
    private Appointment app;

    /**
     * Called when the activity is starting. This is where most initialization done: calling
     * setContentView(int) to inflate the activity's UI, using findViewById(int) to programmatically
     * interact with widgets in the UI. All the View contents in this activity are initialized with
     * {@code Appointment} object passed after clicking on item in the Appointment List.
     *
     * @param savedInstanceState  If the activity is being re-initialized after previously being shut
     *                           down then this Bundle contains the data it most recently supplied in
     *                           onSaveInstanceState(Bundle). Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointment);

        //Get the appointment object
        Bundle b = getIntent().getExtras();
        app = b.getParcelable("appObj");

        //Initialize the Views using the values inside appointment object
        TextView specialtyName = (TextView) findViewById(R.id.viewSpecialty);
        specialtyName.setText(Container.getSpecialtyManager().getSpecialtyNameBySpecialtyId(app.getSpecialtyId(),this));
        TextView serviceName = (TextView) findViewById(R.id.viewService);
        serviceName.setText(Container.getServiceManager().getServiceNameByServiceID(app.getServiceId(), this));
        TextView appointmentStatus = (TextView) findViewById(R.id.viewStatus);
        appointmentStatus.setText(Container.getAppointmentManager().getStatus(app));
        TextView appointmentDate = (TextView) findViewById(R.id.viewDate);
        appointmentDate.setText(app.getDateString());
        TextView appointmentTime = (TextView) findViewById(R.id.viewTime);
        appointmentTime.setText(app.getTimeString());
        TextView appointmentLocation = (TextView) findViewById(R.id.viewLocation);
        appointmentLocation.setText(Container.getClinicManager().getClinicNameByClinicId(app.getClinicId(),this));
        TextView doctorName = (TextView) findViewById(R.id.viewDoctor);
        doctorName.setText(Container.getDoctorManager().getDoctorNameByDoctorId(app.getDoctorId(),this));
        TextView preAppointmentActions = (TextView) findViewById(R.id.viewPreAppointmentActions);
        preAppointmentActions.setText(app.getPreAppointmentActions());
        ImageView specialtyIcon = (ImageView)findViewById(R.id.specialty_icon);
        specialtyIcon.setImageResource(getImageId(Container.getSpecialtyManager().getSpecialtyNameBySpecialtyId(app.getSpecialtyId(),this)));
    }

    /**
     * Get the Image Resource id of the specialty
     *
     * @param specialtyName is the name of the specialty
     * @return the Icon of the corresponding specialty
     */
    public int getImageId(String specialtyName){
        if (specialtyName.equalsIgnoreCase("ENT"))
            return R.mipmap.ear;
        else if (specialtyName.equalsIgnoreCase("Dental Services"))
            return R.mipmap.dental;
        else if (specialtyName.equalsIgnoreCase("Women's Health"))
            return R.mipmap.female;
        return R.mipmap.icontp_medipoint;
    }

    /**
     * Start the EditAppointmentActivity while passing the appointment object of currently being viewed
     */
    public void ViewApptEdit()
    {
        Intent in = new Intent(getApplicationContext(), EditAppointmentActivity.class);
        in.putExtra("appFromView", getIntent().getExtras().getParcelable("appObj"));
        startActivity(in);
    }

    /**
     * Showing AlertDialog confirming user with appointment deletion
     */
    public void ViewApptDelete(){
        AlertDialog diaBox = ConfirmDelete();
        diaBox.show();
    }


    /**
     *  Delete the appointment from list of appointment
     */
    private void performDelete(){
        //Cancel appointment
        Container.getAppointmentManager().cancelAppointment(app, this);

        SessionManager sessionMgr = new SessionManager(this);
        long accountId = 0;
        try {
            accountId = sessionMgr.getAccountId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Account account = new Account();
        account = Container.getAccountManager().getAccountById(accountId,this);

        //Cancelling the alarm
        AlarmSetter mAlarm = new AlarmSetter();
        mAlarm.cancelAlarm(getApplicationContext(),app,account);
        Toast.makeText(this, "Appointment deleted", Toast.LENGTH_SHORT).show();
        Intent in = new Intent(this, MainActivity.class);
        startActivity(in);
    }

    /**
     * Initialize the contents of the Activity's standard options menu
     *
     * @param menu the options menu in which the items are placed
     * @return true for the menu to be displayed; if false is returned, the items will not be shown.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * This hook is called whenever an item in options menu is selected.
     *
     * @param item the menu item that was selected.
     * @return boolean value. Return false to allow normal menu processing to proceed, true to consume it here.
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_call:
                Intent editIntent= new Intent(getApplicationContext(),EditAppointmentActivity.class);
                startActivity(editIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Gets the alertDialog for confirming deletion
     * @return AlertDialog for Confirming Deletion
     */
    private AlertDialog ConfirmDelete(){
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this)
                //Set message, title, and icon
                .setTitle("Confirm action")
                .setMessage("Are you sure you want to delete this appointment?")

                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton){
                        //Perform Deletion
                        performDelete();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    //Cancelling deletion
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        return myQuittingDialogBox;
    }
}
