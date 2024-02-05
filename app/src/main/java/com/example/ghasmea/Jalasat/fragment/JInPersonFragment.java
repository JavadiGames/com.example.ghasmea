package com.example.ghasmea.Jalasat.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ghasmea.R;


public class JInPersonFragment extends Fragment {


/*    public JInPersonFragment() {  \\ no need for the constructor because of the new instance method
        // Required empty public constructor
    }*/

    public static JInPersonFragment newInstance() {return new JInPersonFragment();}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_j_in_person, container, false);
    }
}