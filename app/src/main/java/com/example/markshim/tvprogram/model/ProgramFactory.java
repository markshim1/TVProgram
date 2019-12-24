package com.example.markshim.tvprogram.model;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mark Shim on 2018-01-17.
 */

public class ProgramFactory {
    public static final String BASE_URL = "https://4fc8f5cd-2236-454b-ada8-7fbe8fa0be0e.mock.pstmn.io/";

    public static ProgramService create() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(ProgramService.class);
    }
}
