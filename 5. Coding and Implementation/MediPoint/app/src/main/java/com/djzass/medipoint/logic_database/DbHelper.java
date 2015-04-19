package com.djzass.medipoint.logic_database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Shreyas on 3/17/2015.
 * @author Shreyas
 * @version 1.0
 * @since 2015
 *
 * This class contains the database schema of all the tables
 */
public class DbHelper extends SQLiteOpenHelper {
    /**
     * Attribute type TEXT
     */
    private static final String TEXT_TYPE = " TEXT";
    /**
     * Attribute type CHAR with 8 characters
     */
    private static final String CHAR_EIGHT_TYPE = " CHAR(8)";
    /**
     * Attribute type CHAR with 10 characters
     */
    private static final String CHAR_TEN_TYPE = " CHAR(10)";
    /**
     * Attribute type VARCHAR of 8 characters
     */
    private static final String VARCHAR_TEN_TYPE = " VARCHAR(10)";
    /**
     * Attribute type VARCHAR with 30 characters
     */
    private static final String VARCHAR_THIRTY_TYPE = " VARCHAR(30)";
    /**
     * Attribute type VARCHAR with 50 characters
     */
    private static final String VARCHAR_FIFTY_TYPE = " VARCHAR(50)";
    /**
     * Attribute type INTEGER
     */
    private static final String INT_TYPE = " INTEGER";
    /**
     * Attribute type INTEGER, AUTOINCREMENT and is PRIMARY KEY
     */
    private static final String INT_KEY_TYPE = " INTEGER PRIMARY KEY AUTOINCREMENT";
    //private static final String DATETIME_TYPE = " DATETIME";
    /**
     * Attribute type LONG
     */
    private static final String DATETIME_TYPE = " LONG";
    /**
     * Attribute type PRIMARYKEY
     */
    private static final String PRIMARY_KEY = " PRIMARY KEY";
    /**
     * Attribute type FOREIGNKEY
     */
    private static final String FOREIGN_KEY = " FOREIGN KEY(";
    /**
     * SQL statement REFERENCES
     */
    private static final String REFERENCES = ") REFERENCES ";
    /**
     * SQL statement comma separator ';'
     */
    private static final String COMMA_SEP = ",";
    /**
     * Contains the latest database version. If the database schema is changed,
     * the database version must be incremented.
     */
    private static final int DATABASE_VERSION = 15;
    /**
     * Contains the database name
     */
    private static final String DATABASE_NAME = "MediPoint.db";

    /* ACCOUNT TABLE*/
    /**
     * Contains SQL statement for creating Account table
     */
    private static final String SQL_CREATE_ACCOUNT =
            "CREATE TABLE " + DbContract.AccountEntry.TABLE_NAME + " (" +
                    DbContract.AccountEntry.COLUMN_NAME_ACCOUNT_ID + INT_KEY_TYPE /*+ PRIMARY_KEY*/ + COMMA_SEP +
                    DbContract.AccountEntry.COLUMN_NAME_NAME + VARCHAR_FIFTY_TYPE + COMMA_SEP +
                    DbContract.AccountEntry.COLUMN_NAME_NRIC + CHAR_TEN_TYPE + COMMA_SEP +
                    DbContract.AccountEntry.COLUMN_NAME_EMAIL + VARCHAR_THIRTY_TYPE + COMMA_SEP +
                    DbContract.AccountEntry.COLUMN_NAME_CONTACTNO + VARCHAR_TEN_TYPE + COMMA_SEP +
                    DbContract.AccountEntry.COLUMN_NAME_ADDRESS + VARCHAR_FIFTY_TYPE + COMMA_SEP +
                    DbContract.AccountEntry.COLUMN_NAME_DOB + DATETIME_TYPE + COMMA_SEP +
                    DbContract.AccountEntry.COLUMN_NAME_GENDER + " CHAR(1)" + COMMA_SEP +
                    DbContract.AccountEntry.COLUMN_NAME_MARITAL_STATUS + VARCHAR_TEN_TYPE + COMMA_SEP +
                    DbContract.AccountEntry.COLUMN_NAME_CITIZENSHIP + VARCHAR_THIRTY_TYPE + COMMA_SEP +
                    DbContract.AccountEntry.COLUMN_NAME_COUNTRY_OF_RESIDENCE + VARCHAR_THIRTY_TYPE + COMMA_SEP +
                    DbContract.AccountEntry.COLUMN_NAME_USERNAME + VARCHAR_THIRTY_TYPE + COMMA_SEP +
                    DbContract.AccountEntry.COLUMN_NAME_PASSWORD + VARCHAR_THIRTY_TYPE + COMMA_SEP +
                    DbContract.AccountEntry.COLUMN_NAME_NOTIFY_EMAIL + INT_TYPE + COMMA_SEP +
                    DbContract.AccountEntry.COLUMN_NAME_NOTIFY_SMS + INT_TYPE +
                    " );";
    /**
     * Contains SQL statement for deleting Account table
     */
    private static final String SQL_DELETE_ACCOUNT =
            "DROP TABLE IF EXISTS " + DbContract.AccountEntry.TABLE_NAME + ";";


