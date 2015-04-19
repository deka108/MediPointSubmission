package com.djzass.medipoint.entity;


import android.os.AsyncTask;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by Stefan on 3/29/2015.
 */

/**
 * Create a reminder for the user appointment through Email with javamail library help.
 * @author Stefan Artaputra Indriawan.
 * @version 1
 * @since 2015
 */


public class Email{

    // a string that is used to log on to the gmail account
    private static final String username = "djzass15@gmail.com";

    //a string that is used to log on to the gmail account
    private static final String password = "medipoint";

    /**
     *  This method will send an email to the registered user's email address when this function is called.
     * @param email {@link String} object which contain the user email adddress.
     * @param messageBody {@link String} object which contain the reminder message.
     * @exception javax.mail.internet.AddressException to avoid address error
     * @exception javax.mail.MessagingException to avoid message error
     * @exception java.io.UnsupportedEncodingException to avoid unsupported encoding error
     * @exception java.lang.NullPointerException to avoid null pointer error
     */
    public void sendMail(String email,String messageBody) {
        //create new session
        Session session = createSessionObject();

        //avoiding several exception
        try {
            Message message = createMessage(email, messageBody, session);
            new SendMailTask().execute(message);
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }

    }

    /**
     *  This method will create the email for reminding user.
     * @param email {@link String} object which contain the user email adddress.
     * @param messageBody {@link String} object which contain the reminder message.
     * @param  session {@link javax.mail.Session} session object from which the method is called.
     */
    private Message createMessage(String email, String messageBody, Session session) throws MessagingException, UnsupportedEncodingException {
        Message message = new MimeMessage(session);

        //set the email address to be sent, title and the body of the message.
        message.setFrom(new InternetAddress("MediPoint.com", "MediPoint"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        message.setSubject("Medipoint");
        message.setText(messageBody);
        return message;
    }

    /**
     *  This method will create the session object which is used to establish the network and log on to the sender email
     */
    private Session createSessionObject() {

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        return Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    /**
     * this method will work in background and publish the result without manipulating thread or handlers.
     * @see android.os.AsyncTask
     */
    public class SendMailTask extends AsyncTask<Message, Void, Void> {
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }

        @Override
        protected Void doInBackground(Message... messages) {
            try {
                Transport.send(messages[0]);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
