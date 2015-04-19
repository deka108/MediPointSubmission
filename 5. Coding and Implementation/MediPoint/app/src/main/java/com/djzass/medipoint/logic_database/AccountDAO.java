package com.djzass.medipoint.logic_database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.djzass.medipoint.entity.Account;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Deka on 26/3/2015.
 * AccountDAO is the data access object of the Account model. It provides access to an underlying
 * database for Account objects.
 *
 * @author Shreyas
 * @version 1.0
 * @since 2015
 */
public class AccountDAO extends DbDAO{
    /**
     * Store the SQL query for comparing account object id
     */
    private static final String WHERE_ID_EQUALS = DbContract.AccountEntry.COLUMN_NAME_ACCOUNT_ID + " =?";

    /**
     * Store the SQL query for verifying user which match the username and password from the database
     */
    private static final String SQL_VERIFY_USER =
            "SELECT " + DbContract.AccountEntry.COLUMN_NAME_USERNAME + "," +
                    DbContract.AccountEntry.COLUMN_NAME_PASSWORD +
                    " FROM " + DbContract.AccountEntry.TABLE_NAME +
                    " WHERE " + DbContract.AccountEntry.COLUMN_NAME_USERNAME + "=? AND " +
                    DbContract.AccountEntry.COLUMN_NAME_PASSWORD + "=?";
    /**
     * Store the SQL query for finding the email from given NRIC from the database
     */
    private static final String SQL_FIND_NRIC =
            "SELECT " + DbContract.AccountEntry.COLUMN_NAME_NRIC + "," +
                    DbContract.AccountEntry.COLUMN_NAME_EMAIL + "," +
                    DbContract.AccountEntry.COLUMN_NAME_PASSWORD +
                    " FROM " + DbContract.AccountEntry.TABLE_NAME +
                    " WHERE " + DbContract.AccountEntry.COLUMN_NAME_NRIC + "=?";
    /**
     * Store the SQL query for finding username from existing Account table in the database
     */
    private static final String SQL_FIND_USERNAME =
            "SELECT " + DbContract.AccountEntry.COLUMN_NAME_USERNAME +
                    " FROM " + DbContract.AccountEntry.TABLE_NAME +
                    " WHERE " + DbContract.AccountEntry.COLUMN_NAME_USERNAME + "=?";
    /**
     * Store the SQL query for finding account id from database
     */
    private static final String SQL_FIND_ACCOUNTID =
            "SELECT " + DbContract.AccountEntry.COLUMN_NAME_ACCOUNT_ID +
                    " FROM " + DbContract.AccountEntry.TABLE_NAME +
                    " WHERE " + DbContract.AccountEntry.COLUMN_NAME_USERNAME + "=?";
    /**
     * Store the SQL query for finding all information  about account based on account id
     */
    public static final String SQL_FIND_ACCOUNT_BY_ID =
            "SELECT * FROM "+ DbContract.AccountEntry.TABLE_NAME +
            " WHERE " + DbContract.AccountEntry.COLUMN_NAME_ACCOUNT_ID + "=?";

    /**
     * Constructor of AccountDAO with initialization
     * @param context
     * @throws SQLException
     */
    public AccountDAO(Context context) throws SQLException {
        super(context);
        initializeDAO();
    }

    /**
     * Inserting account into accounts table and return the account id if insertion successful,
     * otherwise -1 will be returned
     * @param account
     * @return id of the inserted account. If insertion failed, -1 will be returned.
     */
    public long insertAccount(Account account){
        ContentValues values = new ContentValues();
        values.put(DbContract.AccountEntry.COLUMN_NAME_NAME, account.getName());
        values.put(DbContract.AccountEntry.COLUMN_NAME_NRIC, account.getNric());
        values.put(DbContract.AccountEntry.COLUMN_NAME_EMAIL, account.getEmail());
        values.put(DbContract.AccountEntry.COLUMN_NAME_CONTACTNO, account.getPhoneNumber());
        values.put(DbContract.AccountEntry.COLUMN_NAME_ADDRESS, account.getAddress());
        values.put(DbContract.AccountEntry.COLUMN_NAME_DOB, account.getDob().getTimeInMillis());
        values.put(DbContract.AccountEntry.COLUMN_NAME_GENDER, account.getGender());
        values.put(DbContract.AccountEntry.COLUMN_NAME_MARITAL_STATUS, account.getMaritalStatus());
        values.put(DbContract.AccountEntry.COLUMN_NAME_CITIZENSHIP, account.getCitizenship());
        values.put(DbContract.AccountEntry.COLUMN_NAME_COUNTRY_OF_RESIDENCE, account.getCountryOfResidence());
        values.put(DbContract.AccountEntry.COLUMN_NAME_USERNAME, account.getUsername());
        values.put(DbContract.AccountEntry.COLUMN_NAME_PASSWORD, account.getPassword());
        values.put(DbContract.AccountEntry.COLUMN_NAME_NOTIFY_EMAIL, account.getNotifyEmail());
        values.put(DbContract.AccountEntry.COLUMN_NAME_NOTIFY_SMS, account.getNotifySMS());

        return database.insert(DbContract.AccountEntry.TABLE_NAME, null, values);
    }

