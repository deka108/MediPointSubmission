package com.djzass.medipoint.boundary_ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.djzass.medipoint.R;
import com.djzass.medipoint.logic_manager.Container;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * Activity for entering personal details of creating an account, this page is the first part of the SignUp activity.
 *
 * @author Shreyas
 * @since 2015
 * @version 1.0
 */
public class SignUpPageOneActivity extends Activity {
    /**
     * Contains the static variable of this SignUpPageOneActivity
     */
    public static Activity PageOne;


    /**
     * Called when the activity is starting. This is where most initialization done: calling
     * setContentView(int) to inflate the activity's UI, using findViewById(int) to programmatically
     * interact with widgets in the UI. The static variable {@link SignUpPageOneActivity#PageOne} is also
     * initialized here
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut
     *                           down then this Bundle contains the data it most recently supplied in
     *                           onSaveInstanceState(Bundle). Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        PageOne = this;
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
     * Start SignUpPageTwoActivity while passing the user details retrieved from SignUpPageOneActivity
     *
     * @param name
     * @param nric
     * @param email
     * @param contact
     * @param address
     */
    public void goToPage2(String name,String nric,String email,String contact,String address){
        Intent PageOneToTwo = new Intent(this,SignUpPageTwoActivity.class);
        Bundle pageOne = new Bundle();
        pageOne.putString("NAME",name);
        pageOne.putString("NRIC",nric);
        pageOne.putString("EMAIL",email);
        pageOne.putString("CONTACT",contact);
        pageOne.putString("ADDRESS",address);
        PageOneToTwo.putExtra("PAGE_ONE",pageOne);
        PageOneToTwo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PageOneToTwo.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(PageOneToTwo);
    }

    /**
     * Start LoginActivity
     */
    public void goToLoginPage()
    {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    /**
     * Validate the nric, email, and contact number. If the inputs are valid, then allow going to the
     * next Sign Up page.
     * @param view
     */
    public void goToNext(View view)
    {
                EditText[] checkViews = new EditText[5];
                checkViews[0] = (EditText) findViewById(R.id.NameTextbox);
                checkViews[1] = (EditText) findViewById(R.id.NRICTextbox);
                checkViews[2] = (EditText) findViewById(R.id.EmailTextbox);
                checkViews[3] = (EditText) findViewById(R.id.ContactTextbox);
                checkViews[4] = (EditText) findViewById(R.id.AddressTextbox);

                String name = checkViews[0].getText().toString();
                String nric = checkViews[1].getText().toString();
                String email = checkViews[2].getText().toString();
                String contact = checkViews[3].getText().toString();
                String address = checkViews[4].getText().toString();

                //If the NRIC length is invalid, a toast will be shown
                if(!isFormFilled(checkViews,5)){
                    Toast.makeText(this,"Please fill all fields",Toast.LENGTH_LONG).show();
                }
                //If the NRIC length is invalid, a toast will be shown
                else if(!isValidNricChars(nric)){
                    Toast.makeText(this,"NRIC can only contain alphabets and numbers",Toast.LENGTH_LONG).show();
                }
                //If the NRIC length is invalid, a toast will be shown
                else if(!isValidNricLength(nric)){
                    Toast.makeText(this,"NRIC has to be between 4 and 16 characters long",Toast.LENGTH_LONG).show();
                }
                //If username already exist, AlertDialog will be shown
                else if(!Container.getAccountManager().isNewAccount(nric, this)){
                    Runnable r = new Runnable() {
                        @Override
                        public void run() {
                            goToLoginPage();
                        }
                    };
                    String title = "Existing account";
                    String message = "You already have an existing account";
                    AlertDialogInterface AlertDisplayer = new AlertDialogInterface(title,message,this);
                    AlertDisplayer.AccountAlreadyExists(r);
                }
                //If email is invalid, a toast will be shown
                else if(!isValidEmailAddress(email)){
                    Toast.makeText(this,"Please enter a valid email address",Toast.LENGTH_LONG).show();
                }
                //If email length is invalid, a toast will be shown
                else if(!isValidEmailAddressLength(email)){
                    Toast.makeText(this,"Email address too long",Toast.LENGTH_LONG).show();
                }
                //If contact is invalid, a toast will be shown
                else if(!isValidContactNoChars(contact)){
                    Toast.makeText(this,"Contact number can only contain numbers",Toast.LENGTH_LONG).show();
                }
                //If contact number length is invalid, a toast will be shown
                else if(!isValidContactNoLength(contact)){
                    Toast.makeText(this,"Contact number has to be between 4 to 16 characters long",Toast.LENGTH_LONG).show();
                }
                //If all other fields are valid, allow user to go to the next stage
                else {
                    //AccountCreator.savePageOne(name,nric,email,contact,address);
                    goToPage2(name,nric,email,contact,address);
                }
    }

    /**
     * Check whether the form is filled
     * @param views
     * @param n
     * @return boolean value, returns true if the textbox is filled, and false otherwise.
     */
    public boolean isFormFilled(EditText[] views,int n)
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

    /**
     * Validates NRIC, valid NRIC can only contain alphanumeric characters
     * @param nric
     * @return boolean value true, or false
     */
    public static boolean isValidNricChars(String nric) {
        return(nric.matches("[a-zA-Z0-9]*")); //only alphanumeric
    }

    /**
     * Validates NRIC length, valid NRIC length is between 4 to 16 characters
     * @param nric
     * @return boolean value true, or false
     */
    public static boolean isValidNricLength(String nric) {
        return(nric.length()<=16 && nric.length()>=4); //4<length<16
    }

    /**
     * Validates email address, valid email address exist in the internet in aa@bb.domain format
     * @param email
     * @return boolean value true, or false
     */
    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    /**
     * Validates email address length, valid email address must be less than 40 characters
     * @param email
     * @return boolean value true, or false
     */
    public static boolean isValidEmailAddressLength(String email) {
        return(email.length()<=40); //<40 chars
    }

    /**
     * Validates contact number, valid contact number can only contains digits
     * @param contactno
     * @return boolean value true, or false
     */
    public static boolean isValidContactNoChars(String contactno) {
        return (contactno.matches("[0-9]*")); //only numeric,
    }

    /**
     * Validates contact number length, valid contact number must be between 4 to 16 digits
     * @param contactno
     * @return
     */
    public static boolean isValidContactNoLength(String contactno) {
        return(contactno.length()<=16 && contactno.length()>=4); //4<length<16
    }
}