    /* APPOINTMENT TABLE */
    /**
     * Contains SQL statement for creating Appointment table
     */
    private static final String SQL_CREATE_APPOINTMENT = "CREATE TABLE " + DbContract.AppointmentEntry.TABLE_NAME + " (" +
            DbContract.AppointmentEntry.COLUMN_NAME_APPOINTMENT_ID + INT_KEY_TYPE /*+ PRIMARY_KEY*/ + COMMA_SEP +
            DbContract.AppointmentEntry.COLUMN_NAME_CLINIC_ID + INT_TYPE + COMMA_SEP +
            DbContract.AppointmentEntry.COLUMN_NAME_PATIENT_ID + INT_TYPE + COMMA_SEP +
            DbContract.AppointmentEntry.COLUMN_NAME_DOCTOR_ID + INT_TYPE + COMMA_SEP +
            DbContract.AppointmentEntry.COLUMN_NAME_REFERRER_ID + INT_TYPE + COMMA_SEP +
            DbContract.AppointmentEntry.COLUMN_NAME_DATE_TIME + DATETIME_TYPE + COMMA_SEP +
            DbContract.AppointmentEntry.COLUMN_NAME_SERVICE_ID + INT_TYPE + COMMA_SEP +
            DbContract.AppointmentEntry.COLUMN_NAME_SPECIALTY_ID + INT_TYPE + COMMA_SEP +
            DbContract.AppointmentEntry.COLUMN_NAME_PREAPPOINTMENT_ACTIONS + TEXT_TYPE + COMMA_SEP +
            DbContract.AppointmentEntry.COLUMN_NAME_START_TIME + INT_TYPE + COMMA_SEP +
            DbContract.AppointmentEntry.COLUMN_NAME_END_TIME + INT_TYPE + COMMA_SEP +
            FOREIGN_KEY + DbContract.AppointmentEntry.COLUMN_NAME_CLINIC_ID + REFERENCES + DbContract.ClinicEntry.TABLE_NAME +
            "(" + DbContract.ClinicEntry.COLUMN_NAME_CLINIC_ID + ")" + COMMA_SEP +
            FOREIGN_KEY + DbContract.AppointmentEntry.COLUMN_NAME_PATIENT_ID + REFERENCES + DbContract.PatientEntry.TABLE_NAME +
            "(" + DbContract.PatientEntry.COLUMN_NAME_PATIENT_ID + ")" + COMMA_SEP +
            FOREIGN_KEY + DbContract.AppointmentEntry.COLUMN_NAME_DOCTOR_ID + REFERENCES + DbContract.DoctorEntry.TABLE_NAME +
            "(" + DbContract.DoctorEntry.COLUMN_NAME_DOCTOR_ID + ")" + COMMA_SEP +
            FOREIGN_KEY + DbContract.AppointmentEntry.COLUMN_NAME_SERVICE_ID + REFERENCES + DbContract.ServiceEntry.TABLE_NAME +
            "(" + DbContract.ServiceEntry.COLUMN_NAME_SERVICE_ID + ")" + " );";
    /**
     * Contains SQL statement for deleting Appointment table
     */
    private static final String SQL_DELETE_APPOINTMENT =
            "DROP TABLE IF EXISTS " + DbContract.AppointmentEntry.TABLE_NAME + ";";

