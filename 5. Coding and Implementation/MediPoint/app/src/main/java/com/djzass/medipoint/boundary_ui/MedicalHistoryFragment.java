package com.djzass.medipoint.boundary_ui;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.djzass.medipoint.R;
import com.djzass.medipoint.entity.Patient;
import com.djzass.medipoint.logic_manager.Container;
import com.djzass.medipoint.logic_manager.SessionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Zillion Govin on 1/4/2015.
 * fragment class of view medical history
 * displayed in main activity
 */
public class MedicalHistoryFragment extends Fragment {

    /**
     * an instance of patient id
     */
    long patientId;

    /**
     * an instance of patient name
     */
    private String name;

    /**
     * an instance of patient gender
     */
    private String patientGender;

    /**
     * an instance of patient age
     */
    private int patientAge;

    /**
     * an instance of patient medical history
     */
    private String medicalHistory;

    /**
     * an instance of patient allergy
     */
    private String allergy;

    /**
     * an instance of patient ongoing treatment
     */
    private String ongoingTreatment;

    /**
     * an instance of patient ongoing medication
     */
    private String ongoingMedication;

    /**
     * an instance of list of patient
     */
    List<Patient> patient = new ArrayList<>();

    /**
     * new instance of medical history fragment
      * @return fragment of medical history
     */
    public static MedicalHistoryFragment newInstance() {
        MedicalHistoryFragment fragment = new MedicalHistoryFragment();
        return fragment;
    }

    /**
     * Called when the fragment is first created
     * @param savedInstanceState Bundle containing the fragment's previously frozen state, if there was one
     */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //get patient id
        try {
            SessionManager sessionManager = new SessionManager(this.getActivity());
            this.patientId = sessionManager.getAccountId();
            patient = Container.getPatientManager().getPatientsByID((int)patientId,this.getActivity());

            //get name and gender from account table
            Cursor cursor = Container.getAccountManager().getAccountCursorById(patientId,this.getActivity());
            if(cursor!=null && cursor.moveToFirst()){
                this.name = cursor.getString(1);
                this.patientGender = cursor.getString(7);
            }


            //get medical history and age in patient table
            this.patientAge = patient.get(0).getAge();
            this.medicalHistory = patient.get(0).getMedicalHistory();
            this.allergy = patient.get(0).getAllergy();
            this.ongoingTreatment = patient.get(0).getListOfTreatments();
            this.ongoingMedication = patient.get(0).getListOfMedications();

            Calendar DOB = patient.get(0).getDob();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when the activity is first created
     * normal static set up: create views, bind data to lists, etc.
     * @param savedInstanceState Bundle containing the activity's previously frozen state, if there was one
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * Called when the Fragment is visible to the user.
     */
    @Override
    public void onStart(){
        super.onStart();
    }

    /**
     * Called when the fragment is visible to the user and actively running.
     */
    @Override
    public void onResume(){
        super.onResume();
    }

    /**
     * Called when the Fragment is no longer resumed.
     */
    @Override
    public void onPause(){
        super.onPause();
    }

    /**
     * Called to ask the fragment to save its current dynamic state
     * so it can later be reconstructed in a new instance of its process is restarted.
     * @param outState Bundle containing the saved state
     */
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }

    /**
     * Called when the Fragment is no longer started.
     */
    @Override
    public void onStop(){
        super.onStop();
    }

    /**
     * Called when the view previously created by onCreateView has been detached from the fragment.
     */
    @Override
    public void onDestroyView(){
        super.onDestroyView();
    }

    /**
     * Called when the fragment is no longer in use
     */
    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    /**
     * Called when the fragment is no longer attached to its activity
     */
    @Override
    public void onDetach(){
        super.onDetach();
    }

    /**
     * Called to have the fragment instantiate its user interface view
     * @param inflater Instantiates a layout XML file into its corresponding View objects
     * @param container a special view that can contain other views
     * @param savedInstanceState Bundle containing the fragment's previously frozen state, if there was one
     * @return View of the fragment interface
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_view_medical_history, null);

        //get patientName textview
        TextView patientName = (TextView) view.findViewById(R.id.patientName);
        //set the patient name
        if(this.name == null || this.name.trim().equals("")){
            patientName.setText("Error, name is not specified.");
        }
        else {
            patientName.setText(this.name);
        }

        //get patientAge textview
        TextView patientAge = (TextView) view.findViewById(R.id.patientAge);
        //set the patient age
        patientAge.setText(""+this.patientAge);

        //get patientGender textview
        TextView patientGender = (TextView) view.findViewById(R.id.patientGender);
        //set the patient gender
        if(this.patientGender == null || this.patientGender.trim().equals("")){
            patientGender.setText("Error, gender is not specified.");
        }
        else {
            patientGender.setText(this.patientGender);
        }

        //get medicalHistory textview
        TextView medicalHistory = (TextView) view.findViewById(R.id.medicalHistory);
        //set medical history
        if(this.medicalHistory == null || this.medicalHistory.trim().equals("")){
            medicalHistory.setText("No medical history.\n");
        }
        else {
            medicalHistory.setText(this.medicalHistory);
        }

        //get allergy textview
        TextView allergy = (TextView) view.findViewById(R.id.allergy);
        //set allergy
        if(this.allergy == null || this.allergy.trim().equals("")){
            allergy.setText("No allergy.\n");
        }
        else {
            allergy.setText(this.allergy);
        }

        //get ongoingTreatment textview
        TextView ongoingTreatment = (TextView) view.findViewById(R.id.ongoingTreatment);
        //set ongoing Treatment
        if(this.ongoingTreatment == null || this.ongoingTreatment.trim().equals("")){
            ongoingTreatment.setText("No ongoing treatment.");
        }
        else {
            ongoingTreatment.setText(this.ongoingTreatment);
        }

        //get ongoingMedication textview
        TextView ongoingMedication = (TextView) view.findViewById(R.id.ongoingMedication);
        //set ongoing medication
        if(this.ongoingMedication == null || this.ongoingMedication.trim().equals("")){
            ongoingMedication.setText("No ongoing medication.");
        }
        else {
            ongoingMedication.setText(this.ongoingMedication);
        }


        Button editHistory = (Button) view.findViewById(R.id.editHistory);
        editHistory.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                onEdit();
            }
        });

        return view;
    }

    /**
     * Edit button listener
     */
    public void onEdit(){
        Intent intent = new Intent(this.getActivity(), EditHistoryActivity.class);
        startActivity(intent);
    }
}
