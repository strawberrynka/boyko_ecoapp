package com.example.ecoapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecoapp.R;
import com.example.ecoapp.data.models.Habit;
import com.example.ecoapp.databinding.FragmentHabitsBinding;
import com.example.ecoapp.domain.helpers.StorageHandler;
import com.example.ecoapp.presentation.adapters.HabitsAdapter;
import com.example.ecoapp.presentation.fragments.HabitFragment;
import com.example.ecoapp.presentation.viewmodels.HabitViewModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
public class HabitUnitTest {
    @Mock
    private FragmentHabitsBinding mockBinding;
    @Mock
    private HabitViewModel mockViewModel;
    @Mock
    private NavController mockNavController;
    @Mock
    private HabitsAdapter mockHabitsAdapter;
    @Mock
    private StorageHandler mockStorageHandler;
    @Mock
    private LayoutInflater mockLayoutInflater;
    @Mock
    private View mockView;
    @Mock
    private ViewGroup mockViewGroup;
    @Mock
    private RecyclerView mockRecyclerView;

    private HabitFragment habitFragment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        habitFragment = new HabitFragment();
        habitFragment.binding = mockBinding;
        habitFragment.viewModel = mockViewModel;
        habitFragment.habitsAdapter = mockHabitsAdapter;
    }

    @Test
    public void onCreateView_shouldInitializeViewsAndData() {
        String type = "daily";
        Bundle args = new Bundle();
        args.putString("type", type);
        when(mockBinding.getThemeInfo()).thenReturn(0);
//        doNothing().when(mockBinding.habitsRecyclerView).setHasFixedSize(true);
//        doNothing().when(mockBinding.habitsRecyclerView).setLayoutManager(new LinearLayoutManager(mockViewGroup.getContext(), LinearLayoutManager.VERTICAL, false));
        when(mockViewModel.getHabitsListByType(type)).thenReturn(new MutableLiveData<>());
        when(mockStorageHandler.getTheme()).thenReturn(0);
        when(mockLayoutInflater.inflate(R.layout.fragment_habits, mockViewGroup, false)).thenReturn(mockView);

        View view = habitFragment.onCreateView(mockLayoutInflater, mockViewGroup, null);

        assertNotNull(view);
//        verify(mockBinding.habitsRecyclerView).setHasFixedSize(true);
//        verify(mockBinding.habitsRecyclerView).setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
//        verify(mockViewModel).getHabitsListByType(type);
//        verify(mockBinding.habitsRecyclerView).setAdapter(mockHabitsAdapter);
    }
}