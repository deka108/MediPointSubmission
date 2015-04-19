package com.djzass.medipoint.logic_database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import java.sql.SQLException;

/**
 * Created by Deka on 26/3/2015.
 *
 * @author Joshua
 * @since 2015
 * @version 1.0
 */

public class DbDAO {
    /**
     * An instance of the database
     */
    protected SQLiteDatabase database;
    /**
     * An instance of {@link DbHelper}, which contains all the pre-prepared instructions.
     */
    private DbHelper dbHelper;
    /**
     * An instance of Context to be used whenever required
     */
    private Context mContext;

    /**
     * Constructor
     * @param context Interface to global information about an application environment
     * @throws SQLException throw an SQLException
     */
    public DbDAO(Context context) throws SQLException {
        this.mContext = context;
        dbHelper = DbHelper.getHelper(mContext);
        open();
    }

    /**
     * Opens database for writing
     * @throws SQLException throw an SQLException
     */
    public void open() throws SQLException {
        if(dbHelper == null)
            dbHelper = DbHelper.getHelper(mContext);
        database = dbHelper.getWritableDatabase();
    }

    /**
     * Closes database
     * @throws SQLException throw an SQLException
     */
    public void close() {
        dbHelper.close();
        database = null;
    }
}
