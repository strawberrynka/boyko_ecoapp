package com.example.ecoapp.presentation.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ecoapp.R;
import com.example.ecoapp.databinding.FragmentAddHabitBinding;
import com.example.ecoapp.domain.helpers.StorageHandler;
import com.example.ecoapp.presentation.MainActivity;
import com.example.ecoapp.presentation.viewmodels.HabitViewModel;

import org.jetbrains.annotations.NotNull;

public class AddHabitFragment extends Fragment {
    private FragmentAddHabitBinding binding;
    public HabitViewModel viewModel;
    private String typeHabit;

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_habit, container, false);
        binding.setThemeInfo(new StorageHandler(requireContext()).getTheme());

        viewModel = new ViewModelProvider(requireActivity()).get(HabitViewModel.class);

        Bundle args = getArguments();
        if (args != null) typeHabit = args.getString("type");

        binding.fragmentAddHabitBackToPreviousFragmentButton.setOnClickListener(v -> {
            Navigation.findNavController(v).popBackStack();
        });

        binding.fragmentHabitButtonAddHabit.setOnClickListener(v -> {
            int selectedID = binding.habitFrequencyRadioGroup.indexOfChild(requireActivity().findViewById(binding.habitFrequencyRadioGroup.getCheckedRadioButtonId()));
            if (selectedID == -1) Toast.makeText(requireContext(), "Вы не выбрали тип привычки", Toast.LENGTH_SHORT).show();
            else if (binding.habitNameEditText.getText().toString().isEmpty()) Toast.makeText(requireContext(), "Вы не указали название", Toast.LENGTH_SHORT).show();
            else {
                String type = "daily";
                switch (selectedID) {
                    case 1: type = "weekly"; break;
                    case 2: type = "monthly"; break;
                    default: type = "daily";
                }

                viewModel.createHabit(binding.habitNameEditText.getText().toString(), type).observe(requireActivity(), statusCode -> {
                    if (statusCode == 0) binding.fragmentHabitButtonAddHabit.setClickable(false);
                    else binding.fragmentHabitButtonAddHabit.setClickable(true);
                });

            }
        });

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View createdView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(createdView, savedInstanceState);

        viewModel.isNavigate().observe(getViewLifecycleOwner(), isNavigate -> {
            if (isNavigate) {
                Navigation.findNavController(requireView()).popBackStack();
                viewModel.cancelNavigate();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        viewModel.cancelNavigate();
    }
}