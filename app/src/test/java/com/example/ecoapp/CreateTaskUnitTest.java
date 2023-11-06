package com.example.ecoapp;

import static org.mockito.Mockito.verify;

import com.example.ecoapp.presentation.fragments.CreateEventFragment;
import com.example.ecoapp.presentation.fragments.CreateTaskFragment;
import com.example.ecoapp.presentation.viewmodels.EventViewModel;
import com.example.ecoapp.presentation.viewmodels.TaskViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnit4.class)
public class CreateTaskUnitTest {
    @Mock
    private TaskViewModel mockTaskViewModel;
    private CreateTaskFragment createTaskFragment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        createTaskFragment = new CreateTaskFragment();
        createTaskFragment.viewModel = mockTaskViewModel;
    }

    @Test
    public void onDestroyView_shouldSetCancelNavigation() {
        createTaskFragment.onDestroyView();

        verify(mockTaskViewModel).setCancelNavigation();
    }
}
