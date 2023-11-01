package com.example.ecoapp.presentation.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecoapp.R;
import com.example.ecoapp.data.models.HabitStats;
import com.example.ecoapp.databinding.ProgressLayoutBinding;
import com.example.ecoapp.domain.helpers.StorageHandler;
import com.example.ecoapp.presentation.MainActivity;
import com.example.ecoapp.presentation.adapters.CalendarDecorator;
import com.example.ecoapp.presentation.viewmodels.HabitViewModel;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import org.jetbrains.annotations.NotNull;

public class ProgressFragment extends Fragment {
    private ProgressLayoutBinding binding;
    private HabitViewModel viewModel;

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) requireActivity()).changeMenu(false);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) requireActivity()).changeMenu(true);
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.progress_layout, container, false);
        binding.setThemeInfo(new StorageHandler(requireContext()).getTheme());

        viewModel = new ViewModelProvider(this).get(HabitViewModel.class);

        viewModel.getHabitStats().observe(requireActivity(), habitStats -> {
            if (habitStats != null) {
                for (HabitStats habit: habitStats) {
                    CalendarDay targetDate = CalendarDay.from(2000 + habit.getYear(), habit.getMonth(), habit.getDay());
                    String type = "lose";
                    if (habit.getCount() == habit.getMaxCount()) type = "full";
                    else if (habit.getMaxCount() != habit.getCount() && habit.getCount() > 0) type = "normal";
                    binding.progressCalendar.addDecorator(new CalendarDecorator(requireContext(), targetDate, type));
                }
            }
        });


        return binding.getRoot();
    }
}