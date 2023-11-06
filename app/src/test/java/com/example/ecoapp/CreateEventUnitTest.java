package com.example.ecoapp;

import static org.mockito.Mockito.verify;

import com.example.ecoapp.presentation.fragments.AuthLoginFragment;
import com.example.ecoapp.presentation.fragments.CreateEventFragment;
import com.example.ecoapp.presentation.viewmodels.AuthViewModel;
import com.example.ecoapp.presentation.viewmodels.EventViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnit4.class)
public class CreateEventUnitTest {
    @Mock
    private EventViewModel mockEventViewModel;
    private CreateEventFragment createEventFragment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        createEventFragment = new CreateEventFragment();
        createEventFragment.eventViewModel = mockEventViewModel;
    }

    @Test
    public void onDestroyView_shouldSetCancelNavigation() {
        createEventFragment.onDestroyView();

        verify(mockEventViewModel).cancelNavigate();
    }
}
