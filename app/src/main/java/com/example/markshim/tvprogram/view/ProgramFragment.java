package com.example.markshim.tvprogram.view;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.markshim.tvprogram.R;
import com.example.markshim.tvprogram.databinding.ProgramFragmentBinding;
import com.example.markshim.tvprogram.di.DaggerServerRequestComponent;
import com.example.markshim.tvprogram.di.ServerRequestComponent;
import com.example.markshim.tvprogram.model.ProgramService;
import com.example.markshim.tvprogram.viewmodel.ProgramViewModel;

import java.util.Observable;
import java.util.Observer;

import javax.inject.Inject;

public class ProgramFragment extends Fragment implements Observer {

    private static final String TAG = ProgramFragment.class.getSimpleName();
    private ProgramFragmentBinding programFragmentBinding;
    private ProgramViewModel programViewModel;
    private ServerRequestComponent component;

    @Inject
    ProgramService programService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponent();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        programFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.program_fragment, container, false);
        programViewModel = new ProgramViewModel(getActivity().getApplicationContext(), programService);
        programFragmentBinding.setProgramViewModel(programViewModel);
        setupListProgramView(programFragmentBinding.listProgram);
        setupObserver(programViewModel);
        return programFragmentBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        programViewModel.init();
        programViewModel.requestPrograms(1);
        programViewModel.requestPrograms(2);
        programViewModel.requestPrograms(3);
    }

    private void initComponent() {
        component = DaggerServerRequestComponent.builder().build();
        component.inject(this);
    }

    private void setupListProgramView(RecyclerView listProgram) {
        ProgramAdapter programAdapter = new ProgramAdapter(programService, getFragmentManager());
        listProgram.setAdapter(programAdapter);
        listProgram.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
    }

    private void setupObserver(Observable observable) {
        observable.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object arg) {
        Log.d(TAG, "update");
        if (observable instanceof ProgramViewModel) {
            ProgramAdapter programAdapter = (ProgramAdapter) programFragmentBinding.listProgram.getAdapter();
            ProgramViewModel programViewModel = (ProgramViewModel) observable;
            programAdapter.setProgramList(programViewModel.getProgramList());
        }
    }
}
