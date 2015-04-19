package com.djzass.medipoint.boundary_ui;

/**
 * Created by Zillion Govin on 11/4/2015.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.djzass.medipoint.R;
import com.djzass.medipoint.entity.Patient;
import com.djzass.medipoint.logic_manager.Container;
import com.djzass.medipoint.logic_manager.SessionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class EditHistoryActivity extends Activity {

    /**
     * an instance of allergy info
     */
    String allergyInfo = "";

    /**
     * an instance of medical history
     */
    String medicalHistory = "";

    /**
     * an instance of ongoing treatment
     */
    String ongoingTreatment = "";

    /**
     * an instance of ongoing medication
     */
    String ongoingMedication = "";


    /**
     * an instance of Calendar object
     * contains DOB
     */
    Calendar DOB = Calendar.getInstance();

    /**
     * an instance of patient id
     */
    Long patientId;

    /**
     * an instance of list of patient
     */
    List<Patient> patientList = new ArrayList<>();

    /**
     * Called when the activity is first created
     * normal static set up: create views, bind data to lists, etc.
     * @param savedInstanceState Bundle containing the activity's previously frozen state, if there was one
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_history);


        //Form header style and content
        TextView header = (TextView)findViewById(R.id.FormHeader);
        String headerText = "<i>It is <b>recommended</b> to fill in this form as we always try to provide the most suitable treatment for our patients.</i>";
        header.setText(Html.fromHtml(headerText));

        //get patient id
        try {
            //instantiate the DAO
            SessionManager sessionManager = new SessionManager(this);
            this.patientId = sessionManager.getAccountId();
            patientList = Container.getPatientManager().getPatientsByID(patientId.intValue(),this);

            //get existing history
            this.medicalHistory = patientList.get(0).getMedicalHistory();
            this.allergyInfo = patientList.get(0).getAllergy();
            this.ongoingTreatment = patientList.get(0).getListOfTreatments();
            this.ongoingMedication = patientList.get(0).getListOfMedications();
            this.DOB = patientList.get(0).getDob();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        View view = this.findViewById(android.R.id.content);
        setChecked(view);


        //set submit button listener
        Button submit = (Button)findViewById(R.id.submitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                try {
                    onSubmit();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * specify the options menu for an activity
     * @param menu menu resource provided in xml
     * @return result
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * an item is selected from the option menu
     * @param item menu item selected
     * @return result
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
     * Checkbox listener
     * @param view view of current activity
     */
    public void onCheckboxClicked(View view){

        //define boolean checked
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {

            //case for dental info
            //bleeding gums
            case R.id.bleedingGums:
                if (checked){
                    medicalHistory += ((CheckBox)findViewById(R.id.bleedingGums)).getText() + "\n";
                }
                else{
                    medicalHistory = medicalHistory.replace(((CheckBox)findViewById(R.id.bleedingGums)).getText() + "\n", "");
                }
                break;
            //braces treatment
            case R.id.bracesTreatment:
                if (checked){
                    medicalHistory += ((CheckBox)findViewById(R.id.bracesTreatment)).getText() + "\n";
                }
                else{
                    medicalHistory = medicalHistory.replace(((CheckBox)findViewById(R.id.bracesTreatment)).getText() + "\n", "");
                }
                break;
            //teeth sensitive to hot and cold
            case R.id.teethSensitiveToHotCold:
                if (checked){
                    medicalHistory += ((CheckBox)findViewById(R.id.teethSensitiveToHotCold)).getText() + "\n";
                }
                else{
                    medicalHistory = medicalHistory.replace(((CheckBox)findViewById(R.id.teethSensitiveToHotCold)).getText() + "\n", "");
                }
                break;
            //earaches or neck pains
            case R.id.earachesOrNeckPain:
                if (checked){
                    medicalHistory += ((CheckBox)findViewById(R.id.earachesOrNeckPain)).getText() + "\n";
                }
                else{
                    medicalHistory = medicalHistory.replace(((CheckBox)findViewById(R.id.earachesOrNeckPain)).getText() + "\n", "");
                }
                break;

            //case for ENT
            //Hearing problems
            case R.id.hearingProblems:
                if (checked){
                    medicalHistory += ((CheckBox)findViewById(R.id.hearingProblems)).getText() + "\n";
                }
                else{
                    medicalHistory = medicalHistory.replace( ((CheckBox)findViewById(R.id.hearingProblems)).getText() + "\n", "");
                }
                break;
            //ear infection
            case R.id.earInfection:
                if (checked){
                    medicalHistory += ((CheckBox)findViewById(R.id.earInfection)).getText() + "\n";
                }
                else{
                    medicalHistory = medicalHistory.replace( ((CheckBox)findViewById(R.id.earInfection)).getText() + "\n", "");
                }
                break;
            //nose bleeding
            case R.id.noseBleeding:
                if (checked){
                    medicalHistory += ((CheckBox)findViewById(R.id.noseBleeding)).getText() + "\n";
                }
                else{
                    medicalHistory = medicalHistory.replace( ((CheckBox)findViewById(R.id.noseBleeding)).getText() + "\n", "");
                }
                break;

            //case for genital urinary system
            //blood in urine
            case R.id.bloodInUrine:
                if (checked){
                    medicalHistory += ((CheckBox)findViewById(R.id.bloodInUrine)).getText() + "\n";
                }
                else{
                    medicalHistory = medicalHistory.replace( ((CheckBox)findViewById(R.id.bloodInUrine)).getText() + "\n", "");
                }
                break;
            //hernia
            case R.id.hernia:
                if (checked){
                    medicalHistory += ((CheckBox)findViewById(R.id.hernia)).getText() + "\n";
                }
                else{
                    medicalHistory = medicalHistory.replace( ((CheckBox)findViewById(R.id.hernia)).getText() + "\n", "");
                }
                break;
            //sexually transmitted infections
            case R.id.sexuallyTransmittedInfections:
                if (checked){
                    medicalHistory += ((CheckBox)findViewById(R.id.sexuallyTransmittedInfections)).getText() + "\n";
                }
                else{
                    medicalHistory = medicalHistory.replace( ((CheckBox)findViewById(R.id.sexuallyTransmittedInfections)).getText() + "\n", "");
                }
                break;
            //menstrual period problem
            case R.id.menstrualPeriodProblems:
                if (checked){
                    medicalHistory += ((CheckBox)findViewById(R.id.menstrualPeriodProblems)).getText() + "\n";
                }
                else{
                    medicalHistory = medicalHistory.replace( ((CheckBox)findViewById(R.id.menstrualPeriodProblems)).getText() + "\n", "");
                }
                break;

            //case others
            //high blood pressure
            case R.id.highBloodPressure:
                if (checked){
                    medicalHistory += ((CheckBox)findViewById(R.id.highBloodPressure)).getText() + "\n";
                }
                else{
                    medicalHistory = medicalHistory.replace( ((CheckBox)findViewById(R.id.highBloodPressure)).getText() + "\n", "");
                }
                break;
            //diabetes
            case R.id.diabetes:
                if (checked){
                    medicalHistory += ((CheckBox)findViewById(R.id.diabetes)).getText() + "\n";
                }
                else{
                    medicalHistory = medicalHistory.replace( ((CheckBox)findViewById(R.id.diabetes)).getText() + "\n", "");
                }
                break;
            //gastric problems
            case R.id.gastricProblems:
                if (checked){
                    medicalHistory += ((CheckBox)findViewById(R.id.gastricProblems)).getText() + "\n";
                }
                else{
                    medicalHistory = medicalHistory.replace( ((CheckBox)findViewById(R.id.gastricProblems)).getText() + "\n", "");
                }
                break;
            //heart diseases
            case R.id.heartDiseases:
                if (checked){
                    medicalHistory += ((CheckBox)findViewById(R.id.heartDiseases)).getText() + "\n";
                }
                else{
                    medicalHistory = medicalHistory.replace( ((CheckBox)findViewById(R.id.heartDiseases)).getText() + "\n", "");
                }
                break;

            //case for drug allergies
            //local anesthetics
            case R.id.localAnesthetics:
                if (checked){
                    allergyInfo += ((CheckBox)findViewById(R.id.localAnesthetics)).getText() + "\n";
                }
                else{
                    allergyInfo = allergyInfo.replace( ((CheckBox)findViewById(R.id.localAnesthetics)).getText() + "\n", "");
                }
                break;
            //aspirin
            case R.id.aspirin:
                if (checked){
                    allergyInfo += ((CheckBox)findViewById(R.id.aspirin)).getText() + "\n";
                }
                else{
                    allergyInfo = allergyInfo.replace( ((CheckBox)findViewById(R.id.aspirin)).getText() + "\n", "");
                }
                break;
            //penicillin or other antibiotics
            case R.id.antibiotics:
                if (checked){
                    allergyInfo += ((CheckBox)findViewById(R.id.antibiotics)).getText() + "\n";
                }
                else{
                    allergyInfo = allergyInfo.replace( ((CheckBox)findViewById(R.id.antibiotics)).getText() + "\n", "");
                }
                break;
            //sulfa drugs
            case R.id.sulfaDrugs:
                if (checked){
                    allergyInfo += ((CheckBox)findViewById(R.id.sulfaDrugs)).getText() + "\n";
                }
                else{
                    allergyInfo = allergyInfo.replace( ((CheckBox)findViewById(R.id.sulfaDrugs)).getText() + "\n", "");
                }
                break;
            //others
            case R.id.Other:
                //will take the edittext input after pressing submit
                //in this case, just show the visibility
                EditText spec = (EditText) findViewById(R.id.Specification);
                if (checked){
                    spec.setVisibility(View.VISIBLE);
                }
                else{
                    spec.setText("");
                    spec.setVisibility(View.GONE);
                    this.allergyInfo = this.allergyInfo.replace( "Other : " + getOther(this.allergyInfo) + "\n" ,"" );
                }
                break;
        }
    }

    /**
     * submit button listener
     * update Patient information
     * @throws SQLException for safety reason while accessing database
     */
    public void onSubmit() throws SQLException {

        //get edittext
        EditText spec = (EditText) findViewById(R.id.Specification);
        EditText ongoingTreatmentEditable = (EditText) findViewById(R.id.ongoingTreatment);
        EditText ongoingMedicationEditable = (EditText) findViewById(R.id.ongoingMedication);

        //make a copy of each text
        CharSequence specTest = spec.getText();
        CharSequence treatmentTest = ongoingTreatmentEditable.getText();
        CharSequence medicationTest = ongoingMedicationEditable.getText();

        //check if they only contain whitespaces
        if( !((specTest.toString().trim()).equals("")) ){
            if( getOther(allergyInfo) == null || getOther(allergyInfo).trim().equals("")){
                allergyInfo += "Other : " + spec.getText() + "\n";
            }
            else{
                allergyInfo = allergyInfo.replace(getOther(allergyInfo), spec.getText());
            }
        }
        else{
            allergyInfo = allergyInfo.replace("Other : " + getOther(allergyInfo) + "\n", "");
        }

        if( !((treatmentTest.toString().trim()).equals("")) ){
            ongoingTreatment = treatmentTest.toString();
        }
        else{
            ongoingTreatment = "";
        }

        if( !((medicationTest.toString().trim()).equals("")) ){
            ongoingMedication = medicationTest.toString();
        }
        else{
            ongoingMedication = "";
        }

        //update medicalHistory, allergyInfo, ongoingTreatment, ongoingMedication to DB
        Patient updatedPatient = new Patient(patientId.intValue(),DOB, medicalHistory, ongoingTreatment, ongoingMedication, allergyInfo);
        Container.getPatientManager().editPatient(updatedPatient,this);


        Toast.makeText(this,"Medical History updated",Toast.LENGTH_SHORT).show();

        //go back to login page after submitting
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * show all the information from database
     * @param view Current activity view
     */
    public void setChecked(View view){
        //check the box for existing information
        //dental info
        CheckBox bleedingGums = (CheckBox) findViewById(R.id.bleedingGums);
        CheckBox bracesTreatment = (CheckBox) findViewById(R.id.bracesTreatment);
        CheckBox sensitiveTeeth = (CheckBox) findViewById(R.id.teethSensitiveToHotCold);
        CheckBox earAchesNeckPain = (CheckBox) findViewById(R.id.earachesOrNeckPain);

        if(this.medicalHistory.contains(bleedingGums.getText())){
            bleedingGums.setChecked(true);
        }
        if(this.medicalHistory.contains(bracesTreatment.getText())){
            bracesTreatment.setChecked(true);
        }
        if(this.medicalHistory.contains(sensitiveTeeth.getText())){
            sensitiveTeeth.setChecked(true);
        }
        if(this.medicalHistory.contains(earAchesNeckPain.getText())){
            earAchesNeckPain.setChecked(true);
        }

        //ENT info
        CheckBox hearingProblems = (CheckBox) findViewById(R.id.hearingProblems);
        CheckBox earInfection = (CheckBox) findViewById(R.id.earInfection);
        CheckBox noseBleeding = (CheckBox) findViewById(R.id.noseBleeding);

        if(this.medicalHistory.contains(hearingProblems.getText())){
            hearingProblems.setChecked(true);
        }
        if(this.medicalHistory.contains(earInfection.getText())){
            earInfection.setChecked(true);
        }
        if(this.medicalHistory.contains(noseBleeding.getText())){
            noseBleeding.setChecked(true);
        }

        //genital system
        CheckBox bloodUrine = (CheckBox) findViewById(R.id.bloodInUrine);
        CheckBox hernia = (CheckBox) findViewById(R.id.hernia);
        CheckBox STD = (CheckBox) findViewById(R.id.sexuallyTransmittedInfections);
        CheckBox menstrualPeriod = (CheckBox) findViewById(R.id.menstrualPeriodProblems);

        if(this.medicalHistory.contains(bloodUrine.getText())){
            bloodUrine.setChecked(true);
        }
        if(this.medicalHistory.contains(hernia.getText())){
            hernia.setChecked(true);
        }
        if(this.medicalHistory.contains(STD.getText())){
            STD.setChecked(true);
        }
        if(this.medicalHistory.contains(menstrualPeriod.getText())){
            menstrualPeriod.setChecked(true);
        }

        //others info
        CheckBox highBloodPressure = (CheckBox) findViewById(R.id.highBloodPressure);
        CheckBox diabetes = (CheckBox) findViewById(R.id.diabetes);
        CheckBox gastricProblem = (CheckBox) findViewById(R.id.gastricProblems);
        CheckBox heartDisease = (CheckBox) findViewById(R.id.heartDiseases);

        if(this.medicalHistory.contains(highBloodPressure.getText())){
            highBloodPressure.setChecked(true);
        }
        if(this.medicalHistory.contains(diabetes.getText())){
            diabetes.setChecked(true);
        }
        if(this.medicalHistory.contains(gastricProblem.getText())){
            gastricProblem.setChecked(true);
        }
        if(this.medicalHistory.contains(heartDisease.getText())){
            heartDisease.setChecked(true);
        }

        //allergy
        CheckBox localAnesthetics = (CheckBox) findViewById(R.id.localAnesthetics);
        CheckBox aspirin = (CheckBox) findViewById(R.id.aspirin);
        CheckBox antibiotics = (CheckBox) findViewById(R.id.antibiotics);
        CheckBox sulfaDrugs = (CheckBox) findViewById(R.id.sulfaDrugs);
        CheckBox others = (CheckBox) findViewById(R.id.Other);
        EditText specification = (EditText) findViewById(R.id.Specification);

        if(this.allergyInfo.contains(localAnesthetics.getText())){
            localAnesthetics.setChecked(true);
        }
        if(this.allergyInfo.contains(aspirin.getText())){
            aspirin.setChecked(true);
        }
        if(this.allergyInfo.contains(antibiotics.getText())){
            antibiotics.setChecked(true);
        }
        if(this.allergyInfo.contains(sulfaDrugs.getText())){
            sulfaDrugs.setChecked(true);
        }
        if(this.allergyInfo.contains("Other")){
            others.setChecked(true);
            specification.setVisibility(View.VISIBLE);
            specification.setText(getOther(this.allergyInfo));
        }


        //ongoing treatment
        EditText ongoingTreatment = (EditText) findViewById(R.id.ongoingTreatment);
        if(!(this.ongoingTreatment.equals("No ongoing treatment."))) {
            ongoingTreatment.setText(this.ongoingTreatment);
        }

        //ongoing medication
        EditText ongoingMedication = (EditText) findViewById(R.id.ongoingMedication);
        if(!(this.ongoingMedication.equals("No ongoing medication."))) {
            ongoingMedication.setText(this.ongoingMedication);
        }

    }

    /**
     * get substring after 'Other :'
     * @param inp String input
     * @return String object containing substring
     */
    private String getOther(String inp){
        String[] lines = inp.split("\n");
        for (String s:lines){
            if (s.contains("Other : ")){
                return s.substring(8);
            }
        }
        return "";
    }

}

