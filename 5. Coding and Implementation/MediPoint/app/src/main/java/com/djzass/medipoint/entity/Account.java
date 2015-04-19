package com.djzass.medipoint.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

/**
 *  Account class to represent a user account for our application. To enable object passing between
 *  activities, this class extends Parcelable.
 *
 *  @author Shreyas
 *  @since 2015
 *  @version 1.0
 */
public class Account implements Parcelable {
    /**
     * Store the account id
     */
    private long id;
    /**
     * Store the username
     */
	private String username;
    /**
     * Store the password
     */
	private String password;
    /**
     * Store the full name of the user
     */
	private String name;
    /**
     * Store the personal identification of the user
     */
	private String nric;
    /**
     * Store the email of the user
     */
	private String email;
    /**
     * Store the phone number of the user
     */
    private String phoneNumber;
    /**
     * Store the gender of the user
     */
	private String gender;
    /**
     * Store the address of the user
     */
	private String address;
    /**
     * Store the marital status of the user
     */
	private String maritalStatus;
    /**
     * Store the date of birth of the user
     */
	private Calendar dob;
    /**
     * Store the citizenship of the user
     */
	private String citizenship;
    /**
     * Store the country of residence of the user
     */
	private String countryOfResidence;
    /**
     * Store the user preference of email notification, either 1 or 0
     */
    private int notifyEmail;
    /**
     * Store the user preference of sms notification, either 1 or 0
     */
    private int notifySMS;

    /**
     * Constructor for Account class with no arguments
     */
    public Account(){

    }

