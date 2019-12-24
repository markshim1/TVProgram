package com.example.markshim.tvprogram.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mark.shim on 2018. 1. 19..
 */

public class BookingResponse {
    private static final String TAG = BookingResponse.class.getSimpleName();
    @SerializedName("results") private Booking booking;

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}
