package com.example.markshim.tvprogram;

import android.test.mock.MockContext;

import com.example.markshim.tvprogram.model.Booking;
import com.example.markshim.tvprogram.viewmodel.CancelViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by Mark Shim on 2018-01-22.
 */

@RunWith(MockitoJUnitRunner.class)
public class CancelViewModelTest {
    @Mock
    private Booking booking;
    @Mock
    private MockContext mockContext;

    private CancelViewModel cancelViewModel;

    @Before
    public void setUp() {
        booking = new Booking();
        booking.setTitle("National Treasure");
        booking.setRecordingId(3456);
        cancelViewModel = new CancelViewModel(mockContext, booking);
    }

    @Test
    public void checkTitleTest() {
        assertEquals(cancelViewModel.getTitle(), booking.getTitle());
    }
}
