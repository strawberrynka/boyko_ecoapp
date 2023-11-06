package com.example.ecoapp.presentation.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ecoapp.R;
import com.example.ecoapp.databinding.FragmentEventBinding;
import com.example.ecoapp.domain.helpers.StorageHandler;
import com.example.ecoapp.data.models.EventCustom;
import com.example.ecoapp.presentation.MainActivity;
import com.example.ecoapp.presentation.adapters.CommentAdapter;
import com.example.ecoapp.presentation.viewmodels.EventViewModel;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class EventFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private FragmentEventBinding binding;
    public EventViewModel viewModel;
    private EventCustom eventCustom;
    private StorageHandler storageHandler;
    private Bundle args;
    private CommentAdapter commentAdapter;

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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.cancelNavigate();

        binding.eventTitle.setText("");
        binding.theEventDescription.setText("");
        binding.theEventDate.setText("");
        binding.theEventTime.setText("");
        binding.theEventAddress.setText("");
        binding.theEventAwardPoints.setText("");
        binding.theEventCurrentPeopleAmount.setText("");
        binding.eventAuthorName.setText("");
        binding.takePartInButton.setVisibility(View.GONE);
        binding.finishEvent.setVisibility(View.GONE);
        binding.refuseButton.setVisibility(View.GONE);

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_event, container, false);
        storageHandler = new StorageHandler(requireContext());
        binding.setThemeInfo(storageHandler.getTheme());

        viewModel = new ViewModelProvider(requireActivity()).get(EventViewModel.class);

        binding.commentRecyclerView.setHasFixedSize(true);
        binding.commentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        args = getArguments();
        if (args == null) Navigation.findNavController(requireView()).navigate(R.id.homeFragment);
        else {
            loadData();

            binding.eventLoader.setOnRefreshListener(this);

            binding.takePartInButton.setOnClickListener(View -> {
                showButton(true);

                viewModel.enroll(eventCustom.getEventID()).observe(requireActivity(), statusCode -> {
                    if (statusCode != 0) {
                        if (statusCode >= 400) showButton(false);
                    }
                });
            });

            binding.refuseButton.setOnClickListener(View -> {
                showButton(false);

                viewModel.refusePeople(eventCustom.getEventID()).observe(requireActivity(), statusCode -> {
                    if (statusCode >= 400) showButton(true);
                });
            });

            binding.theEventBackToPreviousFragmentButton.setOnClickListener(v -> {
                Navigation.findNavController(v).popBackStack();
            });

            binding.currentEventViewMap.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putDouble("longt", eventCustom.getLongt());
                bundle.putDouble("lat", eventCustom.getLat());
                Navigation.findNavController(v).navigate(R.id.secondMapFragment, bundle);
            });

            binding.theEventCurrentPeopleAmount.setOnClickListener(v -> {
                if (eventCustom != null && storageHandler.getUserID().equals(eventCustom.getAuthorID())) {
                    Bundle args2 = new Bundle();
                    args2.putString("eventID", eventCustom.getEventID());
                    Navigation.findNavController(v).navigate(R.id.userListDialogFragment, args2);
                }
            });

            binding.finishEvent.setOnClickListener(View -> viewModel.deleteEvent(eventCustom.getEventID()));

            binding.postCommentButton.setOnClickListener(View -> {
                String content = binding.writeEventCommentEditText.getText().toString();
                if (content.isEmpty()) Toast.makeText(requireContext(), "Вы не написали комментарий", Toast.LENGTH_SHORT).show();
                else {
                    viewModel.createComment(eventCustom.getEventID(), content).observe(requireActivity(), comments -> {
                        if (comments != null) {
                            commentAdapter = new CommentAdapter(comments, storageHandler.getTheme());
                            commentAdapter.setOnItemClickListener(this::deleteComment);
                            binding.commentRecyclerView.setAdapter(commentAdapter);

                            binding.writeEventCommentEditText.setText("");
                        }
                    });
                }
            });
        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getNavigate().observe(getViewLifecycleOwner(), isNavigation -> {
            if (isNavigation) {
                Navigation.findNavController(requireView()).navigate(R.id.eventsFragment);
                viewModel.cancelNavigate();
            }
        });
    }

    public void showButton(boolean isJoined) {
        if (isJoined) {
            binding.takePartInButton.setVisibility(View.GONE);
            binding.refuseButton.setVisibility(View.VISIBLE);
        } else {
            binding.takePartInButton.setVisibility(View.VISIBLE);
            binding.refuseButton.setVisibility(View.GONE);
        }
    }

    private void loadData() {
        binding.eventLoader.setRefreshing(true);
        viewModel.getEventByID(args.getString("id", "")).observe(requireActivity(), eventCustom -> {
            if (eventCustom != null) {
                this.eventCustom = eventCustom;

                String url = "http://178.21.8.29:8080/image/" + eventCustom.getPhoto();

                binding.eventTitle.setText(eventCustom.getTitle());
                binding.theEventDescription.setText(eventCustom.getDescription());
                binding.theEventDate.setText(eventCustom.getDate());
                binding.theEventTime.setText(eventCustom.getTime());
                binding.theEventAddress.setText(eventCustom.getPlace());
                binding.theEventAwardPoints.setText(String.valueOf("Баллы в награду: " + eventCustom.getScores()));
                binding.theEventCurrentPeopleAmount.setText("Участники: " + String.valueOf(eventCustom.getUsersList().size()) + " / " + String.valueOf(eventCustom.getMaxUsers()));
                binding.eventAuthorName.setText("Автор: " + eventCustom.getAuthorName());
                if (!storageHandler.getUserID().equals(eventCustom.getAuthorID())) {
                    binding.finishEvent.setVisibility(View.GONE);

                    showButton(eventCustom.getUsersList().contains(storageHandler.getUserID()));
                    if (eventCustom.getCurrentUsers() >= eventCustom.getMaxUsers()) {
                        binding.takePartInButton.setVisibility(View.GONE);
                    }
                } else if (storageHandler.getUserID().equals(eventCustom.getAuthorID())) {
                    binding.finishEvent.setVisibility(View.VISIBLE);
                    binding.refuseButton.setVisibility(View.GONE);
                    binding.takePartInButton.setVisibility(View.GONE);
                }

                Picasso.get().load(url).into(binding.eventImage);
                binding.eventLoader.setRefreshing(false);
            }
        });

        viewModel.getCommentsList(args.getString("id", "")).observe(requireActivity(), comments -> {
            if (comments != null) {
                commentAdapter = new CommentAdapter(comments, storageHandler.getTheme());
                commentAdapter.setOnItemClickListener(this::deleteComment);
                binding.commentRecyclerView.setAdapter(commentAdapter);
            }
        });
    }

    private void deleteComment(String commentID) {
        viewModel.deleteComment(commentID).observe(requireActivity(), statusCode -> {
            if (statusCode >= 200 && statusCode < 400) {
                commentAdapter.deleteItem(commentID);
            }
        });
    }

    @Override
    public void onRefresh() {
        loadData();
    }
}