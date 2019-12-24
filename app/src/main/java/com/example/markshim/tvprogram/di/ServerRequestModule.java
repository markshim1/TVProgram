package com.example.markshim.tvprogram.di;

import com.example.markshim.tvprogram.model.ProgramFactory;
import com.example.markshim.tvprogram.model.ProgramService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ServerRequestModule {
    private ProgramService programService;

    @Provides
    @Singleton
    ProgramService provideProgramService() {
        return getProgramService();
    }

    private ProgramService getProgramService() {
        if (programService == null) {
            programService = ProgramFactory.create();
        }

        return programService;
    }
}
