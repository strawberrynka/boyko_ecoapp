package com.example.ecoapp.presentation.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ecoapp.R;
import com.example.ecoapp.databinding.FragmentCreateTaskBinding;
import com.example.ecoapp.domain.helpers.StorageHandler;
import com.example.ecoapp.presentation.MainActivity;
import com.example.ecoapp.presentation.viewmodels.ProfileViewModel;
import com.example.ecoapp.presentation.viewmodels.TaskViewModel;

import org.jetbrains.annotations.NotNull;

public class CreateTaskFragment extends Fragment {
    private FragmentCreateTaskBinding binding;
    public TaskViewModel viewModel;
    private ProfileViewModel profileViewModel;
    private int userScores;

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_task, container, false);
        binding.setThemeInfo(new StorageHandler(requireContext()).getTheme());
        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        profileViewModel.getUserData("", "").observe(requireActivity(), user -> {
            if (user != null) {
                userScores = user.getScores();
                binding.fragmentCreateTaskPersonPoints.setText("Баллы: " + Integer.toString(userScores));
            }
        });

        binding.createTaskBackToProfileFragmentButton.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());

        binding.buttonCreateTaskTextView.setOnClickListener(View -> {
            String title = binding.taskNameEditText.getText().toString();
            String description = binding.taskDescriptionEditText.getText().toString();
            String scores = binding.taskPointsToAPersonEditText.getText().toString();
            if (title.isEmpty() || description.isEmpty() || scores.isEmpty()) {
                Toast.makeText(requireContext(), "Вы не заполнили все поля", Toast.LENGTH_SHORT).show();
            } else if (Integer.parseInt(scores) > userScores) {
                Toast.makeText(requireContext(), "У вас недостаточно баллов", Toast.LENGTH_SHORT).show();
            } else if (Integer.parseInt(scores) < 50) {
                Toast.makeText(requireContext(), "Вы ввели слишком мало баллов: нужно не меньше 50", Toast.LENGTH_SHORT).show();
            } else {
                viewModel.createTask(title, description, Integer.parseInt(scores));
            }
        });

        binding.taskPointsToAPersonEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                binding.createTaskRequiredPoints.setText("Требуется баллов: " + binding.taskPointsToAPersonEditText.getText().toString());
            }
        });


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getNavigation().observe(getViewLifecycleOwner(), isNavigation -> {
            if (isNavigation) {
                Navigation.findNavController(requireView()).navigate(R.id.action_createTaskFragment_to_profileFragment);
                viewModel.setCancelNavigation();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.setCancelNavigation();
    }
}