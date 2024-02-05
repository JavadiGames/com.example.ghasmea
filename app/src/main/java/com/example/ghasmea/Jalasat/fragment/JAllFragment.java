package com.example.ghasmea.Jalasat.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ghasmea.R;


public class JAllFragment extends Fragment {


    public static JAllFragment newInstance() {return new JAllFragment();}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_j_all, container, false);
    }
}