    /**
     *  Get list of accounts from the database based on the whereclause
     *  @param whereclause the selection condition for the query
     *  @return list of account
     */
     public List<Account> getAccounts(String whereclause) {
        List<Account> accounts = new ArrayList<Account>();

        Cursor cursor = database.query(DbContract.AccountEntry.TABLE_NAME,
                new String[] {DbContract.AccountEntry.COLUMN_NAME_ACCOUNT_ID,
                             DbContract.AccountEntry.COLUMN_NAME_NAME,
                             DbContract.AccountEntry.COLUMN_NAME_NRIC,
                             DbContract.AccountEntry.COLUMN_NAME_EMAIL,
                             DbContract.AccountEntry.COLUMN_NAME_CONTACTNO,
                             DbContract.AccountEntry.COLUMN_NAME_ADDRESS,
                             DbContract.AccountEntry.COLUMN_NAME_DOB,
                             DbContract.AccountEntry.COLUMN_NAME_GENDER,
                             DbContract.AccountEntry.COLUMN_NAME_MARITAL_STATUS,
                             DbContract.AccountEntry.COLUMN_NAME_CITIZENSHIP,
                             DbContract.AccountEntry.COLUMN_NAME_COUNTRY_OF_RESIDENCE,
                             DbContract.AccountEntry.COLUMN_NAME_USERNAME,
                             DbContract.AccountEntry.COLUMN_NAME_PASSWORD,
                             DbContract.AccountEntry.COLUMN_NAME_NOTIFY_EMAIL,
                             DbContract.AccountEntry.COLUMN_NAME_NOTIFY_SMS
                }, whereclause, null, null, null,
                null);

        while (cursor.moveToNext()) {
            Account account = new Account();
            account.setId(cursor.getInt(0));
            account.setName(cursor.getString(1));
            account.setNric(cursor.getString(2));
            account.setEmail(cursor.getString(3));
            account.setPhoneNumber(cursor.getString(4));
            account.setAddress(cursor.getString(5));
            Calendar cal = Calendar.getInstance();
            Long c = cursor.getLong(6);
            cal.setTimeInMillis(c);
            account.setDob(cal);
            account.setGender(cursor.getString(7));
            account.setMaritalStatus(cursor.getString(8));
            account.setCitizenship(cursor.getString(9));
            account.setCountryOfResidence(cursor.getString(10));
            account.setUsername(cursor.getString(11));
            account.setPassword(cursor.getString(12));
            account.setNotifyEmail(cursor.getInt(13));
            account.setNotifySMS(cursor.getInt(14));
            accounts.add(account);
        }

        return accounts;
    }

    /**
     * Get all accounts from database
     * @return list of all accounts from the database
     */
    public List<Account> getAllAccounts() {
        return getAccounts(null);
    }
    /*
    public List<Account> getAccountById(int accountId) {
        String whereclause = DbContract.AccountEntry.COLUMN_NAME_ACCOUNT_ID + " = " + accountId;
        return getAccounts(whereclause);
    }
    */
    public List<Account> getAccountByNRIC(String nric) {
        String whereclause = DbContract.AccountEntry.COLUMN_NAME_NRIC + " = " + nric;
        return getAccounts(whereclause);
    }

    /**
     * Get all accounts from database based on citizenship
     * @param citizenship
     * @return list of all accounts from database based on Citizenship
     */
    public List<Account> getAccountByCitizenship(String citizenship) {
        String whereclause = DbContract.AccountEntry.COLUMN_NAME_CITIZENSHIP + " = " + citizenship;
        return getAccounts(whereclause);
    }

    /**
     * Get all accounts from database based on Username
     * @param username
     * @return list of accounts from database based on Username
     */
    public List<Account> getAccountByUsername(String username) {
        String whereclause = DbContract.AccountEntry.COLUMN_NAME_USERNAME + " = " + username;
        return getAccounts(whereclause);
    }

