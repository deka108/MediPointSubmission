package com.djzass.medipoint.boundary_ui;

/**
 * Created by Ankur on 4/4/2015
 *
 * This activity handles creation of follow up appointments. The list od past appointment is
 * retrieved and the appointment to be followed is selected.
 *
 * @author Ankur
 * @since 2015
 * @version 1.0
 *
 * @see com.djzass.medipoint.boundary_ui.onDataPass
 */
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.djzass.medipoint.R;
import com.djzass.medipoint.entity.Account;
import com.djzass.medipoint.entity.Appointment;
import com.djzass.medipoint.entity.Service;
import com.djzass.medipoint.entity.Timeframe;
import com.djzass.medipoint.logic_manager.AlarmSetter;
import com.djzass.medipoint.logic_manager.Container;
import com.djzass.medipoint.logic_manager.SessionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CreateFollowUpActivity extends onDataPass implements AdapterView.OnItemSelectedListener, SelectionListener{

    /**
     * Instance for appointment date
     */
    Calendar apptDate = Calendar.getInstance();
    Timeframe timeframe = new Timeframe(-1,-1);
    /**
     * Slot duration for appointment
     */
    int duration;
    /**
     * Id for speciality for the appointment
     */
    int specialtyId;

    /**
     * ID for the service type for the appointment
     */
    int serviceId;
    /**
     * Id of the patient who booked the appointment
     */
    int patientId;
    /**
     * ID of the doctor with whom the appointment was booked
     */
    int doctorId;
    /**
     * ID of the clinic where appointment was held
     */
    int clinicId;
    Spinner serviceSpinnerCreate;
    List<Service> services;
    List<String> serviceNames = new ArrayList<String>();
    Button confirmButton;
    Button cancelButton;
    long accountId;

    /**
     * Called when the activity is starting. This is where most initialization is done: calling
     * setContentView(int) to inflate the activity's UI, using findViewById(int) to programmatically
     * interact with widgets in the UI.
     *
     * @param savedInstanceState  If the activity is being re-initialized after previously being shut
     *                           down then this Bundle contains the data it most recently supplied in
     *                           onSaveInstanceState(Bundle). Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_follow_up);

        SessionManager sessionManager = new SessionManager(this);
        try {
            this.patientId = (int)sessionManager.getAccountId();
            this.accountId = sessionManager.getAccountId();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Bundle b = getIntent().getExtras();
        Appointment app = b.getParcelable("appFollowUp");
        TextView country = (TextView) findViewById(R.id.FollowUpCountry);
        country.setText(Container.getClinicManager().getClinicsByID(app.getClinicId(),this).get(0).getCountry());
        TextView location = (TextView) findViewById(R.id.FollowUpLocation);
        location.setText(Container.getClinicManager().getClinicNameByClinicId(app.getClinicId(),this));
        TextView doctor = (TextView) findViewById(R.id.FollowUpDoctor);
        doctor.setText(Container.getDoctorManager().getDoctorNameByDoctorId(app.getDoctorId(),this));
        TextView specialty = (TextView) findViewById(R.id.FollowUpSpecialty);
        specialty.setText(Container.getSpecialtyManager().getSpecialtyNameBySpecialtyId(app.getSpecialtyId(),this));


        this.specialtyId = app.getSpecialtyId();
        this.serviceId = app.getServiceId();
        this.doctorId = app.getDoctorId();
        this.clinicId = app.getClinicId();
        //service spinner
        serviceSpinnerCreate = (Spinner) findViewById(R.id.CreateFollowUpServices);
        services = Container.getServiceManager().getServicesBySpecialtyID(app.getSpecialtyId(), this);

        for (Service s : services) {
            serviceNames.add(s.getName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, serviceNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        serviceSpinnerCreate.setAdapter(dataAdapter);
        serviceSpinnerCreate.setOnItemSelectedListener(this);

        confirmButton = (Button)findViewById(R.id.ConfirmCreateFollowUpAppt);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                onClickCreateAppointment();
            }
        });

        cancelButton = (Button)findViewById(R.id.CancelCreateFollowUpAppt);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent in = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(in);
            }
        });
    }

    /**
     * Initialize the contents of Activity's standard option menu
     * @param menu the options menu in which items are placed
     * @return true for the menu to be displayed, if false is returned, the items will not be shown
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * This hook is called whenever an item in options menu is selected.
     * @param item the menu item that was selected.
     * @return boolean value. Return false to allow normal menu processing to proceed, true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        //logout menu item selected
        else if (id == R.id.action_logout) {
            SessionManager sessionManager = new SessionManager(this);
            sessionManager.deleteLoginSession();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Callback method to be invoked when an item in this view has been selected.
     * This callback is invoked only when the newly selected position is different
     * from the previously selected position or if there was no selected item.
     * @param parent The adapter view where selection happened
     * @param view the view within adapter view that was selected
     * @param position the position of the view in adapter
     * @param id the row id of the item that is selected
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String service = String.valueOf(serviceSpinnerCreate.getSelectedItem());

        for (Service s : services) {
            if (service.equals(s.getName())) {
                serviceId = s.getId();
            }
        }
    }

    /**
     * This hook is called whenever nothing is selected
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * This hook is called whenever an the select date button is pressed.
     * Calls the fragment manager to pop up the date selection pop-up
     * @param v the view
     */
    public void onDateButtonSelected(View v){
        int id = v.getId();
        Bundle bundle = new Bundle();
        bundle.putInt("VIEW_ID",id);
        FragmentManager manager = getFragmentManager();
        DatePickerFragment datepicker = new DatePickerFragment();
        datepicker.setArguments(bundle);
        datepicker.show(manager, "Datepicker");
    }

    /**
     * This function updates date based on selected calendar date
     */
    @Override
    public void DatePickerFragmentToActivity(int date, int month, int year, Button button)
    {
        super.DatePickerFragmentToActivity(date,month,year,button);
        apptDate.set(year,month,date);
    }

    /**
     * This hook is called whenever an the select time button is pressed.
     * Calls the fragment manager to pop up the time selection pop-up
     * @param v the view
     */
    public void onTimeButtonSelected(View v){
        if (this.apptDate.getTimeInMillis()==0){
            Toast.makeText(this, "Please select a date ", Toast.LENGTH_SHORT).show();
            return;
        }
        FragmentManager manager = getFragmentManager();
        TimePickerFragment timepicker = new TimePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(TimePickerFragment.DATA, getTimePickerItems());     // Require ArrayList
        bundle.putInt(TimePickerFragment.SELECTED, 0);
        timepicker.setArguments(bundle);
        timepicker.show(manager, "TimePicker");
    }

    /**
     * This function is called whenever a timepicker item gets chosen
     */
    @Override
    public void selectItem(int position) {
        Button btn = (Button) findViewById(R.id.timepicker);
        if(getTimePickerItems().get(position)!="N/A"){
            btn.setText(getTimePickerItems().get(position));
            this.timeframe = new Timeframe(getTimePickerItems().get(position));
        } else {
            resetTimePicker();
        }
    }

    /**
     * This function calls an appointmentManager function to get the available timeframes to be used by timepicker
     * @return String Arraylist containing all the strings to be used in the timepicker
     */
    private ArrayList<String> getTimePickerItems() {
        ArrayList<String> availableSlots = new ArrayList<String>();
        //Toast.makeText(this, this.date.getTime().toString(), Toast.LENGTH_SHORT).show();
        this.duration = Container.getServiceManager().getServiceDurationbyID(this.serviceId, this);
        List<Timeframe> temp = Container.getAppointmentManager().getAvailableTimeSlot(this.apptDate, this.patientId, this.doctorId, this.clinicId, 18, 42, duration, this);

        availableSlots.add("N/A");
        for (Timeframe t:temp){
            availableSlots.add(t.getTimeLine());
        }
        return availableSlots;
    }

    /**
     * This function resets timepicker to default null value
     */
    public void resetTimePicker(){
        Button btn = (Button) findViewById(R.id.timepicker);
        btn.setText("TAP TO CHOOSE TIME");
        this.timeframe = new Timeframe(-1,-1);
    }

    /**
     * This hook is called whenever the create button is pressed
     * Performs error checks and input validation
     * If valid, perform addition to database
     */
    public void onClickCreateAppointment() {
        //AppointmentManager appointmentManager = new AppointmentManager();
        //Toast.makeText(this, "Button clicked.", Toast.LENGTH_SHORT).show();
        Calendar currentDate = Calendar.getInstance();
        currentDate.add(Calendar.DATE, 1);

        if (this.apptDate.getTimeInMillis()==0){
            Toast.makeText(this, "Please select a date ", Toast.LENGTH_SHORT).show();
        } else if (this.timeframe.getStartTime()<0){
            Toast.makeText(this, "Please select a time. ", Toast.LENGTH_SHORT).show();
        } else {
            this.apptDate.set(Calendar.HOUR_OF_DAY, (this.timeframe.getStartTime() / 2));
            this.apptDate.set(Calendar.MINUTE,30*(this.timeframe.getStartTime()%2));
            if (this.apptDate.compareTo(currentDate)<0){
                Toast.makeText(this, "You must book at least 24 hours in advance. ", Toast.LENGTH_SHORT).show();
            } else {
                Appointment appointment = new Appointment(this.patientId, this.clinicId, this.specialtyId, this.serviceId, this.doctorId, -1, this.apptDate, this.timeframe, Container.getServiceManager().getServicePreappbyID(this.serviceId, this));

                long res = Container.getAppointmentManager().createAppointment(appointment, this);
                if (res == -1) {
                    Toast.makeText(this,"Appointment creation failed", Toast.LENGTH_SHORT).show();

                } else {
                    AlarmSetter malarm = new AlarmSetter();
                    Account account = new Account();
                    account = Container.getAccountManager().getAccountById(accountId, this);
                    malarm.setAlarm(getApplicationContext(),appointment,account);
                    /*Notification notification = new Notification();
                    notification.buildNotification(this, "Appointment created.",appointment);*/
                    Intent goToMain = new Intent(this, MainActivity.class);
                    startActivity(goToMain);

                }
            }
        }
    }

}
