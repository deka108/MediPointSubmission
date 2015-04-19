package com.djzass.medipoint.boundary_ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import java.util.List;

/**
 * Created by Deka on 6/4/2015.
 *
 * This DialogFragment displays the available time slot for the patient to select the preferred time for
 * Appointment.
 *
 * @author Deka
 * @version 1.0
 * @since 2015
 */
public class TimePickerFragment extends DialogFragment{
    /**
     * Stores the data tag
     */
    public static final String DATA = "items";
    /**
     * Stores the selected tag
     */
    public static final String SELECTED = "selected";
    /**
     *  Observe when item from fragment is selected
     */
    private SelectionListener listener;

    /**
     * Called when a fragment is first attached to its activity. onCreate(Bundle) will be called after this.
     * @param activity the activity which the dialog fragment is attached to
     */
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        try{
            this.listener = (SelectionListener)activity;
        }
        catch ( ClassCastException e ){
            e.printStackTrace();
        }
    }

    /**
     * Build custom Dialog container, it simply instantiates and returns a Dialog class.
     *
     * @param savedInstanceState The last saved instance state of the Fragment, or null if this is a freshly created Fragment
     * @return new Dialog instance to be displayed by the Fragment.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        //Get data from activity
        Bundle bundle = getArguments();

        //Create new AlertDialog
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

        //Set the dialog title and cancel button
        dialog.setTitle("Please Select");
        dialog.setPositiveButton("Cancel", new PositiveButtonClickListener());

        //Set the data from the bundle
        List<String> list = (List<String>)bundle.get(DATA);

        //Get the position of the selected item
        int position = bundle.getInt(SELECTED);

        //Set the data from into listView
        CharSequence[] cs = list.toArray(new CharSequence[list.size()]);
        dialog.setSingleChoiceItems(cs, position, selectItemListener);

        return dialog.create();
    }

    /**
     *
     */
    class PositiveButtonClickListener implements DialogInterface.OnClickListener
    {
        /**
         * Dismiss the dialog box when cancel button is selected
         *
         * @param dialog the dialog fragment
         * @param which the item position in the dialog fragment
         */
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            dialog.dismiss();
        }
    }

    /**
     *
     */
    DialogInterface.OnClickListener selectItemListener = new DialogInterface.OnClickListener()
    {
        /**
         * Pass the selected item to the listener
         *
         * @param dialog the dialog fragment
         * @param which the item position in dialog fragment
         */
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            if ( listener != null ){
                listener.selectItem(which);
            }
            dialog.dismiss();
        }
    };
}

