package com.djzass.medipoint.entity;


import android.app.PendingIntent;

import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;


import java.util.ArrayList;

/**
 * Created by Stefan on 3/30/2015.
 */

/**
 * Create a reminder for the user appointment through SMS provider.
 * @author Stefan Artaputra Indriawan
 * @version 1.
 * @since 2015.
 */
public class SMS {

    //a string that is used as pending intent later
    private static final String SMS_SENT="Message Sent";

    //maximum length of a message
    private static final int MAX_SMS_MESSAGE_LENGTH =160;

    //port for received data SMS
    private static final short SMS_PORT=8901;

    //a string that is used as pending intent later
    private static final String SMS_DELIVERED ="Message Delivered";

    /**
     *  This method will send a SMS to the user's phone number when this function is called.
     *  @param context {@link Context} object from which this method is called.
     * @param  phonenumber {@link String} for sending SMS to the designated number.
     * @param message {@link String} the message to be sent to the user.
     * @param isBinary {@link java.lang.Boolean} check whether the message is in binary or not.
     */
    public void sendSms(Context context,String phonenumber,String message, boolean isBinary)
    {
        SmsManager manager = SmsManager.getDefault();

        //pending Intent for later use
        PendingIntent piSend = PendingIntent.getBroadcast(context, 0, new Intent(SMS_SENT), 0);
        PendingIntent piDelivered = PendingIntent.getBroadcast(context, 0, new Intent(SMS_DELIVERED), 0);

        //if it is binary
        if(isBinary)
        {
            byte[] data = new byte[message.length()];

            for(int index=0; index<message.length() && index < MAX_SMS_MESSAGE_LENGTH; ++index)
            {
                data[index] = (byte)message.charAt(index);
            }

            manager.sendDataMessage(phonenumber, null, (short) SMS_PORT, data,piSend, piDelivered);
        }
        else
        {
            int length = message.length();

            // if the message exceed 160 characters
            if(length > MAX_SMS_MESSAGE_LENGTH)
            {

                ArrayList<String> messagelist = manager.divideMessage(message);

                manager.sendMultipartTextMessage(phonenumber, null, messagelist, null, null);
            }
            else
            {
                manager.sendTextMessage(phonenumber, null, message, piSend, piDelivered);
            }
        }
    }
}


