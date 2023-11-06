package com.example.ecoapp.presentation.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import com.example.ecoapp.R;
import com.example.ecoapp.databinding.FragmentSearchBinding;
import com.example.ecoapp.domain.helpers.StorageHandler;
import com.example.ecoapp.presentation.adapters.SearchAdapter;
import com.example.ecoapp.presentation.viewmodels.EventViewModel;

import org.jetbrains.annotations.NotNull;

public class SearchFragment extends Fragment {
    private FragmentSearchBinding binding;
    private EventViewModel viewModel;
    private SearchAdapter searchAdapter;
    private int theme;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        theme = new StorageHandler(requireContext()).getTheme();
        binding.setThemeInfo(theme);
        viewModel = new ViewModelProvider(this).get(EventViewModel.class);

        binding.foundRecyclerView.setHasFixedSize(true);
        binding.foundRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        binding.searchBarEditText.setOnEditorActionListener((textView, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                viewModel.getPosts(binding.searchBarEditText.getText().toString()).observe(requireActivity(), searches -> {
                    if (searches != null) {
                        searchAdapter = new SearchAdapter(searches, theme);
                        binding.foundRecyclerView.setAdapter(searchAdapter);
                    }
                });

                return true;
            }
            return false;
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getNavigate().observe(getViewLifecycleOwner(), isNavigation -> {
            if (isNavigation) {
                searchAdapter.setOnItemClickListener(search -> {
                    Bundle bundle = new Bundle();
                    if (search.getType().equals("guide")) {
                        bundle.putString("guideID", search.getId());
                        Navigation.findNavController(requireView()).navigate(R.id.guideFragment, bundle);
                    } else if (search.getType().equals("event")) {
                        bundle.putString("id", search.getId());
                        Navigation.findNavController(requireView()).navigate(R.id.eventFragment, bundle);
                    }
                    binding.searchBarEditText.setText("");
                    viewModel.cancelNavigate();
                });
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.searchBarEditText.setText("");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        viewModel.cancelNavigate();
    }
}