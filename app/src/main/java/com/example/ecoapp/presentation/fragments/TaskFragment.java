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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ecoapp.R;
import com.example.ecoapp.databinding.FragmentTaskBinding;
import com.example.ecoapp.domain.helpers.StorageHandler;
import com.example.ecoapp.presentation.MainActivity;
import com.example.ecoapp.presentation.viewmodels.ProfileViewModel;
import com.example.ecoapp.presentation.viewmodels.TaskViewModel;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class TaskFragment extends Fragment {
    private FragmentTaskBinding binding;
    private TaskViewModel viewModel;
    private StorageHandler storageHandler;
    private Bundle args;
    private String taskID;
    private int SELECT_PHOTO_PROFILE = 1;
    private Uri uri;
    private File file1;
    private File file2;
    private File file3;
    private int currentFile = 1;

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_task, container, false);
        storageHandler = new StorageHandler(requireContext());
        binding.setThemeInfo(storageHandler.getTheme());

        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        binding.theTaskBackToPreviousFragmentButton.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());

        args = getArguments();
        if (args != null) loadData();

        binding.fragmentTaskRefuseButton.setOnClickListener(v -> {
            viewModel.cancelTakeTask(taskID).observe(requireActivity(), statusCode -> {
                if (statusCode >= 200 && statusCode < 400) {
                    binding.taskConfirmation.setVisibility(View.GONE);
                    binding.fragmentTaskRefuseButton.setVisibility(View.GONE);
                    binding.fragmentTaskBeginButton.setVisibility(View.VISIBLE);
                }
            });
        });

        binding.fragmentTaskBeginButton.setOnClickListener(v -> {
            viewModel.takeTask(taskID, "", null, null, null).observe(requireActivity(), statusCode -> {
                if (statusCode >= 200 && statusCode < 400) {
                    binding.fragmentTaskBeginButton.setVisibility(View.GONE);
                    binding.fragmentTaskRefuseButton.setVisibility(View.VISIBLE);
                    binding.taskConfirmation.setVisibility(View.VISIBLE);
                    binding.theTaskDescription.setEnabled(true);
                }
            });
        });

        binding.deleteTaskButton.setOnClickListener(View -> viewModel.deleteTask(taskID));

        binding.confirmTaskPhoto1.setScaleType(ImageView.ScaleType.FIT_CENTER);
        binding.confirmTaskPhoto1.setAdjustViewBounds(true);

        binding.confirmTaskPhoto2.setScaleType(ImageView.ScaleType.FIT_CENTER);
        binding.confirmTaskPhoto2.setAdjustViewBounds(true);

        binding.confirmTaskPhoto3.setScaleType(ImageView.ScaleType.FIT_CENTER);
        binding.confirmTaskPhoto3.setAdjustViewBounds(true);

        binding.confirmTaskPhoto1.setOnClickListener(v -> loadPhoto(1));
        binding.confirmTaskPhoto2.setOnClickListener(v -> loadPhoto(2));
        binding.confirmTaskPhoto3.setOnClickListener(v -> loadPhoto(3));

        binding.fragmentTaskConfirmationSendButton.setOnClickListener(v -> {
            String text = binding.taskReportEditText.getText().toString().isEmpty() ? "" : binding.taskReportEditText.getText().toString();
            if (file1 == null || file2 == null || file3 == null) Toast.makeText(requireContext(), "Вы должны загрузить 3 изображения", Toast.LENGTH_SHORT).show();
            else viewModel.takeTask(taskID, text, file1, file2, file3);
        });

        binding.fragmentTaskAcceptButton.setOnClickListener(View -> {
            viewModel.makeTaskDone(taskID);
        });

        binding.fragmentTaskDeclineButton.setOnClickListener(v -> {
            viewModel.cancelTakeTask(taskID).observe(requireActivity(), statusCode -> {
                if (statusCode >= 200 && statusCode < 400) {
                    binding.fragmentTaskAcceptButton.setVisibility(View.GONE);
                    binding.fragmentTaskDeclineButton.setVisibility(View.GONE);
                    binding.taskConfirmation.setVisibility(View.GONE);
                    binding.taskReportEditText.setVisibility(View.GONE);
                }
            });
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getNavigation().observe(getViewLifecycleOwner(), isNavigation -> {
            if (isNavigation) {
                Navigation.findNavController(requireView()).navigate(R.id.action_taskFragment_to_profileFragment);
                viewModel.setCancelNavigation();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.setCancelNavigation();
    }

    private void loadData() {
        taskID = args.getString("taskID", "");
        viewModel.getTask(taskID).observe(requireActivity(), task -> {
            if (task != null) {
                binding.taskTitle.setText(task.getName());
                binding.theTaskDescription.setText(task.getDescription());
                binding.theTaskAwardPoints.setText("Баллы в награду: " + Integer.toString(task.getScores()));
                binding.taskAuthorName.setText(task.getAuthorName());

                if (task.getAuthorID().equals(storageHandler.getUserID())) {
                    binding.fragmentTaskRefuseButton.setVisibility(View.GONE);
                    binding.fragmentTaskBeginButton.setVisibility(View.GONE);
                    binding.fragmentTaskConfirmationSendButton.setVisibility(View.GONE);
                    binding.taskConfirmation.setVisibility(View.GONE);
                    if (task.getUserID() != null && task.getImages() != null) binding.deleteTaskButton.setVisibility(View.GONE);
                    else binding.deleteTaskButton.setVisibility(View.VISIBLE);

                    if (task.getUserDescription() == null) binding.taskReportEditText.setVisibility(View.GONE);
                    else {
                        binding.taskReportEditText.setVisibility(View.VISIBLE);
                        binding.taskReportEditText.setText(task.getUserDescription());
                        binding.taskReportEditText.setEnabled(false);
                    }
                }
                if (task.getUserID() != null && !task.getUserID().isEmpty() && task.getAuthorID().equals(storageHandler.getUserID())) {
                    binding.fragmentTaskRefuseButton.setVisibility(View.VISIBLE);
                    binding.taskConfirmation.setVisibility(View.VISIBLE);
                    binding.theTaskDescription.setEnabled(false);
                } else {
                    if (!task.getAuthorID().equals(storageHandler.getUserID())) binding.fragmentTaskBeginButton.setVisibility(View.VISIBLE);
                }

                binding.taskReportEditText.setVisibility(View.VISIBLE);
                if (task.getUserDescription() != null) {
                    binding.taskReportEditText.setText(task.getUserDescription());
                    binding.taskReportEditText.setEnabled(false);
                }

                if (task.getAuthorID().equals(storageHandler.getUserID()) && task.getUserID() != null && !task.getUserID().isEmpty()) {
                    binding.fragmentTaskAcceptButton.setVisibility(View.VISIBLE);
                    binding.fragmentTaskDeclineButton.setVisibility(View.VISIBLE);
                }

                if (task.getImages() != null && !task.getImages().isEmpty() && (task.getUserID().equals(storageHandler.getUserID()))) {
                    binding.fragmentTaskConfirmationSendButton.setVisibility(View.GONE);

                    if (task.getImages().get(0) != null) {
                        binding.taskConfirmation.setVisibility(View.VISIBLE);
                        String url = "http://178.21.8.29:8080/image/" + task.getImages().get(0);
                        Picasso.get().load(url).into(binding.confirmTaskPhoto1);
                    }
                    if (task.getImages().get(1) != null) {
                        String url = "http://178.21.8.29:8080/image/" + task.getImages().get(1);
                        Picasso.get().load(url).into(binding.confirmTaskPhoto2);
                    }
                    if (task.getImages().get(2) != null) {
                        String url = "http://178.21.8.29:8080/image/" + task.getImages().get(2);
                        Picasso.get().load(url).into(binding.confirmTaskPhoto3);
                    }
                }
            }
        });
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

                    switch (currentFile) {
                        case 2: file2 = new File(imagePath); binding.confirmTaskPhoto2.setImageBitmap(originalBitmap); break;
                        case 3: file3 = new File(imagePath); binding.confirmTaskPhoto3.setImageBitmap(originalBitmap); break;
                        default: file1 = new File(imagePath); binding.confirmTaskPhoto1.setImageBitmap(originalBitmap); break;
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Bundle extras = data.getExtras();
                Bitmap bitmap = (Bitmap) extras.get("data");
                File f = new File(requireContext().getCacheDir(), "test");
                try {
                    f.createNewFile();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);

                    byte[] bitmapdata = bos.toByteArray();

                    FileOutputStream fos = new FileOutputStream(f);
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();

                    switch (currentFile) {
                        case 2: file2 = f; binding.confirmTaskPhoto2.setImageBitmap(bitmap); break;
                        case 3: file3 = f; binding.confirmTaskPhoto3.setImageBitmap(bitmap); break;
                        default: file1 = f; binding.confirmTaskPhoto1.setImageBitmap(bitmap); break;
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void loadPhoto(int currentNum) {
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
            currentFile = currentNum;

            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");

            Intent chooserIntent = Intent.createChooser(intent, "Choose Photo");

            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{cameraIntent});

            startActivityForResult(chooserIntent, SELECT_PHOTO_PROFILE);
        }
    }
}