package com.example.ecoapp;

import static org.mockito.Mockito.verify;

import com.example.ecoapp.presentation.adapters.TasksAdapter;
import com.example.ecoapp.presentation.fragments.AddHabitFragment;
import com.example.ecoapp.presentation.fragments.ProfileFragment;
import com.example.ecoapp.presentation.viewmodels.HabitViewModel;
import com.example.ecoapp.presentation.viewmodels.ProfileViewModel;
import com.example.ecoapp.presentation.viewmodels.TaskViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnit4.class)
public class AddHabitUnitTest {
    @Mock
    private HabitViewModel mockHabitViewModel;
    private AddHabitFragment addHabitFragment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        addHabitFragment = new AddHabitFragment();
        addHabitFragment.viewModel = mockHabitViewModel;
    }

    @Test
    public void onDestroyView_shouldSetCancelNavigation() {
        addHabitFragment.onDestroyView();

        verify(mockHabitViewModel).cancelNavigate();
    }
}
