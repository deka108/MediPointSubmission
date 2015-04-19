package com.djzass.medipoint.logic_manager;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import com.djzass.medipoint.entity.Account;
import com.djzass.medipoint.logic_database.AccountDAO;

import java.sql.SQLException;
import java.util.Calendar;

/**
 * Manage the accounts
 *
 * @author Shreyas
 * @version 1.0
 * @since 2015
 */
public class AccountManager {
    
    /**
     * An instance of {@link AccountDAO}. This is to be re-instantiated with context before use.
     */
    private AccountDAO accountDao;
    private static AccountManager instance = new AccountManager();

    /**
     * Returns AccountManager instance
     * @return AccountManager instance
     */
    public static AccountManager getInstance() {
        return instance;
    }

    /**
     * Re-initializes the AccountDAO with the given context
     * @param context current state of the Application
     */
    public void updateAccountDao(Context context){
        try {
            accountDao = new AccountDAO(context);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create new Account by inserting Account object into Account table in the database
     * @param AccountDetails
     * @param context Current state of the Application
     * @return
     */
    public long createAccount(Bundle AccountDetails,Context context){
        updateAccountDao(context);
        Account newAccount = extractAccountDetails(AccountDetails);
        long ret = accountDao.insertAccount(newAccount);
        return ret;
    }

    /**
     * Update the account table with the AccountDetails information
     * @param AccountDetails
     * @param context Current state of the Application
     * @return
     */
    public long updateAccount(Bundle AccountDetails,Context context){
        updateAccountDao(context);
        Account newAccount = extractAccountDetails(AccountDetails);
        long ret = accountDao.update(newAccount);
        return ret;
    }

    /**
     * Check if NRIC already exist in database
     * @param nric
     * @param context Current state of the Application
     * @return boolean value, True if NRIC does not exist in database, False otherwise.
     */
    public boolean isNewAccount(String nric, Context context){
        Cursor cursor = findAccount(nric,context);
        if(cursor==null)
            return true;
        else
            return false;
    }

    /**
     * Check if username already exist in database
     * @param username
     * @param context
     * @return boolean value, true if username already exist in database, false otherwise.
     */
    public boolean doesUsernameExist(String username, Context context){
        updateAccountDao(context);
        return accountDao.checkUsername(username)>0? true:false;
    }

    /**
     * Extract the account details from sign up activities
     * @param AccountDetails
     * @return new Account object with attributes retrieved from the sign up activities
     */
    public Account extractAccountDetails(Bundle AccountDetails){
        Bundle PageOne = AccountDetails.getBundle("PAGE_ONE");
        Bundle PageTwo = AccountDetails.getBundle("PAGE_TWO");
        Bundle PageThree = AccountDetails.getBundle("PAGE_THREE");

        String name = PageOne.getString("NAME");
        String nric = PageOne.getString("NRIC");
        String email = PageOne.getString("EMAIL");
        String contact = PageOne.getString("CONTACT");
        String address = PageOne.getString("ADDRESS");

        String gender = PageTwo.getString("GENDER");
        String maritalStatus = PageTwo.getString("MARITAL_STATUS");
        String citizenship = PageTwo.getString("CITIZENSHIP");
        String countryOfResidence = PageTwo.getString("COUNTRY_OF_RESIDENCE");
        long dob = PageTwo.getLong("DOB");
        int notify_email = PageTwo.getInt("NOTIFY_EMAIL");
        int notify_sms = PageTwo.getInt("NOTIFY_SMS");
        Calendar dobCal = Calendar.getInstance();
        dobCal.setTimeInMillis(dob);

        String username = PageThree.getString("USERNAME");
        String password = PageThree.getString("PASSWORD");
        Account newAccount = new Account(username,password,name,nric,email,contact,gender,address,maritalStatus,dobCal,citizenship,countryOfResidence,notify_email,notify_sms);
        return newAccount;
    }

    /**
     * Returns the Account object based on the id
     * @param id of the Appointment
     * @param context Current state of the Application
     * @return Account object with the matching id
     */
    public Account getAccountById(long id, Context context){
        updateAccountDao(context);
        Cursor cursor = accountDao.getAccountById(id);
        cursor.moveToFirst();

        String name = cursor.getString(1);
        String nric = cursor.getString(2);
        String email = cursor.getString(3);
        String contact = cursor.getString(4);
        String address = cursor.getString(5);
        Calendar dobCal = Calendar.getInstance();
        Long dob = cursor.getLong(6);
        dobCal.setTimeInMillis(dob);
        String gender = cursor.getString(7);
        String maritalStatus = cursor.getString(8);
        String citizenship = cursor.getString(9);
        String countryOfResidence = cursor.getString(10);
        String username = cursor.getString(11);
        String password = cursor.getString(12);
        int isEmail = cursor.getInt(13);
        int isSMS = cursor.getInt(14);

        return new Account(id,username,password,name,nric,email,contact,gender,address,maritalStatus,dobCal,citizenship,countryOfResidence,isEmail,isSMS);
    }

    /**
     * Get the cursor pointing to the Account tuple with the same NRIC
     *
     * @param nric
     * @param context Current state of the Application
     * @return cursor pointing to the tuple that contains NRIC
     */
    public Cursor findAccount(String nric, Context context){
        updateAccountDao(context);
        Cursor cursor = accountDao.checkAccount(nric);
        return cursor.getCount()>0? cursor:null;
    }

    /**
     * Authenticate the username and password during login
     *
     * @param username
     * @param password
     * @param context Current state of the Application
     * @return boolean value, true if Authentication is successful, and false otherwise.
     */
    public boolean authenticate(String username,String password, Context context){
        updateAccountDao(context);
        int numUsers = accountDao.onLogin(username,password);
        return numUsers>0? true:false;
    }

    /**
     * Get the cursor pointing to the Account with the same nric
     * @param nric
     * @return the cursor if the Account with the same nric exist, null otherwise.
     */
    public Cursor findAccount(String nric){
        Cursor cursor = accountDao.checkAccount(nric);
        return cursor.getCount()>0? cursor:null;
    }

    /**
     * Authenticate the username and password during login
     *
     * @param username
     * @param password
     * @return boolean value, true if Authentication is successful, and false otherwise.
     */
    public boolean authenticate(String username,String password){
        int numUsers = accountDao.onLogin(username,password);
        return numUsers>0? true:false;
    }

    /**
     * Return the number of username with the same username in the database
     * @param username
     * @param context Current state of the Application
     * @return number of username exist in the database
     *
     */
    public int checkUsername(String username, Context context){
        updateAccountDao(context);
        return accountDao.checkUsername(username);
    }

    /**
     * Return cursor pointing to the Account with the same username
     * @param username
     * @param context Current state of the Application
     * @return cursor pointing to the Account with the same username
     */
    public Cursor findAccountId(String username, Context context){
        updateAccountDao(context);
        return accountDao.findAccountId(username);
    }

    /**
     * Return cursor pointing to the Account tuple with the same id
     * @param id
     * @param context
     * @return cursor pointing to the Account with the same id
     */
    public Cursor getAccountCursorById(long id, Context context){
        updateAccountDao(context);
        return accountDao.getAccountById(id);
    }
}

