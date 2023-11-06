package com.example.ecoapp.presentation.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecoapp.data.models.Guide;
import com.example.ecoapp.domain.helpers.PermissionHandler;
import com.example.ecoapp.domain.helpers.StorageHandler;
import com.example.ecoapp.presentation.adapters.AdviceAdapter;
import com.example.ecoapp.presentation.adapters.NearbyAdapter;
import com.example.ecoapp.presentation.adapters.SavedAdviceAdapter;
import com.example.ecoapp.presentation.adapters.TasksAdapter;
import com.example.ecoapp.databinding.FragmentHomeBinding;
import com.example.ecoapp.data.models.Advice;
import com.example.ecoapp.data.models.EventCustom;
import com.example.ecoapp.data.models.Task;
import com.example.ecoapp.R;
import com.example.ecoapp.presentation.viewmodels.EventViewModel;
import com.example.ecoapp.presentation.viewmodels.GuideViewModel;
import com.example.ecoapp.presentation.viewmodels.TaskViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private FragmentHomeBinding binding;
    private EventViewModel viewModel;
    private GuideViewModel guideViewModel;
    private TaskViewModel taskViewModel;
    private ArrayList<EventCustom> eventCustoms;
    private AppCompatActivity activity;
    private boolean isLoad = false;
    private StorageHandler storageHandler;
    private TasksAdapter tasksAdapter;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AppCompatActivity) {
            activity = (AppCompatActivity) context;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        isLoad = false;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        taskViewModel.setCancelNavigation();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        storageHandler = new StorageHandler(requireContext());
        binding.setThemeInfo(storageHandler.getTheme());

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        viewModel = new ViewModelProvider(this).get(EventViewModel.class);
        guideViewModel = new ViewModelProvider(this).get(GuideViewModel.class);
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        binding.homeLoader.setOnRefreshListener(this);

        binding.tasksRecyclerView.setHasFixedSize(true);
        binding.tasksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        binding.nearbyRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.nearbyRecyclerView.setLayoutManager(layoutManager);

        binding.adviceRecyclerView.setHasFixedSize(true);
        binding.adviceRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        binding.savedAdviceRecyclerView.setHasFixedSize(true);
        binding.savedAdviceRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        loadData();

        binding.dailyHabits.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("type", "daily");
            Navigation.findNavController(v).navigate(R.id.habitFragment, bundle);
        });

        binding.weeklyHabits.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("type", "weekly");
            Navigation.findNavController(v).navigate(R.id.habitFragment, bundle);
        });

        binding.monthlyHabits.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("type", "monthly");
            Navigation.findNavController(v).navigate(R.id.habitFragment, bundle);
        });

        return binding.getRoot();
    }

    private void loadNearbyEvents(double lat, double longt) {
        if (activity != null) {
            viewModel.findNearestEventsByAuthorCoords(lat, longt).observe(activity, events -> {
                if (events != null) {
                    if (eventCustoms == null) eventCustoms = new ArrayList<>();
                    else eventCustoms.clear();

                    for (EventCustom event : events) {
                        if (event == null || event.getAuthorID().equals(storageHandler.getUserID()))
                            continue;
                        eventCustoms.add(event);
                    }

                    NearbyAdapter nearbyAdapter = new NearbyAdapter(eventCustoms, storageHandler.getTheme());
                    binding.nearbyRecyclerView.setAdapter(nearbyAdapter);

                    isLoad = true;
                }
            });
        }
    }

    private void loadData() {
        taskViewModel.getAllTasks().observe(requireActivity(), tasks -> {
            if (tasks != null) {
                ArrayList<Task> tasksList = new ArrayList<>();
                for (Task task : tasks) {
                    if (task.getAuthorID().equals(storageHandler.getUserID()) || !task.getImages().isEmpty())
                        continue;
                    tasksList.add(task);
                }
                tasksAdapter = new TasksAdapter(tasksList, storageHandler.getTheme());
                binding.tasksRecyclerView.setAdapter(tasksAdapter);
            }
        });

        guideViewModel.getGuidesList().observe(requireActivity(), guides -> {
            List<Advice> guidesList = new ArrayList<>();
            for (Guide guide : guides) {
                guidesList.add(new Advice(guide.getPhoto(), guide.getTitle(), guide.getGuideID()));
            }

            AdviceAdapter adviceAdapter = new AdviceAdapter(guidesList, storageHandler.getTheme());
            binding.adviceRecyclerView.setAdapter(adviceAdapter);
            binding.homeLoader.setRefreshing(false);
        });

        guideViewModel.getGuidesSavedList().observe(requireActivity(), guides -> {
            if (guides != null) {
                List<Advice> guidesList = new ArrayList<>();
                for (Guide guide : guides) {
                    guidesList.add(new Advice(guide.getPhoto(), guide.getTitle(), guide.getGuideID()));
                }

                SavedAdviceAdapter adviceAdapter = new SavedAdviceAdapter(guidesList, storageHandler.getTheme());
                binding.savedAdviceRecyclerView.setAdapter(adviceAdapter);
                binding.homeLoader.setRefreshing(false);
            }
        });

        getLastKnownLocation();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        taskViewModel.getNavigation().observe(getViewLifecycleOwner(), isNavigation -> {
            if (isNavigation) {
                tasksAdapter.setOnItemClickListener(task -> {
                    Bundle bundle = new Bundle();
                    bundle.putString("taskID", task.getTaskID());
                    Navigation.findNavController(requireView()).navigate(R.id.taskFragment, bundle);
                    taskViewModel.setCancelNavigation();
                });
            }
        });
    }

    @Override
    public void onRefresh() {
        loadData();
    }

    private void getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            new PermissionHandler().requestMapPermissions((AppCompatActivity) requireActivity());
            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(requireActivity(), location -> {
            if (location != null) {
                loadNearbyEvents(location.getLatitude(), location.getLongitude());
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastKnownLocation();
            }
        }
    }
}
