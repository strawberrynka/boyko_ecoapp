package com.example.ecoapp.presentation.fragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ecoapp.databinding.FragmentCreateEventBinding;
import com.example.ecoapp.R;
import com.example.ecoapp.domain.helpers.StorageHandler;
import com.example.ecoapp.data.models.EventCustom;
import com.example.ecoapp.presentation.MainActivity;
import com.example.ecoapp.presentation.viewmodels.EventViewModel;
import com.example.ecoapp.presentation.viewmodels.ProfileViewModel;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateEventFragment extends Fragment {
    private FragmentCreateEventBinding fragmentCreateEventBinding;
    public EventViewModel eventViewModel;
    public ProfileViewModel profileViewModel;
    private StorageHandler storageHandler;
    private int SELECT_PHOTO_PROFILE = 1;
    private String address;
    private Uri uri;
    private File fileImage;
    private double longt;
    private double lat;
    private int scoresUser;

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
        fragmentCreateEventBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_event, container, false);
        storageHandler = new StorageHandler(requireContext());
        fragmentCreateEventBinding.setThemeInfo(storageHandler.getTheme());

        fragmentCreateEventBinding.createEventPhoto.setScaleType(ImageView.ScaleType.FIT_CENTER);
        fragmentCreateEventBinding.createEventPhoto.setAdjustViewBounds(true);

        Bundle args = getArguments();
        if (args != null) {
            address = args.getString("address");
            longt = args.getDouble("long");
            lat = args.getDouble("lat");
            if (address != null) {
                fragmentCreateEventBinding.eventAddressText.setVisibility(View.VISIBLE);
                fragmentCreateEventBinding.eventAddressText.setText(address);
            }

            EventCustom eventCustom = storageHandler.getIntermediateData();
            String title = eventCustom.getTitle();
            String description = eventCustom.getDescription();
            String date = eventCustom.getDate();
            String time = eventCustom.getTime();
            Integer peopleLen = eventCustom.getMaxUsers();
            Integer scores = eventCustom.getScores();
            String link = eventCustom.getPhoto();
            if (!title.isEmpty()) fragmentCreateEventBinding.eventNameEditText.setText(title);
            if (!description.isEmpty()) fragmentCreateEventBinding.eventDescriptionEditText.setText(description);
            if (!date.isEmpty()) fragmentCreateEventBinding.eventDateEditText.setText(date);
            if (!time.isEmpty()) fragmentCreateEventBinding.eventTimeEditText.setText(time);
            if (peopleLen > 0) fragmentCreateEventBinding.eventPeopleEditText.setText(String.valueOf(peopleLen));
            if (scores > 0) fragmentCreateEventBinding.eventPointsToAPersonEditText.setText(String.valueOf(scores));
            if (!link.isEmpty()) {
                fileImage = new File(eventCustom.getPhoto());
                String filePath = fileImage.getPath();
                Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                fragmentCreateEventBinding.createEventPhoto.setImageBitmap(bitmap);
            }
        }

        eventViewModel = new ViewModelProvider(requireActivity()).get(EventViewModel.class);
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        fragmentCreateEventBinding.createEventBackToEventFragmentButton.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());

        profileViewModel.getUserData(storageHandler.getToken(), storageHandler.getUserID()).observe(requireActivity(), user -> {
            if (user != null) {
                scoresUser = user.getScores();
                fragmentCreateEventBinding.fragmentCreateEventPersonPoints.setText("Баллы: " + Integer.toString(user.getScores()));
            }
        });

        fragmentCreateEventBinding.eventFindMap.setOnClickListener(v -> {
            String title = fragmentCreateEventBinding.eventNameEditText.getText().toString();
            String description = fragmentCreateEventBinding.eventDescriptionEditText.getText().toString();
            String date = fragmentCreateEventBinding.eventDateEditText.getText().toString();
            String time = fragmentCreateEventBinding.eventTimeEditText.getText().toString();
            String peopleLen = fragmentCreateEventBinding.eventPeopleEditText.getText().toString().isEmpty() ? "0" : fragmentCreateEventBinding.eventPeopleEditText.getText().toString();
            String scores = fragmentCreateEventBinding.eventPointsToAPersonEditText.getText().toString().isEmpty() ? "0" : fragmentCreateEventBinding.eventPointsToAPersonEditText.getText().toString();

            storageHandler.saveIntermediateData(title, description, date, time, Integer.parseInt(peopleLen), fileImage == null ? "" : fileImage.getAbsolutePath(), Integer.parseInt(scores));

            Navigation.findNavController(v).navigate(R.id.mapFragment);
        });

        fragmentCreateEventBinding.createEventButtonFragmentCreateEvent.setOnClickListener(v -> {;
            String title = fragmentCreateEventBinding.eventNameEditText.getText().toString();
            String description = fragmentCreateEventBinding.eventDescriptionEditText.getText().toString();
            String date = fragmentCreateEventBinding.eventDateEditText.getText().toString();
            String time = fragmentCreateEventBinding.eventTimeEditText.getText().toString();
            String lenPeople = fragmentCreateEventBinding.eventPeopleEditText.getText().toString();
            String scores = fragmentCreateEventBinding.eventPointsToAPersonEditText.getText().toString();

            if ((title != null && title.isEmpty()) || (date != null && date.isEmpty()) || (description != null && description.isEmpty()) || (time != null && time.isEmpty()) || (lenPeople != null && lenPeople.isEmpty()) || (address != null && address.isEmpty()) || fileImage == null || (scores != null && scores.isEmpty()) ||
                    address == null || lat == 0 || longt == 0) {
                Toast.makeText(requireContext(), "Вы не заполнили все данные", Toast.LENGTH_LONG).show();
            } else if (!isValidDateFormat(date)) {
                Toast.makeText(requireContext(), "Вы ввели дату в неправильном формате", Toast.LENGTH_LONG).show();
            } else if (!isValidTimeFormat(time)) {
                Toast.makeText(requireContext(), "Вы ввели время в неправильном формате", Toast.LENGTH_LONG).show();
            } else if (Integer.parseInt(scores) < 100) {
                Toast.makeText(requireContext(), "Вы ввели слишком маленькое количество баллов", Toast.LENGTH_LONG).show();
            } else if (scoresUser < Integer.parseInt(scores)) {
                Toast.makeText(requireContext(), "У вас недостаточно баллов", Toast.LENGTH_LONG).show();
            } else {
                eventViewModel.sendData(title, description, date, time, fileImage, lenPeople, address, lat, longt, Integer.parseInt(scores)).observe(requireActivity(), statusCode -> {
                    if (statusCode < 400 && statusCode != 0) {
                        fragmentCreateEventBinding.eventNameEditText.setText("");
                        fragmentCreateEventBinding.eventDescriptionEditText.setText("");
                        fragmentCreateEventBinding.eventDateEditText.setText("");
                        fragmentCreateEventBinding.eventTimeEditText.setText("");
                        fragmentCreateEventBinding.eventPeopleEditText.setText("");
                        fragmentCreateEventBinding.eventPointsToAPersonEditText.setText("");
                        storageHandler.clearIntermediateData();
                    }
                });
            }
        });

        fragmentCreateEventBinding.createEventPhoto.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R && ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_IMAGES)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{Manifest.permission.READ_MEDIA_IMAGES},
                        1);
            } else {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(intent, "Choose Photo");

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{cameraIntent});

                startActivityForResult(chooserIntent, SELECT_PHOTO_PROFILE);
            }
        });

        fragmentCreateEventBinding.eventPointsToAPersonEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                executeEditTexts();
            }
        });

        fragmentCreateEventBinding.eventPeopleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                executeEditTexts();
            }
        });

        return fragmentCreateEventBinding.getRoot();
    }

    private void executeEditTexts() {
        String peopleLen = fragmentCreateEventBinding.eventPeopleEditText.getText().toString();
        String scores = fragmentCreateEventBinding.eventPointsToAPersonEditText.getText().toString();
        if (!peopleLen.isEmpty() && !scores.isEmpty()) {
            fragmentCreateEventBinding.createEventRequiredPoints.setText("Требуется баллов: " + Integer.toString(Integer.parseInt(peopleLen) * Integer.parseInt(scores)));
        } else {
            fragmentCreateEventBinding.createEventRequiredPoints.setText("");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        eventViewModel.getNavigate().observe(getViewLifecycleOwner(), isNavigate -> {
            if (isNavigate) {
                Navigation.findNavController(requireView()).navigate(R.id.eventsFragment);
                eventViewModel.cancelNavigate();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        eventViewModel.cancelNavigate();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (((requestCode == SELECT_PHOTO_PROFILE && resultCode == RESULT_OK)) && data != null) {
            if (data.getData() != null) {
                uri = data.getData();

                try {
                    final InputStream imageStream = requireActivity().getContentResolver().openInputStream(uri);
                    final Bitmap originalBitmap = BitmapFactory.decodeStream(imageStream);

                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    Cursor cursor = requireActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String imagePath = cursor.getString(columnIndex);
                    cursor.close();

                    fileImage = new File(imagePath);
                    fragmentCreateEventBinding.createEventPhoto.setImageBitmap(originalBitmap);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Bundle extras = data.getExtras();
                Bitmap bitmap = (Bitmap) extras.get("data");
                fileImage = new File(requireContext().getCacheDir(), "test");
                try {
                    fileImage.createNewFile();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);

                    byte[] bitmapdata = bos.toByteArray();

                    FileOutputStream fos = new FileOutputStream(fileImage);
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();

                    fragmentCreateEventBinding.createEventPhoto.setImageBitmap(bitmap);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public boolean isValidDateFormat(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

        try {
            Date date = dateFormat.parse(dateString);

            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public boolean isValidTimeFormat(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        try {
            Date date = dateFormat.parse(dateString);

            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}