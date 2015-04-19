package com.djzass.medipoint.logic_manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;

import com.djzass.medipoint.logic_database.AccountDAO;

import java.sql.SQLException;

/**
 *
 * Manage the sessions
 * Created by Shreyas on 3/24/2015.
 *
 * This class
 * @author Shreyas
 * @version 1.0
 * @since 2015
 */
public class SessionManager {

    /**
     * Contains the SharedPreferences object
     */
    SharedPreferences pref;

    /**
     * Contains the Editor for SharedPreferences
     */
    SharedPreferences.Editor editor;

    /**
     * Contains the context
     */
    Context _context;

    /**
     * Store the SharedPreferences mode
     */
    int PRIVATE_MODE = 0;

    /**
     * Store the SharedPreference file name
     */
    public static final String PREF_NAME = "UserSession";

    /**
     * Store the SharedPreferences is login
     */
    private static final String IS_LOGIN = "IsLoggedIn";

    /**
     * Store the SharedPreferences username keys
     */
    public static final String KEY_USERNAME = "username";

    /**
     * Store the SharedPreference password keys
     */
    public static final String KEY_PASSWORD = "password";

    /**
     * Constructor for SessionManager which initialize the context, the SharedPreferences name,
     * SharedPreferences mode, and SharedPreference editor
     * @param context Current state of the Application
     */
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Set the session for the current user, store the username and password and save the changes
     * @param username of the user that is logged in
     * @param password of the user that is logged in
     */
    public void createLoginSession(String username, String password){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_USERNAME, username);

        // Storing email in pref
        editor.putString(KEY_PASSWORD, password);

        // commit changes
        editor.commit();
    }

    /**
     * Deleting current login session and save it
     */
    public void deleteLoginSession(){
        editor.clear();
        editor.commit();
    }

    /**
     * Get the account id of the current user that is logged in
     * @return the id of the current user
     * @throws SQLException
     */
    public long getAccountId()throws SQLException{
        AccountDAO accountDAO = new AccountDAO(_context);
        String username = pref.getString(KEY_USERNAME,"");
        Cursor cursor = accountDAO.findAccountId(username);
        if(cursor!=null && cursor.moveToFirst())
            return cursor.getInt(0);
        return -1;
    }
}
