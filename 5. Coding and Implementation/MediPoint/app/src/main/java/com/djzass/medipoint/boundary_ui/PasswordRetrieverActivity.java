package com.djzass.medipoint.boundary_ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.djzass.medipoint.R;
import com.djzass.medipoint.entity.Email;
import com.djzass.medipoint.logic_manager.Container;

/**
 *
 * Activity class for retrieving password
 *
 * @author Shreyas
 * @version 1.0
 * @since 2015
 *
 * @see android.app.Activity
 */
public class PasswordRetrieverActivity extends Activity {
    /**
     *
     * Called when the activity is starting. This is where most initialization done: calling
     * setContentView(int) to inflate the activity's UI, using findViewById(int) to programmatically
     * interact with widgets in the UI. The confirmation button for sending password to nric is
     * initialized here.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut
     *                           down then this Bundle contains the data it most recently supplied in
     *                           onSaveInstanceState(Bundle). Otherwise it is null.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_retriever);

        //AlertDialog asking to confirm the correctness of the NRIC keyed in
        Button confirmButton = (Button) findViewById(R.id.confirmButton);
        Container.getAccountManager().updateAccountDao(this);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nricTextbox = (EditText) findViewById(R.id.nricTextbox);
                Cursor cursor = Container.getAccountManager().findAccount(nricTextbox.getText().toString());
                if (cursor == null) {
                    AccountNotExist();
                }
                else {
                    emailPassword(cursor);
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
        getMenuInflater().inflate(R.menu.menu_password_retriever, menu);
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
     * Show toast if there is no user with NRIC that is keyed in into the password retriever form
     */
    public void AccountNotExist() {
        Toast.makeText(this, "You do not have any existing account", Toast.LENGTH_LONG).show();
    }

    /**
     * Sends email to the user
     * @param cursor points to the tuple which contains the NRIC keyed in into the password retriever form
     */
    public void emailPassword(Cursor cursor){
        cursor.moveToFirst();

        //Retrieving email and password
        String email = cursor.getString(1);
        String password = cursor.getString(2);

        //Sending email to the user
        String body = "Dear Sir/Madam,\n The password of your account is '" + password + "'.\n Thank You!";
        Email emailSender = new Email();
        emailSender.sendMail(email,body);
        Toast.makeText(this,"Email sent",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

}
