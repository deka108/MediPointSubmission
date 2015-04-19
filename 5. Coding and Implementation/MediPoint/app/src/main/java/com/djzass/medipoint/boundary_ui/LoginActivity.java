package com.djzass.medipoint.boundary_ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.djzass.medipoint.R;
import com.djzass.medipoint.logic_manager.Container;
import com.djzass.medipoint.logic_manager.SessionManager;

/**
 * Activity class for login
 *
 * @author Shreyas
 * @since 2015
 * @version 1.0
 *
 * @see android.app.Activity
 */
public class LoginActivity extends Activity {
    /**
     * Button for login
     */
    Button loginButton;

    /**
     * Contains the SessionManager
     */
    private SessionManager sessionManager;

    /**
     * Called when the activity is starting. This is where most initialization done: calling
     * setContentView(int) to inflate the activity's UI, using findViewById(int) to programmatically
     * interact with widgets in the UI. The login button, session manager and user authentication are
     * all done here.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut
     *                           down then this Bundle contains the data it most recently supplied in
     *                           onSaveInstanceState(Bundle). Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Container.init();
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);
        Container.getAccountManager().updateAccountDao(this);

        loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                EditText usernameBox = (EditText) findViewById(R.id.enterUsernameTextbox);
                EditText passwordBox = (EditText) findViewById(R.id.enterPasswordTextbox);
                String username = usernameBox.getText().toString();
                String password = passwordBox.getText().toString();

                boolean isAuthenticated = Container.getAccountManager().authenticate(username, password);
                if(isAuthenticated==true){
                    sessionManager.createLoginSession(username,password);
                    loginSuccessful(username);
                    goToMain();
                }
                else{
                    wrongCredentials();
                }
            }
        });
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
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
     *  Go back to the home screen if back button is pressed to avoid going back to previous activities.
     */
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        System.exit(0);
    }

    /**
     * Start PasswordRetriever Activity
     */
    public void ForgotPassword()
    {
        Intent intent = new Intent(this,PasswordRetrieverActivity.class);
        startActivity(intent);
    }

    /**
     * Starts SignUp Activity
     */
    public void createSignUpForm()
    {
        Intent intent = new Intent(this,SignUpPageOneActivity.class);
        startActivity(intent);
    }

    /**
     * Shows error toast when the username or password entered is wrong
     */
    public void wrongCredentials(){
        Toast.makeText(this,"Wrong username or password",Toast.LENGTH_LONG).show();
    }

    /**
     * Show toast for showing login is successful
     * @param username
     */
    public void loginSuccessful(String username){
        Toast.makeText(this,"Welcome "+username+"!",Toast.LENGTH_LONG).show();

    }

    /**
     * Start the MainActivity
     */
    public void goToMain(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
