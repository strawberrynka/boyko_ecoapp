package com.example.ecoapp;

import static org.mockito.Mockito.verify;

import com.example.ecoapp.presentation.fragments.AuthLoginFragment;
import com.example.ecoapp.presentation.fragments.AuthSignupFragment;
import com.example.ecoapp.presentation.viewmodels.AuthViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnit4.class)
public class AuthSignupUnitTest {
    @Mock
    private AuthViewModel mockAuthViewModel;
    private AuthSignupFragment authSignupFragment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        authSignupFragment = new AuthSignupFragment();
        authSignupFragment.viewModel = mockAuthViewModel;
    }

    @Test
    public void onDestroyView_shouldSetCancelNavigation() {
        authSignupFragment.onDestroyView();

        verify(mockAuthViewModel).cancelNavigate();
    }
}