    /*DOCTOR TABLE*/
    /**
     * Contains SQL statement for creating Doctor table
     */
    private static final String SQL_CREATE_DOCTOR = "CREATE TABLE " + DbContract.DoctorEntry.TABLE_NAME + " (" +
            DbContract.DoctorEntry.COLUMN_NAME_DOCTOR_ID + INT_KEY_TYPE /*+ PRIMARY_KEY*/ + COMMA_SEP +
            //DbContract.DoctorEntry.COLUMN_NAME_DOCTOR_ID + INT_TYPE + PRIMARY_KEY + COMMA_SEP +
            DbContract.DoctorEntry.COLUMN_NAME_DOCTOR_NAME + VARCHAR_THIRTY_TYPE + COMMA_SEP +
            DbContract.DoctorEntry.COLUMN_NAME_SPECIALIZATION_ID + INT_TYPE + COMMA_SEP +
            DbContract.DoctorEntry.COLUMN_NAME_PRACTICE_DURATION + INT_TYPE + COMMA_SEP +
            DbContract.DoctorEntry.COLUMN_NAME_CLINIC_ID + INT_TYPE + COMMA_SEP +
            FOREIGN_KEY + DbContract.DoctorEntry.COLUMN_NAME_SPECIALIZATION_ID + REFERENCES + DbContract.SpecialtyEntry.TABLE_NAME +
            "(" + DbContract.SpecialtyEntry.COLUMN_NAME_SPECIALTY_ID + ")" +
            FOREIGN_KEY + DbContract.DoctorEntry.COLUMN_NAME_CLINIC_ID + REFERENCES + DbContract.ClinicEntry.TABLE_NAME +
            "(" + DbContract.ClinicEntry.COLUMN_NAME_CLINIC_ID + ")" +" )";

    /**
     * Contains SQL statement for deleting Doctor table
     */
    private static final String SQL_DELETE_DOCTOR =
            "DROP TABLE IF EXISTS " + DbContract.DoctorEntry.TABLE_NAME + ";";

    /*DOCTOR SCHEDULE TABLE*/
    /**
     * Contains SQL statement for creating Doctor Schedule Table
     */
    private static final String SQL_CREATE_DOCTOR_SCHEDULE = "CREATE TABLE " + DbContract.DoctorScheduleEntry.TABLE_NAME + " (" +
            DbContract.DoctorScheduleEntry.COLUMN_NAME_DOCTOR_SCHEDULE_ID + INT_KEY_TYPE /*+ PRIMARY_KEY*/ + COMMA_SEP +
            DbContract.DoctorScheduleEntry.COLUMN_NAME_DOCTOR_ID + INT_TYPE + COMMA_SEP +
            DbContract.DoctorScheduleEntry.COLUMN_NAME_CLINIC_ID + INT_TYPE + COMMA_SEP +
            DbContract.DoctorScheduleEntry.COLUMN_NAME_DAY + VARCHAR_TEN_TYPE + COMMA_SEP +
            DbContract.DoctorScheduleEntry.COLUMN_NAME_START_TIME + DATETIME_TYPE + COMMA_SEP +
            DbContract.DoctorScheduleEntry.COLUMN_NAME_END_TIME + DATETIME_TYPE  + COMMA_SEP +
            FOREIGN_KEY + DbContract.DoctorScheduleEntry.COLUMN_NAME_DOCTOR_ID + REFERENCES + DbContract.DoctorEntry.TABLE_NAME +
            "(" + DbContract.DoctorEntry.COLUMN_NAME_DOCTOR_ID + ")" + COMMA_SEP +
            FOREIGN_KEY + DbContract.DoctorScheduleEntry.COLUMN_NAME_CLINIC_ID + REFERENCES + DbContract.ClinicEntry.TABLE_NAME +
            "(" + DbContract.ClinicEntry.COLUMN_NAME_CLINIC_ID + ")" + ")";
    /**
     * Contains SQL statement for deleting Doctor Schedule table
     */
    private static final String SQL_DELETE_DOCTOR_SCHEDULE =
            "DROP TABLE IF EXISTS " + DbContract.DoctorScheduleEntry.TABLE_NAME + ";";

