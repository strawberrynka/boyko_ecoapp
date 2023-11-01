package com.example.ecoapp.presentation.fragments;

import android.graphics.PointF;
import android.location.Address;
import android.location.Geocoder;
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

import com.example.ecoapp.R;
import com.example.ecoapp.databinding.FragmentMapBinding;
import com.example.ecoapp.data.models.EventCustom;
import com.example.ecoapp.domain.helpers.StorageHandler;
import com.example.ecoapp.presentation.MainActivity;
import com.example.ecoapp.presentation.viewmodels.EventViewModel;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CompositeIcon;
import com.yandex.mapkit.map.IconStyle;
import com.yandex.mapkit.map.InputListener;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.RotationType;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.search.SearchFactory;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.mapkit.user_location.UserLocationView;
import com.yandex.runtime.image.ImageProvider;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapFragment extends Fragment implements UserLocationObjectListener {
    private FragmentMapBinding binding;
    private WeakReference<FragmentMapBinding> mBinding;
    private MapView mapView;
    private UserLocationLayer userLocationLayer;
    private MapObjectCollection mapObjects;
    private final Animation pingAnimation = new Animation(Animation.Type.SMOOTH, 0);
    private EventViewModel viewModel;
    private StorageHandler storageHandler;
    private Bundle bundle;
    private Geocoder geoCoder;
    private ArrayList<EventCustom> eventCustoms;
    private boolean isTrue = false;

    private final InputListener listener = new InputListener() {
        @Override
        public void onMapTap(@NonNull Map map, @NonNull Point point) {
            double latitude = point.getLatitude();
            double longitude = point.getLongitude();

            if (bundle != null) bundle.clear();
            bundle = new Bundle();
            bundle.putDouble("lat", latitude);
            bundle.putDouble("long", longitude);

            try {
                List<Address> addresses = geoCoder.getFromLocation(latitude, longitude, 1);

                if (addresses != null && !addresses.isEmpty()) {
                    Address address = addresses.get(0);
                    String fullAddress = address.getAddressLine(0);
                    bundle.putString("address", fullAddress);

                    boolean isTrueTrue = false;

                    for (EventCustom eventCustom: eventCustoms) {
                        if (eventCustom.getPlace().equals(fullAddress)) {
                            isTrueTrue = true;
                            bundle.putString("id", eventCustom.getEventID());
                            break;
                        }
                    }

                    binding.mapCardView.setVisibility(View.VISIBLE);
                    binding.mapCoords.setText(fullAddress);

                    if (isTrueTrue) {
                        binding.mapButtonOneEvent.setOnClickListener(v -> {
                            Navigation.findNavController(v).navigate(R.id.eventFragment, bundle);
                            isTrue = false;
                        });
                        binding.mapButton.setVisibility(View.GONE);
                        binding.mapButtonOneEvent.setVisibility(View.VISIBLE);
                    } else {
                        binding.mapButton.setVisibility(View.VISIBLE);
                        binding.mapButtonOneEvent.setVisibility(View.GONE);
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onMapLongTap(@NonNull Map map, @NonNull Point point) {}
    };

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false);
        binding.setThemeInfo(new StorageHandler(requireContext()).getTheme());
        mBinding = new WeakReference<>(binding);

        viewModel = new ViewModelProvider(requireActivity()).get(EventViewModel.class);

        this.mapView = binding.mapview;

        initMap();

        return binding.getRoot();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding.clear();
        isTrue = false;
//        if (mapView != null) mapView.getMap().getMapObjects().clear();
    }

    /**
     * Инициализация параметров отображения яндекс карты
     * Отображение меток магазинов на карте
     * Отображение метки пользователя по умолчанию
     * Запрос на включение геолокации
     */
    private void initMap() {
        storageHandler = new StorageHandler(requireContext());
        mapView.getMap().setNightModeEnabled(storageHandler.getTheme() == 1);

        userLocationLayer = MapKitFactory.getInstance().createUserLocationLayer(mapView.getMapWindow());
        userLocationLayer.setVisible(true);
        userLocationLayer.setHeadingEnabled(true);
        userLocationLayer.setObjectListener(this);
        mapView.getMap().setRotateGesturesEnabled(false);
        mapObjects = mapView.getMap().getMapObjects().addCollection();

        mapView.getMap().move(new CameraPosition(new Point(55.71989101308894, 37.5689757769603), 14, 0, 0), pingAnimation, null);

        binding.mapButton.setOnClickListener(v -> {
            if (bundle != null) {
                Navigation.findNavController(v).navigate(R.id.createEventFragment, bundle);
                isTrue = false;
            }
        });

        binding.fragmentMapBackToPreviousFragmentButton.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());

        geoCoder = new Geocoder(requireContext(), Locale.getDefault());

        viewModel.getEventsList().observe(requireActivity(), eventsList -> {
            if (eventsList != null) {
                eventCustoms = eventsList;
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView.getMap().addInputListener(listener);

        viewModel.getIsContext().observe(getViewLifecycleOwner(), isCtx -> {
            if (isCtx) {
                ImageProvider imageProvider = ImageProvider.fromResource(requireContext(), R.drawable.place_mark);
                for (EventCustom event: eventCustoms) {
                    if (event.getLongt() != 0 && event.getLat() != 0) {
                        mapObjects.addPlacemark(new Point(event.getLat(), event.getLongt()), imageProvider);
                    }
                }
                viewModel.setCancelContext();
            }
        });
    }

    @Override
    public void onObjectAdded(@NotNull UserLocationView userLocationView) {
        if (!isTrue) {
            userLocationLayer.setAnchor(
                    new PointF((float) (mapView.getWidth() * 0.5), (float)
                            (mapView.getHeight() * 0.5)),
                    new PointF((float) (mapView.getWidth() * 0.5), (float)
                            (mapView.getHeight() * 0.83)));

//            userLocationView.getArrow().setIcon(ImageProvider.fromResource(
//                    requireContext(), R.drawable.add_guide_icon));

//            CompositeIcon pinIcon = userLocationView.getPin().useCompositeIcon();

//            pinIcon.setIcon("icon", ImageProvider.fromResource(requireContext(), R.drawable.add_guide_icon),
//                    new IconStyle().setAnchor(new PointF(0f, 0f))
//                            .setRotationType(RotationType.ROTATE)
//                            .setZIndex(0f)
//                            .setScale(1f)
//            );

            isTrue = true;
        }
    }

    @Override
    public void onObjectRemoved(@NotNull UserLocationView view) {}

    @Override
    public void onObjectUpdated(@NotNull UserLocationView view, @NotNull ObjectEvent event) {}
}

