package com.mark.androidfinal;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

/**
 * This Class outlines a Book object and the attributes it contains.
 */

public class Book {
    // Attributes of Book objects.
    private String book_name;
    private String reader;
    private Date start_date;
    private Date end_date;
    private int total_pages;
    private int pages_read;
    private ArrayList<Float> hours_spent_per_week;
    private boolean completed;

    Book() {
    }

    // Constructor.
    public Book(String book_name, String reader, int total_pages) {
        this.book_name = book_name;
        this.reader = reader;
        this.start_date = new Date();
        this.total_pages = total_pages;
        this.completed = false;
    }

// Getters and Setters.
    public String getBook_name() {
        return book_name;
    }

    public String getReader() {
        return reader;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date() {
        this.end_date = new Date();
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public int getPages_read() {
        return pages_read;
    }

    // This Setter might want to be merged with add_hours_for_week method/setter.
    public void setPages_read(int pages_read) {
        this.pages_read = pages_read;
    }

    public ArrayList<Float> getHours_spent_per_week() {
        return hours_spent_per_week;
    }

    public void add_hours_for_week(float hours) {
        // TODO determine which week it currently is
        // TODO add hours to that week in arraylist  --  hours_spent_per_week[3] = 42
    }


//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel parcel, int flags) {
//        parcel.writeString(book_name);
//        parcel.writeString(reader);
//        parcel.writeParcelable((Parcelable)start_date, flags);
//        parcel.writeParcelable((Parcelable)end_date, flags);
//        parcel.writeInt(total_pages);
//        parcel.writeInt(pages_read);
//        // TODO figure out how to parse arraylist
////        parcel.writeDoubleArray((double[])hours_spent_per_week);
//        parcel.writeInt(completed ? 1 : 0);
////        parcel.writeBooleanArray(new boolean[] {completed});
//
//    }
}
