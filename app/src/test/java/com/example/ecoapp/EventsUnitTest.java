package com.example.ecoapp;

import static org.mockito.Mockito.verify;

import com.example.ecoapp.presentation.fragments.EventFragment;
import com.example.ecoapp.presentation.fragments.EventsFragment;
import com.example.ecoapp.presentation.viewmodels.EventViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnit4.class)
public class EventsUnitTest {
    @Mock
    private EventViewModel mockEventViewModel;
    private EventsFragment eventsFragment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        eventsFragment = new EventsFragment();
        eventsFragment.viewModel = mockEventViewModel;
    }

    @Test
    public void onDestroyView_shouldSetCancelNavigation() {
        eventsFragment.onDestroyView();

        verify(mockEventViewModel).clearLoadData();
    }
}
