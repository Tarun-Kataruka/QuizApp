package com.example.quizapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toolbar;

import com.example.quizapp.Adapters.CategoryAdapter;

public class CategoryFragment extends Fragment {



    public CategoryFragment() {
        // Required empty public constructor
    }

    private GridView catView;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_category, container, false);
//        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
//        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Categories");

        catView = view.findViewById(R.id.cat_Grid);

       //loadCategories();

        CategoryAdapter adapter = new CategoryAdapter(DbQuery.g_catList);

        catView.setAdapter(adapter);

        return view;

    }
}