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
import com.djzass.medipoint.logic_manager.AccountManager;
import com.djzass.medipoint.logic_manager.Container;

/**
 * Activity for entering personal details of creating an account, this page is the third part of the SignUp activity.
 *
 * @author Shreyas
 * @since 2015
 * @version 1.0
 */
public class SignUpPageThreeActivity extends Activity {
    /**
     * Contains the account manager
     */
    private AccountManager AccountCreator;

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
        setContentView(R.layout.activity_sign_up3);
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

    public void goToPage2(){
        Intent PageThreeToTwo = new Intent(this,SignUpPageTwoActivity.class);
        PageThreeToTwo.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(PageThreeToTwo);
    }

    /*public void goToPage3(){
        setContentView(R.layout.activity_sign_up3);
    }*/

    /**
     * Check the password with the password confirmation field
     * @param Password
     * @param ConfirmPassword
     * @return boolean value, return true if both fields match and false otherwise
     */
    public boolean checkPassword(EditText Password,EditText ConfirmPassword)
    {
        String pass1 = Password.getText().toString();
        String pass2 = ConfirmPassword.getText().toString();
        return pass1.equals(pass2);
    }

    /**
     * Go to MedicalHistory form
     * @param username
     * @param password
     * @param PageThreeToHistory
     */
    public void goToMedicalHistoryForm(String username,String password,Intent PageThreeToHistory)
    {
        SignUpPageOneActivity.PageOne.finish();
        SignUpPageTwoActivity.PageTwo.finish();
        this.finish();
        startActivity(PageThreeToHistory);
    }

    /**
     * Go to previous SignUp Activity page
     * @param view
     */
    public void goToPrevious(View view)
    {
        goToPage2();
    }

    /**
     * Perform validation towards user inputs. Only allows user to go create an Account when all the fields
     * are valid and complete.
     *
     * @param view
     */
    public void goToNext(View view)
    {
        EditText[] checkViews = new EditText[3];
        String[] str = new String[3];
        checkViews[0] = (EditText) findViewById(R.id.UsernameTextbox);
        checkViews[1] = (EditText) findViewById(R.id.PasswordTextbox);
        checkViews[2] = (EditText) findViewById(R.id.ConfirmPasswordTextbox);

        boolean isFilled = isFormFilled(checkViews,3);
        boolean usernameExists = Container.getAccountManager().doesUsernameExist(checkViews[0].getText().toString(), this);
        boolean isPasswordEqual = checkPassword(checkViews[1],checkViews[2]);

        //If form is incomplete, a toast will show up
        if(!isFilled) {
            Toast.makeText(this,"Please fill all fields",Toast.LENGTH_LONG).show();
        }
        //If username already exist, a toast will show up
        else if(usernameExists){
            Toast.makeText(this,"Username already exists",Toast.LENGTH_LONG).show();
        }
        //If the username is invalid, a toast will show up
        else if(!isValidUsernameChars(checkViews[0].getText().toString())){
            Toast.makeText(this,"Username can only contain alphabets and numbers",Toast.LENGTH_LONG).show();
        }
        //If the username length is invalid, a toast will show up
        else if(!isValidUsernameLength(checkViews[0].getText().toString())){
            Toast.makeText(this,"Username has to be between 4 and 30 characters long",Toast.LENGTH_LONG).show();
        }
        //If password is invalid, a toast will show up
        else if(!isValidPasswordLength(checkViews[1].getText().toString())){
            Toast.makeText(this,"Password has to be between 4 and 30 characters long",Toast.LENGTH_LONG).show();
        }
        //If password between password and confirm password does not match, a toast will show up
        else if (!isPasswordEqual){
            Toast.makeText(this,"Passwords doesn't match",Toast.LENGTH_LONG).show();
        }
        //If all the fields are valid, Account will be created and an AlertDialog for successful account creation
        // confirmation will show up
        else {
            String username = checkViews[0].getText().toString();
            String password = checkViews[1].getText().toString();

            //medical history form intent
            Intent PageThreeToHistory = createIntentToHistory(username,password);

            long accountId = Container.getAccountManager().createAccount(PageThreeToHistory.getExtras(), this);

            AccountCreatedDialog(username,password,PageThreeToHistory, (int) accountId);

        }
    }

    /**
     * Check if the field is filled
     * @param views
     * @param n
     * @return boolean value, true if it is filled, false otherwise
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
     * Show the AlertDialog confirmation for successful account creation
     * @param username
     * @param password
     * @param intent
     * @param accountID
     */
    public void AccountCreatedDialog(final String username, final String password,final Intent intent,int accountID)
    {
        String message = "Congratulations! Your account has been successfully created.";
        String title = "Success";
        intent.putExtra("ID",accountID);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                goToMedicalHistoryForm(username, password, intent);

                //goToLoginPage(username,password,intent);
            }
        };
        AlertDialogInterface AlertDisplayer = new AlertDialogInterface(title,message,this);
        AlertDisplayer.AccountCreated(r);
    }

    /**
     * Go to MedicalHistory Activity while passing the data retrieved from SignUpPageOne, SignUpPageTwo,
     * and SignUpPageThree
     * @param username
     * @param password
     * @return {@code Intent} that includes all the extras from the first page of sign up, second page of sign up,
     * and the third page of sign up activities.
     */
    public Intent createIntentToHistory(String username,String password)
    {
        Intent PageThreeToHistory = new Intent(this, MedicalHistoryActivity.class);
        Bundle pageThree = new Bundle();
        pageThree.putString("USERNAME",username);
        pageThree.putString("PASSWORD",password);
        PageThreeToHistory.putExtra("PAGE_THREE",pageThree);
        PageThreeToHistory.putExtra("PAGE_TWO",getIntent().getBundleExtra("PAGE_TWO"));
        PageThreeToHistory.putExtra("PAGE_ONE",getIntent().getBundleExtra("PAGE_ONE"));
        return PageThreeToHistory;
    }

    /**
     * Validate username. Valid username will only contains alphanumeric characters
     * @param username
     * @return boolean value
     */
    public static boolean isValidUsernameChars(String username) {
        return (username.matches("[a-zA-Z0-9]*")); //only alphanumeric
    }

    /**
     * Validate username character length. Valid username length will be between 4 to 30 characters
     * @param username
     * @return boolean value
     */
    public static boolean isValidUsernameLength(String username) {
        return(username.length()<=30 && username.length()>=4); //4<length<30
    }

    /**
     * Validate password length. Valid password length will be between 4 to 30 characters
     * @param password
     * @return boolean value
     */
    public static boolean isValidPasswordLength(String password) {
        return(password.length()<=30 && password.length()>=4); //4<length<30
    }
}

