package com.djzass.medipoint.boundary_ui;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.djzass.medipoint.R;


/**
 * Created by Zillion Govin on 28/3/2015.
 * Fragment class in dialog form
 * called when view is clicked
 */
public class DatePickerFragment extends DialogFragment implements View.OnClickListener {

    /**
     * instance of Button object
     * set date button
     * cancel set date button
     */
    Button set, cancel;

    /**
     * instance of Button object
     * activity button
     */
    Button activityButton;

    /**
     * Called to have the fragment instantiate its user interface view
     * @param inflater Instantiates a layout XML file into its corresponding View objects
     * @param container a special view that can contain other views
     * @param savedInstanceState Bundle containing the fragment's previously frozen state, if there was one
     * @return View of the fragment interface
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.datepicker_fragment, null);

        //button for datepicker fragment
        set = (Button) view.findViewById(R.id.setDate);

        //set listener when button is clicked
        set.setOnClickListener(this);

        int viewID = getArguments().getInt("VIEW_ID");
        activityButton = (Button)getActivity().findViewById(viewID);

        //set dialog title
        getDialog().setTitle("Select Date");

        return view;
    }

    /**
     * Called when the fragment is first created
     * @param savedInstanceState Bundle containing the fragment's previously frozen state, if there was one
     */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
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
     * onclick listener
     * @param v current activity view
     */
    @Override
    public void onClick(View v) {
        //instantiate datepicker
        DatePicker datepicker = (DatePicker)getView().findViewById(R.id.chooseDate);

        //get input from datepicker
        int date = datepicker.getDayOfMonth();
        int month = datepicker.getMonth();
        int year = datepicker.getYear();

        onDataPass activity = (onDataPass)getActivity();

        activity.DatePickerFragmentToActivity(date,month,year,activityButton);

        //close dialog
        dismiss();
    }

}
