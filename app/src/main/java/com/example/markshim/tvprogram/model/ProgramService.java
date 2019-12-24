package com.example.markshim.tvprogram.model;

import io.reactivex.Observable;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Mark Shim on 2018-01-17.
 */

public interface ProgramService {
    /**
     * get Program list
     * @param number : channel number
     * @return
     */
    @GET("/programs") Observable<ProgramResponse> getProgramList(@Query("channel_number") String number);

    /**
     * request recording
     * @param number : channel number
     * @param programId : program id
     * @return
     */
    @POST("/recording") Observable<BookingResponse> requestRecord(@Query("channel_number") String number, @Query("program_id") String programId);

    /**
     * cancel recording
     * @param recordingId : recording id
     * @return
     */
    @DELETE("/delete") Observable<BookingResponse> cancelRecord(@Query("recording_id") String recordingId);
}
