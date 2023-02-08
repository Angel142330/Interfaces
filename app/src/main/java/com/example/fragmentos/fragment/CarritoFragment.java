package com.example.fragmentos.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fragmentos.CartAdapter;
import com.example.fragmentos.R;
import com.example.fragmentos.SharedViewModel;

import java.util.ArrayList;
import java.util.List;

public class CarritoFragment extends Fragment {

    private SharedViewModel viewModel;

    private RecyclerView recyclerView;
    private CartAdapter carritoAdapter;
    private ArrayList<Comida> listaCarrito;
    public CarritoFragment() {
        // Required empty public constructor
    }
    public static CarritoFragment newInstance() {
        CarritoFragment fragment = new CarritoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_compra, container, false);


        recyclerView = view.findViewById(R.id.recyclerView_carrito);
        listaCarrito = new ArrayList<>();
        carritoAdapter = new CartAdapter(listaCarrito,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(carritoAdapter);
        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        viewModel.listaCarrito.observe(getViewLifecycleOwner(), lista -> {
            listaCarrito.clear();
            listaCarrito.addAll(lista);
            carritoAdapter.notifyDataSetChanged();
        });

        return view;
    }
}