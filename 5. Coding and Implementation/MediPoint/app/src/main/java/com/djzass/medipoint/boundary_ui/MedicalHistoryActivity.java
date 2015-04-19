package com.djzass.medipoint.boundary_ui;

/**
 * Created by Zillion Govin on 28/3/2015.
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

import java.sql.SQLException;
import java.util.Calendar;


public class MedicalHistoryActivity extends Activity {
    /**
     * an instance of dental info
     */
    String dentalInfo = "";

    /**
     * an instance of ear nose throat info
     */
    String ENTInfo = "";

    /**
     * an instance of genital urinary system info
     */
    String genitalInfo = "";

    /**
     * an instance of other info
     */
    String otherInfo = "";

    /**
     * an instance of medical history
     * include dental, ENT, genital urinary system, and other
     */
    String medicalHistory = "";

    /**
     * an instance of allergy info
     */
    String allergyInfo = "";

    /**
     * an instance of ongoing treatment
     */
    String ongoingTreatment = "";

    /**
     * an instance of ongoing medication
     */
    String ongoingMedication = "";

    /**
     * an instance of Calendar object DOB
     */
    Calendar DOB = Calendar.getInstance();

    /**
     * an instance of patient id
     */
    int patientId;

    /**
     * Called when the activity is first created
     * normal static set up: create views, bind data to lists, etc.
     * @param savedInstanceState Bundle containing the activity's previously frozen state, if there was one
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_history);


        //Form header style and content
        TextView header = (TextView)findViewById(R.id.FormHeader);
        String headerText = "<i>Please <b>fill in this medical history form</b> before proceeding to the application.</i>";
        header.setText(Html.fromHtml(headerText));

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

        //get DOB from intent
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("PAGE_TWO");
        Long DOBLong = bundle.getLong("DOB");
        DOB.setTimeInMillis(DOBLong);

        //get patient from intent
        this.patientId = intent.getIntExtra("ID", 0);
    }

    /**
     * specify the options menu for an activity
     * @param menu menu resource provided in xml
     * @return result
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

        return super.onOptionsItemSelected(item);
    }

    /**
     * Called when the activity has detected the user's press of the back key.
     */
    @Override
    public void onBackPressed(){
        String title = "Skip this form for now?";
        String  message = "You can edit your medical history anytime.";
        Runnable insertPatientDOB = new Runnable() {
            @Override
            public void run(){
                insertPatientToDatabase(new Patient(patientId, DOB, "", "", "", ""));
            }
        };
        Runnable goToLoginPage = new Runnable() {
            @Override
            public void run() {
                goToLoginPage();
            }
        };

        AlertDialogInterface alert = new AlertDialogInterface(title,message,this);
        alert.BackToLogin(insertPatientDOB,goToLoginPage);
    }

    /**
     * insert object Patient to database
     * @param pat Patient object to be inserted to database
     */
    public void insertPatientToDatabase(Patient pat){
        Container.getPatientManager().createPatient(pat,this);
    }

    /**
     * back to login page
     */
    public void goToLoginPage(){
        Intent MedicalHistoryToLogin = new Intent(this,LoginActivity.class);
        startActivity(MedicalHistoryToLogin);
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
                    dentalInfo += ((CheckBox)findViewById(R.id.bleedingGums)).getText() + "\n";
                }
                else{
                    dentalInfo = dentalInfo.replace(((CheckBox)findViewById(R.id.bleedingGums)).getText() + "\n", "");
                }
                break;
            //braces treatment
            case R.id.bracesTreatment:
                if (checked){
                    dentalInfo += ((CheckBox)findViewById(R.id.bracesTreatment)).getText() + "\n";
                }
                else{
                    dentalInfo = dentalInfo.replace(((CheckBox)findViewById(R.id.bracesTreatment)).getText() + "\n", "");
                }
                break;
            //teeth sensitive to hot and cold
            case R.id.teethSensitiveToHotCold:
                if (checked){
                    dentalInfo += ((CheckBox)findViewById(R.id.teethSensitiveToHotCold)).getText() + "\n";
                }
                else{
                    dentalInfo = dentalInfo.replace(((CheckBox)findViewById(R.id.teethSensitiveToHotCold)).getText() + "\n", "");
                }
                break;
            //earaches or neck pains
            case R.id.earachesOrNeckPain:
                if (checked){
                    dentalInfo += ((CheckBox)findViewById(R.id.earachesOrNeckPain)).getText() + "\n";
                }
                else{
                    dentalInfo = dentalInfo.replace(((CheckBox)findViewById(R.id.earachesOrNeckPain)).getText() + "\n", "");
                }
                break;

            //case for ENT
            //Hearing problems
            case R.id.hearingProblems:
                if (checked){
                    ENTInfo += ((CheckBox)findViewById(R.id.hearingProblems)).getText() + "\n";
                }
                else{
                    ENTInfo = ENTInfo.replace( ((CheckBox)findViewById(R.id.hearingProblems)).getText() + "\n", "");
                }
                break;
            //ear infection
            case R.id.earInfection:
                if (checked){
                    ENTInfo += ((CheckBox)findViewById(R.id.earInfection)).getText() + "\n";
                }
                else{
                    ENTInfo = ENTInfo.replace( ((CheckBox)findViewById(R.id.earInfection)).getText() + "\n", "");
                }
                break;
            //nose bleeding
            case R.id.noseBleeding:
                if (checked){
                    ENTInfo += ((CheckBox)findViewById(R.id.noseBleeding)).getText() + "\n";
                }
                else{
                    ENTInfo = ENTInfo.replace( ((CheckBox)findViewById(R.id.noseBleeding)).getText() + "\n", "");
                }
                break;

            //case for genital urinary system
            //blood in urine
            case R.id.bloodInUrine:
                if (checked){
                    genitalInfo += ((CheckBox)findViewById(R.id.bloodInUrine)).getText() + "\n";
                }
                else{
                    genitalInfo = genitalInfo.replace( ((CheckBox)findViewById(R.id.bloodInUrine)).getText() + "\n", "");
                }
                break;
            //hernia
            case R.id.hernia:
                if (checked){
                    genitalInfo += ((CheckBox)findViewById(R.id.hernia)).getText() + "\n";
                }
                else{
                    genitalInfo = genitalInfo.replace( ((CheckBox)findViewById(R.id.hernia)).getText() + "\n", "");
                }
                break;
            //sexually transmitted infections
            case R.id.sexuallyTransmittedInfections:
                if (checked){
                    genitalInfo += ((CheckBox)findViewById(R.id.sexuallyTransmittedInfections)).getText() + "\n";
                }
                else{
                    genitalInfo = genitalInfo.replace( ((CheckBox)findViewById(R.id.sexuallyTransmittedInfections)).getText() + "\n", "");
                }
                break;
            //menstrual period problem
            case R.id.menstrualPeriodProblems:
                if (checked){
                    genitalInfo += ((CheckBox)findViewById(R.id.menstrualPeriodProblems)).getText() + "\n";
                }
                else{
                    genitalInfo = genitalInfo.replace( ((CheckBox)findViewById(R.id.menstrualPeriodProblems)).getText() + "\n", "");
                }
                break;

            //case others
            //high blood pressure
            case R.id.highBloodPressure:
                if (checked){
                    otherInfo += ((CheckBox)findViewById(R.id.highBloodPressure)).getText() + "\n";
                }
                else{
                    otherInfo = otherInfo.replace( ((CheckBox)findViewById(R.id.highBloodPressure)).getText() + "\n", "");
                }
                break;
            //diabetes
            case R.id.diabetes:
                if (checked){
                    otherInfo += ((CheckBox)findViewById(R.id.diabetes)).getText() + "\n";
                }
                else{
                    otherInfo = otherInfo.replace( ((CheckBox)findViewById(R.id.diabetes)).getText() + "\n", "");
                }
                break;
            //gastric problems
            case R.id.gastricProblems:
                if (checked){
                    otherInfo += ((CheckBox)findViewById(R.id.gastricProblems)).getText() + "\n";
                }
                else{
                    otherInfo = otherInfo.replace( ((CheckBox)findViewById(R.id.gastricProblems)).getText() + "\n", "");
                }
                break;
            //heart diseases
            case R.id.heartDiseases:
                if (checked){
                    otherInfo += ((CheckBox)findViewById(R.id.heartDiseases)).getText() + "\n";
                }
                else{
                    otherInfo = otherInfo.replace( ((CheckBox)findViewById(R.id.heartDiseases)).getText() + "\n", "");
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
                }
                break;
        }
    }

    /**
     * submit button listener
     * store all the information to database
     * create new Object Patient
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
            allergyInfo += "Other : " + spec.getText() + "\n";
        }

        if( !((treatmentTest.toString().trim()).equals("")) ){
            ongoingTreatment = treatmentTest.toString();
        }

        if( !((medicationTest.toString().trim()).equals("")) ){
            ongoingMedication = medicationTest.toString();
        }

        //combine all personal history
        medicalHistory = dentalInfo + ENTInfo + genitalInfo + otherInfo;

        //store medicalHistory, allergyInfo, ongoingTreatment, ongoingMedication to DB
        long ret = Container.getPatientManager().createPatient(new Patient(patientId, DOB, medicalHistory, ongoingTreatment, ongoingMedication, allergyInfo),this);

        Toast.makeText(this,"Medical History updated",Toast.LENGTH_SHORT).show();
        //go back to login page after submitting
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
