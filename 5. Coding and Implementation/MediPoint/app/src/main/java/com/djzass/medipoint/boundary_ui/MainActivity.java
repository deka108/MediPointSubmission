package com.djzass.medipoint.boundary_ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.djzass.medipoint.R;
import com.djzass.medipoint.logic_manager.*;
import com.google.samples.apps.iosched.ui.widget.SlidingTabLayout;

/**
 * This is the main screen of the app. After successful user actions login, appointment creation and
 * appointment deletion the app will be redirected here.
 *
 * <p>The main screen has two tabs, the first tab is to display the appointments and the second tab is for
 * displaying the medical history.</p>
 *
 * @author Deka
 * @since 2015
 * @version 1.0
 */
public class MainActivity extends FragmentActivity{

    /**
     * Called when the activity is starting. This is where most initialization done: calling
     * setContentView(int) to inflate the activity's UI, using findViewById(int) to programmatically
     * interact with widgets in the UI. The slideTab for both appointment list and medical history are
     * initialized here.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut
     *                           down then this Bundle contains the data it most recently supplied in
     *                           onSaveInstanceState(Bundle). Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(), MainActivity.this));

        // Give the SlidingTabLayout the ViewPager
        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);

        // Set custom tab layout
        slidingTabLayout.setCustomTabView(R.layout.custom_tab, 0);

        // Center the tabs in the layout
        slidingTabLayout.setDistributeEvenly(true);

        // Customize tab color
        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.theme_bg);
            }
        });
        slidingTabLayout.setViewPager(viewPager);
    }

    /**
     *  Go back to the home screen if back button is pressed to avoid going back to previous activities.
     */
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    /**
     *  Defining the content of each of the tabs and controls the order of the tabs, the titles and
     *  their associated content.
     *
     */
    public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
        /**
         * Store number of slides
         */
        final int PAGE_COUNT = 2;

        /**
         *  Store the tab titles
         */
        private String tabTitles[] = new String[]{"Appointment", "Medical History"};

        /**
         * Display the fragment slides into the MainActivity
         * @param fm Interface for interacting with Fragment objects inside of an Activity
         * @param mainActivity the homescreen
         *
         */
        public SampleFragmentPagerAdapter(android.support.v4.app.FragmentManager fm, MainActivity mainActivity) {
            super(fm);
        }

        /**
         * Get the number of slide tabs
         * @return number of slide tabs
         */
        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        /**
         * Determines the fragment for each tab
         *
         * @param position is the fragment position
         * @return the position of the fragment
         */
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return AppointmentListFragment.newInstance();
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return MedicalHistoryFragment.newInstance();
                /*case 2: // Fragment # 1 - This will show SecondFragment
                    return SecondFragment.newInstance(2, "Page # 3");*/
                default:
                    return null;
            }
        }

        /**
         * Get the slide title for each tab
         * @param position is the fragment position
         * @return slide title of the slide fragment
         */
        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }
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
        else if (id == R.id.action_logout) {
            SessionManager sessionManager = new SessionManager(this);
            sessionManager.deleteLoginSession();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}