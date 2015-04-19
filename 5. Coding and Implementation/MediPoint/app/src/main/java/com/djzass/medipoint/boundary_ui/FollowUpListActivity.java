package com.djzass.medipoint.boundary_ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.djzass.medipoint.R;
import com.djzass.medipoint.entity.Appointment;
import com.djzass.medipoint.logic_manager.Container;
import com.djzass.medipoint.logic_manager.SessionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * This activity display the list of recent appointments of the patient within 30 days.
 *
 * @author Deka
 * @version 1.0
 * @since 2015
 *
 * @see android.app.Activity
 */
public class FollowUpListActivity extends Activity {
    /**
     * Stores the id of the patient
     */
    private int patientId;

    /**
     * Called when the activity is starting. This is where most initialization done: calling
     * setContentView(int) to inflate the activity's UI, using findViewById(int) to programmatically
     * interact with widgets in the UI, retrieving patientId, and most recent appointment List.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut
     *                           down then this Bundle contains the data it most recently supplied in
     *                           onSaveInstanceState(Bundle). Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_up_list);

        SessionManager sessionManager = new SessionManager(this);
        try {
            this.patientId = (int)sessionManager.getAccountId();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrayList<Appointment> appointments = (ArrayList<Appointment>) Container.getAppointmentManager().sortByDate(Container.getAppointmentManager().getPatientRecentAppointments(this.patientId, Calendar.getInstance(), this));

        TextView tv = (TextView)findViewById(R.id.noPastAppoinment);
        if (appointments.size() > 0) {
            ListView apptList = (ListView)findViewById(R.id.followuplist);
            FollowUpAdapter apptAdapter = null;
            tv.setVisibility(View.GONE);
            try {
                apptAdapter = new FollowUpAdapter(this, appointments);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            apptList.setAdapter(apptAdapter);
            apptList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView <?> parent, View view, int position, long id) {
                    Appointment app = (Appointment) parent.getAdapter().getItem(position);
                    Intent in = new Intent(getApplicationContext(), CreateFollowUpActivity.class);
                    in.putExtra("appFollowUp", app);
                    startActivity(in);
                }
            });
        }
        else {
            tv.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Initialize the contents of the Activity's standard options menu
     *
     * @param menu the options menu in which the items are placed
     * @return true for the menu to be displayed; if false is returned, the items will not be shown.
     *
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        //logout menu item selected
        else if(id==R.id.action_logout){
            SessionManager sessionManager = new SessionManager(this);
            sessionManager.deleteLoginSession();
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