    /*CLINIC TABLE*/
    /**
     * Contains SQL statement for creating Clinic table
     */
    private static final String SQL_CREATE_CLINIC = "CREATE TABLE " + DbContract.ClinicEntry.TABLE_NAME + " (" +
            DbContract.ClinicEntry.COLUMN_NAME_CLINIC_ID + INT_KEY_TYPE /*+ PRIMARY_KEY*/ + COMMA_SEP +
            DbContract.ClinicEntry.COLUMN_NAME_CLINIC_NAME + VARCHAR_THIRTY_TYPE + COMMA_SEP +
            DbContract.ClinicEntry.COLUMN_NAME_ADDRESS + VARCHAR_FIFTY_TYPE + COMMA_SEP +
            DbContract.ClinicEntry.COLUMN_NAME_COUNTRY + INT_TYPE + COMMA_SEP +
            DbContract.ClinicEntry.COLUMN_NAME_ZIPCODE + VARCHAR_TEN_TYPE + COMMA_SEP +
            DbContract.ClinicEntry.COLUMN_NAME_TEL_NUMBER + VARCHAR_TEN_TYPE + COMMA_SEP +
            DbContract.ClinicEntry.COLUMN_NAME_FAX_NUMBER + VARCHAR_TEN_TYPE + COMMA_SEP +
            DbContract.ClinicEntry.COLUMN_NAME_EMAIL + VARCHAR_THIRTY_TYPE + " );";
    /**
     * Contains SQL statement for deleting Clinic table
     */
    private static final String SQL_DELETE_CLINIC =
            "DROP TABLE IF EXISTS " + DbContract.ClinicEntry.TABLE_NAME + ";";

    /*SPECIALTY TABLE*/
    /**
     * Contains SQL statement for creating Specialty table
     */
    private static final String SQL_CREATE_SPECIALTY = "CREATE TABLE " + DbContract.SpecialtyEntry.TABLE_NAME + " (" +
            DbContract.SpecialtyEntry.COLUMN_NAME_SPECIALTY_ID + INT_KEY_TYPE /*+ PRIMARY_KEY*/ + COMMA_SEP +
            DbContract.SpecialtyEntry.COLUMN_NAME_SPECIALTY_NAME + VARCHAR_THIRTY_TYPE + " );";
    /**
     * Contains SQL statement for deleting Specialty table
     */
    private static final String SQL_DELETE_SPECIALTY =
            "DROP TABLE IF EXISTS " + DbContract.SpecialtyEntry.TABLE_NAME + ";";

    /*SERVICE TABLE*/
    /**
     * Contains SQL statement for creating Service table
     */
    private static final String SQL_CREATE_SERVICE = "CREATE TABLE " + DbContract.ServiceEntry.TABLE_NAME + " (" +
            DbContract.ServiceEntry.COLUMN_NAME_SERVICE_ID + INT_KEY_TYPE /*+ PRIMARY_KEY*/ + COMMA_SEP +
            DbContract.ServiceEntry.COLUMN_NAME_SERVICE_NAME + VARCHAR_THIRTY_TYPE + COMMA_SEP +
            DbContract.ServiceEntry.COLUMN_NAME_SPECIALTY_ID + INT_TYPE + COMMA_SEP +
            DbContract.ServiceEntry.COLUMN_NAME_SERVICE_DURATION + INT_TYPE + COMMA_SEP +
            DbContract.ServiceEntry.COLUMN_NAME_PREAPPOINTMENT_ACTIONS + TEXT_TYPE + COMMA_SEP +
            FOREIGN_KEY + DbContract.ServiceEntry.COLUMN_NAME_SPECIALTY_ID + REFERENCES + DbContract.SpecialtyEntry.TABLE_NAME +
            "(" + DbContract.SpecialtyEntry.COLUMN_NAME_SPECIALTY_ID + ")" + " );";

