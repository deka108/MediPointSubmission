package com.djzass.medipoint.boundary_ui;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.djzass.medipoint.R;
import com.djzass.medipoint.entity.Appointment;
import com.djzass.medipoint.logic_manager.Container;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Deka on 30/3/2015.
 *
 * Appointment Adapter is a custom adapter class for displaying appointment item in the appointment
 * list.  This class extends from ArrayAdapter class and accepts {@link Appointment} objects.
 * Each of the Appointment objects that use this adapter will be displayed as individual item in
 * {@link AppointmentListFragment}.
 *
 * <p>Each item in the appointment list will include:
 * <ul>
 *     <li>Specialty Icon</li>
 *     <li>Appointment Service</li>
 *     <li>Appointment Status: Upcoming, Ongoing, Finished</li>
 *     <li>Appointment Date</li>
 *     <li>Appointment Time</li>
 * </ul>
 *</p>
 *
 * @author Deka
 * @version 1.0
 * @since 2015
 *
 * @see android.widget.ArrayAdapter
 */
public class AppointmentAdapter extends ArrayAdapter<Appointment> {
    /**
     * Constructor for ViewHolder class.  This class is used to optimise ListView in android as it makes
     * the data loads faster.
     */
    private static class ViewHolder {
        /**
         * Icon represents the appointment specialty
         */
        public ImageView specialtyIcon;

        /**
         * Display the service of the appointment
         */
        public TextView appointmentService;

        /**
         *  Display the current status of the appointment: Upcoming, Ongoing and Finished
         */
        public TextView appointmentStatus;

        /**
         *  Display the date of the appointment
         */
        public TextView appointmentDate;

        /**
         * Display the time of the appointment
         */
        public TextView appointmentTime;
    }

    /**
     *  Sets up the layout for each appointment item in {@link AppointmentListFragment}
     *
     * @param context is the current state of the application
     * @param appointments contains the list of Appointment objects owned by the patient
     * @throws SQLException
     */
    public AppointmentAdapter(Context context, ArrayList<Appointment> appointments) throws SQLException {
        super(context, R.layout.appointment_adapter, appointments);
    }

    /**
     * Get a View that displays the data at the specified position in the data set.
     *
     * @param position  is the position of the item within the adapter's data set of items which view is requested
     * @param convertView for using the old view, if possible. The view is first checked, if this view is non-null
     *                    and of an appropriate type before using. If it is not possible to convert this view to
     *                    display the correct data, this method can be used to create a new view
     * @param parent the parent that this view will eventually be attached to
     * @return View corresponding to the data at the specified position.
     *
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        // Get the data item for this position
        Appointment appointment = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.appointment_adapter, parent, false);
            viewHolder = new ViewHolder();
            // Lookup view for data population
            viewHolder.specialtyIcon = (ImageView) convertView.findViewById(R.id.specialty_icon);
            viewHolder.appointmentService = (TextView) convertView.findViewById(R.id.appointment_service);
            viewHolder.appointmentStatus = (TextView) convertView.findViewById(R.id.appointment_status);
            viewHolder.appointmentDate = (TextView) convertView.findViewById(R.id.appointment_date);
            viewHolder.appointmentTime= (TextView) convertView.findViewById(R.id.appointment_time);
            convertView.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        HashMap<String,String> appointmentDetails = getAppointmentDetails(appointment.getId());

        // Populate the data into the template view using the data object
        viewHolder.specialtyIcon.setImageResource(getImageId(appointmentDetails.get("SPECIALTY_NAME")));
        viewHolder.appointmentService.setText(appointmentDetails.get("SERVICE_NAME"));
        viewHolder.appointmentStatus.setText(appointmentDetails.get("STATUS"));
        viewHolder.appointmentDate.setText(appointmentDetails.get("DATE"));
        viewHolder.appointmentTime.setText(appointmentDetails.get("TIME"));

        // Return the completed view to render on screen
        return convertView;
    }

    /**
     * Get the Image Resource id of the specialty
     *
     * @param specialtyName is the name of the specialty
     * @return the Icon of the corresponding specialty
     */
    public int getImageId(String specialtyName){
        if (specialtyName.equalsIgnoreCase("ENT"))
            return R.mipmap.ear;
        else if (specialtyName.equalsIgnoreCase("Dental Services"))
            return R.mipmap.dental;
        else if (specialtyName.equalsIgnoreCase("Women's Health"))
            return R.mipmap.female;
        return R.mipmap.icontp_medipoint;
    }

    /**
     * Gets the appointment details information in form of key value pairs of strings
     *
     * @param id of the appointment
     * @return the key value pair appointment details and its Text value
     *
     */
    public HashMap<String,String> getAppointmentDetails(int id){
        Appointment appointment = Container.getAppointmentManager().getAppointmentByID(id, getContext());
        HashMap<String, String> appointmentDetails = new HashMap<String, String>();
        appointmentDetails.put("SPECIALTY_NAME",Container.getSpecialtyManager().getSpecialtyNameBySpecialtyId(appointment.getSpecialtyId(),getContext()));
        appointmentDetails.put("SERVICE_NAME",Container.getServiceManager().getServiceNameByServiceID(appointment.getServiceId(), getContext()));
        appointmentDetails.put("DOCTOR_NAME",Container.getDoctorManager().getDoctorNameByDoctorId(appointment.getDoctorId(), getContext()));
        appointmentDetails.put("CLINIC_NAME",Container.getClinicManager().getClinicNameByClinicId(appointment.getClinicId(), getContext()));
        appointmentDetails.put("DATE",appointment.getDateString());
        appointmentDetails.put("TIME",appointment.getTimeString());
        appointmentDetails.put("STATUS",Container.getAppointmentManager().getStatus(appointment));

        return appointmentDetails;
    }
}
