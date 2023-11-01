package com.example.ecoapp.presentation.fragments;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecoapp.R;
import com.example.ecoapp.domain.helpers.PermissionHandler;
import com.example.ecoapp.domain.helpers.StorageHandler;
import com.example.ecoapp.presentation.MainActivity;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.search.SearchFactory;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.runtime.image.ImageProvider;
import com.example.ecoapp.databinding.FragmentSecondMapBinding;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Locale;

public class SecondMapFragment extends Fragment {
    private FragmentSecondMapBinding binding;
    private WeakReference<FragmentSecondMapBinding> mBinding;
    private MapView mapView;
    private UserLocationLayer userLocationLayer;
    private MapObjectCollection mapObjects;
    private final Animation pingAnimation = new Animation(Animation.Type.SMOOTH, 0);
    private PermissionHandler permissionHandler;
    private Geocoder geoCoder;
    private boolean isTrue = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        permissionHandler = new PermissionHandler();
        if (permissionHandler != null)
            permissionHandler.requestMapPermissions((AppCompatActivity) requireActivity());

        SearchFactory.initialize(requireContext());
    }

    @Override
    public void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_second_map, container, false);
        binding.setThemeInfo(new StorageHandler(requireContext()).getTheme());
        mBinding = new WeakReference<>(binding);

        Bundle args = getArguments();

        this.mapView = binding.mapview;

        initMap(args);

        return binding.getRoot();
    }

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

        isTrue = true;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mBinding.clear();

        if (mapView != null) mapView.getMap().getMapObjects().clear();
    }

    private void initMap(Bundle args) {
        mapView.getMap().setNightModeEnabled(new StorageHandler(requireContext()).getTheme() == 1);

        if (permissionHandler != null)
            permissionHandler.requestMapPermissions((AppCompatActivity) requireActivity());

        userLocationLayer = MapKitFactory.getInstance().createUserLocationLayer(mapView.getMapWindow());
        userLocationLayer.setVisible(true);
        userLocationLayer.setHeadingEnabled(true);
        mapView.getMap().setRotateGesturesEnabled(false);
        mapObjects = mapView.getMap().getMapObjects().addCollection();

        geoCoder = new Geocoder(requireContext(), Locale.getDefault());

        if (args != null) {
            double longt = args.getDouble("longt");
            double lat = args.getDouble("lat");
            mapView.getMap().move(new CameraPosition(new Point(lat, longt), 14, 0, 0), pingAnimation, null);

            try {
                List<Address> addresses = geoCoder.getFromLocation(lat, longt, 1);

                if (addresses != null && !addresses.isEmpty()) {
                    Address address = addresses.get(0);
                    String fullAddress = address.getAddressLine(0);

                    binding.mapCardView.setVisibility(View.VISIBLE);
                    binding.mapCoords.setText(fullAddress);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            ImageProvider imageProvider = ImageProvider.fromResource(requireContext(), R.drawable.place_mark);
            mapObjects.addPlacemark(new Point(lat, longt), imageProvider);
        }

        binding.mapButton.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());
    }
}