package com.djzass.medipoint.boundary_ui;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.djzass.medipoint.R;

import java.util.Calendar;

/**
 * Activity for entering personal details of creating an account, this page is the second part of the SignUp activity.
 *
 * @author Shreyas
 * @since 2015
 * @version 1.0
 */

public class SignUpPageTwoActivity extends onDataPass {
    /**
     * Contains the static variable of this SignUpPageTwoActivity
     */
    public static Activity PageTwo;
    /**
     * Contains date of birth
     */
    int dateOB = 0;
    /**
     * Contains the month from date of birth
     */
    int monthOB = 0;
    /**
     * Contains the year of birth
     */
    int yearOB = 0;

    /**
     * Called when the activity is starting. This is where most initialization done: calling
     * setContentView(int) to inflate the activity's UI, using findViewById(int) to programmatically
     * interact with widgets in the UI. The static variable {@link SignUpPageOneActivity#PageOne} is also
     * initialized here.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut
     *                           down then this Bundle contains the data it most recently supplied in
     *                           onSaveInstanceState(Bundle). Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);
        PageTwo = this;

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
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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

        return super.onOptionsItemSelected(item);
    }

    /**
     * Go to SignUpActivityPageOne
     */
    public void goToPage1(){
        Intent PageTwoToOne = new Intent(this,SignUpPageOneActivity.class);
        PageTwoToOne.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(PageTwoToOne);
    }

    /**
     * Start SignUpPageThreeActivity while passing the user details retrieved from SignUpPageOneActivity
     * and SignUpPageTwoActvity
     *
     * @param gender
     * @param maritalStatus
     * @param citizenship
     * @param countryOfResidence
     * @param dob
     * @param isEmailChecked
     * @param isSmsChecked
     */
    public void goToPage3(String gender,String maritalStatus,String citizenship,String countryOfResidence,long dob,int isEmailChecked, int isSmsChecked){
        Intent PageTwoToThree = new Intent(this,SignUpPageThreeActivity.class);
        Bundle pageTwo = new Bundle();
        pageTwo.putString("GENDER",gender);
        pageTwo.putString("MARITAL_STATUS",maritalStatus);
        pageTwo.putString("CITIZENSHIP",citizenship);
        pageTwo.putString("COUNTRY_OF_RESIDENCE",countryOfResidence);
        pageTwo.putLong("DOB", dob);
        pageTwo.putInt("NOTIFY_EMAIL",isEmailChecked);
        pageTwo.putInt("NOTIFY_SMS",isSmsChecked);
        PageTwoToThree.putExtra("PAGE_TWO",pageTwo);
        PageTwoToThree.putExtra("PAGE_ONE",getIntent().getBundleExtra("PAGE_ONE"));
        PageTwoToThree.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PageTwoToThree.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(PageTwoToThree);
    }

    /**
     * Go to previous Sign Up Page
     */
    public void goToPrevious()
    {
        goToPage1();
    }

    /**
     * Validate the user progress in the activity. Only when the inputs are all valid user is allowed
     * to go to the next page.
     *
     * @param view
     */
    public void goToNext(View view)
    {
        RadioGroup genderGroup = (RadioGroup)findViewById(R.id.GenderRadioGroup);
        int selGender = genderGroup.getCheckedRadioButtonId();
        RadioButton selGenderButton = (RadioButton)findViewById(selGender);
        RadioGroup maritalStatusGroup = (RadioGroup)findViewById(R.id.MaritalStatusRadioGroup);
        int selMaritalStatus = maritalStatusGroup.getCheckedRadioButtonId();
        RadioButton selMaritalStatusButton = (RadioButton)findViewById(selMaritalStatus);
        Spinner citizenshipSpinner = (Spinner)findViewById(R.id.CitizenshipSpinner);
        Spinner countryOfResidenceSpinner = (Spinner)findViewById(R.id.CountryOfResidenceSpinner);
        CheckBox emailCheckbox = (CheckBox) findViewById(R.id.email);
        CheckBox smsCheckbox = (CheckBox) findViewById(R.id.sms);

        int isEmailChecked = onCheckBoxClicked(emailCheckbox);
        int isSmsChecked = onCheckBoxClicked(smsCheckbox);

        Calendar dobCal = Calendar.getInstance();
        dobCal.set(yearOB, monthOB, dateOB);

        Calendar currentDate = Calendar.getInstance();

        //Check if user has not fill all the form, if invalid a toast will be shown
        if(selGender==-1||selMaritalStatus==-1||(isEmailChecked==0 && isSmsChecked==0)||(dateOB==0 && monthOB==0 && yearOB==0))
            incompleteForm();
        //Check if the Date of Birth is valid, if invalid a toast will be shown
        else if(dobCal.after(currentDate)){
            Toast.makeText(this,"Please enter your date of birth correctly",Toast.LENGTH_LONG).show();
        }
        //If all the fields are filled in and valid, allow user to go to the next page by passing all the
        // details retrieved from SignUpPageTwoActivity
        else
        {
            String gender = selGenderButton.getText().toString();
            String maritalStatus = selMaritalStatusButton.getText().toString();
            String citizenship = citizenshipSpinner.toString();
            String countryOfResidence = countryOfResidenceSpinner.toString();
            long dobLong = dobCal.getTimeInMillis();
            //AccountCreator.savePageTwo(gender,maritalStatus,citizenship,countryOfResidence,dobCal);
            goToPage3(gender,maritalStatus,citizenship,countryOfResidence,dobLong,isEmailChecked,isSmsChecked);
        }
    }

/*    public boolean isFormFilled(EditText[] views,int n)
    {
        String[] str = new String[n];
        for(int i=0;i<n;i++)
        {
            str[i] = views[i].getText().toString();
            if(str[i].equals(""))
                return false;
        }
        return true;
    }
*/

    /**
     * If the form is incomplete, a toast will show up
     */
    public void incompleteForm()
    {
        Toast.makeText(this,"Please fill all fields",Toast.LENGTH_LONG).show();
    }

    /**
     * Change the value of the checkbox
     * @param view
     * @return 1 if checked, 0 if otherwise
     */
    public int onCheckBoxClicked(View view){
        boolean checked = ((CheckBox) view).isChecked();
        return checked ? 1:0;
    }

    /**
     * Show DatePickerFragment when the button select date is clicked
     * @param v
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
     * Retrieve Calendar data selected from the DatePickerFragment
     * @param date chosen from DatePickerFragment
     * @param month chosen from DatePickerFragment
     * @param year chosen from DatePickerFragment
     * @param button from which Activity the DatePickerFragment is called
     */
    public void DatePickerFragmentToActivity(int date, int month, int year, Button button){
        super.DatePickerFragmentToActivity(date,month,year,button);
        dateOB = date;
        monthOB = month;
        yearOB = year;
    }

}

