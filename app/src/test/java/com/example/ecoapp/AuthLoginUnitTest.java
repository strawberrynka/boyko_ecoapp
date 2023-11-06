package com.example.ecoapp;

import static org.mockito.Mockito.verify;

import com.example.ecoapp.presentation.fragments.AddHabitFragment;
import com.example.ecoapp.presentation.fragments.AuthLoginFragment;
import com.example.ecoapp.presentation.viewmodels.AuthViewModel;
import com.example.ecoapp.presentation.viewmodels.HabitViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnit4.class)
public class AuthLoginUnitTest {
    @Mock
    private AuthViewModel mockAuthViewModel;
    private AuthLoginFragment authLoginFragment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        authLoginFragment = new AuthLoginFragment();
        authLoginFragment.viewModel = mockAuthViewModel;
    }

    @Test
    public void onDestroyView_shouldSetCancelNavigation() {
        authLoginFragment.onDestroyView();

        verify(mockAuthViewModel).cancelNavigate();
    }
}
