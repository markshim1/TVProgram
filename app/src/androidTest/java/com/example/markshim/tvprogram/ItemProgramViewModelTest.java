package com.example.markshim.tvprogram;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.markshim.tvprogram.model.Program;
import com.example.markshim.tvprogram.viewmodel.ItemProgramViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Created by Mark Shim on 2018-01-22.
 */

@RunWith(AndroidJUnit4.class)
public class ItemProgramViewModelTest {
    private Program program;
    private ItemProgramViewModel itemProgramViewModel;

    @Before
    public void setUp() {
        program = MockProgram.getProgram();
        itemProgramViewModel = new ItemProgramViewModel(InstrumentationRegistry.getTargetContext(), program);
    }

    @Test
    public void checkProgramTest(){
        assertEquals(program.getTitle(), itemProgramViewModel.getTitle());
        assertEquals(String.valueOf(program.getId()), itemProgramViewModel.getId());
        assertEquals(String.valueOf(program.getStartTime()), itemProgramViewModel.getStartTime());
        assertEquals(program.getDuration(), itemProgramViewModel.getDuration());
        assertEquals(String.valueOf(program.getChannel()), itemProgramViewModel.getChannel());
        assertEquals(program.getPoster(), itemProgramViewModel.getPoster());
        assertEquals(program.getBookingView(), itemProgramViewModel.getBookingView());
        assertEquals(program.getRunningTimeView(), itemProgramViewModel.getRunningTimeView());
        assertEquals(program.getDateView(), itemProgramViewModel.getDateView());
    }
}
