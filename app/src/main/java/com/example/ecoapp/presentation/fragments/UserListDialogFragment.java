package com.example.ecoapp.presentation.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.ecoapp.R;
import com.example.ecoapp.data.models.User;
import com.example.ecoapp.databinding.UsersListDialogBinding;
import com.example.ecoapp.domain.helpers.StorageHandler;
import com.example.ecoapp.presentation.adapters.UserScoresAdapter;
import com.example.ecoapp.presentation.viewmodels.EventViewModel;
import com.example.ecoapp.presentation.viewmodels.ProfileViewModel;

import java.util.ArrayList;

public class UserListDialogFragment extends DialogFragment {
    private UsersListDialogBinding binding;
    public UserScoresAdapter adapter;
    public EventViewModel viewModel;
    public ProfileViewModel profileViewModel;
    public ArrayList<User> users;
    public String eventID;
    public int theme;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.users_list_dialog, container, false);
        theme = new StorageHandler(requireContext()).getTheme();

        binding.setThemeInfo(theme);
        if (getDialog() != null) getDialog().setTitle("Cписок пользователей");

        viewModel = new ViewModelProvider(this).get(EventViewModel.class);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        Bundle args = getArguments();

        binding.usersListRecycler.setHasFixedSize(true);

        if (args != null) {
            eventID = args.getString("eventID");
            viewModel.getUsersScores(eventID).observe(requireActivity(), users -> {
                if (users != null) {
                    this.users = users;

                    adapter = new UserScoresAdapter(users, theme);
                    binding.usersListRecycler.setAdapter(adapter);

//                    adapter.setOnItemClickListener((position) -> {
//                        profileViewModel.updateUserScores(users.get(position).getId(), eventID).observe(requireActivity(), statusCode -> {
//                            if (statusCode >= 200 && statusCode < 400) {
//                                adapter.deleteItem(users.get(position).getId());
//                            }
//                        });
//                    });
                }
            });
        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getIsContext().observe(getViewLifecycleOwner(), isCtx -> {
            if (isCtx) {
                adapter.setOnItemClickListener((position) -> {
                    profileViewModel.updateUserScores(users.get(position).getId(), eventID).observe(requireActivity(), statusCode -> {
                        if (statusCode >= 200 && statusCode < 400) {
                            adapter.deleteItem(users.get(position).getId());
                        }
                    });
                });
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        viewModel.setCancelContext();
    }
}
