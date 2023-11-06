package com.example.ecoapp.presentation.fragments;

import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecoapp.R;
import com.example.ecoapp.data.models.User;
import com.example.ecoapp.databinding.FragmentGuideBinding;
import com.example.ecoapp.domain.helpers.StorageHandler;
import com.example.ecoapp.presentation.MainActivity;
import com.example.ecoapp.presentation.viewmodels.GuideViewModel;
import com.example.ecoapp.presentation.viewmodels.ProfileViewModel;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class GuideFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private FragmentGuideBinding binding;
    private GuideViewModel viewModel;
    private ProfileViewModel profileViewModel;
    private String guideID;

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_guide, container, false);
        binding.setThemeInfo(new StorageHandler(requireContext()).getTheme());

        viewModel = new ViewModelProvider(this).get(GuideViewModel.class);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        binding.guideBack.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());

        binding.guideLoader.setOnRefreshListener(this);

        Bundle args = getArguments();
        guideID = args != null ? args.getString("guideID") : null;

        loadData();

        return binding.getRoot();
    }

    private void updateGuideToUser() {
        profileViewModel.updateGuideToUser(guideID).observe(requireActivity(), user -> {
            if (user != null) showBookmark(user);
        });
    }

    private void showBookmark(User user) {
        if (user.getGuidesList().contains(guideID)) {
            binding.unactivatedBookmark.setVisibility(View.GONE);
            binding.activatedBookmark.setVisibility(View.VISIBLE);
        } else {
            binding.unactivatedBookmark.setVisibility(View.VISIBLE);
            binding.activatedBookmark.setVisibility(View.GONE);
        }

        binding.unactivatedBookmark.setOnClickListener(View -> updateGuideToUser());
        binding.activatedBookmark.setOnClickListener(View -> updateGuideToUser());
    }

    private void loadData() {
        if (guideID != null) {
            viewModel.getGuideByID(guideID).observe(getViewLifecycleOwner(), guide -> {
                if (guide != null) {
                    profileViewModel.getUserData("", "").observe(getViewLifecycleOwner(), user -> {
                        if (user != null) showBookmark(user);
                    });

                    viewModel.getRating(guideID).observe(getViewLifecycleOwner(), rating -> {
                        if (rating != null) binding.guideRatingBar.setRating(rating.getMark());
                    });

                    binding.guideSourceName.setText(guide.getSource());
                    binding.articleTv.setText(guide.getDescription());
                    binding.guideTitleTitle.setText(guide.getTitle());
                    binding.guideAuthorName.setText(guide.getAuthorName());

                    String url = "http://178.21.8.29:8080/image/" + guide.getPhoto();
                    Picasso.get().load(url).into(binding.adviceImage);

                    binding.guideLoader.setRefreshing(false);
                }
            });

            binding.guideRatingBar.setOnRatingBarChangeListener((ratingBar, rating, b) -> {
                viewModel.setRating(guideID, rating);
            });
        }
    }

    @Override
    public void onRefresh() {
        loadData();
    }
}