    /**
     * Constructor for Account class with all Account attributes except Account id
     * @param username
     * @param password
     * @param name
     * @param nric
     * @param email
     * @param phoneNumber
     * @param gender
     * @param address
     * @param maritalStatus
     * @param dob
     * @param citizenship
     * @param countryOfResidence
     * @param notifyEmail
     * @param notifySMS
     */
    public Account(String username, String password, String name, String nric,
                   String email, String phoneNumber, String gender, String address,
                   String maritalStatus, Calendar dob, String citizenship,
                   String countryOfResidence, int notifyEmail, int notifySMS) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.nric = nric;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.address = address;
        this.maritalStatus = maritalStatus;
        this.dob = dob;
        this.citizenship = citizenship;
        this.countryOfResidence = countryOfResidence;
        this.notifyEmail = notifyEmail;
        this.notifySMS = notifySMS;
    }

    /**
     * Constructor for Account class with all Account attributes
     * @param id
     * @param username
     * @param password
     * @param name
     * @param nric
     * @param email
     * @param phoneNumber
     * @param gender
     * @param address
     * @param maritalStatus
     * @param dob
     * @param citizenship
     * @param countryOfResidence
     * @param notifyEmail
     * @param notifySMS
     */
    public Account(long id, String username, String password, String name, String nric,
                   String email, String phoneNumber, String gender, String address,
                   String maritalStatus, Calendar dob, String citizenship,
                   String countryOfResidence, int notifyEmail, int notifySMS) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.nric = nric;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.address = address;
        this.maritalStatus = maritalStatus;
        this.dob = dob;
        this.citizenship = citizenship;
        this.countryOfResidence = countryOfResidence;
        this.notifySMS = notifySMS;
        this.notifyEmail = notifyEmail;
    }

    /**
     * Constructor for Account class that receives parcel as argument
     * @param in
     */
    public Account(Parcel in){
        readFromParcel(in);
    }

    /**
     * Printing all attributes of the Account
     * @return string that combines all attributes
     */
    public String print(){
        String temp = "";
        temp+= id + " ";
        temp+= username + " ";
        temp+= password + " ";
        temp+= name + " ";
        temp+= nric + " ";
        temp+= email + " ";
        temp+= phoneNumber + " ";
        temp+= gender + " ";
        temp+= address + " ";
        temp+= maritalStatus + " ";
        temp+= String.valueOf(dob) + " ";
        temp+= citizenship + " ";
        temp+= countryOfResidence + " ";
        temp+= notifyEmail + " ";
        temp+= notifySMS + " ";
        return temp;
    }

    /**
     * Get username
     * @return username
     */
	public String getUsername() {
		return username;
	}

    /**
     * Set the username of this account
     * @param username
     */
	public void setUsername(String username) {
		this.username = username;
	}

    /**
     * Get user password
     * @return password of the user
     */
	public String getPassword() {
		return password;
	}

    /**
     * Set the password of this account
     * @param password
     */
	public void setPassword(String password) {
		this.password = password;
	}

    /**
     * Get the full name of the user
     * @return name of the user
     */
	public String getName() {
		return name;
	}

    /**
     * Set the full name of this account
     * @param name
     */
	public void setName(String name) {
		this.name = name;
	}

    /**
     * Get the nric of the user
     * @return nric of the user
     */
	public String getNric() {
		return nric;
	}

    /**
     * Set the nric of this account
     * @param nric
     */
	public void setNric(String nric) {
		this.nric = nric;
	}

    /**
     * Get the email of this user account
     * @return email of the user
     */
	public String getEmail() {
		return email;
	}

    /**
     * Set the email of this user account
     * @param email
     */
	public void setEmail(String email) {
		this.email = email;
	}

    /**
     * Get the phone number of the user
     * @return phone number of the user
     */
	public String getPhoneNumber() {
		return phoneNumber;
	}

    /**
     * Set the phone number of this user account
     * @param phoneNumber
     */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

    /**
     * Get the gender of the user
     * @return gender of the user
     */
	public String getGender() {
		return gender;
	}

    /**
     * Set the gender of the user account
     * @param gender
     */
	public void setGender(String gender) {
		this.gender = gender;
	}

    /**
     * Get the address of this user account
     * @return address of the user
     */
	public String getAddress() {
		return address;
	}

    /**
     * Set the address of the user
     * @param address
     */
	public void setAddress(String address) {
		this.address = address;
	}

    /**
     * Get the marital status of the user
     * @return marital status of the user
     */
	public String getMaritalStatus() {
		return maritalStatus;
	}

    /**
     * Set the marital status of the user
     * @param maritalStatus
     */
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

    /**
     * Get the Date of Birth of the user
     * @return the Date of Birth of the user in Calendar
     */
	public Calendar getDob() {
		return dob;
	}

    /**
     * Set the Date of Birth of the user
     * @param dob
     */
	public void setDob(Calendar dob) {
		this.dob = dob;
	}

    /**
     * Get the citizenship of the user
     * @return citizenship of the user
     */
   	public String getCitizenship() {
		return citizenship;
	}

    /**
     * Set the citizenship of the user
     * @param citizenship
     */
	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}

    /**
     * Get the country of residence of the user
     * @return country of residence of the user
     */
	public String getCountryOfResidence() {
		return countryOfResidence;
	}

    /**
     * Set the country of residence of the user
     * @param countryOfResidence
     */
	public void setCountryOfResidence(String countryOfResidence) {
		this.countryOfResidence = countryOfResidence;
	}

    /**
     * Get the id of the account
     * @return {@link Account#id} of the Account
     */
    public long getId() {
        return id;
    }

    /**
     * Set the id of the account
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get the user preference for email notification
     * @return {@link Account#notifyEmail}
     */
    public int getNotifyEmail() {
        return notifyEmail;
    }

    /**
     * Set the user preference for email notification
     * @param notifyEmail
     */
    public void setNotifyEmail(int notifyEmail) {
        this.notifyEmail = notifyEmail;
    }

    /**
     * Get the user preference SMS notification
     * @return {@link Account#notifySMS}
     */
    public int getNotifySMS() {
        return notifySMS;
    }

    /**
     * Set the user preference for SMS notification
     * @param notifySMS
     */
    public void setNotifySMS(int notifySMS) {
        this.notifySMS = notifySMS;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable's marshalled representation.
     * @return 0
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten Account object in to a Parcel.
     * @param desc the destination Parcel in which the object should be written
     * @param flags Additional flags about how the object should be written
     */
    @Override
    public void writeToParcel(Parcel desc, int flags) {
        desc.writeLong(this.id);
        desc.writeString(this.username);
        desc.writeString(this.password);
        desc.writeString(this.name);
        desc.writeString(this.nric);
        desc.writeString(this.email);
        desc.writeString(this.phoneNumber);
        desc.writeString(this.gender);
        desc.writeString(this.address);
        desc.writeString(this.maritalStatus);
        desc.writeString(this.citizenship);
        desc.writeString(this.countryOfResidence);
        desc.writeInt(this.notifyEmail);
        desc.writeInt(this.notifySMS);
    }

    /**
     * Interface that must be implemented and provided as a public CREATOR field that generates instances
     * of your Parcelable class from a Parcel.
     */
    public static final Parcelable.Creator<Account> CREATOR
            = new Parcelable.Creator<Account>() {
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    /**
     * Create an instance of the Account class, instantiating it from the given Parcel whose data had
     * previously been written.
     * @param in The Parcel to read the object's data from.
     */
    public void readFromParcel(Parcel in) {
        this.id = in.readLong();
        this.username = in.readString();
        this.password = in.readString();
        this.name = in.readString();
        this.nric = in.readString();
        this.email = in.readString();
        this.phoneNumber = in.readString();
        this.gender = in.readString();
        this.address = in.readString();
        this.maritalStatus = in.readString();
        this.citizenship = in.readString();
        this.countryOfResidence = in.readString();
        this.notifyEmail= in.readInt();
        this.notifySMS = in.readInt();
    }
}
