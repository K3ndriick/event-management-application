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

import com.fit2081.fit2081_a1.provider.CategoryClass;
import com.fit2081.fit2081_a1.provider.EMAViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentListCategory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentListCategory extends Fragment {

//    ArrayList<CategoryClass> listCategories = new ArrayList<>();
    CategoryRecyclerAdapter categoryRecyclerAdapter;
    RecyclerView categoryRecyclerView;
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

    public FragmentListCategory() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentListCategory.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentListCategory newInstance(String param1, String param2) {
        FragmentListCategory fragment = new FragmentListCategory();
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
        View categoryFragmentView = inflater.inflate(R.layout.fragment_list_category, container, false);

        categoryRecyclerView = categoryFragmentView.findViewById(R.id.recyclerViewCategory);

        layoutManager = new LinearLayoutManager(getContext());
        categoryRecyclerView.setLayoutManager(layoutManager);

//        listCategories = addCategoryToRecyclerView(requireContext(), "category_key");

        categoryRecyclerAdapter = new CategoryRecyclerAdapter();
//        categoryRecyclerAdapter.setCategoryData(listCategories);
        categoryRecyclerView.setAdapter(categoryRecyclerAdapter);

        mEmaViewModel = new ViewModelProvider(this).get(EMAViewModel.class);
        mEmaViewModel.getAllCategories().observe(getViewLifecycleOwner(), categoryData -> {
            categoryRecyclerAdapter.setCategoryData(categoryData);
            categoryRecyclerAdapter.notifyDataSetChanged();
        });

        return categoryFragmentView;
    }


//    public ArrayList<CategoryClass> addCategoryToRecyclerView(Context context, String categoryKeyString){
//        SharedPreferences categorySharedPreference = context.getSharedPreferences("show_recycler", Context.MODE_PRIVATE);
//        String json = categorySharedPreference.getString("category_key", String.valueOf(new ArrayList<CategoryClass>()));
//        Type type = new TypeToken<ArrayList<CategoryClass>>() {}.getType();
//
//        return GSON.fromJson(json,type);
//    }
}