package com.djzass.medipoint.boundary_ui;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.djzass.medipoint.R;
import com.djzass.medipoint.entity.Appointment;
import com.djzass.medipoint.logic_manager.Container;
import com.djzass.medipoint.logic_manager.SessionManager;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Deka on 4/4/2015.
 *
 * Display the list of appointments of the patient sorted by date in descending order.
 *
 * @author Deka
 * @version 1.0
 * @since 2015
 *
 * @see android.support.v4.app.Fragment
 */
public class AppointmentListFragment extends Fragment implements ActionBar.OnNavigationListener{
    /**
     * Display dropdown spinners for different appointment types creation.
     *
     */
    Spinner buttonSpinner;
    /**
     * Contains the appointments that the patient have
     */
    ArrayList<Appointment> appointments;

    /**
     * Stores the patientId
     */
    private int patientId;

    /**
     * Gets and creates a new instance of a Fragment with the given class name
     * @return the new instance of the Fragment
     */
    public static AppointmentListFragment newInstance() {
        AppointmentListFragment fragment = new AppointmentListFragment();
        return fragment;
    }

    /**
     * Called when the activity is starting, perform initialization including retrieving the patientId and list
     * of appointments that this patient have.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down
     *                           then this Bundle contains the data it most recently supplied in
     *                           onSaveInstanceState(Bundle). Otherwise it is null.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appointments = new ArrayList<Appointment>();

        //Gets the patient Id
        SessionManager sessionManager = new SessionManager(getActivity());
        try {
            this.patientId = (int)sessionManager.getAccountId();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Gets the appointments of this patients
        appointments = (ArrayList<Appointment>) Container.getAppointmentManager().getPatientAppointmentList(this.patientId, this.getActivity());

        //Sort the appointment according to the date in descending order
        appointments = (ArrayList<Appointment>) Container.getAppointmentManager().sortByDate(appointments);
    }

    /**
     * Called whenever a navigation item in action bar is selected.
     * @param position Position of the item clicked
     * @param id ID of the item clicked
     * @return True if the event was handled, false otherwise
     *
     */
    @Override
    public boolean onNavigationItemSelected(int position, long id) {
        // When the given dropdown item is selected, show its contents in the container view.
        switch (position){
            case 0:
                Intent appIntent = new Intent(getActivity().getApplicationContext(),CreateAppointmentActivity.class);
                startActivity(appIntent);
            case 1:
                Intent refIntent = new Intent(getActivity().getApplicationContext(),ReferralActivity.class);
                startActivity(refIntent);
            case 2:
                Intent followIntent = new Intent(getActivity().getApplicationContext(),FollowUpListActivity.class);
                startActivity(followIntent);
                break;
            default:
                break;
        }

        return true;
    }

    /**
     * Creates and returns the view hierarchy associated with the fragment.
     *
     * @param inflater  the LayoutInflater object that can be used to inflate any views in the fragment
     * @param container if non-null, this is the parent view that the fragment's UI should be attached to.
     * The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here
     * @return Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_appointment_list, container, false);

        TextView tv = (TextView)view.findViewById(R.id.noAppointmentText);
        if (appointments.size() > 0){
            tv.setVisibility(view.GONE);
            ListView apptList = (ListView)view.findViewById(R.id.customListView);
            AppointmentAdapter apptAdapter = null;
            try {
                apptAdapter = new AppointmentAdapter(getActivity(), appointments);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            apptList.setAdapter(apptAdapter);

            apptList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView <?> parent, View view, int position, long id) {
                    Appointment app = (Appointment) parent.getAdapter().getItem(position);
                    //Toast.makeText(getApplicationContext(), app.toString(), Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(getActivity().getApplicationContext(), ViewAppointmentActivity.class);
                    in.putExtra("appObj", app);
                    startActivity(in);
                    /*Toast.makeText(getApplicationContext(),
                            "Click ListItem Number " + position, Toast.LENGTH_SHORT)
                            .show();*/
                }
            });
        }
        else{
            tv.setText("No appointment available");
            tv.setVisibility(view.VISIBLE);
        }

        //Display the dropdown items for create appointment, referral and follow up activity
        buttonSpinner= (Spinner) view.findViewById(R.id.buttonSpinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this.getActivity(),R.array.create_new, android.R.layout.simple_spinner_dropdown_item);

        buttonSpinner.setAdapter(adapter);
        buttonSpinner.setSelection(0);
        buttonSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView adapterView, View v, int i, long lng) {
                String choice = buttonSpinner.getSelectedItem().toString();

                switch(choice){
                    case "Appointment":     buttonSpinner.setSelection(0);
                        goToCreateAppointment();
                        break;
                    case "Referral":        buttonSpinner.setSelection(0);
                        goToCreateReferral();
                        break;
                    case "Follow Up":       buttonSpinner.setSelection(0);
                        goToCreateFollowUp();
                        break;
                    default:
                        buttonSpinner.setSelection(0);

                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView)
            {
            }
        });

        return view;
    }

    /**
     * Starts create appointment activity
     */
    public void goToCreateAppointment()
    {
        Intent intent = new Intent(getActivity(), CreateAppointmentActivity.class);
        startActivity(intent);
    }

    /**
     *  Starts referral activity
     */
    public void goToCreateReferral()
    {
        Intent intent = new Intent(getActivity(), ReferralActivity.class);
        startActivity(intent);
    }

    /**
     * Starts follow up appointment list activity
     */
    public void goToCreateFollowUp()
    {
        Intent intent = new Intent(getActivity(), FollowUpListActivity.class);
        startActivity(intent);
    }
}
