package com.example.markshim.tvprogram.view;

import android.app.FragmentManager;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.markshim.tvprogram.R;
import com.example.markshim.tvprogram.databinding.ItemProgramBinding;
import com.example.markshim.tvprogram.model.Program;
import com.example.markshim.tvprogram.model.ProgramService;
import com.example.markshim.tvprogram.viewmodel.ItemProgramViewModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by Mark Shim on 2018-01-17.
 */

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ProgramAdapterViewHolder>{

    private List<Program> programList;
    private ProgramService programService;
    private FragmentManager fragmentManager;

    public ProgramAdapter(ProgramService programService, FragmentManager fragmentManager) {
        this.programService = programService;
        this.programList = Collections.emptyList();
        this.fragmentManager = fragmentManager;
    }

    public void setProgramList(List<Program> programList) {
        this.programList = programList;
        notifyDataSetChanged();
    }

    @Override
    public ProgramAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemProgramBinding itemProgramBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_program, parent, false);
        return new ProgramAdapterViewHolder(itemProgramBinding, programService, fragmentManager);
    }

    @Override
    public void onBindViewHolder(ProgramAdapterViewHolder holder, int position) {
        holder.bindProgram(programList.get(position));
    }

    @Override
    public int getItemCount() {
        return programList.size();
    }

    public static class ProgramAdapterViewHolder extends RecyclerView.ViewHolder {
        ItemProgramBinding itemProgramBinding;
        ProgramService programService;
        FragmentManager fragmentManager;

        public ProgramAdapterViewHolder(ItemProgramBinding itemProgramBinding, ProgramService programService, FragmentManager fragmentManager) {
            super(itemProgramBinding.itemProgram);
            this.itemProgramBinding = itemProgramBinding;
            this.programService = programService;
            this.fragmentManager = fragmentManager;
        }

        void bindProgram(Program program) {
            if (itemProgramBinding.getItemProgramViewModel() == null) {
                itemProgramBinding.setItemProgramViewModel(
                        new ItemProgramViewModel(itemView.getContext(), program, programService, fragmentManager));
            } else {
                itemProgramBinding.getItemProgramViewModel().setProgram(program);
            }
        }
    }
}
