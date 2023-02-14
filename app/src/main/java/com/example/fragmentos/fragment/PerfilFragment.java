package com.example.fragmentos.fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fragmentos.R;

public class PerfilFragment extends Fragment {


    public PerfilFragment() {}

    public static Fragment newInstance() {
        PerfilFragment fragment = new PerfilFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        setStatusBarColor();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil, container, false);

    }
    private void setStatusBarColor(){
        if(getActivity()!=null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.principal, this.getActivity().getTheme()));
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.principal, this.getActivity().getTheme()));
            }
        }
    }
}

