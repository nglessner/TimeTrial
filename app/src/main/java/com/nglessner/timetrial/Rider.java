package com.nglessner.timetrial;

/**
 * Created by Neil on 4/16/2015.
 */
public class Rider {
    public int RiderId;
    public int RiderNumber;
    public String FirstName;
    public String LastName;

    public String toString()
    {
        return "#" + RiderNumber + " - " + FirstName + " " + LastName;
    }
}
