package com.djzass.medipoint.boundary_ui;

/**
 * Created by Ankur on 2/4/2015
 *
 * This activity handles the creation of new appointments for users.
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
import android.widget.Toast;

import com.djzass.medipoint.R;
import com.djzass.medipoint.entity.Account;
import com.djzass.medipoint.entity.Appointment;
import com.djzass.medipoint.entity.Clinic;
import com.djzass.medipoint.entity.Doctor;
import com.djzass.medipoint.entity.Service;
import com.djzass.medipoint.entity.Specialty;
import com.djzass.medipoint.entity.Timeframe;
import com.djzass.medipoint.logic_manager.AlarmSetter;
import com.djzass.medipoint.logic_manager.Container;
import com.djzass.medipoint.logic_manager.SessionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CreateAppointmentActivity extends onDataPass implements AdapterView.OnItemSelectedListener, SelectionListener{

    //appointment atrribute selections
    /**
     * Clinic Id for the appointment being booked
     */
    int clinicId;

    /**
     * Patient ID is the ID of patient booking the appointment
     */
    int patientId;

    /**
     * ID of the doctor for the appointment being booked
     */
    int doctorId;
    /**
     * Store ID of the referring doctor if appointment is a referral
     */
    int referrerId;

    /**
     * date for the appointment being booked
     */
    Calendar date = Calendar.getInstance();

    /**
     * service ID for the appointment being booked
     */
    int serviceId;

    /**
     * specialty ID for the appointment being booked
     */
    int specialtyId;

    /**
     * Slot duration for the appointment being booked
     */
    int duration;

    /**
     * Pre Appointment actions for the appointment being booked
     */
    String preAppointmentActions;
    Timeframe timeframe = new Timeframe(-1,-1);

    /**
     * ID of the logged in user
     */
    long accountId;

    /*SPINNER*/

    /**
     * Contains the spinner for specialties
     */
    Spinner specialtySpinnerCreate;
    /**
     * Contains the spinner for countries
     */
    Spinner countrySpinnerCreate;
    /**
     * Contains the spinner for services
     */
    Spinner serviceSpinnerCreate;
    /**
     * Button for creating appointment
     */
    Button confirmButton;
    /**
     * Button for cancelling creating an appointment
     */
    Button cancelButton;
    /**
     * Contains the spinner for doctors
     */
    Spinner doctorSpinnerCreate;
    /**
     * Contains the spinner for clinics
     */
    Spinner clinicSpinnerCreate;
    /**
     * Contains the list of specialties
     */
    List<Specialty> specialities;


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
        setContentView(R.layout.activity_create_appointment);

        referrerId = getIntent().getIntExtra("REFERRER_ID",-1);
        date.setTimeInMillis(0);

        try {
            SessionManager sessionManager = new SessionManager(this);
            this.patientId = (int)sessionManager.getAccountId();


            /**
             * Create a dropdown list for avaialable specialities
             */
            specialities = Container.getSpecialtyManager().getSpecialtys(this);
            specialtySpinnerCreate = (Spinner) findViewById(R.id.CreateApptSpecialty);
            List<String> specialtyNames = new ArrayList<String>();
            for(Specialty s: specialities){
                specialtyNames.add(s.getName());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,specialtyNames);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter.notifyDataSetChanged();

            accountId = sessionManager.getAccountId();
            specialtySpinnerCreate.setAdapter(dataAdapter);
            specialtySpinnerCreate.setOnItemSelectedListener(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /**
         * Creates a dropdown displaying available countries
         */
        countrySpinnerCreate = (Spinner) findViewById(R.id.CreateApptCountries);
        countrySpinnerCreate.setOnItemSelectedListener(this);
        ArrayAdapter countryAdapter_create = ArrayAdapter.createFromResource(this, R.array.countries, android.R.layout.simple_spinner_dropdown_item);
        countryAdapter_create.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinnerCreate.setAdapter(countryAdapter_create);


        //service spinner
        serviceSpinnerCreate = (Spinner) findViewById(R.id.CreateApptServices);
        //countrySpinnerCreate.setOnItemSelectedListener(this);
        //doctor spinner
        doctorSpinnerCreate = (Spinner) findViewById(R.id.CreateApptDoctors);
        //countrySpinnerCreate.setOnItemSelectedListener(this);
        //clinic location spinner
        clinicSpinnerCreate = (Spinner) findViewById(R.id.CreateApptLocations);
        //countrySpinnerCreate.setOnItemSelectedListener(this);

        confirmButton = (Button)findViewById(R.id.ConfirmCreateAppt);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                onClickCreateAppointment();
            }
        });

        cancelButton = (Button)findViewById(R.id.CancelCreateAppt);
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
        else if(id==R.id.action_logout){
            //((Container)getApplicationContext()).getGlobalAccountManager().logout();
            SessionManager sessionManager = new SessionManager(this);
            sessionManager.deleteLoginSession();
            //Container.GlobalAccountManager.logout();
            Intent intent = new Intent(this,LoginActivity.class);
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

        switch(parent.getId()) {
            case R.id.CreateApptSpecialty:
                String speciality = String.valueOf(specialtySpinnerCreate.getSelectedItem());
                for (Specialty s : specialities) {
                    if (speciality.equals(s.getName())) {
                        specialtyId = s.getId();
                    }
                }

                List<Service> services = Container.getServiceManager().getServicesBySpecialtyID(specialtyId,this);
                List<String> serviceNames = new ArrayList<String>();
                for (Service s : services) {
                    serviceNames.add(s.getName());
                }

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, serviceNames);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dataAdapter.notifyDataSetChanged();
                serviceSpinnerCreate.setAdapter(dataAdapter);
                serviceSpinnerCreate.setOnItemSelectedListener(this);
                List<Doctor> doctors = Container.getDoctorManager().getDoctorsByClinicAndSpecialization(clinicId,specialtyId,this);
                List<String> doctorNames = new ArrayList<String>();
                for (Doctor d : doctors) {
                    doctorNames.add(d.getName());
                }
                ArrayAdapter<String> doctorDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, doctorNames);
                doctorDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                doctorDataAdapter.notifyDataSetChanged();
                doctorSpinnerCreate.setAdapter(doctorDataAdapter);
                doctorSpinnerCreate.setOnItemSelectedListener(this);

                resetTimePicker();
                break;

            case R.id.CreateApptServices:
                services = Container.getServiceManager().getServicesBySpecialtyID(specialtyId,this);
                String service = String.valueOf(serviceSpinnerCreate.getSelectedItem());
                for (Service s : services) {
                    if (service.equals(s.getName())) {
                        this.serviceId = s.getId();
                        this.preAppointmentActions = s.getPreAppointmentActions();
                        this.duration = s.getDuration();
                    }
                }
                resetTimePicker();
                break;

            case R.id.CreateApptDoctors:
                doctors = Container.getDoctorManager().getDoctorsByClinicAndSpecialization(clinicId,specialtyId,this);
                String doctor = String.valueOf(doctorSpinnerCreate.getSelectedItem());
                for (Doctor d : doctors) {
                    if (doctor.equals(d.getName())) {
                        doctorId = d.getDoctorId();
                    }
                }

                resetTimePicker();
                break;

            case R.id.CreateApptCountries:
                String country = String.valueOf(countrySpinnerCreate.getSelectedItem());
                List<Clinic> clinics = Container.getClinicManager().getClinicsByCountry(country,this);
                List<String> clinicNames = new ArrayList<String>();
                for (Clinic c : clinics) {
                    clinicNames.add(c.getName());
                }

                ArrayAdapter<String> clinicDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, clinicNames);
                clinicDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                clinicDataAdapter.notifyDataSetChanged();
                clinicSpinnerCreate.setAdapter(clinicDataAdapter);
                clinicSpinnerCreate.setOnItemSelectedListener(this);
                String clinic = String.valueOf(clinicSpinnerCreate.getSelectedItem());
                for (Clinic c : clinics) {
                    if (clinic.equals(c.getName())) {
                        clinicId = c.getId();
                    }
                }

                doctors = Container.getDoctorManager().getDoctorsByClinicAndSpecialization(clinicId,specialtyId,this);
                doctorNames = new ArrayList<String>();
                for (Doctor d : doctors) {
                    doctorNames.add(d.getName());
                }
                doctorDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, doctorNames);
                doctorDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                doctorDataAdapter.notifyDataSetChanged();
                doctorSpinnerCreate.setAdapter(doctorDataAdapter);
                doctorSpinnerCreate.setOnItemSelectedListener(this);

                resetTimePicker();
                break;

            case R.id.CreateApptLocations:

                clinic = String.valueOf(clinicSpinnerCreate.getSelectedItem());
                clinics = Container.getClinicManager().getClinics(this);
                for (Clinic c : clinics) {
                    if (clinic.equals(c.getName())) {
                        clinicId = c.getId();
                    }
                }

                doctors = Container.getDoctorManager().getDoctorsByClinicAndSpecialization(clinicId,specialtyId,this);
                doctorNames = new ArrayList<String>();
                for (Doctor d : doctors) {
                    doctorNames.add(d.getName());
                }
                doctorDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, doctorNames);
                doctorDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                doctorDataAdapter.notifyDataSetChanged();
                doctorSpinnerCreate.setAdapter(doctorDataAdapter);
                doctorSpinnerCreate.setOnItemSelectedListener(this);

                resetTimePicker();
                break;
        }

    }

    /**
     * This hook is called whenever nothing is selected
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * This hook is called on activity start
     */
    @Override
    public void onStart(){
        super.onStart();
    }

    /**
     * This hook is called whenever activity is resumed
     */
    @Override
    public void onResume(){
        super.onResume();
    }

    /**
     * This hook is called whenever activity is paused
     */
    @Override
    public void onPause(){
        super.onPause();
    }

    /**
     * This hook is called whenever an instance state is saved
     */
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }

    /**
     * This hook is called whenever an instance state is restored
     */
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * This hook is called on activity stop
     */
    @Override
    public void onStop(){
        super.onStop();
    }

    /**
     * This hook is called on activity destroy
     */
    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    /**
     * This hook is called whenever an the select select time button is pressed.
     * Shows the content of timepicker
     * @param v the view
     */
    public void showTimepicker(View v){
        if (this.date.getTimeInMillis()==0){
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

    /*
    public void onTimeButtonSelected(View v){
        int id = v.getId();
        Bundle bundle = new Bundle();
        bundle.putInt("VIEW_ID",id);
        FragmentManager manager = getFragmentManager();
        TimePickerFragment datepicker = new TimePickerFragment();
        datepicker.setArguments(bundle);
        datepicker.show(manager, "Datepicker");
    }*/

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


        if (this.date.getTimeInMillis()==0){
            Toast.makeText(this, "Please select a date ", Toast.LENGTH_SHORT).show();
        } else if (this.timeframe.getStartTime()<0){
            Toast.makeText(this, "Please select a time. ", Toast.LENGTH_SHORT).show();
        } else {
            this.date.set(Calendar.HOUR_OF_DAY,(this.timeframe.getStartTime()/2));
            this.date.set(Calendar.MINUTE,30*(this.timeframe.getStartTime()%2));
            if (this.date.compareTo(currentDate)<0){
                Toast.makeText(this, "You must book at least 24 hours in advance. ", Toast.LENGTH_SHORT).show();
            } else {
                Appointment appointment = new Appointment(this.patientId, this.clinicId, this.specialtyId, this.serviceId, this.doctorId, referrerId,this.date, this.timeframe, Container.getServiceManager().getServicePreappbyID(this.serviceId, this));
                long res = Container.getAppointmentManager().createAppointment(appointment, this);
                if (res == -1) {
                    Toast.makeText(this,"Appointment creation failed", Toast.LENGTH_SHORT).show();

                } else {
                    AlarmSetter malarm = new AlarmSetter();
                    Account account = new Account();
                    account = Container.getAccountManager().getAccountById(accountId,this);
                    malarm.setAlarm(getApplicationContext(),appointment,account);
                    /*Notification notification = new Notification();
                    notification.buildNotification(this, "Appointment created.",appointment);*/
                    Toast.makeText(this,"Appointment created!", Toast.LENGTH_SHORT).show();
                    Intent goToMain = new Intent(this, MainActivity.class);
                    startActivity(goToMain);

                }
            }
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
        List<Timeframe> temp = Container.getAppointmentManager().getAvailableTimeSlot(this.date, this.patientId, this.doctorId, this.clinicId, 18, 42, duration, this);

        availableSlots.add("N/A");
        for (Timeframe t:temp){
            availableSlots.add(t.getTimeLine());
        }
        return availableSlots;
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
     * This function resets timepicker to default null value
     */
    public void resetTimePicker(){
        Button btn = (Button) findViewById(R.id.timepicker);
        btn.setText("TAP TO CHOOSE TIME");
        this.timeframe = new Timeframe(-1,-1);
    }

    /**
     * This function updates date based on selected calendar date
     */
    @Override
    public void DatePickerFragmentToActivity(int date,int month,int year,Button button){
        super.DatePickerFragmentToActivity(date,month,year,button);
        this.date = Calendar.getInstance();
        this.date.set(year,month,date);
        resetTimePicker();
    }
}
