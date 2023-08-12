package com.example.ecoapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecoapp.Adapter.ComingAdapter;
import com.example.ecoapp.Model.Coming;

import java.util.ArrayList;
import java.util.List;

public class EventsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView comingRecyclerView;

    public EventsFragment() {

    }

    public static EventsFragment newInstance(String param1, String param2) {
        EventsFragment fragment = new EventsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        comingRecyclerView = view.findViewById(R.id.comingRecyclerView);
        comingRecyclerView.setHasFixedSize(true);
        comingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        List<Coming> comingList = new ArrayList<>();
        comingList.add(new Coming(R.drawable.coming, "Cубботник в парке Горького"));
        comingList.add(new Coming(R.drawable.coming, "Cубботник в парке Горького"));
        comingList.add(new Coming(R.drawable.coming, "Cубботник в парке Горького"));
        comingList.add(new Coming(R.drawable.coming, "Cубботник в парке Горького"));

        ComingAdapter comingAdapter = new ComingAdapter(comingList);
        comingRecyclerView.setAdapter(comingAdapter);

        return view;
    }
}