    /**
     * Contains SQL statement for deleting Service table
     */
    private static final String SQL_DELETE_SERVICE =
            "DROP TABLE IF EXISTS " + DbContract.ServiceEntry.TABLE_NAME + ";";

    /*PATIENT ENTRY*/
    /**
     * Contains SQL statement for creating Patient table
     */
    private static final String SQL_CREATE_PATIENT = "CREATE TABLE " + DbContract.PatientEntry.TABLE_NAME + " (" +
            //DbContract.PatientEntry.COLUMN_NAME_PATIENT_ID + INT_KEY_TYPE + PRIMARY_KEY + COMMA_SEP +
            DbContract.PatientEntry.COLUMN_NAME_PATIENT_ID + INT_TYPE + PRIMARY_KEY + COMMA_SEP +
            DbContract.PatientEntry.COLUMN_NAME_DOB + DATETIME_TYPE + COMMA_SEP +
            DbContract.PatientEntry.COLUMN_NAME_AGE + INT_TYPE + COMMA_SEP +
            DbContract.PatientEntry.COLUMN_NAME_MEDICAL_HISTORY + TEXT_TYPE + COMMA_SEP +
            DbContract.PatientEntry.COLUMN_NAME_ALLERGIES + TEXT_TYPE + COMMA_SEP +
            DbContract.PatientEntry.COLUMN_NAME_TREATMENTS + TEXT_TYPE + COMMA_SEP +
            DbContract.PatientEntry.COLUMN_NAME_MEDICATIONS + TEXT_TYPE + COMMA_SEP +
            FOREIGN_KEY + DbContract.PatientEntry.COLUMN_NAME_PATIENT_ID + REFERENCES + DbContract.AccountEntry.TABLE_NAME +
            "(" + DbContract.AccountEntry.COLUMN_NAME_ACCOUNT_ID + ")" +  " );";
    /**
     * Contains SQL statement for deleting Patient table
     */
    private static final String SQL_DELETE_PATIENT =
            "DROP TABLE IF EXISTS " + DbContract.PatientEntry.TABLE_NAME + ";";

    /**
     * Constructor for DbHelper, which will create database schema
     * @param context Current state of the application
     */
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Contains DbHelper instance
     */
    private static DbHelper instance;

    /**
     * Retrieve the current instance of DbHelper, if instance is null, DbHelper will be created
     *
     * @param context Current state of the application
     * @return the instance of DbHelper
     */
    public static synchronized DbHelper getHelper(Context context) {
        if (instance == null)
            instance = new DbHelper(context);
        return instance;
    }

    /**
     * Create all the tables exist in the database
     * @param db SQLiteDatabase
     */
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ACCOUNT);
        db.execSQL(SQL_CREATE_APPOINTMENT);
        db.execSQL(SQL_CREATE_CLINIC);
        db.execSQL(SQL_CREATE_DOCTOR);
        db.execSQL(SQL_CREATE_DOCTOR_SCHEDULE);
        db.execSQL(SQL_CREATE_PATIENT);
        db.execSQL(SQL_CREATE_SERVICE);
        db.execSQL(SQL_CREATE_SPECIALTY);
    }

    /**
     * Update the database schema
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ACCOUNT);
        db.execSQL(SQL_DELETE_APPOINTMENT);
        db.execSQL(SQL_DELETE_CLINIC);
        db.execSQL(SQL_DELETE_DOCTOR);
        db.execSQL(SQL_DELETE_DOCTOR_SCHEDULE);
        db.execSQL(SQL_DELETE_PATIENT);
        db.execSQL(SQL_DELETE_SERVICE);
        db.execSQL(SQL_DELETE_SPECIALTY);
        onCreate(db);
    }

    /**
     * Revert back the database schema to the oldVersion
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    /**
     * To SQLiteDatabase before modification into the database
     * @param db
     */
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    /**
     * Close SQLiteDatabase after modification is finished
     * @param db
     */
    public void closeDb(SQLiteDatabase db) {
        if (db != null && db.isOpen())
            db.close();
    }
}
