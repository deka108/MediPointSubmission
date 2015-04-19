package com.djzass.medipoint.boundary_ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.djzass.medipoint.R;
import com.djzass.medipoint.entity.Clinic;
import com.djzass.medipoint.entity.Doctor;
import com.djzass.medipoint.entity.Specialty;
import com.djzass.medipoint.logic_manager.Container;
import com.djzass.medipoint.logic_manager.SessionManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity class for Referral. User will fill in the details of the referrer here.
 * @author Ankur
 * @version 1.0
 * @since 2015
 */
public class ReferralActivity extends Activity implements AdapterView.OnItemSelectedListener{

    /**
     * Contains the spinner for specialties
     */
    Spinner specialtySpinnerCreate;
    /**
     * Contains the spinner for countries
     */
    Spinner countrySpinnerCreate;

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
     * Contains the id of the specialty chosen by the user
     */
    int specialtyId;

    /**
     * Contains the id of the clinic chosen by the user
     */
    int clinicId;

    /**
     * Contains the id of the doctor chosen by the user
     */
    int doctorId;

    /**
     * Called when the activity is starting. This is where most initialization done: calling
     * setContentView(int) to inflate the activity's UI, using findViewById(int) to programmatically
     * interact with widgets in the UI.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut
     *                           down then this Bundle contains the data it most recently supplied in
     *                           onSaveInstanceState(Bundle). Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referral);

        //specialty spinner
        specialities = Container.getSpecialtyManager().getSpecialtys(this);
        specialtySpinnerCreate = (Spinner) findViewById(R.id.CreateApptSpecialty);
        List<String> specialtyNames = new ArrayList<String>();
        for(Specialty s: specialities){
            specialtyNames.add(s.getName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,specialtyNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        specialtySpinnerCreate.setAdapter(dataAdapter);
        specialtySpinnerCreate.setOnItemSelectedListener(this);

        //country spinner
        countrySpinnerCreate = (Spinner) findViewById(R.id.CreateApptCountries);
        ArrayAdapter countryAdapterCreate = ArrayAdapter.createFromResource(this, R.array.countries, android.R.layout.simple_spinner_dropdown_item);
        countryAdapterCreate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinnerCreate.setAdapter(countryAdapterCreate);
        countrySpinnerCreate.setOnItemSelectedListener(this);

        //doctor spinner
        doctorSpinnerCreate = (Spinner) findViewById(R.id.CreateApptDoctors);

        //clinic spinner
        clinicSpinnerCreate = (Spinner) findViewById(R.id.CreateApptLocations);
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
     * Change the dropdownlist dynamically according to the user selection. This is the
     * Callback method to be invoked when an item in this view has been selected.
     *
     * @param parent The AdapterView where the selection happened
     * @param view The view within the AdapterView that was clicked
     * @param position The position of the view in the adapter
     * @param id The row id of the item that is selected
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId()) {
            //Specialty dropdown selected
            case R.id.CreateApptSpecialty:
                String speciality = String.valueOf(specialtySpinnerCreate.getSelectedItem());
                for (Specialty s : specialities) {
                    if (speciality.equals(s.getName())) {
                        specialtyId = s.getId();
                    }
                }

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
                break;
            //Country dropdown selected
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
                break;
            //Clinic dropdown selected
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
                break;
            //Doctor dropdown selected
            case R.id.CreateApptDoctors:
                doctors = Container.getDoctorManager().getDoctorsByClinicAndSpecialization(clinicId,specialtyId,this);
                String doctor = String.valueOf(doctorSpinnerCreate.getSelectedItem());
                for (Doctor d : doctors) {
                    if (doctor.equals(d.getName())) {
                        doctorId = d.getDoctorId();
                    }
                }
                break;
        }
    }

    /**
     * Callback method to be invoked when the selection disappears from this view.
     * @param parent The AdapterView that now contains no selected item.
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * Start CreateAppointmentActivity while passing the ReferrerId
     */
    public void goto_appointment()
    {
        Intent intent = new Intent(ReferralActivity.this, CreateAppointmentActivity.class);
        intent.putExtra("REFERRER_ID",doctorId);
        startActivity(intent);
    }
}
