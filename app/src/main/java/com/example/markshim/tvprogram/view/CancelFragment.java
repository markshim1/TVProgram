package com.example.markshim.tvprogram.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.markshim.tvprogram.R;
import com.example.markshim.tvprogram.databinding.CancelFragmentBinding;
import com.example.markshim.tvprogram.di.DaggerServerRequestComponent;
import com.example.markshim.tvprogram.di.ServerRequestComponent;
import com.example.markshim.tvprogram.model.Booking;
import com.example.markshim.tvprogram.model.ProgramService;
import com.example.markshim.tvprogram.viewmodel.CancelViewModel;

import javax.inject.Inject;

public class CancelFragment extends Fragment {

    private static final String BOOKING = "BOOKING";
    private CancelFragmentBinding cancelFragmentBinding;
    private ServerRequestComponent component;
    private CancelViewModel cancelViewModel;

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

        Booking booking = (Booking) getArguments().getSerializable(BOOKING);
        cancelFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.cancel_fragment, container, false);
        cancelViewModel = new CancelViewModel(getActivity().getApplicationContext(), booking, programService, getActivity().getFragmentManager());
        cancelFragmentBinding.setCancelViewModel(cancelViewModel);
        return cancelFragmentBinding.getRoot();
    }

    private void initComponent() {
        component = DaggerServerRequestComponent.builder().build();
        component.inject(this);
    }

    public void btnBack(View view) {
        FragmentManager fragmentManager = getActivity().getFragmentManager();
        fragmentManager.popBackStack();
    }
}
