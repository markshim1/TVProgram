package com.example.markshim.tvprogram.di;

import com.example.markshim.tvprogram.view.CancelFragment;
import com.example.markshim.tvprogram.view.ConflictActivity;
import com.example.markshim.tvprogram.view.ProgramFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ServerRequestModule.class)
public interface ServerRequestComponent {
    void inject(ConflictActivity conflictActivity);
    void inject(CancelFragment cancelFragment);
    void inject(ProgramFragment programFragment);
}