    /**
     * Get all accounts from database based on Email
     * @param email
     * @return list of accounts from database based on email
     */
    public List<Account> getAccountByEmail(String email) {
        String whereclause = DbContract.AccountEntry.COLUMN_NAME_EMAIL + " = " + email;
        return getAccounts(whereclause);
    }

    /**
     * Update the account table in database
     * @param account
     * @return the number of the rows that get updated. If update is failed, -1 is returned.
     */
    public long update(Account account) {
        ContentValues values = new ContentValues();
        values.put(DbContract.AccountEntry.COLUMN_NAME_NAME, account.getName());
        values.put(DbContract.AccountEntry.COLUMN_NAME_NRIC, account.getNric());
        values.put(DbContract.AccountEntry.COLUMN_NAME_EMAIL, account.getEmail());
        values.put(DbContract.AccountEntry.COLUMN_NAME_CONTACTNO, account.getPhoneNumber());
        values.put(DbContract.AccountEntry.COLUMN_NAME_ADDRESS, account.getAddress());
        values.put(DbContract.AccountEntry.COLUMN_NAME_DOB, account.getDob().getTimeInMillis());
        values.put(DbContract.AccountEntry.COLUMN_NAME_GENDER, account.getGender());
        values.put(DbContract.AccountEntry.COLUMN_NAME_MARITAL_STATUS, account.getMaritalStatus());
        values.put(DbContract.AccountEntry.COLUMN_NAME_CITIZENSHIP, account.getCitizenship());
        values.put(DbContract.AccountEntry.COLUMN_NAME_COUNTRY_OF_RESIDENCE, account.getCountryOfResidence());
        values.put(DbContract.AccountEntry.COLUMN_NAME_USERNAME, account.getUsername());
        values.put(DbContract.AccountEntry.COLUMN_NAME_PASSWORD, account.getPassword());
        values.put(DbContract.AccountEntry.COLUMN_NAME_NOTIFY_EMAIL, account.getNotifyEmail());
        values.put(DbContract.AccountEntry.COLUMN_NAME_NOTIFY_SMS, account.getNotifySMS());

        long result = database.update(DbContract.AccountEntry.TABLE_NAME, values,
                WHERE_ID_EQUALS,
                new String[] { String.valueOf(account.getId()) });

        return result;
    }


    /**
     * Delete rows from Account table in the database
     * @param account
     * @return the number of rows affected, 0 otherwise
     */
    public int deleteAccount(Account account) {
        return database.delete(DbContract.AccountEntry.TABLE_NAME,
                WHERE_ID_EQUALS, new String[] { account.getId() + "" });
    }

    /**
     * Get the number of tuples in account table
     * @return number of rows in account table
     */
    public int getAccountCount(){
        return getAllAccounts().size();
    }

    /**
     * Initialize the database with account upon AccountDAO creation
     *
     */
    private void initializeDAO(){
        if (getAccountCount()==0){
            insertAccount(new Account("test", "123", "Test", "A142049", "deka108@gmail.com", "82342891", "Female", "NTU", "Single", new GregorianCalendar(1995, 1, 1), "Singaporean", "Singapore", 1, 1));
        }
    }

    /**
     * @param username
     * @param password
     * @return Number of rows the cursor points to
     */
    public int onLogin(String username,String password){
        String[] selArgs = {username,password};
        Cursor userCursor = database.rawQuery(SQL_VERIFY_USER, selArgs);
        return userCursor.getCount();
    }

    /**
     * Get cursor for finding NRIC in the database
     * @param nric
     * @return cursor if the nric is found in database. Return null otherwise.
     */
    public Cursor checkAccount(String nric){
        String[] selArgs = {nric};
        return database.rawQuery(SQL_FIND_NRIC,selArgs);
    }

    /**
     *
     * @param username
     * @return number of usernames exist in database
     */
    public int checkUsername(String username){
        String[] selArgs = {username};
        return database.rawQuery(SQL_FIND_USERNAME,selArgs).getCount();
    }

    /**
     * Get cursor for finding username in the database
     * @param username
     * @return cursor if username is found in database. Return null otherwise.
     */
    public Cursor findAccountId(String username){
        String[] selArgs = {username};
        return database.rawQuery(SQL_FIND_ACCOUNTID,selArgs);
    }

    /**
     * Get cursor for finding Account from database which has the matching id
     * @param id
     * @return cursor if the Account with the matching id is found in database. Return null otherwise.
     */
    public Cursor getAccountById(long id){
        String[] selArgs = {""+id};
        return database.rawQuery(SQL_FIND_ACCOUNT_BY_ID,selArgs);
    }
}
