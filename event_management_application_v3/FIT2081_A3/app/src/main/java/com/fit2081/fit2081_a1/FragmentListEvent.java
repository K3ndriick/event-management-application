package com.fit2081.fit2081_a1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fit2081.fit2081_a1.provider.EMAViewModel;
import com.fit2081.fit2081_a1.provider.EventClass;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentListEvent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentListEvent extends Fragment {

//    ArrayList<EventClass> listEvents = new ArrayList<>();
    EventRecyclerAdapter eventRecyclerAdapter;
    RecyclerView eventRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    Gson GSON = new Gson();

    private EMAViewModel mEmaViewModel;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentListEvent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentListEvent.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentListEvent newInstance(String param1, String param2) {
        FragmentListEvent fragment = new FragmentListEvent();
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
        // Inflate the layout for this fragment
        View eventFragmentView = inflater.inflate(R.layout.fragment_list_event, container, false);

        eventRecyclerView = eventFragmentView.findViewById(R.id.recyclerViewEvents);

        layoutManager = new LinearLayoutManager(getContext());
        eventRecyclerView.setLayoutManager(layoutManager);

//        listEvents = addEventToRecyclerView(requireContext(), "event_key");

        eventRecyclerAdapter = new EventRecyclerAdapter();
//        eventRecyclerAdapter.setEventData(listEvents);
        eventRecyclerView.setAdapter(eventRecyclerAdapter);

        mEmaViewModel = new ViewModelProvider(this).get(EMAViewModel.class);
        mEmaViewModel.getAllEvents().observe(getViewLifecycleOwner(), eventData -> {
            eventRecyclerAdapter.setEventData(eventData);
            eventRecyclerAdapter.notifyDataSetChanged();
        });

        return eventFragmentView;
    }


//    public ArrayList<EventClass> addEventToRecyclerView(Context context, String eventKeyString){
//        SharedPreferences eventSharedPreference = context.getSharedPreferences("show_recycler", Context.MODE_PRIVATE);
//        String json = eventSharedPreference.getString("event_key", String.valueOf(new ArrayList<EventClass>()));
//        Type type = new TypeToken<ArrayList<EventClass>>() {}.getType();
//
//        return GSON.fromJson(json,type);
//    }
}