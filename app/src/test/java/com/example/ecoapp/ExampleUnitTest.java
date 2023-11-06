package com.example.ecoapp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.verify;

import com.example.ecoapp.presentation.adapters.TasksAdapter;
import com.example.ecoapp.presentation.fragments.ProfileFragment;
import com.example.ecoapp.presentation.viewmodels.ProfileViewModel;
import com.example.ecoapp.presentation.viewmodels.TaskViewModel;

@RunWith(JUnit4.class)
public class ExampleUnitTest {
    @Mock
    private ProfileViewModel mockProfileViewModel;
    @Mock
    private TaskViewModel mockTaskViewModel;
    @Mock
    private TasksAdapter mockTasksAdapter;

    private ProfileFragment profileFragment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        profileFragment = new ProfileFragment();
        profileFragment.viewModel = mockProfileViewModel;
        profileFragment.taskViewModel = mockTaskViewModel;
        profileFragment.tasksAdapter = mockTasksAdapter;
    }

    @Test
    public void onDestroyView_shouldSetCancelNavigation() {
        profileFragment.onDestroyView();

        verify(mockTaskViewModel).setCancelNavigation();
    }
}